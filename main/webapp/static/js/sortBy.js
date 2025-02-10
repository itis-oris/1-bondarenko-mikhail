document.addEventListener('DOMContentLoaded', function () {
    const sortSelect = document.getElementById('sortSelect');

    // Чтение текущего значения сортировки из URL
    const urlParams = new URLSearchParams(window.location.search);
    const currentSort = urlParams.get('sort');

    // Устанавливаем выбранное значение в select
    if (currentSort) {
        sortSelect.value = currentSort;
    }

    // Обработчик изменения значения сортировки
    sortSelect.addEventListener('change', function () {
        const curUrl = window.location.href;
        const selectedSort = sortSelect.value;
        let newUrl = new URL(curUrl);

        // Устанавливаем или удаляем параметр сортировки
        if (selectedSort) {
            newUrl.searchParams.set('sort', selectedSort);
        } else {
            newUrl.searchParams.delete('sort');
        }

        // Перенаправляем на обновленный URL
        window.location.href = newUrl.toString();
    });
});
