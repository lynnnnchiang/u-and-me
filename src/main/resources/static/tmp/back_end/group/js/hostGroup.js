const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
let id;

const gId_el = document.getElementById('gId');
const gSta_el = document.getElementById('gSta');
const pSta_el = document.getElementById('pSta');
const pStaVal_el = document.getElementById('pStaVal');
const gStaVal_el = document.getElementById('gStaVal');
const confirm_el = document.getElementById('confirm');


// 管理員filter
// <!--網頁載入後執行-->


window.addEventListener("load", function (e) {
this.fetch(baseUrl + 'host/match', {
    method: 'GET'
}).then(response => {
    if(response.status == 401){

            this.location.href = baseUrl + 'tmp/back_end/host/hostLogin.html';

    }
});
})

/* global bootstrap: false */
$(function () {
  'use strict'
  var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  tooltipTriggerList.forEach(function (tooltipTriggerEl) {
    new bootstrap.Tooltip(tooltipTriggerEl)

    var dropdownToggleList = [].slice.call(document.querySelectorAll('[data-bs-toggle="dropdown"]'))
    dropdownToggleList.map(function (dropdownToggle) {
      return new bootstrap.Dropdown(dropdownToggle)
    });
  })

  findqalist();

});



//查詢所有的QA編號
function findqalist() {
  //ajax區塊
  $.ajax({
    url: baseUrl + "groups", //請求動態網址
    contentType: 'application/json; charset=UTF-8',
    data: {}, //請求獲取全部無參數
    dataType: "json",
    success: function (data) {
      // 綁定tableId，使用 jQuery Table 來動態生成表格
      $('#qatable').DataTable({
        //抓取請求的資料
        data: data,
        //搜尋欄位是否開啟及設定
        searching: true,
        searchDelay: 500,
        //資料欄位區塊(columns)
        columns: [
          { data: 'groupId' },               // targets[0]
          { data: 'theme' },            // targets[1]
          { data: 'memId' },            // targets[2]
          { data: 'amount' },            // targets[3]
          { data: 'members' },            // targets[4]
          { data: 'groupSta' },      // targets[5]
          { data: 'paymentSta' },      // targets[6]
          { data: 'depDate' },      // targets[7]
          { data: 'deadline' },  // targets[8]
          { data: null }                  // targets[9] 操作欄位
        ],
        // 欄位元素定義區塊(columnDefs)，依需求決定內容可加可不加。
        columnDefs: [
          {
            //調整th:qaId欄位的寬度
            targets: [0],
            width: "50px",
            targets: [2],
            width: "50px"
          }, {
            targets: [1],
            render: function (data, type, row) {
              //點擊qaTitle跳出檢視單筆資料視窗。
              $(document).on("click", `a.qalink${row.groupId}`, function () {

                //將qaState值改成中文顯示，狀態 = 0:下架、1:上架。
                // if (row.qaState === 0) {
                //   row.qaState = '下架';
                // } else if (row.qaState === 1) {
                //   row.qaState = '上架';
                // }

                let groupSta = '';
                //將揪團狀態更改中文
                switch (row.groupSta) {
                  case 0:
                    groupSta = '揪團中';
                    break;
                  case 1:
                    groupSta = '揪團成功';
                    break;
                  case 2:
                    groupSta = '揪團取消';
                    break;
                  case 3:
                    groupSta = '揪團下架';
                    break;
                  case 4:
                    groupSta = '揪團被下架';
                    break;
                  case 5:
                    groupSta = '揪團額滿';
                    break;
                }

                let paymentSta = '';
                switch (row.paymentSta) {
                  case 0:
                    paymentSta = '未到請款時間';
                    break;
                  case 1:
                    paymentSta = '可撥款';
                    break;
                  case 2:
                    paymentSta = '退款中';
                    break;
                  case 3:
                    paymentSta = '撥款完成';
                    break;
                }

                //彈跳視窗抓取單筆值。
                Swal.fire({
                  title: '<span> 檢視揪團資料 </span><hr>',
                  html: `
                  <div class="oneqa">
                    <div class="oneqa">揪團編號 :  <span>${row.groupId}</span></div>
                    <div class="oneqa">標題 :  <span>${row.theme}</span></div>
                    <div class="oneqa">團主編號 :  <span>${row.memId}</span></div>
                    <div class="oneqa">金額 :  <span>${row.amount}</span></div>
                    <div class="oneqa">參團人數 :  <span>${row.members}</span></div>
                    <div class="oneqa">最小人數 :  <span>${row.minMember}</span></div>
                    <div class="oneqa">揪團狀態 :  <span>${groupSta}</span></div>
                    <div class="oneqa">尾款狀態 :  <span>${paymentSta}</span></div>
                    <div class="oneqa">出團日期 :  <span>${row.depDate}</span></div>
                    <div class="oneqa">截止日期 :  <span>${row.deadline}</span></div>
                  </div>
                  `,
                  confirmButtonText: '確認'
                })

              });
              return `<a class="link-primary qalink${row.groupId}" >` + data + '</a>'
            }
          }, {
            // 將qaState值改成中文顯示，狀態 = 0:下架、1:上架。
            // targets: [5],
            // render: function (data) {
            //   let groupSta1 = '';
            //     //將揪團狀態更改中文
            //     switch(data){
            //       case 0:
            //         groupSta1 = '揪團中';
            //         break;
            //       case 1:
            //         groupSta1 = '揪團成功';
            //         break;
            //       case 2:
            //         groupSta1 = '揪團取消';
            //         break;
            //       case 3:
            //         groupSta1 = '揪團延期';
            //         break;
            //       case 4:
            //         groupSta1 = '揪團被下架';
            //         break;
            //       case 5:
            //         groupSta1 = '揪團額滿';
            //         break;
            //     }
            // },
            // targets: [6],
            // render: function (data) {
            //   let paymentSta = '';
            //     switch(data){
            //       case 0:
            //         paymentSta = '未到請款時間';
            //         break;
            //       case 1:
            //         paymentSta = '可撥款';
            //         break;
            //       case 2:
            //         paymentSta = '退款中';
            //         break;
            //       case 3:
            //         paymentSta = '撥款完成';
            //         break;
            //     }
            // }
          }, {
            //在操作欄位回傳按鈕。
            targets: [9],
            render: function (row) {
              return `<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="fetchSta(${row.groupId})">更改狀態</button>
                      <br>
                      <button type="button" class="btn btn-secondary" onclick="jump(${row.groupId})">更改揪團</button>`
            }
          }, {
            //將全部欄位的字置中。
            targets: '_all',
            className: 'text-center'  
          }
        ],
        //語言區塊:將預設英文配置改成中文顯示。
        language: {
          url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/zh_Hant.json"
        }
      });
    },
    error: function () {
      alert('Failed to fetch data from server.');
    }
  });
}

