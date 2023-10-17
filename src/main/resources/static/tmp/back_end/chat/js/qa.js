

const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
// <!--網頁載入後執行-->


window.addEventListener("load", function (e) {
  this.fetch(baseUrl + 'host/match', {
    method: 'GET'
  }).then(response => {
    if (response.status == 401) {

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
    url: baseUrl+"qas", //請求動態網址
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
          { data: 'qaId' },               // targets[0]
          { data: 'qaTitle' },            // targets[1]
          { data: 'qaState' },            // targets[2]
          { data: 'qaCreatedTime' },      // targets[3]
          { data: 'qaLastUpdatedTime' },  // targets[4]
          { data: null }                  // targets[5] 操作欄位
        ],
        // 欄位元素定義區塊(columnDefs)，依需求決定內容可加可不加。
        columnDefs: [
          {
            //調整th:qaId欄位的寬度
            targets: [0],
            width: "50px"
          }, {
            targets: [1],
            render: function (data, type, row) {
              //點擊qaTitle跳出檢視單筆資料視窗。
              $(document).on("click", `a.qalink${row.qaId}`, function () {

                //將qaState值改成中文顯示，狀態 = 0:下架、1:上架。
                if (row.qaState === 0) {
                  row.qaState = '下架';
                } else if (row.qaState === 1) {
                  row.qaState = '上架';
                }

                //彈跳視窗抓取單筆值。
                Swal.fire({
                  title: '<span> 檢視QA資訊 </span><hr>',
                  html: `
                  <div class="oneqa">
                    <div class="oneqa">QA編號 :  <span>${row.qaId}</span></div>
                    <div class="oneqa">標題 :  <span>${row.qaTitle}</span></div>
                    <div class="oneqa">內容 :  <br><span>${row.qaCon}</span></div>
                    <div class="oneqa">狀態 :  <span class="state">${row.qaState}</span></div>
                    <div class="oneqa">建立時間 :  <span>${row.qaCreatedTime}</span></div>
                    <div class="oneqa">最後編輯時間 :  <span>${row.qaLastUpdatedTime}</span></div>
                  </div>
                  `,
                  confirmButtonText: '確認'
                })

              });
              return `<a class="link-primary qalink${row.qaId}" >` + data + '</a>'
            }
          }, {
            //將qaState值改成中文顯示，狀態 = 0:下架、1:上架。
            targets: [2],
            render: function (data) {
              if (data === 0) {
                return '下架'
              } else {
                return '上架'
              }
            }
          }, {
            //在操作欄位回傳按鈕。
            targets: [5],
            render: function (data, type, row) {

              $(document).on("click", `button.upd${row.qaId}`, function () {

                return window.location.href = baseUrl + `tmp/back_end/chat/updqa.html?qaId=${row.qaId}`;

              })

              $(document).on("click", `button.del${row.qaId}`, function () {

                Swal.fire({
                  title: '刪除資料',
                  text: "確定刪除此筆資料嗎?",
                  icon: 'warning',
                  showCancelButton: true,
                  confirmButtonColor: '#3085d6',
                  cancelButtonColor: '#d33',
                  cancelButtonText: '取消',
                  confirmButtonText: '確定刪除'
                }).then((result) => {

                  if (result.isConfirmed) {
                    fetch(`/u-and-me/delqa/${row.qaId}`, {
                      method: 'Delete',
                      headers: { 'Content-Type': 'application/json' }
                    }).catch(function () {
                      Swal.fire({
                        title: '刪除失敗',
                        icon: 'error'
                      })
                    })
                    Swal.fire({
                      title: '刪除成功',
                      icon: 'success'
                    }).then(function () {
                      location.reload();
                    })
                  }

                })

              })

              return `<button type="button" class="btn btn-primary btn-sm upd${row.qaId}">編輯</button> ` +
                `<button type="button" class="btn btn-danger btn-sm del${row.qaId}">刪除</button>`
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

// 登出按鈕
const logoutBtn_el = document.getElementById("logOut");
logoutBtn_el.addEventListener("click", async function () {
  const response = await fetch(`${baseUrl}host/hostLogout`, {
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

