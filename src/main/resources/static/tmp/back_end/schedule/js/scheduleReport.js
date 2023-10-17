/* global bootstrap: false */
(function () {
  'use strict'
  var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  tooltipTriggerList.forEach(function (tooltipTriggerEl) {
    new bootstrap.Tooltip(tooltipTriggerEl)

    var dropdownToggleList = [].slice.call(document.querySelectorAll('[data-bs-toggle="dropdown"]'))
    dropdownToggleList.map(function (dropdownToggle) {
      return new bootstrap.Dropdown(dropdownToggle)
    });
  })
})()

// ===================== 管理員filter ===================== 
const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

window.addEventListener("load", function (e) {
  this.fetch(baseUrl + 'host/match', {
    method: 'GET'
  }).then(response => {
    if (response.status == 401) {
      this.location.href = baseUrl + 'tmp/back_end/host/hostLogin.html';
    }
  });
})

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

// ===================== 後台行程檢舉 =====================
const repSta_el = document.getElementById('repSta');// 檢舉處理選單
const selected_el = document.getElementById('selected');// 檢舉處理目前狀態
const schPub_el = document.getElementById('schPub');// 行程瀏覽權限處理選單
const existPub_el = document.getElementById('existPub');// 行程瀏覽權限目前狀態
const repId_el = document.getElementById('repId');
const memId_el = document.getElementById('memId');
const schId_el = document.getElementById('schId');
const schName_el = document.getElementById('schName');
const detail_el = document.getElementById('detail');
const repDesc_el = document.getElementById('repDesc');
const confirm_el = document.getElementById('confirm');
const selectSta_el = document.getElementById('selectSta');
const listAllReport_btn_el = document.querySelector('.listAllReport-btn');
let repIdStr = '';
let repId;
let schRepSta;
let schPub;
let schId;

// 網頁載入後執行，查出所有檢舉清單
document.addEventListener("DOMContentLoaded", function () {
  fetchScheRepList(`${baseUrl}schRep`);
});

// 按下所有檢舉查詢所有檢舉清單
listAllReport_btn_el.addEventListener('click', function () {
  fetchScheRepList(`${baseUrl}schRep`);
});

// 依照檢舉處理狀態查詢檢舉清單
selectSta_el.addEventListener('change', function () {
  let selectedSta = Number(selectSta_el.value);
  fetchScheRepList(`${baseUrl}schRep/status/${selectedSta}`);
})

// 查詢所有行程檢舉清單
async function fetchScheRepList(Url) {
  try {
    const response = await fetch(Url);
    const scheRepList = await response.json();

    const dataTableList = document.getElementById("dataTableList");
    dataTableList.innerHTML = "";

    scheRepList.forEach(async schRep => {

      const response2 = await fetch(`${baseUrl}schedules/schId/${schRep.schId}`)
      const sch = await response2.json();

      const row = document.createElement("tr");
      row.innerHTML = `
            <td >${schRep.schRepId}</td>
            <td >${schRep.memId}</td>
            <td >${sch.schName}</td>
            <td text-align: left;>${schRep.schRepCon}</td>
            <td >${formatHostId(schRep.hostId)}</td>
            <td >${statusMapping.get(schRep.schRepSta)}</td>
            <td >
            <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#exampleModal"
              onclick="fetchSchRep(${schRep.schRepId})">
              <i class="fa-solid fa-eye"></i>
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pen"
                viewBox="0 0 16 16">
                <path
                  d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z" />
              </svg>
            </button>
            </td>
            `;
      dataTableList.appendChild(row);
    });

  } catch (error) {
    console.error("Error fetching scheRep list:", error);
  }
}

// 查詢單一筆行程檢舉詳情
function fetchSchRep(schRepId) {
  repId = schRepId;
  fetch(baseUrl + 'schRep/getOne/' + schRepId, {
    method: 'GET'
  }).then(response => {
    return response.json();
  }).then(async rep => {
    switch (rep.schRepSta) {
      case 0:
        repStaStr = '目前狀態:審核中';
        break;
      case 1:
        repStaStr = '目前狀態:已處理';
        break;
      case 2:
        repStaStr = '目前狀態:已撤銷';
        break;
    }
    selected_el.innerText = repStaStr;
    repId_el.innerText = '檢舉編號：' + rep.schRepId;
    memId_el.innerHTML = '發起檢舉會員編號：' + rep.memId;
    schId_el.innerHTML = '行程編號：' + rep.schId;
    detail.innerHTML = `<button type="button" class="btn btn-outline-success" onclick="jump(${rep.schId})">查看行程詳情</button>`;
    repDesc_el.innerText = rep.schRepCon;
    schId = rep.schId;

    const response = await fetch(`${baseUrl}schedules/schId/${rep.schId}`)
    const schedule = await response.json();
    schPub = schedule.schPub;

    schName_el.innerHTML = '行程名稱：' + schedule.schName;
    existPub_el.innerText = `目前：${convertNumberToText(schPub)}`;

  })
}
// 將檢舉狀態文字轉換為數字
repSta_el.addEventListener('change', function () {
  schRepSta = Number(repSta_el.value);
})
// 將行程公開權限狀態轉換為數字
schPub_el.addEventListener('change', function () {
  schPub = Number(schPub_el.value);
})

confirm_el.addEventListener('click', function () {
  let data = {
    schId: schId,
    schRepSta: schRepSta
  }
  fetch(`${baseUrl}schRep/modifySta/${repId}/${schPub}`, {
    headers: {
      "content-type": "application/json",
    },
    method: 'PUT',
    body: JSON.stringify(data)
  })
  Swal.fire(
    '修改完成!',
    '已完成檢舉處理!',
    'success'
  ).then(() => {
    location.reload();
  })
});

// 檢舉狀態轉換
const statusMapping = new Map([
  [0, '審核中'],
  [1, '已處理'],
  [2, '已撤銷'],
]);

// 查看被檢舉的行程詳情
function jump(schId) {
  window.open(baseUrl + "tmp/Front/schedule/myScheduleEdit.html?schId=" + schId);
}

// 行程分享權限轉換
function convertNumberToText(number) {
  switch (number) {
    case 0:
      return "私人檢視";
    case 1:
      return "連結分享";
    case 2:
      return "公開瀏覽";
  }
}

// 管理員為空值
function formatHostId(hostId) {
  // 假設 data 是可能包含 null 的變數
  let result = (hostId !== null) ? hostId : "尚無資料";
  return result;
}

// ===================== 後台行程檢舉 =====================