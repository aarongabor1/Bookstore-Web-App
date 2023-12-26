document.addEventListener('DOMContentLoaded', (event) => {
    // Retrieve the total amount from local storage
    const totalAmount = localStorage.getItem('totalAmount') || '0.00'; // Default to '0.00' if not set
    // Display the total amount
    document.getElementById('totalAmount').textContent = `$${totalAmount}`;

    const checkoutForm = document.getElementById('checkout-form');

    checkoutForm.addEventListener('submit', function(event) {
        event.preventDefault();

        // Collect form data
        const formData = new FormData(checkoutForm);
        const checkoutData = Object.fromEntries(formData);

        // Convert collected data to JSON
        const checkoutJsonData = JSON.stringify(checkoutData);

        // Submit data to the server
        fetch('/orders/complete', {
            method: 'POST',
            body: checkoutJsonData,
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                alert('Order complete. Thank you for your purchase!');

                // Clear the total amount from localStorage after a successful order
                localStorage.removeItem('totalAmount');

                    // Redirect to the confirmation page with the order ID
                    window.location.href = '/confirmation';
            })
            .catch(error => {
                console.error('Error:', error);
                alert('There was a problem with your order. Please try again.');
            });
    });
});