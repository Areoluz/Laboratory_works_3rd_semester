import React, { useState, useEffect } from 'react';

function Main() {
    const [functions, setFunctions] = useState([]); // Данные функций
    const [loading, setLoading] = useState(false); // Индикатор загрузки
    const [error, setError] = useState(null); // Состояние ошибки

    // Загрузка данных с API
    const fetchFunctions = async () => {
        setLoading(true);
        setError(null);

        try {
            const response = await fetch('http://localhost:8080/api/math'); // Ссылка на ваш API
            if (!response.ok) {
                throw new Error('Ошибка при загрузке данных');
            }

            const data = await response.json();

            // Группируем данные по hash
            setFunctions(groupByHash(data));
        } catch (err) {
            setError(err.message); // Ловим и записываем ошибку
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
                    points: [], // массива пар x-y
                };
            }
            grouped[hash].points.push({ x, y });
        });

        return Object.values(grouped);
    };

    // Загружаем данные при монтировании
    useEffect(() => {
        fetchFunctions();
    }, []);

    if (loading) return <div>Загрузка...</div>; // Во время загрузки выводим сообщение
    if (error) return <div>Ошибка: {error}</div>; // В случае ошибки

    if (!functions.length) {
        return <div>Нет данных для отображения</div>; // Нет данных
    }

    return (
        <div className="flex-1 flex flex-col overflow-hidden">
            <div className="flex-1 overflow-auto p-4">
                <h1 className="text-xl font-semibold mb-4 text-base-content">
                    Список табулированных функций
                </h1>
                <h1 className="text-md mb-8 text-base-content">
                    Для создания функции нажмите + на панели сбоку
                </h1>

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
                            <td>{func.points.length}</td> {/* Количество пар x-y */}
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
            </div>
        </div>
    );
}

export default Main;