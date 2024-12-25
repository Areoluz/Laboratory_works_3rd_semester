import './globals.css';
import { Inter } from 'next/font/google';
import React from 'react';
import Layout from './_components/Layout'; // Assuming the Layout component is in this file
import { FunctionsProvider } from '@/app/_components/FunctionsContext'; // Импортируем FunctionsProvider

const inter = Inter({ subsets: ['latin'] });

export const metadata = {
    title: 'Калькулятор математических функций',
    description: 'uni project',
};

export default function RootLayout({
                                       children,
                                   }: {
    children: React.ReactNode;
}) {
    return (
        <html lang="en">
        <body className={inter.className}>
        {/* Оборачиваем все в FunctionsProvider для доступности контекста */}
        <FunctionsProvider>
            <Layout>
                {children}
            </Layout>
        </FunctionsProvider>
        </body>
        </html>
    );
}
