//全局變數
let detailId = '';

//元素
const baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me";
const bank_el = document.getElementById('bank');
const bankContent_el = document.getElementById('bankContent');
const reason_el = document.getElementById('reason');
const account_el = document.getElementById('account');
const btn_submit_el = document.getElementById('btn_submit');
const inputReason_el = document.getElementById('inputReason');
const inputAccount_el = document.getElementById('inputAccount');
const inputs_el = document.querySelectorAll('.autoTab');

//判斷錯誤要用的字串
let inputReasonStr = document.createElement("span");
inputReasonStr.style.color = 'red';

let inputAccountStr = document.createElement("span");
inputAccountStr.style.color = 'red';

window.addEventListener('load', function (e) {

    //取得網址列detailId
    let url = location.href;

    if (url.indexOf('?') != -1) {

        let ary1 = url.split('?');

        let ary2 = ary1[1].split('=');

        detailId = ary2[1];
    }

    bankContent_el.value = bank_el.value;
});

bank_el.addEventListener('change', function (e) {
    bankContent_el.value = bank_el.value;
    console.log(account_el.value);
});

btn_submit_el.addEventListener('click', async function (e) {
    inputAccountStr.innerHTML = '';
    inputReasonStr.innerHTML = '';

    e.preventDefault();

    let control = true;

    //判斷每個輸入框字數
    try {
        inputs_el.forEach(input => {
            if (input.value.length != 4) {
                throw new Error();
            }
        });
    }catch (e){
        inputAccountStr.innerHTML = ' *帳戶格式不正確';
        inputAccount_el.appendChild(inputAccountStr);
    }


    if (reason.value.trim() == null || reason.value.trim() == "") {
        control = false;
        inputReasonStr.innerHTML = ' *請輸入原因';
        inputReason_el.appendChild(inputReasonStr);
    }

    if (control) {
        const data = {
            account: bankContent_el.value + '-' + account_el.value.trim(),
            reason: reason_el.value,
        }

        await fetch(baseUrl + '/member/memberDetail/' + detailId, {
            headers: {
                "content-type": "application/json",
            },
            method: 'PUT',
            body: JSON.stringify(data)
        }).catch(function (e) {
            Swal.fire({
                icon: 'error',
                title: '退款申請失敗',
                showCancelButton: true
            })
        })

        Swal.fire({
            icon: 'success',
            title: '退款申請成功',
            text: '已提交退款申請',
        }).then(()=>{
            location.href = baseUrl + '/tmp/Front/group/groupFav.html';
        });
    } else {
        Swal.fire({
            icon: 'error',
            title: '退款申請失敗',
            text: '請檢查退款資料',
            showCancelButton: true
        })
    }

});



//自動跳input格子
inputs_el.forEach((input, index) => {
    input.addEventListener('input', (event) => {
        if (event.target.value.length === 4) {
            if (index < inputs_el.length - 1) {
                inputs_el[index + 1].focus();
            }
        }
    });
});