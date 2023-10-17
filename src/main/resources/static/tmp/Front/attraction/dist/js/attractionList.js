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

// 檢舉燈箱的檢舉提交鈕
const repDesc_el = document.getElementById("repDesc");
const repModal_el = document.getElementById("exampleModal");

// fetch對應到的路徑
let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";
let schListbaseURL = baseURL + "schedules/";

// 隨機取整數函式，更改行程卡片圖片
function getRandomInteger() {
  return Math.floor(Math.random() * (40 - 1 + 1)) + 1;
}

const attrTypeInput = document.getElementById("attrTypeInput");
attrTypeInput.addEventListener("change", async function () {
  console.log(attrTypeInput.value);
  const response = await fetch(baseURL + `getAttrsByType/` + attrTypeInput.value);
  const attrList = await response.json();
  // const attrList = respJson.body;
  // console.log(respJson);

  const schListInner = document.getElementById("schListInner");
  schListInner.innerHTML = "";

  attrList.forEach(async attr => {

    const responseOfAttrFirstPic = await fetch(baseURL + `getAttrPics/` + attr.attrId);
    const respJson = await responseOfAttrFirstPic.json();
    const attrPic = respJson.attrPic[0].attrPicData;
    console.log(attrPic);
    const picSrc = "data: image/jpeg;base64," + attrPic;
    let row = document.createElement("div");

    row.classList.add("col-lg-4");
    row.classList.add("mb-4");

    row.innerHTML = `
            <div class="card">
                <img src="${picSrc}"
                    alt="等待圖片載入..." class="card-img-top" 
                    style="max-width: 354.656px; max-height: 236.604px; object-fit: cover;">
                
                <div class="card-body">
                    <h5 class="card-title">${attr.attrName}</h5>
                    <p class="sch-date" style="font-size: 15px">
                      ${attr.attrAddr}
                    </p>
                    <p class="sch-date" style="font-size: 15px">
                      ${attr.attrType}
                    </p>
                    <p class="sch-date" style="font-size: 13px">
                      ${bussTimeString(attr.attrBussTime)}
                      <br>
                      <span><i class="fa-solid fa-copy"></i> ${codeToPriceRange(attr.attrCostRange)}</span>
                    </p>
                    <a href="${baseURL}tmp/Front/attraction/myAttractionEdit.html?attrId=${attr.attrId}" 
                      class="btn btn-outline-success btn-sm viewDetails">查看詳情</a>
                </div>
              </div>
            </div>
            `;
    schListInner.appendChild(row);
  });

})

// ----------- 行程顯示設定相關函式結束 -----------

// ====== 載入旅遊行程頁面，將所有公開的行程列表查出 ======
let page = 0; // 從第一個分頁開始
// 載入網頁就將所有公開行程列表查出
document.addEventListener("DOMContentLoaded", function () {

  // 查詢所有公開行程列表，並依照起始日期(新到舊)排序
  // fetchPublicScheduleList(`${schListbaseURL}all/`, page);
  fetchPublicAttractionList();
});

