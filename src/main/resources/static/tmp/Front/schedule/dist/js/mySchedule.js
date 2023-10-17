// class -off 屬性
const classSwitchOff = "-off";

// 行程搜尋欄相關的標籤
let search_btn_el = document.getElementById("search-btn");
let keywords_el = document.getElementById("keywords");
let sortByStart_el = document.getElementById("sortByStart");
let sortByStartASC_el = document.getElementById("sortByStartASC");
let sortByDays_el = document.getElementById("sortByDays");
let sortByDaysDESC_el = document.getElementById("sortByDaysDESC");

// 分頁元素
const pageSelect_els = document.querySelectorAll("li.page-item");
const page_els = document.querySelectorAll("li.page-item>a");
const pagination_el = document.querySelector("ul.pagination");

// 新增行程內容標籤
const startDate_el = document.getElementById("startDate");
const endDate_el = document.getElementById("endDate");
const selectDest_el = document.getElementById("selectDest");
const selectTag_el = document.getElementById("selectTag");
const schName_el = document.getElementById("schName");
// 新增行程按鈕
const addSch_btn = document.getElementById("addSch");
// 新增行程完成按鈕
const btn_schDone = document.getElementById("schDone");

// fetch對應到的路徑
let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";
let mySchbaseURL = baseURL + "mySch/";
let myURL = mySchbaseURL + "my/";
let addURL = mySchbaseURL + "create";
let privateURL = mySchbaseURL + "private/";
let copyrightURL = mySchbaseURL + "copyright/";
let deleteURL = mySchbaseURL + "delete/";
let selectOneSchURL = baseURL + "schedules/schId/";

// ------------ 行程顯示設定相關函式 ------------ 
// 日期格式化(YYYY年MM月DD日)
function formatDate(dateString) {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString('zh-TW', options);
}

// 計算日期間的天數(行程共?天)
function calDays(schStart, schEnd) {
  // 創建兩個日期對象
  var startDate = new Date(schStart);
  var endDate = new Date(schEnd);
  // 計算兩個日期之間的毫秒差距
  var timeDifference = endDate - startDate;
  // 將毫秒差距轉換為天數
  var daysDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
  return (daysDifference + 1);
}

// 隨機取整數函式，更改行程卡片圖片
function getRandomInteger() {
  return Math.floor(Math.random() * (40 - 1 + 1)) + 1;
}

// 轉換行程複製權限設定顯示文字
function convertBooleanToText(boolValue) {
  return boolValue ? "可供複製" : "不可複製";
}

// 轉換行程公開權限設定顯示文字
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

// ---------- 行程顯示設定相關函式結束 ----------
// ------------ 行程參數驗證相關函式 ------------
// 日期格式驗證
function isDateValid(dateS, dateE) {

  // 日期有填寫，才進行日期間的大小比較
  if (dateS !== "" && dateE !== "") {
    // 將日期轉換成Date物件
    const today = new Date();
    const dateStart = new Date(dateS);
    const dateEnd = new Date(dateE);

    if (dateStart >= today && dateEnd >= today && dateStart <= dateEnd) {
      return true; // 起始日期小於等於結束日期(驗證通過)
    } else {
      return false; // 日期不得小於當天，且起始日期大於結束日期(驗證不通過)
    }
  } else {
    return false;
  }
}
// ---------- 行程參數驗證相關函式結束 -----------
// -------------- 判斷會員是否登入 ---------------
function memberLoginOrNot() {
  $.ajax({
    url: baseURL + "member/getMemId",
    method: "POST",
    dataType: "JSON",
    success: function (data) {
      memId = data.memId;
    },
    error: function (status, error) {
      if (status.status === 401) {
        Swal.fire({
          title: '請先登入會員',
          text: "將為您導向登入頁面....",
          // icon: 'error',
          confirmButtonText: '返回登入頁面',
          confirmButtonColor: '#d33',
          imageUrl: 'https://storage.googleapis.com/sticker-prod/RWsnOMnSplAHd5vb10YN/20-1.png'
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = baseURL + 'tmp/Front/member/memberLogin.html';
          }
        });
      }
    },
  });
}
// ---------- 判斷會員是否登入結束 -----------
// ====== 載入我的行程頁面，將所有該會員的行程列表查出 ======

