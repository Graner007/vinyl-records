const firstName = document.querySelector("#first_name");
const lastName = document.querySelector("#last_name");
const email = document.querySelector("#email");
const password = document.querySelector("#password");
const registerButton = document.querySelector('#register-button');

async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });
    return response.json();
}

function main() {
    registerButton.addEventListener("click", () => {
        const fullName = firstName.textContent + " " + lastName.textContent;
        let data = { name: fullName, email: email.textContent, password: password.textContent };
        postData("/registration", data)
            .then(data => console.log(data))
            .catch(error => console.log(error));
    });
}

main();