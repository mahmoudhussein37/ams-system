<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.name"/></span></th>
        <th style=""></th>
        <%--<th style=""><span class="text-primary"></span>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="division" items="${divisionList}" varStatus="varStatus">
        <c:if test="${division.enabled}">
            <tr class="text-center">

                <td class="pl-0">
                        ${varStatus.count}
                </td>
                <td class="pl-0">
                    <a href="#" class="course-editable" data-type="text" data-name="name" data-url="${baseUrl}/admin/systemManagement/divisionMajor/divisionEditable" data-pk="${division.id}" data-original-title="<spring:message code="common.name"/>">${division.name}</a>
                </td>


                <td>
                    <button class="btn btn-light btm-sm delete-division" data-id="${division.id}"><spring:message code="common.delete"/></button>
                </td>
            </tr>
        </c:if>

    </c:forEach>
    <c:forEach var="division" items="${divisionList}" varStatus="varStatus">
        <c:if test="${not division.enabled}">
            <tr class="text-center disabled-tr">

                <td class="pl-0">
                        ${varStatus.count}
                </td>
                <td class="pl-0">
                    <a href="#" class="course-editable" data-type="text" data-name="name" data-url="${baseUrl}/admin/systemManagement/divisionMajor/divisionEditable" data-pk="${division.id}" data-original-title="<spring:message code="common.name"/>">${division.name}</a>
                </td>


                <td>
                    <button class="btn btn-light btm-sm enable-division" data-id="${division.id}"><spring:message code="common.enable"/></button>
                </td>
            </tr>
        </c:if>

    </c:forEach>


    </tbody>
</table>

<script>
    $(document).ready(function() {
        $("#student-list").DataTable();
        $('.course-editable').editable();
        $("body").on("click", ".delete-division", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/systemManagement/divisionMajor/deleteDivision?id=" + id, function(result) {
                if(result == true) {
                    alert("<spring:message code="common.success"/>");
                    location.reload();
                } else {

                    alert("<spring:message code="admin.divisionDisabled"/>");
                    location.reload();
                }

            });
        });
        $("body").on("click", ".enable-division", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/systemManagement/divisionMajor/enableDivision?id=" + id, function(result) {
                if(result == true) {
                    alert("<spring:message code="common.success"/>");
                    location.reload();
                }

            });
        });
    });

</script>