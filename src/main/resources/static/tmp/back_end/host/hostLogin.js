const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

const loginBtn_el = document.getElementById("loginBtn");
loginBtn_el.addEventListener("click", function () {
  const hostEmail = document.getElementById("hostEmail").value;
  const hostPassword = document.getElementById("hostPassword").value;
  const host = {
    "hostEmail": hostEmail,
    "hostPassword": hostPassword
  }

  Login(host);
  //      alert("登入成功")
})

//    const testloginBtn_el = document.getElementById("testloginBtn");
//    testloginBtn_el.addEventListener("click", async function () {
//      const response = await fetch(`${baseUrl}host/testlogin`, {
//        method: "POST",
//        headers: {
//          "Content-Type": "application/json",
//        }
//      });
//      console.log(response);
//    })
//    const logoutBtn_el = document.getElementById("logoutBtn");
//    logoutBtn_el.addEventListener("click", async function () {
//      const response = await fetch(`${baseUrl}host/hostLogout`, {
//        method: "POST",
//        headers: {
//          "Content-Type": "application/json",
//        }
//      });
//      console.log(response);
//    })



Login = async function Login(host) {

  const response = await fetch(baseUrl + 'host/hostLogin', {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(host)
  }); if (response.ok) {
    Swal.fire({
      icon: 'success',
      title: '管理員登入成功',
      text: '',
      confirmButtonText: '確定'
    }).then(() => {
      location.href = baseUrl + 'tmp/back_end/member/member.html'
    })
  } else if (response.status == 402) {
    Swal.fire({
      icon: 'error',
      title: '您已停權',
      text: '請聯繫客服信箱',
      showCancelButton: true
    })
    // location.reload();
  } else {
    Swal.fire({
      icon: 'error',
      title: '管理員登入失敗',
      text: '請檢查您的帳號密碼',
      showCancelButton: true
    })
  }
}


// 登出按鈕
const logoutBtn_el = document.getElementById("logOut");
logoutBtn_el.addEventListener("click", async function () {
  const response = await fetch(baseUrl + 'host/hostLogout', {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  }); if (response.ok) {
    Swal.fire({
      icon: 'success',
      title: '管理員登出成功',
      text: '',
      confirmButtonText: '確定'
    }).then(() => {
      location.href = baseUrl + 'tmp/back_end/host/hostLogin.html'
    })
    // location.reload();
  }
});
