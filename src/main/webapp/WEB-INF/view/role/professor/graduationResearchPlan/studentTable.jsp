<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style="min-width: 100px"><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.year"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.studentNumber"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.studentsName"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.status"/></span>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="plan" items="${plans}" varStatus="varStatus">
        <tr>

            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                    ${plan.year}
            </td>
            <td>
                <a href="#" class="plan-detail" data-plan-id="${plan.id}">
                        ${plan.user.number}
                </a>
            </td>
            <td>
                    ${plan.user.getFullName()}
            </td>
            <td>
                    ${plan.user.division.name}
            </td>
            <td>
                <c:choose>
                    <c:when test="${plan.approve eq -1}">
                                <span style="color:red">
                                <spring:message code="professor.graduateResearchPlan.status.returned"/>
                                    </span>
                    </c:when>
                    <c:when test="${plan.approve eq 0}">
                        <spring:message code="professor.graduateResearchPlan.status.submitted"/>
                    </c:when>
                    <c:when test="${plan.approve eq 1}">
                            <span style="color:green">
                                <spring:message code="professor.graduateResearchPlan.status.approved"/>
                            </span>
                    </c:when>
                </c:choose>
            </td>

        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    $("#student-list").DataTable();

    <c:if test="${not empty firstOne}">
        $(".detail-div").load("${baseUrl}/professor/classProgress/graduationResearchPlan/planDetail?planId=${firstOne.id}");
    </c:if>
    $("body").on('click', '.plan-detail', function (e) {
        e.preventDefault();
        var planId = $(this).attr("data-plan-id");
        $(".detail-div").load("${baseUrl}/professor/classProgress/graduationResearchPlan/planDetail?planId=" + planId);

    });
</script>