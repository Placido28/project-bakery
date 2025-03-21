import axios from "axios";
import { getAccessToken, getRefreshToken, saveToken, logout } from "@/service/authService";

const api = axios.create({
    baseURL: process.env.VUE_APP_API_URL || "http://localhost:8081/api",
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

                    if (!response.data.accessToken) {
                        throw new Error("No se recibió un nuevo token");
                    }

                    const { accessToken, refreshToken: newRefreshToken } = response.data;
                    saveToken(accessToken, newRefreshToken);
                    
                    error.config.headers.Authorization = `Bearer ${accessToken}`;
                    return api(error.config); // Reintentar la petición original
                } catch (refreshError) {
                    console.error("Refresh token inválido. Cerrando sesión...");
                    logoutAndRedirect();
                }
            } else {
                console.warn("No hay refresh token. Cerrando sesión...");
                logoutAndRedirect();
            }
        }
        return Promise.reject(error);
    }
);

// Función para cerrar sesión y redirigir correctamente
function logoutAndRedirect() {
    logout(); // Elimina tokens del localStorage
    window.location.href = "/signin"; // Redirige al login
}

export default api;