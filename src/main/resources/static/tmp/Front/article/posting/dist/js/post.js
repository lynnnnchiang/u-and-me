let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";

const url = `${baseURL}post`;
const post_picURL = `${baseURL}post_pics`;

const articleTitle = document.querySelector('#title');
const articleContent = document.querySelector('#content');


let artId = 0; // 初始化 artId

document.addEventListener('DOMContentLoaded', function () {
    // 頁面載入時先判斷是否為會員
    memberLogin();
    //避免在DOM尚未完全加載和解析之前執行代碼

    const shopBtn = document.getElementById('shopBtn');
    const schedBtn = document.getElementById('schedBtn');
    const groupBtn = document.getElementById('groupBtn');

    // 貼文分類按鈕按下去的效果
    // 商城按鈕onclick事件
    shopBtn.addEventListener('click', function () {
        // 移除其他按钮的active
        schedBtn.classList.remove('active');
        groupBtn.classList.remove('active');

        // 被點擊按鈕轉為active狀態(CSS中有設定active狀態的樣式)
        shopBtn.classList.add('active');

        // 若選擇以商城版發文，向後端傳遞1的值
        artId = 1;
    });

    // 行程按鈕onclick事件
    schedBtn.addEventListener('click', function () {
        // 移除其他按钮的active
        shopBtn.classList.remove('active');
        groupBtn.classList.remove('active');

        // 被點擊按鈕轉為active狀態(CSS中有設定active狀態的樣式)
        schedBtn.classList.add('active');

        // 若選擇以行程版發文，向後端傳遞2的值
        artId = 2;
    });

    // 揪團按鈕onclick事件
    groupBtn.addEventListener('click', function () {
        shopBtn.classList.remove('active');
        schedBtn.classList.remove('active');

        groupBtn.classList.add('active');

        artId = 3;
    });


});

// ===============新增圖片及預覽圖 ===============

// 上傳欄位
const fileInputsContainer = document.getElementById('fileInputs');
// 上傳按鈕
const addFileButton_el = document.getElementById('addFileButton');

addFileButton_el.addEventListener('click', function () {
    // 包著預覽圖與上傳欄位的div
    const imagePreviewContainer = document.createElement('div');
    // 上傳欄位
    const newFileInput = document.createElement('input');
    // 預覽圖
    const imagePreview = document.createElement('img');

    newFileInput.type = 'file';
    newFileInput.name = 'files';
    newFileInput.className = 'form-control';
    // event参数包含了关于事件的信息，包括用户选择的文件。
    newFileInput.addEventListener('change', function (event) {
        // event.target表示触发事件的元素(也就是新增的檔案)，而files属性是一个包含用户选择文件的文件列表。
        // 因为選擇檔案時可一次選多個，使用[0]來解析選擇圖片中的第一個。
        const file = event.target.files[0];
        // 檢查file是否確實有被選中檔案，而不是取消上傳等其他動作
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                imagePreview.src = e.target.result;
            };
            reader.readAsDataURL(file);
            // 将文件内容读取为数据 URL（以data:开头的 URL）。这个 URL 包含了文件的二进制数据，可以直接用于图像预览。
        }
    });

    fileInputsContainer.appendChild(imagePreviewContainer);
    imagePreviewContainer.appendChild(newFileInput);
    imagePreviewContainer.appendChild(imagePreview);
});

// =============== 判斷會員是否登入 ===============
function memberLogin() {
    console.log("memberLogin");
    fetch(baseURL + "member/getMemId", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 401) {
                Swal.fire({
                    title: '請先登入會員',
                    text: "將為您導向登入頁面....",
                    icon: 'error',
                    confirmButtonText: '返回登入頁面',
                    confirmButtonColor: '#d33'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = baseURL + 'tmp/Front/member/memberLogin.html';
                    }
                });
                throw new Error('Unauthorized');
            } else {
                throw new Error('Fetch Error');
            }
        })
        .then(data => {
            memId = data.memId;
        })
        .catch(error => {
            console.error('Error:', error);
        });

}


