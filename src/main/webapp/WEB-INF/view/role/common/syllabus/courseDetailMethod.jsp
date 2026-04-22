<form:form class="form" modelAttribute="profLectureMethod" id="profLectureMethodForm"  method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <form:hidden path="courseId" value="${pc.courseId}"/>
    <div class="card-body">
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.teachingMaterials.main"/></label>
                    <form:input type="text" path="mainTeaching1" class="form-control" disabled="true"/><br/>
                    <form:input type="text" path="mainTeaching2" class="form-control" disabled="true"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.teachingMaterials.sub"/></label>
                    <form:input type="text" path="subTeaching1" class="form-control" disabled="true"/><br/>
                    <form:input type="text" path="subTeaching2" class="form-control" disabled="true"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.reference"/></label>
                    <form:input type="text" path="ref1" class="form-control" disabled="true"/><br/>
                    <form:input type="text" path="ref2" class="form-control" disabled="true"/><br/>
                    <form:input type="text" path="ref3" class="form-control" disabled="true"/><br/>
                    <form:input type="text" path="ref4" class="form-control" disabled="true"/><br/>
                    <form:input type="text" path="ref5" class="form-control" disabled="true"/><br/>
                    <form:input type="text" path="ref6" class="form-control" disabled="true"/><br/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

        </div>
        <br/><br/>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.course.lectureMethod"/></label>
                    <table class="table table-head-custom table-vertical-center">
                        <c:set var="count" value="1"/>
                        <thead>
                        <tr class="table-secondary text-center">
                            <th class="pl-0" style=""><spring:message code="common.no"/></th>
                            <th style=""><spring:message code="professor.methods"/></th>
                            <th style=""></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="lm" items="${lectureMethods}">
                            <c:if test="${profLectureMethod.hasValue(profLectureMethod.lectureMethods, lm.id)}">
                                <tr class="text-center">
                                    <td class="pl-0">
                                            ${count}
                                    </td>
                                    <td class="pl-0">
                                            ${lm.name}
                                    </td>
                                    <td> </td>
                                </tr>
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${profLectureMethod.hasValue(profLectureMethod.lectureMethods, 100)}">
                            <tr class="text-center">
                                <td class="pl-0">
                                        ${count}
                                </td>
                                <td class="pl-0">
                                    <spring:message code="common.other"/>
                                </td>
                                <td>
                                    <form:input type="text" path="lectureMethodOther" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                        </c:if>

                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <br/><br/>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.evaluationMethods"/></label>
                    <table class="table table-head-custom table-vertical-center">
                        <c:set var="count" value="1"/>
                        <thead>
                        <tr class="table-secondary text-center">
                            <th class="pl-0" style=""><spring:message code="common.no"/></th>
                            <th style=""><spring:message code="professor.methods"/></th>
                            <th style=""></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="lm" items="${evaluationMethods}">
                            <c:if test="${profLectureMethod.hasValue(profLectureMethod.evaluationMethods, lm.id)}">
                                <tr class="text-center">
                                    <td class="pl-0">
                                            ${count}
                                    </td>
                                    <td class="pl-0">
                                            ${lm.name}
                                    </td>
                                    <td> </td>
                                </tr>
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${profLectureMethod.hasValue(profLectureMethod.evaluationMethods, 100)}">
                            <tr class="text-center">
                                <td class="pl-0">
                                        ${count}
                                </td>
                                <td class="pl-0">
                                    <spring:message code="common.other"/>
                                </td>
                                <td>
                                    <form:input type="text" path="evaluationMethodOther" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                        </c:if>

                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <br/><br/>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.typeOfEducationalMedium"/></label>
                    <table class="table table-head-custom table-vertical-center">
                        <c:set var="count" value="1"/>
                        <thead>
                        <tr class="table-secondary text-center">
                            <th class="pl-0" style=""><spring:message code="common.no"/></th>
                            <th style=""><spring:message code="professor.methods"/></th>
                            <th style=""></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="lm" items="${educationalMediums}">
                            <c:if test="${profLectureMethod.hasValue(profLectureMethod.educationalMediums, lm.id)}">
                                <tr class="text-center">
                                    <td class="pl-0">
                                            ${count}
                                    </td>
                                    <td class="pl-0">
                                            ${lm.name}
                                    </td>
                                    <td> </td>
                                </tr>
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${profLectureMethod.hasValue(profLectureMethod.educationalMediums, 100)}">
                            <tr class="text-center">
                                <td class="pl-0">
                                        ${count}
                                </td>
                                <td class="pl-0">
                                    <spring:message code="common.other"/>
                                </td>
                                <td>
                                    <form:input type="text" path="educationalMediumOther" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                        </c:if>

                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <br/><br/>
            <%--<div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label><spring:message code="professor.classificationOfEducationPlace"/></label>
                        <table class="table table-head-custom table-vertical-center">
                            <thead>
                            <tr class="table-secondary text-center">
                                <th class="pl-0" style=""></th>
                                <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                <th style=""><spring:message code="professor.methods"/></th>
                                <th style=""><spring:message code="professor.directInput"/></th>
                                <th style=""><spring:message code="professor.inputAvailable"/></th>
                                <th style=""></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="text-center">
                                <td class="pl-0">
                                    <input type="checkbox" name=""/>
                                </td>
                                <td class="pl-0">
                                    1
                                </td>
                                <td class="pl-0">
                                    General Classroom
                                </td>
                                <td>

                                    Text Input

                                </td>

                                <td>
                                    Y
                                </td>
                            </tr>


                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
            <br/><br/>--%>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.equipmentAndTools"/></label>
                        <%--&nbsp;&nbsp;&nbsp;<button class="btn btn-light btn-sm">Find Eq</button>
                        <button class="btn btn-light btn-sm">Delete</button>
                        <button class="btn btn-light btn-sm">Save</button>
                        <br/><br/>--%>
                    <table class="table table-head-custom table-vertical-center">
                        <c:set var="count" value="1"/>
                        <thead>
                        <tr class="table-secondary text-center">
                            <th class="pl-0" style=""><spring:message code="common.no"/></th>
                            <th style=""><spring:message code="professor.methods"/></th>
                            <th style=""></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="lm" items="${equipments}">
                            <c:if test="${profLectureMethod.hasValue(profLectureMethod.equipments, lm.id)}">
                                <tr class="text-center">
                                    <td class="pl-0">
                                            ${count}
                                    </td>
                                    <td class="pl-0">
                                            ${lm.name}
                                    </td>
                                    <td> </td>
                                </tr>
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${profLectureMethod.hasValue(profLectureMethod.equipments, 100)}">
                            <tr class="text-center">
                                <td class="pl-0">
                                        ${count}
                                </td>
                                <td class="pl-0">
                                    <spring:message code="common.other"/>
                                </td>
                                <td>
                                    <form:input type="text" path="equipmentOther" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                        </c:if>

                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <br/><br/>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.note"/></label>
                    <form:textarea path="note" class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" disabled="true"></form:textarea>
                </div>
            </div>

        </div>


    </div>

</form:form>