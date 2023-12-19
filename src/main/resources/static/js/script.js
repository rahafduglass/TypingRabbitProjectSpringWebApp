const input1 = document.querySelector("#password");
const input2 = document.querySelector("#confirm-password");
const button = document.querySelector("button");

function check(){
    input1.value !== input2.value ? alert("your passwords dont't match!"):console.log("all good");
}

button.addEventListener("click",check);

