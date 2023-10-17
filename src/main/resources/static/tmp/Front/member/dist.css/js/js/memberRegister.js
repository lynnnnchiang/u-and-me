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
      const myCityOptions = document.querySelectorAll("#my-mem-city > option");
      const myDistOptions = document.querySelectorAll("#my-mem-city > option");

      const memCity = document.getElementById("my-mem-city");
      const memDist = document.getElementById("my-mem-dist");

memCity.addEventListener("change", function(){

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
        //memDist.innerHTML= `<option value="0" class="text-secondary">選擇居住地區</option>`;
        for(let dist of distList2){
        console.log(":)))");
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




  
/////////////////////////////

      const memEmail = document.getElementById("memEmail");
      const memPassword = document.getElementById("memPassword");
      const memName = document.getElementById("memName");
      const memPhone = document.getElementById("memPhone");
      const memAddr = document.getElementById("my-addr-input");
      const memGender = document.getElementById("memGender");

//錯誤處理

  const inputMemName_el = this.document.getElementById("inputMemName");
  const inputMemEmail_el = this.document.getElementById("inputMemEmail");
  const inputMemPassword_el = this.document.getElementById("inputMemPassword");
  const inputMemPhone_el = this.document.getElementById("inputMemPhone");
  const inputMyAddr_el = this.document.getElementById("inputMyAddr");


 //錯誤判斷新增的字串
    let memNameStr = document.createElement("span");
    memNameStr.style.color = 'red';

    let memEmailStr = document.createElement("span");
    memEmailStr.style.color = 'red';

    let memPasswordStr = document.createElement("span");
    memPasswordStr.style.color = 'red';

    let memPhoneStr = document.createElement("span");
    memPhoneStr.style.color = 'red';

    let myAddrInputStr = document.createElement("span");
    myAddrInputStr.style.color = 'red';

   //////////




  function Register(member) {
//      console.log(123);
      const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
       const response = fetch(baseUrl + "member/register", {
          method: "POST",
          headers: {
              "Content-Type": "application/json",
          },
          body: JSON.stringify(member)
      }).then(response => {
        if (response.ok) {
          Swal.fire({
                         icon: 'success',
                         title: '註冊成功',
                         text: '',
                         confirmButtonText: '確定'
      }).then(() => {window.history.go(-1);
                            });
   }else if(response.status == 400){
                            Swal.fire({
                                icon: 'error',
                                title: '此信箱已註冊過會員',
                                text: '請再次確認帳號',
                                showCancelButton: true
                            }).then(() => {
                                this.location.href = baseUrl + '/tmp/Front/member/memberLogin.html';
                            })
                        }
      })
   }


//按下註冊按鈕
  const regBtn_el = document.getElementById("regButton");
  regBtn_el.addEventListener("click", function(event) {

   event.preventDefault()
      let control = true; //控制是否進入fetch
      //==================錯誤驗證==================
          //初始化Str
          memNameStr.innerHTML = '';
          memPhoneStr.innerHTML = '';
          memPasswordStr.innerHTML = '';
          memEmailStr.innerHTML = '';
          myAddrInputStr.innerHTML = '';

          //會員姓名
          if (memName.value === null || memName.value.trim() === "") {
              control = false;
              memNameStr.innerHTML = ' *會員姓名必須填入';
              inputMemName_el.appendChild(memNameStr);
          }
          //會員電話
          const phoneCheck = /^09[0-9]{8}$/;
          if (memPhone.value === null || memPhone.value.trim() === "") {
              control = false;
              memPhoneStr.innerHTML = ' *會員電話必須填入';
              inputMemPhone_el.appendChild(memPhoneStr);
                  }else if (!phoneCheck.test(memPhone.value)) {
                    control = false;
                    memPhoneStr.innerHTML = ' *請輸入正確的手機號碼格式';
                    inputMemPhone_el.appendChild(memPhoneStr);
                   }
          //會員地址
          if (memAddr.value === null || memAddr.value.trim() === "") {
              control = false;
              myAddrInputStr.innerHTML = ' *會員地址必須填入';
              inputMyAddr_el.appendChild(myAddrInputStr);
                          }

          //會員密碼
          const passwordCheck = /^[A-Za-z\d!@#$%^&*()]{8,12}$/;
          if (memPassword.value === null || memPassword.value.trim() === "") {
              control = false;
              memPasswordStr.innerHTML = ' *會員密碼必須填入';
              inputMemPassword_el.appendChild(memPasswordStr);
              }else if (!passwordCheck.test(memPassword.value)) {
              control = false;
              memPasswordStr.innerHTML = ' *密碼必須介於8至12字元';
              inputMemPassword_el.appendChild(memPasswordStr);
             }

          //會員信箱
          const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
          if (memEmail.value === null || memEmail.value.trim() === "") {
              control = false;
              memEmailStr.innerHTML = ' *會員信箱必須填入';
              inputMemEmail_el.appendChild(memEmailStr);
              }else if (!emailRegex.test(memEmail.value)) {
              control = false;
              memEmailStr.innerHTML = ' *必須符合信箱格式';
              inputMemEmail_el.appendChild(memEmailStr);
              }

      const member = {
          "memEmail": memEmail.value,
          "memPassword": memPassword.value,
          "memName": memName.value,
          "memPhone": memPhone.value,
          "memCity":memCity.value,
          "memDist":memDist.value,
          "memAddr":memAddr.value,
          "memGender": memGender.value

      };

      if(control){
//          console.log(123);
        Register(member);
      }
  });

