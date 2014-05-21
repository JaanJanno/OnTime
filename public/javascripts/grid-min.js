$(document).ready(function() {
    $(".chat-header").click(function() {
    	if ($(".chat-body").is(":visible") ){
    		$(".chat-body").hide();
    	} else{
    		$(".chat-body").show();
    	}
    });

    $(".war-header").click(function() {
    	if ($(".war-body").is(":visible") ){
    		$(".war-body").hide();
    	} else{
    		$(".war-body").show();
    	}
    });

    $(".event-header").click(function() {
    	if ($(".event-body").is(":visible") ){
    		$(".event-body").hide();
    	} else{
    		$(".event-body").show();
    	}
    });

    $(".attr-header").click(function() {
    	if ($(".attr-body").is(":visible") ){
    		$(".attr-body").hide();
    	} else{
    		$(".attr-body").show();
    	}
    });

    $(".stat-header").click(function() {
    	if ($(".stat-body").is(":visible") ){
    		$(".stat-body").hide();
    	} else{
    		$(".stat-body").show();
    	}
    });
});
