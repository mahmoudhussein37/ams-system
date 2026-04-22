<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="card-body">
    <div class="row">
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.studentNumber"/></label>
                <input type="text" class="form-control" value="${studentUser.number}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>

        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.department"/></label>
                <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.name"/></label>
                <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>

    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.status"/></label>
                <input type="text" class="form-control" value="<spring:message code="student.status.${studentUser.status}"/>" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.advisor"/></label>
                <c:choose>
                    <c:when test="${studentUser.advisorId gt 0 and not empty studentUser.advisor}">
                        <input type="text" class="form-control" value="${studentUser.advisor.getFullName()}" disabled/>
                    </c:when>
                    <c:otherwise>
                        <input type="text" class="form-control" value="<spring:message code="common.notAssigned"/>" disabled/>
                    </c:otherwise>
                </c:choose>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.admissionYear"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.admissionYear}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.completeSemester"/></label>
                <input type="text" class="form-control" value="${completeSemester}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
    </div>

    <br/>
    <div class="separator separator-solid my-5"></div>
    <br/>
    <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailCourseHistory.jsp" %>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>