document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const activName = urlParams.get('searchText');

    async function fetchSearchResults() {
        try {
            const response = await fetch(`http://localhost:8080/u-and-me/name/${activName}`);
            const searchResults = await response.json();

            const cardContainer1 = document.querySelector('.row.row-cols-1.row-cols-md-3.g-3');
            cardContainer1.innerHTML = '';

            if (searchResults.length === 0) {
                cardContainer1.innerHTML = '<h5>查無活動</h5>';
            } else {
                searchResults.forEach(item => {
                    const card = document.createElement('div');
                    const dataurl = `data:image/png;base64,${item.activPic}`;

                    cardContainer1.classList.add('col');
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

                    cardContainer1.appendChild(card);
                });
            }
        } catch (error) {
            console.error('查詢錯誤：', error);
        }
    }

    fetchSearchResults();
});