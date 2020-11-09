<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span></th>
        <th style="width:15%"></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="courseElement" items="${courseList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                    ${courseElement.code}
            </td>
            <td>
                    ${courseElement.title}

            </td>

            <td>
                <spring:message code="subj.category.${courseElement.subjCategory}"/>
            </td>
            <td>
                <button type="button" class="btn btn-light btm-sm add-to-btn" data-id="${courseElement.id}" data-type="alternative" style="width:100%"><spring:message code="admin.addToAlternative"/></button><br/><br/>
                <button type="button" class="btn btn-light btm-sm add-to-btn" data-id="${courseElement.id}" data-type="prerequisite" style="width:100%"><spring:message code="admin.addToPrerequisite"/></button>
            </td>


        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();
    $('.course-editable').editable();
    <%--<c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/admin/courseManagement/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/admin/courseManagement/courseDetail?courseId=" + courseId);

    });--%>

    $("body").on("click", ".add-to-btn", function(e) {
        e.preventDefault();
        var id = $(this).attr("data-id");
        var type = $(this).attr("data-type");
        $.post("${baseUrl}/admin/courseManagement/alternative/addToAlt?id=" + id + "&targetCourseId=${targetCourseId}&type=" + type, function(result) {
            if(result == true) {
                location.reload();
            }

        });
    });
</script>