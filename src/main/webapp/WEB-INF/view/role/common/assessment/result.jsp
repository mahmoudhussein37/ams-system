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

                        <td>
                            <spring:message code="professor.assessment.totalResponse"/>
                        </td>
                        <td>
                            <spring:message code="common.avg"/>
                        </td>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="af" items="${assessmentFactors}" varStatus="varStatus">
                        <c:set var="totalScore" value="0"/>
                        <c:set var="numAm" value="0"/>
                        <tr class="table-light text-center">
                            <td>${varStatus.count}</td>
                            <td>
                                ${af.title}
                            </td>
                            <td>
                                ${af.question}
                            </td>
                            <td>
                                <c:set var="scoreCount" value="0"/>
                                <c:forEach var="am" items="${assessments}">

                                    <c:if test="${am.getScoreByItemId(af.id) eq 1}">
                                        <c:set var="scoreCount" value="${scoreCount + 1}"/>
                                    </c:if>
                                </c:forEach>
                                ${scoreCount}

                            </td>
                            <td>
                                <c:set var="scoreCount" value="0"/>
                                <c:forEach var="am" items="${assessments}">
                                    <c:if test="${am.getScoreByItemId(af.id) eq 2}">
                                        <c:set var="scoreCount" value="${scoreCount + 1}"/>
                                    </c:if>
                                </c:forEach>
                                    ${scoreCount}
                            </td>
                            <td>
                                <c:set var="scoreCount" value="0"/>
                                <c:forEach var="am" items="${assessments}">
                                    <c:if test="${am.getScoreByItemId(af.id) eq 3}">
                                        <c:set var="scoreCount" value="${scoreCount + 1}"/>
                                    </c:if>
                                </c:forEach>
                                    ${scoreCount}
                            </td>
                            <td>
                                <c:set var="scoreCount" value="0"/>
                                <c:forEach var="am" items="${assessments}">
                                    <c:if test="${am.getScoreByItemId(af.id) eq 4}">
                                        <c:set var="scoreCount" value="${scoreCount + 1}"/>
                                    </c:if>
                                </c:forEach>
                                    ${scoreCount}
                            </td>
                            <td>
                                <c:set var="scoreCount" value="0"/>
                                <c:forEach var="am" items="${assessments}">
                                        <%--${am.getScoreByItemId(af.id)}--%>
                                    <c:if test="${am.getScoreByItemId(af.id) eq 5}">
                                        <c:set var="scoreCount" value="${scoreCount + 1}"/>
                                    </c:if>
                                </c:forEach>
                                    ${scoreCount}
                            </td>
                            <td>
                                <c:set var="scoreCount" value="0"/>
                                <c:forEach var="am" items="${assessments}">
                                    <%--${am.getScoreByItemId(af.id)}--%>
                                    <c:if test="${am.getScoreByItemId(af.id) ne -1}">
                                        <c:set var="totalScore" value="${totalScore + am.getScoreByItemId(af.id)}"/>
                                        <c:set var="numAm" value="${numAm + 1}"/>
                                    </c:if>
                                </c:forEach>
                                    ${numAm}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${totalScore > 0 and numAm > 0}">
                                        ${totalScore / numAm}
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>


            </div>
        </div>
    </div>

</div>