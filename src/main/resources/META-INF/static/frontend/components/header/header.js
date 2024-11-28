function goToLogin() {
    window.location.href = '/login';
}

function goToRegister() {
    window.location.href = '/register';
}

const loginBtn = document.getElementById('login-btn');
const registerBtn = document.getElementById('register-btn');

loginBtn.addEventListener('click', goToLogin);
registerBtn.addEventListener('click', goToRegister);

function logout() {
    localStorage.removeItem('authToken');
    window.location.href = '/login';
}

function isUserLoggedIn() {
    const authToken = localStorage.getItem('authToken');
    return authToken !== null;
}

if (isUserLoggedIn()) {
    const logoutBtn = document.createElement('button');
    logoutBtn.textContent = 'Logout';
    logoutBtn.classList.add('btn');
    logoutBtn.addEventListener('click', logout);
    document.querySelector('.user-actions').appendChild(logoutBtn);
}