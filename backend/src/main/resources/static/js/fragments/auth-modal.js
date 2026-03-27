function openModal() {
    const overlay = document.getElementById('loginOverlay');
    const modal = overlay.querySelector('.modal');
    overlay.classList.add('active');
    modal.style.animation = 'popIn 0.2s ease forwards';
}

function closeModal() {
    const overlay = document.getElementById('loginOverlay');
    const modal = overlay.querySelector('.modal');
    modal.style.animation = 'popOut 0.18s ease forwards';
    setTimeout(() => {
        overlay.classList.remove('active');
        modal.style.animation = '';
    }, 180);
}

function handleOverlay(e) {
    if (e.target === document.getElementById('loginOverlay')) closeModal();
}

function switchPanel(panel) {
    const panelLogin = document.getElementById('panel-login');
    const panelCadastro = document.getElementById('panel-cadastro');

    if (panel === 'cadastro') {
        panelLogin.style.animation = 'slideOutLeft 0.18s ease forwards';
        setTimeout(() => {
            panelLogin.style.display = 'none';
            panelLogin.style.animation = '';
            panelCadastro.style.display = 'block';
            panelCadastro.style.animation = 'slideInRight 0.18s ease forwards';
            setTimeout(() => { panelCadastro.style.animation = ''; }, 180);
        }, 180);
    } else {
        panelCadastro.style.animation = 'slideOutRight 0.18s ease forwards';
        setTimeout(() => {
            panelCadastro.style.display = 'none';
            panelCadastro.style.animation = '';
            panelLogin.style.display = 'block';
            panelLogin.style.animation = 'slideInLeft 0.18s ease forwards';
            setTimeout(() => { panelLogin.style.animation = ''; }, 180);
        }, 180);
    }
}