
//會員地址下拉式選單
    const dists = {
'臺北市': ['中正區', '大同區', '中山區', '萬華區', '信義區', '松山區', '大安區', '南港區', '北投區', '內湖區', '士林區', '文山區'],
'新北市': ['板橋區', '新莊區', '泰山區', '林口區', '淡水區', '金山區', '八里區', '萬里區', '石門區', '三芝區', '瑞芳區', '汐止區', '平溪區', '貢寮區', '雙溪區', '深坑區', '石碇區', '新店區', '坪林區', '烏來區', '中和區', '永和區', '土城區', '三峽區', '樹林區', '鶯歌區', '三重區', '蘆洲區', '五股區'],
'基隆市': ['仁愛區', '中正區', '信義區', '中山區', '安樂區', '暖暖區', '七堵區'],
'桃園市': ['桃園區', '中壢區', '平鎮區', '八德區', '楊梅區', '蘆竹區', '龜山區', '龍潭區', '大溪區', '大園區', '觀音區', '新屋區', '復興區'],
'新竹縣': ['竹北市', '竹東鎮', '新埔鎮', '關西鎮', '峨眉鄉', '寶山鄉', '北埔鄉', '橫山鄉', '芎林鄉', '湖口鄉', '新豐鄉', '尖石鄉', '五峰鄉'],
'新竹市': ['東區', '北區', '香山區'],
'苗栗縣': ['苗栗市', '通霄鎮', '苑裡鎮', '竹南鎮', '頭份鎮', '後龍鎮', '卓蘭鎮', '西湖鄉', '頭屋鄉', '公館鄉', '銅鑼鄉', '三義鄉', '造橋鄉', '三灣鄉', '南庄鄉', '大湖鄉', '獅潭鄉', '泰安鄉'],
'臺中市': ['中區', '東區', '南區', '西區', '北區', '北屯區', '西屯區', '南屯區', '太平區', '大里區', '霧峰區', '烏日區', '豐原區', '后里區', '東勢區', '石岡區', '新社區', '和平區', '神岡區', '潭子區', '大雅區', '大肚區', '龍井區', '沙鹿區', '梧棲區', '清水區', '大甲區', '外埔區', '大安區'],
'南投縣': ['南投市', '埔里鎮', '草屯鎮', '竹山鎮', '集集鎮', '名間鄉', '鹿谷鄉', '中寮鄉', '魚池鄉', '國姓鄉', '水里鄉', '信義鄉', '仁愛鄉'],
'彰化縣': ['彰化市', '員林鎮', '和美鎮', '鹿港鎮', '溪湖鎮', '二林鎮', '田中鎮', '北斗鎮', '花壇鄉', '芬園鄉', '大村鄉', '永靖鄉', '伸港鄉', '線西鄉', '福興鄉', '秀水鄉', '埔心鄉', '埔鹽鄉', '大城鄉', '芳苑鄉', '竹塘鄉', '社頭鄉', '二水鄉', '田尾鄉', '埤頭鄉', '溪州鄉'],
'雲林縣': ['斗六市', '斗南鎮', '虎尾鎮', '西螺鎮', '土庫鎮', '北港鎮', '莿桐鄉', '林內鄉', '古坑鄉', '大埤鄉', '崙背鄉', '二崙鄉', '麥寮鄉', '臺西鄉', '東勢鄉', '褒忠鄉', '四湖鄉', '口湖鄉', '水林鄉', '元長鄉'],
'嘉義縣': ['太保市', '朴子市', '布袋鎮', '大林鎮', '民雄鄉', '溪口鄉', '新港鄉', '六腳鄉', '東石鄉', '義竹鄉', '鹿草鄉', '水上鄉', '中埔鄉', '竹崎鄉', '梅山鄉', '番路鄉', '大埔鄉', '阿里山鄉'],
'嘉義市': ['東區', '西區'],
'臺南市': ['中西區', '東區', '南區', '北區', '安平區', '安南區', '永康區', '歸仁區', '新化區', '左鎮區', '玉井區', '楠西區', '南化區', '仁德區', '關廟區', '龍崎區', '官田區', '麻豆區', '佳里區', '西港區', '七股區', '將軍區', '學甲區', '北門區', '新營區', '後壁區', '白河區', '東山區', '六甲區', '下營區', '柳營區', '鹽水區', '善化區', '大內區', '山上區', '新市區', '安定區'],
'高雄市': ['楠梓區', '左營區', '鼓山區', '三民區', '鹽埕區', '前金區', '新興區', '苓雅區', '前鎮區', '小港區', '旗津區', '鳳山區', '大寮區', '鳥松區', '林園區', '仁武區', '大樹區', '大社區', '岡山區', '路竹區', '橋頭區', '梓官區', '彌陀區', '永安區', '燕巢區', '田寮區', '阿蓮區', '茄萣區', '湖內區', '旗山區', '美濃區', '內門區', '杉林區', '甲仙區', '六龜區', '茂林區', '桃源區', '那瑪夏區'],
'屏東縣': ['屏東市', '潮州鎮', '東港鎮', '恆春鎮', '萬丹鄉', '長治鄉', '麟洛鄉', '九如鄉', '里港鄉', '鹽埔鄉', '高樹鄉', '萬巒鄉', '內埔鄉', '竹田鄉', '新埤鄉', '枋寮鄉', '新園鄉', '崁頂鄉', '林邊鄉', '南州鄉', '佳冬鄉', '琉球鄉', '車城鄉', '滿州鄉', '枋山鄉', '霧台鄉', '瑪家鄉', '泰武鄉', '來義鄉', '春日鄉', '獅子鄉', '牡丹鄉', '三地門鄉'],
'宜蘭縣': ['宜蘭市', '羅東鎮', '蘇澳鎮', '頭城鎮', '礁溪鄉', '壯圍鄉', '員山鄉', '冬山鄉', '五結鄉', '三星鄉', '大同鄉', '南澳鄉'],
'花蓮縣': ['花蓮市', '鳳林鎮', '玉里鎮', '新城鄉', '吉安鄉', '壽豐鄉', '秀林鄉', '光復鄉', '豐濱鄉', '瑞穗鄉', '萬榮鄉', '富里鄉', '卓溪鄉'],
'臺東縣': ['臺東市', '成功鎮', '關山鎮', '長濱鄉', '海端鄉', '池上鄉', '東河鄉', '鹿野鄉', '延平鄉', '卑南鄉', '金峰鄉', '大武鄉', '達仁鄉', '綠島鄉', '蘭嶼鄉', '太麻里鄉'],
'澎湖縣': ['馬公市', '湖西鄉', '白沙鄉', '西嶼鄉', '望安鄉', '七美鄉'],
'金門縣': ['金城鎮', '金湖鎮', '金沙鎮', '金寧鄉', '烈嶼鄉', '烏坵鄉'],
'連江縣': ['南竿鄉', '北竿鄉', '莒光鄉', '東引鄉'],
'0': []
}
      const myCityOptions = document.querySelectorAll("#myMemCity > option");
      const myDistOptions = document.querySelectorAll("#myMemDist > option");

      const memCity = document.getElementById("myMemCity");
      const memDist = document.getElementById("myMemDist");
      const memAddrInput1 = document.getElementById("memAddrInput1");
      const memAddrInput2 = document.getElementById("memAddrInput2");

 memCity.addEventListener("click", function(){
  memAddrInput1.value = memCity.value;
if(parseInt(memCity.value) === 0){
memCity.classList.add("text-secondary");
}else{
memCity.classList.remove("text-secondary");
}

memDist.classList.add("text-secondary");

const distList1 = dists[memCity.value];

memDist.innerHTML= `<option value="0" class="text-secondary">選擇您的居住地區</option>`;

for(let dist of distList1){
memDist.insertAdjacentHTML("beforeend", `<option value=${dist} class="text-secondary">${dist}</option>`);
}

if(parseInt(memDist.value) === 0){
memDist.classList.add("text-secondary");
}

});
memDist.addEventListener("change", function(){
  memAddrInput2.value = memDist.value;
if(parseInt(memDist.value) === 0){
memDist.classList.add("text-secondary");
}else{
memDist.classList.remove("text-secondary");
}

//填入居住縣市
    if(typeof memName !== "undefined"){
      for(let myCityOption of myCityOptions){
        if(myCityOption.value === memCity){
          myCityOption.setAttribute("selected", true);
        }else{
          myCityOption.removeAttribute("selected");
        }
      }
    }

    //如果頁面載入有取得縣市，則動態生成地區的選單
    if(typeof memName !== "undefined"){
      if(memCity !== null){
        const distList2 = dists[memCity.value];
        for(let dist of distList2){
          memDist.insertAdjacentHTML("beforeend", `<option value=${dist} class="text-secondary">${dist}</option>`);
        }

        //填入居住地區
        for(let myDistOption of myDistOptions){
          if(myDistOption.value === memDist){
            myDistOption.setAttribute("selected", true);
          }else{
            myDistOption.removeAttribute("selected");
          }
        }
        //如果居住縣市與地區皆已填入，取消灰階的顯示
        memCity.classList.remove("text-secondary");
        memDist.classList.remove("text-secondary");
      }
    }

});


