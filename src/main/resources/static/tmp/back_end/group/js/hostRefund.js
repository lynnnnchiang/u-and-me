const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

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
//查詢所有的QA編號
function findqalist() {
    //ajax區塊
    $.ajax({
        url: baseUrl + "memberDetailsAll", //請求動態網址
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
                    { data: 'detailId' },               // targets[0]
                    { data: 'formId' },            // targets[1]
                    { data: 'name' },            // targets[2]
                    { data: 'reason' },      // targets[3]
                    { data: 'account' },      // targets[4]
                    { data: 'refund' },      // targets[5]
                    { data: 'refundDate' },      // targets[6]
                    { data: 'refundSta' },  // targets[7]
                    { data: null }                  // targets[8] 操作欄位
                ],
                // 欄位元素定義區塊(columnDefs)，依需求決定內容可加可不加。
                columnDefs: [
                    {
                        //調整th:qaId欄位的寬度
                        targets: [0],
                        width: "50px"
                    }, {
                        targets: [2],
                        render: function (data, type, row) {
                            //點擊qaTitle跳出檢視單筆資料視窗。
                            $(document).on("click", `a.qalink${row.detailId}`, function () {

                                let refundSta = '';
                                //將揪團狀態更改中文
                                switch (row.refundSta) {
                                    case 0:
                                        refundSta = '未申請退款';
                                        break;
                                    case 1:
                                        refundSta = '退款時間到期';
                                        break;
                                    case 2:
                                        refundSta = '已申請退款';
                                        break;
                                    case 3:
                                        refundSta = '退款完成';
                                        break;
                                    case 4:
                                        refundSta = '退款失敗';
                                        break;
                                }

                                //彈跳視窗抓取單筆值。
                                Swal.fire({
                                    title: '<span> 檢視QA資料 </span><hr>',
                                    html: `
                                            <div class="oneqa">
                                                <div class="oneqa">資料編號 :  <span>${row.detailId}</span></div>
                                                <div class="oneqa">報名表編號 :  <span>${row.formId}</span></div>
                                                <div class="oneqa">姓名 :  <span>${row.name}</span></div>
                                                <div class="oneqa">退款緣由 :  <span>${row.reason}</span></div>
                                                <div class="oneqa">銀行帳戶 :  <span>${row.account}</span></div>
                                                <div class="oneqa">退款金額 :  <span>${row.refund}</span></div>
                                                <div class="oneqa">退款時間 :  <span>${row.refundDate}</span></div>
                                                <div class="oneqa">退款狀態 :  <span>${refundSta}</span></div>
                                            </div>
                                            `,
                                    confirmButtonText: '確認'
                                })

                            });
                            return `<a class="link-primary qalink${row.detailId}" >` + data + '</a>'
                        }
                    }, {
                        //將qaState值改成中文顯示，狀態 = 0:下架、1:上架。
                        //   targets: [2],
                        //   render: function (data) {
                        //     if (data === 0) {
                        //       return '下架'
                        //     } else {
                        //       return '上架'
                        //     }
                        //   }
                    }, {
                        //在操作欄位回傳按鈕。
                        targets: [8],
                        render: function (row) {
                            return `<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="fetchSta(${row.detailId})">更改狀態</button>`
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



let detailId;
const rId_el = document.getElementById('rId');
const rSta_el = document.getElementById('rSta');
const rStaVal_el = document.getElementById('rStaVal');
const confirm_el = document.getElementById('confirm');

async function fetchSta(id) {
  detailId = id;
  await fetch(baseUrl + 'memberDetail/' + id, {
    method: 'GET',
  }).then(response => {
    return response.json();
  }).then(detail => {
    let refundSta = '';
    switch (detail.refundSta) {
        case 0:
            refundSta = '原本狀態 0: 未申請退款';
            break;
        case 1:
            refundSta = '原本狀態 1: 退款時間到期';
            break;
        case 2:
            refundSta = '原本狀態 2: 已申請退款';
            break;
        case 3:
            refundSta = '原本狀態 3: 退款完成';
            break;
        case 4:
            refundSta = '原本狀態 4: 退款失敗';
            break;
    }

    rId_el.innerHTML = 'DetailId: ' + detail.detailId + '<br>姓名: ' + detail.name;
    rSta_el.innerHTML = refundSta;
  })
}

let rSta = null;
function getRSta(){
  rSta = rStaVal_el.value;
}


confirm_el.addEventListener('click', async function (e) {
  if (rStaVal_el.value != null) {
    const data ={
      refundSta: rSta,
    }

    await fetch(baseUrl + 'updRefundSta/' + detailId, {
      headers: {
        "content-type": "application/json",
      },
      method: 'PUT',
      body: JSON.stringify(data)
    }).catch(function (e){
      Swal.fire({
        icon: 'error',
        title: '更新失敗',
        text: '未修改狀態',
        showCancelButton: true
      });
    })
  }else{
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