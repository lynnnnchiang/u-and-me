
// {/* <!--網頁載入後執行--> */}

// 解析當前網址的查詢參數（query parameters）。
const urlParams = new URLSearchParams(window.location.search);
// 使用get方法從查詢參數中獲取名為'article_id'的值。
const articleId = urlParams.get('articleId');
const article_pic = urlParams.get('articleId');
console.log(articleId);
// const article_id = parseInt(urlParams.get('article_id'));
let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";


// 表單中的各輸入框元素
const memId_el = document.getElementById('memId');

const singleArticlePic_el = document.getElementById('singleArticlePic');
const articleTitle_el = document.getElementById('articleTitle');
const articleTime_el = document.getElementById('articleTime');
const articleContent_el = document.getElementById('articleContent');
const articleLike_el = document.getElementById('articleLike');
const commentNum_el = document.getElementById('commentNum');
const collection_el = document.getElementById('collection');
const like_el = document.getElementById('like');

// 該篇文章所有的留言
const articleCommentCard_el = document.getElementById('article_comment_card');
const articleCommentDiv_el = document.getElementById('article_comment_div');
// 使用者新增留言的 textarea
const myTextarea_el = document.getElementById('myTextarea');

// const submitBtn = document.queryS_elector('button[type="submit"]');
// 將文章填充到內頁中
// let cover;

document.addEventListener("DOMContentLoaded", function () {
    fetchSingleArticle();
    fetchArticlePic();
    fetchArticleComment();
    fetchCollectedOrNot();
    fetchLikeOrNot();
});

// 使用 fetch API 請求article table資料，獲取特定article_id文章資料
async function fetchSingleArticle() {
    try {

        const response1 = await fetch(`${baseURL}article_comment/articleId/${articleId}`);

        // 解析JSON数据
        const articleDetail = await response1.json();
        console.log(articleDetail);

        // 將文章填充到內頁中
        articleTitle_el.innerText = articleDetail.articleTitle; // 使用 innerText
        // activConTextarea.value = articleDetail.activCon;
        articleTime_el.innerText = formatDate(articleDetail.articleTime); // 使用 innerText
        articleContent_el.innerText = articleDetail.articleContent; // 使用 innerText
        articleLike_el.innerText = articleDetail.articleLike; // 使用 innerText
        commentNum_el.innerText = articleDetail.commentNum; // 使用 innerText

        const response2 = await fetch(`${baseURL}article_comment/articleId/memName`);
        const memName = await response2.text();
        console.log(memName);
        memId_el.innerHTML =  memName;// 使用 innerText



    } catch (error) {
        console.error("Error fetching article list:", error);
    }
}

// 使用 fetch API 請求article_picture資料，獲取特定article_id圖片資料
async function fetchArticlePic() {
    try {
        const response = await fetch(`${baseURL}article_comment/articleId/article_pic/${article_pic}`);
        const articlePicsArray = await response.json();
        console.log(articlePicsArray);


        // 遍歷圖片並創建img元素顯示
        articlePicsArray.forEach(pic => {
            // 創建包装圖片的<div>
            const imgDiv = document.createElement('div');
            imgDiv.classList.add('picframe'); // 添加你的 class
            const img = document.createElement('img');
            img.src = `data:image/png;base64,${pic.articlePic}`; // 使用Base64編碼的圖片
            img.alt = `Article Pic ${pic.articlePicId}`;
            img.classList.add('col-7'); // 根據需要添加樣式
            singleArticlePic.appendChild(img);


        });



    } catch (error) {
        console.error('Error fetching article detail:', error);
    }
}

// 使用 fetch API 請求article_comment資料，獲取特定article_id留言資料
async function fetchArticleComment() {
    try {
        // 使用反引号（‵‵)可以插入变量${}。若使用單引號 ('')則只能使用字符串连接，例:'Hello, ' + name + '!';
        const response = await fetch(`${baseURL}article_comment/${articleId}`);
        const resCommentList = await response.json();
        console.log(resCommentList);
        articleCommentDiv_el.innerHTML = "";
        // 先清空要被動態插入內容的元素容器，以便插入新的文章內容

        resCommentList.forEach(resComment => {
            const articleDiv = document.createElement("div"); // 在每次迭代中創建新的 div 元素
            articleDiv.className = "article_comment_card"; // 添加 class
            articleDiv.innerHTML = `
<div>
    <p id="article_comment_memId" style="font-size: 14px;">會員編號：${resComment.memId}</p>
</div>
<div>
    <p>
        ${resComment.commentPost}
    </p>

</div>

`;

            articleCommentDiv_el.appendChild(articleDiv); // 將文章 div 添加到容器中
          

        });

    } catch (error) {
        console.error("Error fetching article list:", error);
    }
}

