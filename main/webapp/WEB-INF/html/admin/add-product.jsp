<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="baseHeader.jsp"%>
<style>
    form.product-add {
        display: flex;
        flex-direction: column;
        gap: 15px;
        margin: 20px auto;

    }
    input, select, button.add {
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 325px;
    }
    button.add {
        background-color: #4CAF50;
        color: white;
        border: none;
        cursor: pointer;
    }
    button.add:hover {
        background-color: #45a049;
    }
</style>
<h2 style="text-align: center;">Добавить товар</h2>
<form method="post" enctype="multipart/form-data" class="product-add">
    <label>
        <input type="text" name="name" required placeholder="Название товара">
    </label>

    <label>
        <select name="categoryId" required>
            <option selected disabled>Выберите категорию</option>
            <c:forEach var="ct" items="${categories}">
                <option value="${ct.id}">${ct.name}</option>
            </c:forEach>
        </select>
    </label>

    <label>
        <input type="number" name="price" min="0" step="0.01" required placeholder="Цена">
    </label>

    <label style="width: 100px">
        <input type="file" name="picture" accept="image/*" required>
    </label>

    <button type="submit" class="add">Добавить товар</button>
</form>