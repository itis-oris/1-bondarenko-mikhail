<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_header.jsp" %>
<body>

<main>
    <div class="container">
        <!-- Боковая панель категорий -->
        <aside class="sidebar">
            <h3>CATALOG</h3>
            <ul>
                <li>
                    <h5>
                        <a href="?category=">Все категории</a>
                    </h5>

                </li>
                <c:forEach var="category" items="${categories}">
                    <li>
                        <h5>
                            <a href="?category=${category.name}">${category.name}</a>
                        </h5>

                    </li>
                </c:forEach>
            </ul>
        </aside>

        <!-- Основной контент с поиском и товарами -->
        <div class="catalog">
            <!-- Поиск -->
            <div class="search-sort-container">
                <!-- Поиск -->
                <div class="search">
                    <form method="get" action="" id="searchForm">
                        <input class="search-stroke" type="text" name="name" placeholder="Введите запрос">
                        <button class="search-button" type="submit">Поиск</button>
                    </form>
                </div>

                <!-- Сортировка -->
                <div class="sort" id="sortBy">
                    <label for="sortSelect">Сортировка:</label>
                    <select id="sortSelect" class="sort-select">
                        <option value="price_asc" selected>Цена: по возрастанию</option>
                        <option value="price_desc">Цена: по убыванию</option>
                        <option value="name_asc">Название: от А до Я</option>
                        <option value="name_desc">Название: от Я до А</option>
                    </select>
                    <script src="<c:url value='/static/js/sortBy.js' />"></script>
                </div>
            </div>


            <!-- Список товаров -->
            <div class="products">
                <c:forEach var="product" items="${products}">
                    <div class="product-cart">
                        <img src="${product.picture_path}" alt="Изображения нет">

                            <p>${product.name}</p>


                            <p>${product.price} Р</p>


                        <form method="post" action="/add-to-cart">
                            <input type="hidden" name="productId" value="${product.id}">
                            <button type="submit">Добавить в корзину</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>

<%@ include file="_footer.jsp" %>

</body>
