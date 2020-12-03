<form:form class="form" modelAttribute="profLectureMethod" id="profLectureMethodForm" action="${baseUrl}/professor/classProgress/syllabus/courseDetail/profLectureMethod?profCourseId=${pc.courseId}" method="post">
    <form:hidden path="courseId" value="${pc.courseId}"/>
    <div class="card-body">
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.teachingMaterials.main"/></label>
                    <form:input type="text" path="mainTeaching1" class="form-control"/><br/>
                    <form:input type="text" path="mainTeaching2" class="form-control"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.teachingMaterials.sub"/></label>
                    <form:input type="text" path="subTeaching1" class="form-control"/><br/>
                    <form:input type="text" path="subTeaching2" class="form-control"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.reference"/></label>
                    <form:input type="text" path="ref1" class="form-control"/><br/>
                    <form:input type="text" path="ref2" class="form-control"/><br/>
                    <form:input type="text" path="ref3" class="form-control"/><br/>
                    <form:input type="text" path="ref4" class="form-control"/><br/>
                    <form:input type="text" path="ref5" class="form-control"/><br/>
                    <form:input type="text" path="ref6" class="form-control"/><br/>
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
                        <c:choose>
                            <c:when test="${menuAccess.syllabus}">
                                <thead>
                                <tr class="table-secondary text-center">
                                    <th class="pl-0" style=""></th>
                                    <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                    <th style=""><spring:message code="professor.methods"/></th>
                                    <th style=""></th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach var="lm" items="${lectureMethodsEnabled}">
                                    <c:if test="${lm.enabled}">

                                        <tr class="text-center">
                                            <td class="pl-0">
                                                <input type="checkbox" name="lectureMethodCheckbox" value="${lm.id}" ${profLectureMethod.hasValue(profLectureMethod.lectureMethods, lm.id) ? 'checked' : ''}/>
                                            </td>
                                            <td class="pl-0">
                                                    ${count}
                                            </td>
                                            <td class="pl-0">
                                                    ${lm.name}
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <c:set var="count" value="${count + 1}"/>
                                    </c:if>

                                </c:forEach>
                                <tr class="text-center">
                                    <td class="pl-0">
                                        <input type="checkbox" name="lectureMethodCheckbox" value="100" ${profLectureMethod.hasValue(profLectureMethod.lectureMethods, 100) ? 'checked' : ''}/>
                                    </td>
                                    <td class="pl-0">
                                            ${count}
                                    </td>
                                    <td class="pl-0">
                                            <spring:message code="common.other"/>
                                    </td>
                                    <td>
                                        <form:input type="text" path="lectureMethodOther" class="form-control"/>
                                    </td>
                                </tr>
                                </tbody>
                            </c:when>
                            <c:otherwise>
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
                            </c:otherwise>
                        </c:choose>
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
                        <c:choose>
                            <c:when test="${menuAccess.syllabus}">
                                <thead>
                                <tr class="table-secondary text-center">
                                    <th class="pl-0" style=""></th>
                                    <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                    <th style=""><spring:message code="professor.methods"/></th>
                                    <th style=""></th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach var="lm" items="${evaluationMethodsEnabled}">
                                    <c:if test="${lm.enabled}">

                                        <tr class="text-center">
                                            <td class="pl-0">
                                                <input type="checkbox" name="evaluationMethodCheckbox" value="${lm.id}" ${profLectureMethod.hasValue(profLectureMethod.evaluationMethods, lm.id) ? 'checked' : ''}/>
                                            </td>
                                            <td class="pl-0">
                                                    ${count}
                                            </td>
                                            <td class="pl-0">
                                                    ${lm.name}
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <c:set var="count" value="${count + 1}"/>
                                    </c:if>

                                </c:forEach>
                                <tr class="text-center">
                                    <td class="pl-0">
                                        <input type="checkbox" name="evaluationMethodCheckbox" value="100" ${profLectureMethod.hasValue(profLectureMethod.evaluationMethods, 100) ? 'checked' : ''}/>
                                    </td>
                                    <td class="pl-0">
                                            ${count}
                                    </td>
                                    <td class="pl-0">
                                        <spring:message code="common.other"/>
                                    </td>
                                    <td>
                                        <form:input type="text" path="evaluationMethodOther" class="form-control"/>
                                    </td>
                                </tr>
                                </tbody>
                            </c:when>
                            <c:otherwise>
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
                            </c:otherwise>
                        </c:choose>
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
                        <c:choose>
                            <c:when test="${menuAccess.syllabus}">
                                <thead>
                                <tr class="table-secondary text-center">
                                    <th class="pl-0" style=""></th>
                                    <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                    <th style=""><spring:message code="professor.methods"/></th>
                                    <th style=""></th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach var="lm" items="${educationalMediumsEnabled}">
                                    <c:if test="${lm.enabled}">

                                        <tr class="text-center">
                                            <td class="pl-0">
                                                <input type="checkbox" name="educationalMediumCheckbox" value="${lm.id}" ${profLectureMethod.hasValue(profLectureMethod.educationalMediums, lm.id) ? 'checked' : ''}/>
                                            </td>
                                            <td class="pl-0">
                                                    ${count}
                                            </td>
                                            <td class="pl-0">
                                                    ${lm.name}
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <c:set var="count" value="${count + 1}"/>
                                    </c:if>

                                </c:forEach>
                                <tr class="text-center">
                                    <td class="pl-0">
                                        <input type="checkbox" name="educationalMediumCheckbox" value="100" ${profLectureMethod.hasValue(profLectureMethod.educationalMediums, 100) ? 'checked' : ''}/>
                                    </td>
                                    <td class="pl-0">
                                            ${count}
                                    </td>
                                    <td class="pl-0">
                                        <spring:message code="common.other"/>
                                    </td>
                                    <td>
                                        <form:input type="text" path="educationalMediumOther" class="form-control"/>
                                    </td>
                                </tr>
                                </tbody>
                            </c:when>
                            <c:otherwise>
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
                            </c:otherwise>
                        </c:choose>
                    </table>
                </div>
            </div>

        </div>
        <br/><br/>

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
                        <c:choose>
                            <c:when test="${menuAccess.syllabus}">
                                <thead>
                                <tr class="table-secondary text-center">
                                    <th class="pl-0" style=""></th>
                                    <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                    <th style=""><spring:message code="common.code"/></th>
                                    <th style=""><spring:message code="professor.methods"/></th>
                                    <th style=""></th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach var="lm" items="${equipmentsEnabled}">
                                    <c:if test="${lm.enabled}">

                                        <tr class="text-center">
                                            <td class="pl-0">
                                                <input type="checkbox" name="equipmentCheckbox" value="${lm.id}" ${profLectureMethod.hasValue(profLectureMethod.equipments, lm.id) ? 'checked' : ''}/>
                                            </td>
                                            <td class="pl-0">
                                                    ${count}
                                            </td>
                                            <td class="pl-0">
                                                    ${lm.code}
                                            </td>
                                            <td class="pl-0">
                                                    ${lm.name}
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <c:set var="count" value="${count + 1}"/>
                                    </c:if>

                                </c:forEach>
                                <tr class="text-center">
                                    <td class="pl-0">
                                        <input type="checkbox" name="equipmentCheckbox" value="100" ${profLectureMethod.hasValue(profLectureMethod.equipments, 100) ? 'checked' : ''}/>
                                    </td>
                                    <td class="pl-0">
                                            ${count}
                                    </td>
                                    <td class="pl-0">

                                    </td>
                                    <td class="pl-0">
                                        <spring:message code="common.other"/>
                                    </td>
                                    <td>
                                        <form:input type="text" path="equipmentOther" class="form-control"/>
                                    </td>
                                </tr>
                                </tbody>
                            </c:when>
                            <c:otherwise>
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
                            </c:otherwise>
                        </c:choose>
                    </table>
                </div>
            </div>

        </div>
        <br/><br/>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.note"/></label>
                    <form:textarea path="note" class="form-control" dir="rtl" rows="6"></form:textarea>
                </div>
            </div>

        </div>


    </div>
    <c:if test="${menuAccess.syllabus}">
    <div class="card-footer">
        <button type="button" id="prof-lecture-method-save" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
            <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
    </div>
    </c:if>
</form:form>