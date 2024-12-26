import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CreateFunctionModal from './CreateFunctionModal'; // Импортируем компонент для создания функции

function FunctionOperationsModal({ isOpen, onClose }) {
    const [function1, setFunction1] = useState([]);
    const [function2, setFunction2] = useState([]);
    const [resultFunction, setResultFunction] = useState([]);
    const [error, setError] = useState('');
    const [isCreateFunctionModalOpen, setIsCreateFunctionModalOpen] = useState(false);
    const [isSaveModalOpen, setIsSaveModalOpen] = useState(false);
    const [saveTarget, setSaveTarget] = useState({ id: '', func: null });

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
                params: { operation: operation, op1: 'op1', op2: 'op2', result: 'result' }
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

    const opts = {
        types: [
            {
                description: 'Tabulated Function',
                accept: { 'application/x-binary': ['.bin'] },
            },
            {
                description: 'Tabulated Function',
                accept: { 'application/xml': ['.xml'] },
            },
        ],
        excludeAcceptAllOption: true,
    }

    async function onSave(id) {
        try {
            const handle = await window.showSaveFilePicker(opts)

            let response
            if (handle.name.endsWith('.xml')) {
                response = await axios.get(`/tabulated/operands/${id}/xml`, {
                    responseType: 'blob',
                })
            } else {
                response = await axios.get(`/tabulated/operands/${id}/serialized`, {
                    responseType: 'blob',
                })
            }

            const writableStream = await handle.createWritable()
            await writableStream.write(response.data)
            await writableStream.close()
        } catch (e) {
            if (e.name !== 'AbortError') throw e
        }
    }

    async function onUpload(id) {
        try {
            const [handle] = await window.showOpenFilePicker(opts)
            const fileData = await handle.getFile()

            let response
            if (handle.name.endsWith('.xml')) {
                response = await axios.post(`/api/operands/xml`, await fileData.text(), {
                    headers: {
                        'Content-Type': 'application/xml',
                    },
                })
            } else {
                response = await axios.post(
                    `/api/operands/binary`,
                    await fileData.arrayBuffer(),
                    {
                        headers: {
                            'Content-Type': 'application/octet-stream',
                        },
                    }
                )
            }

            if (id === 'op1') {
                setFunction1(response.data);
            } else if (id === 'op2') {
                setFunction2(response.data);
            }
        } catch (e) {
            if (e.name !== 'AbortError') throw e
        }
    }

    const handleCreateFunction = () => {
        setIsCreateFunctionModalOpen(true);
    };

    const handleSaveFunction = (id, func) => {
        setSaveTarget({ id, func });
        setIsSaveModalOpen(true);
    };

    const handleSaveToXml = async () => {
        try {
            await axios.get(`/api/operands/xml`, {
                params: { id: saveTarget.id }
            });
            alert('Функция успешно сохранена в формате XML.');
        } catch (error) {
            console.error('Ошибка при сохранении в XML:', error);
            setError('Не удалось сохранить функцию в XML.');
        } finally {
            setIsSaveModalOpen(false);
        }
    };

    const handleSaveToBinary = async () => {
        try {
            await axios.get(`/api/operands/binary`, {
                params: { id: saveTarget.id }
            });
            alert('Функция успешно сериализована.');
        } catch (error) {
            console.error('Ошибка при сериализации:', error);
            setError('Не удалось сериализовать функцию.');
        } finally {
            setIsSaveModalOpen(false);
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
                                {idx === 0 ? 'Функция 1' : idx === 1 ? 'Функция 2' : 'Результат'}
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
                                        onClick={() => handleSaveFunction(idx === 0 ? 'op1' : 'op2', idx === 0 ? function1 : function2)}
                                        className="btn btn-secondary m-2"
                                    >
                                        Сохранить
                                    </button>
                                    <button
                                        onClick={() => onUpload(idx === 0 ? 'op1' : 'op2')}
                                        className="btn btn-info m-2"
                                    >
                                        Загрузить из файла
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
                    setIsCreateFunctionModalOpen(false);
                    fetchFunctionData('op1', setFunction1);
                    fetchFunctionData('op2', setFunction2);
                }}
            />

            {/* Модальное окно для выбора способа сохранения */}
            {isSaveModalOpen && (
                <div
                    className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
                    onClick={() => setIsSaveModalOpen(false)}
                >
                    <div
                        className="bg-white p-6 rounded-lg shadow-md w-full max-w-sm"
                        onClick={(e) => e.stopPropagation()}
                    >
                        <h3 className="text-lg font-bold mb-4 text-base-content">Выберите способ сохранения</h3>
                        <div className="flex flex-col gap-4">
                            <button onClick={handleSaveToXml} className="btn btn-primary">
                                Сохранить в XML
                            </button>
                            <button onClick={handleSaveToBinary} className="btn btn-secondary">
                                Сериализация
                            </button>
                            <button onClick={() => setIsSaveModalOpen(false)} className="btn btn-error">
                                Отмена
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default FunctionOperationsModal;
