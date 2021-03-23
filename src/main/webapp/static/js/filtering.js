const genres = document.querySelector('#genres');
const artists = document.querySelector('#artists');
const sidebarContent = document.querySelector('#sidebarContent');

const sendData = (text) => {
    fetch("/", {
        method: "POST",
        headers: {'Content-Type': "application/json"},
        body: JSON.stringify({text: text})
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => console.log(error));
}

genres.addEventListener("click", () => {
    sendData("genre");
});

artists.addEventListener("click", () => {
   sendData("artist");
});