<form:form class="form" modelAttribute="lectureFundamentals" id="lectureFundamentalsForm" method="post">
    <form:hidden path="courseId" value="${pc.courseId}"/>
    <div class="card-body">

        <%@include file="/WEB-INF/view/role/common/syllabus/courseDetailBasic.jsp" %>


        <div class="row">

            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.subjectIntro"/></label>
                    <form:textarea path="intro" class="form-control" rows="6" disabled="true"></form:textarea>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>

        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.achievements"/></label>
                    <table class="table rounded">
                        <thead>
                        <tr class="table-secondary text-center">

                            <c:forEach var="achi" begin="1" end="10">
                                <td>
                                        ${achi}
                                </td>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="table-light text-center">
                            <c:forEach var="achi" begin="1" end="10">
                                <td>
                                    <spring:message code="professor.achieve${achi}"/>
                                </td>
                            </c:forEach>
                        </tr>
                        <tr class="text-center">
                            <c:forEach var="achi" begin="1" end="10">
                                <td>
                                    <form:checkbox path="achieve${achi}" disabled="true"/>
                                </td>
                            </c:forEach>

                        </tr>

                        </tbody>
                    </table>


                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.gradeRates"/></label>
                    <table class="table rounded">
                        <thead>
                        <tr class="table-secondary text-center">

                            <td>
                                <spring:message code="professor.grade.attendance"/>
                            </td>
                            <td>
                                <spring:message code="professor.grade.assignment"/>
                            </td>
                            <td>
                                <spring:message code="professor.grade.midTerm"/>
                            </td>
                            <td>
                                <spring:message code="professor.grade.finalTerm"/>
                            </td>
                            <td>
                                <spring:message code="professor.grade.options"/>
                            </td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="text-center">
                            <td>
                                <form:input type="number" path="rateAttendance" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input type="number" path="rateAssignment" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input type="number" path="rateMid" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input type="number" path="rateFinal" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input type="number" path="rateOptional" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>

                        </tbody>
                    </table>


                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.courseLearningObjectives"/></label>
                    <table class="table rounded">
                        <thead>
                        <tr class="table-secondary text-center">

                            <td>
                                <spring:message code="common.no"/>
                            </td>
                            <td>
                                <spring:message code="professor.courseLearningObjectives"/>
                            </td>
                            <td>
                                <spring:message code="professor.learningObjectives"/>
                            </td>
                            <td>
                                <spring:message code="professor.reflectType"/>
                            </td>
                            <td>
                                <spring:message code="professor.objectiveAboveMedium"/>
                            </td>
                            <td>
                                <spring:message code="professor.competencyUnitElement"/>
                            </td>
                        </tr>
                        </thead>
                        <tbody>
                        <%@include file="/WEB-INF/view/role/common/syllabus/courseDetailFundamentalsList.jsp" %>


                        </tbody>
                    </table>


                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.performanceLevel"/></label>
                    <table class="table rounded">
                        <thead>
                        <tr class="table-secondary text-center">

                            <td>
                                <spring:message code="common.no"/>
                            </td>
                            <td>
                                <spring:message code="professor.classification"/>
                            </td>
                            <td>
                                <spring:message code="professor.performanceLevel"/>
                            </td>

                        </tr>
                        </thead>
                        <tbody>
                        <tr class="text-center">
                            <td>
                                1
                            </td>
                            <td>
                                <spring:message code="professor.pl.high"/>
                            </td>
                            <td>
                                <form:input type="text" path="pl1" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>
                        <tr class="text-center">
                            <td>
                                2
                            </td>
                            <td>
                                <spring:message code="professor.pl.medium"/>
                            </td>
                            <td>
                                <form:input type="text" path="pl2" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>
                        <tr class="text-center">
                            <td>
                                3
                            </td>
                            <td>
                                <spring:message code="professor.pl.low"/>
                            </td>
                            <td>
                                <form:input type="text" path="pl3" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>


                        </tbody>
                    </table>


                </div>
            </div>
        </div>

    </div>

</form:form>