$(document).ready(function() {
    $(".popup_button").click(function (event) {
        event.preventDefault();

        history.pushState({place: 'popup'}, "Log in opened", "#popup");

        openPopup();
    });

    $(".sulge_popup").click(function () {
        history.pushState({}, "Log in closed", document.location.pathname);
        closePopup();
    });

    function openPopup() {
        $(".popup").show();
    }

    function closePopup() {
        $(".popup").hide();
    }

    if (document.location.hash == "#popup") openPopup();

	if(typeof addEventListener == 'function'){
		window.addEventListener("popstate", function (e) {
		    if (e.state && e.state.place == "popup") {
		        openPopup();
		    } else {
		        closePopup();
		    }
		});
	}
});
