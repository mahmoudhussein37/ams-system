<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.semester"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.professor"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.divide"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="professor.attendanceList"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.download"/></span></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="pc" items="${profCourseList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                    ${pc.semester.year} - ${pc.semester.semester}
            </td>
            <td>
                    ${pc.course.code}
            </td>
            <td>
                    ${pc.course.title}

            </td>
            <td>
                    ${pc.professorUser.getFullName()}
            </td>
            <td>
                    ${pc.divide}
            </td>
            <td>
                <a href="${baseUrl}/professor/classProgress/attendance/studentListExcel?courseId=${pc.id}"><spring:message code="common.download"/></a>
            </td>
            <td>
                <c:if test="${not empty pc.attendanceFile}">
                    <a href="${baseUrl}/download?uploadedFileId=${pc.attendanceFile.id}"><spring:message code="common.download"/></a>
                </c:if>
            </td>


        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();

    <c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/professor/classProgress/attendance/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/professor/classProgress/attendance/courseDetail?courseId=" + courseId);

    });
</script>