let page = 0; // 從第一個分頁開始
document.addEventListener("DOMContentLoaded", async function () {

  // 待加入會員是否登入驗證，登入才可進入此頁面查看專屬行程
  memberLoginOrNot();

  // 拿取會員id
  const response = await fetch(baseURL + `member/getMemId`);
  const member = await response.json();
  const memId = member.memId;

  // 查詢會員建立的私人行程
  fetchMyScheduleList(`${myURL + memId}`, page);
});

// =============== 查詢會員專屬的行程 ===============
async function fetchMyScheduleList(URL, page) {

  try {
    const response = await fetch(`${URL}/${page}`);
    const schList = await response.json();

    const schListInner = document.getElementById("schListInner");
    schListInner.innerHTML = "";
    let i = 1;
    schList.forEach(schedule => {

      let row = document.createElement("div");

      row.classList.add("col-lg-4");
      row.classList.add("mb-4");

      row.innerHTML = `
            <div class="card">
            <img src="../dist/img/scheduleimg/trip${i}.jpeg" id="myImg${schedule.schId}"
                alt="" class="card-img-top" style="max-width: 354.656px; max-height: 236.604px; object-fit: cover;">
                <div class="settingSch" id="settingSch${schedule.schId}" onclick="editMySchedule(${schedule.schId})">
                  <div><i class="fa-solid fa-pen-to-square edit"></i></div>
                  <div class="settingSelect -off" id="settingSelect${schedule.schId}" onmouseleave="removeEditBox(${schedule.schId})">
                      <div class="privateSetting" id="privateSetting${schedule.schId}" onclick="selectPrivateSetting(${schedule.schId})">隱私設定與分享</div>
                      <div class="copyrightSetting" onclick="selectCopyrightSetting(${schedule.schId})">複製權限設定</div>
                     <div class="deleteMySch" id="deleteMySch${schedule.schId}" onclick="deleteOneSchedule(${schedule.schId})">刪除</div>
                  </div>
                </div>
                <div class="launchGroup" id="launchGroup${schedule.schId}" onclick="launchMyGroup(${schedule.schId})" onmouseover="hint(${schedule.schId})" onmouseout="hint(${schedule.schId})">
                  <i class="fa-solid fa-people-group group"></i>
                  <div class="launchGroupHint -off">發起揪團</div>
                </div>
                <div class="card-body">
                    <h5 class="card-title">${schedule.schName}</h5>
                    <p class="sch-date" style="font-size: 15px">
                    ${formatDate(schedule.schStart)} ~ ${formatDate(schedule.schEnd)}</p>
                    <p class="sch-date" style="font-size: 13px">
                    行程共${calDays(schedule.schStart, schedule.schEnd)}天
                    <span><i class="fa-solid fa-eye"></i> ${convertNumberToText(schedule.schPub)}</span>
                    <span><i class="fa-solid fa-copy"></i> ${convertBooleanToText(schedule.schCopy)}</span>
                    </p>
                    <a href="${baseURL}tmp/Front/schedule/myScheduleEdit.html?schId=${schedule.schId}" class="btn btn-outline-success btn-sm viewDetails">查看詳情</a>
                </div>
              </div>
            `;
      schListInner.appendChild(row);
      i++;
      // 範例用
      // let img_el = document.getElementById(`myImg${schedule.schId}`);
      // img_el.src = "../dist/img/scheduleimg/trip13.jpeg";
      if (schedule.schId === 1) {
        let img_el = document.getElementById("myImg1");
        img_el.src = "../../../img/attrPic/attr2.jpg";
      }
      if (schedule.schId === 11) {
        let img_el11 = document.getElementById("myImg11");
        img_el11.src = "../dist/img/scheduleimg/trip25.jpeg";
      }
      if (schedule.schId === 17) {
        let img_el18 = document.getElementById("myImg17");
        img_el18.src = "../dist/img/scheduleimg/trip41.jpeg";
      }
      if (schedule.schId === 18) {
        let img_el18 = document.getElementById("myImg18");
        img_el18.src = "../dist/img/scheduleimg/trip8.jpeg";
      }
      if (schedule.schId === 6) {
        let img_el18 = document.getElementById("myImg6");
        img_el18.src = "../dist/img/scheduleimg/trip42.jpeg";
      }
      if (schedule.schId === 5) {
        let img_el18 = document.getElementById("myImg5");
        img_el18.src = "../dist/img/scheduleimg/trip11.jpeg";
      }
    });
  } catch (error) {
    console.error("Error fetching MyScheduleList:", error);
  }
}


