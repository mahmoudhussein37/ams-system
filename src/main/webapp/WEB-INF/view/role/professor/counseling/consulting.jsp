<div class="row">
    <div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.counselingNumber"/></label>
            <input type="text" class="form-control" value="${counseling.number}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.year"/></label>
            <input type="text" class="form-control"  value="${counseling.year}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.date"/></label>
            <input type="text" class="form-control" value="${counseling.date}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>

</div>
<div class="row">

    <div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.studentsName"/></label>
            <input type="text" class="form-control" value="${counseling.studentUser.getFullName()}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.department"/></label>
            <input type="text" class="form-control" value="${counseling.studentUser.division.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <%--<div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.major"/></label>
            <input type="text" class="form-control" value="${counseling.studentUser.major.name}" disabled/>
            &lt;%&ndash;<span class="form-text text-muted">We'll never share your email with anyone else</span>&ndash;%&gt;
        </div>
    </div>--%>
    <div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.place"/></label>
            <input type="text" class="form-control" value="${counseling.place}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-3">


    </div>
</div>
<div class="row">



    <div class="col-md-3">


    </div>
</div>
<div class="row">

    <div class="col-md-12">

        <div class="form-group">
            <label><spring:message code="professor.consultingContents"/></label>
            <textarea class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" disabled>${counseling.contents}</textarea>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
</div>
<div class="row">

    <div class="col-md-12">

        <div class="form-group">
            <label><spring:message code="professor.suggestions"/></label>
            <textarea class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" disabled>${counseling.suggestions}</textarea>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
</div>