

$(document).ready(function () {
    $("#button-add-new").click(function (event) {
    	event.preventDefault();
        var boxContent = $('.textbox-add-new').val();

        $.ajax({
            type: "POST",
            url: document.URL.substring(0, document.URL.length - 4) + "chat",
            data: {
                'text': boxContent,
            },
            success: function () {
                alert('wow');
            },
            dataType: String
        });
    });
});
