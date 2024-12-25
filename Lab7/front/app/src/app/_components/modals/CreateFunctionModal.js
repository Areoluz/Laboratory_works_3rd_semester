'use client';

import React, {useEffect, useState} from 'react';
import axios from 'axios';
import { useFunctions } from '../FunctionsContext'; // Импорт контекста

function CreateFunctionModal({ isOpen, onClose, onCreate }) {
    const [scenario, setScenario] = useState(null); // null, 'array', or 'mathFunction'
    const [pointCount, setPointCount] = useState('');
    const [tableData, setTableData] = useState([]);
    const [functionSelect, setFunctionSelect] = useState('');
    const [intervalStart, setIntervalStart] = useState('');
    const [intervalEnd, setIntervalEnd] = useState('');
    const [pointCountMath, setPointCountMath] = useState('');
    const [availableFunctions, setAvailableFunctions] = useState([]); // Список функций
    const [loadingFunctions, setLoadingFunctions] = useState(true); // Состояние загрузки
    const [selectedOperand, setSelectedOperand] = useState('op1'); // Выбор операнда: op1 или op2

    // Загрузка списка функций с API
    useEffect(() => {
        const fetchFunctions = async () => {
            try {
                const token = localStorage.getItem('authToken');

                const response = await axios.get('/api/functions/simple/all', {
                    headers: {
                        'Authorization': token ? `Bearer ${token}` : '', // Add token to the request header
                    },
                });
                setAvailableFunctions(response.data); // Сохраняем функции в состоянии
            } catch (error) {
                console.error('Ошибка при загрузке списка функций:', error);
                alert('Не удалось загрузить список функций. Попробуйте позже.');
            } finally {
                setLoadingFunctions(false);
            }
        };

        if (isOpen) {
            fetchFunctions();
        }
    }, [isOpen]);

    const handleGenerateTable = () => {
        const rows = Array.from({ length: Number(pointCount) }, () => ({ x: '', y: '' }));
        setTableData(rows);
    };

    const handleCreateArrayFunction = async () => {
        const hasEmptyFields = tableData.some(row => row.x === '' || row.y === '');
        if (hasEmptyFields) {
            alert('Все значения должны быть заполнены.');
            return;
        }

        const xValues = tableData.map(row => Number(row.x));
        if (new Set(xValues).size !== xValues.length || !xValues.every((_, i, arr) => i === 0 || arr[i] > arr[i - 1])) {
            alert('X-координаты должны быть уникальными и отсортированы по возрастанию.');
            return;
        }

        // Генерация данных в нужном формате с массивами x и y
        const xArray = tableData.map(row => Number(row.x)); // Массив X
        const yArray = tableData.map(row => Number(row.y)); // Массив Y

        const data = { x: xArray, y: yArray };

        console.log(data);

        try {
            // Используем axios для выполнения запроса с новым форматом данных
            const response = await axios.post('/api/functions/array', data, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            console.log(response.data);

            // Устанавливаем функцию как op1
            await axios.post('/api/operands/set', null, {
                params: {
                    'id':selectedOperand,
                },
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            //addFunction({ data: response.data });
            onCreate({ type: 'array', data });
            onClose();
        } catch (error) {
            console.error('Ошибка при добавлении функции:', error);
            if (error.response) {
                console.error('Ответ от сервера:', error.response.data);
            }
            alert('Ошибка при добавлении функции. Попробуйте снова.');
        }
    };

    const handleCreateMathFunction = async () => {
        if (!functionSelect || pointCountMath < 2 || intervalStart >= intervalEnd) {
            alert('Введите корректные значения.');
            return;
        }

        const payload = {
            count: Number(pointCountMath),
            className: functionSelect,
            xstart: Number(intervalStart),
            xend: Number(intervalEnd),
        };

        try {
            const response = await axios.post('/api/functions/simple', payload, {
                //baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
                headers: {
                    'Content-Type': 'application/json',
                    //'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
                },
            });

            // Устанавливаем функцию как op1
            await axios.post('/api/operands/set', null, {
                params: {
                    'id': selectedOperand,
                },
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            console.log(response.data);
            //addFunction({ data: response.data });
            onCreate({ type: 'mathFunction', payload });
            onClose();
        } catch (error) {
            console.error('Ошибка при добавлении функции:', error);
            if (error.response) {
                console.error('Ответ от сервера:', error.response.data);
            }
            alert('Ошибка при добавлении функции. Попробуйте снова.');
        }
    };

    const handleOutsideClick = (e) => {
        if (e.target.id === 'modal-overlay') {
            onClose();
        }
    };

    if (!isOpen) return null;

    return (
        <div
            id="modal-overlay"
            className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
            onClick={handleOutsideClick}
        >
            <div
                className="bg-white p-6 rounded-lg shadow-md w-full max-w-lg"
                onClick={(e) => e.stopPropagation()} // Останавливаем всплытие клика внутри модального окна
            >
                <h2 className="text-xl font-bold mb-4 text-primary-content">Создать новую функцию</h2>
                <div className="mb-4">
                    <label className="block text-base-content font-semibold mb-2">
                        Выберите операнд:
                    </label>
                    <select
                        value={selectedOperand}
                        onChange={(e) => setSelectedOperand(e.target.value)}
                        className="select select-bordered text-base-content w-full"
                    >
                        <option value="op1">op1</option>
                        <option value="op2">op2</option>
                        <option value="op3">op3</option>
                    </select>
                </div>
                {!scenario && (
                    <div className="flex flex-col gap-4">
                        <button
                            onClick={() => setScenario('array')}
                            className="btn btn-primary"
                        >
                            Создать из массивов
                        </button>
                        <button
                            onClick={() => setScenario('mathFunction')}
                            className="btn btn-secondary"
                        >
                            Создать на основе математической функции
                        </button>
                    </div>
                )}
                {scenario === 'array' && (
                    <div>
                        <input
                            placeholder="Количество точек"
                            value={pointCount}
                            onChange={(e) => setPointCount(e.target.value)}
                            className="input input-bordered w-full text-base-content mb-4"
                        />
                        <button onClick={handleGenerateTable} className="btn btn-primary mb-4">
                            Сгенерировать таблицу
                        </button>
                        {tableData.length > 0 && (
                            <table className="table w-full mb-4">
                                <thead>
                                <tr>
                                    <th>X</th>
                                    <th>Y</th>
                                </tr>
                                </thead>
                                <tbody>
                                {tableData.map((row, index) => (
                                    <tr key={index}>
                                        <td>
                                            <input
                                                value={row.x}
                                                onChange={(e) => {
                                                    const newData = [...tableData];
                                                    newData[index].x = e.target.value;
                                                    setTableData(newData);
                                                }}
                                                className="input text-base-content input-bordered w-full"
                                            />
                                        </td>
                                        <td>
                                            <input
                                                value={row.y}
                                                onChange={(e) => {
                                                    const newData = [...tableData];
                                                    newData[index].y = e.target.value;
                                                    setTableData(newData);
                                                }}
                                                className="input text-base-content input-bordered w-full"
                                            />
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        )}
                        <div className="flex gap-4">
                            <button onClick={handleCreateArrayFunction} className="btn btn-secondary">
                                Создать функцию
                            </button>
                            <button onClick={onClose} className="btn btn-error">
                                Отмена
                            </button>
                        </div>
                    </div>
                )}
                {scenario === 'mathFunction' && (
                    <div>
                        {loadingFunctions ? (
                            <p>Загрузка списка функций...</p>
                        ) : (
                            <select
                                value={functionSelect}
                                onChange={(e) => setFunctionSelect(e.target.value)}
                                className="select select-bordered text-base-content w-full mb-4"
                            >
                                <option value="" disabled>Выберите функцию</option>
                                {availableFunctions.map((func) => (
                                    <option key={func.className} value={func.className}>
                                        {func.name}
                                    </option>
                                ))}
                            </select>
                        )}
                        <input
                            placeholder="Количество точек"
                            value={pointCountMath}
                            onChange={(e) => setPointCountMath(e.target.value)}
                            className="input input-bordered text-base-content w-full mb-4"
                        />
                        <input
                            placeholder="Начало интервала"
                            value={intervalStart}
                            onChange={(e) => setIntervalStart(Number(e.target.value))}
                            className="input input-bordered text-base-content w-full mb-4"
                        />
                        <input
                            placeholder="Конец интервала"
                            value={intervalEnd}
                            onChange={(e) => setIntervalEnd(Number(e.target.value))}
                            className="input input-bordered text-base-content w-full mb-4"
                        />
                        <div className="flex gap-4">
                            <button onClick={handleCreateMathFunction} className="btn btn-primary">
                                Создать функцию
                            </button>
                            <button onClick={onClose} className="btn btn-error">
                                Отмена
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default CreateFunctionModal;
