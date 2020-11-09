<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.year"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.semester"/></span></th>
        <%--<th style=""><span class="text-primary"></span>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="semester" items="${semesterList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td class="pl-0">
                <a href="#" class="course-editable" data-type="number" data-name="year" data-url="${baseUrl}/admin/systemManagement/yearSemester/semesterEditable" data-pk="${semester.id}" data-original-title="<spring:message code="common.year"/>">${semester.year}</a>
            </td>
            <td>
                <a href="#" class="course-editable" data-type="number" data-name="semester" data-url="${baseUrl}/admin/systemManagement/yearSemester/semesterEditable" data-pk="${semester.id}" data-original-title="<spring:message code="common.semester"/>">${semester.semester}</a>
            </td>

            <%--<td>
                <button class="btn btn-light btm-sm delete-semester" data-id="${semester.id}"><spring:message code="common.delete"/></button>
            </td>--%>
        </tr>
    </c:forEach>



    </tbody>
</table>

<script>
    $(document).ready(function() {
        $("#student-list").DataTable();
        $('.course-editable').editable();
        $("body").on("click", ".delete-semester", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/systemManagement/yearSemester/deleteSemester?id=" + id, function(result) {
                alert("<spring:message code="common.success"/>");
                location.reload();
            });
        });
    });



</script>