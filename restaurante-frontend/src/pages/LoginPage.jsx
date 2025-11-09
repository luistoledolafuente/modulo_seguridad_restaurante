import React, { useState } from 'react';
import AuthService from '../services/AuthService';
import { useNavigate, Link } from 'react-router-dom';

const LoginPage = () => {
    const [nombreUsuario, setNombreUsuario] = useState('');
    const [contrasena, setContrasena] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const response = await AuthService.login(nombreUsuario, contrasena);
            if (response.data.token) {
                AuthService.saveAuthData(response.data);
                navigate('/dashboard'); 
                window.location.reload(); // Recarga para asegurar estado
            }
        } catch (err) {
            console.error("Error en el login:", err);
            setError('Credenciales incorrectas. Por favor, inténtalo de nuevo.');
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-md">
                <h2 className="text-2xl font-bold text-center text-gray-900">
                    Sabor Gourmet - Iniciar Sesión
                </h2>
                
                <form className="space-y-6" onSubmit={handleLogin}>
                    <div>
                        <label 
                            htmlFor="username" 
                            className="block text-sm font-medium text-gray-700">
                            Usuario
                        </label>
                        <input
                            id="username"
                            type="text"
                            value={nombreUsuario}
                            onChange={(e) => setNombreUsuario(e.target.value)}
                            required
                            className="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                        />
                    </div>

                    <div>
                        <label 
                            htmlFor="password" 
                            className="block text-sm font-medium text-gray-700">
                            Contraseña
                        </label>
                        <input
                            id="password"
                            type="password"
                            value={contrasena}
                            onChange={(e) => setContrasena(e.target.value)}
                            required
                            className="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                        />
                    </div>

                    {error && (
                        <p className="text-sm text-center text-red-600">
                            {error}
                        </p>
                    )}

                    <div>
                        <button 
                            type="submit" 
                            className="w-full px-4 py-2 font-medium text-white bg-indigo-600 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Entrar
                        </button>
                    </div>
                </form>

                <div className="text-sm text-center text-gray-600">
                    ¿Primera vez aquí? 
                    <Link to="/register" className="font-medium text-indigo-600 hover:text-indigo-500 ml-1">
                        Crear cuenta de Administrador
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;