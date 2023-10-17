let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";

// 取得查詢articleId值
const urlParams = new URLSearchParams(window.location.search);
const articleId = urlParams.get('articleId');

// 表單中的各輸入框元素
const acTypeId_el = document.getElementById('acTypeId');
const imgOuterFrame_el = document.getElementById('imgOuterFrame');
// const articlePicImage = document.getElementById('articlePic');
const articleTitle_el = document.getElementById('articleTitle');
const articleContent_el = document.getElementById('articleContent');
const submitBtn = document.getElementById('submit');
let cover;

// =======使用 fetch API 發送請求，獲取單筆活動詳細資料==============
async function fetchArticleDetail() {
    try {
        const response = await fetch(`${baseURL}UserEdit/${articleId}`);
        const articleDetail = await response.json();
        console.log(articleDetail);
        acTypeId_el.value = articleDetail.acTypeId;

        articleTitle_el.value = articleDetail.articleTitle;
        articleContent_el.value = articleDetail.articleContent;

    } catch (error) {
        console.error('Error fetching article detail:', error);
    }
}

async function fetchArticlePic() {

    try {
        const response = await fetch(`${baseURL}article_comment/articleId/article_pic/${articleId}`);
        const articlePicsArray = await response.json();
        console.log(articlePicsArray);


        // 遍歷圖片並創建img元素顯示
        articlePicsArray.forEach(pic => {
            // 創建包装圖片的<img>
            const imgInnerFrame_el = document.createElement('div');
            imgInnerFrame_el.classList.add('imgInnerFrame');

            const img_el = document.createElement('img');
            img_el.src = `data:image/png;base64,${pic.articlePic}`; // 使用Base64編碼的圖片
            img_el.alt = "Loading...";
            img_el.classList.add('img_el'); // 根據需要添加樣式
            img_el.id = `${pic.articlePicId}`;

            const deleteButton_el = document.createElement('button');
            deleteButton_el.classList.add('deleteButton');
            deleteButton_el.addEventListener('click', () => {
                confirmDelete(`${pic.articlePicId}`);
            })
            deleteButton_el.innerHTML = `
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"></path>
                        <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"></path>
                    </svg>
                `;

            //游標滑到圖片上方時，會出現刪除圖示，且圖示綁定onclick事件
            imgInnerFrame_el.addEventListener('mouseenter', () => {
                // 顯示刪除圖示
                imgInnerFrame_el.appendChild(deleteButton_el);
            });

            imgInnerFrame_el.addEventListener('mouseleave', () => {
                // 隐藏刪除圖示
                imgInnerFrame_el.removeChild(deleteButton_el);
            });


            imgOuterFrame_el.appendChild(imgInnerFrame_el);
            imgInnerFrame_el.appendChild(img_el);

        });
    } catch (error) {
        console.error('Error fetching article detail:', error);
    }
}

// =============== 限制輸入文字 ===============
const maxContentLength = 1000;
const maxTitleLength = 45;

articleContent_el.addEventListener("input", function () {
    if (articleContent_el.value.length > maxContentLength) {
        // 检查內文輸入的文字长度是否超过最大长度
        articleContent_el.value = articleContent_el.value.substring(0, maxContentLength);
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



articleTitle_el.addEventListener("input", function () {
    // 检查標題輸入的文字长度是否超过最大长度
    if (articleTitle_el.value.length > maxTitleLength) {
        // 如果超过了最大长度，截断文本内容
        articleTitle_el.value = articleTitle_el.value.substring(0, maxTitleLength);

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


articleContent_el.addEventListener("blur", function () {
    if (articleContent_el.value.length < 1) {
        contentWarning.textContent = '   內文不得空白';

    }
    else {
        // 如果输入有效，清除警告
        titleWarning.textContent = '';
    }
});

articleTitle_el.addEventListener("blur", function () {
    if (articleTitle_el.value.length < 1) {
        titleWarning.textContent = '   標題不得為空白';
    }
    else {
        // 如果输入有效，清除警告
        contentWarning.textContent = '';
    }
});

//===============修改照片與預覽照片============================
function confirmDelete(articlePicId) {
    // 在點擊刪除的圖示後，不會自動送出網內容
    event.preventDefault();
    swal.fire({
        title: '刪除後即無法回復圖片，確定繼續刪除動作?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '確定',
        cancelButtonText: '取消'
    }).then(async (result) => {
        if (result.isConfirmed) {
            // 點擊確認按鈕後在此執行删除操作
            await fetch(`${baseURL}UserEdit/upd/deletePic/${articlePicId}`, {
                method: 'DELETE'
            });
            console.log(`圖片 ${articlePicId} 已成功删除`);
            location.reload();
        } else {
            console.error("用户点击了取消按钮，不执行删除操作");
        }
    });

}
const fileInputsContainer = document.getElementById('fileInputs');
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
    newFileInput.className = 'form-control_';
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
//格式化日期
function formatDate(dateString) {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString('zh-TW', options);
}

//在網頁載入完成後執行
document.addEventListener('DOMContentLoaded', function () {
    fetchArticleDetail();
    fetchArticlePic();

});


// 在提交審核按鈕被點擊時執行以下程式碼
submitBtn.addEventListener('click', async function (event) {
    event.preventDefault()
    const userInput_content = articleContent_el.value;
    const userInput_content_title = articleTitle_el.value;
    // 清洗輸入的文字，擋住html標籤，以及將斷行以<br>存到資料庫
     const articleContent = userInput_content.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\n/g, '<br>');
     const articleTitle = userInput_content_title.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\n/g, '<br>');

    // 判斷標題與內文不得為空值
    if (articleContent.length < 1) {
        contentWarning.textContent = '內文不得為空白';
        return;

    } else {
        // 如果输入有效，清除警告
        titleWarning.textContent = '';
    }

    if (articleTitle.length < 1) {
        titleWarning.textContent = '標題不得為空白';
        return;

    } else {
        // 如果输入有效，清除警告
        titleWarning.textContent = '';
    }

    // 構建要傳遞的資料
    const requestData = {
        acTypeId: acTypeId_el.value,
        articleTitle: articleTitle,
        articleContent: articleContent,
        articleId: articleId,
    };
    console.log(requestData);

    try {
        // 使用FormData型態處理form(picForm)裡面的檔案

        const formData = new FormData(picForm);
        const post_picURL = `${baseURL}UserEdit/upd/post_pics/${articleId}`;
        fetch(post_picURL, {
            method: "POST",
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('Error:', error);
            });

        const response = await fetch(`${baseURL}UserEdit/upd/${articleId}`, {

            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })

            .then(response => {
                console.log(response)
                if (response.ok) {
                    console.log('狀態更新成功。');
                    Swal.fire({
                        icon: 'success',
                        title: '文章更新成功',
                        text: '',
                        confirmButtonText: '確定'
                    }).then((result) => {
                        // if (result.isConfirmed) { ... }：这是一个条件语句，检查用户是否点击了 
                        // SweetAlert2 弹出框中的 "確定" 按钮。如果用户点击了 "確定" 按钮，那么条件成立，将执行括号内的代码块。
                        if (result.isConfirmed) {
                            window.location.href = `${baseURL}tmp/Front/article/userUpdAndDel/memberArticle.html`
                        }

                    });

                } else {
                    console.error('無法更新狀態。');

                }
            })

    } catch (error) {
        console.error('Error to update article.', error);
    }
});