// ============ 查詢會員專屬的行程結束 =============

// ================= 分頁查詢行程 ==================
// 每次搜尋鈕觸發點擊事件時，就將分頁按鈕重置回第一頁
function switchToPage1() {
  pagination_el.style.display = "";
  // 將分頁按鈕換成第一頁亮起
  for (let pageSelect of pageSelect_els) {
    if (pageSelect.classList.contains("active"))
      pageSelect.classList.remove("active");
  }
  pageSelect_els[0].classList.add("active");
}

let searchSortByStartDESC = true; // (預設)查詢行程，並依照起始日期降冪排序(新到舊)
let searchSortByStartASC = false; // 查詢行程，並依照起始日期升冪排序(舊到新)
let searchByKeyWords = false; // 依照關鍵字查詢行程，並依照起始日期降冪排序
let searchByDaysASC = false;  // 依照行程天數查詢行程，並依照天數(小到大)升冪排序
let searchByDaysDESC = false; // 依照行程天數查詢行程，並依照天數(大到小)降冪排序

// 依照起始日期降冪排序(預設)
sortByStart_el.addEventListener("click", async function (event) {
  event.preventDefault();
  switchToPage1();

  const response = await fetch(baseURL + `member/getMemId`);
  const member = await response.json();
  const memId = member.memId;

  fetchMyScheduleList(`${myURL + memId}`, page);
  searchSortByStartDESC = true;
  searchSortByStartASC = false;
  searchByKeyWords = false;
  searchByDaysASC = false;
  searchByDaysDESC = false;
  // 清空搜尋關鍵字
  keywords_el.value = "";
});

// 依照起始日期升冪排序
sortByStartASC_el.addEventListener("click", async function (event) {
  event.preventDefault();
  switchToPage1();

  let myURLASC = `${mySchbaseURL}my`;
  fetchMyScheduleList(myURLASC, page);

  searchSortByStartDESC = false;
  searchSortByStartASC = true;
  searchByKeyWords = false;
  searchByDaysASC = false;
  searchByDaysDESC = false;
  // 清空搜尋關鍵字
  keywords_el.value = "";
});

// 依行程天數排序行程清單(由小到大)，並依照起始日期降冪排序
sortByDays_el.addEventListener("click", async function (event) {
  event.preventDefault();
  switchToPage1();

  let daysURL = `${mySchbaseURL}days`;
  fetchMyScheduleList(daysURL, page);

  searchSortByStartDESC = false;
  searchSortByStartASC = false;
  searchByKeyWords = false;
  searchByDaysASC = true;
  searchByDaysDESC = false;
  // 清空搜尋關鍵字
  keywords_el.value = "";
});

// 依行程天數排序行程清單(由大到小)，並依照起始日期降冪排序
sortByDaysDESC_el.addEventListener("click", async function (event) {
  event.preventDefault();
  switchToPage1();

  let daysDESCURL = `${mySchbaseURL}daysDESC`;
  fetchMyScheduleList(daysDESCURL, page);

  searchSortByStartDESC = false;
  searchSortByStartASC = false;
  searchByKeyWords = false;
  searchByDaysASC = false;
  searchByDaysDESC = true;
  // 清空搜尋關鍵字
  keywords_el.value = "";
});

// 依關鍵字(行程名稱)搜尋會員自己的行程清單(依照起始日期降冪排序)
search_btn_el.addEventListener("click", async function (event) {
  event.preventDefault();
  switchToPage1();
  pagination_el.style.display = "none";

  const responseMem = await fetch(baseURL + `member/getMemId`);
  const member = await responseMem.json();
  const memId = member.memId;

  let keywords = keywords_el.value;
  let keywordsURL = `${mySchbaseURL}${keywords}/${memId}`;
  fetchMyScheduleList(keywordsURL, page);

  searchSortByStartDESC = false;
  searchSortByStartASC = false;
  searchByKeyWords = true;
  searchByDaysASC = false;
  searchByDaysDESC = false;
});

