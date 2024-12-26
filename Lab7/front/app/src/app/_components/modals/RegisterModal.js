'use client';

import React, { useState } from 'react';
import axios from 'axios';

function RegisterModal({ isOpen, onClose }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleRegister = async (e) => {
        e.preventDefault();

        setLoading(true);
        setError(null);
        setSuccess(null);

        try {
            const data = { username, password };

            const response = await axios.post('/api/register', data, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            setSuccess('Регистрация прошла успешно! Теперь вы можете войти.');
            console.log(response.data);

            // Сбрасываем поля формы

            setPassword('');

            // Сообщаем родительскому компоненту, что регистрация завершена
            if (onClose) {
onClose(); // Убираем передачу имени пользователя
            }
        } catch (err) {
            console.error('Ошибка регистрации:', err);
            setError(err.response?.data?.message || 'Ошибка регистрации. Попробуйте снова.');
        } finally {
            setLoading(false);
        }
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="w-full max-w-md bg-white rounded-lg shadow-md p-6">
                <h2 className="text-2xl font-bold mb-6 text-gray-700">Регистрация</h2>

                {error && (
                    <div className="bg-red-100 text-red-600 p-3 rounded mb-4">
                        {error}
                    </div>
                )}

                {success && (
                    <div className="bg-green-100 text-green-600 p-3 rounded mb-4">
                        {success}
                    </div>
                )}

                <form onSubmit={handleRegister}>
                    <div className="mb-4">
                        <label className="block text-gray-600 text-sm mb-2" htmlFor="register-username">
                            Логин
                        </label>
                        <input
                            id="register-username"
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            className="input input-bordered w-full"
                            placeholder="Введите имя пользователя"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-600 text-sm mb-2" htmlFor="register-password">
                            Пароль
                        </label>
                        <input
                            id="register-password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="input input-bordered w-full"
                            placeholder="Введите пароль"
                            required
                        />
                    </div>

                    <button
                        type="submit"
                        className={`btn btn-primary w-full ${loading ? 'loading' : ''}`}
                        disabled={loading}
                    >
                        {loading ? 'Регистрируем...' : 'Зарегистрироваться'}
                    </button>
                </form>

                <button
                    onClick={onClose}
                    className="btn btn-error w-full mt-4"
                >
                    Отмена
                </button>
            </div>
        </div>
    );
}

export default RegisterModal;