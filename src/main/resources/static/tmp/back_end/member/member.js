//  網頁載入後執行
const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

        document.addEventListener("DOMContentLoaded", function () {
            fetchMemberList();
        });

        async function fetchMemberList() {
            try {
                const response = await fetch(baseUrl+'hostToMember/all');
                const memberList = await response.json();

                const dataTableList = document.getElementById("dataTableList");
                dataTableList.innerHTML = "";

                memberList.forEach(activity => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td style="width: 50px;text-align: center; vertical-align: middle;">${member.memId}</td>
                        <td style="width: 100px;text-align: center;vertical-align: middle;">${member.memName}</td>
                        <td style="width: 100px;text-align: center;vertical-align: middle;">${member.memPhone}</td>
                        <td style="width: 100px;text-align: center;vertical-align: middle;">${member.memIdcard}</td>
                        <td style="width: 70px;text-align: center;"><img src="data:image/jpeg;base64,${base64Image}" alt="身分證照片" width="100" height="100"></td>
                        <td style="width: 65px;text-align: center;vertical-align: middle;">${statusMapping.get(member.memSta)}</td>
                        <td style="width: 65px;text-align: center;vertical-align: middle;">${statusMappingGroup.get(member.memGroup)}</td>
                        <td style="width: 65px;text-align: center;vertical-align: middle;">
                         <button class="btn btn-outline-success" onclick="redirectToDetailPage(${member.memId})">
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"fill="currentColor" class="bi bi-pen" viewBox="0 0 16 16">
                          <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"/>
                          </svg>
                          </button>
                        </td>
                    `;
                    dataTableList.appendChild(row);
                });

            } catch (error) {
                console.error("Error fetching member list:", error);
            }
        }


        function formatDate(dateString) {
              const options = { year: 'numeric', month: 'long', day: 'numeric' };
              return new Date(dateString).toLocaleDateString('zh-TW', options);
        }

        const statusMapping = new Map([
            [0, '註冊未驗證'],
            [1, '正常使用'],
            [2, '停止使用'],
        ]);

        const statusMappingGroup = new Map([
            [0, '非團主'],
            [1, '團主'],
        ]);

        // <!--按修改鈕會根據memId跳轉到詳細內容頁面，並將資料映射到相關欄位上-->
        function redirectToDetailPage(memId) {
              var newPageUrl = `activityEdit.html?memId=${memId}`;
              window.location.href = newPageUrl;
        }


