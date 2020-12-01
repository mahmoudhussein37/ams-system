<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.courseInformation"/></h3>
<div class="row">
    <div class="col-md-12">
        <div class="form-group">
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label><spring:message code="common.courseCode"/></label>
                        <input type="text" class="form-control" disabled value="${pc.course.code}"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label><spring:message code="common.courseTitle"/></label>
                        <input type="text" class="form-control" disabled value="${pc.course.title}"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label><spring:message code="common.semester"/></label>
                        <input type="text" class="form-control" disabled value="${pc.semester.year} - ${pc.semester.semester}"/>

                    </div>
                </div>
                <div class="col-md-3">

                    <div class="form-group">
                        <label><spring:message code="common.divide"/></label>
                        <input type="text" class="form-control" disabled value="${pc.divide}"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label><spring:message code="common.professor"/></label>
                        <input type="text" class="form-control" disabled value="${pc.professorUser.getFullName()}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>