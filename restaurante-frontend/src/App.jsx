import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import AuthService from './services/AuthService';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import UserManagementPage from './pages/UserManagementPage';

// Componente para proteger rutas (debe estar logueado)
const ProtectedRoute = ({ children }) => {
    const isAuthenticated = AuthService.isAuthenticated();
    if (!isAuthenticated) {
        return <Navigate to="/" replace />;
    }
    return children;
};

// Componente para proteger rutas (debe ser ADMIN)
const AdminRoute = ({ children }) => {
    const isAuthenticated = AuthService.isAuthenticated();
    const user = AuthService.getUser();

    if (!isAuthenticated || user.rol !== 'ROLE_ADMIN') {
        return <Navigate to="/dashboard" replace />;
    }
    return children;
};


function App() {
    return (
        <BrowserRouter>
            <Routes>
                {/* --- RUTAS PÚBLICAS --- */}
                <Route path="/" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />

                {/* --- RUTAS PROTEGIDAS --- */}
                <Route 
                    path="/dashboard" 
                    element={<ProtectedRoute><DashboardPage /></ProtectedRoute>} 
                />
                
                {/* --- RUTAS DE ADMIN --- */}
                <Route 
                    path="/admin/users" 
                    element={
                        <AdminRoute>
                            {/* Centramos la página de gestión */}
                            <div className="flex justify-center items-center min-h-screen bg-gray-100 p-4">
                                <UserManagementPage />
                            </div>
                        </AdminRoute>
                    }
                />
                
                {/* Aquí añadirías las otras rutas del proyecto:
                <Route path="/clientes" element={<ProtectedRoute><ClientesPage /></ProtectedRoute>} />
                <Route path="/menu" element={<ProtectedRoute><MenuPage /></ProtectedRoute>} /> 
                */}

            </Routes>
        </BrowserRouter>
    );
}

export default App;