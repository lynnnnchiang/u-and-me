let baseUrl = window.location.protocol + "//" + window.location.host + "/u-and-me/";
const randomSchedule_el = document.querySelector("div.Schedule");

document.addEventListener("DOMContentLoaded", function () {

    fetchRandomActivities();
});

async function fetchRandomActivities() {
    try {

        // < !--card1-->
        const cardContainer1 = document.querySelector('.row.row-cols-1.row-cols-md-3.g-3');
        cardContainer1.innerHTML = '';

        // < !--card2-->
        const cardContainer2 = document.querySelector('.row.row-cols-1.row-cols-md-4.g-4');
        cardContainer2.innerHTML = '';

        // < !--card3-->
        const cardContainer3 = document.querySelector('.row.row-cols-1.row-cols-md-3.g-3');
        cardContainer3.innerHTML = '';


        const response1 = await fetch(baseUrl + "activityrandom?count=3");
        const randomActivities1 = await response1.json();

        const response2 = await fetch(baseUrl + "activityrandom?count=4");
        const randomActivities2 = await response2.json();

        const response3 = await fetch(baseUrl + "schedules/all");
        const randomActivities3 = await response3.json();


        // < !--card1-->
        randomActivities1.forEach(item => {
            const card = document.createElement('div');
            const dataurl = `data:image/png;base64,${item.activPic}`;

            card.classList.add('col');
            card.innerHTML = `
                <div class="card shadow-sm">
                    <div class="hovereffect">
                        <img class="bd-placeholder-img card-img-top img-fluid" src="${dataurl}" alt="Loading...">
                        <div class="overlay">
                            <h2>${item.activName}</h2>
                            <p><a href="ActivityDetail.html?activId=${item.activId}">More Info</a></p>
                        </div>
                    </div>
                    <div class="card-body card-content">
                        <p class="card-text">${item.activCon}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <!-- 可以加更多的内容 -->
                        </div>
                    </div>
                </div>
            `;

            cardContainer1.appendChild(card);
        });


        // < !--card2-->
        randomActivities2.forEach(item => {
            const card = document.createElement('div');
            const dataurl2 = `data:image/png;base64,${item.activPic}`;

            card.classList.add('col');
            card.innerHTML = `
                <div class="card shadow-sm">
                    <div class="hovereffect1">
                        <img class="bd-placeholder-img card-img-top img-responsive" src="${dataurl2}" alt="Loading...">
                        <div class="overlay">
                            <h2>${item.activName}</h2>
                            <p><a href="ActivityDetail.html?activId=${item.activId}">More Info</a></p>
                        </div>
                    </div>
                    <div class="card-body">
                        <p class="card-text">${formatDate(item.activStarttime)} - <br>${formatDate(item.activEndtime)}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <!-- 可以加更多的内容 -->
                        </div>
                    </div>
                </div>
            `;

            cardContainer2.appendChild(card);
        });


        //  < !--card3-->
        randomSchedule_el.innerHTML = "";

        const imagePaths = [
            "./mon.jpg",
            "./walk.jpg",
            "./travel.jpg"

        ];

        let imageIndex = 0; // 初始化圖像索引

        randomActivities3.forEach(item => {
            const card = document.createElement('div');

            card.classList.add('col');
            card.innerHTML = `
                <div class="card shadow-sm">
                    <div class="hovereffect">
                        <img class="bd-placeholder-img card-img-top img-fluid" src="${imagePaths[imageIndex]}" alt="Loading...">
                        <div class="overlay">
                            <h2>${item.schName}</h2>
                            <p><a href="../schedule/myScheduleEdit.html?schId=${item.schId}">More Info</a></p>
                        </div>
                    </div>
                    <div class="card-body card-content">
                        <p class="card-text">${formatDate(item.schStart)} - ${formatDate(item.schEnd)}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <!-- 可以加更多的内容 -->
                        </div>
                    </div>
                </div>
            `;

            randomSchedule_el.appendChild(card);


            imageIndex++;

            if (imageIndex >= imagePaths.length) {
                    imageIndex = 0;
            }

        });


    } catch (error) {
        console.error('Error fetching random activities:', error);
    }
}



function formatDate(dateString) {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', options);
}