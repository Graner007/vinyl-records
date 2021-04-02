const quantities = document.querySelectorAll(".quantity");
const subTotalPrices = document.querySelectorAll(".sub-total-price");
const albumNames = document.querySelectorAll(".album-name");
const grandTotal = document.querySelector("#grand-total");
const items = document.querySelectorAll(".items");
const deleteIcons = document.querySelectorAll(".fa-trash-alt");
const checkoutButton = document.querySelector("#checkout-button");
let index;

async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });
    return response.json();
}

function deleteAlbumByTrashIcon() {
    for (let i = 0; i < deleteIcons.length; i++) {
        deleteIcons[i].addEventListener("click", () => {
            index = i;
            let data = {
                quantity: 0,
                name: albumNames[i].textContent
            }

            postData("/shopping-cart", data)
                .then(data => {
                    for (let i = 0; i < items.length; i++) {
                        items[index].textContent = "";
                    }
                })
                .catch(error => console.log(error));
        });
    }
}

function deleteAlbumByChangeQuantityToZero() {
    for (let i = 0; i < quantities.length; i++) {
        quantities[i].addEventListener("change", () => {
            index = i;
            let data = {
                quantity: quantities[i].value,
                name: albumNames[i].textContent
            }

            postData("/shopping-cart", data)
                .then(data => {
                    if (data[0] == 0) {
                        for (let i = 0; i < items.length; i++) {
                            items[index].textContent = "";
                        }
                    }

                    for (let i = 0; i < subTotalPrices.length; i++) {
                        subTotalPrices[index].innerHTML = data[0].toFixed(2);
                    }
                    grandTotal.innerHTML = data[1].toFixed(2);
                })
                .catch(error => console.log(error));
        });
    }
}

function main() {
    checkoutButton.addEventListener("click", () => {
        window.location.href = '/checkout';
    });

    deleteAlbumByChangeQuantityToZero();
    deleteAlbumByTrashIcon();
}

main();