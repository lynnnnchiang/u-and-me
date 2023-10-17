let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";
// 行程總天數
var schDuringDays;
// class -on -off -one 屬性
const classSwitchOn = "-on";
const classSwitchOff = "-off";
const classMapOne = "-one";

// 抓取所有會切換頁面使用到的標籤
const backToMySchedule_el = document.querySelector("#backToMySchedule");
// 行程大綱名稱
const mySchName_el = document.querySelector("div.mySchName");
// 行程標籤

// =============== 行程細節相關標籤 =============== 
// 編輯按鈕
const editPen_el = document.querySelector(".editPen");
// 選擇日期天數頁籤
const tab_dateBarLeft_el = document.querySelector(".dateBarTab.-left");
const tab_dateBarRight_el = document.querySelector(".dateBarTab.-right");
// 更換順序
const iswitch_el = document.querySelectorAll("i.switch");
// 刪除第幾天行程
const itrash_el = document.querySelectorAll("i.trash");
// 新增景點按鈕
const addNewAttrbtn_el = document.querySelector("div.addNewAttrbtn");
// 行程細節cell(有很多個，先抓第一個)
const schDetailCells = document.querySelector(".schDetailCell");

// =============== 行程細節時間計算相關標籤(BY Reynolds) =============== 
const mySchDateStart_el = document.getElementById("mySchDateStart");
const mySchDateEnd_el = document.getElementById("mySchDateEnd");

const viewWhichDay_els = document.querySelectorAll(".viewWhichDay");
const viewWhichWeekDay_els = document.querySelectorAll(".viewWhichWeekDay");
const schDeStartTime_els = document.querySelectorAll(".schDeStartTime");
const viewSchDetailsRows = document.querySelector(".viewSchDetailsRows");
// =============== 行程細節時間計算相關標籤(BY Reynolds) =============== 



// =============== 景點搜尋相關標籤 =============== 
// 返回行程細節(箭頭)、搜尋、景點收藏、自訂景點
const tab_back_el = document.querySelector("#tab-back");
const tab_search_el = document.querySelector("#tab-search");
const tab_attrCollect_el = document.querySelector("#tab-attrCollect");
const tab_customize_el = document.querySelector("#tab-customize");

// 頁籤icon
const searchIcons = document.querySelectorAll("i.search");
const searchGlassIcon = document.querySelectorAll("i.search")[0];
const myCollectIcon = document.querySelectorAll("i.search")[1];
const myCustomIcon = document.querySelectorAll("i.search")[2];

// 每個頁籤對應到的頁面
const viewSchDetailsPage = document.querySelector(".viewSchDetailsColumn");
const viewSearchPage = document.querySelector(".viewAttrSearchColumn");
const attrSearchPage = document.querySelector(".attrSearch-result");
const myAttrsPage = document.querySelector(".myAttrs");
const myAttrCollectionList = document.querySelector(".attrCollectionList");
const attrCollectNotFound = document.querySelector(".attrCollectNotFound");
const myCustomizeAttrPage = document.querySelector(".myCustomizeAttr");

// 景點詳情及 GOOGLE MAP
const viewAttrDetailsCard = document.querySelector(".viewAttrDetailsColumn");
const viewGoogleMap = document.querySelector(".map");

// 自訂景點內容標籤
const myAttrName_el = document.querySelector("#myAttrName");
const myAttrAddr_el = document.querySelector("#myAttrAddr");
const attrTypeInput = document.getElementById("attrTypeInput");
const attrIllaInput = document.getElementById("attrIllaInput");

// 景點操作相關按鈕(將景點加入收藏、移除收藏、加入行程及完成自訂景點按鈕)
const addAttrCollect_btn_el = document.querySelector(".addAttrCollect-btn");
const removeAttrCollect_btn_el = document.querySelector(".removeAttrCollect-btn");
const addToSchedule_btn_el = document.querySelector(".addToSchedule-btn");
const myAttrDone_btn_el = document.querySelector(".myAttrDone-btn");

// =============== 元素插入處相關標籤 =============== 
// --------- 行程相關 ------------
const mySchTagsInsert_el = document.querySelector("div.mySchTagsInsert");
const dateSelectCellInsert_el = document.querySelector("div.dateSelect");

// --------- 景點相關 ------------
// 景點搜尋
const attrSearchListInsert_el = document.querySelector(".attrSearchList");
// 景點收藏
const attrCollectionListInsert_el = document.querySelector(".attrCollectionList")
// 單一景點詳情圖片輪播
const attrTotalImgsInsert_el = document.querySelector(".attrTotalImgsInsert");
// 單一景點詳情內容
// 景點id隱藏欄位
const attrIdText_el = document.querySelector("#attrIdText");
// 景點名稱(標題)
const attrNameTitle = document.querySelector("#attrNameTitle");
// 景點評價
const attrComScore = document.querySelector("#attrComScore");
// 景點類型
const attrTypeName = document.querySelector("#attrTypeName");
// 景點地址
const attrAddress = document.querySelector("#attrAddress");
// 景點營業時間(只抓第一個標籤)
const attrBussTime = document.querySelector("#attrBussTimeMon");
// 景點消費價位(低中高價位)
const attrCostRange = document.querySelector("#attrCostRange");
// 景點描述
const attrIlla = document.querySelector("#attrIlla");

// ================== 會使用到的函式 ==================
// =============== 行程細節時間計算相關函式(BY Reynolds) =============== 
// 時間計算
function getDayOfWeek(dateString) {
    const daysOfWeek = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
    const date = new Date(dateString);
    const dayOfWeek = daysOfWeek[date.getDay()];
    // console.log("Date to change: " + dateString + ":" + dayOfWeek);
    return dayOfWeek;
}

//將轉換成字串的日期物件中的 "-" 改成"/"
function modifiyDate(date) {
    return date.replace(/-/g, '/');
}

//將轉換成字串的日期物件中的 "00:00:00" 改成 "X時X分"
function formatStayTime(stayTime) {
    const parts = stayTime.split(':');
    const hours = parseInt(parts[0], 10);
    const minutes = parseInt(parts[1], 10);

    let formattedTime = '';

    if (hours > 0) {
        formattedTime += hours + '小時';
    }

    if (minutes > 0) {
        formattedTime += minutes + '分';
    }

    return formattedTime || '0分'; // 如果没有小时和分钟，默认为0分
}

//將timestampString轉成Date 並調整精度
function parseTimestamp(timestampString) {
    const parts = timestampString.split(' ');
    const datePart = parts[0];
    const timePart = parts[1];

    const dateParts = datePart.split('-');
    const year = parseInt(dateParts[0]);
    const month = parseInt(dateParts[1]) - 1; // 月份从0开始，所以要减1
    const day = parseInt(dateParts[2]);

    const timeParts = timePart.split(':');
    const hour = parseInt(timeParts[0]);
    const minute = parseInt(timeParts[1]);
    const second = parseInt(timeParts[2]);

    return new Date(year, month, day);//後面可加 ,hour , minute, second 調整精度
}

