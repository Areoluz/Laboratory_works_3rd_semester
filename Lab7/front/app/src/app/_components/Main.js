import React, { useState } from 'react';

// Пример списка функций
const functionsList = [
    {
        id: 1,
        name: 'Function 1',
        type: 'Табулированная',
        source: 'Массив',
    },
    {
        id: 2,
        name: 'Function 2',
        type: 'Сложная',
        source: 'Другая функция',
    },
    {
        id: 3,
        name: 'Function 3',
        type: 'Табулированная',
        source: 'Массив',
    },
];

function Main() {
    const [functions, setFunctions] = useState(functionsList);

    const handleView = (id) => {
        // Логика для открытия модального окна для изучения функции
        alert(`Просмотр функции с ID: ${id}`);
    };

    const handleDelete = (id) => {
        // Удаление функции из списка
        setFunctions(functions.filter((func) => func.id !== id));
    };

    return (
        <div className="flex-1 flex flex-col overflow-hidden">
            <div className="flex-1 overflow-auto p-4">
                <h1 className="text-xl font-semibold mb-4 text-base-content">Список созданных функций</h1>
                <h1 className="text-md mb-8 text-base-content">Для создания функции нажмите + на панели сбоку</h1>

                {/* Таблица */}
                <table className="table table-zebra w-full text-base-content">
                    <thead>
                    <tr>
                        <th>Имя функции</th>
                        <th>Тип</th>
                        <th>Источник</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    {functions.map((func) => (
                        <tr key={func.id}>
                            <td>{func.name}</td>
                            <td>{func.type}</td>
                            <td>{func.source}</td>
                            <td>
                                <button
                                    onClick={() => handleView(func.id)}
                                    className="btn mr-2"
                                >
                                    Просмотреть
                                </button>
                                <button
                                    onClick={() => handleDelete(func.id)}
                                    className="btn"
                                >
                                    Удалить
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
