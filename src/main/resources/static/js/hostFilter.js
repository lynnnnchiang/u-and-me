const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
// <!--網頁載入後執行-->


window.addEventListener("load", function (e) {
this.fetch(baseUrl + 'host/match', {
    method: 'GET'
}).then(response => {
    if(response.status == 401){

            this.location.href = baseUrl + 'tmp/back_end/back_end/host/hostLogin.html';

    }
});
})
