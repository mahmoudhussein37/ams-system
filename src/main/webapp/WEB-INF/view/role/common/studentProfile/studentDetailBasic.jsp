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
            <label><spring:message code="common.name"/></label>
            <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.department"/></label>
            <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3" style="text-align:center">
        <br/>
        <c:choose>
            <c:when test="${not empty studentUser.profile}">
                <img src="${baseUrl}/download?uploadedFileId=${studentUser.profile.id}" style="max-width:100px"/>
            </c:when>
            <c:otherwise>
                <img src="${resources}/images/user.png" style="max-width:100px"/>
            </c:otherwise>
        </c:choose>

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
            <input type="text" class="form-control"  value="${studentUser.advisor.getFullName()}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.schoolYear"/></label>
            <input type="text" class="form-control" value="${studentUser.schoolYear}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">


    </div>
</div>