'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CreateFunctionModal from './CreateFunctionModal'; // Импортируем компонент для создания функции

function FunctionOperationsModal({ isOpen, onClose }) {
    const [function1, setFunction1] = useState([]);
    const [function2, setFunction2] = useState([]);
    const [resultFunction, setResultFunction] = useState([]);
    const [error, setError] = useState('');
    const [isCreateFunctionModalOpen, setIsCreateFunctionModalOpen] = useState(false);

    // Функция для загрузки данных с сервера
    const fetchFunctionData = async (id, setFunction) => {
        try {
            const response = await axios.get(`/api/operands/get`, {
                params: { id }
            });
            const { x, y } = response.data;
            const newFunction = x.map((xValue, index) => ({
                x: xValue,
                y: y[index]
            }));
            setFunction(newFunction);
        } catch (error) {
            console.error('Ошибка при загрузке данных функции:', error);
            setError('Не удалось загрузить данные для функции.');
        }
    };

    // Функция для обновления значения y
    const handleYChange = async (id, index, value, setFunction, func) => {
        try {
            await axios.post('/api/operands/setY', null, {
                params: {
                    id: id,
                    index: index,
                    value: value
                }
            });

            const newData = [...func];
            newData[index].y = value;
            setFunction(newData);
        } catch (error) {
            console.error('Ошибка при обновлении значения y:', error);
            setError('Не удалось обновить значение Y.');
        }
    };

    // Функция для выполнения операции
    const handleOperation = async (operation) => {
        setError('');
        if (function1.length !== function2.length) {
            setError('Размеры функций не совпадают.');
            return;
        }

        try {
            const response = await axios.post('/api/operands/calculate', null, {
                params: { operation: operation }
            });

            const resultResponse = await axios.get(`/api/operands/get`, {
                params: { id: 'result' }
            });

            const { x, y } = resultResponse.data;
            const result = x.map((xValue, index) => ({
                x: xValue,
                y: y[index]
            }));
            setResultFunction(result);
        } catch (error) {
            console.error('Ошибка при выполнении операции:', error);
            setError('Не удалось выполнить операцию.');
        }
    };

    // Загрузка данных для функций 1 и 2 при открытии модального окна
    useEffect(() => {
        if (isOpen) {
            fetchFunctionData('op1', setFunction1);
            fetchFunctionData('op2', setFunction2);
        }
    }, [isOpen]);

    const handleOutsideClick = (e) => {
        if (e.target.id === 'modal-overlay') {
            onClose();
        }
    };

    const handleCreateFunction = () => {
        setIsCreateFunctionModalOpen(true);
    };

    const handleSaveFunction = async (func, name) => {
        try {
            const serializedFunction = JSON.stringify(func);
            const functionsIO = new FunctionsIO();
            await functionsIO.saveToFile(name, serializedFunction);
            alert('Функция сохранена успешно!');
        } catch (error) {
            console.error('Ошибка при сохранении функции:', error);
            setError('Не удалось сохранить функцию.');
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
                className="bg-white p-6 rounded-lg shadow-md w-full max-w-4xl max-h-[700px] scroll-container"
                onClick={(e) => e.stopPropagation()}
            >
                <h2 className="text-xl font-bold mb-4 text-primary-content">Операции над функциями</h2>
                {error && <p className="text-red-500 mb-4">{error}</p>}

                <div className="grid grid-cols-3 gap-6">
                    {[function1, function2, resultFunction].map((tableData, idx) => (
                        <div key={idx}>
                            <h3 className="text-lg font-bold text-base-content mb-2">
                                {idx === 0
                                    ? 'Функция 1'
                                    : idx === 1
                                        ? 'Функция 2'
                                        : 'Результат'}
                            </h3>
                            <table className="table w-full mb-4">
                                <thead>
                                <tr>
                                    <th>X</th>
                                    <th>Y</th>
                                </tr>
                                </thead>
                                <tbody>
                                {tableData.map((row, i) => (
                                    <tr key={i}>
                                        <td>
                                            <input
                                                value={row.x}
                                                className="input input-bordered text-base-content w-full"
                                                disabled
                                            />
                                        </td>
                                        <td>
                                            <input
                                                value={row.y}
                                                onChange={(e) => {
                                                    if (idx === 0) {
                                                        const newValue = Number(e.target.value);
                                                        handleYChange('op1', i, newValue, setFunction1, function1);
                                                    } else if (idx === 1) {
                                                        const newValue = Number(e.target.value);
                                                        handleYChange('op2', i, newValue, setFunction2, function2);
                                                    }
                                                }}
                                                className="input input-bordered text-base-content w-full"
                                                disabled={idx === 2}
                                            />
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>

                            {idx !== 2 && (
                                <div className="flex-col gap-2 p-2">
                                    <button
                                        onClick={() => fetchFunctionData(idx === 0 ? 'op1' : 'op2', idx === 0 ? setFunction1 : setFunction2)}
                                        className="btn btn-primary m-2"
                                    >
                                        Загрузить
                                    </button>
                                    <button
                                        onClick={() => handleCreateFunction()}
                                        className="btn btn-success m-2"
                                    >
                                        Создать
                                    </button>
                                    <button
                                        onClick={() => handleSaveFunction(idx === 0 ? function1 : function2, `Функция_${idx + 1}`)}
                                        className="btn btn-secondary m-2"
                                    >
                                        Сохранить
                                    </button>
                                </div>
                            )}
                        </div>
                    ))}
                </div>

                <div className="flex gap-4 justify-center mt-4">
                    <button onClick={() => handleOperation('add')} className="btn btn-primary">
                        Сложение
                    </button>
                    <button onClick={() => handleOperation('sub')} className="btn btn-warning">
                        Вычитание
                    </button>
                    <button onClick={() => handleOperation('mul')} className="btn btn-secondary">
                        Умножение
                    </button>
                    <button onClick={() => handleOperation('div')} className="btn btn-accent">
                        Деление
                    </button>
                </div>

                <div className="flex justify-end mt-6">
                    <button onClick={onClose} className="btn btn-error">
                        Закрыть
                    </button>
                </div>
            </div>

            {/* Модальное окно для создания функции */}
            <CreateFunctionModal
                isOpen={isCreateFunctionModalOpen}
                onClose={() => setIsCreateFunctionModalOpen(false)}
                onCreate={(data) => {
                    console.log('Созданная функция:', data);
                    // Закрытие модального окна
                    setIsCreateFunctionModalOpen(false);

                    // Обновление данных для op1 и op2
                    // Запрашиваем данные для обеих функций после создания
                    fetchFunctionData('op1', setFunction1);
                    fetchFunctionData('op2', setFunction2);
                }}
            />

        </div>
    );
}

export default FunctionOperationsModal;