// 2023-09-08
function addTimeToStartTime(startTime, stayTime) {
    // 解析 startTime 字符串为 Date 对象
    const startDate = new Date(startTime);

    // 解析 stayTime 字符串为小时、分钟和秒
    const stayTimeParts = stayTime.split(':');
    const stayHours = parseInt(stayTimeParts[0]);
    const stayMinutes = parseInt(stayTimeParts[1]);

    // 计算新的小时和分钟
    let newHours = startDate.getHours() + stayHours;
    let newMinutes = startDate.getMinutes() + stayMinutes;
    let newDays = startDate.getDate();

    // 处理跨天情况
    if (newMinutes >= 60) {
        newHours += 1;
        newMinutes %= 60;
    }
    if (newHours >= 24) {
        newHours -= 24;
        newDays += 1;
    }

    // 更新日期对象
    startDate.setDate(newDays);
    startDate.setHours(newHours);
    startDate.setMinutes(newMinutes);
    // console.log(startDate);

    return startDate;
}

// 轉換成幾時幾分
function extractHourAndMinute(timeString) {
    // 去掉字符串前后的方括号，然后解析为 Date 对象
    const date = new Date(timeString);

    // 获取时和分的部分
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');

    // 格式化结果
    return `${hours}:${minutes}`;
}

// 轉換成幾月幾日
function extractMonthAndDate(timeString) {
    // 去掉字符串前后的方括号，然后解析为 Date 对象
    const date = new Date(timeString);

    // 获取时和分的部分
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const dates = date.getDate().toString().padStart(2, '0');

    // 格式化结果
    return `${month}月${dates}日`;
}

function formatDateToYYYYMMDD(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始，需要加1并补齐两位
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
}
//---------------------------------------------------------------------------
let travelIconCount = 0;
let countedScheDetailsBase = 0;
async function addDailySchedule(restScheDetails) {
    //拿第一個細節的起始時間當今天開始時間
    //迴圈塞入各個行程細節 直到下一個行程的日期不是本日
    //期間 計算行程的停留時間 換算結束時間
    //              透過交通方式計算行程間的交通距離 交通時間
    //結束時間=開始時間+停留時間
    //開始時間=結束時間+交通時間
    let todayWaypoint = [];
    const response = await fetch(baseURL + `schedules/schId/` + restScheDetails[0].schId);
    const schedule = await response.json();
    const startDay = schedule.schStart;
    // console.log(startDay);
    let firstDay = parseTimestamp(startDay + " 0");
    //印出 第幾天 星期幾 本日出發時間
    // viewSchDetailsRows
    let today = parseTimestamp(restScheDetails[0].schdeStarttime);
    // console.log(restScheDetails[0].schdeStarttime);
    // 2023-12-11 10:00:00
    // console.log("today: " + (today));
    // console.log("today: " + formatDateToYYYYMMDD(today));
    let todayDate = new Date(today);
    let startDate = new Date(startDay);
    // console.log("todayDate: " + todayDate);
    // console.log("startDate: " + firstDay);
    let nthDays = (todayDate - firstDay + 24 * 60 * 60 * 1000) / (24 * 60 * 60 * 1000);
    // console.log("nthday: " + nthDays);

    let row = document.createElement("div");
    row.classList.add("schDeStartTimeRows" + nthDays);
    row.classList.add("schDeStartTimeRows");
    row.addEventListener('click', showDayRoute(`${todayWaypoint.join("-")}`));
    row.innerHTML = `
        <div>
            <span class="viewWhichDay">第${nthDays}天 :</span>
            <span class="viewWhichWeekDay">${getDayOfWeek((today + " 0"))}</span>
            <span class="schDeStartTime">出發時間：${extractHourAndMinute(restScheDetails[0].schdeStarttime)}</span>
        </div>
    `;
    viewSchDetailsRows.appendChild(row);


    //一但有變動 重新呼叫本函式更新一天行程
    var schdeStarttime = restScheDetails[0].schdeStarttime;
    let countedScheDetails;

    for (countedScheDetails = 0; countedScheDetails < 8; countedScheDetails++) {
        //如果下一筆行程細節是明天的 結束今天的行程列印


        const schdeId = restScheDetails[countedScheDetails].schdeId;
        const schId = restScheDetails[countedScheDetails].schId;
        const attrId = restScheDetails[countedScheDetails].attrId;
        // const schdeStarttime = restScheDetails[countedScheDetails].schdeStarttime;
        const schdeStaytime = restScheDetails[countedScheDetails].schdeStaytime;
        // console.log("schdeStarttime::" + schdeStarttime);
        // console.log("schdeStaytime::" + schdeStaytime);
        const schdeEndtime = addTimeToStartTime(schdeStarttime, schdeStaytime);
        // console.log("schdeEndtime::" + schdeEndtime);

        const schdeTranstime = restScheDetails[countedScheDetails].schdeTranstime;
        const schdeTrans = restScheDetails[countedScheDetails].schdeTrans;
        const schdeCost = restScheDetails[countedScheDetails].schdeCost;
        const schdeRemark = restScheDetails[countedScheDetails].schdeRemark;
        const responseAttr = await fetch(baseURL + `getAttr?attrId=` + attrId);
        const attr = await responseAttr.json();
        const responseAttrPics = await fetch(baseURL + `getAttrPics/` + attrId);
        const attrPicList = await responseAttrPics.json();
        //取得景點的第一張圖片
        const attrPicture = attrPicList.attrPic[0].attrPicData;

        todayWaypoint.push(`${attr.attrAddr}`);

        let row = document.createElement("div");
        row.innerHTML = `
                <div class="schDetailCell card mb-3" onclick="viewSearchResultOfOneAttr(${attrId});">
                    <div class="row g-0">
                        <div class="attrPic col-md-4">
                            <img src="data:image/jpeg;base64,${attrPicture}"
                                class="img-fluid rounded-start"
                                alt="..." style="width: 140.219px;height: 92.208px;object-fit: cover;">
                            <div class="schDetailOrder"><span class="schDetailOrder">${countedScheDetails + 1}</span>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="schDetailsCardBody">
                                <p class="schTimes"><span class="stayTimes">${formatStayTime(schdeStaytime)}</span> |
                                    <span class="startTimes">${extractHourAndMinute(schdeStarttime)}</span> - <span
                                        class="endTimes">${extractHourAndMinute(schdeEndtime)}</span>
                                </p>
                                <h5 class="attrName">
                                    <div class="attrNameInSchDetail">
                                        ${attr.attrName}
                                    </div>
                                </h5>
                                <p class="attrAddr">
                                    <small class="text-body-secondary attrAddrWaypoints"
                                        id="attrAddr${countedScheDetailsBase + countedScheDetails + 1}">${attr.attrAddr}</small>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            `;

        schdeStarttime = schdeEndtime;

        // console.log("NEW START TIME: " + schdeStarttime);
        viewSchDetailsRows.appendChild(row);


        // 2023/09/11========================================================================================
        var parentElement = row.parentNode;
        var children = Array.from(parentElement.children);
        var index = children.indexOf(row);
        // console.log("這是父元素的第 " + (index + 1) + " 個子元素。");

        if (index > 1 && !row.previousSibling.classList.contains("schDeStartTimeRows")) {
            console.log("Not the first Row");
            // let row1 = document.createElement("div");

            // row1.innerHTML = `<div class="transTotalTime" id="transTotalTime${++travelIconCount}">
            //     <span class="selectTransMode" >
            //         <select id="travelIconCount${travelIconCount}" style="display: inline-block;"
            //          class="travelIcon form-select form-select-lg mb-3"
            //             aria-label="Large select example" onchange="mapApiBetw2(${travelIconCount})">
            //             <option selected value="DRIVING">&#x1F697;</option>
            //             <option value="WALKING">&#x1F6B6;</option>
            //             <option value="BICYCLING">&#x1F6B2;</option>
            //             <option value="TRANSIT">&#x1F68C;</option>
            //         </select>
            //     </span>
            // </div>`;
            // row1.innerHTML="";

            let row1 = document.createElement("div");
            row1.classList.add("transTotalTime");
            row1.id = `transTotalTime${++travelIconCount}`;

            row1.innerHTML = `
                <span class="selectTransMode">
                    <select id="travelIconCount${travelIconCount}"
                     class="travelIcon form-select form-select-lg mb-3"
                        aria-label="Large select example" onchange="reloadMapApiBetw2(${travelIconCount})">
                        <option selected value="DRIVING">&#x1F697;</option>
                        <option value="WALKING">&#x1F6B6;</option>
                        <option value="BICYCLING">&#x1F6B2;</option>
                        <option value="TRANSIT">&#x1F68C;</option>
                    </select>
                </span>
                `;

            row.insertAdjacentElement("beforebegin", row1);
            // row.appendChild(row1);
            mapApiBetw2(travelIconCount);
            // setTimeout(()=>{
            //     const row1_el = document.getElementById("transTotalTime"+travelIconCount);
            // // 查找包含类名 "totalTime" 的子元素
            // var totalTimeElement = document.querySelector(".totalTime");

            // // 获取子元素的文本内容
            // var timeValue = totalTimeElement.textContent;
            // console.log("row1_el: "+parseTimeString(timeValue));
            // },500);
            // schdeStarttime = addTimeToStartTime(parseTimestamp2(schdeEndtime), parseTimeString(timeValue));
            // console.log(schdeStarttime);
        }

        // 2023/09/11========================================================================================

        //將TimeStampString轉為Date 且可以直接比大小
        //測試只比較日期的比大小 輸出應為相等
        if (restScheDetails[countedScheDetails + 1] == undefined ||
            parseTimestamp(restScheDetails[countedScheDetails].schdeStarttime)
            < parseTimestamp(restScheDetails[countedScheDetails + 1].schdeStarttime)
        ) {
            break;
        }
    }
    console.log(todayWaypoint);
    const target = document.querySelector(`.schDeStartTimeRows${nthDays}`);
    const span = document.createElement("span");
    span.classList.add("showDayRoute");
    span.innerHTML = `
            <button onclick="showDayRoute('` + todayWaypoint.join("-") + `')">本日路線</button>
        `;
    target.appendChild(span);

    console.log(todayWaypoint);
    // <div class="addNewAttrbtn" onclick="addNewAttrbtnOnclick();">＋新增景點</div>
    const addNewAttrbtn = document.createElement("div");
    addNewAttrbtn.classList.add("addNewAttrbtn");
    // addNewAttrbtn.onclick = "addNewAttrbtnOnclick()";
    addNewAttrbtn.addEventListener("click", function () {
        indexOfNewScheDetail = this.classList.toString().replace("addNewAttrbtn addNewAttrbtn", "");

        addNewAttrbtnOnclick();
    });
    addNewAttrbtn.innerText = "＋新增景點";
    viewSchDetailsRows.appendChild(addNewAttrbtn);
    // console.log("DO NEW A BTN");

    countedScheDetailsBase = travelIconCount;

    return countedScheDetails;
}
//--------------------------------------------------------------
// var indexOfNewScheDetail;
// 
// function parseTimeString(timeString) {
//     // 解析时间字符串
//     const timeParts = timeString.match(/(\d+) 小時 (\d+) 分鐘/);

