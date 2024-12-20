import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Main() {
    const [functions, setFunctions] = useState([]); // Данные функций
    const [loading, setLoading] = useState(false); // Индикатор загрузки
    const [error, setError] = useState(null); // Состояние ошибки
    const [modalData, setModalData] = useState(null); // Данные для модального окна
    const [isModalOpen, setIsModalOpen] = useState(false); // Стейт для открытия/закрытия модального окна

    // Загрузка данных с API через прокси
    const fetchFunctions = async () => {
        setLoading(true);
        setError(null);

        try {
            const response = await axios.get('/api/math', {
                baseURL: process.env.NEXT_PUBLIC_API_BASE_URL, // Базовый URL через прокси
            });

            console.log(response.data);
            setFunctions(groupByHash(response.data));
        } catch (err) {
            console.error('Ошибка при загрузке данных:', err);
            setError(err.response?.data?.message || 'Ошибка при загрузке данных'); // Показываем сообщение об ошибке
        } finally {
            setLoading(false); // Снимаем флаг загрузки
        }
    };

    // Группировка данных по hash
    const groupByHash = (data) => {
        const grouped = {};

        data.forEach((item) => {
            const { hash, x, y } = item;
            if (!grouped[hash]) {
                grouped[hash] = {
                    hash,
                    points: [], // Массив пар x-y
                };
            }
            grouped[hash].points.push({ x, y });
        });

        return Object.values(grouped);
    };

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

    // Загружаем данные при монтировании
    useEffect(() => {
        fetchFunctions();
    }, []);

    if (loading) return <div>Загрузка...</div>;
    if (error) return <div>Ошибка: {error}</div>;

    if (!functions.length) {
        return <div>Нет данных для отображения</div>;
    }

    // Открытие модального окна
    const openModal = (func) => {
        setModalData(func);
        setIsModalOpen(true);
    };

<<<<<<< Updated upstream
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
                                <div className="w-1/4 font-semibold">Количество точек</div>
                                <div className="w-1/2 font-semibold">Действия</div>
                            </div>
                            <div>
                                {functions.map((func, index) => (
                                    <div key={func.hash}>
                                        <div className="flex w-full mb-2 text-base-content">
                                            <div className="w-1/4">{func.hash}</div>
                                            <div className="w-1/4">{func.points.length}</div>
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
=======
                {/* Таблица */}
                <table className="table w-full text-base-content">
                    <thead>
                    <tr>
                        <th>Hash</th>
                        <th>Количество точек</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    {functions.map((func) => (
                        <tr key={func.hash}>
                            <td>{func.hash}</td>
                            <td>{func.points.length}</td>
                            <td>
                                <button
                                    onClick={() => alert(`Просмотр функции с hash: ${func.hash}`)}
                                    className="btn btn-primary mr-2"
                                >
                                    Просмотреть
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
>>>>>>> Stashed changes
            </div>
    );
}

export default Main;
