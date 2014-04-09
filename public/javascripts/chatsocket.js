function writeToChat(message)
{
	var chat = document.getElementById("chatlist");
	var li = document.createElement("LI");
	chat.appendChild(li);
	li.innerHTML = message;
}

function clear()
{
	for(var i=0;i<10;i++){
		for(var j=0;j<10;j++){
			document.getElementById('f+'+i.toString()+'+'+j.toString()).src = "assets/images/game/tiles/void.png";
		}	
	}	
}

if ("WebSocket" in window)
{
	var ws = new WebSocket("ws"+document.URL.substring(4, document.URL.length-4)+"ws");
	
	
	ws.onmessage = function (evt) 
	{ 
		var received_msg = evt.data;
		
		if(!received_msg == "" && received_msg[0] == "m"){
			writeToChat(received_msg.substring(2));
		}
		
		if(!received_msg == "" && received_msg[0] == "t"){
			
			var partsOfStr = received_msg.substring(2).split(';');
			clear();
			for(var rida in partsOfStr){
				var id = "f"+"+"+partsOfStr[rida][2]+"+"+partsOfStr[rida][0]
				document.getElementById(id).src = partsOfStr[rida].substring(4);
			}		
		}
	};
}
