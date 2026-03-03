import axios from "axios";

const API_URL = axios.create({
    baseURL: 'http://localhost:8080/',
    headers: {
        'Content-Type': 'application/json'
    }
})

export const loginRequest = async (email: string, senha: string) => {
  try {
    const response = await API_URL.post(`/auth/login`, { email, senha });
    return response.data;
  } catch (error: any) {
    if (error.response && error.response.status === 401) {
      throw new Error("Credenciais inválidas");
    }
    throw new Error("Erro no login");
  }
};

