
    const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
    // <!--網頁載入後執行-->


window.addEventListener("load", function (e) {
 this.fetch(baseUrl + 'host/match', {
        method: 'GET'
    }).then(response => {
        if(response.status == 401){

                this.location.href = baseUrl + 'tmp/back_end/host/hostLogin.html';

        }
    });
})

    //  <!--生成會員列表-->
    document.addEventListener("DOMContentLoaded", function () {
      fetchHostList();
    });

    async function fetchHostList() {
      try {
        const response = await fetch(baseUrl+'host/hostAll');
        const hostList = await response.json();

        const dataTableList = document.getElementById("dataTableList");
        dataTableList.innerHTML = "";


        hostList.forEach(host => {
          const row = document.createElement("tr");
          row.innerHTML = `
  <td style="width: 50px;text-align: center; vertical-align: middle; ">${host.hostId}</td>
  <td style="width: 100px;text-align: center;vertical-align: middle;">${host.hostName}</td>
  <td style="width: 100px;text-align: center;vertical-align: middle;">${host.hostPhone}</td>
  <td style="width: 65px;text-align: center;vertical-align: middle;">${statusMapping.get(host.hostSta)}</td>
  <td style="width: 65px;text-align: center;vertical-align: middle; backgroung-color:#1D3124;">

    <!--動態視窗觸發按鈕-->

     <button type="button" id="buttonId" class="view_host" data-bs-toggle="modal" data-bs-target="#staticBackdrop${host.hostId}">
      <i class="fa-solid fa-pen fa-xs" style="color: #1d3124;"></i>
       </button>

     <!-- Modal -->

    <div class="modal fade" id="staticBackdrop${host.hostId}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
      aria-labelledby="staticBackdropLabel" aria-hidden="true">

      <!-- Modal-dialog -->
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel" style="padding-left:180px;">管理員明細</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>


          <!-- 小視窗內容 -->
          <div class="modal-body" style="padding-left:70px; height:400px;">

            <table class="table1 table-bordered" id="modal_table" style="width: 350px;height:550px;padding-left: 20px; margin-top:10px;">
              <thead>

              </thead>
              <tbody id="dataTableList1">

                <th style="width: 40px; height:40px;color:beige;  background-color:#1D3124 ;">管理員編號</th>
                <td class="editStyle" style="width: 40px;height:40px;text-align: center; vertical-align: middle; ">${host.hostId}</td>
                <td style="width: 40px;height:40px;text-align: center; vertical-align: middle; ">${host.hostId}</td>



                <tr>
                <th style="width: 40px; height:20px;color:beige;  background-color:#1D3124 ;">姓名</th>
                <td class="editStyle" style="width: 100px;height:50px;text-align: center;vertical-align: middle;">${host.hostName}</td>
                <td style="width: 120px;height:50px;text-align: center;vertical-align: middle;">
                    <input type="text" class="form-control hostName" id="hostName" value="${host.hostName}"
                    style="text-align: center;"></td>
                    </tr>
                <tr>
                <th style="width: 80px; height:30px;color:beige;  background-color:#1D3124 ;">電話</th>
                  <td class="editStyle" style="width: 100px;height:50px;text-align: center;vertical-align: middle;">${host.hostPhone}</td>
                  <td style="width: 120px;height:50px;text-align: center;vertical-align: middle;">
                  <input type="text" class="form-control hostPhone" id="hostPhone" value="${host.hostPhone}"
                    style="text-align: center;"></td>
                </tr>


                <tr>
                  <th style="width: 100px;height:50px;color:beige; background-color:#1D3124 ;">管理員狀態</th>
                  <td class="editStyle"style="width: 65px;height:50px;text-align: center;vertical-align: middle;">${statusMapping.get(host.hostSta)}</td>
                    <td style="width: 65px;height:50px;text-align: center;vertical-align: middle;">
                    <select class=" form-select text-secondary hostSta" id="hostSta">
                     <option value="0" ${host.hostSta === 0 ? 'selected' : ''}>正常使用</option>
                     <option value="1" ${host.hostSta === 1 ? 'selected' : ''}>已停職</option>
                        </select>
                    </td>
                </tr>

                <tr>
                <th style="display:none; width: 5px; height:25px;color:beige;  background-color:#1D3124 ;">管理員信箱</th>
                <td class="editStyle" style="display:none; width: 5px;height:5px;text-align: center;vertical-align: middle;">${host.hostEmail}</td>
                <td style="display:none; width: 5px;height:50px;text-align: center;vertical-align: middle;">
                    <input type="text" class="form-control hostEmail" id="hostEmail" value="${host.hostEmail}"
                    style="display:none; text-align: center;"></td>
                    </tr>



              </tbody>
            </table>

          </div>

                <!--編輯按鈕-->
          <div class="modal-footer">
            <button type="submit" id="editButton"class="editBtn" data-bs-dismiss="modal" data-hostid="${host.hostId}">完成</button>
          </div>

         <!--按下編輯鈕可以修改管理員資訊--!>



        </div>
      </div>
    </div>

  </td>
  `;
          dataTableList.appendChild(row);
        });


      } catch (error) {
        console.error("Error fetching host list:", error);
      }
    }





    const statusMapping = new Map([
      [0, '正常使用'],
      [1, '已停職'],
    ]);




    //  按修改鈕會根據hostId跳轉到詳細內容頁面，並將資料映射到相關欄位上
    function redirectToDetailPage(hostId) {
      var newPageUrl = `hostEdit.html?hostId=${hostId}`;
      window.location.href = newPageUrl;
    }


    //  編輯按鈕按下會能夠更新會員資料


    //const editBtn_el = document.getElementsByClassName("editBtn");



    const hostName = document.getElementsByClassName("hostName");
    const hostEmail = document.getElementsByClassName("hostEmail");
    const hostPhone = document.getElementsByClassName("hostPhone");
    const hostSta = document.getElementsByClassName("hostSta");
    const editBtnElements  = document.getElementsByClassName("editBtn");


   $(document).on("click", ".editBtn", async function(event){


        event.preventDefault();
       // 建立要傳遞的資料
        const hostId = $(this).data('hostid')
        const requestData = {
        "hostEmail": hostEmail[hostId-1].value,
        "hostName": hostName[hostId-1].value,
        "hostPhone": hostPhone[hostId-1].value,
        "hostSta": hostSta[hostId-1].value,}

    try {
        const response = await fetch(`${baseUrl}host/update`, {
            method: 'POST',
            headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestData)
                });

                if (response.ok) {
                    const updatedMemberData = await response.json();
              Swal.fire({
                         icon: 'success',
                         title: '更改成功',
                         text: '',
                         confirmButtonText: '確定'
      })}else {
                    console.error('Failed to update host.');
                }
            } catch (error) {
                console.error('Error to update host.',error);
            }
            location.reload();
   })
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
