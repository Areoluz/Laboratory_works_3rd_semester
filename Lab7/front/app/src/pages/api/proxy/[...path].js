import axios from 'axios';
import https from 'https';

// Create a custom HTTPS agent that allows self-signed certificates
const httpsAgent = new https.Agent({
    rejectUnauthorized: false // This is not recommended for production use
});

export default async function handler(req, res) {
    const { path } = req.query;
    const targetUrl = `${process.env.NEXT_PUBLIC_API_BASE_URL}/${path.join('/')}`;

    try {
        const response = await axios({
            method: req.method,
            url: targetUrl,
            data: req.body,
            headers: {
                ...req.headers,
                host: new URL(process.env.NEXT_PUBLIC_API_BASE_URL).host,
            },
            httpsAgent, // Use the custom HTTPS agent
        });

        res.status(response.status).json(response.data);
    } catch (error) {
        console.error('Proxy error:', error);
        res.status(error.response?.status || 500).json(error.response?.data || { message: 'Internal server error' });
    }
}
