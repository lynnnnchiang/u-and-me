let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";
const statusMapping = new Map([
    [1, '商城版'],
    [2, '行程版'],
    [3, '揪團版'],
]);
// <!--網頁載入後執行-->
document.addEventListener("DOMContentLoaded", function () {
    fetchMemIdArticleList();
});
const collection_el = document.getElementById('collection');


// document.querySelector('#btnSubmit').addEventListener('click', () => {
async function fetchMemIdArticleList() {
    try {
        const response = await fetch(`${baseURL}getArticleFromCollectionByMemId`);
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
            <a  href="${baseURL}tmp/Front/article/articleindex/articleDetail.html?articleId=${MemIdArticle.articleId}"  
            style="text-decoration: none;" ">
<div class="articlecard">
<div style="display: flex;vertical-align: top; margin-right: 10px;">
    <div>
        <p style="color: grey;margin-bottom: 8px;width:50px;">${statusMapping.get(MemIdArticle.memId)}</p>
    </div>

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
</a>
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


