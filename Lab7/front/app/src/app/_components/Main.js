import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Main() {
    const [modalData, setModalData] = useState(null); // Данные для модального окна
    const [isModalOpen, setIsModalOpen] = useState(false); // Стейт для открытия/закрытия модального окна
    const [operands, setOperands] = useState([]); // Список операндов

    // Открытие модального окна
    const openModal = async (operandId) => {
        try {
            // Отправляем запрос для получения всех операндов
            const token = localStorage.getItem('authToken');
            const response = await axios.get('/api/operands/getAll', {
                headers: {
                    'Authorization': token ? `Bearer ${token}` : '', // Добавляем токен в заголовок
                },
            });

            // Преобразуем объект в массив
            const transformedOperands = Object.entries(response.data).map(([key, value]) => ({
                id: key,
                x: value.x,
                y: value.y,
            }));

            setOperands(transformedOperands);

            // Находим операнд по id и открываем модальное окно с его данными
            const operand = transformedOperands.find((op) => op.id === operandId);
            setModalData(operand);
            setIsModalOpen(true);

        } catch (error) {
            console.error('Ошибка при загрузке операндов:', error);
            alert('Не удалось загрузить список операндов. Попробуйте позже.');
        }
    };

    // Закрытие модального окна
    const closeModal = () => {
        setIsModalOpen(false);
        setModalData(null);
    };

    const handleOutsideClick = (e) => {
        if (e.target.id === 'modal-overlay') {
            closeModal();
        }
    };

    useEffect(() => {
        // Initial loading of operands on component mount
        const fetchOperands = async () => {
            try {
                const token = localStorage.getItem('authToken');

                const response = await axios.get('/api/operands/getAll', {
                    headers: {
                        'Authorization': token ? `Bearer ${token}` : '', // Добавляем токен в заголовок
                    },
                });

                console.log(response.data);

                // Преобразуем объект в массив
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

        fetchOperands();
    }, []);

    return (
        <div>
            <h1 className="text-xl md:text-2xl font-bold mb-4 text-base-content">
                Список операндов
            </h1>
            <div className="flex-1 overflow-hidden flex flex-col">
                <div className="flex flex-col gap-6">
                    <div className="bg-base-100 dark:bg-base-700 p-6 rounded-lg shadow-md">
                        <h1 className="text-md font-bold mb-4 text-base-content">
                            Приложение предназначено для работы с табулированными функциями. Оно предоставляет возможность выполнять следующие операции:</h1>

                        <h1 className="text-md text-base-content">• Выполнение арифметических операций между функциями.</h1>

                        <h1 className="text-md text-base-content">• Добавление функции в качестве операнда </h1>

                        <h1 className="text-md text-base-content">• Дифференцирование функции. </h1>

                        <h1 className="text-md text-base-content">• Управление значениями точек
                            функции. </h1>

                        <h1 className="text-md mb-4 text-base-content">• Экспорт и импорт функций
                            (сериализация/десериализация). </h1>

                        <h1 className="text-md mb-10 font-bold text-base-content">Для взаимодействия с
                            приложением, рекмоендуется использовать всплывающие над иконками подсказки. </h1>

                        {/* Таблица операндов */}
                        <div className="overflow-x-auto">
                            <div className="flex w-full mb-2 text-base-content">
                                <div className="w-1/4 font-semibold">ID</div>
                                <div className="w-1/4 font-semibold">Количество точек</div>
                                <div className="w-1/2 font-semibold">Действия</div>
                            </div>

                            {/* Рендер списка операндов */}
                            {operands.map((operand) => (
                                <div key={operand.id} className="flex w-full mb-2 text-base-content">
                                    <div className="w-1/4">
                                        {operand.id}
                                        {operand.id === 'op1' && (
                                            <button className="ml-2 btn btn-primary btn-sm">главный</button>
                                        )}
                                    </div>
                                    <div className="w-1/4">{operand.x.length}</div>
                                    <div className="w-1/2">
                                        <button
                                            onClick={() => openModal(operand.id)} // Обновлено: передаем id операнда
                                            className="btn btn-primary btn-sm"
                                        >
                                            Просмотреть точки
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>

            {/* Модальное окно */}
            {isModalOpen && modalData && (
                <div
                    id="modal-overlay"
                    className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
                    onClick={handleOutsideClick}
                >
                    <div
                        className="bg-white p-6 rounded-lg shadow-md w-full max-w-lg"
                        onClick={(e) => e.stopPropagation()} // Останавливаем всплытие клика внутри модального окна
                    >
                        <h2 className="text-xl font-bold mb-4 text-primary-content">
                            Данные операнда
                        </h2>
                        <div className="text-base-content">
                            {modalData.x.map((x, idx) => (
                                <p key={idx}>
                                    <strong>Точка {idx + 1}:</strong> x = {x}, y = {modalData.y[idx]}
                                </p>
                            ))}
                        </div>
                        <div className="mt-4 flex justify-end">
                            <button onClick={closeModal} className="btn btn-secondary">
                                Закрыть
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Main;