// 將所有分頁標籤監聽click事件
for (let pageSelect of pageSelect_els) {

  pageSelect.addEventListener('click', async function (event) {
    // console.log(event.target); // 被點擊到的事件物件<a>標籤
    // console.log(event.target.innerText); // 被點擊到的分頁頁數
    event.preventDefault();

    // 將所有active移除(使分頁暗下去)
    for (let pageSelect of pageSelect_els) {
      if (pageSelect.classList.contains("active"))
        pageSelect.classList.remove("active");
    }
    // 將被點擊到的選項加上active(使分頁亮起來)
    event.target.parentNode.classList.add("active");

    // 控制fetch傳入網址
    const response = await fetch(baseURL + `member/getMemId`);
    const member = await response.json();
    const memId = member.memId;

    // 依照搜尋條件更換查詢行程內容
    let sendURL = `${myURL + memId}`; // 預設依照起始日期(新到舊)查詢會員所有行程
    if (searchSortByStartDESC === true)
      sendURL = `${myURL + memId}`; // myDESCURL 依起始日期新到舊
    else if (searchSortByStartASC === true)
      sendURL = `${mySchbaseURL}my`; // myASCURL 依起始日期舊到新
    else if (searchByDaysASC === true)
      sendURL = `${mySchbaseURL}days`; // daysASCURL 依行程天數小到大
    else if (searchByDaysDESC === true)
      sendURL = `${mySchbaseURL}daysDESC`; // daysDESCURL 依行程天數大到小
    else if (searchByKeyWords === true)
      sendURL = `${mySchbaseURL}${keywords}/${memId}`;

    fetchMyScheduleList(sendURL, (event.target.innerText - 1));
  });
}
// ============== 分頁查詢行程結束 ===============

// =============== 新增一個行程大綱 ===============
// 新增行程鈕按下時，調出select選項
addSch_btn.onclick = async () => {

  // 選擇目的地選項
  const response = await fetch(baseURL + `schTag/`);
  const schTagList = await response.json();

  selectDest_el.innerHTML = "";
  selectTag_el.innerHTML = "";

  // 地區預設選項
  let rowSelectDefault = document.createElement("option");
  rowSelectDefault.selected = true;
  // rowSelectDefault.disabled = true;
  rowSelectDefault.innerText = "請選擇地區";
  selectDest_el.appendChild(rowSelectDefault);

  // 類型預設選項
  let rowSelectDefault2 = document.createElement("option");
  rowSelectDefault2.selected = true;
  // rowSelectDefault2.disabled = true;
  rowSelectDefault2.innerText = "請選擇類型";
  selectTag_el.appendChild(rowSelectDefault2);

  // 地區類標籤為 schTagId 1~20
  for (let i = 0; i < schTagList.length; i++) {


    if (i < 20) {
      // 選擇地區類型
      let destOption = document.createElement("option");
      destOption.value = schTagList[i].schTagId;
      destOption.innerText = schTagList[i].schTagName;
      selectDest_el.appendChild(destOption);

    } else {
      // 選擇旅遊類型選項
      // 剩餘的標籤
      let typeOption = document.createElement("option");
      typeOption.value = schTagList[i].schTagId;
      typeOption.innerText = schTagList[i].schTagName;
      selectTag_el.appendChild(typeOption);
    }
  }
}

// 將新增行程資料傳輸進資料庫，並回到我的行程頁面
let schTagId1;
let schTagId2;

