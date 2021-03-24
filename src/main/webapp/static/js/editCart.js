const quantities = document.querySelectorAll(".quantity");
const subTotalPrices = document.querySelectorAll(".sub-total-price");
const albumNames = document.querySelectorAll(".album-name");
const grandTotal = document.querySelector("#grand-total");
const items = document.querySelectorAll(".items");
const checkoutButton = document.querySelector("#checkout-button");

let index;

for (let i = 0; i < quantities.length; i++) {
    quantities[i].addEventListener("change", () => {
        index = i;
        let data = {
            quantity: quantities[i].value,
            name: albumNames[i].textContent
        }

        fetch("/shopping-cart", {
           method: "POST",
           headers: {"Content-Type": "application/json"},
           body: JSON.stringify(data)
        })
            .then(response => response.json())
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

checkoutButton.addEventListener("click", () => {
    window.location.href = '/checkout';
});

