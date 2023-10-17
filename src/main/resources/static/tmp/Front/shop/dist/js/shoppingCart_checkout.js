
const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

//-----取得元素-----
const totalPriceCount_el = document.getElementById('totalPriceCount');
const finalTotalPrice_el = document.getElementById('finalTotalPrice');
const memName_el = document.getElementById('memName');
const memEmail_el = document.getElementById('memEmail');
const recipientPhone_el = document.getElementById('recipientPhone');
const recipientName_el = document.getElementById('recipientName');
// const recipientAddr_el = document.getElementById('recipientAddr');
const orderConfirmedBtn_el = document.getElementById('orderConfirmedBtn');
const temp_el = document.getElementById("temp");
const addrText_el =this.document.getElementById('addrText');
// let recipientAddr;

//-----地址元素-----
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
    const cityOptions = document.querySelectorAll("#selectCity > option");
    const distOptions = document.querySelectorAll("#selectDist > option");
    
    const selectCity_el = document.getElementById("selectCity");
    const selectDist_el = document.getElementById("selectDist");

    selectCity_el.addEventListener("change", function(){

        if(parseInt(selectCity_el.value) === 0){
            selectCity_el.classList.add("text-secondary");
        }else{
            selectCity_el.classList.remove("text-secondary");
        }
        
        selectDist_el.classList.add("text-secondary");
        
        const distList1 = dists[selectCity_el.value];
        
        selectDist_el.innerHTML= `<option value="0" class="text-secondary">選擇地區</option>`;
        
        for(let dist of distList1){
            selectDist_el.insertAdjacentHTML("beforeend", `<option value=${dist} class="text-secondary">${dist}</option>`);
        }
        if(parseInt(selectDist_el.value) === 0){
            selectDist_el.classList.add("text-secondary");
        }
        });
        
        
        selectDist_el.addEventListener("change", function(){
        
        if(parseInt(selectDist_el.value) === 0){
            selectDist_el.classList.add("text-secondary");
        }else{
            selectDist_el.classList.remove("text-secondary");
        }
        
        //填入居住縣市
            if(typeof selectCity !== "undefined"){
              for(let cityOption of cityOptions){
                if(cityOption.value === selectCity){
                    cityOption.setAttribute("selected", true);
                }else{
                    cityOption.removeAttribute("selected");
                }
              }
            }
        
            //如果頁面載入有取得縣市，則動態生成地區的選單
            if(typeof cityOptions !== "undefined"){
              if(selectCity_el !== null){
                const distList2 = dists[selectCity.value];
                //memDist.innerHTML= `<option value="0" class="text-secondary">選擇居住地區</option>`;
                for(let dist of distList2){
                selectDist_el.insertAdjacentHTML("beforeend", `<option value=${dist} class="text-secondary">${dist}</option>`);
                }
        
                //填入居住地區
        
                for(let distOption of distOptions){
                  if(distOption.value === selectDist_el){
                    distOption.setAttribute("selected", true);
                  }else{
                    distOption.removeAttribute("selected");
                  }
                }
                //如果居住縣市與地區皆已填入，取消灰階的顯示
                selectCity_el.classList.remove("text-secondary");
                selectDist_el.classList.remove("text-secondary");
              }
            }
          });
       

          
            
// -----錯誤處理-----
const inputMemEmail_el = this.document.getElementById('inputMemEmail');
const inputRecipientName_el = this.document.getElementById('inputRecipientName');
const inputRecipientPhone_el = this.document.getElementById('inputRecipientPhone');
const inputAddr_el = this.document.getElementById('inputAddr');


