const shoppingCart = document.querySelector("#cart");
const names = document.querySelectorAll(".card-title");
const prices = document.querySelectorAll(".lead");
const albums = document.querySelectorAll(".album-names");
const orderQuantity = document.querySelector('#products-number');
const hiddenOrderQuantity = document.querySelector('#hidden-products-number');

function renderOrderQuantity() {
    orderQuantity.innerHTML = hiddenOrderQuantity.value;
}

function increaseOrderQuntatiy() {
    orderQuantity.innerHTML = parseInt(orderQuantity.innerHTML) + 1;
}

function addGlobalEventListener(type, classname, callback) {
    document.addEventListener(type, e => {
        if (e.target.className == classname) callback (e)
    });
}

async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });
    return response.json();
}

function main() {
    renderOrderQuantity();

    shoppingCart.addEventListener("click", () => {
        window.location.href = '/shopping-cart';
    });

    addGlobalEventListener("click", "btn btn-outline-primary", e => {
        let name;
        let price;

        for (let i = 0; i < names.length; i++) {
            if (names[i].textContent == e.target.id) {
                name = albums[i].textContent;
                price = prices[i].textContent;
            }
        }

        let data = {
            name: name,
            price: price
        };

        postData("/", data)
            .then(async data => {
                increaseOrderQuntatiy();
            })
            .catch((error) => {console.error("Error: " + error);
            });
    });
}

main();