//     const hours = parseInt(timeParts[1]);
//     const minutes = parseInt(timeParts[2]);

//     // 使用 padStart 函数确保小时和分钟有两位数
//     const formattedHours = hours.toString().padStart(2, '0');
//     const formattedMinutes = minutes.toString().padStart(2, '0');

//     // 创建时间格式字符串 "HH:mm"
//     const timeFormat = `${formattedHours}:${formattedMinutes}`;

//     return timeFormat;
// }
// //用來刷新一天行程的所有開始結束時間
// //需要的參數或物件有 :  今天是第幾天 每一個行程細節的區塊物件(時間)
// //                      第一個行程的開始時間 每個路線的旅行時間 每個行程細節的停留時間
// function renewScheDeTimes(restScheDetails) {
//     restScheDetails[0].schdeStarttime
// }
// const attrIdText_el = document.getElementById("attrIdText");
// addToSchedule_btn_el.addEventListener("click", async function () {
//     //day  = indexOfNewScheDetail
//     // const responseOfOneAttr = await fetch(getAttrByAttrIdURL + attrId);
//     // const attr = await responseOfOneAttr.json();
//     // attrNameTitle.innerText = attr.attrName;
//     // attrComScore.innerText = generateRandomNumber();
//     // attrTypeName.innerText = attr.attrType;
//     // attrAddress.innerText = attr.attrAddr;
//     // attrBussTime.innerText = bussTimeString(attr.attrBussTime);
//     // attrCostRange.innerText = codeToPriceRange(attr.attrCostRange);
//     // attrIlla.innerText = attr.attrIlla;

//     // // console.log("LatLng: " + attr.attrLat + "::" + attr.attrLon);
//     // // 將 attr.attrLat 和 attr.attrLon 轉換為數字
//     // // 假設你有一個經緯度
//     // var latitude = attr.attrLat; // 緯度
//     // var longitude = attr.attrLon; // 經度

//     //用多種方法把scheduleDetail存入資料庫 用來後面重新刷新一天行程
//     var url = window.location.href;
//     var urlParams = new URLSearchParams(url.split('?')[1]);
//     // urlParams = urlParams.split('#')[0];
//     var schId = urlParams.get("schId");
//     var attrId = attrIdText_el.innerText;
//     const saveScheDetail = {
//         schId: schId,
//         attrId: attrId,
//         schdeStaytime: '01:00:00',
//         schdeStarttime: "2023-08-15 11:15:00",
//         schdeTranstime: "01:00:00",
//         schdeTrans: 1,
//         schdeCostname: null,
//         schdeCost: null,
//         schdeRemark: null
//     };
//     console.log(saveScheDetail.attrId);
//     console.log(saveScheDetail);
//     const response = await fetch(baseURL + `schDetails/update   `, saveScheDetail);
//     const scheDetail = await response.json();
// })

// --------------------- 顯示總天數行程所有路線 ---------------------
// function showAllRoute() {
//     calculateMultipleTravelTimes(getAllWaypoints(),"DRIVING");
// }
// ---------------------- 顯示一天行程所有路線 ----------------------
function showDayRoute(inputString) {
    const waypointsParts = inputString.split('-');
    let waypoints = [];
    for (let waypointsPart of waypointsParts) {
        waypoints.push(waypointsPart);
    }
    console.log(waypoints);
    calculateMultipleTravelTimes(waypoints, "DRIVING");
}


// 幾月幾日直接+1天
function addOneDay(dateString) {
    // 将日期字符串转换为 Date 对象
    const date = new Date(dateString);

    // 将日期增加一天（24小时）
    date.setDate(date.getDate() + 1);

    // 提取月份和日期
    const month = date.getMonth() + 1; // 月份是从0开始的，所以要加1
    const day = date.getDate();

    // 将月份和日期格式化成两位数
    const formattedMonth = month.toString().padStart(2, '0');
    const formattedDay = day.toString().padStart(2, '0');

    // 构建新的日期字符串
    const newDateString = `${formattedMonth}月${formattedDay}日`;

    return newDateString;
}

