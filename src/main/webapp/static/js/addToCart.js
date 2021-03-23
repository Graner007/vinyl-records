const buttons = document.querySelectorAll(".card-text");
let productsNumber = document.querySelector("#products-number");

for (let button of buttons) {
    button.addEventListener('click', () => {
        let data = {
            name: button.parentNode.previousSibling.previousSibling.firstChild.nextSibling.textContent,
            price: button.parentNode.firstChild.nextSibling.firstChild.nextSibling.textContent
        };
        fetch('/', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                productsNumber.innerHTML = data;
            })
            .catch((error) => {console.error("Error: " + error);
            });
    });
}