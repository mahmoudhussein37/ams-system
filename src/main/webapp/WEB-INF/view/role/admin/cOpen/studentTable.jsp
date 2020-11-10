<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.studentNumber"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.name"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span></th>
        <th style=""></th>
        <%--<th style="min-width: 150px"><span class="text-primary"><spring:message code="common.major"/></span>--%>

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
                <button class="btn btn-light btm-sm add-to-btn" data-id="${studentUser.id}" data-to-status="true"><spring:message code="admin.addToDivide"/></button>
            </td>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $(document).ready(function() {
        $("#student-list").DataTable();
        $(".add-to-btn").click(function(e) {
           e.preventDefault();
           var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/courseManagement/cOpen/manageStudent/addToDivide?id=" + id, function(result) {

                location.reload();
            });

        });

    });




</script>