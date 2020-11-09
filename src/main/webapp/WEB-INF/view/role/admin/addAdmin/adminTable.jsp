<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style="min-width: 100px"><spring:message code="common.no"/></th>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.username"/></span>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.name"/></span>
        <th style=""><span class="text-primary"></span>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="adm" items="${adminList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td class="pl-0">
                ${adm.username}
            </td>
            <td>
                ${adm.contact.getFullName()}
            </td>

            <td>
                <a class="btn btn-light edit-admin" href="/admin/systemManagement/editAdmin?id=${adm.id}"><spring:message code="common.edit"/></a>
                <c:if test="${fn:length(adminList) > 1}">
                <button class="btn btn-light delete-admin" data-id="${adm.id}"><spring:message code="common.delete"/></button>
                </c:if>
            </td>
        </tr>
    </c:forEach>



    </tbody>
</table>

<script>
    $(document).ready(function() {
        $("#student-list").DataTable();

        $("body").on("click", ".delete-admin", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/systemManagement/deleteAdmin?id=" + id, function(result) {
                alert("<spring:message code="common.success"/>");
                location.reload();
            });
        });
    });



</script>