<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/html/_header.jsp" %>
<main>
  <div class="admin-container">
    <nav class="admin-nav">
      <div class="nav-logo">Admin Panel</div>
      <ul class="nav-links">
        <li>
          <form method="GET" action="/admin">
            <input type="hidden" name="section" value="users">
            <button type="submit">Пользователи</button>
          </form>
        </li>
        <li>
          <form method="GET" action="/admin">
            <input type="hidden" name="section" value="products">
            <button type="submit">Товары</button>
          </form>
        </li>
        <li>
          <form method="GET" action="/admin">
            <input type="hidden" name="section" value="orders">
            <button type="submit">Заказы</button>
          </form>
        </li>
        <li>
          <form method="GET" action="/admin">
            <input type="hidden" name="section" value="categories">
            <button type="submit">Категории</button>
          </form>
        </li>
      </ul>
    </nav>
