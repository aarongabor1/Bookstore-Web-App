describe('Book Details', () => {
    beforeEach(() => {
        // Set up the DOM elements needed for testing
        document.body.innerHTML = `
            <div id="bookDetails"></div>
        `;
    });

    it('should fetch and display book details', (done) => {
        const mockBook = {
            title: 'Test Book',
            picture: 'test-cover.jpg',
            author: 'Test Author',
            year: 2022,
            isbn: '1234567890',
            publisher: 'Test Publisher',
            description: 'This is a test book.',
            price: 29.99,
            quantity: 10,
        };

        spyOn(window, 'fetch').and.returnValue(Promise.resolve({
            ok: true,
            json: () => Promise.resolve(mockBook),
        }));

        bookDetails('1234567890');

        setTimeout(() => {
            const detailsContainer = document.getElementById('bookDetails');

            // Expectations
            expect(detailsContainer.innerHTML).toContain('<h1>Test Book</h1>');
            expect(detailsContainer.innerHTML).toContain('<img src="test-cover.jpg" alt="Book Cover Not Found" />');
            expect(detailsContainer.innerHTML).toContain('<h2>Test Author</h2>');
            expect(detailsContainer.innerHTML).toContain('<h3>2022</h3>');
            expect(detailsContainer.innerHTML).toContain('<h3>1234567890</h3>');
            expect(detailsContainer.innerHTML).toContain('<h4>Test Publisher</h4>');
            expect(detailsContainer.innerHTML).toContain('<p>This is a test book.</p>');
            expect(detailsContainer.innerHTML).toContain('<h2> Price $ 29.99</h2>');
            expect(detailsContainer.innerHTML).toContain('<h3>Quantity = 10</h3>');

            done();
        });
    });

    it('should handle error during book details fetch', (done) => {
        spyOn(window, 'fetch').and.returnValue(Promise.reject('Network error'));

        bookDetails('1234567890');

        setTimeout(() => {
            const detailsContainer = document.getElementById('bookDetails');

            // Expectations
            expect(detailsContainer.innerHTML).toContain('<h1> Book Details Unavailable </h1>');

            done();
        });
    });
});