<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.year"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="subj.category.msc"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="subj.category.liberal"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="subj.category.major"/></span></th>
        <th style=""></th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="gc" items="${gcList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                ${gc.year}
            </td>
            <td>
                ${gc.division.name}
            </td>
            <td>
                <a href="#" class="course-editable" data-type="text" data-name="msc" data-url="${baseUrl}/admin/academicManagement/graduationCriteria/criteriaEditable" data-pk="${gc.id}" data-original-title="<spring:message code="subj.category.msc"/>">${gc.msc}</a>

            </td>
            <td>
                <a href="#" class="course-editable" data-type="text" data-name="liberal" data-url="${baseUrl}/admin/academicManagement/graduationCriteria/criteriaEditable" data-pk="${gc.id}" data-original-title="<spring:message code="subj.category.liberal"/>">${gc.liberal}</a>
            </td>
            <td>
                <a href="#" class="course-editable" data-type="text" data-name="major" data-url="${baseUrl}/admin/academicManagement/graduationCriteria/criteriaEditable" data-pk="${gc.id}" data-original-title="<spring:message code="subj.category.major"/>">${gc.major}</a>
            </td>
            <td>
                <button class="btn btn-light btm-sm delete-criteria" data-id="${gc.id}"><spring:message code="common.delete"/></button>
            </td>

        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#student-list").DataTable();

    $('.course-editable').editable();
    $("body").on("click", ".delete-criteria", function(e) {
        e.preventDefault();
        var id = $(this).attr("data-id");
        $.post("${baseUrl}/admin/courseManagement/course/deleteCriteria?id=" + id, function(result) {
            if(result == true) {
                alert("<spring:message code="common.success" javaScriptEscape="true" />");
                location.reload();
            } else {

                alert("<spring:message code="admin.graduationCriteriaNotDelete" javaScriptEscape="true" />");
                location.reload();
            }

        });
    });
</script>