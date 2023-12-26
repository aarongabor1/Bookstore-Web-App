describe('Bookstore Application', () => {
    beforeEach(() => {
        // Set up the DOM elements needed for testing
        document.body.innerHTML = `
            <div id="books-container"></div>
        `;
    });

    it('should fetch and display books on DOMContentLoaded', (done) => {
        const mockBooks = [
            { id: 1, title: 'Book 1', author: 'Author 1', price: 20 },
            { id: 2, title: 'Book 2', author: 'Author 2', price: 25 },
        ];

        spyOn(window, 'fetch').and.returnValue(Promise.resolve({
            json: () => Promise.resolve(mockBooks),
        }));

        const event = new Event('DOMContentLoaded');
        document.dispatchEvent(event);

        setTimeout(() => {
            const container = document.getElementById('books-container');
            const bookElements = container.querySelectorAll('div');

            // Expectations
            expect(bookElements.length).toBe(2);
            expect(bookElements[0].querySelector('h2').innerText).toBe('Book 1');
            expect(bookElements[0].querySelector('p:nth-of-type(1)').innerText).toBe('Author 1');
            expect(bookElements[0].querySelector('p:nth-of-type(2)').innerText).toBe('Price: $20');
            expect(bookElements[1].querySelector('h2').innerText).toBe('Book 2');
            expect(bookElements[1].querySelector('p:nth-of-type(1)').innerText).toBe('Author 2');
            expect(bookElements[1].querySelector('p:nth-of-type(2)').innerText).toBe('Price: $25');

            done();
        });
    });

    it('should add a book to the cart', (done) => {
        const mockResponse = { ok: true };

        spyOn(window, 'fetch').and.returnValue(Promise.resolve({
            ok: true,
            json: () => Promise.resolve(mockResponse),
        }));

        addToCart(1);

        setTimeout(() => {
            // Expectation
            expect(window.getShoppingCart).toHaveBeenCalledWith(1);

            done();
        });
    });

    it('should fetch the shopping cart on DOMContentLoaded', (done) => {
        const mockCart = { id: 1, items: [] };

        spyOn(window, 'fetch').and.returnValue(Promise.resolve({
            json: () => Promise.resolve(mockCart),
        }));

        const event = new Event('DOMContentLoaded');
        document.dispatchEvent(event);

        setTimeout(() => {
            // Expectation
            expect(currentCartId).toBe(1);

            done();
        });
    });

});