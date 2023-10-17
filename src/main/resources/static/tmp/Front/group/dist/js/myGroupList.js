let count = 0;
const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me";
const regFormList_el = document.getElementById("regFormList");

// 網頁載入後執行
document.addEventListener("DOMContentLoaded", function () {
    fetchMyGroupList();
});

async function fetchMyGroupList() {
    try {
        const response = await fetch(baseUrl + '/member/grouper/myGroupList/0');
        if(response.status == 401){
            Swal.fire({
                icon: 'error',
                title: '尚未登入',
                showCancelButton: true
            }).then(()=>{
                location.href = baseUrl + '/tmp/Front/member/memberLogin.html';
            });
        }else if(response.status == 403){
            Swal.fire({
                icon: 'error',
                title: '尚未成為團主',
                showCancelButton: true
            }).then(()=>{
                location.href = baseUrl + '/tmp/Front/member/memberGroupRegister.html';
            });
        }
        const myGroupList = await response.json();
        const myGroupList_el = document.getElementById("myGroupList");

        myGroupList_el.innerHTML = "";

        myGroupList.forEach(group => {
            let group_Sta = "";
            count++;

            switch (group.group_Sta) {
                case 0:
                    group_Sta = "揪團中";
                    break;
                case 1:
                    group_Sta = "揪團成功";
                    break;
                case 2:
                    group_Sta = "揪團取消";
                    break;
                case 3:
                    group_Sta = "揪團下架"; 
                    break;
                case 4:
                    group_Sta = "揪團被下架";
                    break;
                case 5:
                    group_Sta = '揪團額滿';
                    break;
            }

            let paySta = '';
            switch (group.payment_Sta){
                case 0:
                    paySta = "未到請款時間";
                    break;
                case 1:
                    paySta = "可撥款";
                    break;
                case 2:
                    paySta = "團員退款中";
                    break;
                case 3:
                    paySta = "撥款完成";
                    break;
            }

            const row = document.createElement("tr");
            row.innerHTML = `
                <td scope="row" style="text-align: center;vertical-align: middle; width: 60px" id="id${count}">${group.group_Id}</td>
                <td style="text-align: center;vertical-align: middle; width: 125px">${group.theme}</td>
                <td style="text-align: center;vertical-align: middle; width: 60px"><a href="${baseUrl}/tmp/Front/schedule/myScheduleEdit.html?schId=${group.sch_Id}" rel="noreferrer noopenner">行程</a></td>
                <td style="text-align: center;vertical-align: middle; width: 60px"><a href="${baseUrl}/tmp/Front/group/groupMemo.html?schId=${group.group_Id}" rel="noreferrer noopenner">資料</a></td>
                <td style="text-align: center;vertical-align: middle; width: 75px""><button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#regForm" id="regForm_btn${count}" onclick="fetchRegForm(${group.group_Id})">
                    點此</button></td>
                <td style="text-align: center;vertical-align: middle; width: 90px">${group_Sta}</td>
                <td style="text-align: center;vertical-align: middle; width: 125px">${paySta}</td>
                <td>
                    <button class="btn btn-outline-success  width: 70px" onclick="window.location.href='http://localhost:8080/u-and-me/tmp/Front/group/myGroupListUpdate.html?gorupId=${group.group_Id}'" type="button">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"fill="currentColor" class="bi bi-pen" viewBox="0 0 16 16">
                        <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"/>
                        </svg>
                    </button>
                </td>
                <td style="text-align: center;vertical-align: middle; width: 50px">
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" role="switch" id="launched${count}" checked onclick="changeGroupSta(${group.group_Id}, ${count})">
                    </div>
                </td>
                <td>
                    <button class="btn btn-outline-success id="delete" width: 70px" onclick="deleteGroup(${group.group_Id})" type="button">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"/>
                            <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"/>
                        </svg>
                    </button>
               </td>
            `;

            myGroupList_el.appendChild(row);

            let launched_el = document.getElementById(`launched${count}`);
            if(group.group_Sta != 0 && group.group_Sta != 1){
                launched_el.checked = false;
                launched_el.disable = true;
            }
        });

    } catch (error) {
        console.error("Error fetching GroupList:", error);
    }
}


function formatDate(dateString) {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString('zh-TW', options);
}