const baseUrL = window.location.protocol + "//" + window.location.host + "/u-and-me/";

// <!--網頁載入後評斷是否為會員-->


window.addEventListener("load", function (e) {
this.fetch(baseUrL + 'member/match', {
    method: 'GET'
}).then(response => {
    if(response.status == 401){

            this.location.href = baseUrL + 'tmp/Front/member/memberLogin.html';

    }
});
})   




//在網頁載入完成後載入會員原先資料
        document.addEventListener("DOMContentLoaded", function () {
          console.log("HIIIII");
             fetchMemDetail();
        });





// 表單中的各輸入框元素
const memName = document.getElementById('memName');
const memIdcard = document.getElementById('memIdcard');
const memPhone = document.getElementById('memPhone');
const myAddrInput = document.getElementById("myAddrInput");
const myMemDist = document.getElementById("myMemDist");
const myMemCity = document.getElementById("myMemCity");
const memGender = document.getElementById("memGender");
const memPassword = document.getElementById("memPassword");
const newPassword = document.getElementById("newPassword");
const submitBtn = document.querySelector('button[type="submit"]');
var memEmail;

  const inputMemName_el = this.document.getElementById("inputMemName");
  const inputMemPhone_el = this.document.getElementById("inputMemPhone");
  const inputMyAddr_el = this.document.getElementById("inputMyAddr");
  const inputNewPassword_el = this.document.getElementById("inputNewPassword");


 //錯誤判斷新增的字串
    let memNameStr = document.createElement("span");
    memNameStr.style.color = 'red';

    let memPhoneStr = document.createElement("span");
    memPhoneStr.style.color = 'red';

    let myAddrInputStr = document.createElement("span");
    myAddrInputStr.style.color = 'red';

    let newPasswordStr = document.createElement("span");
    newPasswordStr.style.color = 'red';


   //////////


