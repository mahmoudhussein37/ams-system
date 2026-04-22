<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.professor"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.divide"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.opening"/></span>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="sc" items="${courseList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td class="pl-0">
                    ${sc.course.division.name}
            </td>
            <td>
                <a href="#" class="course-detail" data-student-course-id="${sc.id}">
                    ${sc.course.code}
                </a>

            </td>
            <td>
                    ${sc.course.title}
            </td>
            <td>
                <spring:message code="subj.category.${sc.course.subjCategory}"/>
            </td>
            <td>
                    ${sc.professorCourse.professorUser.getFullName()}
            </td>
            <td>
                    ${sc.professorCourse.divide}
            </td>

            <td>
                    ${sc.course.enabled ? 'Y' : 'N'}
            </td>

        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();

    <c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/student/classInformation/classAssessment/courseDetail?studentCourseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var studentCourseId = $(this).attr("data-student-course-id");
        $(".detail-div").load("${baseUrl}/student/classInformation/classAssessment/courseDetail?studentCourseId=" + studentCourseId);

    });
</script>