document.addEventListener("DOMContentLoaded", function () {
    fetchRandomPeriods();
});

async function fetchRandomPeriods() {
    try {
        const response = await fetch('http://localhost:8080/u-and-me/activityavailable');
        const randomPeriods = await response.json();

        const cardContainer = document.querySelector('.row.row-cols-1.row-cols-md-3.g-4');
        cardContainer.innerHTML = '';

        randomPeriods.forEach((item) => {
            const card = document.createElement('div');
            const dataurl = `data:image/png;base64,${item.activPic}`;

            card.classList.add('col-md-4', 'mt-4');
            card.innerHTML = `
            <div class="card profile-card-5">
                 <div class="card-img-block">
                     <img src="${dataurl}" alt="" class="card-img-top">
                 </div>
                     <div class="card-body  pt-0" style="height: 150px; overflow: hidden;">
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
        console.error('Error fetching random periods:', error);
    }
}