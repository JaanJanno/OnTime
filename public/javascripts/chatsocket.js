function writeToChat(message)
{
	var chat = document.getElementById("chatlist");
	var li = document.createElement("LI");
	chat.appendChild(li);
	li.innerHTML = message;
	$(chat).find('li').slice(0, -5).remove();
}

function writeToEvents(message)
{
	var chat = document.getElementById("eventlist");
	var li = document.createElement("LI");
	chat.appendChild(li);
	li.innerHTML = message;
	$(chat).find('li').slice(0, -5).remove();
	
}

function writeToWars(message)
{
	var chat = document.getElementById("warlist");
	var li = document.createElement("LI");
	chat.appendChild(li);
	li.innerHTML = message;
	$(chat).find('li').slice(0, -5).remove();
}

function clear()
{
	for(var i=0;i<11;i++){
		for(var j=0;j<11;j++){
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
				var parts = partsOfStr[rida].split(',');
				var id = "f"+"+"+parts[1]+"+"+parts[0]
				if (document.getElementById(id).src != parts[2]){
					document.getElementById(id).src = parts[2];
				}
			}		
		}
		
		if(!received_msg == "" && received_msg[0] == "r"){

			var partsOfStr = received_msg.substring(2).split(';');
			for(var rida in partsOfStr){		
				var parts = partsOfStr[rida].split(',');
				var id = "b"+"+"+parts[1]+"+"+parts[0]
				if (document.getElementById(id).src != parts[2]){
					document.getElementById(id).src = parts[2];
				}
			}		
		}
		
		if(!received_msg == "" && received_msg[0] == "e"){

			writeToEvents(received_msg.substring(2));
		}
		
		if(!received_msg == "" && received_msg[0] == "w"){

			writeToWars(received_msg.substring(2));
		}
	};
}
