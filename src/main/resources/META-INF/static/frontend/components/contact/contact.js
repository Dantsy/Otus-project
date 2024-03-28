const contactForm = document.getElementById('contact-form');
const nameInput = document.getElementById('name');
const emailInput = document.getElementById('email');
const messageInput = document.getElementById('message');

contactForm.addEventListener('submit', function (event) {
    event.preventDefault();

    const name = nameInput.value;
    const email = emailInput.value;
    const message = messageInput.value;


    fetch('/submit-form', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: name,
            email: email,
            message: message
        })
    })
        .then(response => {
            if (response.ok) {
                alert('Message sent successfully!');
                nameInput.value = '';
                emailInput.value = '';
                messageInput.value = '';
            } else {
                throw new Error('Failed to send message');
            }
        })
        .catch(error => {
            alert('Failed to send message: ' + error.message);
        });
});