import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import AdminService from '../services/AdminService';

const UserManagementPage = () => {
    // Estado para el formulario
    const [nombreUsuario, setNombreUsuario] = useState('');
    const [contrasena, setContrasena] = useState('');
    const [nombreRol, setNombreRol] = useState('ROLE_MOZO');
    const [message, setMessage] = useState(null);
    const [error, setError] = useState(null);

    // Estado para la lista de usuarios
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchUsers = async () => {
        try {
            const response = await AdminService.getUsers();
            setUsers(response.data);
        } catch (err) {
            console.error("Error cargando usuarios:", err);
            setError("No se pudieron cargar los usuarios.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage(null);
        setError(null);

        try {
            await AdminService.createUser(nombreUsuario, contrasena, nombreRol);
            setMessage(`¡Usuario ${nombreUsuario} creado exitosamente!`);
            setNombreUsuario('');
            setContrasena('');
            fetchUsers();
        } catch (err) {
            console.error(err);
            setError('Error al crear el usuario. Verifique los datos.');
        }
    };

    return (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 w-full max-w-5xl">
            
            <div className="w-full p-8 space-y-6 bg-white rounded-lg shadow-md">
                <Link 
                    to="/dashboard"
                    className="inline-block px-4 py-2 mb-4 text-sm font-medium text-white bg-gray-500 rounded-md hover:bg-gray-600"
                >
                    &larr; Volver al Dashboard
                </Link>
                <h2 className="text-2xl font-bold text-center text-gray-900">
                    Crear Nuevo Usuario
                </h2>
                <form className="space-y-6" onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="username" className="block text-sm font-medium text-gray-700">Nombre de Usuario</label>
                        <input id="username" type="text" value={nombreUsuario} onChange={(e) => setNombreUsuario(e.target.value)} required className="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md shadow-sm" />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700">Contraseña</label>
                        <input id="password" type="password" value={contrasena} onChange={(e) => setContrasena(e.target.value)} required className="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md shadow-sm" />
                    </div>
                    <div>
                        <label htmlFor="role" className="block text-sm font-medium text-gray-700">Rol del Usuario</label>
                        <select id="role" value={nombreRol} onChange={(e) => setNombreRol(e.target.value)} className="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md shadow-sm">
                            <option value="ROLE_MOZO">Mozo</option>
                            <option value="ROLE_CAJERO">Cajero</option>
                            <option value="ROLE_COCINERO">Cocinero</option>
                            <option value="ROLE_ADMIN">Administrador</option>
                        </select>
                    </div>
                    {message && <p className="text-sm text-center text-green-600">{message}</p>}
                    {error && <p className="text-sm text-center text-red-600">{error}</p>}
                    <button type="submit" className="w-full px-4 py-2 font-medium text-white bg-indigo-600 rounded-md hover:bg-indigo-700">
                        Crear Usuario
                    </button>
                </form>
            </div>

            <div className="w-full p-8 bg-white rounded-lg shadow-md">
                <h2 className="text-2xl font-bold text-center text-gray-900">
                    Usuarios del Sistema
                </h2>
                <div className="mt-6 flow-root">
                    {loading ? (
                        <p className="text-center text-gray-500">Cargando usuarios...</p>
                    ) : users.length === 0 ? (
                        <p className="text-center text-gray-500">Aún no hay usuarios creados.</p>
                    ) : (
                        <ul className="divide-y divide-gray-200">
                            {users.map((user) => (
                                <li key={user.idUsuario} className="flex justify-between items-center py-4">
                                    <div>
                                        <p className="text-lg font-medium text-indigo-600">{user.nombreUsuario}</p>
                                        <p className="text-sm text-gray-500">{user.rol}</p>
                                    </div>
                                    <div>
                                        <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                                            user.estado === 'ACTIVO' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                                        }`}>
                                            {user.estado}
                                        </span>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
        </div>
    );
};

export default UserManagementPage;