// =============== 查詢所有公開的景點 ===============
async function fetchPublicAttractionList() {

  try {
    var response = await fetch(baseURL + 'getAllAttr');
    console.log(response);
    var attrList = await response.json();
    console.log(attrList);

    // page_el.max = Math.floor(attrList.length / pageSize) + 1;
    // console.log(page_el.max);

    // response = await fetch(baseURL + 'attr/all/' + pageSize + '/' + page);
    // attrList = await response.json();

    const schListInner = document.getElementById("schListInner");
    schListInner.innerHTML = "";

    attrList.body.forEach(async attr => {

      const responseOfAttrFirstPic = await fetch(baseURL + `getAttrPics/` + attr.attrId);
      const respJson = await responseOfAttrFirstPic.json();
      const attrPic = respJson.attrPic[0].attrPicData;
      console.log(attrPic);
      const picSrc = "data: image/jpeg;base64," + attrPic;
      let row = document.createElement("div");

      row.classList.add("col-lg-4");
      row.classList.add("mb-4");

      row.innerHTML = `
            <div class="card">
                <img src="${picSrc}"
                    alt="等待圖片載入..." class="card-img-top" 
                    style="max-width: 354.656px; max-height: 236.604px; object-fit: cover;">
                
                <div class="card-body">
                    <h5 class="card-title">${attr.attrName}</h5>
                    <p class="sch-date" style="font-size: 15px">
                      ${attr.attrAddr}
                    </p>
                    <p class="sch-date" style="font-size: 15px">
                      ${attr.attrType}
                    </p>
                    <p class="sch-date" style="font-size: 13px">
                      ${bussTimeString(attr.attrBussTime)}
                      <br>
                      <span><i class="fa-solid fa-copy"></i> ${codeToPriceRange(attr.attrCostRange)}</span>
                    </p>
                    <a href="${baseURL}tmp/Front/attraction/myAttractionEdit.html?attrId=${attr.attrId}" 
                      class="btn btn-outline-success btn-sm viewDetails">查看詳情</a>
                </div>
              </div>
            </div>
            `;
      schListInner.appendChild(row);
    });
  } catch (error) {
    console.error("Error fetching schedulePublic list:", error);
  }
}
// 景點消費價位轉換
function codeToPriceRange(code) {
  switch (code) {
    case 1:
      return "低價位 (500元以下)";
    case 2:
      return "中價位 (500~1000元)";
    case 3:
      return "高價位 (1000元以上)";
    // 添加更多的代号和对应的描述
    default:
      return "尚無資料";
  }
}
function bussTimeString(inputString) {
  // 使用逗号分割字符串，并将结果存储在数组中
  if (inputString == undefined || inputString == null || inputString.trim() == '')
    return "尚無資料";
  if (inputString == "尚無資料" || inputString == "暫無資料")
    return "尚無資料";
  const timeRanges = inputString.split('|');

  // 分别存储平日和假日的时间范围
  let weekdayTimeRange = "";
  let holidayTimeRange = "";

  holidayTimeRange = timeRanges[0];
  holidayTimeRangeArr = [];
  holidayTimeRangeArr = holidayTimeRange.split('');
  holidayTimeRange = holidayTimeRangeArr[0] + holidayTimeRange[1] + ":"
    + holidayTimeRange[2] + holidayTimeRange[3] + "~"
    + holidayTimeRange[5] + holidayTimeRange[6] + ":"
    + holidayTimeRange[7] + holidayTimeRange[8];
  weekdayTimeRange = timeRanges[1];
  weekdayTimeRangeArr = [];
  weekdayTimeRangeArr = weekdayTimeRange.split('');
  weekdayTimeRange = weekdayTimeRangeArr[0] + weekdayTimeRange[1] + ":"
    + weekdayTimeRange[2] + weekdayTimeRange[3] + "~"
    + weekdayTimeRange[5] + weekdayTimeRange[6] + ":"
    + weekdayTimeRange[7] + weekdayTimeRange[8];
  const resultString = `平日:${weekdayTimeRange} | 假日:${holidayTimeRange}`;
  return resultString;
}

// =============== 查詢所有公開的行程 ===============
async function fetchPublicScheduleList(URL, page) {
  try {
    const response = await fetch(URL + page);
    const schList = await response.json();

    const schListInner = document.getElementById("schListInner");
    schListInner.innerHTML = "";

    schList.forEach(schedule => {

      let row = document.createElement("div");

      row.classList.add("col-lg-4");
      row.classList.add("mb-4");

      row.innerHTML = `
            <div class="card">
                <img src="../dist/img/scheduleimg/trip${getRandomInteger()}.jpeg"
                    alt="等待圖片載入..." class="card-img-top" 
                    style="max-width: 354.656px; max-height: 236.604px; object-fit: cover;">
                <div class="copySch" id="copySch${schedule.schId}" onclick="copySchedule(${schedule.schId})">
                    <div><i class="fa-solid fa-copy"></i></div>
                </div>
                <div class="addReport" id="addReport${schedule.schId}" onclick="addReport(${schedule.schId})" onmouseover="hint(${schedule.schId})"
                onmouseout="hint(${schedule.schId})">
                    <i class="fa-solid fa-triangle-exclamation report" data-bs-toggle="modal" data-bs-target="#exampleModal"></i>
                    <div class="addReportHint -off">我要檢舉</div>
                </div>
                <div class="card-body">
                    <h5 class="card-title">${schedule.schName}</h5>
                    <p class="sch-date" style="font-size: 15px">
                      ${formatDate(schedule.schStart)} ~ ${formatDate(schedule.schEnd)}
                    </p>
                    <p class="sch-date" style="font-size: 13px">
                      行程共${calDays(schedule.schStart, schedule.schEnd)}天
                      <span><i class="fa-solid fa-copy"></i> ${convertBooleanToText(schedule.schCopy)}</span>
                    </p>
                    <a href="${baseURL}tmp/Front/attraction/myAttractionEdit.html?attrId=${attr.attrId}" 
                      class="btn btn-outline-success btn-sm viewDetails">查看詳情</a>
                </div>
              </div>
            </div>
            `;
      schListInner.appendChild(row);
    });
  } catch (error) {
    console.error("Error fetching schedulePublic list:", error);
  }
}
// ================ 查詢公開行程結束 =================

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
// sortByStart_el.addEventListener("click", async function (event) {
//   event.preventDefault();
//   switchToPage1();

