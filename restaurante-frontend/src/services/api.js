import axios from 'axios';
import AuthService from './AuthService';

// 1. Crea una instancia de Axios con la URL base de tu API
const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api', // La base de todos tus endpoints
});

// 2. Crea un "interceptor"
// Esto se ejecutará ANTES de cada petición que hagas con 'apiClient'
apiClient.interceptors.request.use(
    (config) => {
        const token = AuthService.getToken(); // Obtiene el token guardado
        if (token) {
            // Si el token existe, lo añade a la cabecera Authorization
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        // Maneja el error de la petición
        return Promise.reject(error);
    }
);

export default apiClient;