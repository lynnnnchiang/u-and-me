//小bug 最末頁時下一頁要按兩次才能暗掉

const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me";
//全局變數
let page = 0;
let urlstr = '';

//元素
const groups_el = document.getElementById('groups');
const nextPage_el = document.getElementById('nextPage');
const prePage_el = document.getElementById('prePage');
const select_el = document.getElementById('select');
const sta0_el = document.getElementById('sta0');
const sta1_el = document.getElementById('sta1');
const sta2_el = document.getElementById('sta2');
const sta3_el = document.getElementById('sta3');
const name_btn_el = document.getElementById('name_btn');
const inputName_el = document.getElementById('inputName');
const detailContent_el = document.getElementById('detailContent');

window.addEventListener('load', function (e) {
    page = 0;
    fetchGroup(page, urlstr);
});


let count = 0;
async function fetchGroup(page, urlstr) {
    // try{
        let response = await fetch(baseUrl + '/member/groups/joined/' + urlstr + page);
        if(response.status == 401){
            Swal.fire({
                icon: 'error',
                title: '尚未登入',
                showCancelButton: true
            }).then(() => {
                this.location.href = baseUrl + '/tmp/Front/member/memberLogin.html';
            });
        }
        const groupList = await response.json();
        groups_el.innerHTML = '';
        count = 0;
        groupList.forEach(group => {
            let dataurl = `data:image/png;base64,${group.cover}`

            groups_el.innerHTML +=
                `
        <div class="favMenu">
            <div class="favMenu_left">
                <img src="../dist/img/groupFav.png" alt="">
            </div>

            <div class="favMenu_right">
                <div class="favMenuTittle" style="font-weight:bold;display: inline;">報名表編號:</div>
                <div class="favMenuTittleInput" style="display: inline; margin-left: 3px;">${group.form_Id}</div>
                <div></div>
                <div class="favMenuContent" style="font-weight:bold;display: inline;">揪團名稱: </div>
                <div class="favMenuContentInput" style="display: inline; margin-left: 18px;">${group.theme}</div>
                <div></div>
                <div class="favMenuTime" style="font-weight:bold;display: inline;">出發時間:</div>
                <div class="favMenuTimeInput" style="display: inline; margin-left: 18px;">${group.dep_Date}</div>
                <div style="margin-top: 20px; margin-right: 10px;">
                    <span class="moneyBack">
                        <button type="button" class="btn btn-outline-success btn-sm" id="memberDetail_btn" data-bs-toggle="modal" data-bs-target="#memberDetail" onclick="fetchDetail(${group.form_Id})">參團人員資訊</button>
                    </span>

                    <span class="showDetail111" style="margin-left: 190px;">
                        <button type="button" class="btn btn-outline-success btn-sm" id="viewMenuDetails" onclick="jump(${group.group_Id})">前往頁面</button>
                    </span>
                </div>
            </div>
        </div>
        `
            count++;
        });
    // }catch(error){
    //     console.log(error.response.status);
    // }
}

sta0_el.addEventListener('click', function (e) {
    e.preventDefault();
    select_el.innerHTML = '持續揪團中';
    urlstr = 'groupSta0/';
    page = 0;
    prePage_el.disabled = true;
    fetchGroup(page, urlstr);
});

sta1_el.addEventListener('click', function (e) {
    e.preventDefault();
    select_el.innerHTML = '揪團成功';
    urlstr = 'groupSta1/';
    page = 0;
    prePage_el.disabled = true;
    fetchGroup(page, urlstr);
});

sta2_el.addEventListener('click', function (e) {
    e.preventDefault();
    select_el.innerHTML = '揪團額滿';
    urlstr = 'groupSta5/';
    page = 0;
    prePage_el.disabled = true;
    fetchGroup(page, urlstr);
});

sta3_el.addEventListener('click', function (e) {
    e.preventDefault();
    select_el.innerHTML = '已下架揪團';
    urlstr = 'groupSta4/';
    page = 0;
    prePage_el.disabled = true;
    fetchGroup(page, urlstr);
});

name_btn_el.addEventListener('click', function (e) {
    e.preventDefault();
    urlstr = 'searchTheme=' + inputName_el.value + '/';
    page = 0;
    prePage_el.disabled = true;
    fetchGroup(page, urlstr);
})

nextPage_el.addEventListener('click', function (e) {
    let control = 0;

    if (count === 3) {
        page++;
    }

    if (count % 3 !== 0 || count === 0) {
        nextPage_el.disabled = true;
    }

    prePage_el.disabled = false;

    fetchGroup(page, urlstr);
});

prePage_el.addEventListener('click', function (e) {
    if (page >= 1) {
        page--;
    }
    if (page == 0) {
        prePage_el.disabled = true;
    }
    nextPage_el.disabled = false;
    fetchGroup(page, urlstr);
});

async function fetchDetail(formId) {
    let count = 0;
    try {
        const response = await fetch(baseUrl + '/member/memberDetailsForms/' + formId);
        const memberDetails = await response.json();
        detailContent_el.innerHTML = '';
        let refundSta = '';

        memberDetails.forEach(detail => {

            switch (detail.refundSta) {
                case 0:
                    refundSta = `<button type="button" class="btn btn-outline-success btn-sm" id="viewMenuDetails" onclick="window.location.href='${baseUrl}/tmp/Front/group/refund.html?detailId=${detail.detailId}'">退費申請</button>`;
                    break;
                case 1:
                    refundSta = `退款時間到期`;
                    break;
                case 2:
                    refundSta = `已申請退款`;
                    break;
                case 3:
                    refundSta = `退款已完成`;
                    break;
                case 4:
                    refundSta = `退款為0失敗`;
                    break;
            }

            count++;
            detailContent_el.innerHTML +=
                `
        <th scope="row">${count}</th>
            <td>${detail.name}</td>
            <td>${detail.idnumber}</td>
            <td>${detail.birthday}</td>
            <td>${detail.gender}</td>
            <td>${refundSta}</td>
        `
        })
    }catch(error){
        location.href = baseUrl + '/tmp/Front/member/memberLogin.html';
    }
}

function jump(groupId){
    window.open(`${baseUrl}/tmp/Front/group/groupMemo.html?gorupId=${groupId}`);
}



// pageSelect1_el.addEventListener('click', async function () {

//     //控制fetch傳入網址
//     page = 0
//     pageSelectControl(page);
//     fetchDetail(page, urlstr);
// });

// pageSelect2_el.addEventListener('click', async function () {
//     //控制fetch傳入網址
//     page = 1;
//     pageSelectControl(page);
//     fetchGroup(page, urlstr);
// });

// pageSelect3_el.addEventListener('click', async function () {
//     //控制fetch傳入網址
//     page = 2;
//     pageSelectControl(page);
//     fetchGroup(page, urlstr);
// });

// //控制分頁按鈕明暗的方法
// function pageSelectControl(e){
//     let n = page + 1;
//     switch(n){
//         case 1:
//             // //增加actice 使分頁亮起來
//             pageSelect1_el.classList.add("active");

//             // //刪除actice 使分頁暗下去
//             pageSelect2_el.classList.remove("active");

//             pageSelect3_el.classList.remove("active");
//             break;
//         case 2:
//             pageSelect1_el.classList.remove("active");

//             pageSelect2_el.classList.add("active");

//             pageSelect3_el.classList.remove("active");
//             break;
//         case 3:
//             pageSelect1_el.classList.remove("active");

//             pageSelect2_el.classList.remove("active");

//             pageSelect3_el.classList.add("active");
//             break;
//     }
// }