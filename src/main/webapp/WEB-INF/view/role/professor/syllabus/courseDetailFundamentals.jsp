<form:form class="form" modelAttribute="lectureFundamentals" id="lectureFundamentalsForm" action="${baseUrl}/professor/classProgress/syllabus/courseDetail?courseId=${course.id}" method="post">
    <form:hidden path="courseId" value="${course.id}"/>
    <div class="card-body">
        <%@include file="/WEB-INF/view/role/professor/inquiryCourse/courseDetailBasicInfo.jsp" %>

        <div class="row">

            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.subjectIntro"/></label>
                    <form:textarea path="intro" class="form-control" rows="6"></form:textarea>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>

        </div>

    </div>
    <div class="card-footer">
        <button type="submit" id="lecture-fundamental-save" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
        <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
    </div>
</form:form>