// =============== 行程細節時間計算相關函式(BY Reynolds)結束 =============== 
// 景點評價(3.5~5.0)
function generateRandomNumber() {
    const min = 3.5; // 最小值
    const max = 5.0; // 最大值

    // 生成随机数
    const randomNumber = Math.random() * (max - min) + min;

    // 使用 toFixed 方法四舍五入到小数点第一位
    const roundedNumber = Number(randomNumber.toFixed(1));

    return roundedNumber;
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


// ================== 載入行程編輯頁面 ================== //
document.addEventListener("DOMContentLoaded", async function () {


    // 關閉景點搜尋內其他頁籤內容
    switchSearchPagesAddOff();
    switchAttrDetailsAndMapOff();

    // RN's LOADING function
    // 获取当前页面的 URL
    const currentURL = window.location.href.replace("#", "");
    // 创建一个 URLSearchParams 对象，传入查询参数部分
    const urlSearchParams = new URLSearchParams(currentURL.split('?')[1]);
    // 使用 get() 方法来获取特定查询参数的值
    const schId = urlSearchParams.get('schId');

    //透過schId 獲取整個schedule物件
    const response = await fetch(baseURL + `schedules/schId/${schId}`);
    const schedule = await response.json();
    // console.log("Schedule ID: " + schId);

    // 顯示行程名稱
    mySchName_el.innerText = schedule.schName;

    // 顯示行程標籤
    findTagsInSchedule(schId);

    //行程開始日期 結束日期
    var schStartDate = new Date(schedule.schStart);
    var schEndDate = new Date(schedule.schEnd);
    var schStart = schedule.schStart;
    var schEnd = schedule.schEnd;
    schDuringDays = ((schEndDate - schStartDate) / (1000 * 60 * 60 * 24)) + 1;

    //將起始日期及結束日期放在行程最上方
    mySchDateStart_el.innerText = modifiyDate(schStart);
    mySchDateEnd_el.innerText = modifiyDate(schEnd);

    // const viewWhichDay_els = document.querySelectorAll(".viewWhichDay");第幾天
    // const viewWhichWeekDay_els = document.querySelectorAll(".viewWhichWeekDay");周幾
    // const schDeStartTime_els = document.querySelectorAll(".schDeStartTime");今天的起始時間

    //取出本行程所有的行程細節
    const response1 = await fetch(baseURL + `schDetails/${schId}`);
    const schDetails = await response1.json();
    console.log(schDetails);
    if (schDetails.length === 0) {
        console.log("schDetails is null");
        let todayWaypoint = [];
        const response = await fetch(baseURL + `schedules/schId/${schId}`);
        const schedule = await response.json();
        const startDay = schedule.schStart;
        // console.log(startDay + " 0");
        // 2023-09-19 0
        let firstDay = parseTimestamp(startDay + " 0");
        console.log(firstDay);
        // Tue Sep 19 2023 00:00:00 GMT+0800 (台北標準時間)

        //印出 第幾天 星期幾 本日出發時間
        // viewSchDetailsRows
        let today = parseTimestamp(startDay+" 08:00:00");
        console.log("today: " + (today));
        // Tue Sep 19 2023 00:00:00 GMT+0800 (台北標準時間)
        // console.log("today: " + formatDateToYYYYMMDD(today));
        let todayDate = new Date(today);
        let startDate = new Date(startDay);
        // console.log("todayDate: " + todayDate);
        // console.log("startDate: " + firstDay);
        let nthDays = (todayDate - firstDay + 24 * 60 * 60 * 1000) / (24 * 60 * 60 * 1000);
        // console.log("nthday: " + nthDays);
    
        let row = document.createElement("div");
        row.classList.add("schDeStartTimeRows" + nthDays);
        row.classList.add("schDeStartTimeRows");
        row.addEventListener('click', showDayRoute(`${todayWaypoint.join("-")}`));
        row.innerHTML = `
            <div>
                <span class="viewWhichDay">第${nthDays}天 :</span>
                <span class="viewWhichWeekDay">${getDayOfWeek((today + " 0"))}</span>
                <span class="schDeStartTime">出發時間：${extractHourAndMinute(startDay+" 08:00:00")}</span>
            </div>
        `;
        viewSchDetailsRows.appendChild(row);

        const addNewAttrbtn = document.createElement("div");
        addNewAttrbtn.classList.add("addNewAttrbtn");
        // addNewAttrbtn.onclick = "addNewAttrbtnOnclick()";
        addNewAttrbtn.addEventListener("click", function () {
            indexOfNewScheDetail = this.classList.toString().replace("addNewAttrbtn addNewAttrbtn", "");
    
            addNewAttrbtnOnclick();
        });
        addNewAttrbtn.innerText = "＋新增景點";
        viewSchDetailsRows.appendChild(addNewAttrbtn);

    } else {
        // console.log("STAY time: " + schDetails[0].schdeStaytime);
        // console.log("STAY time: " + formatStayTime(schDetails[0].schdeStaytime));
        const stayTimes = document.querySelectorAll(".stayTimes");
        let count = 0;
        //將所有行程細節stayTime顯示
        for (let stayTime of stayTimes) {
            stayTime.innerText = formatStayTime(schDetails[count++].schdeStaytime);
        }
        // schDetails.shift();
        // console.log(schDetails);
        const result = await addDailySchedule(schDetails);
        for (let i = 0; i < result + 1; i++) {
            schDetails.shift();
        }
        // result.then(data=>{
        //     console.log(data);
        //     for(let i = 0;i<data+1;i++){
        //         schDetails.shift();
        //     }
        // });
        // console.log(schDetails);
        addDailySchedule(schDetails);
    }
    // console.log("!!!!!!!: " + schDetails);

    // ====================== 生成行程細節天數tab內容 ======================

    // 依據schDuringDays生成tab頁籤
    dateSelectCellInsert_el.innerHTML = "";
    let currentDate = schedule.schStart;
    for (let i = 1; i <= schDuringDays; i++) {
        // console.log(i);
        let dateCell = document.createElement("div");
        dateCell.classList.add("dateSelectCell");
        dateCell.id = `day${i}`;

        dateCell.innerHTML = `
            <i class="fa-solid fa-arrow-right-arrow-left switch"></i>
            <div class="whichDate" id="date${i}">${extractMonthAndDate(currentDate)}</div>
            <div class="whichDays">第${i}天</div>
            <i class="fa-regular fa-trash-can trash"></i>
        `
            ;
        currentDate = addTimeToStartTime(currentDate, "24:00:00");
        // currentDate = addOneDay(currentDate);
        dateSelectCellInsert_el.appendChild(dateCell);
    }



});


//  ================== 行程細節頁面(待新增) ================== //
// 按下選擇天數tab，移動左右天數欄
// 向左移
let translateXValue = -60;
let totalDisplacement = 0;
tab_dateBarLeft_el.onclick = () => {
    totalDisplacement += translateXValue;
    for (let i = 1; i <= schDuringDays; i++) {
        let dateSelectCell = document.querySelector("div#day" + i);
        dateSelectCell.style.transform = `translateX(${totalDisplacement}px)`;
    }
}

// 向右移
let count_click_rightbtn = 0;
tab_dateBarRight_el.onclick = () => {
    // 要讓元素從當下位置開始向右移動
    totalDisplacement -= translateXValue;
    for (let i = 1; i <= schDuringDays; i++) {
        let dateSelectCell = document.querySelector("div#day" + i);
        dateSelectCell.style.transform = `translateX(${totalDisplacement}px)`;
    }
}

// BBBBBBBBBBBBBBBBBBBBBBBUUUUUUUUUUUUUUUUUUUUUUGGGGGGGGGGGGGGGGGGGGGGGG
// 移動到底部時，按鈕不會再有作用
// 获取要测量距离的两个元素
// const element1 = document.getElementById('element1');
// const element2 = document.getElementById('element2');

// 计算两个元素之间的距离
function calculateDistance(element1, element2) {
    const rect1 = element1.getBoundingClientRect();
    const rect2 = element2.getBoundingClientRect();

    const x1 = rect1.left + rect1.width / 2;
    const x2 = rect2.left + rect2.width / 2;

    const distance = Math.abs(x2 - x1);
    return distance;
}

// // 触发事件
// function handleDistanceCalculation() {
//     const distance = calculateDistance();

//     // 在这里可以根据距离触发事件
//     if (distance < 50) {
//         alert('距离小于50像素！');
//     } else {
//         alert('距离大于50像素。');
//     }
// }

// LBTN.onclick{
//     calculateDistance(l,l)
//     if(cal<=0){
//         LBtn.removeClass
//     }else{
//         LBtn.addClass
//     }
// }

// RBTN.onclick{
//     calculateDistance(r,r)
//     if(cal<=0){
//         RBtn.removeClass
//     }else{
//         RBtn.addClass
//     }

// }

// 给元素添加事件处理程序
// element1.addEventListener('click', handleDistanceCalculation);
// element2.addEventListener('click', handleDistanceCalculation);
// BBBBBBBBBBBBBBBBBBBBBBBUUUUUUUUUUUUUUUUUUUUUUGGGGGGGGGGGGGGGGGGGGGGGG


// 在行程細節中按下新增景點按鈕
async function addNewAttrbtnOnclick() {
    memberLoginOrNot();
    const currentURL = window.location.href.replace('#', '');
    const urlSearchParams = new URLSearchParams(currentURL.split('?')[1]);
    const schId = urlSearchParams.get('schId');

    const response = await fetch(baseURL + `member/getMemId`);
    const member = await response.json();
    const memId = member.memId;




    const res = await fetch(baseURL + `schedules/schId/` + schId);
    const sche = await res.json();
    if (memId !== sche.memId) {
        Swal.fire({
            title: '您沒有編輯權限！',
            // icon: 'error',
            confirmButtonText: '知道了',
            confirmButtonColor: '#d33',
            cancelButtonText: '取消',
            imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/498706731/android/sticker.png'
        }).then(() => {
            return;
        });
        return;
    }

    // 關閉行程細節Page
    if (!viewSchDetailsPage.classList.contains(classSwitchOff)) {
        viewSchDetailsPage.classList.add(classSwitchOff);
    }
    // 關閉開啟中的景點詳細Page及景點搜尋相關Page
    switchSearchPagesAddOff();
    switchAttrDetailsAndMapOff();
    // 開啟景點搜尋Page
    if (viewSearchPage.classList.contains(classSwitchOff)) {
        viewSearchPage.classList.remove(classSwitchOff);
    }
    // 將所有頁籤icon變灰色
    switchIconsRemoveOn();
    // 搜尋放大鏡變色
    searchGlassIcon.classList.add(classSwitchOn);
    // 顯示搜尋欄及搜尋結果
    attrSearchPage.classList.remove(classSwitchOff);
}


//  ================== 行程細節頁面結束 ================== //


//  ================== 景點搜尋相關頁面 ================== //
// 從景點搜尋頁面中按下箭頭返回鍵，返回行程細節頁面
tab_back_el.addEventListener("click", function () {
    // 關閉所有景點搜尋相關頁面
    switchSearchPagesAddOff();
    switchAttrDetailsAndMapOff();
    // 顯示查看行程細節頁面
    viewSchDetailsPage.classList.remove(classSwitchOff);
    // 顯示加入收藏按鈕
    showAddAttrToCollectionBtn();
});

// ---- 換頁籤處理 ----//
// 將所有icon顏色移除
function switchIconsRemoveOn() {
    for (let searchIcon of searchIcons) {
        // 將所有icon都變灰色
        if (searchIcon.classList.contains(classSwitchOn)) {
            searchIcon.classList.remove(classSwitchOn);
        }
    }
}

// 將所有頁籤頁面display: none
function switchSearchPagesAddOff() {
    if (!attrSearchPage.classList.contains(classSwitchOff)) {
        attrSearchPage.classList.add(classSwitchOff);
    }
    if (!myAttrsPage.classList.contains(classSwitchOff)) {
        myAttrsPage.classList.add(classSwitchOff);
    }
    if (!myCustomizeAttrPage.classList.contains(classSwitchOff)) {
        myCustomizeAttrPage.classList.add(classSwitchOff);
    }
}

// 將查看單一景點詳情頁面及 GOOGLE MAP(查看單一景點模式) 關閉
function switchAttrDetailsAndMapOff() {
    if (!viewAttrDetailsCard.classList.contains(classSwitchOff)) {
        viewAttrDetailsCard.classList.add(classSwitchOff);
    }
    if (viewGoogleMap.classList.contains(classMapOne)) {
        viewGoogleMap.classList.remove(classMapOne);
    }
}
// ---- 換頁籤處理結束 ----//

// ================== 景點搜尋 ================== //
// 按下景點搜尋頁籤時換到景點搜尋頁面
tab_search_el.addEventListener("click", function (e) {
    e.preventDefault();
    // 將當前icon變色
    switchIconsRemoveOn();
    searchGlassIcon.classList.add(classSwitchOn);
    // 關閉其他頁籤內容
    switchSearchPagesAddOff();
    switchAttrDetailsAndMapOff();
    // 顯示搜尋欄及搜尋結果
    attrSearchPage.classList.remove(classSwitchOff);
    // 顯示加入收藏按鈕
    showAddAttrToCollectionBtn();
});

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

function getAllWaypoints() {
    const waypoints = document.querySelectorAll(".attrAddrWaypoints");
    let waypointsContents = [];
    for (let waypoint of waypoints) {
        waypointsContents.push(waypoint.textContent);
    }
    return waypointsContents;
}

// 點擊到schDetailCell(單一行程細節Cell)、attrCell(景點搜尋、景點收藏Cell)時，出現景點詳細頁面
async function viewSearchResultOfOneAttr(attrId) {
    //-----------------------------------------------------------------------------
    //Reynolds0918
    // 抓取會員id
    memberLoginOrNot();
    const response2 = await fetch(baseURL + `member/getMemId`);
    const member = await response2.json();
    const memId = member.memId;
    if (memId !== undefined) {
        // 抓取景點id
        // let attrId = attrIdText_el.innerText;
        const response = await fetch(baseURL + `attrCol/ifMemContainAttrInCol/` + attrId);
        const attrCollectionDTO = await response.json();
        const collecttionId = attrCollectionDTO.body.collectionId;
        console.log("ANS: " + collecttionId);

        const addBtn = document.querySelector(".addAttrCollect-btn");
        const removeBtn = document.querySelector(".removeAttrCollect-btn");
        if (collecttionId === undefined || collecttionId === null) {
            console.log("此會員沒有收藏該景點: ");
            // showAddAttrToCollectionBtn();
            addBtn.classList.remove("-off");
            removeBtn.classList.add("-off");
            // viewSearchResultOfOneAttr(attrId);
        }
        else {
            console.log("!!此會員有收藏該景點: ");
            // showRemoveAttrFromCollectionBtn();
            addBtn.classList.add("-off");
            removeBtn.classList.remove("-off");
            // viewSearchResultOfOneAttr(attrId);
        }
    }
    else {

    }
    //-----------------------------------------------------------------------------

    // 顯示單一景點詳情頁面
    viewAttrDetailsCard.classList.remove(classSwitchOff);
    // 開啟 GOOGLE MAP(查看單一景點模式)
    viewGoogleMap.classList.add(classMapOne);

    // 藉由景點id查詢相應所有圖片
    const responseOfAttrAllPic = await fetch(getAttrPicsByAttrIdURL + attrId);
    const attrPicList = await responseOfAttrAllPic.json();
    const pics = attrPicList.attrPic;

    // 清空景點詳情圖片
    attrTotalImgsInsert_el.innerHTML = "";

    // 取出每一張圖片，放入輪播img標籤中
    for (let pic of pics) {

        let imgs = document.createElement("div");
        imgs.classList.add("carousel-item");

        imgs.innerHTML = `
            <img class="attrImgs"
            style="width: 318.3px;height: 208.3px;object-fit: cover;"
            src="data: image/jpeg;base64,${pic.attrPicData}"
            alt="...">`;

        attrTotalImgsInsert_el.appendChild(imgs);

        // 將輪播的第一個圖片<div> class加上active屬性
        let firstImgDiv = document.querySelector("div.carousel-item:first-child");
        firstImgDiv.classList.add("active");
    }

    // 更改景點詳情內容
    const responseOfOneAttr = await fetch(getAttrByAttrIdURL + attrId);
    const attr = await responseOfOneAttr.json();
    attrIdText_el.innerHTML = attrId;
    attrNameTitle.innerText = attr.attrName;
    attrComScore.innerText = generateRandomNumber();
    attrTypeName.innerText = attr.attrType;
    attrAddress.innerHTML = attr.attrAddr;
    attrBussTime.innerText = bussTimeString(attr.attrBussTime) ? bussTimeString(attr.attrBussTime) : attr.attrBussTime;
    attrCostRange.innerText = codeToPriceRange(attr.attrCostRange);
    attrIlla.innerHTML = attr.attrIlla;

    // console.log("LatLng: " + attr.attrLat + "::" + attr.attrLon);
    // 將 attr.attrLat 和 attr.attrLon 轉換為數字
    // 假設你有一個經緯度
    var latitude = attr.attrLat; // 緯度
    var longitude = attr.attrLon; // 經度
    console.log("經度: " + latitude);
    console.log("緯度: " + longitude);

    // 創建一個包含經緯度信息的 Place 物件
    var locationPlace = {
        geometry: {
            location: new google.maps.LatLng(latitude, longitude)
        }
    };

    // 創建地圖選項
    var mapOptions = {
        center: locationPlace.geometry.location, // 設置地圖中心為 Place 的位置
        zoom: 16, // 放大級別，根據需求調整
    };

    // 創建地圖對象
    var map = new google.maps.Map(document.getElementById('map'), mapOptions);

    // 在地圖上放置標記
    var marker = new google.maps.Marker({
        position: locationPlace.geometry.location,
        map: map,
        title: '自定義位置' // 可以自定義標記的標題
    });

}


// 關閉單一景點詳情頁面(按下叉叉時觸發)
function closeViewAttrDetailsCard() {
    // 關閉單一景點詳情頁面
    viewAttrDetailsCard.classList.add(classSwitchOff);
    // 開啟 GOOGLE MAP(查看單一景點模式)
    viewGoogleMap.classList.remove(classMapOne);
}

// ================== 景點收藏 ================== //

// fetch to Controller路徑
// 列出所有景點收藏
let myAttrCollectionURL = baseURL + "attrCol/getAttrsFromCollectionByMemId/";
// 依據景點編號查詢景點資訊
let getAttrByAttrIdURL = baseURL + "getAttr?attrId=";
// 依據景點編號查詢所有景點圖片
let getAttrPicsByAttrIdURL = baseURL + "getAttrPics/";


// 按下我的景點收藏時換到景點收藏頁面
tab_attrCollect_el.addEventListener("click", async function (e) {
    e.preventDefault();
    // 將當前icon變色
    switchIconsRemoveOn();
    myCollectIcon.classList.add(classSwitchOn);
    // 關閉其他頁籤內容
    switchSearchPagesAddOff();
    switchAttrDetailsAndMapOff();
    // 顯示景點收藏清單頁面
    myAttrsPage.classList.remove(classSwitchOff);
    if (myAttrCollectionList.classList.contains(classSwitchOff)) {
        myAttrCollectionList.classList.remove(classSwitchOff);
    }
    // 顯示移除收藏按鈕
    showRemoveAttrFromCollectionBtn();

    // 根據會員id動態生成景點收藏清單
    const response = await fetch(baseURL + `member/getMemId`);
    const member = await response.json();
    const memId = member.memId;

    FindAllAttrCollectionList(memId);

});


// 查看景點收藏，動態生成景點收藏清單
async function FindAllAttrCollectionList(memId) {
    try {
        // 查詢會員專屬的景點收藏清單
        const responseOfCollection = await fetch(myAttrCollectionURL + memId);
        const myAttrCollectList = await responseOfCollection.json();

        // 如果找不到收藏，則顯示您沒有收藏景點的頁面
        if (myAttrCollectList.length === 0) {
            attrCollectNotFound.classList.remove(classSwitchOff);
        }

        // 清空景點收藏清單插入處所有的資料
        attrCollectionListInsert_el.innerHTML = "";

        myAttrCollectList.forEach(async attrCollect => {

            // 依據每個景點收藏清單的景點id，查詢對應的景點詳細資訊
            // 找出每一個景點收藏中景點的資料：如景點名稱
            if (attrCollect == null)
                return;
            const responseOfOneAttr = await fetch(getAttrByAttrIdURL + attrCollect.attrId);
            const attr = await responseOfOneAttr.json();

            // 景點的第一張圖片編號、景點的第一張圖片
            const responseOfAttrFirstPic = await fetch(getAttrPicsByAttrIdURL + attrCollect.attrId);
            const attrPicList = await responseOfAttrFirstPic.json();
            const pics = attrPicList.attrPic;

            // 動態生成每一個景點資訊框
            let row = document.createElement("div");
            row.innerHTML = `
                    <div class="attrCell card mb-3" onclick="viewSearchResultOfOneAttr(${attrCollect.attrId})" id="attrId${attrCollect.attrId}">
                    <div class="row g-0">
                        <div class="col-md-8">
                            <div class="attrCardBody">
                                <h5 class="attrName">
                                    <div id="attrName">
                                        ${attr.attrName}
                                    </div>
                                </h5>
                            </div>
                        </div>
                        <div class="attrPic col-md-4">
                            <img src="data: image/jpeg;base64,${pics[0].attrPicData}"
                                class="img-fluid rounded-end" alt="..."
                                style="width: 140.219px;height: 92.208px;object-fit: cover;">
                        </div>
                    </div>
                </div>`;

            attrCollectionListInsert_el.appendChild(row);
        });

    } catch (error) {
        console.error("Error fetching AttrCollectList:", error);
    }
}


// 新增景點收藏(在景點搜尋頁面)
addAttrCollect_btn_el.onclick = async () => {

    // 抓取會員id
    const response = await fetch(baseURL + `member/getMemId`);
    const member = await response.json();
    const memId = member.memId;

    // 抓取景點id
    let attrId = attrIdText_el.innerText;

    const collectionId = {
        memId: memId,
        attrId: attrId
    }
    const responseCol = await fetch(baseURL + `attrCol/addAttrToCollection`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(collectionId)
    })
    Swal.fire(
        '儲存成功!',
        '已儲存至景點收藏!',
        'success'
    ).then(() => {
        FindAllAttrCollectionList(memId);
    })
    // showRemoveAttrFromCollectionBtn();
    viewSearchResultOfOneAttr(attrId);


}


