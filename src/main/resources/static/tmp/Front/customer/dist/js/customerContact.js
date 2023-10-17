//綁定
let name_val = document.querySelector("#username");
let errorname = document.getElementById("errorname");

let mail_val = document.querySelector("#usermail");
let errormail = document.getElementById("errormail");

let mesg_val = document.querySelector("#mesg");
let errormesg = document.getElementById("errormesg");

let sendbtn = document.getElementById("sendbtn");
var baseUrl = window.location.protocol + "//" + window.location.host;

//點擊新增按鈕
sendbtn.addEventListener("click", function () {

  //錯誤驗證
  if (name_val.value.trim() === "") {
    errorname.innerHTML = '姓名不可空白!';
    return;
  }
  if (mail_val.value.trim() === "") {
    errormail.innerHTML = 'Email不可空白!';
    return;
  }
  if (mesg_val.value.trim() === "") {
    mesg_val.classList.toggle("is-invalid");
    errormesg.innerHTML = 'Message不可空白!';
    return;
  }


  //送出物件
  fetch(baseUrl + '/u-and-me/contact', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      name: name_val.value,
      usermail: mail_val.value,
      content: mesg_val.value
    })
  }).catch(function () {
    Swal.fire({
      title: '寄信失敗',
      icon: 'error'
    })

  })
  Swal.fire({
    title: '寄信成功',
    text:'感謝您的回饋及留言~!',
    icon: 'success'
  }).then(function () {
    location.reload();
  })

})