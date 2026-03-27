function openModal() {
    document.getElementById('loginOverlay').classList.add('active');
}

function closeModal() {
    document.getElementById('loginOverlay').classList.remove('active');
}

function handleOverlay(e) {
    if (e.target === document.getElementById('loginOverlay')) closeModal();
}



function switchPanel(panel) {
    document.getElementById('panel-login').style.display = panel === 'login' ? 'block' : 'none';
    document.getElementById('panel-cadastro').style.display = panel === 'cadastro' ? 'block' : 'none';
}