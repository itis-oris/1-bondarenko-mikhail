<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Управление заказами</h2>
<table>
  <thead>
  <tr>
    <th>ID заказа</th>
    <th>ID пользователя</th>
    <th>Статус</th>
    <th>Стоимость заказа</th>
    <th>Товары</th>
    <th>Дата заказа</th>
    <th></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="order" items="${orders}">
    <tr>
      <td>${order.order_id}</td>
      <td>${order.user_id}</td>
      <td>
        <form method="POST" action="/admin/update-order-status">
          <input type="hidden" name="order_id" value="${order.order_id}" />
          <select name="status">
            <option value="Оформлен" ${order.status == 'Оформлен' ? 'selected' : ''}>Оформлен</option>
            <option value="Доставляется" ${order.status == 'Доставляется' ? 'selected' : ''}>Доставляется</option>
            <option value="Доставлен" ${order.status == 'Доставлен' ? 'selected' : ''}>Доставлен</option>
            <option value="Отменен" ${order.status == 'Отменен' ? 'selected' : ''}>Отменен</option>
          </select>
          <button type="submit">Изменить</button>
        </form>
      </td>
      <td>${order.total_price} Р</td>
      <td>
        <div class="order-products-admin">
          <c:forEach var="product" items="${order.products}">
            <div class="order-product-admin">
              <img src="${product.picture_path}" alt="Нет изображения" class="order-product-image-admin">
              <p class="order-product-name-admin">${product.name}</p>
            </div>
          </c:forEach>
        </div>

        <div class="order-total-admin">
          <p class="order-total-price-admin">Стоимость: ${order.total_price} Р</p>
        </div>
        </div>


      </td>
      <td><fmt:formatDate value="${order.created_at}" pattern="dd-MM-yyyy HH:mm" /></td>
      <td>
        <form method="POST" action="/admin/delete-order">
          <input type="hidden" name="orderId" value="${order.order_id}" />
          <button type="submit" class="delete-button">Удалить</button>
        </form>
      </td>

    </tr>

  </c:forEach>
  </tbody>
</table>
