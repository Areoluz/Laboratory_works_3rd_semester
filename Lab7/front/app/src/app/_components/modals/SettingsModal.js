import React, { useState } from 'react';
import './SettingsModal.css';

function SettingsModal({ isOpen, onClose }) {
    const [factoryType, setFactoryType] = useState('array');

    const handleSave = () => {
        // Сохранить выбранную фабрику в глобальное состояние
        onClose();
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
                onClick={(e) => e.stopPropagation()} // Останавливаем всплытие клика внутри модального окна
            >
                <h2 className="text-xl font-bold mb-4 text-primary-content">Настройки фабрики</h2>
                <div className="mb-3 flex items-center">
                    <input
                        type="radio"
                        id="array"
                        name="factoryType"
                        value="array"
                        checked={factoryType === 'array'}
                        onChange={() => setFactoryType('array')}
                        className="custom-radio mr-3"
                    />
                    <label className="text-md text-base-content" htmlFor="array">Фабрика на основе массива</label>
                </div>
                <div className="mb-5 flex items-center">
                    <input
                        type="radio"
                        id="linkedList"
                        name="factoryType"
                        value="linkedList"
                        checked={factoryType === 'linkedList'}
                        onChange={() => setFactoryType('linkedList')}
                        className="custom-radio mr-3"
                    />
                    <label className="text-md text-base-content" htmlFor="linkedList">Фабрика на основе связного списка</label>
                </div>
                <div className="flex gap-4">
                    <button className="btn btn-primary" onClick={handleSave}>Сохранить изменения</button>
                    <button className="btn btn-error" onClick={onClose}>Закрыть</button>
                </div>
            </div>
        </div>
    );
}

export default SettingsModal;
