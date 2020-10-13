
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.courseCode"/></label>
                    <input type="text" class="form-control" value="${course.code}" disabled/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>






            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.courseTitle"/></label>
                    <input type="text" class="form-control" value="${course.title}" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.credit"/></label>
                    <input type="text" class="form-control" value="${course.credit}" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.lectureLanguage"/></label>
                    <input type="text" class="form-control" value="${course.lang}" disabled/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>






            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.lectureTime"/></label>
                    <input type="text" class="form-control" value="${course.lectureTime}" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.engAccreditation"/></label>
                    <input type="text" class="form-control" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.compCategory"/></label>
                    <input type="text" class="form-control" value="<spring:message code='comp.category.${course.compCategory}'/>" disabled/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>






            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.subjCategory"/></label>
                    <input type="text" class="form-control" value="<spring:message code='subj.category.${course.subjCategory}'/>" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.profEmail"/></label>
                    <input type="text" class="form-control" value="${not empty course.profUser ? course.profUser.username : ''}" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.supervisor"/></label>
                    <input type="text" class="form-control" disabled/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>






            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label><spring:message code="common.profLab"/></label>
                    <input type="text" class="form-control" value="${not empty course.profUser ? course.profUser.contact.lab : ''}" disabled/>
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
                    <input type="text" class="form-control" value="${not empty course.profUser ? course.profUser.contact.phone : ''}" disabled/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>






            </div>
        </div>
        <div class="row">

            <div class="col-md-4">
                <div class="form-group">
                    <label><spring:message code="common.prerequisite"/></label>
                    <input type="text" class="form-control" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>


            </div>

        </div>
