import React, { useState } from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import axios from 'axios'; // Для отправки запросов на сервер
import "../styleScroll.css";

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

function GraphModal({ isOpen, onClose }) {
    const [xValues, setXValues] = useState([]);
    const [yValues, setYValues] = useState([]);
    const [selectedX, setSelectedX] = useState(null);
    const [calculatedY, setCalculatedY] = useState(null); // Для хранения вычисленного y
    const [selectedOperand, setSelectedOperand] = useState('op1'); // Состояние для выбранного операнда

    // Список доступных операндов
    const operands = ['op1', 'op2', 'op3', 'result'];

    const generateFunction = (xData, yData) => {
        // Получаем массивы x и y от сервера и строим график
        setXValues(xData);
        setYValues(yData);
    };

    const handleSave = () => {
        // Логика для сохранения функции
        console.log('Функция сохранена', { xValues, yValues });
        onClose();
    };

    const handleCalculateAtX = async () => {
        if (selectedX === null || isNaN(selectedX)) {
            alert('Введите корректное значение x');
            return;
        }

        try {
            const response = await axios.get('/api/operands/calculateY', {
                params: {
                    id: selectedOperand, // ID функции
                    x: selectedX, // Точка, в которой вычисляем y
                },
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            console.log(response.data);

            if (response.data !== 'undefined') {
                setCalculatedY(response.data); // Обновляем вычисленное значение y
            } else {
                alert('Сервер не вернул корректное значение y');
            }
        } catch (error) {
            console.error('Ошибка при вычислении значения y:', error);
            alert('Не удалось вычислить значение y. Попробуйте снова.');
        }
    };

    const handleOutsideClick = (e) => {
        if (e.target.id === 'modal-overlay') {
            onClose();
        }
    };

    // Функция для отправки запроса на сервер и получения данных по выбранному операнду
    const fetchOperandData = async () => {
        try {
            // Используем axios для выполнения запроса с новым форматом данных
            const response = await axios.get('/api/operands/get', {
                params: {
                    id: selectedOperand,
                },
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            console.log(response.data);

            // Проверка на корректность данных
            if (response.data && response.data.x && response.data.y) {
                generateFunction(response.data.x, response.data.y); // Строим график по данным
            } else {
                console.error('Данные от сервера не содержат массивов x и y');
            }
        } catch (error) {
            console.error('Ошибка при получении данных от сервера:', error);
        }
    };

    const data = {
        labels: xValues,
        datasets: [
            {
                label: 'Функция',
                data: yValues,
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1,
            },
        ],
    };

    if (!isOpen) return null;

    return (
        <div
            id="modal-overlay"
            className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
            onClick={handleOutsideClick}
        >
            <div
                className="bg-white p-6 rounded-lg shadow-md w-full max-w-4xl overflow-hidden"
                onClick={(e) => e.stopPropagation()}
            >
                <h2 className="text-xl font-bold text-base-content mb-4">График функции</h2>

                <div className="flex gap-4 mb-4">
                    <select
                        className="select select-bordered w-full text-base-content"
                        value={selectedOperand}
                        onChange={(e) => setSelectedOperand(e.target.value)}
                    >
                        {operands.map((operand) => (
                            <option key={operand} value={operand}>
                                {operand}
                            </option>
                        ))}
                    </select>

                    <button className="btn btn-info" onClick={fetchOperandData}>
                        Получить данные для операнда
                    </button>
                </div>

                <div className="mb-4">
                    <Line data={data} />
                </div>

                <div className="mb-4 max-h-[130px] overflow-y-auto scroll-container">
                    <table className="table-auto w-full border-collapse text-base-content">
                        <thead>
                        <tr>
                            <th className="border px-4 py-2">x</th>
                            <th className="border px-4 py-2">y</th>
                        </tr>
                        </thead>
                        <tbody>
                        {xValues.map((x, index) => (
                            <tr key={index}>
                                <td className="border px-4 py-2">{x}</td>
                                <td className="border px-4 py-2">
                                    <input
                                        type="number"
                                        value={isNaN(yValues[index]) ? '' : yValues[index]} // Если значение NaN, показываем пустую строку
                                        onChange={(e) => {
                                            const newYValues = [...yValues];
                                            const value = parseFloat(e.target.value);
                                            newYValues[index] = isNaN(value) ? '' : value; // Если значение некорректное, заменяем на пустую строку
                                            setYValues(newYValues);
                                        }}
                                        className="w-full"
                                    />
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                <div className="flex gap-4 mb-4">
                    <button className="btn btn-primary" onClick={() => generateFunction([], [])}>
                        Создать функцию
                    </button>
                    <button className="btn btn-success" onClick={handleSave}>
                        Сохранить
                    </button>
                    <button className="btn btn-error" onClick={onClose}>
                        Закрыть
                    </button>
                </div>

                <div className="flex gap-4">
                    <input
                        placeholder="Введите x"
                        value={selectedX || ''}
                        onChange={(e) => setSelectedX(parseFloat(e.target.value))}
                        className="input input-bordered text-base-content"
                    />
                    <button className="btn btn-info" onClick={handleCalculateAtX}>
                        Вычислить значение в точке x
                    </button>
                </div>

                {calculatedY !== null && (
                    <div className="mt-4 text-lg text-base-content">
                        Значение функции в точке x = {selectedX}: y = {calculatedY}
                    </div>
                )}
            </div>
        </div>
    );
}

export default GraphModal;
