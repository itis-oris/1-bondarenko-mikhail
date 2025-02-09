<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Управление товарами</h2>
<a href="/admin/add-product">Добавить товар</a>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Цена</th>
        <th>Категория</th>
        <th>Фото</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price} Р</td>
            <td>${product.categoryName}</td>
            <td>
                <img src="${product.picture_path}" style="width: 80px;object-fit: contain;height: 80px">
            </td>
            <td>
                <form method="post" action="/admin/delete-product">
                    <input name="productId" hidden="hidden" value="${product.id}">
                    <button type="submit">Удалить</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
