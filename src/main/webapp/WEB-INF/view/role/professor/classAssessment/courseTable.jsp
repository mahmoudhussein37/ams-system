<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.divide"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.lectureCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.ctlp"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.numStudent"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.numResponse"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.avg"/></span>


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
                <a href="#" class="course-detail" data-course-id="${course.id}">
                        ${course.code}
                </a>


            </td>
            <td>
                    ${course.title}
            </td>
            <td>
                1
            </td>
            <td>

            </td>

            <td>

            </td>
            <td>

            </td>
            <td>

            </td>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    /*$("#course-list").DataTable();*/

    <c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/professor/classProgress/classAssessment/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/professor/classProgress/classAssessment/courseDetail?courseId=" + courseId);

    });
</script>