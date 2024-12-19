import './globals.css'
import { Inter } from 'next/font/google'
import React from "react";
import Layout from './_components/Layout'; // Assuming the Layout component is in this file

const inter = Inter({ subsets: ['latin'] })

export const metadata = {
    title: 'Калькуляор математических функций',
    description: 'uni project',
}

export default function RootLayout({
                                       children,
                                   }: {
    children: React.ReactNode
}) {
    return (
        <html lang="en">
        <body className={inter.className}>
            <Layout>
                {children}
            </Layout>
        </body>
        </html>
    )
}
