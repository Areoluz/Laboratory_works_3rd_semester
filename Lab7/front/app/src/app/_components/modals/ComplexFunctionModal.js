import React, { useState } from 'react';

function ComplexFunctionModal({ isOpen, onClose, onCreate, availableFunctions }) {
    const [functionName, setFunctionName] = useState('');
    const [functionTree, setFunctionTree] = useState([]); // Array of nodes representing the tree structure

    const handleAddOperand = () => {
        const newNode = { id: Date.now(), type: 'operand', name: '', children: [] };
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
                            className="select select-bordered w-full max-w-xs"
                        >
                            <option value="" disabled>Выберите функцию</option>
                            {availableFunctions.map((func) => (
                                <option key={func} value={func}>{func}</option>
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

    const handleCreateComplexFunction = () => {
        if (!functionName.trim() || functionTree.length === 0) {
            alert('Введите название функции и добавьте операнды.');
            return;
        }

        onCreate({ name: functionName, tree: functionTree });
        onClose();
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
