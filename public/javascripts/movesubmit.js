$(document).ready(function () {
	var mycars = new Array();
	for(i=0;i<11;i++){
		for(j=0;j<11;j++){
			$("#g"+i+"on"+j).click(function (event) {
				event.preventDefault();
				var a = $(this).attr('id').split("on");
				$.ajax({
				    type: "POST",
				    url: document.URL.substring(0, document.URL.length - 4) + "game/move",
				    data: {
				        'x': a[0].substring(1),
				        'y': a[1],
				    },
				    success: function (event) {
				    },
				    dataType: String
				});
    		});
		}	
	}
});
