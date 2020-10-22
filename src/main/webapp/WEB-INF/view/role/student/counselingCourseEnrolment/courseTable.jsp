<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="course-list" style="width:100% !important;">
    <thead>
    <tr class="text-uppercase">

        <th style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.divide2"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.compCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.ltlp"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.professor"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.limitStudent"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.numStudent"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.lectureTime"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.classRoom"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.division"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.syllabus"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.apply"/></span>
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
            <td>
            </td>
            <td>
                <spring:message code="comp.category.${courseElement.compCategory}"/>
            </td>
            <td>
                <spring:message code="subj.category.${courseElement.subjCategory}"/>
            </td>
            <td>

            </td>
            <td>

            </td>
            <td>
15
            </td>
            <td>
15
            </td>
            <td>

            </td>
            <td>
B308
            </td>

            <td>
                ${courseElement.division.name}
            </td>
            <td>
                    View
            </td>
            <td>
                <button type="button" class="btn btn-primary mr-2"><spring:message code="common.apply"/></button>
            </td>

        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();
    /*$('.course-editable').editable();*/
    <c:if test="${not empty firstCourse}">
        $(".detail-div").load("${baseUrl}/student/courseGuide/courseInfo/courseDetail?courseId=${firstCourse.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-course-id");
        $(".detail-div").load("${baseUrl}/student/courseGuide/courseInfo/courseDetail?courseId=" + courseId);

    });
</script>