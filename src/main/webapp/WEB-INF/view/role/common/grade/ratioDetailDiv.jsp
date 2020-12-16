<div class="row">
    <div class="col-md-4">
        <table class="table rounded">
            <thead>
            <tr class="table-secondary text-center">

                <td><spring:message code="common.grade"/></td>
                <td>
                    <spring:message code="professor.course.numStudent"/>
                </td>
                <td>
                    <spring:message code="common.ratio"/>
                </td>
            </tr>
            </thead>
            <tbody>
            <c:set var="count" value="0"/>
            <c:set var="ratio" value="0.0"/>
            <tr class="text-center">
                <td>A</td>
                <td>

                    <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                        <c:if test="${sc.grade eq 'Ap' or sc.grade eq 'A0'}">
                            <c:set var="count" value="${count + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${count}
                </td>
                <td>
                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${count * 100 / fn:length(studentCourses)}" />%
                </td>
            </tr>
            <c:set var="count" value="0"/>
            <c:set var="ratio" value="0.0"/>
            <tr class="text-center">
                <td>B</td>
                <td>

                    <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                        <c:if test="${sc.grade eq 'Bp' or sc.grade eq 'B0'}">
                            <c:set var="count" value="${count + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${count}
                </td>
                <td>
                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${count * 100 / fn:length(studentCourses)}" />%
                </td>
            </tr>
            <c:set var="count" value="0"/>
            <c:set var="ratio" value="0.0"/>
            <tr class="text-center">
                <td>C</td>
                <td>

                    <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                        <c:if test="${sc.grade eq 'Cp' or sc.grade eq 'C0'}">
                            <c:set var="count" value="${count + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${count}
                </td>
                <td>
                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${count * 100 / fn:length(studentCourses)}" />%
                </td>
            </tr>
            <c:set var="count" value="0"/>
            <c:set var="ratio" value="0.0"/>
            <tr class="text-center">
                <td>D</td>
                <td>

                    <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                        <c:if test="${sc.grade eq 'Dp' or sc.grade eq 'D0'}">
                            <c:set var="count" value="${count + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${count}
                </td>
                <td>
                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${count * 100 / fn:length(studentCourses)}" />%
                </td>
            </tr>
            <c:set var="count" value="0"/>
            <c:set var="ratio" value="0.0"/>
            <tr class="text-center">
                <td>F</td>
                <td>

                    <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                        <c:if test="${sc.grade eq 'F'}">
                            <c:set var="count" value="${count + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${count}
                </td>
                <td>
                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${count * 100 / fn:length(studentCourses)}" />%
                </td>
            </tr>
            <c:set var="count" value="0"/>
            <c:set var="ratio" value="0.0"/>
            <tr class="text-center">
                <td>S</td>
                <td>

                    <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                        <c:if test="${sc.grade eq 'S'}">
                            <c:set var="count" value="${count + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${count}
                </td>
                <td>
                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${count * 100 / fn:length(studentCourses)}" />%
                </td>
            </tr>
            <c:set var="count" value="0"/>
            <c:set var="ratio" value="0.0"/>
            <tr class="text-center">
                <td>U</td>
                <td>

                    <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                        <c:if test="${sc.grade eq 'U'}">
                            <c:set var="count" value="${count + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${count}
                </td>
                <td>
                    <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${count * 100 / fn:length(studentCourses)}" />%
                </td>
            </tr>

            </tbody>
        </table>
    </div>
</div>