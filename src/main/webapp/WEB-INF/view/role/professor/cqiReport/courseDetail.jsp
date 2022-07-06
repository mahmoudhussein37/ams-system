<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold print">
        <spring:message code="common.print"/>
    </a>
</div>
<%@include file="/WEB-INF/view/role/common/cqi/chartDiv.jsp" %>


<form:form class="form" modelAttribute="cqi" id="cqiForm" action="${baseUrl}/professor/classProgress/cqiReport/courseDetail?profCourseId=${pc.id}" method="post">
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.courseLearningObjectives"/></h3>
<div class="row">
    <div class="col-md-12">
        <table class="table table-bordered">
            <tr>
                <td rowspan="2" style="text-align:center">
                    <spring:message code="common.no"/>
                </td>
                <td rowspan="2" style="text-align:center">
                    <spring:message code="professor.courseLearningObjectives"/>
                </td>

                <td colspan="3" style="text-align:center">
                    <spring:message code="professor.achievementScore"/> (1 ~ 5)<br/>

                </td>

            </tr>
            <tr style="text-align:center">
                <c:forEach var="entry" items="${cqiMap}">
                    <td>
                        ${entry.key}
                    </td>
                </c:forEach>
                <td>
                    ${currentYear}
                </td>

            </tr>
            <c:forEach var="d" begin="1" end="6">
                <tr style="text-align:center">
                    <td>
                        ${d}
                    </td>
                    <td>
                            ${lectureFundamentals.getClo(d)}
                    </td>
                    <c:forEach var="entry" items="${cqiMap}">
                        <td>
                                ${entry.value.getScore(d)}
                        </td>
                    </c:forEach>

                    <td style="background-color:#37a94b">
                        <input class="form-control" type="number" min="1" max="5" name="score${d}" value="${cqi.getScore(d)}" ${isEditable ? '' : 'disabled'}/>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>
</div>
<br/><br/>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.lectureAnalysis"/></h3>
<div class="row">
    <div class="col-md-12">
        <table class="table table-bordered">
            <tr>
                <td style="width:50%">
                    <spring:message code="professor.previousImprovePlan"/>
                </td>
                <td style="width:50%">
                    <spring:message code="professor.improvementResult"/>
                </td>
            </tr>
            <tr>
                <td>
<textarea rows="6" dir="${isRTL ? 'rtl' : 'ltr'}" class="form-control" disabled>
${prevCqi.plan}

</textarea>
                </td>
                <td>
<form:textarea path="problem" rows="6"  dir="${isRTL ? 'rtl' : 'ltr'}" class="form-control" disabled="${isEditable ? false : true}"/>

                </td>
            </tr>

        </table>
    </div>
</div>
<br/><br/>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.comprehensiveLecture"/></h3>
<div class="row">
    <div class="col-md-12">
<form:textarea path="plan" rows="6" dir="${isRTL ? 'rtl' : 'ltr'}"  class="form-control" disabled="${isEditable ? false : true}"/>
    </div>
</div>

<c:if test="${isEditable}">
    <div class="card-footer">
        <button type="button" id="cqi-save-btn" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
            <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
    </div>
</c:if>
</form:form>
<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<%@include file="/WEB-INF/view/role/common/cqi/chartScript.jsp" %>
<script>
    $(document).ready(function() {
        $("#cqi-save-btn").click(function(e) {
            e.preventDefault();
            var i, valid = true;
            for(i=1; i<=6; i++) {
                var score = $("input[name=score" + i + "]").val();
                if(Number(score) > 5) {
                    valid = false;
                }
            }
            if(valid) {
                $.post('${baseUrl}/professor/classProgress/cqiReport/courseDetail?profCourseId=${pc.id}', $('#cqiForm').serialize(), function() {
                    alert("<spring:message code="common.success"/>");
                });
            } else {
                alert("<spring:message code="professor.checkScores"/>");
            }

        });
        $("body").on('click', '.print', function (e) {
            e.preventDefault();
            location.href="${baseUrl}/professor/classProgress/cqiReport/courseDetail?print=true&profCourseId=${pc.id}";
        });
    });
</script>