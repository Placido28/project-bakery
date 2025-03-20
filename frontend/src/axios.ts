import axios from "axios";
import { getAccessToken, getRefreshToken, saveToken, logout } from "@/service/authService";

const api = axios.create({
    baseURL: "http://localhost:8081/api",
    headers: {
        "Content-Type": "application/json",
    },
});

// Interceptor para incluir el token en cada petición
api.interceptors.request.use(config => {
    const token = getAccessToken();
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// Interceptor para manejar expiración del token y refrescarlo
api.interceptors.response.use(
    response => response,
    async error => {
        if (error.response?.status === 401) {
            const refreshToken = getRefreshToken();
            if (refreshToken) {
                try {
                    const response = await axios.post("http://localhost:8081/api/auth/refresh-token", {
                        refreshToken,
                    });

                    const { accessToken, refreshToken: newRefreshToken } = response.data;
                    saveToken(accessToken, newRefreshToken);
                    
                    error.config.headers.Authorization = `Bearer ${accessToken}`;
                    return api(error.config); // Reintentar la petición original
                } catch (refreshError) {
                    logout();
                    window.location.href = "/login"; // Redirigir a login si falla
                }
            } else {
                logout();
                window.location.href = "/login";
            }
        }
        return Promise.reject(error);
    }
);

export default api;