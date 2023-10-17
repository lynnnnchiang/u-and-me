$("#attractionMap").click(function () {
        handleAttractionClick();
    });

async function handleAttractionClick() {
    var baseUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
    try {
        const response = await fetch('AttractionPage', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            if (response)
                window.location.href = response.url;
            // const responseJson = await response.json();
            // console.log("HI" + responseJson);
            // if (responseJson.redirect)
            //     window.location.href = responseJson.redirect;
        } else {
            console.error('Redirect failed');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

