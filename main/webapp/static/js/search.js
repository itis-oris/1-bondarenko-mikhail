document.addEventListener('DOMContentLoaded', function ()
{
    const form = document.getElementById('searchForm')
    form.addEventListener('submit', function (event) {
        const curUrl = window.location.href
        const searchQuery = form.elements['name'].value
        let newUrl = new URL(curUrl)
        if (searchQuery) {
            newUrl.searchParams.set('name', searchQuery)
        } else {
            newUrl.searchParams.delete('name')
        }

        window.location.href = newUrl.toString()
        event.preventDefault()
    })
})