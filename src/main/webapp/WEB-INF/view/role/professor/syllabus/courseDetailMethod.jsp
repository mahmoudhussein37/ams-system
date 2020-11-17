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
                                Theory Lecture
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
        <br/><br/>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.evaluationMethods"/></label>
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
                                Portfolio
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
        <br/><br/>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.typeOfEducationalMedium"/></label>
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
                                Board
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
        <br/><br/>
        <div class="row">
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
        <br/><br/>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.equipmentAndTools"/></label>
                    &nbsp;&nbsp;&nbsp;<button class="btn btn-light btn-sm">Find Eq</button>
                    <button class="btn btn-light btn-sm">Delete</button>
                    <button class="btn btn-light btn-sm">Save</button>
                    <br/><br/>
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
                                Board
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
    <div class="card-footer">
        <button type="button" id="prof-lecture-method-save" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
            <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
    </div>
</form:form>