btn_schDone.onclick = async event => {

  event.preventDefault();
  // 抓取會員id
  const response = await fetch(baseURL + `member/getMemId`);
  const member = await response.json();
  const memId = member.memId;

  // 資料驗證(日期及行程名稱不得為空值)，驗證通過後才新增一筆行程
  const dateValidResult = isDateValid(startDate_el.value, endDate_el.value);
  const schNameInput = schName_el.value.trim();

  if (dateValidResult && schNameInput !== "") {

    // 欲新增的行程大綱資料
    const send_data = {
      schName: schNameInput,
      memId: memId,
      schStart: startDate_el.value,
      schEnd: endDate_el.value,
      schCopy: true,
      schPub: 0
    }
    await fetch(addURL, {
      headers: {
        "content-type": "application/json",
      },
      method: 'POST',
      body: JSON.stringify(send_data)
    }).then((response) => {
      return response.json();
    }).then(async schedule => {
      // 拿取剛剛新增的行程id
      let schId = schedule.schId;
      // 一、地區標籤有選取值
      if (schTagId1 !== undefined && schTagId2 === undefined) {

        // 建立標籤與行程關聯清單
        const send_data = [{
          schId: schId,
          schTagId: schTagId1
        }]

        await fetch(`${baseURL}schTag/addTagListInSch`, {
          headers: {
            "content-type": "application/json",
          },
          method: 'POST',
          body: JSON.stringify(send_data)
        }).catch(function (error) {
          alert('新增失敗' + error);
          return;
        });
      }
      // 二、類型標籤有選取值
      else if (schTagId1 === undefined && schTagId2 !== undefined) {

        // 建立標籤與行程關聯清單
        const send_data = [{
          schId: schId,
          schTagId: schTagId2
        }]

        await fetch(`${baseURL}schTag/addTagListInSch`, {
          headers: {
            "content-type": "application/json",
          },
          method: 'POST',
          body: JSON.stringify(send_data)
        }).catch(function (error) {
          alert('新增失敗' + error);
          return;
        });
      }
      // 三、地區及類型標籤均有選取值
      else if (schTagId1 !== undefined && schTagId2 !== undefined) {

        // 建立標籤與行程關聯清單
        const send_data = [{
          schId: schId,
          schTagId: schTagId1
        }, {
          schId: schId,
          schTagId: schTagId2
        }]

        await fetch(`${baseURL}schTag/addTagListInSch`, {
          headers: {
            "content-type": "application/json",
          },
          method: 'POST',
          body: JSON.stringify(send_data)
        }).catch(function (error) {
          alert('新增失敗' + error);
          return;
        });
      }
    }).catch(function (error) {
      alert('新增失敗' + error);
      return;
    });

    Swal.fire({
      title: '新增成功！',
      // icon: 'success',
      imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/product/11952172/LINEStorePC/main.png?v=1'
    }).then(() => {
      location.reload();
    });

  } else {
    if (schNameInput === "") {
      Swal.fire({
        icon: 'error',
        title: '新增失敗',
        text: '行程名稱為必填欄位！',
      })
    } else {
      Swal.fire({
        icon: 'error',
        title: '新增失敗',
        text: '請選擇有效的日期！',
      })
    }
  }
}
// =============== 新增一個行程大綱結束 ===============

// ========= 編輯一個行程大綱(瀏覽、複製權限) ==========
function editMySchedule(schId) {
  // 跳出選擇公開權限、複製權限及刪除選項框
  const settingSelect_el = document.querySelector("#settingSelect" + schId);
  if (settingSelect_el.classList.contains(classSwitchOff)) {
    settingSelect_el.classList.remove(classSwitchOff);
  }
}

// 滑鼠離開編輯選項時，自動關閉
function removeEditBox(schId) {
  const settingSelect_el = document.querySelector("#settingSelect" + schId);
  if (!settingSelect_el.classList.contains(classSwitchOff)) {
    settingSelect_el.classList.add(classSwitchOff);
  }
}

// 修改行程隱私設定與分享
async function selectPrivateSetting(schId) {
  Swal.fire({
    title: '隱私設定與分享',
    input: 'select',
    inputOptions: {
      '0': '私密',
      '1': '透過連結分享',
      '2': '公開'
    },
    showCancelButton: true,
    cancelButtonText: '取消',
    confirmButtonText: '確定',
    showLoaderOnConfirm: true,
    preConfirm: async (selectedOption) => {

      const response = await fetch(`${privateURL}${schId}/${selectedOption}`);
      return response;
    },
    allowOutsideClick: () => !Swal.isLoading()
  }).then(async (result) => {
    if (result.isConfirmed) {

      // 如果設定為連結分享，要顯示分享的連結
      await fetch(`${selectOneSchURL}${schId}`)
        .then((response) => {
          return response.json();
        }).then((schedule) => {

          if (schedule.schPub === 1) {
            Swal.fire({
              title: `瀏覽權限設定成功！`,
              html: `<div style="font-size: 10px;">分享連結：<br>http://uandme.ddns.net/u-and-me/tmp/Front/schedule/myScheduleEdit.html?schId=${schedule.schId}</div>`,
              imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/471356583/android/sticker.png'
            }).then(() => {
              // 在用戶按下"確定"後執行重新整理
              location.reload();
            });
          }
          else {
            Swal.fire({
              title: `瀏覽權限設定成功！`,
              imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/471356583/android/sticker.png'
            }).then(() => {
              // 在用戶按下"確定"後執行重新整理
              location.reload();
            });
          }
        });
    }
  });
}

