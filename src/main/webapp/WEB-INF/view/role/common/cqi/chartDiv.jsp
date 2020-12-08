<div class="row">
    <div class="col-md-12">
        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.cqiReport"/></h3>
        <%@include file="/WEB-INF/view/role/common/cqi/basicInfo.jsp" %>

        <table class="table table-bordered">
            <tr>
                <td style="width:150px">
                    <spring:message code="common.divide2"/>
                </td>
                <c:forEach var="d" items="${professorCourseList}">
                    <td>
                            ${d.divide}
                    </td>
                </c:forEach>
                <c:forEach var="d" begin="1" end="${12 - fn:length(professorCourseList)}">
                    <td>
                    </td>
                </c:forEach>
            </tr>
            <tr>
                <td style="width:150px">
                    <spring:message code="professor.course.numStudent"/>
                </td>
                <c:forEach var="d" items="${professorCourseList}">
                    <td>
                            ${d.numStudent}
                    </td>
                </c:forEach>
                <c:forEach var="d" begin="1" end="${12 - fn:length(professorCourseList)}">
                    <td>
                    </td>
                </c:forEach>

            </tr>

        </table>

        <br/>

    </div>



</div>

<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.classResponsivenessStatistics"/></h3>
<div class="row">
    <div class="col-md-6">

        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.averageAssignedDivide"/></h3>
        <canvas id="chart1"></canvas>
        <table class="table table-bordered">
            <tr>
                <td width="100px">
                    <spring:message code="common.year"/>
                </td>
                <c:forEach var="entry" items="${averageAssignedMap}">
                    <td>
                            ${entry.key}
                    </td>
                </c:forEach>

            </tr>
            <tr>
                <td width="100px">
                    <spring:message code="professor.course.numStudent"/>
                </td>
                <c:forEach var="entry" items="${averageAssignedMap}" varStatus="varStatus">
                    <td>
                        <c:choose>
                            <c:when test="${varStatus.count < fn:length(averageAssignedMap)}">
                                0
                            </c:when>
                            <c:otherwise>
                                ${fn:length(pc.assessmentList)}
                            </c:otherwise>
                        </c:choose>

                    </td>
                </c:forEach>

            </tr>
        </table>

    </div>
    <div class="col-md-6">
        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.assignedDivideSemester"/></h3>
        <canvas id="chart2"></canvas>
        <table class="table table-bordered">
            <tr>
                <td>
                    <spring:message code="common.divide2"/>
                </td>
                <td>
                    <spring:message code="professor.course.numStudent"/>
                </td>

            </tr>
            <c:forEach var="d" items="${professorCourseList}">
                <tr>

                    <td>
                            ${d.divide}
                    </td>
                    <td>
                            ${d.numStudent}
                    </td>

                </tr>

            </c:forEach>


        </table>


    </div>
</div>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.averageEvaluationSemester"/></h3>
<div class="row">
    <div class="col-md-12">
        <canvas id="chart3" style="height:300px"></canvas>
    </div>
</div>
<br/>
<br/>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.gradeRatioDistribution"/></h3>
<div class="row">
    <div class="col-md-12">
        <canvas id="chart4" style="height:300px"></canvas>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <table class="table table-bordered">
            <tr>
                <td>
                    <spring:message code="common.divide2"/>
                </td>
                <td>
                    A
                </td>
                <td>
                    B
                </td>
                <td>
                    C
                </td>
                <td>
                    D
                </td>
                <td>
                    F
                </td>
                <td>
                    S
                </td>
                <td>
                    U
                </td>

            </tr>
            <c:forEach var="professorCourse" items="${professorCourseList}">
                <tr>

                    <td>
                        0${professorCourse.divide}
                    </td>
                    <td>
                        <c:set var="count" value="0"/>
                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                            <c:if test="${sc.grade eq 'Ap' or sc.grade eq 'A0'}">
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>

                        </c:forEach>
                            ${count}
                    </td>
                    <td>
                        <c:set var="count" value="0"/>
                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                            <c:if test="${sc.grade eq 'Bp' or sc.grade eq 'B0'}">
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>

                        </c:forEach>
                            ${count}
                    </td>
                    <td>
                        <c:set var="count" value="0"/>
                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                            <c:if test="${sc.grade eq 'Cp' or sc.grade eq 'c0'}">
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>

                        </c:forEach>
                            ${count}
                    </td>
                    <td>
                        <c:set var="count" value="0"/>
                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                            <c:if test="${sc.grade eq 'Dp' or sc.grade eq 'D0'}">
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>

                        </c:forEach>
                            ${count}
                    </td>
                    <td>
                        <c:set var="count" value="0"/>
                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                            <c:if test="${sc.grade eq 'F'}">
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>

                        </c:forEach>
                            ${count}
                    </td>
                    <td>
                        <c:set var="count" value="0"/>
                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                            <c:if test="${sc.grade eq 'S'}">
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>

                        </c:forEach>
                            ${count}
                    </td>
                    <td>
                        <c:set var="count" value="0"/>
                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                            <c:if test="${sc.grade eq 'U'}">
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>

                        </c:forEach>
                            ${count}
                    </td>

                </tr>
            </c:forEach>


        </table>
    </div>
</div>
<br/><br/>