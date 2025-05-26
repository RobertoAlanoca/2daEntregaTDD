// Funciones comunes para todas las páginas

// Manejo del tema oscuro
function initTheme() {
    const savedTheme = localStorage.getItem('theme') || 'light';
    applyTheme(savedTheme);
    
    const themeToggle = document.querySelector('.theme-toggle');
    if (themeToggle) {
        themeToggle.addEventListener('click', toggleTheme);
    }
}

function toggleTheme() {
    const currentTheme = document.body.classList.contains('dark-theme') ? 'dark' : 'light';
    const newTheme = currentTheme === 'light' ? 'dark' : 'light';
    applyTheme(newTheme);
    localStorage.setItem('theme', newTheme);
}

function applyTheme(theme) {
    const themeToggle = document.querySelector('.theme-toggle');
    if (theme === 'dark') {
        document.body.classList.add('dark-theme');
        if (themeToggle) themeToggle.textContent = '☀️ Tema Claro';
    } else {
        document.body.classList.remove('dark-theme');
        if (themeToggle) themeToggle.textContent = '🌙 Tema Oscuro';
    }
}

// Manejo de configuraciones
function saveConfig(key, value) {
    localStorage.setItem(key, JSON.stringify(value));
}

function loadConfig(key, defaultValue) {
    const saved = localStorage.getItem(key);
    return saved ? JSON.parse(saved) : defaultValue;
}

// Configuraciones por defecto
const defaultConfig = {
    player1Symbol: 'X',
    player2Symbol: 'O',
    player1Color: '#007bff',
    player2Color: '#dc3545',
    boardColor: '#ffffff',
    backgroundColor: '#f4f4f4'
};

// Aplicar configuraciones visuales
function applyVisualConfig() {
    const config = {
        player1Color: loadConfig('player1Color', defaultConfig.player1Color),
        player2Color: loadConfig('player2Color', defaultConfig.player2Color),
        boardColor: loadConfig('boardColor', defaultConfig.boardColor),
        backgroundColor: loadConfig('backgroundColor', defaultConfig.backgroundColor)
    };

    // Aplicar colores al CSS
    const style = document.createElement('style');
    style.textContent = `
        .cell.player1 { color: ${config.player1Color} !important; }
        .cell.player2 { color: ${config.player2Color} !important; }
        .cell { background-color: ${config.boardColor} !important; }
        body:not(.dark-theme) { background-color: ${config.backgroundColor} !important; }
    `;
    document.head.appendChild(style);
}

// Manejo de historial de partidas
function saveGameResult(winner, moves) {
    const history = loadConfig('gameHistory', []);
    const gameResult = {
        date: new Date().toLocaleString(),
        winner: winner,
        moves: moves,
        id: Date.now()
    };
    
    history.unshift(gameResult);
    
    // Mantener solo los últimos 10 juegos
    if (history.length > 10) {
        history.splice(10);
    }
    
    saveConfig('gameHistory', history);
}

function clearGameHistory() {
    localStorage.removeItem('gameHistory');
}

function getGameHistory() {
    return loadConfig('gameHistory', []);
}

// Colores disponibles para selección
const availableColors = [
    '#007bff', // Azul
    '#dc3545', // Rojo
    '#28a745', // Verde
    '#ffc107', // Amarillo
    '#6f42c1', // Púrpura
    '#fd7e14', // Naranja
    '#20c997', // Turquesa
    '#e83e8c', // Rosa
    '#6c757d', // Gris
    '#343a40'  // Negro
];

// Símbolos disponibles
const availableSymbols = [
    { symbol: 'X', name: 'X' },
    { symbol: 'O', name: 'O' },
    { symbol: '🔴', name: 'Círculo Rojo' },
    { symbol: '🔵', name: 'Círculo Azul' },
    { symbol: '⭐', name: 'Estrella' },
    { symbol: '❤️', name: 'Corazón' },
    { symbol: '🎯', name: 'Diana' },
    { symbol: '⚡', name: 'Rayo' }
];

// Funciones de autenticación
function checkAuthentication() {
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    const currentPath = window.location.pathname;
    
    // Páginas que no requieren autenticación
    const publicPages = ['/', '/register'];
    
    // Si no está autenticado y no está en una página pública, redirigir
    if (!isLoggedIn && !publicPages.includes(currentPath)) {
        window.location.href = '/';
        return false;
    }
    
    // Si está autenticado y está en la página de login o registro, redirigir al menú
    if (isLoggedIn && (currentPath === '/' || currentPath === '/register')) {
        window.location.href = '/menu';
        return false;
    }
    
    return isLoggedIn;
}

function logout() {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('loginTime');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentUserFullName');
    window.location.href = '/';
}

function addLogoutButton() {
    // Solo agregar botón de logout si está autenticado y no es una página pública
    const publicPages = ['/', '/register'];
    if (localStorage.getItem('isLoggedIn') === 'true' && !publicPages.includes(window.location.pathname)) {
        const navbar = document.querySelector('.navbar .nav-buttons');
        if (navbar && !document.getElementById('logout-btn')) {
            const currentUser = localStorage.getItem('currentUserFullName') || localStorage.getItem('currentUser') || 'Usuario';
            
            // Agregar saludo al usuario
            const userGreeting = document.createElement('span');
            userGreeting.id = 'user-greeting';
            userGreeting.innerHTML = `👋 Hola, ${currentUser}`;
            userGreeting.style.marginRight = '1rem';
            userGreeting.style.color = '#666';
            userGreeting.style.fontSize = '0.9em';
            
            const logoutBtn = document.createElement('button');
            logoutBtn.id = 'logout-btn';
            logoutBtn.className = 'btn btn-danger';
            logoutBtn.innerHTML = '🚪 Cerrar Sesión';
            logoutBtn.style.marginLeft = 'auto';
            logoutBtn.onclick = logout;
            
            navbar.appendChild(userGreeting);
            navbar.appendChild(logoutBtn);
        }
    }
}

// Inicializar cuando la página carga
document.addEventListener('DOMContentLoaded', function() {
    checkAuthentication();
    initTheme();
    applyVisualConfig();
    addLogoutButton();
});