// 修改複製權限設定
async function selectCopyrightSetting(schId) {
  Swal.fire({
    title: '是否供他人複製此行程？',
    input: 'select',
    inputOptions: {
      true: '是',
      false: '否'
    },
    showCancelButton: true,
    cancelButtonText: '取消',
    confirmButtonText: '確定',
    showLoaderOnConfirm: true,
    preConfirm: async (selectedOption) => {
      const response = await fetch(`${copyrightURL}${schId}/${selectedOption}`);
      return response;
    },
    allowOutsideClick: () => !Swal.isLoading()
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: `複製權限設定成功！`,
        imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/425916056/android/sticker.png'
      }).then(() => {
        // 在用戶按下"確定"後執行重新整理
        location.reload();
      });
    }
  });
}
// =============== 編輯一個行程大綱結束 ===============

// ================= 刪除一個行程大綱 =================
async function deleteOneSchedule(schId) {
  Swal.fire({
    title: '確定要刪除此行程？',
    text: "注意：刪除後不可復原！",
    // icon: 'warning',
    imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/438734965/android/sticker.png',
    showCancelButton: true,
    confirmButtonColor: '#008000',
    confirmButtonText: '刪除',
    cancelButtonColor: '#d33',
    cancelButtonText: '取消'
  }).then(async (result) => {
    if (result.isConfirmed) {
      await fetch(`${deleteURL}${schId}`);
      Swal.fire({
        title: '刪除成功！',
        imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/524191583/android/sticker.png'
      }).then(() => {
        location.reload();
      });
    }
  })
}
// =============== 刪除一個行程大綱結束 ===============

// ================= 透過行程發起揪團 =================
// 按發起揪團鈕會帶著schId跳轉到詳細內容頁面，並將資料映射到相關欄位上
function redirectToLaunchGroupPage(schId) {
  let newPageUrl = `${baseURL}tmp/Front/group/groupListInsert.html?schId=${schId}`;
  window.location.href = newPageUrl;
}

// 若尚未成為團主，則導向註冊會員頁面
function redirectToGroupRegisterPage() {
  let newPageUrl = `${baseURL}tmp/Front/member/memberGroupRegister.html`;
  window.location.href = newPageUrl;
}

// 發起揪團提示字出現與否
function hint(schId) {
  const launchGroupHint_el = document.querySelector(`#launchGroup${schId}>div`);
  if (launchGroupHint_el.classList.contains(classSwitchOff)) {
    launchGroupHint_el.classList.remove(classSwitchOff);
  } else if (!launchGroupHint_el.classList.contains(classSwitchOff)) {
    launchGroupHint_el.classList.add(classSwitchOff);
  }
}

// 發起揪團
async function launchMyGroup(schId) {
  // 一、確認行程為公開才能發起揪團，否則不可發起揪團
  const responseSchPub = await fetch(selectOneSchURL + schId);
  const schedule = await responseSchPub.json();

  // 確認行程為公開
  if (schedule.schPub === 2) {
    // 二、驗證團主身分是否符合發起揪團資格
    const responseMemGroup = await fetch(baseURL + `member/getMemId`);
    const member = await responseMemGroup.json();

    switch (member.memGroup) {
      // 非團主，跳轉到註冊成為團主頁面
      case 0:
        Swal.fire({
          icon: 'error',
          title: '發起揪團失敗',
          text: '您不具團主資格，請註冊成為團主！'
        }).then(() => { redirectToGroupRegisterPage(); });
        break;
      // 團主，跳轉到發起揪團頁面
      case 1:
        redirectToLaunchGroupPage(schId);
        break;
    }
  } else {
    Swal.fire({
      icon: 'error',
      title: '發起揪團失敗',
      text: '行程需為公開瀏覽才可發起揪團！'
    });
  }
}
// =============== 透過行程發起揪團結束 ===============

// 將檢舉標籤文字轉換為數字
selectDest_el.addEventListener('change', function () {
  schTagId1 = Number(selectDest_el.value);
})
// 將行程公開權限狀態轉換為數字
selectTag_el.addEventListener('change', function () {
  schTagId2 = Number(selectTag_el.value);
})