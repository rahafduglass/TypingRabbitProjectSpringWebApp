
const typingText = document.querySelector(".typing-text p"),
    inpField = document.querySelector(".wrapper .input-field"),
    tryAgainBtn = document.querySelector(".content button"),
    timeTag = document.querySelector(".time span b"),
    mistakeTag = document.querySelector(".mistake span"),
    wpmTag = document.querySelector(".wpm span"),
    cpmTag = document.querySelector(".cpm span");

let timer,
    maxTime = 60, //constant, it'll never change.
    timeLeft = maxTime,
    charIndex = mistakes = isTyping = 0;

function loadParagraph() {
    const ranIndex = Math.floor(Math.random() * paragraphs.length);
    typingText.innerHTML = "";
    paragraphs[ranIndex].split("").forEach(char => {
        let span = `<span>${char}</span>`
        typingText.innerHTML += span;
    });
    typingText.querySelectorAll("span")[0].classList.add("active");
    document.addEventListener("keydown", () => inpField.focus());
    typingText.addEventListener("click", () => inpField.focus());
}

function initTyping() {
    let characters = typingText.querySelectorAll("span");
    let typedChar = inpField.value.split("")[charIndex];
    let method;
    if (charIndex < characters.length - 1 && timeLeft > 0) {
        if (!isTyping) {
            // If isTyping is not true
            timer = setInterval(initTimer, 1000);
            // Set up a timer that calls the initTimer function every 1000 milliseconds (1 second)
            isTyping = true;
            // Set isTyping to true, indicating that the typing has started
        }
        if (typedChar == null) {
            if (charIndex > 0) {
                charIndex--;
                if (characters[charIndex].classList.contains("incorrect")) {
                    mistakes--;
                }
                characters[charIndex].classList.remove("correct", "incorrect");
            }
        } else {
            if (characters[charIndex].innerText === typedChar) {
                characters[charIndex].classList.add("correct");
            } else {
                mistakes++;
                characters[charIndex].classList.add("incorrect");
            }
            charIndex++;
        }
        characters.forEach(span => span.classList.remove("active"));
        characters[charIndex].classList.add("active");

        let wpm = Math.round(((charIndex - mistakes) / 5) / (maxTime - timeLeft) * 60);
        wpm = wpm < 0 || !wpm || wpm === Infinity ? 0 : wpm;

        wpmTag.innerText = wpm;
        mistakeTag.innerText = mistakes;
        cpmTag.innerText = charIndex - mistakes;
    } else {
        clearInterval(timer);
        inpField.value = "";
        //here we should fetch data.// backend
    }
}

function sendData() {
        var WPM = $("#wpm").text();
        var userName = $("#userName").text();
        var requestData = {
            WPM: WPM,
            userName: userName
        }
        $.ajax({
            type: "POST",
            url: "/updateWPM",
            data: JSON.stringify(requestData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",

        })
    }
function updateContent() {
    var userName=$("#userName").text();

    $.ajax({
        url: '/userTypingSpeedPage', // Use an appropriate endpoint
        type: 'GET',
        dataType: JSON.stringify(userName),
        error: function () {
            console.error('Error updating content');
        }
    });
}

// Update content initially
updateContent();

function initTimer() { //this method will be called literally every second by the timer
    if(timeLeft > 0) {
        timeLeft--;
        // to show realtime timer
        timeTag.innerText = timeLeft;
        // setting it
        let wpm = Math.round(((charIndex - mistakes)  / 5) / (maxTime - timeLeft) * 60);
        // to show the realtime wpm report
        wpmTag.innerText = wpm;
        // setting it
    } else {
        clearInterval(timer);
    }
}
// script.js



function resetGame() {
    loadParagraph();
    clearInterval(timer);
    timeLeft = maxTime;
    charIndex = mistakes = isTyping = 0;
    inpField.value = "";
    timeTag.innerText = timeLeft;
    wpmTag.innerText = 0;
    mistakeTag.innerText = 0;
    cpmTag.innerText = 0;
}

loadParagraph();
inpField.addEventListener("input", initTyping);
tryAgainBtn.addEventListener("click", resetGame);