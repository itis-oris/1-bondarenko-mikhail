<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="cartBase.jsp" %>

<div class="cart-container">
    <form class="cart-form" method="post">
        <c:forEach var="product" items="${products}">
            <div class="cart-item">
                <input type="checkbox" name="selectedCarts" value="${product.cartId}">
                <img src="${product.picture_path}" alt="Нет изображения" class="cart-item-image">
                <div class="cart-item-info">
                    <p class="cart-item-name">${product.name}</p>
                    <p class="cart-item-price">${product.price} Р</p>
                </div>
                    <button type="submit" name="action" value="delete:${product.cartId}">Удалить из корзины</button>
            </div>
        </c:forEach>
        <p style="color: red">${message}</p>
        <div class="cart-actions">
            <button type="submit" class="cart-button" name="action" value="checkout">Оформить заказ</button>
        </div>
    </form>
</div>
</div>
</main>
</body>

<%@ include file="_footer.jsp" %>
