'use client';

import React, { useState } from 'react';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import RegisterModal from './modals/RegisterModal'; // Импорт модального окна

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [isRegisterModalOpen, setIsRegisterModalOpen] = useState(false);
    const router = useRouter();

    const handleSubmit = async (e) => {
        e.preventDefault();

        setLoading(true);
        setError(null);

        try {
            const formData = new FormData();
            formData.append('username', username);
            formData.append('password', password);

            const response = await axios.post('/api/login', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            console.log(response.data);

            router.push('/main'); // Перенаправление на страницу /main после успешного входа
        } catch (err) {
            console.error('Ошибка при входе:', err);
            setError(err.response?.data?.message || 'Ошибка при входе. Попробуйте снова.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div className="w-full max-w-md bg-white rounded-lg shadow-md p-6">
                <h2 className="text-2xl font-bold mb-6 text-gray-700">Вход</h2>
                {error && (
                    <div className="bg-red-100 text-red-600 p-3 rounded mb-4">
                        {error}
                    </div>
                )}
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-gray-600 text-sm mb-2" htmlFor="username">
                            Имя пользователя
                        </label>
                        <input
                            id="username"
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            className="input input-bordered w-full"
                            placeholder="Введите имя пользователя"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-600 text-sm mb-2" htmlFor="password">
                            Пароль
                        </label>
                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="input input-bordered w-full"
                            placeholder="Введите пароль"
                            required
                        />
                    </div>
                    <div className="flex flex-col gap-4">
                        <button
                            type="submit"
                            className={`btn btn-primary w-full ${loading ? 'loading' : ''}`}
                            disabled={loading}
                        >
                            {loading ? 'Вход...' : 'Войти'}
                        </button>
                        <button
                            type="button"
                            onClick={() => setIsRegisterModalOpen(true)}
                            className="btn btn-secondary w-full"
                        >
                            У меня нет аккаунта
                        </button>
                    </div>
                </form>
            </div>

            <RegisterModal
                isOpen={isRegisterModalOpen}
                onClose={(username) => {
                    setIsRegisterModalOpen(false);
                    if (username) {
                        setRegisterUsername(username);
                        setUsername(username); // Автозаполнение поля логина
                        alert('Пользователь зарегистрирован. Теперь войдите.');
                    }
                }}
            />
        </div>
    );
}

export default LoginPage;