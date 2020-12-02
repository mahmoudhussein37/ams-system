<form:form class="form" modelAttribute="lectureFundamentals" id="lectureFundamentalsForm" action="${baseUrl}/professor/classProgress/syllabus/courseDetail/lectureFundamentals?profCourseId=${pc.courseId}" method="post">
    <form:hidden path="courseId" value="${pc.courseId}"/>
    <div class="card-body">

        <%@include file="/WEB-INF/view/role/common/syllabus/courseDetailBasic.jsp" %>


        <div class="row">

            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.subjectIntro"/></label>
                    <form:textarea path="intro" class="form-control" rows="6"></form:textarea>
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
                            <c:choose>
                                <c:when test="${menuAccess.syllabus}">
                                    <c:forEach var="achi" begin="1" end="10">
                                        <td>
                                            <form:checkbox path="achieve${achi}"/>
                                        </td>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="achi" begin="1" end="10">
                                        <td>
                                            <form:checkbox path="achieve${achi}" disabled="true"/>
                                        </td>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>

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
                        <c:choose>
                            <c:when test="${menuAccess.syllabus}">
                                <tr class="text-center">
                                    <td>
                                        <form:input type="number" path="rateAttendance" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input type="number" path="rateAssignment" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input type="number" path="rateMid" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input type="number" path="rateFinal" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input type="number" path="rateOptional" cssClass="form-control"/>
                                    </td>
                                </tr>

                            </c:when>
                            <c:otherwise>
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
                                <spring:message code="professor.learningAchievement"/>
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
                        <c:choose>
                            <c:when test="${menuAccess.syllabus}">
                                <tr class="text-center">
                                    <td>
                                        1
                                    </td>
                                    <td>
                                        <form:input path="clo1" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="la1" cssClass="form-control"/>
                                    </td>
                                    <td style="text-align:left">
                                        <input type="checkbox" name="ref1Checkbox" value="assignment" ${lectureFundamentals.hasValue(lectureFundamentals.reflect1, 'assignment') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.assignment"/>
                                        <br/>
                                        <input type="checkbox" name="ref1Checkbox" value="midTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect1, 'midTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.midTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref1Checkbox" value="finalTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect1, 'finalTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.finalTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref1Checkbox" value="options" ${lectureFundamentals.hasValue(lectureFundamentals.reflect1, 'options') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.options"/>
                                        <br/>

                                    </td>
                                    <td>
                                        <form:input path="obj1" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="comp1" cssClass="form-control"/>
                                    </td>
                                </tr>

                                <tr class="text-center">
                                    <td>
                                        2
                                    </td>
                                    <td>
                                        <form:input path="clo2" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="la2" cssClass="form-control"/>
                                    </td>
                                    <td style="text-align:left">
                                        <input type="checkbox" name="ref2Checkbox" value="assignment" ${lectureFundamentals.hasValue(lectureFundamentals.reflect2, 'assignment') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.assignment"/>
                                        <br/>
                                        <input type="checkbox" name="ref2Checkbox" value="midTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect2, 'midTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.midTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref2Checkbox" value="finalTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect2, 'finalTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.finalTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref2Checkbox" value="options" ${lectureFundamentals.hasValue(lectureFundamentals.reflect2, 'options') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.options"/>
                                        <br/>

                                    </td>
                                    <td>
                                        <form:input path="obj2" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="comp2" cssClass="form-control"/>
                                    </td>
                                </tr>

                                <tr class="text-center">
                                    <td>
                                        3
                                    </td>
                                    <td>
                                        <form:input path="clo3" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="la3" cssClass="form-control"/>
                                    </td>
                                    <td style="text-align:left">
                                        <input type="checkbox" name="ref3Checkbox" value="assignment" ${lectureFundamentals.hasValue(lectureFundamentals.reflect3, 'assignment') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.assignment"/>
                                        <br/>
                                        <input type="checkbox" name="ref3Checkbox" value="midTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect3, 'midTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.midTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref3Checkbox" value="finalTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect3, 'finalTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.finalTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref3Checkbox" value="options" ${lectureFundamentals.hasValue(lectureFundamentals.reflect3, 'options') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.options"/>
                                        <br/>

                                    </td>
                                    <td>
                                        <form:input path="obj3" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="comp3" cssClass="form-control"/>
                                    </td>
                                </tr>

                                <tr class="text-center">
                                    <td>
                                        4
                                    </td>
                                    <td>
                                        <form:input path="clo4" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="la4" cssClass="form-control"/>
                                    </td>
                                    <td style="text-align:left">
                                        <input type="checkbox" name="ref4Checkbox" value="assignment" ${lectureFundamentals.hasValue(lectureFundamentals.reflect4, 'assignment') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.assignment"/>
                                        <br/>
                                        <input type="checkbox" name="ref4Checkbox" value="midTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect4, 'midTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.midTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref4Checkbox" value="finalTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect4, 'finalTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.finalTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref4Checkbox" value="options" ${lectureFundamentals.hasValue(lectureFundamentals.reflect4, 'options') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.options"/>
                                        <br/>

                                    </td>
                                    <td>
                                        <form:input path="obj4" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="comp4" cssClass="form-control"/>
                                    </td>
                                </tr>

                                <tr class="text-center">
                                    <td>
                                        5
                                    </td>
                                    <td>
                                        <form:input path="clo5" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="la5" cssClass="form-control"/>
                                    </td>
                                    <td style="text-align:left">
                                        <input type="checkbox" name="ref5Checkbox" value="assignment" ${lectureFundamentals.hasValue(lectureFundamentals.reflect5, 'assignment') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.assignment"/>
                                        <br/>
                                        <input type="checkbox" name="ref5Checkbox" value="midTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect5, 'midTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.midTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref5Checkbox" value="finalTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect5, 'finalTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.finalTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref5Checkbox" value="options" ${lectureFundamentals.hasValue(lectureFundamentals.reflect5, 'options') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.options"/>
                                        <br/>

                                    </td>
                                    <td>
                                        <form:input path="obj5" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="comp5" cssClass="form-control"/>
                                    </td>
                                </tr>

                                <tr class="text-center">
                                    <td>
                                        6
                                    </td>
                                    <td>
                                        <form:input path="clo6" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="la6" cssClass="form-control"/>
                                    </td>
                                    <td style="text-align:left">
                                        <input type="checkbox" name="ref6Checkbox" value="assignment" ${lectureFundamentals.hasValue(lectureFundamentals.reflect6, 'assignment') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.assignment"/>
                                        <br/>
                                        <input type="checkbox" name="ref6Checkbox" value="midTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect6, 'midTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.midTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref6Checkbox" value="finalTerm" ${lectureFundamentals.hasValue(lectureFundamentals.reflect6, 'finalTerm') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.finalTerm"/>
                                        <br/>
                                        <input type="checkbox" name="ref6Checkbox" value="options" ${lectureFundamentals.hasValue(lectureFundamentals.reflect6, 'options') ? 'checked' : ''}/>
                                        <spring:message code="professor.grade.options"/>
                                        <br/>

                                    </td>
                                    <td>
                                        <form:input path="obj6" cssClass="form-control"/>
                                    </td>
                                    <td>
                                        <form:input path="comp6" cssClass="form-control"/>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <%@include file="/WEB-INF/view/role/common/syllabus/courseDetailFundamentalsList.jsp" %>
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
                        <c:choose>
                            <c:when test="${menuAccess.syllabus}">
                                <tr class="text-center">
                                    <td>
                                        1
                                    </td>
                                    <td>
                                        <spring:message code="professor.pl.high"/>
                                    </td>
                                    <td>
                                        <form:input type="text" path="pl1" cssClass="form-control"/>
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
                                        <form:input type="text" path="pl2" cssClass="form-control"/>
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
                                        <form:input type="text" path="pl3" cssClass="form-control"/>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
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
                            </c:otherwise>
                        </c:choose>


                        </tbody>
                    </table>


                </div>
            </div>
        </div>

    </div>
    <c:if test="${menuAccess.syllabus}">
    <div class="card-footer">
        <button type="submit" id="lecture-fundamental-save" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
            <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
    </div>
    </c:if>
</form:form>