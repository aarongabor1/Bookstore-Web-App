document.addEventListener('DOMContentLoaded', function () {
    const currentUserId = 1; // This should be dynamically determined
    fetchCart(currentUserId);
});

function fetchCart(userId) {
    fetch(`/shoppingCart/user/${userId}`)
        .then(response => response.json())
        .then(cart => displayCart(cart.books))
        .catch(error => console.error('Error fetching cart:', error));
}

function displayCart(books) {
    const cartContent = document.getElementById('cart-content');
    cartContent.innerHTML = '';
    let subtotal = 0;

    books.forEach((book, index) => {
        const row = cartContent.insertRow();

        const coverCell = row.insertCell(0);
        const coverImg = document.createElement("img");
        console.log(book.picture);
        coverImg.src = book.picture;
        coverImg.alt = "Book Cover";
        coverImg.style.width = "50px";
        coverCell.appendChild(coverImg);

        row.insertCell(1).innerText = book.title;
        row.insertCell(2).innerText = book.isbn;
        row.insertCell(3).innerText = book.author;
        row.insertCell(4).innerText = "$" + book.price.toFixed(2);
        row.insertCell(5).innerText = book.quantity;

        // Add a cell for the remove button
        const removeCell = row.insertCell(6);
        const removeButton = document.createElement("button");
        removeButton.innerText = "Remove";
        removeButton.onclick = function () { removeFromCart(index, books); };
        removeCell.appendChild(removeButton);

        subtotal += book.price * book.quantity;
    });

    displayTotals(subtotal);
}

function removeFromCart(index, books) {
    const bookId = books[index].id;
    const cartId = 1; // Replace with the actual cart ID

    fetch(`/shoppingCart/${cartId}/removeItem`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(bookId)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Item removed from cart:', data);

            // Remove the item from the local 'books' array and update the display
            books.splice(index, 1);
            displayCart(books);
        })
        .catch(error => {
            console.error('Error removing item from cart:', error);
        });
}

function displayTotals(subtotal) {
    const taxes = subtotal * 0.13;
    const total = subtotal + taxes;

    document.getElementById("SubtotalAmount").innerText = "$" + subtotal.toFixed(2);
    document.getElementById("TaxesAmount").innerText = "$" + taxes.toFixed(2);
    document.getElementById("TotalAmount").innerText = "$" + total.toFixed(2);

    // Update the total in localStorage
    localStorage.setItem('totalAmount', total.toFixed(2));
}