let emailReg =/^(?:[a-z0-9!#$%&amp;'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&amp;'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])$/;
let phoneReg = /^09[0-9]{8}$/;

//-----判斷錯誤處理新增的文字-----
let emailStr = document.createElement("span");
emailStr.style.color = 'red';

let rNameStr = document.createElement("span");
rNameStr.style.color = 'red';

let rPhoneStr = document.createElement("span");
rPhoneStr.style.color = 'red';

let rAddrStr = document.createElement("span");
rAddrStr.style.color = 'red';
//----------

//變數
let totalPriceCount = 0; //紀錄總計
let finalTotalPrice = 0; //記錄總金額
let ordId;

//-----載入後顯示總金額+會員名/email-----
document.addEventListener("DOMContentLoaded", function () {
  fetchCartList();

  // 先獲取 memId
  fetch(`${baseUrl}member/getMemId`)
    .then(response => response.json())
    .then(data => {
      const memId = data.memId;
      // console.log(data);
      // console.log(memId);
      fetchMember(memId);
    })
    .catch(error => {
      console.error('Error fetching memId:', error);
    });
})

// 再取得會員資料 姓名+email
async function fetchMember(memId) {
  const response = await fetch(`${baseUrl}member/Details/${memId}`);
  const memberDetail = await response.json();
  // console.log(memberDetail);
  memName_el.value = memberDetail.memName;
  memEmail_el.value = memberDetail.memEmail;
}

// 取得整個購物車清單
async function fetchCartList() {
  const response = await fetch(`${baseUrl}showAllCartList`);
  const cartLists = await response.json();
  // console.log(cartLists);

  // // /將總計從httpSession拿出來
  //   async function getTotal() {
  //     const response = await fetch(`${baseUrl}getTotal`, {
  //       method: 'GET',
  //       headers: { 'Content-Type': 'application/json' }
  //     });

  //     if (response.ok) {
  //       // console.log(response);
  //       const data = await response.json();
  //       const total = data.total;
  //       return total;
  //       console.log(total);
  //       // console.log('Response JSON:', total);
  //       // return {total};
  //     } 
  // }
  // finalTotalPrice = total + 80;
  // finalTotalPrice_el.innerText = "訂單總金額:" + finalTotalPrice + "元";
  

  // List 購物車清單 從資料庫拿到每一筆小計+運費就會是總金額顯示
  // 初始化總金額
  //cartLists 是包含所有購物車清單的陣列
  for (const cartList of cartLists) {
    // cartList 是購物車清單中的一項
    // 取到小計金額
    if (cartList.cartPri) {
      // 將 cartPri 的值轉換為數字並加到總金額
      totalPriceCount += parseInt(cartList.cartPri);
    }
  }
    finalTotalPrice = totalPriceCount + 80;
    // console.log(finalTotalPrice);
    // totalPriceCount_el.innerText = "總計:" + totalPriceCount + "元";
    finalTotalPrice_el.innerText = "訂單總金額:" + finalTotalPrice + "元";
    // console.log(totalPriceCount);
  }
  



// -----按下確認結帳按鈕，前往結帳&生成一筆新的訂單-----
  orderConfirmedBtn.addEventListener('click', async function (e) {
    e.preventDefault()
    let control = true; //控制是否進入fetch
//==================錯誤驗證==================
        //初始化Str
        emailStr.innerHTML = '';
        rNameStr.innerHTML = '';
        rPhoneStr.innerHTML = '';
        rAddrStr.innerHTML = '';

        //會員郵件
        if (memEmail_el.value === null || memEmail_el.value.trim() === "") {
          control = false;
          emailStr.innerHTML = '*請填寫E-mail';
          inputMemEmail_el.appendChild(emailStr);
        } else if (!(emailReg.test(memEmail_el.value))) {
          control = false;
          emailStr.innerHTML = '*E-mail格式不正確';
          inputMemEmail_el.appendChild(emailStr);
        }

        //收件人姓名
        if (recipientName.value === null || recipientName.value.trim() === ""){
          control = false;
          rNameStr.innerHTML = '*請填寫收件人姓名';
          inputRecipientName_el.appendChild(rNameStr);
        }
        //收件人電話
        if(recipientPhone.value === null || recipientPhone.value.trim() === ""){
          control = false;
          rPhoneStr.innerHTML = '*請填寫收件人手機號碼';
          inputRecipientPhone_el.appendChild(rPhoneStr);
        }else if (!(phoneReg.test(recipientPhone_el.value))) {
          control = false;
          rPhoneStr.innerHTML = '*手機號碼格式不正確';
          inputRecipientPhone_el.appendChild(rPhoneStr);
        }
        //收件人地址
        if(addrText.value === null || addrText.value.trim() === ""){
          control = false;
          rAddrStr.innerHTML = '*請填寫收件人地址';
          inputAddr_el.appendChild(rAddrStr);
        } 
    
         // 當選擇的城市或區域改變時，更新 recipientAddr
         selectCity_el.addEventListener('change', updateRecipientAddr);
         selectDist_el.addEventListener('change', updateRecipientAddr);
         addrText_el.addEventListener('input', updateRecipientAddr);
 
        //如果沒錯誤，先建立一個訂單產生訂單編號
      if (control) {
        
        // 將資料包裝成一個物件
        const send_data = {
          recipientPhone: recipientPhone_el.value,
          recipientName: recipientName_el.value,
          recipientAddr: updateRecipientAddr(),
          total: totalPriceCount,
          checktotal: finalTotalPrice
        }
        // console.log(totalPriceCount);
        // console.log(finalTotalPrice);
        const response = fetch(`${baseUrl}member/orders`, {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(send_data)
      }).then(response => {
        if (response.ok) {
          fetch(`${baseUrl}findMaxId`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
          }).then(response => {
            return response.json();
          }).then(maxOrdDTO => {
            // console.log(ordDTO);
            ordId = maxOrdDTO.maxOrdId;
            console.log(ordId);
          // 利用訂單編號，前往結帳

            fetch(`${baseUrl}shopOrder/shopCheckout/${ordId}`, {
              method: 'POST',
              headers: { "Content-Type": "application/x-www-form-urlencoded" }
            }).then(res => {
              return res.text();
            }).then(sub => {
              temp_el.innerHTML = sub;
              console.log("Trying to get element: allPayAPIForm");
              document.getElementById('allPayAPIForm').submit();
              temp_el.innerHTML = '';
            });

            fetch(`${baseUrl}shopOrder/updateOrdPaySta/${ordId}`,{
            method: 'GET'
          });
          fetch(`${baseUrl}shopOrder/updateOrdSta/${ordId}`,{
            methodL:'GET'
          });

          })
          // console.log(response);
          
        } else {
          console.error('Failed to submit order.');
        }
      });

    }
  });
  function updateRecipientAddr() {
    const selectedCity = selectCity_el.value;
    const selectedDist = selectDist_el.value;
    const enteredAddr = addrText_el.value;
    
    const recipientAddr = `${selectedCity}${selectedDist}${enteredAddr}`;
    console.log('Recipient Address:', recipientAddr);
    return recipientAddr;
  }


// 過了頁面就拿不到，就要放session 
// .then(跳轉結帳);
//新增一筆訂單
  // .then(寄email通知);
  // 購物車刪除
  // .then(跳轉到list訂單資料頁面);

