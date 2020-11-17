<form:form class="form" modelAttribute="lectureFundamentals" id="lectureFundamentalsForm" action="${baseUrl}/professor/classProgress/syllabus/courseDetail/lectureFundamentals?profCourseId=${pc.courseId}" method="post">
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
                    <label><spring:message code="common.lectureLanguage"/></label>
                    <input type="text" class="form-control" value="${pc.course.lang}" disabled/>
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
                    <input type="text" class="form-control" disabled/>
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
                    <label><spring:message code="common.supervisor"/></label>
                    <input type="text" class="form-control" disabled/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.subjCategory"/></label>
                    <input type="text" class="form-control" value="<spring:message code='subj.category.${pc.course.subjCategory}'/>" disabled/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
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
                    <input type="text" class="form-control" disabled/>
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
                            <c:forEach var="achi" begin="1" end="10">
                                <td>
                                    <form:checkbox path="achieve${achi}"/>
                                </td>
                            </c:forEach>
                        </tr>

                        </tbody>
                    </table>


                </div>
            </div>
        </div>

    </div>
    <div class="card-footer">
        <button type="submit" id="lecture-fundamental-save" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
            <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
    </div>
</form:form>