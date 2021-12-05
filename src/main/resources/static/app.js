var stompClient = null;
var connected = false;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
    }
    else {
    }
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        if (!connected) {
            stompClient.send("/app/getFiles", {}, {});
            connected = true;
        }
        stompClient.subscribe('/topic/files', function (message) {
            showFiles(JSON.parse(message.body));
        });
        stompClient.subscribe('/topic/fileContent', function (message) {
            showFileContent(message.body);
        });
        stompClient.subscribe('/topic/message', function (message) {
            updateContent(JSON.parse(message.body).content);
        });
    });
}

function saveFile() {
    console.log($("#fileName").val());
    stompClient.send("/app/save", {}, JSON.stringify({'name': $("#fileName").val(), 'body': $("#textarea1").val()}));
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showFiles(array) {
    let list = document.getElementById("filesList");
    array.forEach(function (item, index) {
        let li = document.createElement("li");
        li.innerText = item;
        li.id = item;
        list.appendChild(li);
      });
    let li = document.createElement("li");
    li.innerText = "New File";
    li.id = "new";
    list.appendChild(li);
}

function updateContent(message) {
    $("#textarea1").val(message);
}

function showFileContent(content) {
    document.getElementById("filesList").style.visibility = "hidden";
    document.getElementById("connection-container").style.visibility = "hidden";
    document.getElementById("heading").innerHTML = "Text Editor";
    document.getElementById("save-container").style.visibility = "visible";
    document.getElementById("flex-box").style.visibility = "visible";
    document.getElementById("textarea-container").style.visibility = "visible";
    document.getElementById("textarea1").innerText = content;
}

function sendText() {
    stompClient.send("/app/text", {}, JSON.stringify({'name': $("#textarea1").val()}));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#save" ).click(function() { saveFile(); });
    $( "#filesList" ).click(function(e) {
        if(e.target && e.target.nodeName == "LI") {
            if (e.target.id != "new") {
                stompClient.send("/app/getFileContent", {}, JSON.stringify({'fileName': e.target.id}));
            } else {
                showFileContent("");
            }
    }})
    $(document).keydown(function(event){
        setTimeout(function() {
            sendText();
        },1000)

    });
});