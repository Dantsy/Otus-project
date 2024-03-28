document.addEventListener('DOMContentLoaded', function () {
    console.log('Additional JavaScript-код loaded.');

    function showModal(modalId) {
        var modal = document.getElementById(modalId);
        if (modal) {
            modal.style.display = "block";
        }
    }

    function hideModal(modalId) {
        var modal = document.getElementById(modalId);
        if (modal) {
            modal.style.display = "none";
        }
    }

    function initModals() {
        var modalTriggers = document.querySelectorAll('.modal-trigger');
        modalTriggers.forEach(function (trigger) {
            trigger.addEventListener('click', function () {
                var modalId = this.getAttribute('data-modal');
                showModal(modalId);
            });
        });

        var modalClosers = document.querySelectorAll('.modal-closer');
        modalClosers.forEach(function (closer) {
            closer.addEventListener('click', function () {
                var modalId = this.getAttribute('data-modal');
                hideModal(modalId);
            });
        });
    }

    initModals();
});