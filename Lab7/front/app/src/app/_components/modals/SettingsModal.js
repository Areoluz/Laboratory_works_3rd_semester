import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './SettingsModal.css';

function SettingsModal({ isOpen, onClose }) {
    const [factoryType, setFactoryType] = useState('array');
    const [factories, setFactories] = useState([]);

    useEffect(() => {
        const fetchFactories = async () => {
            try {
                const response = await axios.get('/factory/all', {
                    baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
                });
                // Создаем массив className для каждого объекта
                const classNames = response.data.map((factory) => factory.className);

                setFactories(classNames); // Сохраняем только className в состоянии
            } catch (error) {
                console.error('Ошибка при загрузке списка:', error);
                alert('Не удалось загрузить список. Попробуйте позже.');
            }
        };

        if (isOpen) {
            fetchFactories();
        }
    }, [isOpen]); // Эффект зависит от isOpen

    const handleSave = async () => {
        try {
            const response = await axios.post('/factory', { factoryType }, {
                baseURL: process.env.NEXT_PUBLIC_API_BASE_URL, // Базовый URL через прокси
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            console.log(response.data);
            onClose(); // Закрыть модальное окно после успешного сохранения
        } catch (error) {
            console.error('Ошибка:', error);
            if (error.response) {
                console.error('Ответ от сервера:', error.response.data);
            }
            alert('Ошибка. Попробуйте снова.');
        }
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
                className="bg-white p-6 rounded-lg shadow-md w-full max-w-4xl"
                onClick={(e) => e.stopPropagation()} // Prevent click from closing the modal if inside
            >
                <h2 className="text-xl font-bold mb-4 text-primary-content">Настройки фабрики</h2>
                {factories.length > 0 ? (
                    factories.map((factory) => (
                        <div key={factory} className="mb-3 flex items-center">
                            <input
                                type="radio"
                                id={factory}
                                name="factoryType"
                                value={factory}
                                checked={factoryType === factory}
                                onChange={() => setFactoryType(factory)}
                                className="custom-radio mr-3"
                            />
                            <label className="text-md text-base-content" htmlFor={factory}>
                                {`Фабрика на основе ${factory}`}
                            </label>
                        </div>
                    ))
                ) : (
                    <div className="text-center text-md">Загрузка фабрик...</div>
                )}
                <div className="flex gap-4 mt-5">
                    <button className="btn btn-primary" onClick={handleSave}>Сохранить изменения</button>
                    <button className="btn btn-error" onClick={onClose}>Закрыть</button>
                </div>
            </div>
        </div>
    );
}

export default SettingsModal;
