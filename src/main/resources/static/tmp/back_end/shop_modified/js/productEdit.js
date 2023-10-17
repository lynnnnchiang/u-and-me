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

//取得查詢prodId的值
const urlParams = new URLSearchParams(window.location.search);
const prodId = urlParams.get('prodId');

//表單中各欄位的元素id
const prodId_el = document.getElementById('prodId');
const prodName_el = document.getElementById('prodName');
const prodPri_el = document.getElementById('prodPri');
const prodCon_el = document.getElementById('prodCon');
const btnSubmit_el = document.getElementById('btnSubmit');
//上傳的圖片
let prodPicUpload_el = document.getElementById('prodPicUpload');
//預覽圖
let preview_img_el = document.getElementById('preview_img');
//用於紀錄原始fetch圖片的base64(還沒修改前的圖片)
let cover;

//商品類別下拉選單
let prodCatId;
const prodCatId_el = document.getElementById('prodCatIdSelect');
prodCatId_el.addEventListener('change', prodCatIdChange)
function prodCatIdChange() {
  console.log(prodCatId_el.value);
  prodCatId = prodCatId_el.value;
}
// 上下架下拉選單
let prodSta;
const prodSta_el = document.getElementById('prodStaSelect');
prodSta_el.addEventListener('change', prodStaChange)
function prodStaChange() {
  console.log(prodSta_el.value);
  prodSta = prodSta_el.value;
}
//上下架按鈕
// const prodSta_el = document.getElementById('prodSta'); 
// let prodSta;
// const prodStaButtons_on_el = document.getElementById("prodStaButtons_on");
// const prodStaButtons_off_el = document.getElementById("prodStaButtons_off");
// const buttons = prodStaButtons.querySelectorAll("button");
// prodStaButtons_on_el.addEventListener('click',function(){
//     prodSta = 1;

//     // 添加 active 類別到上架按鈕，移除 active 類別從下架按鈕
//     prodStaButtons_on_el.classList.add('active');
//     prodStaButtons_off_el.classList.remove('active');
// });

// prodStaButtons_off_el.addEventListener('click',function(){
//     prodSta = 0;
//     // 添加 active 類別到下架按鈕，移除 active 類別從上架按鈕
//     prodStaButtons_off_el.classList.add('active');
//     prodStaButtons_on_el.classList.remove('active');
// });



// ==================使用到的元素結束==================

const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

//取得單筆商品資料
async function fetchListProductDetail() {
  try {
    const resp = await fetch(`${baseUrl}product/listProductDetail/${prodId}`);
    const productDetail = await resp.json();
    console.log(productDetail);

    //無選擇新圖片，cover保持舊的圖片數據
    const dataurl = `data:image/jpeg;base64,${productDetail.prodPic}`;
    cover = productDetail.prodPic;

    //將商品詳細資訊放進輸入框中
    //prodId readonly
    prodId_el.value = productDetail.prodId;
    prodCatId_el.value = productDetail.prodCatId;
    prodName_el.value = productDetail.prodName;
    prodCon_el.value = productDetail.prodCon;
    prodPri_el.value = productDetail.prodPri;
    prodSta_el.value = productDetail.prodSta;
    // prodSta.value = productDetail.prodSta;
    preview_img_el.src = dataurl;
    preview_img_el.style.width = '180px'; // 設定寬度為200像素
    preview_img_el.style.height = '180px'; // 設定高度為150像素


  } catch (error) {
    console.error('Error fetching product detail:', error);
  }
}

//網頁載入完成後執行
document.addEventListener('DOMContentLoaded', function () {

  console.log(prodId);
  fetchListProductDetail();

  //選擇圖片時觸發事件
  prodPicUpload_el.addEventListener('change', function () {
    const id = this.id;
    const files = this.files;
    console.log(123);
    cover = files[0];
    //URL.createObjectURL(file)創建一個臨時的URL，指用戶選擇的文件，將圖片顯示在img元素中
    preview_img_el.src = URL.createObjectURL(cover);
    //創建一個FileReader對象，用於讀取文件的內容
    const fileReader = new FileReader();
    //當文件加載/讀取完成，將執行event以下的程式
    // btoa 用於將二進制數據轉換為 Base64 編碼的字符串
    fileReader.onload = async event => {
      cover = btoa(event.target.result);
      console.log(cover);
    };
    //將文件內容讀取為二進位字符串
    fileReader.readAsBinaryString(cover);

  });
});
//     // const img = document.getElementById("cover_img");
// console.log(file);
//     //1. 取得File物件
//     cover_img_el.src = URL.createObjectURL(file);

//     //2. 實例化FileReader物件
//     const fileReader = new FileReader();

//     cover_img_el.src = fileReader.readAsBinaryString(file);
//     //3. 替FileReader物件 註冊 載入監聽器
//     fileReader.onload = async event => {
//         //4. 轉成Base64字串
//         prodPic_el = btoa(event.target.result);
//     }
//     //5. 開始讀取檔案
//     fileReader.readAsBinaryString(prodPic_el);
//   }


//透過this關鍵字來引用觸發事件的元素即上傳圖片的prodPic_el
//files 是文件上傳輸入框的屬性，取第一個文件
// const files = this.files;
// console.log(files);

// cover = files[0];
// console.log(cover);




//按下確認修改按鈕時觸發
btnSubmit_el.addEventListener('click', async function (event) {
  //將要傳遞的資料包裝成一個物件
  const send_data = {
    prodName: prodName_el.value,
    prodSta: prodSta_el.value,
    prodCatId: prodCatId_el.value,
    prodPri: prodPri_el.value,
    prodCon: prodCon_el.value,
    prodPic: cover
  };
  // //檢查cover是否有值，如果有選擇圖片，則更新cover_img_el
  // if(cover){
  //   send_data.cover_img_el = cover;
  // }

  try {
    const resp = await fetch(`${baseUrl}product/updateProduct/${prodId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      //將數據對象轉換為可在請求主體中傳輸的JSON字符串
      body: JSON.stringify(send_data)
    });

    if (resp.ok) {
      const updatedProductData = await resp.json();

      Swal.fire({
        icon: 'question',
        title: '確認',
        text: '確定更新此商品？',
        confirmButtonText: '確定',
        showCancelButton: true,
        cancelButtonText: '取消'
      })
        .then(() => {

          location.href = baseUrl + 'tmp/back_end/shop_modified/productList.html';
        });
    } else {
      console.error('Failed to update product.');
    }
  } catch (error) {
    console.error('Error to update product.', error);
  }

});
// 管理員filter

window.addEventListener("load", function (e) {
  this.fetch(baseUrl + 'host/match', {
    method: 'GET'
  }).then(response => {
    if (response.status == 401) {

      this.location.href = baseUrl + 'tmp/back_end/host/hostLogin.html';

    }
  });
})


// 登出按鈕
const logoutBtn_el = document.getElementById("logOut");
logoutBtn_el.addEventListener("click", async function () {
  const response = await fetch(baseUrl + 'host/hostLogout', {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  }); if (response.ok) {
    Swal.fire({
      icon: 'success',
      title: '管理員登出成功',
      text: '',
      confirmButtonText: '確定'
    }).then(() => {
      location.href = baseUrl + 'tmp/back_end/host/hostLogin.html'
    })
    // location.reload();
  }
});

