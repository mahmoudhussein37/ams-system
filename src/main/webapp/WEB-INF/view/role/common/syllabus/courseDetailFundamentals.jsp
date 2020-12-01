<form:form class="form" modelAttribute="lectureFundamentals" id="lectureFundamentalsForm" method="post">
    <form:hidden path="courseId" value="${pc.courseId}"/>
    <div class="card-body">

        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.courseCode"/></label>
                    <input type="text" class="form-control" value="${pc.course.code}" disabled/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.courseTitle"/></label>
                    <input type="text" class="form-control" value="${pc.course.title}" disabled/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.credit"/></label>
                    <input type="text" class="form-control" value="${pc.course.credit}" disabled/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.subjCategory"/></label>
                    <input type="text" class="form-control" value="<spring:message code='subj.category.${pc.course.subjCategory}'/>" disabled/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.lectureLanguage"/></label>
                    <input type="text" class="form-control" value="${pc.language}" disabled/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>

            </div>

                <%--            <div class="col-md-4">
                                <div class="form-group">
                                    <label><spring:message code="common.lectureTime"/></label>
                                    <input type="text" class="form-control" value="${pc.course.lectureTime}" disabled/>
                                        &lt;%&ndash;<span class="form-text text-muted">We'll never share your email with anyone else</span>&ndash;%&gt;
                                </div>


                            </div>--%>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.engAccreditation"/></label>
                    <input type="text" class="form-control" value="${pc.engAccreditation eq true ? 'Y' : 'N'}" disabled/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
        </div>
        <div class="row">
                <%--<div class="col-md-4">
                    <div class="form-group">
                        <label><spring:message code="common.compCategory"/></label>
                        <input type="text" class="form-control" value="<spring:message code='comp.category.${course.compCategory}'/>" disabled/>
                        &lt;%&ndash;<span class="form-text text-muted">Please enter your full name</span>&ndash;%&gt;
                    </div>
                </div>--%>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label><spring:message code="common.divide"/></label>
                            <input type="text" class="form-control" value="${pc.divide}" disabled/>
                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                        </div>

                    </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.supervisor"/></label>
                    <input type="text" class="form-control" value="${not empty pc.professorUser ? pc.professorUser.getFullName() : ''}" disabled/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.profEmail"/></label>
                    <input type="text" class="form-control" value="${not empty pc.professorUser ? pc.professorUser.username : ''}" disabled/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
        </div>
        <div class="row">

            <div class="col-md-2">
                <div class="form-group">
                    <label><spring:message code="common.profLab"/></label>
                    <input type="text" class="form-control" value="${not empty pc.professorUser ? pc.professorUser.contact.lab : ''}" disabled/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label><spring:message code="common.classRoom"/></label>
                    <input type="text" class="form-control" value="${not empty pc.classroomObj ? pc.classroomObj.code : ''}" disabled/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.phone"/></label>
                    <input type="text" class="form-control" value="${not empty pc.professorUser ? pc.professorUser.contact.phone : ''}" disabled/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
        </div>


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
                        <tr class="text-center">
                            <td>
                                1
                            </td>
                            <td>
                                <form:input path="clo1" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="la1" cssClass="form-control" disabled="true"/>
                            </td>
                            <td style="text-align:left">
                                <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect1)}"/>
                                <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect1)}" varStatus="varStatus">
                                    <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <form:input path="obj1" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="comp1" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>

                        <tr class="text-center">
                            <td>
                                2
                            </td>
                            <td>
                                <form:input path="clo2" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="la2" cssClass="form-control" disabled="true"/>
                            </td>
                            <td style="text-align:left">
                                <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect2)}"/>
                                <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect2)}" varStatus="varStatus">
                                    <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
                                </c:forEach>

                            </td>
                            <td>
                                <form:input path="obj2" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="comp2" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>

                        <tr class="text-center">
                            <td>
                                3
                            </td>
                            <td>
                                <form:input path="clo3" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="la3" cssClass="form-control" disabled="true"/>
                            </td>
                            <td style="text-align:left">
                                <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect3)}"/>
                                <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect3)}" varStatus="varStatus">
                                    <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
                                </c:forEach>

                            </td>
                            <td>
                                <form:input path="obj3" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="comp3" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>

                        <tr class="text-center">
                            <td>
                                4
                            </td>
                            <td>
                                <form:input path="clo4" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="la4" cssClass="form-control" disabled="true"/>
                            </td>
                            <td style="text-align:left">
                                <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect4)}"/>
                                <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect4)}" varStatus="varStatus">
                                    <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
                                </c:forEach>

                            </td>
                            <td>
                                <form:input path="obj4" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="comp4" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>

                        <tr class="text-center">
                            <td>
                                5
                            </td>
                            <td>
                                <form:input path="clo5" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="la5" cssClass="form-control" disabled="true"/>
                            </td>
                            <td style="text-align:left">
                                <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect5)}"/>
                                <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect5)}" varStatus="varStatus">
                                    <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
                                </c:forEach>

                            </td>
                            <td>
                                <form:input path="obj5" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="comp5" cssClass="form-control" disabled="true"/>
                            </td>
                        </tr>

                        <tr class="text-center">
                            <td>
                                6
                            </td>
                            <td>
                                <form:input path="clo6" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="la6" cssClass="form-control" disabled="true"/>
                            </td>
                            <td style="text-align:left">
                                <c:set var="sList" value="${lectureFundamentals.toStringList(lectureFundamentals.reflect6)}"/>
                                <c:forEach var="rfString" items="${lectureFundamentals.toStringList(lectureFundamentals.reflect6)}" varStatus="varStatus">
                                    <spring:message code="professor.grade.${rfString}"/><c:if test="${varStatus.index < fn:length(sList) - 1}">,&nbsp;</c:if>
                                </c:forEach>

                            </td>
                            <td>
                                <form:input path="obj6" cssClass="form-control" disabled="true"/>
                            </td>
                            <td>
                                <form:input path="comp6" cssClass="form-control" disabled="true"/>
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