async function deleteGroup(groupId) {

    //利用try catch 跳脫foreach循環
    try {
        // let r = confirm('確定刪除？');
            Swal.fire({
              title: '確認刪除揪團?',
              text: "刪除後無法回復",
              icon: 'warning',
              showCancelButton: true,
              cancelButtonText:'取消',
              cancelButtonColor:'#d33',
              confirmButtonText:'刪除'
            }).then((result) => {
              if(result.isConfirmed) {
                fetch(baseUrl + '/member/grouper/group/' + groupId, {
                    method: 'DELETE'
                }).then(response => {
                    return response.text();
                }).then(body => {
                    if(body == 'success'){
                        Swal.fire({
                            icon: 'success',
                            title: '刪除成功',
                            text: '已修改狀態',
                            showCancelButton: true
                          })
                        throw new Error();
                    }else{
                        Swal.fire({
                            icon: 'error',
                            title: '刪除失敗',
                            text: '需等待團員退款完畢才可以刪除',
                            showCancelButton: true
                          })
                        throw new Error();
                    }
                });
              }
            });
        }catch (e) {
        // window.location.reload();
        }
    }

let j = 0;
let formId = [];
async function fetchRegForm(groupId) {
    const response = await fetch(baseUrl + '/member/regForms/findGroupId' + groupId);
    const formList = await response.json();
    formContent.innerHTML = '';
    j = 0;
    formList.forEach(form => {
        formId.push(form.formId);
        j++;
        formContent.innerHTML +=
            `
        <p>
            <span style="font-size: 18px;" >報名表編號: ${form.formId} ｜ 會員編號: ${form.memId} ｜ 電子信箱: ${form.email} ｜ 電話號碼: ${form.phone} ｜ 參加人數: ${form.joinMember}</span>
        </p>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col">姓名</th>
                            <th scope="col">身分證字號</th>
                            <th scope="col">出生日期</th>
                            <th scope="col">性別</th>
                            <th scope="col">付款狀態</th>
                            <th scope="col">退款金額</th>
                        </tr>
                    </thead>
                    <tbody id="detailContent${j}">
                    </tbody>
                </table>
        <hr>
        `;
    });
    for (let i = 1; i <= j; i++) {
        let detailContent_el = document.getElementById('detailContent' + i);
        detailContent_el.innerHTML = '';
    }
    for (let i = 1; i <= j; i++) {

        
        const res = await fetch(baseUrl + '/member/memberDetailsForms/' + formId.shift());
        //取値後必須移除才不會導致重複讀取 shift() > 從數組中取値後刪除
        const detailList = await res.json();
        
        let detailContent_el = document.getElementById('detailContent' + i);
        let refundSta = '';
        let refund;
        detailContent_el.innerHTML = '';
        detailList.forEach(detail => {
            

            switch(detail.refundSta){
                case 0:
                    refundSta = '已完成付款';
                    refund = '無須退款';
                    break;
                case 1:
                    refundSta = '退費期限到期';
                    refund = '無須退款';
                    break;
                case 2:
                    refundSta = '退款申請中';
                    refund = '申請' + detail.refund + '元';
                    break;
                case 3:
                    refundSta = '退款完成';
                    refund = '已退款' + detail.refund + '元';
                    break;
                case 4:
                    refundSta = '已完成付款';
                    refund = '無須退款';
                    break;
            }
            
            detailContent_el.innerHTML +=
                `<tr>
            <th scope="row">${detail.name}</th>
            <td>${detail.idnumber}</td>
            <td>${detail.birthday}</td>
            <td>${detail.gender}</td>
            <td>${refundSta}</td>
            <td>${refund}</td>
        </tr>
        `
        });

    }
    
}

async function changeGroupSta(gorupId, count){
    let launched_el = document.getElementById('launched'+count);
    let data;
    switch(launched_el.checked){
        case true:
            data ={
                groupSta: 0
            }
            break;
        case false:
            data ={
                groupSta: 3
            }
            break;
    }
    await fetch(baseUrl + '/member/grouper/group/updateGroupSta/' + gorupId,{
        headers: {
            "content-type": "application/json",
        },
        method: 'PUT',
        body: JSON.stringify(data)
    }).then(()=>{
        location.reload();
    }).catch(function (e){
        Swal.fire({
            icon: 'error',
            title: '更新失敗',
            text: '已有成員參加揪團 無法下架',
            showCancelButton: true
          }).then(()=>{
            // location.reload();
          });
    });
    
}
// for(let i = 1; i <= count; i++){
//     let launched_el = document.getElementById('launched' + i);
    
//     launched_el.addEventListener('change', function(e){
//         switch(launched_el.cheaked){
//             case true:

//         }
//     })
// }

// async function fetchMemberDetail(formId){
//     const response = await fetch('http://localhost:8080/u-and-me/memberDetailsForms/' + formId);
//     const detailList = await response.json();
//     const detailContent_el = document.getElementById('detailContent');
//     detailContent_el.innerHTML = '';

//     detailList.forEach(detail => {
//         detailContent_el.innerHTML +=
//         `<tr>
//             <th scope="row">${detail.name}</th>
//             <td>${detail.idnumber}</td>
//             <td>${detail.birthday}</td>
//             <td>${detail.gender}</td>
//         </tr>
//         `
//     })
// }   