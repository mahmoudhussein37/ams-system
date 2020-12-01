<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.semester"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.divide"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.lectureCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.ltlp"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.numStudent"/></span>
        <%--<th style=""><span class="text-primary"><spring:message code="professor.course.numResponse"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.avg"/></span>--%>


    </tr>
    </thead>
    <tbody>
    <c:forEach var="pc" items="${courseList}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td class="pl-0">
                    ${pc.semester.year} - ${pc.semester.semester}
            </td>
            <td class="pl-0">
                    ${pc.course.division.name}
            </td>
            <td>
                <a href="#" class="course-detail" data-course-id="${pc.id}">
                        ${pc.course.code}
                </a>


            </td>
            <td>
                    ${pc.course.title}
            </td>
            <td>
                ${pc.divide}
            </td>
            <td>
                <spring:message code="subj.category.${pc.course.subjCategory}"/>
            </td>
            <td>
                    ${pc.course.lec}-${pc.course.tut}-${pc.course.lab}-${pc.course.ws}
            </td>
            <td>
                ${pc.numStudent}
            </td>
            <%--<td>

            </td>--%>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();

    <c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/professor/classProgress/classAssessment/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/professor/classProgress/classAssessment/courseDetail?courseId=" + courseId);

    });
</script>