let prodCatId;
let prodSta;

// fetch對應到的路徑
const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";

// -----取得元素-----
const prodName_el = document.querySelector('#prodName');
const prodPri_el = document.getElementById('prodPri');
const prodCon_el = document.querySelector('#prodCon');
const btnSubmit_el = document.querySelector('#btnSubmit')

// const inputProdPri_el = this.document.querySelector("#prodPri");

// -----錯誤處理-----
const inputProdName_el = document.getElementById('inputProdName');
const inputProdPri_el = document.getElementById('inputProdPri');
const inputProdCon_el = document.getElementById('inputProdCon');
const inputProdPic_el = document.getElementById('inputProdPic');
const prodNameHint_el = document.getElementById('prodNameHint');
const prodPriHint_el = document.getElementById('prodPriHint');
const prodPicHint_el = document.getElementById('prodPicHint');
const prodConHint_el = document.getElementById('prodConHint');

//-----判斷錯誤處理新增的文字-----
// let iNameStr = document.createElement("span");
// iNameStr.style.color = 'red';

// let iPriStr = document.createElement("span");
// iPriStr.style.color = 'red';

// let iConStr = document.createElement("span");
// iConStr.style.color = 'red';

// let iPicStr = document.createElement("span");
// iPicStr.style.color = 'red';


//當頁面讀取後開始偵測function
window.addEventListener("load", function (e) {

    const prodId_el = document.querySelector('#prodId');

    const prodCatId_el = document.getElementById('prodCatIdSelect');
    prodCatId_el.addEventListener('change', prodCatIdChange)
    function prodCatIdChange() {
        console.log(prodCatId_el.value);
        prodCatId = prodCatId_el.value;
    }

    const prodSta_el = document.getElementById('prodStaSelect');
    prodSta_el.addEventListener('change', prodStaChange)
    function prodStaChange() {
        console.log(prodSta_el.value);
        prodSta = prodSta_el.value;
    }


    // const errorContainer = document.getElementById("errorContainer"); // 創一個 <div> 元素來裝錯誤訊息


    //圖片傳送流程
    //1. 從<input>取得File物件
    const prodPic_el = document.getElementById("prodPic");

    //=======預覽圖=======
    //事件類型 'change': 當使用者選擇了新的檔案時觸發; 函式changeListener: 當事件發生時將被呼叫
    //this 指的是觸發了 'change' 事件的 prodPic_el 元素

    prodPic_el.addEventListener('change', changeListener);
    function changeListener() {
        const id = this.id;
        const files = this.files;
        const img = document.getElementById("cover_img");
        const file = files[0];
        if (!file) {
            img.src = "";
            img.style.width = "0"; // 如果没有檔案，隱藏圖像
            img.style.height = "0";
            return;
        }

        // 設定預覽圖的最大寬度和高度
        const maxWidth = 170; // 調整最大寬度
        const maxHeight = 170; // 調整最大高度
        // img.src = URL.createObjectURL(file);
        const imgUrl = URL.createObjectURL(file);

        img.src = imgUrl;
        img.style.width = `${maxWidth}px`; // 設定圖像的寬度
        img.style.height = `${maxHeight}px`; // 設定圖像的高度

        const previewText = document.querySelector("#coverImgText");
        if (previewText) {
            previewText.style.display = "none";
        }
    }
    //=======預覽圖結束=======

    //當按下送出後
    btnSubmit_el.addEventListener("click", function (e) {
        e.preventDefault()
        let control = true; //控制是否進入fetch
        //==================錯誤驗證==================
        //初始化Str
        // iNameStr.innerHTML = '';
        // iPriStr.innerHTML = '';
        // iConStr.innerHTML = '';
        prodNameHint_el.innerHTML = '';
        prodPriHint_el.innerHTML = '';
        prodPicHint_el.innerHTML = '';
        prodConHint_el.innerHTML = '';

        //商品名稱
        if (prodName.value === null || prodName.value.trim() === "") {
            control = false;
            prodNameHint_el.innerHTML = '*請填寫商品名稱';
        }
        //商品售價
        if (prodPri.value === null || prodPri.value.trim() === "") {
            control = false;
            prodPriHint_el.innerHTML = '*請填寫商品售價';
        } else if (prodPri_el.value < 0 || !(Number.isInteger(Number(prodPri_el.value)))) {
            console.log(prodPri_el.value);
            control = false;
            prodPriHint_el.innerHTML = '*價格必須為正整數';
        }

        //照片
        if (prodPic_el.value == "") {
            control = false;
            prodPicHint_el.innerHTML = '*請選擇商品照片';
        }

        //商品詳情
        if (prodCon.value === null || prodCon.value.trim() === "") {
            control = false;
            prodConHint_el.innerHTML = '*請填寫商品詳情';
        }

        const prodPic = prodPic_el.files[0];

        //2. 實例化FileReader物件-->
        const fileReader = new FileReader();

        if (control) {
            //3. 替FileReader物件 註冊 載入監聽器-->
            fileReader.onload = async event => {

                //4. 轉成Base64字串-->
                const base64Str = btoa(event.target.result);

                //將資料包裝成一個物件-->
                const send_data = {
                    // prodId: prodId_el.value,
                    prodName: prodName_el.value.trim(),
                    prodSta: prodSta,
                    prodCatId: prodCatId_el.value.trim(),
                    prodPri: prodPri_el.value.trim(),
                    prodCon: prodCon_el.value.trim(),
                    prodPic: base64Str
                }
                // console.log(send_data);
                await fetch(baseUrl + "product/createProduct", {
                    headers: { "content-type": "application/json" },
                    method: 'POST',
                    body: JSON.stringify(send_data)
                })
                    .catch(function (error) {
                        Swal.fire({
                            icon: 'error',
                            title: '新增失敗',
                            text: '請檢查填入資料',
                            showCancelButton: true
                        })
                        return;
                    });
                Swal.fire({
                    icon: 'success',
                    title: '新增成功',
                    text: '',
                    confirmButtonText: '確定'
                })
                    .then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = 'productList.html';
                        }
                    });
            };
            try {

                //5. 開始讀取檔案-->
                fileReader.readAsBinaryString(prodPic)
            } catch (e) {
                Swal.fire({
                    icon: 'error',
                    title: '新增失敗',
                    text: '請檢查填入資料',
                    showCancelButton: true
                })
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: '新增失敗',
                text: '請檢查填入資料',
                showCancelButton: true
            })
        }
    });
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

