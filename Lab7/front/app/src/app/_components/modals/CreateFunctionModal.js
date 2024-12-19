'use client';

import React, { useState } from 'react';

function CreateFunctionModal({ isOpen, onClose, onCreate }) {
    const [scenario, setScenario] = useState(null); // null, 'array', or 'mathFunction'
    const [pointCount, setPointCount] = useState('');
    const [tableData, setTableData] = useState([]);
    const [functionSelect, setFunctionSelect] = useState('');
    const [intervalStart, setIntervalStart] = useState('');
    const [intervalEnd, setIntervalEnd] = useState('');
    const [pointCountMath, setPointCountMath] = useState('');

    const handleGenerateTable = () => {
        const rows = Array.from({ length: Number(pointCount) }, () => ({ x: '', y: '' }));
        setTableData(rows);
    };

    const handleCreateArrayFunction = () => {
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

        onCreate({ type: 'array', data: tableData });
        onClose();
    };

    const handleCreateMathFunction = () => {
        if (!functionSelect || pointCountMath < 2 || intervalStart >= intervalEnd) {
            alert('Введите корректные значения.');
            return;
        }

        const step = (intervalEnd - intervalStart) / (pointCountMath - 1);
        const xValues = Array.from({ length: pointCountMath }, (_, i) => intervalStart + i * step);
        const yValues = xValues.map(x => evaluateMathFunction(functionSelect, x)); // Ваша логика вычисления
        const data = xValues.map((x, i) => ({ x, y: yValues[i] }));

        onCreate({ type: 'mathFunction', data });
        onClose();
    };

    const evaluateMathFunction = (func, x) => {
        switch (func) {
            case 'sqr': return x ** 2;
            case 'sin': return Math.sin(x);
            case 'cos': return Math.cos(x);
            default: return x;
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
                        <select
                            value={functionSelect}
                            onChange={(e) => setFunctionSelect(e.target.value)}
                            className="select select-bordered text-base-content w-full mb-4"
                        >
                            <option value="" disabled>Выберите функцию</option>
                            <option value="sqr">Квадратичная</option>
                            <option value="sin">Синус</option>
                            <option value="cos">Косинус</option>
                        </select>
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
