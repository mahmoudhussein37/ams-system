<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style="min-width: 100px"><spring:message code="common.no"/></th>
        <th style="min-width: 120px"><span class="text-primary"><spring:message code="common.profNumber"/></span></th>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.name"/></span>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.department"/></span>
        <%--<th style="min-width: 150px"><span class="text-primary"><spring:message code="common.major"/></span>--%>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="profUser" items="${userList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                <a href="#" class="prof-detail" data-prof-id="${profUser.id}">
                        ${profUser.number}
                </a>
            </td>
            <td>
                    ${profUser.getFullName()}
            </td>
            <td>
                    ${profUser.division.name}
            </td>
            <%--<td>
                    ${profUser.major.name}
            </td>--%>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#student-list").DataTable();

    <c:if test="${not empty firstUser}">
        $(".detail-div").load("${baseUrl}/admin/profManagement/profInformation/profDetail?profId=${firstUser.id}");
    </c:if>
    $("body").on('click', '.prof-detail', function (e) {
        e.preventDefault();
        var profId = $(this).attr("data-prof-id");
        $(".detail-div").load("${baseUrl}/admin/profManagement/profInformation/profDetail?profId=" + profId);

    });
</script>