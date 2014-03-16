$(document).ready(function() {
    $(".latest-header").click(function() {
    	if ($(".latest-body").is(":visible") ){
    		$(".latest-body").hide();
    	} else{
    		$(".latest-body").show();
    	}
    });
});
