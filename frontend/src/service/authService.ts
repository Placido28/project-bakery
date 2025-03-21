import api from "@/axios";
import axios from "axios";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  user: {
    id: number;
    email: string;
    role: string;
  };
}

export async function login(credentials: LoginRequest): Promise<LoginResponse> {
  try {
    const response = await api.post('/auth/login', credentials);
    console.log("Respuesta completa:", response.data);

    const { accessToken, refreshToken } = response.data.data;

    if (!accessToken || !refreshToken) {
      throw new Error("No se recibió un token válido");
    }

    return {
      accessToken,
      refreshToken,
      user: { id: 0, email: "", role: "" } // 📌 Definir estructura en lugar de `any`
    };
  } catch (error: unknown) {
    if (axios.isAxiosError(error)) {
      console.error("Error en la API:", error.response?.data || error.message);
      throw new Error(error.response?.data?.message || "Error en la autenticación");
    }
    throw new Error("Error desconocido en la autenticación");
  }
}

export function saveToken(accessToken: string, refreshToken: string) {
  localStorage.setItem('accessToken', accessToken);
  localStorage.setItem('refreshToken', refreshToken);
  api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
}

export function getAccessToken(): string | null {
  return localStorage.getItem('accessToken');
}

export function getRefreshToken(): string | null {
  return localStorage.getItem('refreshToken');
}

export function logout() {
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
  delete api.defaults.headers.common['Authorization']; // Eliminar token en Axios
  window.location.href = "/signin"; // Redirigir al login
}