// 使用 fetch API 發送請求，獲取單筆會員詳細資料
async function fetchMemDetail() {
    try {
        const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
        const res = await fetch(baseUrl + `member/getMemId`);
        const member = await res.json();
        const memId = member.memId;
        const response = await fetch(baseUrl + `member/Details/${memId}`);
        const MemDetail = await response.json();
        memEmail = MemDetail.memEmail;



        // 將會員詳細資訊填充到輸入框中
        memEmail.value = MemDetail.memEmail;
        memName.value = MemDetail.memName;
        memIdcard.value = MemDetail.memIdcard;
        memPhone.value = MemDetail.memPhone;
        memPassword.value = MemDetail.memPassword;
        newPassword.value = MemDetail.memPassword;
        myMemCity.value = MemDetail.memCity;
        myMemDist.value = MemDetail.memDist;
        myAddrInput.value = MemDetail.memAddr;
        memGender.value = MemDetail.memGender;
        memAddrInput1.value = MemDetail.memCity;
        memAddrInput2.value = MemDetail.memDist;


    } catch (error) {
        console.error('Error fetching Member detail:', error);
    }
}
//會員登出鍵
const logoutBtn_el = document.getElementById("logoutBtn");
logoutBtn_el.addEventListener("click", async function () {
 const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
    const response = await fetch(baseUrl + 'member/logout', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        }
    });
       location.reload();
})

// 在提交審核按鈕被點擊時執行以下程式碼
// 修改

submitBtn.addEventListener('click', async function (event) {
    event.preventDefault()
    let control = true; //控制是否進入fetch
    //==================錯誤驗證==================
        //初始化Str
        memNameStr.innerHTML = "";
        memPhoneStr.innerHTML = '';
        myAddrInputStr.innerHTML = '';
        newPasswordStr.innerHTML = '';


        //會員姓名
        if (memName.value === null || memName.value.trim() === "") {
            control = false;
            memNameStr.innerHTML = ' *會員姓名必須填入';
            inputMemName_el.appendChild(memNameStr);
        }

        //會員電話
        if (memPhone.value === null || memPhone.value.trim() === "") {
            control = false;
            memPhoneStr.innerHTML = ' *會員電話必須填入';
            inputMemPhone_el.appendChild(memPhoneStr);
                }
        //會員地址
        if (myAddrInput.value === null || myAddrInput.value.trim() === "") {
            control = false;
            myAddrInputStr.innerHTML = ' *會員地址必須填入';
            inputMyAddr_el.appendChild(myAddrInputStr);
                        }

        //會員密碼
        const passwordCheck = /^[A-Za-z\d!@#$%^&*()]{8,12}$/;
        if (newPassword.value === null || newPassword.value.trim() === "") {
          control = false;
          newPasswordStr.innerHTML = '會員密碼必須填入';
          inputNewPassword_el.appendChild(newPasswordStr);
                      }else if (!passwordCheck.test(newPassword.value)) {
                        control = false;
                        newPasswordStr.innerHTML = ' *密碼必須介於8至12字元';
                        inputNewPassword_el.appendChild(newPasswordStr);
                       }


console.log(newPassword.value);

    // 構建要傳遞的資料
    const requestData = {
           "memEmail":memEmail,
           "memPassword": newPassword.value,
           "memName": memName.value,
           "memIdcard": memIdcard.value,
           "memPhone": memPhone.value,
           "memAddr": myAddrInput.value,
           "memDist": myMemDist.value,
           "memCity": myMemCity.value,
           "memGender": memGender.value

    };
    if(control){


    try {

        const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
        const response = await fetch(`${baseUrl}member/update`, {
            method:  'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const updatedMemberData = await response.json();

            Swal.fire({
                icon: 'success',
                title: '更新成功',
                text: '',
                confirmButtonText: '確定'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = 'memberCenter.html';
                }
            });


        } else {
            console.error('Failed to update Member.');
        }
    } catch (error) {
        console.error('Error to update Member.', error);
    }
  }
});
