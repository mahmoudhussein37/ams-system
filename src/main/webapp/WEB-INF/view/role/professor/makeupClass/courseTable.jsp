<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style="min-width: 100px"><spring:message code="common.no"/></th>
        <th style="min-width: 120px"><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.category"/></span>
        <th style="min-width: 150px"><span class="text-primary"><spring:message code="common.dateTime"/></span>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="course" items="${courseList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
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
                    ${course.category}
            </td>
            <td>

            </td>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();

    <c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/professor/classProgress/makeupClass/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/professor/classProgress/makeupClass/courseDetail?courseId=" + courseId);

    });
</script>