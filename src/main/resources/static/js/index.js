document.addEventListener('DOMContentLoaded', function (){
    const currentUserId = 1;
    fetchBooks();
    fetchRecommendations(currentUserId);
    getShoppingCart(currentUserId);
})
let cartItemCount = {};
let currentCartId;
function fetchBooks(){
    fetch('/books')
        .then(response => response.json())
        .then(books => displayBooks(books))
        .catch(err => console.error("Error fetching books: ", err));
}

function displayBooks(books){
    const container = document.getElementById('books-container')
    container.innerHTML = '';

    books.forEach(book => {
        const bookElement = document.createElement('div');
        bookElement.classList.add("book-item");
        bookElement.innerHTML= `
            <h2>${book.title}</h2>
            <img src="${book.picture}" alt="${book.title}" style="max-width: 100px; max-height: 150px;"> 
            <p>Author: ${book.author}</p>
            <p>Price: $${book.price.toFixed(2)}</p>
            <button class="add-to-cart" data-bookid="${book.id}">Add to Cart</button>
        `;
        container.appendChild(bookElement);
    });

    container.addEventListener("click", function (event){
        if(event.target && event.target.matches(".add-to-cart")){
            const bookId =event.target.getAttribute("data-bookid");
            addToCart(bookId);
        }
    });
}

function fetchRecommendations(userId){
    fetch(`/books/recommendations/${userId}`)
        .then(response => response.json())
        .then(recommendations => displayRecommendations(recommendations))
        .catch(err => console.error("Error fetching recommendations", err))
}

function displayRecommendations(recommendations){
    const container = document.getElementById("recommendations-container");
    container.innerHTML="";

    recommendations.forEach(book => {
        const bookElement = document.createElement("div");
        bookElement.classList.add("book-item")
        bookElement.innerHTML = `
            <h2>${book.title}</h2>
            <img src="${book.picture}" alt="book cover img" style="max-width: 100px; max-height: 150px;"/>
            <p>Author: ${book.author}</p>
            <p>Price: $${book.price.toFixed(2)}</p>
            <button class="add-to-cart" data-bookid="${book.id}">Add to Cart</button>
        `;
        container.appendChild(bookElement);
    });

    container.addEventListener("click", function (event){
        if(event.target && event.target.matches(".add-to-cart")){
            const bookId =event.target.getAttribute("data-bookid");
            addToCart(bookId);
        }
    });
}

function addToCart(bookId){
    const currentCartId = 1;
    cartItemCount[bookId] = (cartItemCount[bookId] || 0) + 1; // Increment count
    updateCartDisplay(bookId, cartItemCount[bookId]); // Update display
    fetch(`/shoppingCart/${currentCartId}/addItem`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({bookId})
    })
    .then(response => {
        if(response.ok){
            console.log("Book added to cart");
            getShoppingCart(1)
        } else{
            console.error("Error adding book to cart");
        }
    })
    .catch(err => console.error("Error adding book to cart: ", err));
}

function updateCartDisplay(bookId, count) {
    // Update display in both books and recommendations container
    updateDisplayInContainer('books-container', bookId, count);
    updateDisplayInContainer('recommendations-container', bookId, count);
}

function updateDisplayInContainer(containerId, bookId, count) {
    const container = document.getElementById(containerId);
    const bookElement = container.querySelector(`[data-bookid="${bookId}"]`);
    if (bookElement) {
        let countDisplay = bookElement.querySelector('.cart-count');
        if (!countDisplay) {
            countDisplay = document.createElement('span');
            countDisplay.className = 'cart-count';
            bookElement.appendChild(countDisplay);
        }
        countDisplay.textContent = `, In Cart: ${count}`;
    }
}
function getShoppingCart(userId) {
    fetch(`/shoppingCart/user/${userId}`)
        .then(response => response.json())
        .then(cart => {
            currentCartId = cart.id;
            cart.books.forEach(book => {
                cartItemCount[book.id] = book.quantity;
                updateCartDisplay(book.id, book.quantity);
            });
        })
        .catch(error => console.error('Error fetching cart:', error));
}

function filterUpdate(){
    const
        input = document.getElementById("filterInput"),
        container = document.getElementById('books-container'),
        filter = input.value.toUpperCase();
    container.innerHTML = '';
    fetch('/books')
        .then(response => response.json())
        .then(books => {
            books.forEach(book => {
                if(filter === "" || book.title.toUpperCase().indexOf(filter) > -1)
                {
                    const bookElement = document.createElement('div');
                    bookElement.classList.add("book-item");
                    bookElement.innerHTML= `
                        <h2>${book.title}</h2>
                        <img src="${book.picture}" alt="${book.title}" style="max-width: 100px; max-height: 150px;"> 
                        <p>Author: ${book.author}</p>
                        <p>Price: $${book.price.toFixed(2)}</p>
                        <button class="add-to-cart" data-bookid="${book.id}">Add to Cart</button>
                    `;
                    container.appendChild(bookElement);
                }
            });
        })
        .catch(err => console.error("Error fetching books: ", err));
}

window.cartItemCount = cartItemCount;
window.updateCartDisplay = updateCartDisplay;