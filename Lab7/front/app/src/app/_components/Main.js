import React, { useState } from 'react';
import axios from 'axios';
import {useFunctions} from "@/app/_components/FunctionsContext";

function Main() {
    const { functions, setFunctions } = useFunctions();  // Данные функций
    //const [loading, setLoading] = useState(false); // Индикатор загрузки
    //const [error, setError] = useState(null); // Состояние ошибки
    const [modalData, setModalData] = useState(null); // Данные для модального окна
    const [isModalOpen, setIsModalOpen] = useState(false); // Стейт для открытия/закрытия модального окна


    // Удаление функции
    const deleteFunction = async (hash) => {
        try {
            await axios.delete(`/api/math/${hash}`, {
                baseURL: process.env.NEXT_PUBLIC_API_BASE_URL, // Базовый URL через прокси
            });

            // Обновляем список функций после удаления
            setFunctions((prevFunctions) =>
                prevFunctions.filter((func) => func.hash !== hash)
            );

        } catch (err) {
            console.error('Ошибка при удалении функции:', err);
            alert(err.response?.data?.message || 'Ошибка при удалении функции.');
        }
    };

    if (!functions.length) {
        return <div>Нет данных для отображения</div>;
    }

    // Открытие модального окна
    const openModal = (func) => {
        setModalData(func);
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

    return (
        <div>
            <h1 className='text-xl md:text-2xl font-bold mb-4 text-base-content'>
                Список табулированных функций
            </h1>
            <div className="flex-1 overflow-hidden flex flex-col">
                <div className='flex flex-col gap-6'>
                    <div className="bg-base-100 dark:bg-base-700 p-6 rounded-lg shadow-md">
                        <h1 className="text-md mb-8 text-base-content">
                            Для создания функции нажмите + на панели сбоку
                        </h1>

                        {/* Контейнер для "таблицы" */}
                        <div className="overflow-x-auto">
                            <div className="flex w-full mb-2 text-base-content">
                                <div className="w-1/4 font-semibold">Hash</div>

                                <div className="w-1/2 font-semibold">Действия</div>
                            </div>
                            <div>
                                {functions.map((func, index) => (
                                    <div key={func.hash}>
                                        <div className="flex w-full mb-2 text-base-content">
                                            <div className="w-1/4">{func.hash}</div>

                                            {/* Количество пар x-y */}
                                            <div className="w-1/2">
                                                <button
                                                    onClick={() => openModal(func)}
                                                    className="btn btn-primary mr-2"
                                                >
                                                    Просмотреть
                                                </button>
                                                <button
                                                    onClick={() => deleteFunction(func.hash)}
                                                    className="btn btn-secondary"
                                                >
                                                    Удалить
                                                </button>
                                            </div>
                                        </div>
                                        {/* Добавляем разделительную полоску, кроме последней функции */}
                                        {index !== functions.length - 1 && <div className="border-t my-2"></div>}
                                    </div>
                                ))}
                            </div>
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
                            <h2 className="text-xl font-bold mb-4 text-primary-content">Данные функции</h2>
                            <div className="text-base-content">
                                {modalData.points.map((point, idx) => (
                                    <p key={idx}>
                                        <strong>Точка {idx + 1}:</strong> x = {point.x}, y = {point.y}
                                    </p>
                                ))}
                            </div>
                            <div className="mt-4 flex justify-end">
                                <button onClick={closeModal} className="btn btn-secondary">Закрыть</button>
                            </div>
                        </div>
                    </div>
                )}
            </div>
    );
}

export default Main;