//   fetchPublicScheduleList(`${schListbaseURL}all/`, page);
//   searchSortByStartDESC = true;
//   searchSortByStartASC = false;
//   searchByKeyWords = false;
//   searchByDaysASC = false;
//   searchByDaysDESC = false;
//   // 清空搜尋關鍵字
//   keywords_el.value = "";
// });

// 依照起始日期升冪排序
// sortByStartASC_el.addEventListener("click", async function (event) {
//   event.preventDefault();
//   switchToPage1();

//   fetchPublicScheduleList(`${schListbaseURL}allASC/`, page);

//   searchSortByStartDESC = false;
//   searchSortByStartASC = true;
//   searchByKeyWords = false;
//   searchByDaysASC = false;
//   searchByDaysDESC = false;
//   // 清空搜尋關鍵字
//   keywords_el.value = "";
// });

// // 依行程天數排序行程清單(由小到大)，並依照起始日期降冪排序
// sortByDays_el.addEventListener("click", async function (event) {
//   event.preventDefault();
//   switchToPage1();

//   fetchPublicScheduleList(`${schListbaseURL}days/`, page);

//   searchSortByStartDESC = false;
//   searchSortByStartASC = false;
//   searchByKeyWords = false;
//   searchByDaysASC = true;
//   searchByDaysDESC = false;
//   // 清空搜尋關鍵字
//   keywords_el.value = "";
// });

// // 依行程天數排序行程清單(由大到小)，並依照起始日期降冪排序
// sortByDaysDESC_el.addEventListener("click", async function (event) {
//   event.preventDefault();
//   switchToPage1();

//   fetchPublicScheduleList(`${schListbaseURL}daysDESC/`, page);

//   searchSortByStartDESC = false;
//   searchSortByStartASC = false;
//   searchByKeyWords = false;
//   searchByDaysASC = false;
//   searchByDaysDESC = true;
//   // 清空搜尋關鍵字
//   keywords_el.value = "";
// });

// 依關鍵字(行程名稱)搜尋會員自己的行程清單(依照起始日期降冪排序)
search_btn_el.addEventListener("click", async function (event) {
  event.preventDefault();


  const response = await fetch(baseURL + `getAttrsByNameFilter/` + keywords_el.value);
  const respJson = await response.json();
  const attrList = respJson.body;
  console.log(attrList[0]);

  const schListInner = document.getElementById("schListInner");
  schListInner.innerHTML = "";

  attrList.forEach(async attr => {

    const responseOfAttrFirstPic = await fetch(baseURL + `getAttrPics/` + attr.attrId);
    const respJson = await responseOfAttrFirstPic.json();
    const attrPic = respJson.attrPic[0].attrPicData;
    console.log(attrPic);
    const picSrc = "data: image/jpeg;base64," + attrPic;
    let row = document.createElement("div");

    row.classList.add("col-lg-4");
    row.classList.add("mb-4");

    row.innerHTML = `
            <div class="card">
                <img src="${picSrc}"
                    alt="等待圖片載入..." class="card-img-top" 
                    style="max-width: 354.656px; max-height: 236.604px; object-fit: cover;">
                
                <div class="card-body">
                    <h5 class="card-title">${attr.attrName}</h5>
                    <p class="sch-date" style="font-size: 15px">
                      ${attr.attrAddr}
                    </p>
                    <p class="sch-date" style="font-size: 15px">
                      ${attr.attrType}
                    </p>
                    <p class="sch-date" style="font-size: 13px">
                      ${bussTimeString(attr.attrBussTime)}
                      <br>
                      <span><i class="fa-solid fa-copy"></i> ${codeToPriceRange(attr.attrCostRange)}</span>
                    </p>
                    <a href="${baseURL}tmp/Front/attraction/myAttractionEdit.html?attrId=${attr.attrId}" 
                      class="btn btn-outline-success btn-sm viewDetails">查看詳情</a>
                </div>
              </div>
            </div>
            `;
    schListInner.appendChild(row);
    // switchToPage1();
    // // 依需求決定是否分頁，若不需要分頁，則將此行註解解開
    // pagination_el.style.display = "none";

    // let keywords = keywords_el.value;
    // let keywordsURL = `${schListbaseURL}${keywords}/`;
    // fetchPublicScheduleList(keywordsURL, page);

    // searchSortByStartDESC = false;
    // searchSortByStartASC = false;
    // searchByKeyWords = true;
    // searchByDaysASC = false;
    // searchByDaysDESC = false;
  });
})

