$(".listAllAttraction").click(function(){
handleAttractionClick();
});

async function handleAttractionClick() {
    var baseUrl = window.location.protocol + "//" + window.location.host;

    const response = await fetch('listAllAttraction', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
console.log("response.ok : "+response.ok)
console.log("response.url : "+response.url)
    if (response.ok) {
        console.log('Forward success');
        alert("HI" + response);

        if (response)
            window.location.href = response.url;

    } else {
        console.error('Redirect failed');
       // window.location.href = "listAllAttraction.html";
    }
}