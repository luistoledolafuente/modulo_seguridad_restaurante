import apiClient from './api';

const AdminService = {
    /**
     * Llama al endpoint /api/admin/create-user
     */
    createUser: (nombreUsuario, contrasena, nombreRol) => {
        return apiClient.post('/admin/create-user', {
            nombreUsuario,
            contrasena,
            nombreRol
        });
    },

    /**
     * Llama al endpoint /api/admin/users
     */
    getUsers: () => {
        return apiClient.get('/admin/users');
    }
};

export default AdminService;