// 將所有分頁標籤監聽click事件
// for (let pageSelect of pageSelect_els) {

//   pageSelect.addEventListener('click', async function (event) {
//     event.preventDefault();

//     // 將所有active移除(使分頁暗下去)
//     for (let pageSelect of pageSelect_els) {
//       if (pageSelect.classList.contains("active"))
//         pageSelect.classList.remove("active");
//     }
//     // 將被點擊到的選項加上active(使分頁亮起來)
//     event.target.parentNode.classList.add("active");

//     // 依照搜尋條件更換查詢行程內容
//     let sendURL = `${schListbaseURL}all/`; // 預設依照起始日期(新到舊)查詢所有公開行程
//     if (searchSortByStartDESC === true)
//       sendURL = `${schListbaseURL}all/`; // dateStartDESCURL 依起始日期新到舊
//     else if (searchSortByStartASC === true)
//       sendURL = `${schListbaseURL}allASC/`; // dateStartASCURL 依起始日期舊到新
//     else if (searchByDaysASC === true)
//       sendURL = `${schListbaseURL}days/`; // daysASCURL 依行程天數小到大
//     else if (searchByDaysDESC === true)
//       sendURL = `${schListbaseURL}daysDESC/`; // daysDESCURL 依行程天數大到小
//     else if (searchByKeyWords === true)
//       sendURL = `${schListbaseURL}${keywords}/`;

//     fetchPublicScheduleList(sendURL, (event.target.innerText - 1));
//   });
// }
// =============== 分頁查詢行程結束 ================

// ================= 複製一個行程 =================
async function copySchedule(schId) {

  // 一、判斷是否可以複製
  const response = await fetch(`${schListbaseURL}schId/${schId}`);
  const schedule = await response.json();

  if (schedule.schCopy) {

    // 二、若可複製，需先判斷會員已登入
    memberLogin();

    // 拿取會員id
    const responseMem = await fetch(baseURL + `member/getMemId`);
    const member = await responseMem.json();

    // 三、開始複製行程
    const response = await fetch(`${schListbaseURL}copySchedule/${schId}`);
    const copySchedule = await response.json();

    Swal.fire(
      '複製成功！',
      '您已成功複製此行程！',
      'success'
    )
  } else {
    Swal.fire({
      // icon: 'error',
      title: '您沒有複製權限！',
      text: '此行程不開放複製!',
      imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/498706731/android/sticker.png'
    })
  }
}

// -------------- 判斷會員是否登入 ---------------
function memberLogin() {
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
// =============== 檢舉行程 ===============
// 我要檢舉提示字出現與否
function hint(schId) {
  const addReportHint_el = document.querySelector(`#addReport${schId}>div`);
  if (addReportHint_el.classList.contains(classSwitchOff)) {
    addReportHint_el.classList.remove(classSwitchOff);
  } else if (!addReportHint_el.classList.contains(classSwitchOff)) {
    addReportHint_el.classList.add(classSwitchOff);
  }
}


// 檢舉行程
let id;
function addReport(schId) {
  id = schId;
  // 一、檢查是否登入會員，登入會員才可發起檢舉
  memberLogin();
}

// 二、按下檢舉燈箱確認按鈕，提交檢舉資料
function sunmitRep() {

  const data = {
    schId: id,
    schRepCon: repDesc_el.value
  }

  fetch(baseURL + 'schRep/member/add', {
    headers: {
      "content-type": "application/json",
    },
    method: 'POST',
    body: JSON.stringify(data)
  }).then(() => {
    repDesc_el.value = '';
    Swal.fire(
      '提交檢舉成功!',
      '您已提交一份檢舉!',
      'success'
    )
  });
}

// =============== 限制輸入檢舉文字 ===============
const maxReportConLength = 500;
repDesc_el.addEventListener("input", function () {
  // 检查文本内容的长度是否超过最大长度
  if (repDesc_el.value.length > maxReportConLength) {
    // 如果超过了最大长度，截断文本内容
    repDesc_el.value = repDesc_el.value.substring(0, maxReportConLength);

    Swal.fire({
      icon: 'error',
      title: '字數超過500！',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '知道了',
      cancelButtonText: '關閉'
    })
  }
});