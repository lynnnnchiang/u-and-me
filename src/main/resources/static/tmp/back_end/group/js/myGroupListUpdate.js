const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

//===================使用到的元素===================
const minMember_el = document.getElementById("minMember");
const maxMember_el = document.getElementById("maxMember");
const theme_el = document.getElementById("theme");
const amount_el = document.getElementById("amount");
const depDate_el = document.getElementById("depDate");
const deadline_el = document.getElementById("deadline");
const groupDesc_el = document.getElementById("groupDesc");
const notice_el = document.getElementById("notice");
const cover_el = document.getElementById("cover");
const cover_img_el = document.getElementById("cover_img");
const btn_submit_el = document.getElementById("btn_submit");

const inputMinMember_el = document.getElementById("inputMinMember");
const inputMaxMember_el = document.getElementById("inputMaxMember");
const inputTheme_el = document.getElementById("inputTheme");
const inputAmount_el = document.getElementById("inputAmount");
const inputDepDate_el = document.getElementById("inputDepDate");
const inputDeadline_el = document.getElementById("inputDeadline");
const inputGroupDesc_el = document.getElementById("inputGroupDesc");
const inputNotice_el = document.getElementById("inputNotice");

//==========判斷錯誤需要用的=========

let isInteger = n => (parseInt(n, 10) === n); //判斷是否為整數

let minMemberStr = document.createElement("span");
minMemberStr.style.color = 'red';

let maxMemberStr = document.createElement("span");
maxMemberStr.style.color = 'red';

let themeStr = document.createElement("span");
themeStr.style.color = 'red';

let depDateStr = document.createElement("span");
depDateStr.style.color = 'red';

let deadlineStr = document.createElement("span");
deadlineStr.style.color = 'red';

let groupDescStr = document.createElement("span");
groupDescStr.style.color = 'red';

let amountStr = document.createElement("span");
amountStr.style.color = 'red';

let noticeStr = document.createElement("span");
noticeStr.style.color = 'red';
//用於紀錄原始數據
let cover;

//===================使用到的元素結束===================

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

