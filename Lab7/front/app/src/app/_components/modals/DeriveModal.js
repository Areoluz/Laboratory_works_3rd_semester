'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';

function DeriveModal({ isOpen, onClose }) {
    const [result, setResult] = useState(null); // Результат вычисления
    const [executionTime, setExecutionTime] = useState(null); // Время выполнения
    const [operands, setOperands] = useState([]); // Список операндов
    const [selectedOperand, setSelectedOperand] = useState(''); // Выбранный операнд

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

    const calculateDerive = async () => {
        if (!selectedOperand) {
            alert('Выберите операнд для вычисления!');
            return;
        }

        try {
            const startTime = performance.now();

            const response = await axios.post('/api/operands/derive', null, {
                params: {
                    op: selectedOperand,
                    result: 'op3',
                },
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
            const response = await axios.get('/api/operands/binary');
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

                {/* Селектор для выбора операнда */}
                <div className="mb-4">
                    <label className="block text-sm font-medium text-base-content mb-2">Выберите операнд:</label>
                    <select
                        className="select select-bordered w-full text-base-content"
                        value={selectedOperand}
                        onChange={(e) => setSelectedOperand(e.target.value)}
                    >
                        <option value="">Выберите операнд </option>
                        {operands.map((operand) => (
                            <option key={operand.id} value={operand.id}>
                                {operand.id}
                            </option>
                        ))}
                    </select>
                </div>

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
