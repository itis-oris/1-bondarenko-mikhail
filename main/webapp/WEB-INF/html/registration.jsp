<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="_header.jsp" %>

<div class="registration_form">
    <% if(!SecurityService.isSigned(request)) {
        %>
    <form method="post" action="/registration">
        <ul class="reg_input">
            <li class="input_form">
                <input type="text" required placeholder="Имя" name="first_name">
            </li>
            <li class="input_form">
                <input type="text" required placeholder="Фамилия" name="second_name">
            </li>
            <li class="input_form">
                <input type="email" required placeholder="Email" name="email">
            </li>
            <li class="input_form">
                <input type="tel" required placeholder="Номер телефона" name="telephone">
            </li>
            <li class="input_form">
                <input type="password" required placeholder="Пароль" name="password">
            </li>
        </ul>
        <ul>
            <li>
                <button type="submit">Зарегистрироваться</button>
            </li>
            <li>
                <button type="reset">Сбросить</button>
            </li>

        </ul>

    </form>
    <h1 style="color: red">${message}</h1>
    <% } else {
        %>
    <h3>Вы успешно зарегстрированы!</h3>
    <% }
    %>
</div>
</body>
<%@include file="_footer.jsp" %>
