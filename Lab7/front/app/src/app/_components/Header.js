'use client';

import React, { useState, useEffect } from 'react';
import { FaPalette, FaPlus, FaPlusCircle } from 'react-icons/fa';
import { CgMathPercent } from "react-icons/cg";
import { TbMathIntegral } from "react-icons/tb";
import { IoSettingsSharp } from "react-icons/io5";
import { MdAutoGraph } from "react-icons/md";
import { TiPlus } from "react-icons/ti";
import { TbDelta } from "react-icons/tb";
import './Header.css';

import CreateFunctionModal from './modals/CreateFunctionModal';
import IntegralModal from './modals/IntegralModal';
import ComplexFunctionModal from "@/app/_components/modals/ComplexFunctionModal";
import FunctionOperationsModal from "@/app/_components/modals/FunctionOperationsModal";
import SettingsModal from "@/app/_components/modals/SettingsModal";
import GraphModal from "@/app/_components/modals/GraphModal";
import DeriveModal from "@/app/_components/modals/DeriveModal";

function Header() {
    const [currentTheme, setCurrentTheme] = useState('winter');
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [openModal, setOpenModal] = useState(null);

    useEffect(() => {
        const savedTheme = localStorage.getItem('theme') || 'winter';
        setCurrentTheme(savedTheme);
    }, []);

    useEffect(() => {
        document.documentElement.setAttribute('data-theme', currentTheme);
        localStorage.setItem('theme', currentTheme);
    }, [currentTheme]);

    const themes = [
        "valentine",
        "synthwave",
        "forest",
        "winter",
        "lemonade",
        "dracula",
        "cmyk",
        "autumn",
        "pastel"
    ];

    return (
        <div>
            {/* Sidebar */}
            <div className={`fixed inset-0 bg-base-100 z-40 border-r border-base-300 dark:border-base-700 transition-transform duration-300 ease-in-out ${isSidebarOpen ? 'translate-x-0' : '-translate-x-full'} lg:translate-x-0 lg:w-18 lg:inset-auto lg:top-0 lg:left-0 lg:bottom-0`}>
                <div className="flex flex-col h-full">
                    <ul className="menu menu-compact flex-grow p-4 gap-2 items-center justify-center">
                        <li>
                            <a
                                onClick={() => {
                                    setOpenModal('createFunction');
                                    setIsSidebarOpen(false);
                                }}
                                className="flex items-center p-2 rounded-lg hover:bg-base-300 dark:hover:bg-base-700"
                            >
                                <div className="tooltip tooltip-right" data-tip="Создание функции">
                                    <TiPlus size={25} className="text-base-content"/>
                                </div>
                                <span className="ml-3 lg:hidden text-base-content">Создание функции</span>
                            </a>
                        </li>
                        <li>
                            <a
                                onClick={() => {
                                    setOpenModal('createComplexFunction');
                                    setIsSidebarOpen(false);
                                }}
                                className="flex items-center p-2 rounded-lg hover:bg-base-300 dark:hover:bg-base-700"
                            >
                                <div className="tooltip tooltip-right" data-tip="Создание сложной функции">
                                    <FaPlusCircle size={25} className="text-base-content"/>
                                </div>
                                <span className="ml-3 lg:hidden text-base-content">Создание сложной функции</span>
                            </a>
                        </li>
                        <li>
                            <a
                                onClick={() => {
                                    setOpenModal('operations');
                                    setIsSidebarOpen(false);
                                }}
                                className="flex items-center p-2 rounded-lg hover:bg-base-300 dark:hover:bg-base-700"
                            >
                                <div className="tooltip tooltip-right" data-tip="Операции">
                                    <CgMathPercent size={25} className="text-base-content"/>
                                </div>
                                <span className="ml-3 lg:hidden text-base-content">Операции</span>
                            </a>
                        </li>
                        <li>
                            <a
                                onClick={() => {
                                    setOpenModal('integral');
                                    setIsSidebarOpen(false);
                                }}
                                className="flex items-center p-2 rounded-lg hover:bg-base-300 dark:hover:bg-base-700"
                            >
                                <div className="tooltip tooltip-right" data-tip="Интеграл">
                                    <TbMathIntegral size={25} className="text-base-content"/>
                                </div>
                                <span className="ml-3 lg:hidden text-base-content">Интеграл</span>
                            </a>
                        </li>
                        <li>
                            <a
                                onClick={() => {
                                    setOpenModal('derive');
                                    setIsSidebarOpen(false);
                                }}
                                className="flex items-center p-2 rounded-lg hover:bg-base-300 dark:hover:bg-base-700"
                            >
                                <div className="tooltip tooltip-right" data-tip="Дифференциал">
                                    <TbDelta size={25} className="text-base-content"/>
                                </div>
                                <span className="ml-3 lg:hidden text-base-content">Дифференциал</span>
                            </a>
                        </li>
                        <li>
                            <a
                                onClick={() => {
                                    setOpenModal('settings');
                                    setIsSidebarOpen(false);
                                }}
                                className="flex items-center p-2 rounded-lg hover:bg-base-300 dark:hover:bg-base-700"
                            >
                                <div className="tooltip tooltip-right" data-tip="Настройки">
                                    <IoSettingsSharp size={25} className="text-base-content"/>
                                </div>
                                <span className="ml-3 lg:hidden text-base-content">Настройки</span>
                            </a>
                        </li>
                        <li>
                            <a
                                onClick={() => {
                                    setOpenModal('graph');
                                    setIsSidebarOpen(false);
                                }}
                                className="flex items-center p-2 rounded-lg hover:bg-base-300 dark:hover:bg-base-700"
                            >
                                <div className="tooltip tooltip-right" data-tip="Графики">
                                    <MdAutoGraph size={25} className="text-base-content"/>
                                </div>
                                <span className="ml-3 lg:hidden text-base-content">Графики</span>
                            </a>
                        </li>
                        <li>
                            <a
                                onClick={() => {
                                    document.getElementById('theme_modal').showModal();
                                    setIsSidebarOpen(false);
                                }}
                                className="flex items-center p-2 rounded-lg hover:bg-base-300 dark:hover:bg-base-700"
                            >
                                <div className="tooltip tooltip-right" data-tip="Выбор темы">
                                    <FaPalette size={25} className="text-base-content"/>
                                </div>
                                <span className="ml-3 lg:hidden text-base-content">Выбор темы</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            {/* Theme Modal */}
            <dialog id="theme_modal" className="modal">
                <div className="modal-box bg-base-100 dark:bg-base-800 text-base-content dark:text-base-content">
                    <h3 className="font-bold text-lg mb-4">Выберите тему</h3>
                    <div className="grid grid-cols-2 md:grid-cols-3 gap-2">
                        {themes.map((theme) => (
                            <button
                                key={theme}
                                className={`btn ${currentTheme === theme ? 'btn-primary' : 'btn-outline'}`}
                                onClick={() => {
                                    setCurrentTheme(theme);
                                    document.getElementById('theme_modal').close();
                                }}
                            >
                                {theme}
                            </button>
                        ))}
                    </div>
                    <div className="modal-action">
                        <form method="dialog">
                            <button className="btn">Закрыть</button>
                        </form>
                    </div>
                </div>
            </dialog>

            {openModal === 'createFunction' && (
                <CreateFunctionModal
                    isOpen={openModal === 'createFunction'}
                    onClose={() => setOpenModal(null)}
                    onCreate={(data) => {
                        console.log('Созданная функция:', data);
                        setOpenModal(null);
                    }}
                />
            )}
            {openModal === 'createComplexFunction' && (
                <ComplexFunctionModal
                    isOpen={openModal === 'createComplexFunction'}
                    onClose={() => setOpenModal(null)}
                    onCreate={(data) => {
                        console.log('Созданная функция:', data);
                        setOpenModal(null);
                    }}
                    availableFunctions={[
                        { id: 1, name: 'Квадрат (SqrFunction)', type: 'simple' },
                        { id: 2, name: 'Синус (SinFunction)', type: 'simple' },
                        { id: 3, name: 'Косинус (CosFunction)', type: 'simple' },
                        { id: 4, name: 'Сложная функция 1', type: 'complex' },
                        { id: 5, name: 'Сложная функция 2', type: 'complex' },
                    ]} // Список доступных функций
                />
            )}
            {openModal === 'operations' && (
                <FunctionOperationsModal
                    isOpen={openModal === 'operations'}
                    onClose={() => setOpenModal(null)}
                />
            )}
            {openModal === 'integral' && (
                <IntegralModal
                    isOpen={openModal === 'integral'}
                    onClose={() => setOpenModal(null)}
                />
            )}
            {openModal === 'settings' && (
                <SettingsModal
                    isOpen={openModal === 'settings'}
                    onClose={() => setOpenModal(null)}
                />
            )}
            {openModal === 'graph' && (
                <GraphModal
                    isOpen={openModal === 'graph'}
                    onClose={() => setOpenModal(null)}
                />
            )}
            {openModal === 'derive' && (
                <DeriveModal
                    isOpen={openModal === 'derive'}
                    onClose={() => setOpenModal(null)}
                />
            )}

        </div>
    );
}

export default Header;
