function getCurrentUserId() {
    //placeholder till someone gets auth working
    return 1;
}

document.addEventListener('DOMContentLoaded', function () {
    fetchBooks();
    const userId = getCurrentUserId();
    fetchRecommendations();
});

function fetchBooks() {
    fetch('/books')
        .then(response => response.json())
        .then(books => displayBooks(books))
        .catch(error => console.error('Error fetching books:', error));
}


function fetchRecommendations(userId) {
    fetch(`/books/recommendations/${userId}`)
        .then(response => response.json())
        .then(recommendations => displayRecommendations(recommendations))
        .catch(error => console.error("error fetching recommendations...", error))
}
function displayRecommendations(recommendations) {
    const container = document.getElementById("recommendations-container");
    container.innerHTML ='';

    recommendations.forEach(book => {
        const bookElement = document.createElement("div");
        bookElement.innerHTML = `
            <h2>${book.title}</h2>
            <img src="${book.picture}"  alt="book cover img " style="max-width: 100px; max-height: 150px;"/>
            <p>Author: ${book.author}</p>
            <p>Price: $${book.price}</p>
        `;
        console.log(bookElement)
        container.appendChild(bookElement);
    })
}

function displayBooks(books) {
    const container = document.getElementById('books-container');
    container.innerHTML = '';

    books.forEach(book => {
        const bookElement = document.createElement('div');
        // console.log(book.picture);
        bookElement.innerHTML = `
            <h2>${book.title}</h2>
            <img src="${book.picture}"  alt="book cover img"/>
            <p>Author: ${book.author}</p>
            <p>Price: $${book.price}</p>
        `;
        container.appendChild(bookElement);
    });
}
