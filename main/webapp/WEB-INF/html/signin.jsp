<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="_header.jsp"%>

<div class="signin_form">
    <form method="post" action="/signin">
        <ul>
            <li>
                <input type="email" required placeholder="Email" name="email">
            </li>
            <li>
                <input type="password" required placeholder="Пароль" name="password">
            </li>

        </ul>

        <button type="submit">Войти</button>
        <c:if test="${not empty message}">
            <p style="color: red">${message}</p>
        </c:if>

    </form>
    <div class="reg">
        <p>Еще не зарегистрированы?</p>
        <a href="<c:url value="/registration"/>">Зарегистрироваться</a>
    </div>

</div>
</body>
<%@include file="_footer.jsp"%>
