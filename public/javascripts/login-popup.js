$(document).ready(function() {
    $(".popup_button").click(function (event) {
        event.preventDefault();
		$(".popup").show();
		$(".sulge_popup").click(function() {
			$(".popup").hide();
		});
    });
});