//網頁載入後執行
document.addEventListener("DOMContentLoaded", function () {
    let groupId;
    let today = new Date().getTime();//用於驗證、取得目前時間

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

    //因為可能不改圖片，所以當change時才觸發檔案流
    cover_el.addEventListener('change', changeListener);
    function changeListener() {
        const id = this.id;
        const files = this.files;
        const img = document.getElementById("cover_img");

        //1. 取得File物件
        cover = files[0];
        img.src = URL.createObjectURL(cover);

        //2. 實例化FileReader物件
        const fileReader = new FileReader();

        //3. 替FileReader物件 註冊 載入監聽器
        fileReader.onload = async event => {
            //4. 轉成Base64字串
            cover = btoa(event.target.result);
        }
        //5. 開始讀取檔案
        fileReader.readAsBinaryString(cover);
    }

    //===================送出更新開始===================
    btn_submit_el.addEventListener("click", async function (e) {
        e.preventDefault();

        //==================錯誤驗證==================
        let control = true;

        //初始化Str
        minMemberStr.innerHTML = '';
        maxMemberStr.innerHTML = '';
        themeStr.innerHTML = '';
        amountStr.innerHTML = '';
        depDateStr.innerHTML = '';
        deadlineStr.innerHTML = '';
        groupDescStr.innerHTML = '';
        noticeStr.innerHTML = '';

        //最小人數
        if (minMember_el.value === null || minMember_el.value.trim() === "") {
            control = false;
            minMemberStr.innerHTML = ' *最小人數必須填入數值';
            inputMinMember_el.appendChild(minMemberStr);
        } else if (!(Number.isInteger(Number(minMember_el.value))) || Number(minMember_el.value) < 0) {
            control = false;
            minMemberStr.innerHTML = ' *最小人數必須為正整數';
            inputMinMember_el.appendChild(minMemberStr);
        } else if ((Number.isInteger(Number(maxMember_el.value))) && Number(maxMember_el.value) > 0) {
            if (Number(minMember_el.value) > Number(maxMember_el.value)) {
                control = false;
                console.log(minMember_el.value);
                minMemberStr.innerHTML = ' *最小人數不能大於最大人數';
                inputMinMember_el.appendChild(minMemberStr);
            }
        }

        //最大人數
        if (maxMember_el.value === null || maxMember_el.value.trim() === "") {
            control = false;
            maxMemberStr.innerHTML = ' *最大人數必須填入數值';
            inputMaxMember_el.appendChild(maxMemberStr);
        } else if (!(Number.isInteger(Number(maxMember_el.value))) || Number(maxMember_el.value) < 0) {
            control = false;
            maxMemberStr.innerHTML = ' *最大人數必須為正整數';
            inputMaxMember_el.appendChild(maxMemberStr);
        } else if ((Number.isInteger(Number(minMember_el.value))) && Number(minMember_el.value) > 0) {
            if (Number(maxMember_el.value) < Number(minMember_el.value)) {
                control = false;
                console.log(maxMember_el.value);
                maxMemberStr.innerHTML = ' *最大人數不能小於最小人數';
                inputMaxMember_el.appendChild(maxMemberStr);
            }
        }

        //標題
        if (theme_el.value === null || theme_el.value.trim() === "") {
            // alert('標題不可為空值');
            control = false;
            themeStr.innerHTML = ' *標題必須填入數值';
            inputTheme_el.appendChild(themeStr);
        }

        //價格
        if (amount_el.value === null || amount_el.value.trim() === "") {
            control = false;
            amountStr.innerHTML = ' *價格必須填入數值';
            inputAmount_el.appendChild(amountStr);
        } else if (amount_el.value < 0 || !(Number.isInteger(Number(amount_el.value)))) {
            control = false;
            amountStr.innerHTML = ' *價格必須為正整數';
            inputAmount_el.appendChild(amountStr);
        }

        //出發日期
        if (depDate_el.value === null || depDate_el.value.trim() === "") {
            control = false;
            depDateStr.innerHTML = ' *行程出發日期必須填入數值';
            inputDepDate_el.appendChild(depDateStr);
        }else if (depDate_el.value < deadline_el.value) {
            control = false;
            depDateStr.innerHTML = ' *行程出發日期不得小於截止日期';
            inputDepDate_el.appendChild(depDateStr);
        }

        //截止日期
        if (deadline_el.value === null || deadline_el.value.trim() === "") {
            control = false;
            deadlineStr.innerHTML = ' *揪團截止日期必須填入數值';
            inputDeadline_el.appendChild(deadlineStr);
        }else if (deadline_el.value > depDate_el.value) {
            control = false;
            deadlineStr.innerHTML = ' *揪團截止日期不得大於出發日期';
            inputDeadline_el.appendChild(deadlineStr);
        }

        //揪團描述
        if (groupDesc_el.value === null || groupDesc_el.value.trim() === "") {
            control = false;
            groupDescStr.innerHTML = ' *揪團描述必須填入數值';
            inputGroupDesc_el.appendChild(groupDescStr);
        }

        //行前通知
        if (notice_el.value === null || notice_el.value.trim() === "") {
            // alert('行前通知不可為空值');
            control = false;
            noticeStr.innerHTML = ' *行前通知必須填入數值';
            inputNotice_el.appendChild(noticeStr);
        }
        //==================錯誤驗證結束==================

        if (control) {
            //將資料包裝成一個物件
            const send_data = {
                groupId: groupId,
                minMember: minMember_el.value.trim(),
                maxMember: maxMember_el.value.trim(),
                theme: theme_el.value.trim(),
                amount: amount_el.value.trim(),
                depDate: depDate_el.value.trim(),
                deadline: deadline_el.value.trim(),
                groupDesc: groupDesc_el.value.trim(),
                notice: notice_el.value.trim(),
                cover: cover,
            }
            await fetch(baseUrl + 'myGroup/update/' + groupId, {
                headers: {
                    "content-type": "application/json",
                },
                method: 'PUT',
                body: JSON.stringify(send_data)
            }).catch(function (e) {
                Swal.fire({
                    icon: 'error',
                    title: '更新失敗',
                    text: '請檢查更新資料',
                    showCancelButton: true
                  })
            });
            Swal.fire({
                icon: 'success',
                title: '更新成功',
                text: '資料已更新',
                showCancelButton: true
              })
            // location.reload();
        } else {
            Swal.fire({
                icon: 'error',
                title: '更新失敗',
                text: '請檢查更新資料',
                showCancelButton: true
              })
        }
    });
});


//===================送出更新結束===================


//===================取得原揪團資料方法===================
async function fetchMyGroup(groupId) {

    // 取得修改資料
    await fetch(baseUrl + 'myGroup/update/' + groupId, {
        method: 'GET',

        //取得所有資料
        // await fetch(`${baseUrl}group/` + groupId, {
        //     method: 'GET',

    }).then(response => {
        return response.json();
    }).then(group => {

        cover = group.cover;
        // memId = group.memId;
        // schId = group.schId;
        // members = group.members;
        // startDate = group.startDate;
        // groupSta = group.groupSta;
        // paymentSta = group.paymentSta;

        //由於後端傳進來的圖片是base64資料，基於base64，須將base64定義成image/png檔，然後變成一個網址data:
        let dataurl = `data:image/png;base64,${group.cover}`

        minMember_el.value = group.min_Member;
        maxMember_el.value = group.max_Member;
        theme_el.value = group.theme;
        amount_el.value = group.amount;
        depDate_el.value = group.dep_Date;
        deadline_el.value = group.deadline;
        groupDesc_el.value = group.group_Desc;
        notice_el.value = group.notice;
        cover_img_el.src = dataurl;
    });
    
}
//===================取得原揪團資料結束===================
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