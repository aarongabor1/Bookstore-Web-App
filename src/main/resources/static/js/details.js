function bookDetails(isbn)
{
    fetch(`http://localhost:8080/api/${isbn}`)
        .then(response => response.json())
        .then(book => displayBookDetails(book))
        .catch(error => {
            console.error('Error fetching books:', error);
        });
}

function displayBookDetails(book) {
    const detailsContainer = document.getElementById("bookDetails");
    // Clear previous results
    detailsContainer.innerHTML = '';

    if (!book) {
        detailsContainer.innerHTML = '<h1> Book Details Unavailable </h1>';
        return;
    }

    detailsContainer.innerHTML =
        `<h1>${book.title}</h1>` +
        `<img src="${book.picture}" alt="Book Cover Not Found" />` +
        `<h2>${book.author}</h2>` +
        `<h3>${book.year}</h3>` +
        `<h3>${book.isbn}</h3>` +
        `<h4>${book.publisher}</h4>` +
        `<p>${book.description}</p>` +
        `<h2> Price $ ${book.price.toFixed(2)}</h2>` +
        `<h3>Quantity = ${book.quantity}</h3>`;
}