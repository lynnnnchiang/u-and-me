let baseURL = window.location.protocol + "//" + window.location.host + "/u-and-me/";


//  <!--網頁載入後執行-->
 document.addEventListener("DOMContentLoaded", function () {
     fetchPublicArticleList();
     fetchGroupArticleList();
     fetchShopArticleList();
     fetchScheduleArticleList();
 });


 async function fetchGroupArticleList() {
     try {
         const response = await fetch(`${baseURL}article/groupArticles`);
         const resGroupArticleList = await response.json();
         console.log(resGroupArticleList);
         const AllGroupArticle = document.getElementById("AllGroupArticle");
         // 1. 取得要被動態插入內容的元素
         AllGroupArticle.innerHTML = "";
         // 2. 先清空容器，以便插入新的文章內容

         resGroupArticleList.forEach(GroupArticle => {
             const articleDiv = document.createElement("div"); // 在每次迭代中創建新的 div 元素
             articleDiv.className = "articlecard"; // 添加 class
             articleDiv.innerHTML = `
<a style="text-decoration: none;"
 href="${baseURL}tmp/Front/article/articleindex/articleDetail.html?articleId=${GroupArticle.articleId}">
 <div>
     <p style="color: grey;margin-bottom: 8px;">${statusMapping.get(GroupArticle.acTypeId)}</p>

 </div>
 <div>
     <h5>${GroupArticle.articleTitle}</h5>
 </div>
 <div>
     <p class="JQellipsis">
         ${GroupArticle.articleContent}
     </p>
     <span style="position: relative; z-index: 3;">
         <img src="assets/brand/like.jpg" style="width: 20px;">
     </span>
     <span style="margin-left: 5px; margin-right: 20px;">
         ${GroupArticle.articleLike}
     </span>
     <span style="position: relative; z-index: 3;">
         <img src="assets/brand/comment.jpg" style="width: 25px;">
     </span>
     <span style="margin-left: 5px; margin-right: 20px;">
         ${GroupArticle.commentNum}
     </span>

 </div>
 </div>
 <div style="border:1px solid #CCC; width: 70vw;margin-left: 10px;"></div>

</a>

`;

             AllGroupArticle.appendChild(articleDiv); // 將文章 div 添加到容器中


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
 async function fetchScheduleArticleList() {
     try {
         const response = await fetch(`${baseURL}article/scheduleArticles`);
         const resScheduleArticleList = await response.json();
         console.log(resScheduleArticleList);
         // console.log(articleList);
         const AllScheduleArticle = document.getElementById("AllScheduleArticle");
         // 1. 取得要被動態插入內容的元素
         AllScheduleArticle.innerHTML = "";
         // 2. 先清空容器，以便插入新的文章內容

         resScheduleArticleList.forEach(ScheduleArticle => {
             const articleDiv = document.createElement("div"); // 在每次迭代中創建新的 div 元素
             articleDiv.className = "articlecard"; // 添加 class
             articleDiv.innerHTML = `
<a
 href="${baseURL}tmp/Front/article/articleindex/articleDetail.html?articleId=${ScheduleArticle.articleId}">

 <div>
     <p style="color: grey;margin-bottom: 8px;">${statusMapping.get(ScheduleArticle.acTypeId)}</p>

 </div>
 <div>
     <h5>${ScheduleArticle.articleTitle}</h5>
 </div>
 <div>
     <p class="JQellipsis">
         ${ScheduleArticle.articleContent}
     </p>
     <span style="position: relative; z-index: 3;">
         <img src="assets/brand/like.jpg" style="width: 20px;">
     </span>
     <span style="margin-left: 5px; margin-right: 20px;">
         ${ScheduleArticle.articleLike}
     </span>
     <span style="position: relative; z-index: 3;">
         <img src="assets/brand/comment.jpg" style="width: 25px;">
     </span>
     <span style="margin-left: 5px; margin-right: 20px;">
         ${ScheduleArticle.commentNum}
     </span>

 </div>
 </div>
 <div style="border:1px solid #CCC; width: 70vw;margin-left: 10px;"></div>

</a>

`;

             AllScheduleArticle.appendChild(articleDiv); // 將文章 div 添加到容器中


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
 async function fetchShopArticleList() {
     try {
         const response = await fetch(`${baseURL}article/shopArticles`);
         const resShopArticleList = await response.json();
         console.log(resShopArticleList);
         // console.log(articleList);
         const AllShopArticle = document.getElementById("AllShopArticle");
         // 1. 取得要被動態插入內容的元素
         AllShopArticle.innerHTML = "";
         // 2. 先清空容器，以便插入新的文章內容

         resShopArticleList.forEach(ShopArticle => {
             const articleDiv = document.createElement("div"); // 在每次迭代中創建新的 div 元素
             articleDiv.className = "articlecard"; // 添加 class
             articleDiv.innerHTML = `
<a
 href="${baseURL}tmp/Front/article/articleindex/articleDetail.html?articleId=${ShopArticle.articleId}">

 <div>
     <p style="color: grey;margin-bottom: 8px;">${statusMapping.get(ShopArticle.acTypeId)}</p>

 </div>
 <div>
     <h5>${ShopArticle.articleTitle}</h5>
 </div>
 <div>
     <p class="JQellipsis">
         ${ShopArticle.articleContent}
     </p>
     <span style="position: relative; z-index: 3;">
         <img src="assets/brand/like.jpg" style="width: 20px;">
     </span>
     <span style="margin-left: 5px; margin-right: 20px;">
         ${ShopArticle.articleLike}
     </span>
     <span style="position: relative; z-index: 3;">
         <img src="assets/brand/comment.jpg" style="width: 25px;">
     </span>
     <span style="margin-left: 5px; margin-right: 20px;">
         ${ShopArticle.commentNum}
     </span>

 </div>
 </div>
 <div style="border:1px solid #CCC; width: 70vw;margin-left: 10px;"></div>

</a>

`;

             AllShopArticle.appendChild(articleDiv); // 將文章 div 添加到容器中


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
 async function fetchPublicArticleList() {
     try {
         const response = await fetch(`${baseURL}article/publicArticles`);
         const resPublicArticleList = await response.json();
         console.log(resPublicArticleList);
         // console.log(articleList);
         const AllPublicArticle = document.getElementById("AllPublicArticle");
         // 1. 取得要被動態插入內容的元素
         AllPublicArticle.innerHTML = "";
         // 2. 先清空容器，以便插入新的文章內容

         resPublicArticleList.forEach(publicArticle => {
             const articleDiv = document.createElement("div"); // 在每次迭代中創建新的 div 元素
             articleDiv.className = "articlecard"; // 添加 class
             articleDiv.innerHTML = `
<a
 href="${baseURL}tmp/Front/article/articleindex/articleDetail.html?articleId=${publicArticle.articleId}">

 <div>
     <p style="color: grey;margin-bottom: 8px;">${statusMapping.get(publicArticle.acTypeId)}</p>

 </div>
 <div>
     <h5>${publicArticle.articleTitle}</h5>
 </div>
 <div>
     <p class="JQellipsis">
         ${publicArticle.articleContent}
     </p>
     <span style="position: relative; z-index: 3;">
         <img src="assets/brand/like.jpg" style="width: 20px;">
     </span>
     <span style="margin-left: 5px; margin-right: 20px;">
         ${publicArticle.articleLike}
     </span>
     <span style="position: relative; z-index: 3;">
         <img src="assets/brand/comment.jpg" style="width: 25px;">
     </span>
     <span style="margin-left: 5px; margin-right: 20px;">
         ${publicArticle.commentNum}
     </span>

 </div>
 </div>
 <div style="border:1px solid #CCC; width: 70vw;margin-left: 10px;"></div>

</a>

`;

             AllPublicArticle.appendChild(articleDiv); // 將文章 div 添加到容器中


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


 const statusMapping = new Map([
     [1, '商城版'],
     [2, '行程版'],
     [3, '揪團版'],
 ]);

 console.log(statusMapping);

