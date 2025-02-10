<%@include file="baseHeader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <div class="admin-content">
        <c:choose>
            <c:when test="${param.section == 'users'}">
                <jsp:include page="/WEB-INF/html/admin/users.jsp" />
            </c:when>
            <c:when test="${param.section == 'products'}">
                <jsp:include page="/WEB-INF/html/admin/products.jsp" />
            </c:when>
            <c:when test="${param.section == 'orders'}">
                <jsp:include page="/WEB-INF/html/admin/orders.jsp" />
            </c:when>
            <c:when test="${param.section == 'categories'}">
                <jsp:include page="/WEB-INF/html/admin/categories.jsp" />
            </c:when>
            <c:otherwise>
                <p>Выберите секцию из меню.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</main>

<%@ include file="/WEB-INF/html/_footer.jsp" %>
</body>