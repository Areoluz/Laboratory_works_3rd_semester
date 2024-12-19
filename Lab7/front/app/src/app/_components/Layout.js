'use client';

import React from 'react';
import Header from './Header';

function Layout({ children }) {

    return (
        <div className="flex flex-col bg-base-300 dark:bg-base-900 h-svh">
            <Header />
                <main className="flex-1 p-4 overflow-y-auto flex flex-col md:ml-[5%] w-full max-w-[1400px] mx-auto">
                    {children}
                </main>
        </div>
    );
}

export default Layout;