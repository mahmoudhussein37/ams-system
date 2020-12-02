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
            <label><spring:message code="common.subjCategory"/></label>
            <input type="text" class="form-control" value="<spring:message code='subj.category.${pc.course.subjCategory}'/>" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>


    </div>
    <div class="col-md-4">
        <div class="form-group">
            <label><spring:message code="common.lectureLanguage"/></label>
            <input type="text" class="form-control" value="${pc.language}" disabled/>
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
            <input type="text" class="form-control" value="${pc.engAccreditation eq true ? 'Y' : 'N'}" disabled/>
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
                <label><spring:message code="common.divide"/></label>
                <input type="text" class="form-control" value="${pc.divide}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
    <div class="col-md-4">
        <div class="form-group">
            <label><spring:message code="common.supervisor"/></label>
            <input type="text" class="form-control" value="${not empty pc.professorUser ? pc.professorUser.getFullName() : ''}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
        <div class="col-md-4">
            <div class="form-group">
                <label><spring:message code="common.lectureTime"/></label>
                <c:set var="lectureTime" value=""/>

                <c:forEach var="classTime" items="${pc.classTimeList}" varStatus="varStatus">
                    <c:set var="lectureTime">
                        ${lectureTime}<spring:message code="common.week${classTime.w}"/> ${classTime.s}~${classTime.e}<c:if test="${varStatus.count < fn:length(pc.classTimeList)}">,&nbsp;</c:if>
                    </c:set>
                </c:forEach>
                <input type="text" class="form-control" value="${lectureTime}" disabled/>
            </div>
        </div>


</div>
<div class="row">
    <div class="col-md-4">
        <div class="form-group">
            <label><spring:message code="common.classRoom"/></label>
            <input type="text" class="form-control" value="${not empty pc.classroomObj ? pc.classroomObj.code : ''}" disabled/>
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
    <div class="col-md-2">
        <div class="form-group">
            <label><spring:message code="common.profLab"/></label>
            <input type="text" class="form-control" value="${not empty pc.professorUser ? pc.professorUser.contact.lab : ''}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>


    </div>

    <div class="col-md-2">
        <div class="form-group">
            <label><spring:message code="common.phone"/></label>
            <input type="text" class="form-control" value="${not empty pc.professorUser ? pc.professorUser.contact.phone : ''}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>

</div>