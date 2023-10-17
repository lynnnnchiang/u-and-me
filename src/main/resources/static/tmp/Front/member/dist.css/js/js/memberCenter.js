
const baseUrL = window.location.protocol + "//" + window.location.host + "/u-and-me/";

// <!--網頁載入後評斷是否為會員-->


window.addEventListener("load", function (e) {
this.fetch(baseUrL + 'member/match', {
    method: 'GET'
}).then(response => {
    if(response.status == 401){

            this.location.href = baseUrL + 'tmp/Front/member/memberLogin.html';

    }
});
})   

//會員登出鍵
const logoutBtn_el = document.getElementById("logoutBtn");
logoutBtn_el.addEventListener("click", async function () {
 const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
    const response = await fetch(baseUrl + 'member/logout', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        }
    });
       location.reload();
})