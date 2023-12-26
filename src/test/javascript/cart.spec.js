describe('Shopping Cart', () => {
    beforeEach(() => {
        // Set up the DOM elements needed for testing
        document.body.innerHTML = `
            <div id="cart-content"></div>
            <div id="SubtotalAmount"></div>
            <div id="TaxesAmount"></div>
            <div id="TotalAmount"></div>
        `;
    });

    it('should fetch and display the shopping cart', (done) => {
        spyOn(window, 'fetch').and.returnValue(Promise.resolve({
            json: () => Promise.resolve({
                books: [
                    { title: 'Book 1', isbn: '1234567890', author: 'Author 1', price: 20.5, quantity: 2, cover: 'cover1.jpg' },
                    { title: 'Book 2', isbn: '0987654321', author: 'Author 2', price: 15.75, quantity: 1, cover: 'cover2.jpg' },
                ],
            }),
        }));

        fetchCart(1);

        setTimeout(() => {
            const cartContent = document.getElementById('cart-content');
            const rows = cartContent.querySelectorAll('tr');

            // Expectations
            expect(rows.length).toBe(3); // Header row + 2 book rows
            expect(rows[1].cells[1].innerText).toBe('Book 1');
            expect(rows[1].cells[2].innerText).toBe('1234567890');
            expect(rows[2].cells[1].innerText).toBe('Book 2');
            expect(rows[2].cells[2].innerText).toBe('0987654321');
            expect(document.getElementById('SubtotalAmount').innerText).toBe('$56.75');
            expect(document.getElementById('TaxesAmount').innerText).toBe('$7.37');
            expect(document.getElementById('TotalAmount').innerText).toBe('$64.12');

            done();
        });
    });

    it('should remove an item from the shopping cart', () => {
        const books = [
            { title: 'Book 1', isbn: '1234567890', author: 'Author 1', price: 20.5, quantity: 2, cover: 'cover1.jpg' },
            { title: 'Book 2', isbn: '0987654321', author: 'Author 2', price: 15.75, quantity: 1, cover: 'cover2.jpg' },
        ];

        removeFromCart(0, books);

        // Expectations
        expect(books.length).toBe(1);
        expect(books[0].title).toBe('Book 2');
    });
});