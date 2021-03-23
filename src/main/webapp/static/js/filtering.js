const genres = document.querySelector('#genres');
const artists = document.querySelector('#artists');
let sidebarContent = document.querySelector('#sidebarContent');

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
                li.innerHTML = d;
                console.log(li);
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