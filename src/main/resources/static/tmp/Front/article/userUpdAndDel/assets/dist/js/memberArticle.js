/* global bootstrap: false */
(function () {
  'use strict'
  var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  tooltipTriggerList.forEach(function (tooltipTriggerEl) {
    new bootstrap.Tooltip(tooltipTriggerEl)

    var dropdownToggleList = [].slice.call(document.querySelectorAll('[data-bs-toggle="dropdown"]'))
    dropdownToggleList.map(function (dropdownToggle) {
      return new bootstrap.Dropdown(dropdownToggle)
    });
    



  })
})()

let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";
// 網頁載入後執行
document.addEventListener("DOMContentLoaded", function () {
    fetchMemIdArticleList();

});

// 解析當前網址的查詢參數（query parameters）。
// const urlParams = new URLSearchParams(window.location.search);
// 使用get方法從查詢參數中獲取名為'memId'的值。
// const memId = urlParams.get('memId');

async function fetchMemIdArticleList() {
    try {
        const response = await fetch(`${baseURL}UserEdit/findByMemId`);
        const resMemIdArticleList = await response.json();
        console.log(resMemIdArticleList);
        const articles_el = document.getElementById("articles");
        // 1. 取得要被動態插入內容的元素
        articles_el.innerHTML = "";
        // 2. 先清空容器，以便插入新的文章內容

        resMemIdArticleList.forEach(MemIdArticle => {
            const articleDiv = document.createElement("div"); // 在每次迭代中創建新的 div 元素
            articleDiv.className = "articlecardframe"; // 添加 class
            articleDiv.innerHTML = `
<div class="articlecard">
<div style="display: flex;vertical-align: top; margin-right: 10px;">
    <div>
        <p style="color: grey;margin-bottom: 8px;width:50px;">${statusMapping.get(MemIdArticle.acTypeId)}</p>
    </div>
    <button style="display:inline-block; margin-left:600px;height:10px;">
        <a
            href="${baseURL}tmp/Front/article/userUpdAndDel/memberArticleUpdate.html?articleId=${MemIdArticle.articleId}">

            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                class="bi bi-pencil" viewBox="0 0 16 16">
                <path
                    d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z" />
            </svg>
        </a>
    </button>

    <button style="display:inline-block; height:10px;margin-left:30px;"
        onclick="deleteFunction(${MemIdArticle.articleId})">

        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash"
            viewBox="0 0 16 16">
            <path
                d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z" />
            <path
                d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z" />
        </svg>
    </button>

</div>
<div>
    <div>
        <h5>${MemIdArticle.articleTitle}</h5>
    </div>

    <div>
        <p class="JQellipsis">
            ${MemIdArticle.articleContent}
        </p>
        <span style="position: relative; z-index: 3;">
            <img src="assets/brand/like.jpg" style="width: 20px;">
        </span>
        <span style="margin-left: 5px; margin-right: 20px;">
            ${MemIdArticle.articleLike}
        </span>
        <span style="position: relative; z-index: 3;">
            <img src="assets/brand/comment.jpg" style="width: 25px;">
        </span>
        <span style="margin-left: 5px; margin-right: 20px;">
            ${MemIdArticle.commentNum}
        </span>

    </div>
</div>

`;

            articles_el.appendChild(articleDiv); // 將文章 div 添加到容器中


            // 超過130個字以"..."取代
            $(function () {
                var len = 130;
                $(".JQellipsis").each(function (i) {
                    if ($(this).text().length > len) {
                        $(this).attr("title", $(this).text());
                        var text = $(this).text().substring(0, len - 1) + "...";
                        $(this).text(text);
                    }
                });
            });

        });

    } catch (error) {
        console.error("Error fetching article list:", error);
    }
}
// Integer回傳為object，以下要改成字串形式
const statusMapping = new Map([
    [1, '全部'],
    [2, '商城版'],
    [3, '行程版'],
    [4, '揪團版'],
]);
console.log(statusMapping);

function deleteFunction(articleId) {
    Swal.fire({
        title: '刪除後即無法回復文章，確定繼續刪除動作?',
        icon: 'warning',
        showCancelButton: true, // 显示取消按钮
        confirmButtonText: '確定',
        cancelButtonText: '取消' // 自定义取消按钮
    }).then(async (result) => {
        if (result.isConfirmed) {
            // 用户点击了确认按钮
            // 在这里执行删除操作，可以使用 articleId
            await fetch(`${baseURL}UserEdit/deleteArticle/${articleId}`, {
                method: 'DELETE'
            });
            location.reload();
            console.log('用戶點擊確認按鈕，確定删除文章，articleId:', articleId);
        } else {
            // 用户点击了取消按钮，不执行删除操作
            console.log('用戶點擊取消按鈕，不執行删除');
        }
    });
}