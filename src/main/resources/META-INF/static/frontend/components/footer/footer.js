function openInNewTab(url) {
    window.open(url, '_blank');
}

const socialLinks = document.querySelectorAll('.social-links a');

socialLinks.forEach(link => {
    link.addEventListener('click', function (event) {
        event.preventDefault();
        const url = this.getAttribute('href');
        openInNewTab(url);
    });
});

const contactInfoLink = document.querySelector('.contact-info a');

contactInfoLink.addEventListener('click', function (event) {
    event.preventDefault();
    const email = this.getAttribute('href');
    window.location.href = `mailto:${email}`;
});