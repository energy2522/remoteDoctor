function Load() {
    var socket = new SockJS('/secured/room');
    var stompClient = Stomp.over(socket);
    var sessionId = "";

    stompClient.connect({}, function (frame) {
        var url = stompClient.ws._transport.url;
        console.log(url);
        url = url.replace("ws://localhost:8081/secured/room/",  "");
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");
        console.log("Your current session is: " + url);
        sessionId = url;

        stompClient.subscribe('/secured/user/queue/specific-user' + '-user' + sessionId, function(msgOut) {
            showRecipientMessage(JSON.parse(msgOut.body));
        });

        return stompClient;

    });


    function sendMessage() {

        var username = $("#recipient_username").val();
        var message = $("#messageText").val();
        var chatId = $("#chatId").val();

        var msg = {
            'chatId': chatId,
            'toId': username,
            'messageText': message,
            'sendDate': getCurrentTime()
        };

        console.log(JSON.stringify(msg));
        stompClient.send('/secured/room', {}, JSON.stringify(msg));

        showSenderMessage(message);
    }

    function showRecipientMessage(body) {
        removeDefaultText();
        alert(body.messageText);

        var data = {
            'message' : body.messageText,
            'sendDate' : body.sendDate
        };

        var messageTemplate = $("#recipient_message").html();
        var htmlMessage = Mustache.to_html(messageTemplate, data);

        $("#canvas").append(htmlMessage);
    }

    function showSenderMessage(message) {
        removeDefaultText();
        var data = {
            'message' : message,
            'sendDate': getCurrentTime()
        };

        var messageTemplate = $("#sender_message").html();
        var htmlMessage = Mustache.to_html(messageTemplate, data);

        $("#canvas").append(htmlMessage);
        $("#messageText").val('');
    }

    function getCurrentTime() {
        var now = new Date();
        var stringDate = now.getHours() + ':' + now.getMinutes();

        return stringDate;
    }

    function removeDefaultText() {
        var defaultText = $('#default_text');

        if (defaultText) {
            defaultText.remove();
        }
    }

    function openDiscussion() {
        var $form = $("#discussion_form");
        console.log("Send discussion");

        $form.submit();
    }

    var usernameForm = document.querySelector('#sendButton');
    usernameForm.addEventListener('click', sendMessage, true);

    var sendDiscussionButton = document.querySelector("#send_discussion");
    sendDiscussionButton.addEventListener('click', openDiscussion, true);
}