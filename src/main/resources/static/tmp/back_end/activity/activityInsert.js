const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
// 表單中的各輸入框元素
const activIdInput = document.getElementById('activId');
const activPicImage = document.getElementById('activPic');
const activNameInput = document.getElementById('activName');
const activConTextarea = document.getElementById('activCon');
const activStarttimeInput = document.getElementById('activStarttime');
const activEndtimeInput = document.getElementById('activEndtime');
const activStaSelect = document.querySelector('.form-control.select2');
const submitBtn = document.getElementById('addButton');
let cover;
let activSta;


// 管理員filter


window.addEventListener("load", function (e) {
this.fetch(baseUrl + 'host/match', {
    method: 'GET'
}).then(response => {
    if(response.status == 401){

            this.location.href = baseUrl + 'tmp/back_end/host/hostLogin.html';

    }
});
})

// 網頁載入後執行
document.addEventListener('DOMContentLoaded', function () {
    // 點選圖片觸發事件
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

// 獲取當前日期
    const currentDateTime = new Date().toISOString().slice(0, 16);
    activStarttimeInput.setAttribute('min', currentDateTime);

    activStarttimeInput.addEventListener('change', function () {
        const startTime = new Date(activStarttimeInput.value);
        const endTime = new Date(activEndtimeInput.value);

        // 更新結束時間輸入框最小值為活動開始時間
         activEndtimeInput.setAttribute('min', activStarttimeInput.value);

        if (endTime <= startTime) {
            // 將結束時間重置為開始時間的值
            activEndtimeInput.value = activStarttimeInput.value;
        }
    });


    activEndtimeInput.addEventListener('change', function () {
        const startTime = new Date(activStarttimeInput.value);
        const endTime = new Date(activEndtimeInput.value);

        if (endTime <= startTime) {

            activEndtimeInput.value = activStarttimeInput.value;
        }
    });

    // 點擊按鈕後新增活動數據
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

        const startTime = new Date(activStarttimeInput.value);
        const endTime = new Date(activEndtimeInput.value);


        // 構建要傳遞的資料
        const newActivityData = {
            activPic: cover,
            activName: activNameInput.value,
            activCon: activConTextarea.value,
            activStarttime: startTime.toISOString(),
            activEndtime: endTime.toISOString(),
            activSta: activStaSelect.value
        };

        try {
            const response = await fetch(`${baseUrl}add`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newActivityData)
            });

            if (response.ok) {
                const updatedActivityData = await response.json();

                Swal.fire({
                    icon: 'success',
                    title: '新增成功',
                    text: '',
                    confirmButtonText: '確定'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = 'activityList.html';
                    }
                });

            } else {

                Swal.fire({
                    icon: 'error',
                    title: '新增失敗',
                    text: '',
                    confirmButtonText: '確定'
                }).then((result) => {
                    if (result.isConfirmed) {

                    }
                });
                console.error('Error adding activity:', response.statusText);
            }
        } catch (error) {
            console.error('Error adding activity:', error);
        }
    });
});


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