// 使用 fetch API 請求article_collection資料，讀取是否已收藏過
async function fetchCollectedOrNot() {
    try {
        // 控制fetch傳入網址

        // const postData = {
        //     articleId: articleId
        // }
        // 使用反引号（‵‵)可以插入变量${}。若使用單引號 ('')則只能使用字符串连接，例:'Hello, ' + name + '!';
        const url = `${baseURL}CollectionExsitOrNot/${articleId}`;

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            // body: JSON.stringify(postData)
        })
            .then(response => response.json())
            .then(data => {
                // 成功接收并解析响应后的处理逻辑
                console.log(data);


                // 根据 data 值来更新按钮的 HTML 内容
                if (data === 1) {
                    // 如果 data 为 1，显示已收藏的图标
                    console.log("已收藏");

                    collection_el.innerHTML = `
<svg xmlns="http://www.w3.org/2000/svg" width="20px" height="30" fill="#6c757d" class="bi bi-bookmark-fill"
    viewBox="0 0 16 16">
    <path
        d="M2 2v13.5a.5.5 0 0 0 .74.439L8 13.069l5.26 2.87A.5.5 0 0 0 14 15.5V2a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2z" />
</svg>
`;
                } else if (data === 0) {
                    // 如果 data 为 0，显示未收藏的图标
                    console.log("未收藏");
                    collection_el.innerHTML = `
<svg xmlns="http://www.w3.org/2000/svg" width="20px" height="30" fill="currentColor" class="bi bi-bookmark"
    viewBox="0 0 16 16">
    <path
        d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z" />
</svg>
`;
                }
            })
            .catch(error => {
                // 处理错误
                console.error('Error:', error);
            });
    } catch (error) {
        console.error("Error fetching collection", error);
    }
}
// 使用 fetch API 請求article_like資料，讀取是否已按過讚
async function fetchLikeOrNot() {
    try {

        // 控制fetch傳入網址
        // const response = await fetch(baseURL + `member/getMemId`);
        // const member = await response.json();
        // const memId = member.memId;

        // const postData = {
        //     articleId: articleId
        // }
        // console.log(postData);
        const url = `${baseURL}article_comment/LikeExsitOrNot/${articleId}`;

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            // body: JSON.stringify(postData)
        })
            .then(response => response.json())
            .then(data => {
                // 成功接收并解析响应后的处理逻辑
                console.log(data);


                // 根据 data 值来更新按钮的 HTML 内容
                if (data === 1) {
                    // 如果 data 为 1，显示已收藏的图标
                    console.log("已按讚");

                    like_el.innerHTML = `
<svg xmlns="http://www.w3.org/2000/svg" width="20px" height="30" fill="pink" stroke="grey" stroke-width="1"
    class="bi bi-suit-heart-fill" viewBox="-2 0 20 20">
    <path
        d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"
        fill="pink" />
</svg>

`;
                } else if (data === 0) {
                    // 如果 data 为 0，显示未收藏的图标
                    console.log("未按讚");
                    like_el.innerHTML = `
<svg xmlns="http://www.w3.org/2000/svg" width="20px" height="30" fill="white" stroke="grey" stroke-width="1"
    class="bi bi-suit-heart-fill" viewBox="-2 0 20 20">
    <path
        d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"
        fill="white" />
</svg>
`;
                }
            })
            .catch(error => {
                // 处理错误
                console.error('Error:', error);
            });
    } catch (error) {
        console.error("Error fetching likeIcon", error);
    }
}



// 使用 fetch API ，新增留言到URL指定的文章頁面

document.querySelector('#btnSubmit').addEventListener('click', async () => {
    await memberLogin();
    const userInput = document.querySelector('#myTextarea').value;
    const sanitizedInput = userInput.replace(/</g, '&lt;').replace(/>/g, '&gt;');
    // 把使用者輸入的留言洗一洗，讓html標籤go away


    if (sanitizedInput.trim() === '') {
        // 输入为空，设置 placeholder 文本颜色为红色并显示警告
        const placeholder = document.querySelector('#myTextarea::placeholder');
        placeholder.style.setProperty('color', 'red', 'important'); // 设置 placeholder 文本颜色为红色
        // 屬性名    // value // priority
        placeholder.placeholder = '留言不得空白';
        return; // 停止继续执行请求
    } else {
        // 如果输入有效，清除警告
        const textarea = document.querySelector('#myTextarea');
        textarea.style.setProperty('color', ''); // 清除 placeholder 文本颜色
        textarea.placeholder = '留言...'; // 恢复默认的 placeholder 文本
    }


    const postData = {
        commentPost: sanitizedInput,
    };
    console.log(postData);
    const url = `${baseURL}article_comment/post/${articleId}`;
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(postData)
    })
        .then(response => response.json())
        .then(data => {
            // 后端返回的数据
            console.log(data);
            location.reload();
            // 將頁面重新整理
        })
        .catch(error => {

            console.error('Error:', error);
        });



});


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
                    confirmButtonText: '前往登入頁面',
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

