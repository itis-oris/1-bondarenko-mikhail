<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="cartBase.jsp" %>

<div class="orders-container">
    <c:forEach var="order" items="${orders}">
        <div class="order-item">
            <div class="order-header">
                <p class="order-number">Номер заказа: ${order.order_id}</p>
                <p class="order-date">Дата заказа:
                    <fmt:formatDate value="${order.created_at}" pattern="dd-MM-yyyy HH:mm"/>
                </p>
                <p class="order-status"
                   <c:if test="${order.status eq 'Отменен'}">style="color: red"</c:if>>Статус заказа: ${order.status}</p>
            </div>

            <div class="order-products">
                <c:forEach var="product" items="${order.products}">
                    <div class="order-product">
                        <img src="${product.picture_path}" alt="Нет изображения" class="order-product-image">
                        <p class="order-product-name">${product.name}</p>
                    </div>
                </c:forEach>
            </div>

            <div class="order-total">
                <p class="order-total-price">Стоимость: ${order.total_price} Р</p>
            </div>
            <c:if test="${order.status ne 'Отменен'}">
                <form method="post">
                    <input hidden="hidden" name="orderId" value="${order.order_id}">
                    <button type="submit">Отменить заказ</button>
                </form>
            </c:if>
        </div>
    </c:forEach>
</div>
</div>
</main>
</body>

<%@ include file="_footer.jsp" %>
