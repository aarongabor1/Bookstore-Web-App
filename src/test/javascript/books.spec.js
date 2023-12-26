describe('books.js', function(){
    beforeEach(() => {
        // Set up the DOM elements needed for testing
        document.body.innerHTML = `
            <div id="books-container"></div>
        `;
    });

    it('should fetch books and display them in the container', (done) => {
        // Mocking the fetch function
        spyOn(window, 'fetch').and.returnValue(Promise.resolve({
            json: () => Promise.resolve([
                { title: 'Book 1', author: 'Author 1', price: 20 },
                { title: 'Book 2', author: 'Author 2', price: 25 },
            ]),
        }));

        fetchBooks();

        setTimeout(() => {
            const container = document.getElementById('books-container');
            const bookElements = container.querySelectorAll('div');

            // Expectations
            expect(bookElements.length).toBe(2);
            expect(bookElements[0].querySelector('h2').innerText).toBe('Book 1');
            expect(bookElements[0].querySelector('p:nth-of-type(1)').innerText).toBe('Author: Author 1');
            expect(bookElements[0].querySelector('p:nth-of-type(2)').innerText).toBe('Price: $20');
            expect(bookElements[1].querySelector('h2').innerText).toBe('Book 2');
            expect(bookElements[1].querySelector('p:nth-of-type(1)').innerText).toBe('Author: Author 2');
            expect(bookElements[1].querySelector('p:nth-of-type(2)').innerText).toBe('Price: $25');

            done();
        });
    });

    it('should handle fetch error', (done) => {
        // Mocking the fetch function to simulate an error
        spyOn(window, 'fetch').and.returnValue(Promise.reject('Fetch error'));

        fetchBooks();

        setTimeout(() => {
            // Add expectations for error handling if needed

            done();
        });
    });
});
