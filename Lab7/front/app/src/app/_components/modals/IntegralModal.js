'use client';

import React, { useState } from 'react';

function IntegralModal({ isOpen, onClose }) {
    const [threadCount, setThreadCount] = useState(1); // Количество потоков
    const [result, setResult] = useState(null); // Результат вычисления
    const [executionTime, setExecutionTime] = useState(null); // Время выполнения

    const calculateIntegral = () => {
        if (threadCount < 1) {
            alert('Количество потоков должно быть больше нуля.');
            return;
        }

        const startTime = performance.now();

        // Пример вычисления интеграла для функции f(x) = x^2 на интервале [0, 1]
        const a = 0; // начало интервала
        const b = 1; // конец интервала
        const steps = 1000000; // количество шагов интегрирования
        const stepSize = (b - a) / steps;
        let integral = 0;

        for (let i = 0; i < steps; i++) {
            const x = a + i * stepSize;
            integral += x ** 2 * stepSize; // Пример: функция f(x) = x^2
        }

        const endTime = performance.now();

        setResult(integral.toFixed(6)); // Округленный результат
        setExecutionTime((endTime - startTime).toFixed(2)); // Время выполнения в миллисекундах
    };

    if (!isOpen) return null;

    const handleOutsideClick = (e) => {
        if (e.target.id === 'modal-overlay') {
            onClose();
        }
    };

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
                <h2 className="text-xl font-bold mb-4 text-primary-content">Вычисление интеграла</h2>
                <div className="flex justify-between items-center mb-4">
                    <button
                        onClick={calculateIntegral}
                        className="btn btn-primary w-full"
                    >
                        Вычислить
                    </button>
                </div>
                {result !== null && (
                    <div className="mt-4 text-base-content">
                        <h3 className="text-lg font-bold mb-2">Результат</h3>
                        <p>Интеграл: <span className="font-mono">{result}</span></p>
                        <p>Время выполнения: <span className="font-mono">{executionTime} мс</span></p>
                    </div>
                )}
                <div className="flex justify-end mt-6">
                    <button onClick={onClose} className="btn btn-error">
                        Закрыть
                    </button>
                </div>
            </div>
        </div>
    );
}

export default IntegralModal;
