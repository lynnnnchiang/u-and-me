document.addEventListener("DOMContentLoaded", function () {

    async function fetchRandomNews() {
        try {

            const response = await fetch(`http://localhost:8080/u-and-me/activityavailable`);
            const randomNews = await response.json();


            const cardContainer = document.querySelector('.row.row-cols-1.row-cols-md-3.g-4');
            cardContainer.innerHTML = '';

                randomNews.forEach(item => {
                    const card = document.createElement('div');
                    const dataurl = `data:image/png;base64,${item.activPic}`;

                    cardContainer.classList.add('col');
                    card.innerHTML = `
                        <div class="card">
                            <img src="${dataurl}" alt="" class="card-img-top">
                            <div class="card-body" style="height: 150px; overflow: hidden;">
                                <h5 class="card-title">${item.activName}</h5>
                                <p class="card-text">${item.activCon}</p>
                                <div class="btn-container">
                                    <a href="ActivityDetail.html?activId=${item.activId}" class="btn btn-outline-success btn-sm">Read More</a>
                                </div>
                            </div>
                        </div>
                    `;

                    cardContainer.appendChild(card);
                });

        } catch (error) {
           console.error('Error fetching random news:', error);
        }
    }

    fetchRandomNews();
});


//document.addEventListener("DOMContentLoaded", function () {
//async function fetchRandomNews() {
//    try {
//        const response = await fetch('http://localhost:8080/u-and-me/activityavailable');
//        const randomNews = await response.json();
//
//        const cardContainers = document.querySelectorAll('.card');
//
//        randomNews.forEach((item, index) => {
//
//            const cardContainer = cardContainers[index];
//            const cardImg = cardContainer.querySelector('.card-img-top');
//            const cardTitle = cardContainer.querySelector('.card-title');
//            const cardText = cardContainer.querySelector('.card-text');
//            const detailBtn_el = cardContainer.querySelector('.btn-container');
//
//            const dataurl = `data:image/png;base64,${item.activPic}`;
//
//            cardImg.src = dataurl;
//            cardTitle.textContent = item.activName;
//            cardText.textContent = item.activCon;
//
//            detailBtn_el.addEventListener('click', function () {
//                const newPageUrl = `ActivityDetail.html?activId=${item.activId}`;
//                window.location.href = newPageUrl;
//            });
//
//
//        });
//    } catch (error) {
//        console.error('Error fetching random news:', error);
//    }
//}