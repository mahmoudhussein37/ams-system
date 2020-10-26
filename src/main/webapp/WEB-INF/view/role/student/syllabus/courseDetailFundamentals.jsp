<form:form class="form" modelAttribute="lectureFundamentals" id="lectureFundamentalsForm" method="post">
    <form:hidden path="courseId" value="${course.id}"/>
    <div class="card-body">
        <%@include file="/WEB-INF/view/role/professor/inquiryCourse/courseDetailBasicInfo.jsp" %>

        <div class="row">

            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.subjectIntro"/></label>
                    <form:textarea path="intro" class="form-control" rows="6"  disabled="true"></form:textarea>
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

    </div>

</form:form>