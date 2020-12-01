<div class="card-body">
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.personalInfo"/></h3>
    <div class="row">
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.cellPhone"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.cellPhone}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.phone"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.phone}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3"></div>
        <div class="col-md-3"></div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.post"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.postCode}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label><spring:message code="common.address"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.address}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>
    <div class="separator separator-solid my-5"></div>
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.parentsInfo"/></h3>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.name"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.parentName}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.relation"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.relation}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.cellPhone"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.parentCellPhone}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.phone"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.parentPhone}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.post"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.parentPostCode}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label><spring:message code="common.address"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.parentAddress}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>

    <div class="separator separator-solid my-5"></div>
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.admissionInformation"/></h3>

    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.admissionYear"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.admissionYear}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>

        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.admissionDate"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.admissionDate}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.highSchool"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.highSchool}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.graduationYear"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.hGradYear}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>


    <div class="separator separator-solid my-5"></div>
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.graduationInformation"/></h3>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.graduationYear"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.gradYear}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.graduationDate"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.gradDate}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.graduationSemester"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.gradSemester}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.graduationDegree"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.gradDegree}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.degreeNumber"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.degreeNumber}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.certificateNumber"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.certNumber}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>

</div>
