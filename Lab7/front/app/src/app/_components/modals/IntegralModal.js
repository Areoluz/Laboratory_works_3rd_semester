'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';

function IntegralModal({ isOpen, onClose }) {
    const [result, setResult] = useState(null); // Результат вычисления
    const [executionTime, setExecutionTime] = useState(null); // Время выполнения
    const [operands, setOperands] = useState([]); // Список операндов
    const [selectedOperand, setSelectedOperand] = useState(''); // Выбранный операнд
    const [threadCount, setThreadCount] = useState(1); // Количество потоков (не используется, но можно добавить по желанию)

    if (!isOpen) return null;

    const handleOutsideClick = (e) => {
        if (e.target.id === 'modal-overlay') {
            onClose();
        }
    };

    // Загрузка всех операндов при открытии модального окна
    useEffect(() => {
        const fetchOperands = async () => {
            try {
                const token = localStorage.getItem('authToken');

                const response = await axios.get('/api/operands/getAll', {
                    headers: {
                        'Authorization': token ? `Bearer ${token}` : '', // Добавляем токен в заголовок
                    },
                });

                // Преобразуем объект в массив операндов
                const transformedOperands = Object.entries(response.data).map(([key, value]) => ({
                    id: key,
                    x: value.x,
                    y: value.y,
                }));

                setOperands(transformedOperands);
            } catch (error) {
                console.error('Ошибка при загрузке операндов:', error);
                alert('Не удалось загрузить список операндов. Попробуйте позже.');
            }
        };

        if (isOpen) {
            fetchOperands();
        }
    }, [isOpen]);

    const calculateIntegral = async () => {
        if (!selectedOperand) {
            alert('Выберите операнд для вычисления!');
            return;
        }

        try {
            const startTime = performance.now();

            const response = await axios.post('/api/operands/integrate', null, {
                params: {
                    op: selectedOperand,
                },
            });

            const endTime = performance.now();
            setResult(response.data); // Сохраняем результат вычисления
            setExecutionTime((endTime - startTime).toFixed(2)); // Время выполнения в миллисекундах
        } catch (error) {
            console.error('Ошибка при вычислении интеграла:', error);
            alert('Ошибка при вычислении. Попробуйте снова.');
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

                {/* Селектор для выбора операнда */}
                <div className="mb-4">
                    <label className="block text-sm font-medium text-base-content mb-2">Выберите операнд:</label>
                    <select
                        className="select select-bordered w-full text-base-content"
                        value={selectedOperand}
                        onChange={(e) => setSelectedOperand(e.target.value)}
                    >
                        <option value="">Выберите операнд</option>
                        {operands.map((operand) => (
                            <option key={operand.id} value={operand.id}>
                                {operand.id}
                            </option>
                        ))}
                    </select>
                </div>

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
                        <pre className="font-mono bg-gray-100 p-2 rounded">{JSON.stringify(result, null, 2)}</pre>
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
