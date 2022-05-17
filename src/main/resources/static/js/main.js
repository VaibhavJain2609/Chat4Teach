'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var nameField = document.querySelector('#name');

var stompClient = null;
var params = null;
var currentURL = window.location.pathname;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

document.addEventListener("DOMContentLoaded", function(){
	if (window.location.pathname == "/chat") {
		nameField.classList.remove('hidden');
	}
});

function getUsername(url, callback){
    params = new URLSearchParams(window.location.search);
	console.log("getting username " + params.get("UUID"));
	jQuery.get(url + params.get("UUID"), function(data){
		var response = data.name;
		console.log(JSON.stringify(data));
		callback(response);
	}, 'json');
}

function getStudents(callback){
    params = new URLSearchParams(window.location.search);
    console.log("getting students");
    jQuery.get("/allStudents", function(data){
        var response = data;
        console.log(JSON.stringify(data));
        callback(response);
    }, 'json');
}

function revealChat() {
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }	
}

function connect(event) {
    //username = document.querySelector('#name').value.trim();
    currentURL = window.location.pathname;
    
    console.log(currentURL);
    if(currentURL == "/StudentPortal") {
	    getUsername('students/UUID/', function(data){
			username = data;
			console.log("got username" + username);
			revealChat();
		});
	} else if (currentURL == "/chat") {
		username = document.querySelector('#name').value.trim();
		revealChat();
	}
    
    event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
		if(currentURL == '/chat'){
	        var chatMessage = {
	            sender: username,
	            content: messageInput.value,
	            type: 'CHAT'
	        };
        } else {
			var chatMessage = {
				sender: username,
				content: messageInput.value,
				type: 'DIRECTED',
				target: 'admin'
			}
		}
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));

        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    var messageRecieved = false;

    var messageElement = document.createElement('li');
    var studentElement = document.createElement('li');
    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
		messageRecieved = true;
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
		messageRecieved = true;
    } else if (message.type === 'CHAT' || (message.type === 'DIRECTED' && message.target == username) || message.sender == username || (currentURL == '/chat' && message.target == 'admin')){
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        
		messageRecieved = true;
    }
	
	if(messageRecieved) {
	    var textElement = document.createElement('p');
	    var messageText = document.createTextNode(message.content);
	    textElement.appendChild(messageText);
	
	    messageElement.appendChild(textElement);
	
	    messageArea.appendChild(messageElement);
        playSound("https://audio.code.org/goal1.mp3")
	    messageArea.scrollTop = messageArea.scrollHeight;
    }

    getStudents(function(data){
        let students = data;
        console.log("got students" + students);
        var ul = document.createElement('ul');
        var div = document.getElementById('studentsInChat');
        removeAllChildNodes(div);
        document.getElementById('studentsInChat').appendChild(ul);

        students.forEach(item => {
            let li = document.createElement('li');
            ul.appendChild(li);

            li.innerHTML += item;
        });
    });
}
function playSound(url) {
  const audio = new Audio(url);
  audio.play();
}

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function toggleOnlineList(){
	var x = document.getElementById("studentsInChat");
	if (x.style.display == "none") {
		x.style.display = "block";
	} else {
		x.style.display = "none";
	}
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)