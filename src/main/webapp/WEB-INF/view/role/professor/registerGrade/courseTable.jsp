<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.divide"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.ltlp"/></span>


        <th style=""><span class="text-primary"><spring:message code="admin.maxStudent"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.opening"/></span>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="pc" items="${courseList}" varStatus="varStatus">
        <tr>
            <td class="pl-0">
                    ${varStatus.count}
            </td>

            <td>
                    ${pc.course.code}
            </td>
            <td>
                    ${pc.course.title}
            </td>
            <td>
                <spring:message code="subj.category.${pc.course.subjCategory}"/>
            </td>
            <td>
                    ${pc.divide}
            </td>
            <td>
                    ${pc.course.lec}-${pc.course.tut}-${pc.course.lab}-${pc.course.ws}
            </td>

            <td>
                    ${pc.limitStudent}
            </td>
            <td>
                    ${pc.course.enabled ? 'Y' : 'N'}
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<script>
    $("#course-list").DataTable();

    <c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/professor/classProgress/registerGrade/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/professor/classProgress/registerGrade/courseDetail?courseId=" + courseId);

    });
</script>