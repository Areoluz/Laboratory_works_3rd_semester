import React, { useState } from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import "../styleScroll.css"

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

function GraphModal({ isOpen, onClose }) {
    const [functionData, setFunctionData] = useState([]);
    const [xValues, setXValues] = useState([]);
    const [yValues, setYValues] = useState([]);
    const [selectedX, setSelectedX] = useState(null);
    const [calculatedY, setCalculatedY] = useState(null); // Добавлено для хранения вычисленного y

    const generateFunction = (xStart = -3, xEnd = 3, step = 1) => {
        let newXValues = [];
        let newYValues = [];
        for (let x = xStart; x <= xEnd; x += step) {
            newXValues.push(x);
            newYValues.push(Math.sin(x)); // Пример функции: y = sin(x)
        }
        setXValues(newXValues);
        setYValues(newYValues);
        setFunctionData(newYValues.map((y, index) => ({ x: newXValues[index], y })));
    };

    const handleSave = () => {
        // Логика для сохранения функции
        console.log('Функция сохранена', { xValues, yValues });
        onClose();
    };

    const handleCalculateAtX = () => {
        if (selectedX !== null) {
            const yAtX = Math.sin(selectedX); // Пример вычисления
            setCalculatedY(yAtX); // Обновляем состояние с вычисленным y
        }
    };

    const handleChangeY = (index, value) => {
        const newYValues = [...yValues];
        newYValues[index] = value;
        setYValues(newYValues);
        const newFunctionData = newYValues.map((y, idx) => ({ x: xValues[idx], y }));
        setFunctionData(newFunctionData);
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
                className="bg-white p-6 rounded-lg shadow-md w-full max-w-4xl overflow-hidden"
                onClick={(e) => e.stopPropagation()}
            >
                <h2 className="text-xl font-bold text-base-content mb-4">График функции</h2>

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
                                        value={yValues[index]}
                                        onChange={(e) => handleChangeY(index, parseFloat(e.target.value))}
                                        className="w-full"
                                    />
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                <div className="flex gap-4 mb-4">
                    <button className="btn btn-primary" onClick={() => generateFunction()}>
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
