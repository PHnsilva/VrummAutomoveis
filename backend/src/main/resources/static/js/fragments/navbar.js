document.addEventListener('DOMContentLoaded', function () {

    function toggleDropdown() {
        const dropdown = document.getElementById('dropdown');
        if (dropdown.classList.contains('active')) {
            closeDropdown();
        } else {
            dropdown.classList.add('active');
        }
    }

    function closeDropdown() {
        const dropdown = document.getElementById('dropdown');
        dropdown.style.animation = 'popOut 0.18s ease forwards';
        setTimeout(() => {
            dropdown.classList.remove('active');
            dropdown.style.animation = '';
        }, 180);
    }

    function openAccountModal() {
        closeDropdown();
        document.getElementById('account-modal').classList.add('active');
    }

    function closeAccountModal() {
        const modal = document.querySelector('.account-modal');
        modal.style.animation = 'popOut 0.18s ease forwards';
        setTimeout(() => {
            document.getElementById('account-modal').classList.remove('active');
            modal.style.animation = '';
            goBack();
        }, 180);
    }

    function openEdit(field) {
        const mainView = document.getElementById('main-view');
        mainView.style.animation = 'slideOutLeft 0.18s ease forwards';
        setTimeout(() => {
            mainView.style.display = 'none';
            mainView.style.animation = '';
            document.querySelectorAll('.edit-section').forEach(s => s.classList.remove('active'));
            const section = document.getElementById('edit-' + field);
            section.classList.add('active');
            section.style.animation = 'slideInRight 0.18s ease forwards';
            setTimeout(() => { section.style.animation = ''; }, 180);
        }, 180);
    }

    function goBack() {
        const section = document.querySelector('.edit-section.active');
        if (section) {
            section.style.animation = 'slideOutRight 0.18s ease forwards';
            setTimeout(() => {
                section.style.animation = '';
                section.classList.remove('active');
                const mainView = document.getElementById('main-view');
                mainView.style.display = 'block';
                mainView.style.animation = 'slideInLeft 0.18s ease forwards';
                setTimeout(() => { mainView.style.animation = ''; }, 180);
            }, 180);
        }
    }

    function saveField(field) {
        const msg = document.getElementById('success-' + field);
        msg.style.display = 'block';
        setTimeout(() => { msg.style.display = 'none'; goBack(); }, 1500);
    }

    // fecha ao clicar fora do modal
    document.getElementById('account-modal').addEventListener('click', function (e) {
        if (e.target === this) closeAccountModal();
    });

    // fecha dropdown ao clicar fora
    document.addEventListener('click', function (e) {
        const menu = document.querySelector('.user-menu');
        if (menu && !menu.contains(e.target)) {
            const dropdown = document.getElementById('dropdown');
            if (dropdown.classList.contains('active')) closeDropdown();
        }
    });

    window.toggleDropdown = toggleDropdown;
    window.openAccountModal = openAccountModal;
    window.closeAccountModal = closeAccountModal;
    window.openEdit = openEdit;
    window.goBack = goBack;
    window.saveField = saveField;
});