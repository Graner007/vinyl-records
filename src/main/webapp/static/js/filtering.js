const genres = document.querySelector('#genres');
const artists = document.querySelector('#artists');
let sidebarContent = document.querySelector('#sidebarContent');
const filters = document.querySelectorAll(".filters");

const sendData = (text) => {
    fetch('/', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ text:text })
    })
        .then(response => response.json())
        .then(data => {
            sidebarContent.textContent = "";
            for (let d of data) {
                let li = document.createElement("li");
                li.classList.add("filters");
                li.innerHTML = d;
                sidebarContent.appendChild(li);
            }
        })
        .catch((error) => {console.error("Error: " + error);
        });
}

genres.addEventListener("click", () => {
    sendData("genre");
});

artists.addEventListener("click", () => {
   sendData("artist");
});

for (let filter of filters) {
    filter.addEventListener("click", () => {
        fetch('/', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ filter: filter.innerHTML })
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })
            .catch((error) => {console.error("Error: " + error);
            });
    });
}