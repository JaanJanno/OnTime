$(document).ready(function() {
    $(".events-header").click(function() {
    	if ($(".events-body").is(":visible") ){
    		$(".events-body").hide();
    	} else{
    		$(".events-body").show();
    	}
    });
});
