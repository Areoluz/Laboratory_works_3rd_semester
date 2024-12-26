import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Main() {
    const [modalData, setModalData] = useState(null); // Данные для модального окна
    const [isModalOpen, setIsModalOpen] = useState(false); // Стейт для открытия/закрытия модального окна
    const [operands, setOperands] = useState([]); // Список операндов

    // Сопоставление ID операндов с их понятными именами
    const operandNames = {
        op1: 'Функция 1',
        op2: 'Функция 2',
        op3: 'Функция 3',
        result: 'Результат',
    };

    // Функция для загрузки операндов
    const fetchOperands = async () => {
        try {
            const token = localStorage.getItem('authToken');
            const response = await axios.get('/api/operands/getAll', {
                headers: {
                    Authorization: token ? `Bearer ${token}` : '', // Добавляем токен в заголовок
                },
            });

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

    // Открытие модального окна
    const openModal = (operandId) => {
        // Находим операнд по id и открываем модальное окно с его данными
        const operand = operands.find((op) => op.id === operandId);
        setModalData(operand);
        setIsModalOpen(true);
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
        // Запускаем загрузку при монтировании компонента
        fetchOperands();

        // Устанавливаем интервал обновления каждые 5 секунд
        const intervalId = setInterval(fetchOperands, 5000);

        // Очищаем интервал при размонтировании компонента
        return () => clearInterval(intervalId);
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
                            Приложение предназначено для работы с табулированными функциями. Оно предоставляет возможность выполнять следующие операции:
                        </h1>
                        <ul className="list-disc pl-4 text-base-content mb-2">
                            <li>Выполнение арифметических операций между функциями.</li>
                            <li>Добавление функции в качестве операнда.</li>
                            <li>Дифференцирование функции.</li>
                            <li>Вычисление занчения функции в точке</li>
                            <li>Экспорт и импорт функций (сериализация/десериализация).</li>
                        </ul>
                        <h1 className="text-md mb-10 font-bold text-base-content">
                            Для взаимодействия с приложением, рекомендуется использовать всплывающие подсказки над иконками.
                        </h1>

                        {/* Таблица операндов */}
                        <div className="overflow-x-auto">
                            <div className="flex w-full mb-2 text-base-content">
                                <div className="w-1/4 font-semibold">Название</div>
                                <div className="w-1/4 font-semibold">Количество точек</div>
                                <div className="w-1/2 font-semibold">Действия</div>
                            </div>

                            {/* Рендер списка операндов */}
                            {operands.map((operand) => (
                                <div key={operand.id} className="flex w-full mb-2 text-base-content">
                                    <div className="w-1/4">
                                        {operandNames[operand.id] || operand.id} {/* Преобразуем ID в название */}
                                        {operand.id === 'op1' && (
                                            <button className="ml-2 btn btn-primary btn-sm">Основная</button>
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
                            {modalData.x.map((x, idx) => {
                                // Функция для округления, если после запятой больше трех знаков
                                const formatValue = (value) => {
                                    const strValue = value.toString();
                                    if (strValue.includes('.') && strValue.split('.')[1].length > 3) {
                                        return Number(value).toFixed(3);
                                    }
                                    return value;
                                };

                                return (
                                    <p key={idx}>
                                        <strong>Точка {idx + 1}:</strong> x = {formatValue(x)}, y = {formatValue(modalData.y[idx])}
                                    </p>
                                );
                            })}
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
