<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style="min-width: 100px"><spring:message code="common.no"/></th>
        <th style="min-width: 120px"><span class="text-primary"><spring:message code="common.studentNumber"/></span></th>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.name"/></span>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.division"/></span>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.major"/></span>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="studentUser" items="${userList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                <a href="#" class="student-detail" data-student-id="${studentUser.id}">
                        ${studentUser.number}
                </a>
            </td>
            <td>
                    ${studentUser.getFullName()}
            </td>
            <td>
                    ${studentUser.division.name}
            </td>
            <td>
                    ${studentUser.major.name}
            </td>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#student-list").DataTable();

    <c:if test="${not empty firstUser}">
        $(".detail-div").load("${baseUrl}/professor/studentGuidance/coCourseEnrolment/studentDetail?studentId=${firstUser.id}");
    </c:if>
    $("body").on('click', '.student-detail', function (e) {
        e.preventDefault();
        var studentId = $(this).attr("data-student-id");
        $(".detail-div").load("${baseUrl}/professor/studentGuidance/coCourseEnrolment/studentDetail?studentId=" + studentId);

    });
</script>