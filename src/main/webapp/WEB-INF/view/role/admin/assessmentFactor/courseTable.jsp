<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.ltlp"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.registeredDate"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.closedDate"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.alternative"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.prerequisite"/></span>
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
                    ${courseElement.title}

            </td>
            <%--<td>
                <spring:message code="comp.category.${courseElement.compCategory}"/>
            </td>--%>
            <td>
                <spring:message code="subj.category.${courseElement.subjCategory}"/>
            </td>
            <td>

            </td>
            <td>
                    ${courseElement.division.name}
            </td>
            <td>
                YYYY-MM-dd
            </td>
            <td>
                YYYY-MM-dd
            </td>
            <td>
                Y
            </td>
            <td>
                Y
            </td>

        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();
    /*$('.course-editable').editable();*/
    <c:if test="${not empty firstCourse}">
    $(".detail-div").load("${baseUrl}/admin/academicManagement/assessmentFactor/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/admin/academicManagement/assessmentFactor/courseDetail?courseId=" + courseId);

    });
</script>