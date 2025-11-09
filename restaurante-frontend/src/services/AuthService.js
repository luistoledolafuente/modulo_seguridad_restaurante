import axios from 'axios';

// URL de autenticación pública
const AUTH_API_URL = 'http://localhost:8080/api/auth';

const AuthService = {

    /**
     * Llama al endpoint /login
     */
    login: (nombreUsuario, contrasena) => {
        return axios.post(AUTH_API_URL + '/login', {
            nombreUsuario,
            contrasena
        });
    },

    // --- ¡AQUÍ ESTÁ LA FUNCIÓN QUE FALTABA! ---
    /**
     * Llama al endpoint /register (para el primer admin)
     */
    registerAdmin: (nombreUsuario, contrasena) => {
        return axios.post(AUTH_API_URL + '/register', {
            nombreUsuario,
            contrasena
        });
    },
    // --- FIN DE LA CORRECCIÓN ---

    /**
     * Guarda el token y usuario en localStorage
     */
    saveAuthData: (responseData) => {
        localStorage.setItem('token', responseData.token);
        localStorage.setItem('user', JSON.stringify({
            nombreUsuario: responseData.nombreUsuario,
            rol: responseData.rol
        }));
    },

    /**
     * Cierra la sesión
     */
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },

    /**
     * Obtiene el token
     */
    getToken: () => {
        return localStorage.getItem('token');
    },

    /**
     * Obtiene los datos del usuario
     */
    getUser: () => {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    },

    /**
     * Verifica si está autenticado
     */
    isAuthenticated: () => {
        return localStorage.getItem('token') !== null;
    }
};

export default AuthService;