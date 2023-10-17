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

// ----------------------------------------------------------------------------------------------
const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

// <!--網頁載入後執行-->
document.addEventListener("DOMContentLoaded", function () {
    fetchActivityList();
});

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


async function fetchActivityList() {
    try {
        const response = await fetch(`${baseUrl}activityall`);
        const activityList = await response.json();

        const dataTableList = document.getElementById("dataTableList");
        dataTableList.innerHTML = "";

        activityList.forEach(activity => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td style="width: 60px;text-align: center; vertical-align: middle;">${activity.activId}</td>
                <td style="width: 100px;text-align: center;vertical-align: middle;">${activity.activName}</td>
                <td style="vertical-align: middle;">${activity.activCon}</td>
                <td style="width: 120px;text-align: center;vertical-align: middle;">${formatDate(activity.activStarttime)}</td>
                <td style="width: 120px;text-align: center;vertical-align: middle;">${formatDate(activity.activEndtime)}</td>
                <td style="width: 65px;text-align: center;vertical-align: middle;">${statusMapping.get(activity.activSta)}</td>
                <td style="width: 65px;text-align: center;vertical-align: middle;">
                 <button class="btn btn-outline-success" onclick="redirectToDetailPage(${activity.activId})">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pen" viewBox="0 0 16 16">
                  <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"/>
                  </svg>
                  </button>
                </td>
            `;
            dataTableList.appendChild(row);
        });

    } catch (error) {
        console.error("Error fetching activity list:", error);
    }
}


//function formatDate(dateString) {
//      const options = { year: 'numeric', month: 'long', day: 'numeric' };
//      return new Date(dateString).toLocaleDateString('zh-TW', options);
//}


function formatDate(dateString) {
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
    const formattedDate = new Date(dateString).toLocaleString('zh-TW', options).replace(/\//g, '-');

    return formattedDate.replace(' ', '\n');
}

const formattedDate = formatDate('2023-09-01T00:30:00');
console.log(formattedDate); // 输出格式为 "2023-09-01 14:30"



const statusMapping = new Map([
    [0, '下架'],
    [1, '上架'],
]);

// <!--按修改鈕會根據activityId跳轉到詳細內容頁面，並將資料映射到相關欄位上-->
function redirectToDetailPage(activId) {
    var newPageUrl = `activityEdit.html?activId=${activId}`;
    window.location.href = newPageUrl;
}

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