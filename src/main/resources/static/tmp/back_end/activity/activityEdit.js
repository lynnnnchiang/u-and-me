const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
// 取得查詢activId值
const urlParams = new URLSearchParams(window.location.search);
const activId = urlParams.get('activId');

// 表單中的各輸入框元素
const activIdInput = document.getElementById('activId');
const activPicImage = document.getElementById('activPic');
const activNameInput = document.getElementById('activName');
const activConTextarea = document.getElementById('activCon');
const activStarttimeInput = document.getElementById('activStarttime');
const activEndtimeInput = document.getElementById('activEndtime');
const activStaSelect = document.querySelector('.form-control.select2');
const submitBtn = document.querySelector('button[type="submit"]');
const schRecommendInput1 = document.getElementById('schRecommend1');
const schRecommendInput2 = document.getElementById('schRecommend2');
let originalSchId;
let schId;
let cover;



// 管理員filter


window.addEventListener("load", function (e) {
    this.fetch(baseUrl + 'host/match', {
        method: 'GET'
    }).then(response => {
        if (response.status == 401) {

            this.location.href = baseUrl + 'tmp/back_end/host/hostLogin.html';

        }
    });
})


// 使用 fetch API 發送請求，獲取單筆活動詳細資料
async function fetchActivityDetail() {
    try {
        const response = await fetch(baseUrl + `activity/${activId}`);
        const activityDetail = await response.json();

        const response2 = await fetch(baseUrl + `activityRecommend/${activId}`);
        const recommends = await response2.json();

        // 沒有選擇新圖片，cover將保持舊的圖片數據
        const dataurl = `data:image/png;base64,${activityDetail.activPic}`;
        cover = activityDetail.activPic;

        // 將活動詳細資訊填充到輸入框中
        activIdInput.value = activityDetail.activId;
        activPicImage.src = dataurl;
        activNameInput.value = activityDetail.activName;
        activConTextarea.value = activityDetail.activCon;
        activStarttimeInput.value = formatDate(activityDetail.activStarttime);
        activEndtimeInput.value = formatDate(activityDetail.activEndtime);
        activStaSelect.value = activityDetail.activSta;
//        originalSchId = recommends[1];

        // 提取陣列中的兩個值
        const value1 = recommends[0] || ''; // 假設第一個值在陣列的索引 0
        const value2 = recommends[1] || ''; // 假設第二個值在陣列的索引 1

        // 設置結合後的字串為 schRecommendInput 的值
        schRecommendInput1.value = value1;
        schRecommendInput2.value = value2;


    } catch (error) {
        console.error('Error fetching activity detail:', error);
    }
}

//格式化日期
//function formatDate(dateString) {
//    const options = { year: 'numeric', month: 'long', day: 'numeric' };
//    return new Date(dateString).toLocaleDateString('zh-TW', options);
//}

function formatDate(dateString) {
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
    return new Date(dateString).toLocaleString('zh-TW', options).replace(/\//g, '-');
}

const formattedDate = formatDate('2023-09-01T14:30:00');
//console.log(formattedDate);


//在網頁載入完成後執行
document.addEventListener('DOMContentLoaded', function () {
    fetchActivityDetail();

    // 選擇圖片時觸發事件
    const cover_el = document.getElementById('inputGroupFile04');
    const cover_img = document.getElementById('activPic');


    cover_el.addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            cover_img.src = URL.createObjectURL(file);

            const fileReader = new FileReader();
            fileReader.onload = event => {
                cover = btoa(event.target.result);
            };
            fileReader.readAsBinaryString(file);
        } else {
            cover_img.src = "";

        }
    });
});


// 在提交審核按鈕被點擊時執行以下程式碼
// 修改
submitBtn.addEventListener('click', async function (event) {
    event.preventDefault();

        // 驗證
    if (!cover || !activNameInput.value || !activConTextarea.value || !activStarttimeInput.value || !activEndtimeInput.value || !activStaSelect.value) {
        Swal.fire({
            icon: 'warning',
            title: '請填寫所有欄位',
            text: '',
            confirmButtonText: '確定'
            });
            return; // 阻止繼續進行更新操作
        }


    // 構建要傳遞的資料
    const requestData = {
        activName: activNameInput.value,
        activCon: activConTextarea.value,
        activSta: activStaSelect.value,
        activId: activId,
        schId: schId
    };


    //新增
        for(let i=1; i<=2 ; i++){

             switch(i){
                case 1:
                     schId = schRecommendInput1.value;
                     break;
                case 2:
                     schId = schRecommendInput2.value;
                     break;
             }
             fetch(baseUrl + `activityRecommendadd/${activId}/${schId}`, {
                method: 'GET'
             });
        }


    //修改 施工
//    for (let i = 1; i <= 2; i++) {
//
//        switch (i) {
//            case 1:
//                schId = schRecommendInput1.value;
//                break;
//            case 2:
//                schId = schRecommendInput2.value;
//                break;
//        }
//
//        // 創建要發送的數據對象，包含activitySchRecommendId的值
//        const requestData = {
//            activId: activId,
//            schId: schId
//        };
//
//        // 使用fetch發送PUT或PATCH請求
//        fetch(baseUrl + `activityRecommendedit/${activId}/${originalSchId}`, {
//
//            method: 'PUT',
//            headers: {
//                'Content-Type': 'application/json'
//            },
//            body: JSON.stringify(requestData)  // 將數據對象轉換為JSON字符串
//        });
//    }


    // if檢查cover是否有值，如果有選擇圖片，則更新activPic
    if (cover) {
        requestData.activPic = cover;
    }

    try {
        const response = await fetch(baseUrl + `update/${activId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const updatedActivityData = await response.json();

            Swal.fire({
                icon: 'success',
                title: '更新成功',
                text: '',
                confirmButtonText: '確定'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = 'activityList.html';
                }
            });


        } else {
            console.error('Failed to update activity.');
        }
    } catch (error) {
        console.error('Error to update activity.', error);
    }
});


// 刪除 document獲取當前頁面各種元素
document.getElementById("deleteButton").addEventListener("click", async function () {
    try {
        const result = await Swal.fire({
            icon: 'warning',
            title: '確認刪除活動嗎?',
            text: '',
            showCancelButton: true,
            confirmButtonText: '確定',
            cancelButtonText: '取消'
        });

        if (result.isConfirmed) {
            const response = await fetch(baseUrl + `delete/${activId}`, {
                method: 'DELETE',
            });

            Swal.fire({
                icon: 'success',
                title: '刪除成功',
                text: '',
                confirmButtonText: '確定'
            }).then(() => {

                window.location.href = 'activityList.html';
            });

        } else {
            // 點取消
            console.log('Failed to delete activity.');
        }
    } catch (error) {
        console.error('Error deleting activity.', error);
    }
});


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