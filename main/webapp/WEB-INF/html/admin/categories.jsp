<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section id="categories" class="admin-section">
  <h2>Управление категориями</h2>
  <a href="/admin/add-category">Добавить категорию</a>
  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Категория</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="category" items="${categories}">
      <tr>
        <td>${category.id}</td>
        <td>${category.name}</td>
        <td>
          <form method="post" action="/admin/delete-category">
            <input name="categoryId" value="${category.id}" hidden="hidden">
            <button type="submit">Удалить</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</section>