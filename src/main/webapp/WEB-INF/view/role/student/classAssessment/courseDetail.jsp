<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<form:form class="form" modelAttribute="assessment" id="assessmentForm" action="${baseUrl}/student/classInformation/classAssessment/courseDetail?studentCourseId=${sc.id}" method="post">
    <form:hidden path="id"/>
<div class="card-body">

    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <table class="table rounded">
                    <thead>
                    <tr class="table-secondary text-center">

                        <td><spring:message code="common.no"/></td>
                        <td>
                            <spring:message code="admin.analyticalStandard"/>
                        </td>
                        <td>
                            <spring:message code="admin.question"/>
                        </td>
                        <c:forEach var="s" begin="1" end="5">
                            <td>
                                <spring:message code="professor.assessment.score${s}"/>
                            </td>
                        </c:forEach>


                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${menuAccess.assessment and assessment.id eq 0}">
                            <c:forEach var="af" items="${assessmentFactors}" varStatus="varStatus">
                                <input type="hidden" name="item${varStatus.count}" value="${af.id}"/>
                                <tr class="table-light text-center">
                                    <td>${varStatus.count}</td>
                                    <td>
                                            ${af.title}
                                    </td>
                                    <td>
                                            ${af.question}
                                    </td>
                                    <c:forEach var="s" begin="1" end="5">
                                        <td>
                                            <input type="radio" name="score${varStatus.count}" value="${s}" ${assessment.getItem(varStatus.count) eq af.id and assessment.getScore(varStatus.count) == s ? 'checked' : ''}/>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="af" items="${assessmentFactorsAll}" varStatus="varStatus">
                                <input type="hidden" name="item${varStatus.count}" value="${af.id}"/>
                                <tr class="table-light text-center">
                                    <td>${varStatus.count}</td>
                                    <td>
                                            ${af.title}
                                    </td>
                                    <td>
                                            ${af.question}
                                    </td>
                                    <c:forEach var="s" begin="1" end="5">
                                        <td>
                                            <input type="radio" name="score${varStatus.count}" value="${s}" ${assessment.getItem(varStatus.count) eq af.id and assessment.getScore(varStatus.count) == s ? 'checked' : ''} disabled/>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>





                    </tbody>
                </table>


            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">


                <label><spring:message code="common.comment"/></label>
                <form:textarea rows="5" path="comment" class="form-control" style="" disabled="${menuAccess.assessment and assessment.id eq 0 ? 'false' : 'true'}"></form:textarea>
            </div>
        </div>
    </div>
</div>
    <c:if test="${menuAccess.assessment and assessment.id eq 0}">
<div class="card-footer">
    <button type="submit" id="assessment-save" class="btn btn-primary mr-2"><spring:message code="common.submit"/></button>
</div>
    </c:if>

</form:form>
<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {

});
</script>