<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.credit"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.course.ltlp"/></span>
        <%--<th style=""><span class="text-primary"><spring:message code="professor.learningObjectives"/></span>
        <th style=""><span class="text-primary"><spring:message code="professor.courseOverview"/></span>--%>
        <th style=""><span class="text-primary"><spring:message code="common.registeredDate"/></span>
        <th class="pr-0" style="min-width: 160px"><span class="text-primary"></span></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="courseElement" items="${courseList}" varStatus="varStatus">
        <c:if test="${courseElement.enabled}">
            <tr class="">

                <td class="pl-0">
                        ${varStatus.count}
                </td>
                <td>
                        ${courseElement.code}
                </td>
                <td>
                    <a href="#" class="course-editable" data-type="text" data-name="title" data-url="${baseUrl}/admin/courseManagement/courseEditable" data-pk="${courseElement.id}" data-original-title="<spring:message code="common.courseTitle"/>">${courseElement.title}</a>

                </td>
                <td>
                    <spring:message code="subj.category.${courseElement.subjCategory}"/>
                </td>
                <td>
                        ${courseElement.credit}
                </td>
                <td>
${courseElement.lec}-${courseElement.tut}-${courseElement.lab}-${courseElement.ws}
                </td>
<%--
                <td>
                    <a href="#" class="course-editable" data-type="textarea" data-name="learningObjective" data-url="${baseUrl}/admin/courseManagement/courseEditable" data-pk="${courseElement.id}" data-original-title="<spring:message code="professor.learningObjectives"/>">${courseElement.learningObjective}</a>

                </td>
                <td>
                    <a href="#" class="course-editable" data-type="textarea" data-name="overview" data-url="${baseUrl}/admin/courseManagement/courseEditable" data-pk="${courseElement.id}" data-original-title="<spring:message code="professor.courseOverview"/>">${courseElement.overview}</a>

                </td>--%>
                <td>
                    <fmt:formatDate pattern="dd-MMM-yyyy" value="${courseElement.registeredDate}"/>
                </td>
                <td>
                    <a href="${baseUrl}/admin/courseManagement/course/editCourse?id=${courseElement.id}" class="btn btn-light btm-sm" data-id="${courseElement.id}"><spring:message code="common.edit"/></a>
                    <button class="btn btn-light btm-sm change-status-row-btn" data-id="${courseElement.id}" data-to-status="false"><spring:message code="common.disable"/></button>
                    <button class="btn btn-light btm-sm delete-row-btn" data-id="${courseElement.id}"><spring:message code="common.delete"/></button>
                </td>
            </tr>
        </c:if>
        <c:if test="${not courseElement.enabled}">
            <tr class="disabled-tr">

                <td class="pl-0">
                        ${varStatus.count}
                </td>
                <td>
                        ${courseElement.code}
                </td>
                <td>
                    <a href="#" class="course-editable" data-type="text" data-name="title" data-url="${baseUrl}/admin/courseManagement/courseEditable" data-pk="${courseElement.id}" data-original-title="<spring:message code="common.courseTitle"/>">${courseElement.title}</a>

                </td>
                <td>
                    <spring:message code="subj.category.${courseElement.subjCategory}"/>
                </td>
                <td>
                        ${courseElement.credit}
                </td>
                <td>
                        ${courseElement.lec}-${courseElement.tut}-${courseElement.lab}-${courseElement.ws}
                </td>

                <%--<td>
                        ${courseElement.learningObjective}
                </td>
                <td>
                        ${courseElement.overview}
                </td>--%>
                <td>
                    <fmt:formatDate pattern="dd-MMM-yyyy" value="${courseElement.registeredDate}"/>
                </td>
                <td>
                    <a href="${baseUrl}/admin/courseManagement/course/editCourse?id=${courseElement.id}" class="btn btn-light btm-sm" data-id="${courseElement.id}"><spring:message code="common.edit"/></a>
                    <button class="btn btn-light btm-sm change-status-row-btn" data-id="${courseElement.id}"  data-to-status="true"><spring:message code="common.enable"/></button>
                </td>
            </tr>
        </c:if>
    </c:forEach>


    </tbody>
</table>
<script>
    $(document).ready(function() {
        $("#course-list").DataTable();
        $('.course-editable').editable();
        $("body").on("click", ".delete-row-btn", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/courseManagement/course/deleteCourse?id=" + id, function(result) {
                location.reload();
            });
        });

        $("body").on("click", ".change-status-row-btn", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var status = $(this).attr("data-to-status");
            $.post("${baseUrl}/admin/courseManagement/course/changeStatus?id=" + id + "&status=" + status, function(result) {
                if(result == true) {
                    location.reload();
                }

            });
        });
    });
</script>