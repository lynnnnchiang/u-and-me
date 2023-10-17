
$(function () {
  findall();
});

function findall() {

  var baseUrl = window.location.protocol + "//" + window.location.host;

  fetch(baseUrl+"/u-and-me/state/1")  //抓後端Controller的路徑
    .then(function (resp) {
      return resp.json() //要求json檔的資料
    }).then(function (data) {

      let qalist = document.querySelector("#qalist");
      let html = ''; //防止undifended

      let j = 1;
      for (let i = 0; i < data.length; i++) {
        html += `<li>
          <a class="link_title">
              <button type="button" class="switch_btn" onclick='title1(${i})'>
                  <span class="-plus">+</span><span class="-minus">-</span>
              </button>
              <span>Q${j++}.</span>
              <span>${data[i].qaTitle}</span>
          </a>
          <div class="inner_block">
              <span>${data[i].qaCon}</span>
          </div>
          </li>`
      }
      return qalist.innerHTML = html;
    }
    ).catch(function (error) {
      console.error(error);
    })
}

function title1(i) {
   let link_title = document.querySelectorAll(".link_title");
   event.preventDefault(i);
   $(link_title[i]).closest("li").toggleClass("-on");
   $(link_title[i]).closest("li").find("div.inner_block").slideToggle();
}