const payButton = document.querySelector('#pay-button');
//window.location.href = "/payment";

/*
async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });
    return response.json();
}

payButton.addEventListener("click", () => {
    const cardNumber = document.querySelector('#cc-number').value;
    const cardExpiry = document.querySelector("#cc-exp").value;
    const cardCvc = document.querySelector("#cc-cvc").value;
    const cardHolderName = document.querySelector("#card-holder-name").value;
    console.log(cardCvc, cardNumber, cardHolderName);
   postData("/payment", {
       cardnumber: cardNumber,
       cardcvc: cardCvc,
       cardexpiry: cardExpiry,
       cardholdername: cardHolderName
   }).then(data => {
       console.log(data);
       if (data == "success") {
           window.location.href = '/';
       }
   })
       .catch(error => console.log(error));
});*/
