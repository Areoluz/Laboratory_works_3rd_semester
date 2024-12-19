'use client';

import React, { useState } from 'react';

function FunctionOperationsModal({ isOpen, onClose }) {
    const [function1, setFunction1] = useState([]);
    const [function2, setFunction2] = useState([]);
    const [resultFunction, setResultFunction] = useState([]);
    const [error, setError] = useState('');

    const handleCreateFunction = (setFunction) => {
        const newFunction = Array.from({ length: 5 }, (_, i) => ({ x: i, y: 0 }));
        setFunction(newFunction);
    };

    const handleLoadFromFile = (setFunction) => {
        alert('Функция загрузки из файла еще не реализована.');
    };

    const handleSaveToFile = (data) => {
        alert('Функция сохранения в файл еще не реализована.');
    };

    const handleOperation = (operation) => {
        setError('');
        if (function1.length !== function2.length) {
            setError('Размеры функций не совпадают.');
            return;
        }

        const result = function1.map((row, index) => {
            const x1 = row.x;
            const y1 = row.y;
            const x2 = function2[index].x;
            const y2 = function2[index].y;

            if (x1 !== x2) {
                setError('X-координаты не совпадают.');
                return null;
            }

            let yResult;
            switch (operation) {
                case 'add':
                    yResult = y1 + y2;
                    break;
                case 'subtract':
                    yResult = y1 - y2;
                    break;
                case 'multiply':
                    yResult = y1 * y2;
                    break;
                case 'divide':
                    if (y2 === 0) {
                        setError('Деление на ноль.');
                        return null;
                    }
                    yResult = y1 / y2;
                    break;
                default:
                    yResult = 0;
            }
            return { x: x1, y: yResult };
        });

        if (result.includes(null)) return;
        setResultFunction(result);
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
                className="bg-white p-6 rounded-lg shadow-md w-full max-w-4xl max-h-[700px] scroll-container"
                onClick={(e) => e.stopPropagation()} // Останавливаем всплытие клика внутри модального окна
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
                                                onChange={(e) => {
                                                    if (idx === 2) return; // Результат недоступен для редактирования
                                                    const newData = [...tableData];
                                                    newData[i].x = Number(e.target.value);
                                                    idx === 0 ? setFunction1(newData) : setFunction2(newData);
                                                }}
                                                className="input input-bordered text-base-content w-full"
                                                disabled={idx === 2}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                value={row.y}
                                                onChange={(e) => {
                                                    if (idx === 2) return; // Результат недоступен для редактирования
                                                    const newData = [...tableData];
                                                    newData[i].y = Number(e.target.value);
                                                    idx === 0 ? setFunction1(newData) : setFunction2(newData);
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
                                        onClick={() => handleCreateFunction(idx === 0 ? setFunction1 : setFunction2)}
                                        className="btn btn-primary m-2"
                                    >
                                        Создать
                                    </button>
                                    <button
                                        onClick={() => handleLoadFromFile(idx === 0 ? setFunction1 : setFunction2)}
                                        className="btn btn-secondary m-2"
                                    >
                                        Загрузить
                                    </button>
                                    <button
                                        onClick={() => handleSaveToFile(idx === 0 ? function1 : function2)}
                                        className="btn btn-accent m-2"
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
                    <button onClick={() => handleOperation('subtract')} className="btn btn-warning">
                        Вычитание
                    </button>
                    <button onClick={() => handleOperation('multiply')} className="btn btn-secondary">
                        Умножение
                    </button>
                    <button onClick={() => handleOperation('divide')} className="btn btn-accent">
                        Деление
                    </button>
                </div>

                <div className="flex justify-end mt-6">
                    <button onClick={onClose} className="btn btn-error">
                        Закрыть
                    </button>
                </div>
            </div>
        </div>
    );
}

export default FunctionOperationsModal;