let groupId;
async function fetchSta(id) {
  groupId = id;
  await fetch(baseUrl + 'group/' + id, {
    method: 'GET',
  }).then(response => {
    return response.json();
  }).then(group => {
    let groupSta = '';
    switch (group.groupSta) {
      case 0:
        groupSta = '原本狀態 0: 揪團中';
        break;
      case 1:
        groupSta = '原本狀態 1: 揪團成功';
        break;
      case 2:
        groupSta = '原本狀態 2: 揪團取消';
        break;
      case 3:
        groupSta = '原本狀態 3: 揪團延期';
        break;
      case 4:
        groupSta = '原本狀態 4: 揪團被下架';
        break;
      case 5:
        groupSta = '原本狀態 5: 揪團額滿';
        break;
    }

    let paymentSta = '';
    switch (group.paymentSta) {
      case 0:
        paymentSta = '原本狀態 0: 未到請款時間';
        break;
      case 1:
        paymentSta = '原本狀態 1: 可撥款';
        break;
      case 2:
        paymentSta = '原本狀態 2: 退款中';
        break;
      case 3:
        paymentSta = '原本狀態 3: 撥款完成';
        break;
    }

    gId_el.innerHTML = 'GroupId: ' + group.groupId + '<br>揪團名稱: ' + group.theme;
    gSta_el.innerHTML = groupSta;
    pSta_el.innerHTML = paymentSta;
  })
}

function jump(groupId){
  window.open(baseUrl + 'tmp/back_end/group/myGroupListUpdate.html?groupId=' + groupId);
}

let gSta = null;
function getGSta() {
  gSta = gStaVal_el.value;
}

let pSta = null;
function getPSta() {
  pSta = pStaVal_el.value;
}

confirm_el.addEventListener('click', async function (e) {
  if (gStaVal_el.value != null || pStaVal_el.value != null) {
    const data = {
      groupSta: gSta,
      paymentSta: pSta
    }

    await fetch(baseUrl + 'group/updateGStaPSta/' + groupId, {
      headers: {
        "content-type": "application/json",
      },
      method: 'PUT',
      body: JSON.stringify(data)
    }).catch(function (e) {
      Swal.fire({
        icon: 'error',
        title: '更新失敗',
        text: '未修改狀態',
        showCancelButton: true
      })
    })
  } else {
    return;
  }
  Swal.fire({
    icon: 'success',
    title: '更新成功',
    text: '已修改狀態',
    showCancelButton: true
  })
})

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