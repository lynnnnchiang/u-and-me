$(document).ready(function () {
    $("#loadAttraction").click(function () {

        var id = $("#attractionId").val();
        console.log(id);
        var baseUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;

        fetch( "getAttraction/" + id)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Attraction not found!!!");
                }
                return response.json();
            })
            .then(data => {
                document.getElementById("id").textContent = JSON.stringify(data);
                // You can continue to populate other attributes here
            })
            .catch(error => {
                alert(error.message);
            });


        //   $.ajax寫法
        // < !--$.ajax({-- >
        // < !--url: baseUrl + "getAttraction/" + id, -->
        // < !--method: "GET", -->
        // < !--dataType: "json", -->
        // < !--success: function (data) {
        //             -->
        // < !--$("#id").text(JSON.stringify(data)); -->
        // < !--                    // You can continue to populate other attributes here-->
        // < !--                }, -->
        // < !--error: function () {
        //             -->
        // < !--alert("Attraction not found"); -->
        // < !--                }-- >

    });
    });