// 移除景點收藏
removeAttrCollect_btn_el.onclick = async () => {

    // 抓取會員id
    const response = await fetch(baseURL + `member/getMemId`);
    const member = await response.json();
    const memId = member.memId;

    // 抓取景點id
    let attrId = attrIdText_el.innerText;

    const collectionId = {
        memId: memId,
        attrId: attrId
    }

    const responseCol = await fetch(baseURL + `attrCol/removeAttrFromCollection`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(collectionId)
    })
    Swal.fire(
        '移除成功!',
        '已從景點收藏中移除此景點!',
        'success'
    ).then(() => {
        FindAllAttrCollectionList(memId);
    })
    // showAddAttrToCollectionBtn();
    viewSearchResultOfOneAttr(attrId);
}

// TODO...
// 判斷景點是否有收藏，如果無收藏，則顯示儲存景點
// 如果有收藏，則顯示移除收藏
// 顯示加入收藏按鈕
function showAddAttrToCollectionBtn() {
    // 在搜尋及行程細節頁面時點開的話，顯示加入收藏鈕
    if (!viewSearchPage.classList.contains(classSwitchOff) ||
        !viewSchDetailsPage.classList.contains(classSwitchOff)) {
        if (addAttrCollect_btn_el.classList.contains(classSwitchOff)) {
            addAttrCollect_btn_el.classList.remove(classSwitchOff);
            removeAttrCollect_btn_el.classList.add(classSwitchOff);
        }
    }
}

