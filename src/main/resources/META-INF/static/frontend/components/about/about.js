function goToContactPage() {
    window.location.href = '/contact';
}

const contactLink = document.querySelector('a[href="/contact"]');

contactLink.addEventListener('click', function (event) {
    event.preventDefault();
    goToContactPage();
});