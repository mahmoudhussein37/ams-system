<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0"><spring:message code="common.no"/></th>
        <th><span class="text-primary"><spring:message code="common.year"/></span></th>
        <th><span class="text-primary"><spring:message code="common.department"/></span></th>
        <th></th>
        <th></th>

    </tr>
    </thead>
    <tbody>
    <tr>

        <td class="pl-0">
            1
        </td>
        <td>
            2020
        </td>
        <td>
            Electronics & Communication
        </td>
        <td>

            <a href="#"><spring:message code="common.download"/></a>
        </td>
        <td>
            <button type="button" class="btn btn-primary mr-2"><spring:message code="common.upload"/></button>
            <button type="button" class="btn btn-primary mr-2"><spring:message code="common.delete"/></button>
        </td>


    </tr>
    <%--<c:forEach var="course" items="${courseList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                <a href="#" class="course-detail" data-course-id="${course.id}">
                        ${course.code}
                </a>
            </td>
            <td>
                    ${course.title}
            </td>
            <td>
                    ${course.category}
            </td>

        </tr>
    </c:forEach>--%>


    </tbody>
</table>
<script>
    //$("#course-list").DataTable();


</script>