"use client"

import React, { createContext, useState, useContext, useEffect } from 'react';
import axios from "axios";

// Создаем контекст для хранения functions
const FunctionsContext = createContext();

// Провайдер для предоставления доступа к функциям и их изменениям
export const FunctionsProvider = ({ children }) => {
    const [functions, setFunctions] = useState([]);

    // Вы можете использовать эффект, чтобы обновить список функций при изменении состояния
    useEffect(() => {
        // Логика для загрузки данных функций, например, из API
        const fetchFunctions = async () => {
            try {
                const response = await axios.get('/api/math', {
                    baseURL: process.env.NEXT_PUBLIC_API_BASE_URL, // Базовый URL через прокси
                });

                console.log(response.data);
                setFunctions(groupByHash(response.data));
            } catch (err) {
                console.error('Ошибка при загрузке данных:', err);
            }
        };

        fetchFunctions();
    }, []); // Эффект будет вызван один раз при монтировании компонента

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

    const addFunction = (newFunction) => {
        setFunctions((prevFunctions) => [...prevFunctions, newFunction]);
    };

    return (
        <FunctionsContext.Provider value={{ functions, setFunctions, addFunction }}>
            {children}
        </FunctionsContext.Provider>
    );
};

// Хук для использования функций и updateFunctions в компонентах
export const useFunctions = () => {
    const context = useContext(FunctionsContext);
    if (!context) {
        throw new Error('useFunctions must be used within a FunctionsProvider');
    }
    return context;
};
