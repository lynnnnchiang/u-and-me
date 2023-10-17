// 判斷會員是否登錄
function memberLogin() {

  var baseUrl = window.location.protocol + "//" + window.location.host;

  $.ajax({
    url: baseUrl + "/u-and-me/member/getMemId",   //-->判斷會員是否登入，有會員的資料
    method: "POST",
    dataType: "JSON",
    success: function (data) {
      userName = data.memName
      userId = data.memId;
      if (userId !== null) {
        connect();
      }
    },
    error: function (status, error) {
      if (status.status === 401) {
        Swal.fire({
          title: '尚未登入',
          text: "想返回登入畫面嗎?",
          icon: 'error',
          showCancelButton: true,
          cancelButtonText: '取消',
          cancelButtonColor: '#d33',
          confirmButtonText: '返回登入畫面'
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = baseUrl + '/u-and-me/tmp/Front/member/memberLogin.html';
          }
        });

      }
    },
  });

}


//連接websocket
let userId = '';
let userName = '';
const ServerPoint = `/chat/${userName}`;
const lohost = window.location.host;
const path = window.location.pathname;
const webCtx = path.substring(0, path.indexOf("/", 1));
const endPointURL = "ws://" + lohost + webCtx + ServerPoint;

const msgContainer = document.querySelector("#msgContainer");
let webSocket;

let con = false;
let chatbox = document.querySelector("#chatBox"); // 點擊聊天圖示 (#chat-icon) 時才會開啟連線
document.querySelector("#chat-icon").addEventListener("click", function () {

  if (userName.trim() === '') {
    memberLogin();
  } else {
    // memberLogin();
    chatbox.classList.toggle("hide");
    msgContainer.scrollTop = msgContainer.scrollHeight;
    if (!document.querySelector("#alert").classList.contains("hide")) {
      document.querySelector("#alert").classList += " hide";
    }
    if (con === false) {
      connect();
    }

  }

});

document.querySelector("#close").addEventListener("click", function () {
  chatbox.classList += " hide";
});

const user = '<div class="col-md-2 col-xs-2 avatar">' + '<img src="../dist/img/chat/kittybleft.png" class="img-responsive_self">' + "</div>";
const hoster = '<div class="col-md-2 col-xs-2 avatar">' + '<img src="../dist/img/chat/customer-service.png" class="img-responsive_user">' + "</div>";

let isEmpOline = true;
function connect() {

  // 建立 websocket
  webSocket = new WebSocket(endPointURL + userName);
  webSocket.onopen = function (event) {
    // 初始化連線，只會連線一次
    console.log("Connect Success!");
    con = true;
    let jsonObj = {
      type: "openChatRoom",
      sender: userName,
      receiver: userName,
    };
    webSocket.send(JSON.stringify(jsonObj));
  }

  webSocket.onmessage = function (event) {
    let data = JSON.parse(event.data);
    console.log(data);

    // 一般訊息
    if (data.type === 0) {
      buildMessage(data.data);
    }

    // 建立聊天室清單
    if (data.type === 1) {
      buildChatRoomList(data.data);
    }


    //歷史訊息
    if (data.type === 2) {
      buildHisMessage(data.data);
    }

    // 客服上線
    if (data.type === 3) {
      isEmpOline = true;
      buildHisMessage(data.data);
    }

    // 客服不在線上
    if (data.type === 4) {
      isEmpOline = false;
      buildOfflineMessage();
    }
  }
}

//-- input 欄位按Enter(keycode:13)傳送訊息出去 --//
$("#btn-input").on("keydown", function (e) {
  if (e.which === 13) {
    $("#btn-chat").click();
  }
});

function sendMessage() {
  let inputMessage = document.getElementById("btn-input");
  let message = inputMessage.value.trim();

  if (message === "") {
    alert("您未輸入訊息");
    inputMessage.focus();
  } else {
    let now = new Date();
    let nowStr = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
    let jsonObj = {
      type: "message",
      sender: userName,
      receiver: "host",
      message: message,
      time: nowStr,
      status: "unread",
    };
    webSocket.send(JSON.stringify(jsonObj));
    inputMessage.value = "";
    inputMessage.focus();
    buildMessage(jsonObj);
  }
}

function buildHisMessage(data) {
  msgContainer.innerHTML = "";
  // 這行的jsonObj.message是從redis撈出跟客服的歷史訊息，再parse成JSON格式處理
  let messages = data;
  for (let i = 0; i < messages.length; i++) {
    let div = document.createElement("div");
    div.className = "row msg_container";
    let historyData = JSON.parse(messages[i]);
    let showMsg = historyData.message;
    let time = historyData.time;
    let content = "";
    // 根據發送者是自己還是對方來給予不同的html
    if (historyData.sender === userName) {
      content =
        '<div class="col-md-10 col-xs-10">' +
        '<div class="messages_self msg_receive_self">' +
        '<p>' + showMsg + '</p>' +
        '<time>' + `${userName} - ` + time +
        '</time></div></div>' +
        user;
    } else {
      content =
        hoster +
        '<div class="col-md-10 col-xs-10">' +
        '<div class="messages_user msg_receive_user">' +
        '<p>' + showMsg + '</p>' +
        '<time>客服 - ' + time +
        '</time></div></div>';
    }
    div.innerHTML = content;
    msgContainer.appendChild(div);
  }
  msgContainer.scrollTop = msgContainer.scrollHeight;
}

function buildMessage(data) {
  let div = document.createElement("div");
  div.className = "row msg_container";
  let jsonObj = data;
  let showMsg = jsonObj.message;
  let time = jsonObj.time;
  let content = "";
  // 根據發送者是自己還是對方來給予不同的html
  if (jsonObj.sender === userName) {
    content =
      '<div class="col-md-10 col-xs-10">' +
      '<div class="messages_self msg_receive_self">' +
      '<p>' + showMsg + '</p>' +
      '<time>' + `${userName} - ` + time +
      '</time></div></div>' +
      user;
  } else {
    content =
      hoster +
      '<div class="col-md-10 col-xs-10">' +
      '<div class="messages_user msg_receive_user">' +
      '<p>' + showMsg + '</p>' +
      '<time>客服 - ' + time +
      '</time></div></div>';
  }
  div.innerHTML = content;
  msgContainer.appendChild(div);
  if (chatbox.classList.contains("hide")) {
    document.querySelector("#alert").classList.toggle("hide");
  } else {
    msgContainer.scrollTop = msgContainer.scrollHeight;
  }
}

function buildOfflineMessage() {
  let div = document.createElement("div");
  div.className = "row msg_container";
  let now = new Date();
  let time = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
  let content =
    hoster +
    `<div class="col-md-10 col-xs-10">
    <div class="messages_user msg_receive_user">
    <p>您好，目前客服未在線～！請留下想詢問的問題，將於營業時間回覆您(❀╹◡╹)</p>
    <time>客服 - ${time}</time>
    </div>
    </div>`;
  div.innerHTML = content;
  msgContainer.appendChild(div);
  msgContainer.scrollTop = msgContainer.scrollHeight;
}