// 顯示移除收藏按鈕
function showRemoveAttrFromCollectionBtn() {
    // 在收藏頁面點開的話，會顯示移除收藏鈕
    if (!myAttrsPage.classList.contains(classSwitchOff)) {
        if (removeAttrCollect_btn_el.classList.contains(classSwitchOff)) {
            removeAttrCollect_btn_el.classList.remove(classSwitchOff);
            addAttrCollect_btn_el.classList.add(classSwitchOff);
        }
    }
}

// ================== 自訂景點 ================== //
// 按下自訂景點時換到自訂景點頁面
tab_customize_el.addEventListener("click", function (e) {
    e.preventDefault();
    // 將當前icon變色
    switchIconsRemoveOn();
    myCustomIcon.classList.add(classSwitchOn);
    // 關閉其他籤內容
    switchSearchPagesAddOff();
    switchAttrDetailsAndMapOff();
    // 顯示自訂景點頁面
    myCustomizeAttrPage.classList.remove(classSwitchOff);
});

// 自訂景點中新增景點圖片
const attrPicFilesInput = document.querySelector("#attrPicFilesInput");
const myAttrPicsPreview = document.querySelector("#attrPicsPreview");

attrPicFilesInput.addEventListener('change', async (event) => {
    myAttrPicsPreview.innerHTML = '';
    const files = event.target.files;

    let count = 0;
    for (const file of files) {
        var fileReader = await new FileReader();
        fileReader.onload = async event => {
            const base64String = btoa(event.target.result);
            const imgdata = {
                fileOrder: count,
                attrPicData: base64String
            }

            const imageContainer = createImageContainer_preview(imgdata, count);

            attrPicsPreview.appendChild(imageContainer);
        };
        console.log(++count);
        await fileReader.readAsBinaryString(file);
    }
});

