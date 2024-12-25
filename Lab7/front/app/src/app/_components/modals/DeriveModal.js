'use client';

import React, { useState } from 'react';
import axios from 'axios';

function DeriveModal({ isOpen, onClose }) {
    const [result, setResult] = useState(null); // Результат вычисления
    const [executionTime, setExecutionTime] = useState(null); // Время выполнения

    if (!isOpen) return null;

    const handleOutsideClick = (e) => {
        if (e.target.id === 'modal-overlay') {
            onClose();
        }
    };

    const calculateDerive = async () => {
        try {
            const startTime = performance.now();

            const response = await axios.post('/api/operands/derive', null, {

            });

            const endTime = performance.now();
            setResult(response.data); // Сохраняем результат вычисления
            setExecutionTime((endTime - startTime).toFixed(2)); // Время выполнения в миллисекундах
        } catch (error) {
            console.error('Ошибка при вычислении дифференциала:', error);
            alert('Ошибка при вычислении. Попробуйте снова.');
        }
    };

    const saveResult = async () => {
        if (!result) {
            alert('Нет результата для сохранения.');
            return;
        }

        try {
            await axios.post('/api/operands/binary', result, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            alert('Результат успешно сохранён.');
        } catch (error) {
            console.error('Ошибка при сохранении результата:', error);
            alert('Ошибка при сохранении результата.');
        }
    };

    const loadResult = async () => {
        try {
            const response = await axios.get('/api/operands/binary', {
            });

            setResult(response.data); // Загружаем результат в состояние
            alert('Результат успешно загружен.');
        } catch (error) {
            console.error('Ошибка при загрузке результата:', error);
            alert('Ошибка при загрузке результата.');
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
                <h2 className="text-xl font-bold mb-4 text-primary-content">Вычисление дифференциала</h2>
                <div className="flex justify-between items-center mb-4">
                    <button
                        onClick={calculateDerive}
                        className="btn btn-primary w-full"
                    >
                        Вычислить
                    </button>
                </div>
                {result !== null && (
                    <div className="mt-4 text-base-content">
                        <h3 className="text-lg font-bold mb-2">Результат</h3>
                        <pre className="font-mono bg-gray-100 p-2 rounded">{JSON.stringify(result, null, 2)}</pre>
                        <p>Время выполнения: <span className="font-mono">{executionTime} мс</span></p>
                    </div>
                )}
                <div className="flex-col gap-4 mt-6">
                    <button onClick={saveResult} className="btn btn-secondary mb-2 w-full">
                        Сохранить
                    </button>
                    <button onClick={loadResult} className="btn btn-accent mb-2 w-full">
                        Загрузить
                    </button>
                    <button onClick={onClose} className="btn btn-error mb-2 w-full">
                        Закрыть
                    </button>
                </div>
            </div>
        </div>
    );
}

export default DeriveModal;
