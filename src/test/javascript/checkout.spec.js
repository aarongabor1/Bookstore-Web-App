describe('Checkout Form', () => {
    beforeEach(() => {
        // Set up the DOM elements needed for testing
        document.body.innerHTML = `
            <form id="checkout-form">
                <h2>Bank Details</h2>
                <div class="section" id="bank-details">
                    <input type="text" id="firstName" name="firstName" placeholder="First Name" required>
                    <input type="text" id="lastName" name="lastName" placeholder="Last Name" required>
                    <input type="text" id="cardNumber" name="cardNumber" placeholder="Card Number" required>
                    <input type="text" id="ccv" name="ccv" placeholder="CCV" required>
                    <input type="month" id="expiryDate" name="expiryDate" placeholder="Expiry Date" required>
                </div>
            
                <h2>Address</h2>
                <div class="section" id="address-details">
                    <input type="text" id="city" name="city" placeholder="City" required>
                    <input type="text" id="country" name="country" placeholder="Country" required>
                    <input type="text" id="postalCode" name="postalCode" placeholder="Postal Code" required>
                </div>
            
                <button type="submit">Confirm Purchase</button>
            </form>
            <div id="totalAmount"></div>
        `;
    });

    it('should display the total amount on DOMContentLoaded', () => {
        spyOn(localStorage, 'getItem').and.returnValue('50.00');

        const event = new Event('DOMContentLoaded');
        document.dispatchEvent(event);

        expect(document.getElementById('totalAmount').textContent).toBe('$50.00');
    });

    it('should submit the checkout form and handle success', (done) => {
        spyOn(window, 'fetch').and.returnValue(Promise.resolve({
            ok: true,
            json: () => Promise.resolve({ message: 'Order successful' }),
        }));

        spyOn(window, 'FormData').and.returnValue({
            append: () => {},
        });

        const form = document.getElementById('checkout-form');
        form.dispatchEvent(new Event('submit'));

        setTimeout(() => {
            // Expectation
            expect(window.fetch).toHaveBeenCalledWith('/orders/complete', {
                method: 'POST',
                body: jasmine.any(String),
                headers: {
                    'Content-Type': 'application/json'
                },
            });

            done();
        });
    });

    it('should handle fetch error during form submission', (done) => {
        spyOn(window, 'fetch').and.returnValue(Promise.reject('Network error'));

        const form = document.getElementById('checkout-form');
        form.dispatchEvent(new Event('submit'));

        setTimeout(() => {

            done();
        });
    });
});