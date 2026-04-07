function openModal(panel = null) {
    const overlay = document.getElementById('loginOverlay');
    if (!overlay) return;
    overlay.classList.add('active');
    if (panel) {
        switchPanel(panel, true);
    }
}

function closeModal() {
    const overlay = document.getElementById('loginOverlay');
    if (!overlay) return;
    overlay.classList.remove('active');
}

function handleOverlay(e) {
    if (e.target === document.getElementById('loginOverlay')) closeModal();
}

function switchPanel(panel, immediate = false) {
    const panelLogin = document.getElementById('panel-login');
    const panelCadastro = document.getElementById('panel-cadastro');
    if (!panelLogin || !panelCadastro) return;

    if (panel === 'cadastro') {
        panelLogin.style.display = 'none';
        panelCadastro.style.display = 'block';
    } else {
        panelCadastro.style.display = 'none';
        panelLogin.style.display = 'block';
    }

    if (!immediate) {
        const overlay = document.getElementById('loginOverlay');
        if (overlay && !overlay.classList.contains('active')) {
            overlay.classList.add('active');
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const openPanel = document.body.dataset.openPanel;
    if (openPanel === 'cadastro' || openPanel === 'login') {
        openModal(openPanel);
    }
});
