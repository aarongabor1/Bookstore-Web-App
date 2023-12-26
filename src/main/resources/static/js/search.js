document.addEventListener('DOMContentLoaded', function () {
    const searchForm = document.getElementById('search-form');
    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const query = document.getElementById('search-query').value;
        performSearch(query);
    });
});

function performSearch(query) {
    // The URL should point to your backend search endpoint
    //change end point depending on getmapping instead of search
    fetch(`/search?query=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => displaySearchResults(data))
        .catch(error => console.error('There was an error retrieving the search results:', error));
}

function displaySearchResults(results) {
    const resultsContainer = document.getElementById('search-results');
    // Clear previous results
    resultsContainer.innerHTML = '';

    // Check if there are no results
    if (results.length === 0) {
        resultsContainer.innerHTML = '<p>No results found. Try another search query.</p>';
        return;
    }

    // Iterate over the results and create the HTML for each book
    results.forEach(book => {
        const bookRow = document.createElement('div');
        bookRow.className = 'book-result';
        bookRow.innerHTML = `
            <div class="book-cover"><img src="${book.coverImageUrl}" alt="${book.title} Cover"></div>
            <div class="book-title">${book.title}</div>
            <div class="book-author">${book.author}</div>
            <div class="book-price">${book.price}</div>
            <div class="add-to-cart">
                <button onclick="addToCart('${book.coverImageUrl}', '${book.title}', '${book.author}', ${book.price})">Add to Cart</button>
            </div>
        `;
        resultsContainer.appendChild(bookRow);
    });
}
