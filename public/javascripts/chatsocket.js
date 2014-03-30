function writeToChat(message)
{
	var chat = document.getElementById("chatlist");
	var li = document.createElement("LI");
	chat.appendChild(li);
	li.innerHTML = message;
}

if ("WebSocket" in window)
{
	alert("ws"+document.URL.substring(4, document.URL.length-4)+"chat")
	var ws = new WebSocket("ws"+document.URL.substring(4, document.URL.length-4)+"chat");
	
	ws.onmessage = function (evt) 
	{ 
		var received_msg = evt.data;
		writeToChat(received_msg);
	};
}
