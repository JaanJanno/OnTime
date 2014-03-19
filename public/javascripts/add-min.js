$(document).ready(function() {
    $(".new-header").click(function() {
    	if ($(".new-body").is(":visible") ){
    		$(".new-body").hide();
    	} else{
    		$(".new-body").show();
    	}
    });
});
