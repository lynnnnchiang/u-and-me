document.addEventListener('DOMContentLoaded', function () {
    const searchForm = document.getElementById('searchForm');
    searchForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const searchInput = document.getElementById('searchInput');
        const searchText = searchInput.value.trim();

        if (searchText === '') {
            alert('請輸入活動名稱或內容');
            return;
        }

        const newPageUrl = `ActivityResults.html?searchText=${encodeURIComponent(searchText)}`;
        window.location.href = newPageUrl;
    });
});