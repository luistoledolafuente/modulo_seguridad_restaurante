import React, { useState } from 'react';
import AuthService from '../services/AuthService';
import { useNavigate, Link } from 'react-router-dom';

const RegisterPage = () => {
    const [nombreUsuario, setNombreUsuario] = useState('');
    const [contrasena, setContrasena] = useState('');
    const [error, setError] = useState(null);
    const [message, setMessage] = useState(null);
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        setError(null);
        setMessage(null);

        try {
            // Llama al servicio público de registro
            const response = await AuthService.registerAdmin(nombreUsuario, contrasena);
            
            setMessage(response.data + ". Serás redirigido al login.");
            
            setTimeout(() => {
                navigate('/');
            }, 3000);

        } catch (err) {
            console.error("Error en el registro:", err);
            if (err.response && err.response.data) {
                setError(err.response.data);
            } else {
                setError('No se pudo completar el registro.');
            }
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-md">
                <h2 className="text-2xl font-bold text-center text-gray-900">
                    Crear Cuenta de Administrador
                </h2>
                <p className="text-sm text-center text-gray-600">
                    Esta acción solo se puede realizar si no existe ningún administrador en el sistema.
                </p>
                
                <form className="space-y-6" onSubmit={handleRegister}>
                    <div>
                        <label htmlFor="username" className="block text-sm font-medium text-gray-700">Usuario</label>
                        <input id="username" type="text" value={nombreUsuario} onChange={(e) => setNombreUsuario(e.target.value)} required className="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md" />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700">Contraseña</label>
                        <input id="password" type="password" value={contrasena} onChange={(e) => setContrasena(e.target.value)} required className="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md" />
                    </div>

                    {error && <p className="text-sm text-center text-red-600">{error}</p>}
                    {message && <p className="text-sm text-center text-green-600">{message}</p>}

                    <button type="submit" className="w-full px-4 py-2 font-medium text-white bg-indigo-600 rounded-md hover:bg-indigo-700">
                        Crear Cuenta
                    </button>
                    
                    <div className="text-sm text-center">
                        <Link to="/" className="font-medium text-indigo-600 hover:text-indigo-500">
                            Volver a Iniciar Sesión
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default RegisterPage;