//包裝預覽圖容器及生成叉叉可以取消上傳
function createImageContainer_preview(pic) {
    // console.log("Creating preview");
    const imageContainer = document.createElement("div");
    imageContainer.classList.add("image-container");
    imageContainer.style.zIndex = '0';
    imageContainer.style.position = 'relative';
    const image = document.createElement("img");
    image.src = "data: image/jpg;base64," + pic.attrPicData;
    image.style.maxWidth = "318.3px"; // 設定預覽圖最大寬度
    image.style.width = "100%";
    image.style.objectFit = "cover;"// 設定圖片裁切
    image.style.position = "relative";
    image.style.marginTop = "5px";

    const closeButton = document.createElement("span");
    closeButton.classList.add("close-button");
    closeButton.innerHTML = `<i class="fa-solid fa-xmark" style="font-size: 30px;color:white; text-shadow:2px 2px 2px black;"></i>`;
    closeButton.style.zIndex = "1";
    closeButton.style.position = "relative";
    closeButton.style.top = "38px";
    closeButton.style.left = "90%";

    closeButton.addEventListener("click", async function () {
        imageContainer.remove();
        // attrPicFilesInput.value = '';
    });
    imageContainer.appendChild(closeButton);
    imageContainer.appendChild(image);

    return imageContainer;
}

// =============== 限制輸入景點描述文字 ===============
const maxAttrIllaLength = 500;
attrIllaInput.addEventListener("input", function () {
    // 检查文本内容的长度是否超过最大长度
    if (attrIllaInput.value.length > maxAttrIllaLength) {
        // 如果超过了最大长度，截断文本内容
        attrIllaInput.value = attrIllaInput.value.substring(0, maxAttrIllaLength);

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



// 自訂景點內容完成後，顯示新增完成警示框
myAttrDone_btn_el.onclick = async () => {
    let inputAttrBussTime;
    let options = {
        // componentRestrictions: { country: 'tw' } // 限制在台灣範圍
    };

    let siteInput1 = document.querySelector('#myAttrAddr');
    // let siteInput1 = myAttrAddr_el;

    var autocomplete1 = new google.maps.places.Autocomplete(siteInput1, options);
    // 地址的輸入框，值有變動時執行
    autocomplete1.addListener('place_changed', async () => {
        this.place = autocomplete1.getPlace(); // 地點資料存進place

        // 確認回來的資料有經緯度
        if (this.place.geometry) {

            // 改變map的中心點
            let searchCenter = this.place.geometry.location;

            // panTo是平滑移動、setCenter是直接改變地圖中心
            this.map.panTo(searchCenter);

            attrLat = this.place.geometry.location.lat();
            attrLon = this.place.geometry.location.lng();
            inputAttrBussTime = await getBussTime(place.place_id);

            console.log("LAT: " + attrLat);
            console.log("LON: " + attrLon);

            // 在搜尋結果的地點上放置標記
            let marker = new google.maps.Marker({
                // icon: {
                //     path: google.maps.SymbolPath.CIRCLE,
                //     scale: 10
                // },
                position: searchCenter,
                map: this.map
            });

            // info window
            let infowindow = new google.maps.InfoWindow({
                content: this.place.formatted_address
            });
            infowindow.open(this.map, marker);
        }
    });


    let myAttrName = myAttrName_el.value.trim();
    let myAttrAddr = myAttrAddr_el.value.trim();
    let attrIlla = attrIllaInput.value;
    let attrType = attrTypeInput.value;
    const picFiles = attrPicFilesInput.files;

    const newAttr = {
        attrName: myAttrName,
        attrAddr: myAttrAddr,
        attrType: attrType,
        attrLat: attrLat,
        attrLon: attrLon,
        attrBussTime: inputAttrBussTime ? inputAttrBussTime : "尚無資料",
        attrVeriSta: 1,
        attrSta: 1,
        attrCostRange: 2,
        attrRep: 'no report record',
        attrIlla: attrIlla ? attrIlla : '尚無資料'
    };
    const response = await fetch(baseURL + `attrPriv/createPrivateAttr`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newAttr)
    });

    const attrPrivDTO = await response.json();
    let attrBody = attrPrivDTO.body;
    let attrPrivateId = attrBody.attrPrivateId;
    let attrId = attrPrivateId.attrId;
    let memId = attrPrivateId.memId;
    const attrCollectionId = {
        memId: memId,
        attrId: attrId
    }
    const responseCol = await fetch(baseURL + `attrCol/addAttrToCollection`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(attrCollectionId)
    })


    // console.log("開始上傳圖片");
    for (const file of picFiles) {
        try {
            const fileReader = await new FileReader();
            fileReader.onload = async event => {
                const base64Str = btoa(event.target.result);
                await fetch(baseURL + 'insertAttrPictures/' + attrId, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        attrId: attrId,
                        attrPicData: base64Str
                    })
                });
            };

            fileReader.readAsBinaryString(file);

        } catch (error) {
            console.error('Error uploading image:', error);
        }
    }

    if (myAttrName === "" || myAttrAddr === "" || picFiles === null || picFiles.length == 0) {
        Swal.fire({
            icon: 'error',
            title: '新增失敗',
            text: '請檢查資料是否填寫正確!',
        })
    } else {
        Swal.fire(
            '新增成功!',
            '您已新增一個自訂景點!',
            'success'
        ).then(() => {
            location.reload();
        })
    }
}

