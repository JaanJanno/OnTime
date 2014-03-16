$(document).ready(function() {
    $(".live-header").click(function() {
    	if ($(".live-body").is(":visible") ){
    		$(".live-body").hide();
    	} else{
    		$(".live-body").show();
    	}
    });
});
