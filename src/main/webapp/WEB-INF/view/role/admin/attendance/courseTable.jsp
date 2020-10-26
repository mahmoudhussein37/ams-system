<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.compCategory"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span></th>
        <th style=""></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="courseElement" items="${courseList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                <a href="#" class="course-detail" data-course-id="${courseElement.id}">
                        ${courseElement.code}
                </a>
            </td>
            <td>
                <a href="#" class="course-editable" data-type="text" data-name="title" data-url="${baseUrl}/admin/courseManagement/courseEditable" data-pk="${courseElement.id}" data-original-title="<spring:message code="common.courseTitle"/>">${courseElement.title}</a>

            </td>

            <td>
                <spring:message code="comp.category.${courseElement.compCategory}"/>
            </td>
            <td>
                <spring:message code="subj.category.${courseElement.subjCategory}"/>
            </td>
            <td>
                <a href="#"><spring:message code="common.download"/></a>
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
</script>