//========用來判斷未登入的收藏與按讚============
function memberLoginOrNot() {
    console.log("memberLoginOrNot");
    fetch(baseURL + "member/getMemId", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.status === 200) {
                // console.log(response.json());
                return response.json();
            } else if (response.status === 401) {
                // console.log(response.json());

                memId = data.memId;
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

function formatDate(dateString) {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString('zh-TW', options);
}
// 使用 fetch API ，新增新增或刪除收藏
collection_el.addEventListener("click", async () => {
    memberLogin();

    const response = await fetch(baseURL + `member/getMemId`);
    const member = await response.json();
    const memId = member.memId;

    const postData = {

        collectionId: {
            memId: memId,
            articleId: articleId
        }
    };
    console.log(postData);
    const url = `${baseURL}AddOrRemoveCollection`;
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(postData)
    })
        .then(response => response.json())
        .then(data => {
            // 后端返回的数据
            // console.log(response);
            // console.log(data);
            if (data === 1) {
                console.log("已加入收藏");
                // 如果 data 为 1，显示已收藏的图标
                collection_el.innerHTML = `
<svg xmlns="http://www.w3.org/2000/svg" width="20px" height="30" fill="#6c757d" class="bi bi-bookmark-fill"
    viewBox="0 0 16 16">
    <path
        d="M2 2v13.5a.5.5 0 0 0 .74.439L8 13.069l5.26 2.87A.5.5 0 0 0 14 15.5V2a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2z" />
</svg>
`;
                swal.fire({
                    title: '已加入收藏',
                    icon: 'success',
                    showCancelButton: false,
                    confirmButtonText: '確定'
                    // cancelButtonText: '取消'
                })
            } else if (data === 0) {
                // 如果 data 为 0，显示未收藏的图标
                console.log("已刪除收藏");
                collection_el.innerHTML = `
<svg xmlns="http://www.w3.org/2000/svg" width="20px" width="30" fill="currentColor" class="bi bi-bookmark"
    viewBox="0 0 16 16">
    <path
        d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z" />
</svg>
`;
            }
        })
        .catch(error => {
            console.log(error);
            console.error('Error:', error);
        });


})

// 使用 fetch API ，新增按讚或收回讚
like_el.addEventListener("click", async () => {
    memberLogin();

    const response = await fetch(baseURL + `member/getMemId`);
    const member = await response.json();
    const memId = member.memId;
    const postData = {

        likeId: {
            memId: memId,
            articleId: articleId
        }
    };
    console.log(postData);
    const url = `${baseURL}article_comment/AddOrRemoveLike`;
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(postData)
    })
        .then(response => response.json())
        .then(data => {
            // 后端返回的数据
            // console.log(response);
            // console.log(data);
            if (data === 1) {
                console.log("按讚");
                // 如果 data 为 1，显示已收藏的图标
                like_el.innerHTML = `
<svg xmlns="http://www.w3.org/2000/svg" width="20px"  fill="pink" stroke="grey" stroke-width="1"
    class="bi bi-suit-heart-fill" viewBox="-2 0 20 20">
    <path
        d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"
        fill="pink" />
</svg>
`;
            } else if (data === 0) {
                // 如果 data 为 0，显示未收藏的图标
                console.log("收回讚");
                like_el.innerHTML = `
<svg xmlns="http://www.w3.org/2000/svg" width="20px"  fill="white" stroke="grey" stroke-width="1"
    class="bi bi-suit-heart-fill" viewBox="-2 0 20 20">
    <path
        d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"
        fill="white" />
</svg>
`;
            }
            location.reload();

        })
        .catch(error => {
            console.log(error);
            console.error('Error:', error);
        });


})





const statusMapping = new Map([
    [1, '全部'],
    [2, '商城版'],
    [3, '行程版'],
    [4, '揪團版'],
]);
console.log(statusMapping);

