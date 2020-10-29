<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.compCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span>

        <th style=""><span class="text-primary"><spring:message code="admin.maxStudent"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.opening"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.syllabus"/></span>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="course" items="${courseList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td class="pl-0">
                    ${course.division.name}
            </td>
            <td>

                    ${course.code}

            </td>
            <td>
                    ${course.title}
            </td>
            <td>
                <spring:message code="comp.category.${course.compCategory}"/>
            </td>
            <td>
                <spring:message code="subj.category.${course.subjCategory}"/>
            </td>

            <td>
                    ${course.maxStudent}
            </td>
            <td>
                    ${course.enabled ? 'Y' : 'N'}
            </td>
            <td>
                <a href="#" class="course-detail" data-course-id="${course.id}">
                    <i class="far fa-file-alt"></i>
                </a>
            </td>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();

    <c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/admin/courseManagement/syllabus/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/admin/courseManagement/syllabus/courseDetail?courseId=" + courseId);

    });
</script>