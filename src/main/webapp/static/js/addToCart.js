let productsNumber = document.querySelector("#products-number");
const shoppingCart = document.querySelector("#cart");
const names = document.querySelectorAll(".card-title");
const prices = document.querySelectorAll(".lead");

shoppingCart.addEventListener("click", () => {
    window.location.href = '/shopping-cart';
});

function addGlobalEventListener(type, classname, callback) {
    document.addEventListener(type, e => {
        if (e.target.className == classname) callback (e)
    });
}

addGlobalEventListener("click", "btn btn-outline-primary", e => {
    let name;
    let price;

    for (let i = 0; i < names.length; i++) {
        if (names[i].textContent == e.target.id) {
            name = e.target.id;
            price = prices[i].textContent;
        }
    }

    let data = {
        name: name,
        price: price
    };

    console.log(data);

    fetch('/', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(async data => {
            productsNumber.innerHTML = await data;
        })
        .catch((error) => {console.error("Error: " + error);
        });
});