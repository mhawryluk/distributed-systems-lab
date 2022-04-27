let socket;
const idBox = document.getElementById("id-input")
const inputBox = document.getElementById("message-input")
const chatBox = document.getElementById("chat-box")
const setupComponent = document.getElementById("setup")
const mainComponent = document.getElementsByClassName("main-component")[0]
const roomLabel = document.getElementsByClassName("room-name")[0]
const roomBox = document.getElementById("room-input")

function connect() {
    const username = idBox.value
    const room_id = roomBox.value

    if (username === "") {
        alert("enter username")
        return
    }

    socket = new WebSocket(`ws://127.0.0.1:8080/chat/${room_id}?username=${username}`);

    socket.onopen = function () {
        console.log("socket opened");
        roomLabel.innerHTML += room_id
        setupComponent.hidden = true
        mainComponent.hidden = false
    }

    socket.onerror = function (error) {
        console.log(error)
        alert("error")
    }

    socket.onmessage = function (event) {
        const received_msg = event.data;

        let messageType = received_msg.startsWith(username) ? "sent-message" : "received-message"
        chatBox.innerHTML = `<div class="message ${messageType}"> ${received_msg}</div>` + chatBox.innerHTML
    };
}

function sendMessage() {
    let message = inputBox.value;
    socket.send(message);
    inputBox.value = "";
}