//  ================== 景點搜尋相關頁面結束 ================== //

//  ===================== 將景點加入行程 ===================== //
addToSchedule_btn_el.addEventListener('click', async function () {

    const currentURL = window.location.href.replace('#', '');
    const urlSearchParams = new URLSearchParams(currentURL.split('?')[1]);
    const schId = urlSearchParams.get('schId');

    const response = await fetch(baseURL + `member/getMemId`);
    const member = await response.json();
    const memId = member.memId;
    const res = await fetch(baseURL + `schedules/schId/` + schId);
    const sche = await res.json();
    if (memId !== sche.memId) {
        Swal.fire({
            title: '您沒有編輯權限！',
            // icon: 'error',
            confirmButtonText: '知道了',
            confirmButtonColor: '#d33',
            cancelButtonText: '取消',
            imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/498706731/android/sticker.png'
        }).then(() => {
            return;
        });
        return;
    }

    let attrId = attrIdText_el.innerText;

    // TODO...目前寫死，尚未轉換成動態寫法(行程細節起始時間、交通時間及停留時間)
    const send_data = {
        schId: schId,
        attrId: attrId,
        schdeStarttime: '2023-12-11 17:30:00',
        schdeStaytime: '01:00:00',
        schdeTranstime: '00:30:00',
        schdeTrans: 1,
        schdeCostname: null,
        schdeCost: null,
        schdeRemark: null
    }
    await fetch(baseURL + 'schDetails/addOne', {
        headers: {
            "content-type": "application/json",
        },
        method: 'POST',
        body: JSON.stringify(send_data)
    })
        .catch(function (error) {
            alert('新增失敗' + error);
            return;
        });

    Swal.fire(
        '儲存成功!',
        '已新增至行程中!',
        'success'
    ).then(() => {
        location.reload();
    });

});


editPen_el.addEventListener("click", async function () {
    const currentURL = window.location.href.replace('#', '');
    const urlSearchParams = new URLSearchParams(currentURL.split('?')[1]);
    const schId = urlSearchParams.get('schId');

    $.ajax({
        url: baseURL + "member/getMemId",
        method: "POST",
        dataType: "JSON",
        success: async function (data) {
            const res = await fetch(baseURL + `schedules/schId/` + schId);
            const sche = await res.json();
            memId = data.memId;
            if (memId !== sche.memId) {
                Swal.fire({
                    title: '您沒有編輯權限！',
                    // icon: 'error',
                    confirmButtonText: '知道了',
                    confirmButtonColor: '#d33',
                    cancelButtonText: '取消',
                    imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/498706731/android/sticker.png'
                });
                return;
            }
        },
        error: async function (status, error) {
            if (status.status === 401) {
                Swal.fire({
                    title: '請先登入會員',
                    text: "將為您導向登入頁面....",
                    // icon: 'error',
                    confirmButtonText: '返回登入頁面',
                    confirmButtonColor: '#d33',
                    cancelButtonText: '取消',
                    imageUrl: 'https://storage.googleapis.com/sticker-prod/RWsnOMnSplAHd5vb10YN/20-1.png'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = baseURL + 'tmp/Front/member/memberLogin.html';
                    }
                    else if (result.dismiss === Swal.DismissReason.cancel) {
                        // 用户点击取消按钮或者点击模态框外部时执行的操作
                        // 这里可以不执行任何操作，或者添加你需要的其他逻辑
                    }
                });
            }
        },
    });

});


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


// 判斷是否為行程建立者，若不是，則不可編輯
async function isLoginAndOwner() {
    // 驗證是否登入會員，及是否為行程建立者
    const currentURL = window.location.href.replace('#', '');
    const urlSearchParams = new URLSearchParams(currentURL.split('?')[1]);
    const schId = urlSearchParams.get('schId');

    $.ajax({
        url: baseURL + "member/getMemId",
        method: "POST",
        dataType: "JSON",
        success: async function (data) {
            const res = await fetch(baseURL + `schedules/schId/` + schId);
            const sche = await res.json();
            memId = data.memId;
            if (memId !== sche.memId) {
                Swal.fire({
                    title: '您沒有編輯權限！',
                    // icon: 'error',
                    confirmButtonText: '知道了',
                    confirmButtonColor: '#d33',
                    cancelButtonText: '取消',
                    imageUrl: 'https://stickershop.line-scdn.net/stickershop/v1/sticker/498706731/android/sticker.png'
                });
                return;
            }
        },
        error: async function (status, error) {
            if (status.status === 401) {
                Swal.fire({
                    title: '請先登入會員',
                    text: "將為您導向登入頁面....",
                    // icon: 'error',
                    confirmButtonText: '返回登入頁面',
                    confirmButtonColor: '#d33',
                    cancelButtonText: '取消',
                    imageUrl: 'https://storage.googleapis.com/sticker-prod/RWsnOMnSplAHd5vb10YN/20-1.png'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = baseURL + 'tmp/Front/member/memberLogin.html';
                    }
                    else if (result.dismiss === Swal.DismissReason.cancel) {
                        // 用户点击取消按钮或者点击模态框外部时执行的操作
                        // 这里可以不执行任何操作，或者添加你需要的其他逻辑
                    }
                });
            }
        },
    });

}


// 行程內的標籤顯示
async function findTagsInSchedule(schId) {
    const response = await fetch(`${baseURL}schTag/schId/${schId}`);
    let schTags = await response.json();
    if (schTags !== null) {
        for (let i = 0; i < schTags.length; i++) {
            let tagRow = document.createElement("div");
            tagRow.classList.add("toggleTags");
            tagRow.id = `mySchTag${schTags[i].schTagId}`;
            tagRow.innerHTML = `${schTags[i].schTagName}<i
            class="fa-solid fa-xmark"></i>`;
            mySchTagsInsert_el.appendChild(tagRow);
        }
    }
}