import React from 'react';
import AuthService from '../services/AuthService';
import { useNavigate, Link, Navigate } from 'react-router-dom'; 

const DashboardPage = () => {
    const user = AuthService.getUser();
    const navigate = useNavigate();

    const handleLogout = () => {
        AuthService.logout();
        navigate('/');
    };

    if (!user) {
        return <Navigate to="/" />;
    }

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 text-center bg-white rounded-lg shadow-md">
                
                <h1 className="text-3xl font-bold text-gray-900">¡Bienvenido!</h1>
                <p className="text-lg text-gray-700">
                    Has iniciado sesión como 
                    <strong className="text-indigo-600"> {user.nombreUsuario}</strong>.
                </p>
                <div className="px-4 py-2 bg-gray-200 rounded-md">
                    <p className="text-sm font-medium text-gray-800">
                        Tu rol es: <span className="font-bold text-gray-900">{user.rol}</span>
                    </p>
                </div>

                {/* --- ENLACE SOLO PARA ADMIN --- */}
                {user.rol === 'ROLE_ADMIN' && (
                    <Link 
                        to="/admin/users" 
                        className="block w-full px-4 py-2 font-medium text-white bg-green-600 rounded-md hover:bg-green-700"
                    >
                        Gestionar Usuarios
                    </Link>
                )}
                
                {/* Aquí puedes añadir enlaces a Clientes, Mesas, etc. */}

                <button 
                    onClick={handleLogout}
                    className="w-full px-4 py-2 font-medium text-white bg-red-600 rounded-md hover:bg-red-700">
                    Cerrar Sesión
                </button>
            </div>
        </div>
    );
};

export default DashboardPage;