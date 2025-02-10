<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Управление пользователями</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Email</th>
        <th>Телефон</th>
        <th>Роль</th>
        <th></th>
    </tr>
    </thead>
    <tbody >
    <c:forEach var="user" items="${users}">
        <tr ${user.role == 'admin' ? 'class="admin"' : ''}>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.secondName}</td>
            <td>${user.email}</td>
            <td>${user.telephone}</td>
            <td>
                <form method="POST" action="/admin/update-user-role">
                <input type="hidden" name="userId" value="${user.id}" />
                <select name="role">
                    <option value="user" ${user.role == 'user' ? 'selected' : ''}>Пользователь</option>
                    <option value="admin" ${user.role == 'admin' ? 'selected' : ''}>Админ</option>
                </select>
                <button type="submit">Изменить</button>
                </form>
            </td>
            <td>
                <form method="post" action="/admin/delete-user">
                    <input name="userId" value="${user.id}" hidden="hidden">
                    <button type="submit">Удалить</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
