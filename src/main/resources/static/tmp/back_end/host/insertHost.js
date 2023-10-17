const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

/////////////////////////////

      const hostEmail = document.getElementById("hostEmail");
      const hostPassword = document.getElementById("hostPassword");
      const hostName = document.getElementById("hostName");
      const hostPhone = document.getElementById("hostPhone");

//錯誤處理

  const inputHostEmail_el = this.document.getElementById("inputHostEmail");
  const inputHostPassword_el = this.document.getElementById("inputHostPassword");
  const inputHostName_el = this.document.getElementById("inputHostName");
  const inputHostPhone_el = this.document.getElementById("inputHostPhone");


 //錯誤判斷新增的字串
    let hostEmailStr = document.createElement("span");
    hostEmailStr.style.color = 'red';

    let hostPasswordStr = document.createElement("span");
    hostPasswordStr.style.color = 'red';

    let hostNameStr = document.createElement("span");
    hostNameStr.style.color = 'red';

    let hostPhoneStr = document.createElement("span");
    hostPhoneStr.style.color = 'red';

   //////////



  function Register(host) {
//      console.log(123);
       const response = fetch(baseUrl + "host/register", {
          method: "POST",
          headers: {
              "Content-Type": "application/json",
          },
          body: JSON.stringify(host)
      }).then(response => {
        if (response.ok) {
          Swal.fire({
                         icon: 'success',
                         title: '管理員新增成功',
                         text: '',
                         confirmButtonText: '確定'
      }).then((result) => {
                              if (result.isConfirmed) {
                                  window.history.go(-1);
                              }
                          });

      }else if(response.status == 400){
                            Swal.fire({
                                icon: 'error',
                                title: '此信箱已註冊過管理員',
                                text: '請再次確認帳號',
                                showCancelButton: true
                            }).then(() => {
                                this.location.href = baseUrl + 'tmp/back_end/back_end/hostLogin.html';
                            })
                        }
      })
   }


//按下註冊按鈕
  const regBtn_el = document.getElementById("regButton");
  regBtn_el.addEventListener("click", function(event) {

   event.preventDefault()
      let control = true; //控制是否進入fetch
      //==================錯誤驗證==================
          //初始化Str
          hostEmailStr.innerHTML = '';
          hostPasswordStr.innerHTML = '';
          hostNameStr.innerHTML = '';
          hostPhoneStr.innerHTML = '';

          //管理員姓名
          if (hostName.value === null || hostName.value.trim() === "") {
              control = false;
              hostNameStr.innerHTML = ' *姓名必須填入';
              inputHostName_el.appendChild(hostNameStr);
          }
          //管理員電話
          const phoneCheck = /^09[0-9]{8}$/;
          if (hostPhone.value === null || hostPhone.value.trim() === "") {
              control = false;
              hostPhoneStr.innerHTML = ' *電話必須填入';
              inputHostPhone_el.appendChild(hostPhoneStr);
                  }else if (!phoneCheck.test(hostPhone.value)) {
                    control = false;
                    hostPhoneStr.innerHTML = ' *請輸入正確的手機號碼格式';
                    inputHostPhone_el.appendChild(hostPhoneStr);
                   }
          //管理員密碼
          const passwordCheck = /^[A-Za-z\d!@#$%^&*()]{8,12}$/;
          if (hostPassword.value === null || hostPassword.value.trim() === "") {
              control = false;
              hostPasswordStr.innerHTML = ' *密碼必須填入';
              inputHostPassword_el.appendChild(hostPasswordStr);
              }else if (!passwordCheck.test(hostPassword.value)) {
              control = false;
              hostPasswordStr.innerHTML = ' *密碼必須介於8至12字元';
              inputHostPassword_el.appendChild(hostPasswordStr);
             }

          //會員信箱
          const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
          if (hostEmail.value === null || hostEmail.value.trim() === "") {
              control = false;
              hostEmailStr.innerHTML = ' *信箱必須填入';
              inputHostEmail_el.appendChild(hostEmailStr);
              }else if (!emailRegex.test(hostEmail.value)) {
              control = false;
              hostEmailStr.innerHTML = ' *必須符合信箱格式';
              inputHostEmail_el.appendChild(hostEmailStr);
              }

      const host = {
          "hostName": hostName.value,
          "hostPhone": hostPhone.value,
          "hostPassword": hostPassword.value,
          "hostEmail": hostEmail.value

      };

      if(control){
//          console.log(123);
        Register(host);
      }
  });
// 登出按鈕
const logoutBtn_el = document.getElementById("logOut");
logoutBtn_el.addEventListener("click", async function () {
  const response = await fetch(`${baseUrl}host/hostLogout`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
});if (response.ok) {
                              Swal.fire({
                                             icon: 'success',
                                             title: '管理員登出成功',
                                             text: '',
                                             confirmButtonText: '確定'
                          }).then(()=>{
                            location.href = baseUrl + 'tmp/back_end/host/hostLogin.html'
                          })
                        // location.reload();
                    } 
});

//
//
//
//
//
//async function Register(host) {
//    const response = await fetch(`${baseUrl}host/register`, {
//        method: "POST",
//        headers: {
//            "Content-Type": "application/json",
//        },
//        body: JSON.stringify(host)
//    });
//
//    if (response.ok) {
//        Swal.fire({
//                       icon: 'success',
//                       title: '管理員新增成功',
//                       text: '',
//                       confirmButtonText: '確定'
//    })}else {
//        alert("管理員新增失敗，請再次嘗試");
//    }
//}
//
//const regButton = document.getElementById("regButton");
//
//regButton.addEventListener("click", function() {
//
//    const hostEmail = document.getElementById("hostEmail").value;
//    const hostPassword = document.getElementById("hostPassword").value;
//    const hostName = document.getElementById("hostName").value;
//    const hostPhone = document.getElementById("hostPhone").value;
//
//
//
//
//    const host = {
//        "hostEmail": hostEmail,
//        "hostPassword": hostPassword,
//        "hostName": hostName,
//        "hostPhone": hostPhone
//
//
//    };
//
//    Register(host);
//});