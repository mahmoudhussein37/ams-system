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
        <tr class="">

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                    ${courseElement.code}
            </td>
            <td>
                ${courseElement.title}

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
                <a href="${baseUrl}/admin/courseManagement/cOpen/manageDivide?courseId=${courseElement.id}" class="btn btn-light btm-sm" data-id="${courseElement.id}"><spring:message code="common.manage"/></a>

            </td>
        </tr>

    </c:forEach>


    </tbody>
</table>
<script>
    $("#course-list").DataTable();
</script>