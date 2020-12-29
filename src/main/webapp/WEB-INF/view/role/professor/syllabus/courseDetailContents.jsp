<form:form class="form" modelAttribute="lectureContents" id="lectureContentsForm" action="${baseUrl}/professor/classProgress/syllabus/courseDetail/lectureContents?profCourseId=${pc.id}" method="post">
    <div class="card-body">
            <%--<div class="row">
                <div class="col-md-10">
                </div>
                <div class="col-md-2">
                    <br/>
                    <button class="btn btn-primary" style="width:100%;margin-top:10px;">Add</button>
                </div>

            </div>--%>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-head-custom table-vertical-center" id="course-list">
                    <thead>
                    <tr class="text-uppercase">

                        <th class="text-center" style="width:5%"><span class="text-primary"><spring:message code="common.week"/></span></th>
                        <th class="text-center" style="width:60%"><span class="text-primary"><spring:message code="common.classContents"/></span>
                        <th class="text-center" style="width:35%"><span class="text-primary"><spring:message code="common.note"/></span>
                    </tr>
                    </thead>
                    <c:choose>
                        <c:when test="${isEditable}">
                            <tbody>
                            <tr>
                                <td>
                                    1
                                </td>
                                <td>
                                    <form:input type="text" path="content1" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note1" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    2
                                </td>
                                <td>
                                    <form:input type="text" path="content2" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note2" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    3
                                </td>
                                <td>
                                    <form:input type="text" path="content3" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note3" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    4
                                </td>
                                <td>
                                    <form:input type="text" path="content4" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note4" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    5
                                </td>
                                <td>
                                    <form:input type="text" path="content5" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note5" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    6
                                </td>
                                <td>
                                    <form:input type="text" path="content6" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note6" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    7
                                </td>
                                <td>
                                    <form:input type="text" path="content7" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note7" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    8
                                </td>
                                <td>
                                    <form:input type="text" path="content8" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note8" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    9
                                </td>
                                <td>
                                    <form:input type="text" path="content9" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note9" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    10
                                </td>
                                <td>
                                    <form:input type="text" path="content10" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note10" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    11
                                </td>
                                <td>
                                    <form:input type="text" path="content11" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note11" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    12
                                </td>
                                <td>
                                    <form:input type="text" path="content12" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note12" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    13
                                </td>
                                <td>
                                    <form:input type="text" path="content13" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note13" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    14
                                </td>
                                <td>
                                    <form:input type="text" path="content14" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note14" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    15
                                </td>
                                <td>
                                    <form:input type="text" path="content15" class="form-control"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note15" class="form-control"/>
                                </td>
                            </tr>

                            </tbody>
                        </c:when>
                        <c:otherwise>
                            <tbody>
                            <tr>
                                <td>
                                    1
                                </td>
                                <td>
                                    <form:input type="text" path="content1" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note1" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    2
                                </td>
                                <td>
                                    <form:input type="text" path="content2" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note2" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    3
                                </td>
                                <td>
                                    <form:input type="text" path="content3" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note3" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    4
                                </td>
                                <td>
                                    <form:input type="text" path="content4" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note4" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    5
                                </td>
                                <td>
                                    <form:input type="text" path="content5" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note5" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    6
                                </td>
                                <td>
                                    <form:input type="text" path="content6" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note6" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    7
                                </td>
                                <td>
                                    <form:input type="text" path="content7" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note7" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    8
                                </td>
                                <td>
                                    <form:input type="text" path="content8" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note8" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    9
                                </td>
                                <td>
                                    <form:input type="text" path="content9" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note9" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    10
                                </td>
                                <td>
                                    <form:input type="text" path="content10" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note10" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    11
                                </td>
                                <td>
                                    <form:input type="text" path="content11" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note11" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    12
                                </td>
                                <td>
                                    <form:input type="text" path="content12" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note12" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    13
                                </td>
                                <td>
                                    <form:input type="text" path="content13" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note13" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    14
                                </td>
                                <td>
                                    <form:input type="text" path="content14" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note14" class="form-control" disabled="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    15
                                </td>
                                <td>
                                    <form:input type="text" path="content15" class="form-control" disabled="true"/>
                                </td>
                                <td>
                                    <form:input type="text" path="note15" class="form-control" disabled="true"/>
                                </td>
                            </tr>

                            </tbody>
                        </c:otherwise>
                    </c:choose>

                </table>
            </div>

        </div>

    </div>
    <c:if test="${isEditable}">
    <div class="card-footer">
        <button type="submit" id="lecture-contents-save" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
            <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
    </div>
    </c:if>
</form:form>