// =============== 限制輸入文字 ===============
const maxContentLength = 1000;
const maxTitleLength = 45;
const content_el = document.getElementById('content');
const title_el = document.getElementById('title');

content_el.addEventListener("input", function () {
    if (content_el.value.length > maxContentLength) {
        // 检查內文輸入的文字长度是否超过最大长度
        content_el.value = content_el.value.substring(0, maxContentLength);
        // 超過長度就砍斷
        Swal.fire({
            icon: 'error',
            title: '字數超過1000！',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '知道了',
            cancelButtonText: '關閉'
        })
    }

});



title_el.addEventListener("input", function () {
    // 检查標題輸入的文字长度是否超过最大长度
    if (title_el.value.length > maxTitleLength) {
        // 如果超过了最大长度，截断文本内容
        title_el.value = title_el.value.substring(0, maxTitleLength);

        Swal.fire({
            icon: 'error',
            title: '字數超過45！',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '知道了',
            cancelButtonText: '關閉'
        })
    }
});


content_el.addEventListener("blur", function () {
    if (content_el.value.length < 1) {
        Swal.fire({
            icon: 'error',
            title: '內文不得空白！',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '知道了',
            cancelButtonText: '關閉'
        })

    }else {
        // 如果输入有效，清除警告
        contentWarning.textContent = '';
    }

});

title_el.addEventListener("blur", function () {
    if (title_el.value.length < 1) {
        Swal.fire({
            icon: 'error',
            title: '標題不得空白！',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '知道了',
            cancelButtonText: '關閉'
        })    }   else {
            // 如果输入有效，清除警告
            titleWarning.textContent = '';
        }
 
});

// =============== 送出按鈕 ===============

document.querySelector('#btnSubmit').addEventListener('click', async () => {
    memberLogin();

    const userInput_content = document.querySelector('#content').value;
    const userInput_content_title = document.querySelector('#title').value;
    // 清洗輸入的文字，擋住html標籤，以及將斷行以<br>存到資料庫
    const articleContent_el = userInput_content.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\n/g, '<br>');

    const articleTitle_el = userInput_content_title.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\n/g, '<br>');

    if (artId === 0 && content_el.value.length < 1 && title_el.value.length < 1) {
        artIdWarning.textContent = '請選擇發文看板';
        contentWarning.textContent = '內文不得為空白';
        titleWarning.textContent = '標題不得為空白';
        return;

    }

    if (artId === 0) {
        artIdWarning.textContent = '請選擇發文看板';
        return;
    }
    else {
        // 如果输入有效，清除警告
        artIdWarning.textContent = '';
    }
    if (content_el.value.length < 1) {
        contentWarning.textContent = '內文不得為空白';
        return;

    }
    else {
        // 如果输入有效，清除警告
        contentWarning.textContent = '';

    }
    if (title_el.value.length < 1) {
        titleWarning.textContent = '標題不得為空白';
        return;

    }
    else {
        // 如果输入有效，清除警告
        titleWarning.textContent = '';
    }

    const postData = {
        acTypeId: artId,
        articleTitle: articleTitle_el,
        articleContent: articleContent_el
    };

    // 发送第一个请求
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(postData)
    })
        // .then(response => response.json())
        .then(data => {
            // 第一个请求成功后执行第二个请求
            console.log(data);

            // 使用FormData型態處理form(picForm)裡面的檔案
            const formData = new FormData(picForm);

            fetch(post_picURL, {
                method: "POST",
                body: formData
            });
            Swal.fire({
                title: '成功發布文章', // 弹框标题
                // text: '您的操作已成功执行', // 弹框内容
                icon: 'success', // 弹框图标
                confirmButtonText: '确定', // 确认按钮的文本
            }).then((result) => {
                if (result.isConfirmed) {
                    // 重新导向到指定网址
                    window.location.href = '../../article/articleindex/ArticleIndex.html';
                }
            });
        })



        .catch(error => {

            console.error('Error:', error);
        });
});



