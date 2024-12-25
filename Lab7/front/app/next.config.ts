/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
        return [
            {
                source: "/api/:path*",
                destination: "http://localhost:8080/:path*", // Это позволит правильно перенаправлять запросы
            },
        ];
    },
    skipTrailingSlashRedirect: true,
    output: "standalone",
};

export default nextConfig;
