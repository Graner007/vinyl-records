const genres = document.querySelector('#genres');
const artists = document.querySelector('#artists');
let sidebarContent = document.querySelector('#sidebarContent');
const filters = document.querySelectorAll(".filters");
const products = document.querySelector("#products");

async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });
    return response.json();
}

function fillUpSideBar(data) {
    sidebarContent.textContent = "";
    for (let d of data) {
        let li = document.createElement("li");
        li.classList.add("filters");
        li.innerHTML = d;
        sidebarContent.appendChild(li);
    }
}

function filterProducts(data) {
    products.textContent = "";
    for (let i = 0; i < data.length; i++) {
        let div = document.createElement("div");
        const classList = ['col', 'col-sm-12', 'col-md-6', 'col-lg-4'];

        for (let cl of classList) {
            div.classList.add(cl);
        }

        let divCard = document.createElement("div");
        divCard.classList.add("card");

        let img = document.createElement("img");
        img.src = "/static/img/product_" + data[i][3] + ".png";
        divCard.appendChild(img);

        let divCardHeader = document.createElement("div");
        divCardHeader.classList.add("card-header");
        divCardHeader.classList.add("colorize");

        let h4 = document.createElement("h4");
        h4.classList.add("card-text");
        h4.classList.add("album-names");
        h4.innerHTML = data[i][3];
        divCardHeader.appendChild(h4);

        let h6 = document.createElement("h6");
        h6.classList.add("card-title");
        h6.innerHTML = data[i][0];
        divCardHeader.appendChild(h6);

        let p = document.createElement("p");
        p.classList.add("card-text");
        p.innerHTML = data[i][1];
        divCardHeader.appendChild(p);

        let divCardBody = document.createElement("div");
        divCardBody.classList.add("card-text");

        let divPrice = document.createElement("div");
        divPrice.classList.add('card-text');
        divPrice.classList.add('toCenter');

        let pPrice = document.createElement("p");
        pPrice.classList.add("lead");
        pPrice.innerHTML = data[i][2];
        divCardBody.appendChild(pPrice);

        let divAddToCart = document.createElement("div");
        divAddToCart.classList.add('card-text');
        divAddToCart.classList.add('toCenter');

        let button = document.createElement("button");
        button.classList.add("btn");
        button.classList.add("btn-outline-primary");
        button.setAttribute("id", data[i][0]);
        button.innerHTML = "Add to cart";
        divAddToCart.appendChild(button);

        divCard.appendChild(divCardHeader);
        divCard.appendChild(divCardBody);
        divCard.appendChild(divAddToCart);
        div.appendChild(divCard);
        products.appendChild(div);
    }
}

function addGlobalEventListener(type, classname, callback) {
    document.addEventListener(type, async e => {
        if (e.target.className == classname) {
            await callback (e)
        }
    });
}

genres.addEventListener("click", (e) => {
    postData("/", {text: "genre"})
        .then(date => {
            fillUpSideBar(date);
        })
        .catch(error => console.log(error));
});

artists.addEventListener("click", (e) => {
    postData("/", {text: "artist"})
        .then(date => {
            fillUpSideBar(date);
        })
        .catch(error => console.log(error));
});

addGlobalEventListener("click", "filters", e => {
    postData("/", { filter: e.target.innerHTML })
        .then(data => {
            filterProducts(data);
        })
        .catch(error => console.log(error));
});