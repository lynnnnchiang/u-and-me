//===================使用到的元素===================
const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me";
const cover_img_el = document.getElementById("cover_img");
const img1_el = document.getElementById("img1");
const img2_el = document.getElementById("img2");
const img3_el = document.getElementById("img3");
const img4_el = document.getElementById("img4");
const theme_el = document.getElementById("theme");
const members_el = document.getElementById("members");
const depDate_el = document.getElementById("depDate");
const amount_el = document.getElementById("amount");
const days_el = document.getElementById("days");
const minMember_el = document.getElementById("minMember");
const maxMember_el = document.getElementById("maxMember");
const mem_name_el = document.getElementById("mem_name");
const groupSta_el = document.getElementById("groupSta");
const groupDesc_el = document.getElementById("groupDesc");

const join_btn_el = document.getElementById("join_btn");
const tab1_el = document.getElementById("tab1");
const tab2_el = document.getElementById("tab2");
const tab3_el = document.getElementById("tab3");
const notice_el = document.getElementById("notice");

let groupSta = ""; //紀錄groupSta
let days; //紀錄days
let notice = ""; //紀錄notice
let str = "";
let groupId;
let schId;
//===================使用到的元素結束===================

window.addEventListener("load", function () {

    //===================取得網址頁上傳遞的參數===================

    //先取得網址字串
    let url = location.href;

    //再來用去尋找網址列中是否有資料傳遞(QueryString)(有?則代表有資料傳遞)
    if (url.indexOf('?') != -1) {

        //之後去分割字串把分割後的字串放進陣列中
        let ary1 = url.split('?');
        //此時ary1裡的內容為：
        //ary1[0] = 'http://localhost:8081/u-and-me/tmp/Front/group/myGroupListUpdate.html?gorupId=1'，ary2[1] = 'groupId=1'

        //以下為若傳遞參數為兩個做法 如果為一個可省略
        //let ary2 = ary1[1].split('&');
        //參數分別為 arr2[0],arr2[1]

        //在進一步以=做劃分 取得id資料
        let arr3 = ary1[1].split('=');
        //ary3[0] = groupId, arr3[1]=1

        //取得傳遞的Id值
        groupId = arr3[1];
    }
    //===================取得網址頁上傳遞的參數結束===================

    fetchMyGroup(groupId);
});

//===================取得原揪團資料方法===================
async function fetchMyGroup(groupId) {


    //取得所有資料
    await fetch(baseUrl + '/groupMemo/' + groupId, {
        method: 'GET',

    }).then(response => {
        return response.json();
    }).then(group => {
        schId = group.sch_Id;
        //更改揪團狀態名稱
        switch (Number(group.group_Sta)) {
            case 0:
                groupSta = "揪團中";
                break;
            case 1:
                groupSta = "揪團成功";
                break;
            case 2:
                groupSta = "揪團取消";
                break;
            case 3:
                groupSta = "揪團被下架";
                break;
            case 5:
                groupSta = "揪團額滿";
                break;
        }

        //替換剩餘天數
        if (group.days < 0) {
            days = `"已結束揪團"`;
        } else {
            days = `剩餘: "${group.days}" 天`;
        }

        notice = group.notice; //紀錄notice資料到全局變數

        //由於後端傳進來的圖片是base64資料，基於base64，須將base64定義成image/png檔，然後變成一個網址data:
        let dataurl = `data:image/png;base64,${group.cover}`

        days_el.innerHTML = days;
        minMember_el.innerHTML = `成團人數: ${group.min_Member}`;
        maxMember_el.innerHTML = `人數上限: ${group.max_Member}`;
        members_el.innerHTML = `目前人數: ${group.members}`;
        theme_el.innerHTML = `主題: ${group.theme}`;
        amount_el.innerHTML = `價格: ${group.amount}`;
        depDate_el.innerHTML = `行程出發時間: ${group.dep_Date}`;
        groupDesc_el.innerHTML = group.group_Desc;
        notice_el.innerHTML = notice;
        mem_name_el.innerHTML = `提案人: ${group.mem_Name}`;
        groupSta_el.innerHTML = '目前揪團狀態: ' + groupSta;
        cover_img_el.src = dataurl;
    });

}
//===================取得原揪團資料結束===================

//===================參與揪團點擊事件===================
function jump() {
    window.location.href = baseUrl + "/tmp/Front/group/regForm.html?gorupId=" + groupId;
}
function jumpSch() {
    window.location.href = baseUrl + "/tmp/Front/schedule/myScheduleEdit.html?schId=" + schId;
}
//===================參與揪團點擊事件結束===================

//===================切換畫面下方文字框===================
function changeTab1() {
    event.preventDefault();
    notice_el.innerHTML = "";
    notice_el.innerHTML = notice;
    tab1_el.classList.add("active");
    tab2_el.classList.remove("active");
    tab3_el.classList.remove("active");
}

function changeTab2() {
    event.preventDefault();
    notice_el.innerHTML = "";
    notice_el.innerHTML =
        `出發日期 8 天（含）之前取消，收取手續費 0%
出發日期 4 ~ 7 天之間取消，收取手續費 50%
出發日期 0 ~ 3 天之間取消，收取手續費 100%`;
    tab1_el.classList.remove("active");
    tab2_el.classList.add("active");
    tab3_el.classList.remove("active");
}

function changeTab3() {
    event.preventDefault();
    notice_el.innerHTML = "";
    notice_el.innerHTML = `免責聲明`;
    tab1_el.classList.remove("active");
    tab2_el.classList.remove("active");
    tab3_el.classList.add("active");
}
//===================切換畫面下方文字框結束===================