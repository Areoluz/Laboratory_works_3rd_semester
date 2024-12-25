import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ComplexFunctionModal({ isOpen, onClose, onCreate }) {
    const [functionName, setFunctionName] = useState('');
    const [functionTree, setFunctionTree] = useState([]); // Array of nodes representing the tree structure
    const [availableFunctions, setAvailableFunctions] = useState([]); // Список доступных функций

    useEffect(() => {
        // Загружаем операнды и функции при открытии модального окна
        if (isOpen) {
            fetchAvailableFunctions();
        }
    }, [isOpen]);

    const fetchAvailableFunctions = async () => {
        try {
            const response = await axios.get('/api/functions/simple/all', {
                headers: { 'Content-Type': 'application/json' },
            });

            if (response.data && Array.isArray(response.data)) {
                // Проверьте структуру ответа
                console.log('Available functions:', response.data);

                const functionsArray = response.data.map((func) => ({
                    name: func.name,
                    className: func.className, // Убедитесь, что className есть в ответе
                }));
                setAvailableFunctions(functionsArray);
            } else {
                console.error('Некорректный ответ от сервера:', response.data);
            }
        } catch (error) {
            console.error('Ошибка при загрузке функций:', error);
        }
    };

    const handleAddOperand = () => {
        const newNode = { id: Date.now(), type: 'operand', name: '', className: '', children: [] };
        setFunctionTree([...functionTree, newNode]);
    };

    const handleUpdateNode = (id, updatedNode) => {
        const updateTree = (nodes) =>
            nodes.map((node) =>
                node.id === id
                    ? { ...node, ...updatedNode }
                    : { ...node, children: updateTree(node.children) }
            );

        setFunctionTree(updateTree(functionTree));
    };

    const handleRemoveNode = (id) => {
        const filterTree = (nodes) =>
            nodes.filter((node) => node.id !== id).map((node) => ({
                ...node,
                children: filterTree(node.children),
            }));

        setFunctionTree(filterTree(functionTree));
    };

    const renderTree = (nodes) => (
        <ul>
            {nodes.map((node) => (
                <li key={node.id} className="mb-2">
                    <div className="flex items-center gap-2">
                        <select
                            value={node.name}
                            onChange={(e) => handleUpdateNode(node.id, { name: e.target.value })}
                            className="select select-bordered w-full max-w-xs text-base-content"
                        >
                            <option value="" disabled>Выберите функцию</option>
                            {availableFunctions.map((func) => (
                                <option key={`function-${func.name}`} value={func.name}>
                                    Функция: {func.name}
                                </option>
                            ))}
                        </select>
                        <button
                            onClick={() => handleRemoveNode(node.id)}
                            className="btn btn-error btn-sm"
                        >
                            Удалить
                        </button>
                    </div>
                    {node.children.length > 0 && renderTree(node.children)}
                </li>
            ))}
        </ul>
    );

    const handleCreateComplexFunction = async () => {
        if (!functionName.trim() || functionTree.length < 2) {
            alert('Введите название функции и добавьте хотя бы две функции.');
            return;
        }

        // Получаем названия первой и второй функции из дерева
        const [firstFunction, secondFunction] = functionTree;

        if (!firstFunction.name || !secondFunction.name) {
            alert('Выберите названия для всех функций.');
            return;
        }

        // Получаем className для первой и второй функции, если они есть в списке availableFunctions
        const firstFunc = availableFunctions.find((func) => func.name === firstFunction.name);
        const secondFunc = availableFunctions.find((func) => func.name === secondFunction.name);

        if (!firstFunc || !secondFunc) {
            alert('Не найдены выбранные функции в списке доступных функций.');
            return;
        }
        console.log(firstFunc.className);

        // Формируем тело запроса с использованием className
        const requestBody = {
            name: functionName,
            inner: firstFunc.className, // Используем className для первой функции
            outer: secondFunc.className, // Используем className для второй функции
        };
        console.log(requestBody);

        try {
            // Отправляем POST-запрос на API
            const response = await axios.post('/api/composite', requestBody, {
                headers: { 'Content-Type': 'application/json' },
            });

            if (response.status === 200) {
                alert('Функция успешно создана!');
                onCreate(requestBody); // Вызываем onCreate для обновления внешнего состояния
                onClose();
            } else {
                console.error('Ошибка при создании функции:', response);
                alert('Не удалось создать функцию. Проверьте данные.');
            }
        } catch (error) {
            console.error('Ошибка при отправке запроса на создание функции:', error);
            alert('Произошла ошибка при создании функции. Попробуйте снова.');
        }
    };

    if (!isOpen) return null;

    return (
        <div
            id="modal-overlay"
            className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
            onClick={(e) => e.target.id === 'modal-overlay' && onClose()}
        >
            <div
                className="bg-white p-6 rounded-lg shadow-md w-full max-w-3xl"
                onClick={(e) => e.stopPropagation()}
            >
                <h2 className="text-xl font-bold mb-4 text-primary-content">Создать сложную функцию</h2>

                <div className="mb-4">
                    <input
                        type="text"
                        placeholder="Название функции"
                        value={functionName}
                        onChange={(e) => setFunctionName(e.target.value)}
                        className="input input-bordered w-full text-base-content"
                    />
                </div>

                <div className="mb-4">
                    <h3 className="text-lg font-bold mb-2 text-primary-content">Древовидный редактор</h3>
                    {renderTree(functionTree)}
                </div>

                <div className="flex justify-between items-center mb-4">
                    <button onClick={handleAddOperand} className="btn btn-primary">
                        Добавить операнд
                    </button>
                </div>

                <div className="flex gap-4">
                    <button onClick={handleCreateComplexFunction} className="btn btn-secondary">
                        Создать функцию
                    </button>
                    <button onClick={onClose} className="btn btn-error">
                        Отмена
                    </button>
                </div>
            </div>
        </div>
    );
}

export default ComplexFunctionModal;
