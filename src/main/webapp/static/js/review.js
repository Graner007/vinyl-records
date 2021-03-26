const checkbox = document.querySelector("#flexSwitchCheckDefault");

checkbox.addEventListener("click", () => {
    const billingAddress = document.querySelector('#billingAddress');
    const billingCity = document.querySelector('#billingCity');
    const billingState = document.querySelector('#billingState');
    const billingZip = document.querySelector('#billingZip');

    if (checkbox.checked) {
        const shippingAddress = document.querySelector('#shippingAddress').value;
        const shippingCity = document.querySelector('#shippingCity').value;
        const shippingState = document.querySelector('#shippingState').value;
        const shippingZip = document.querySelector('#shippingZip').value;

        billingAddress.value = shippingAddress;
        billingCity.value = shippingCity;
        billingState.value = shippingState;
        billingZip.value = shippingZip;

    }
    else {
        billingAddress.value = "";
        billingCity.value = "";
        billingState.value = "";
        billingZip.value = "";
    }
});