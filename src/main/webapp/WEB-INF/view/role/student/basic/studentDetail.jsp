<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
<div class="row">
    <div class="col-md-2">

        <div class="form-group">
            <label><spring:message code="common.studentNumber"/></label>
            <input type="text" class="form-control" value="${studentUser.number}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-2">
        <div class="form-group">
            <label><spring:message code="common.name"/></label>
            <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-2">
        <div class="form-group">
            <label><spring:message code="common.department"/></label>
            <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>

    <div class="col-md-2">
    </div>
    <div class="col-md-2" style="text-align:center">
        <img src="${resources}/images/user.png" style="max-width:100px"/> <br/><br/>
        <input type="file"/>
    </div>

</div>
<div class="row">
    <div class="col-md-2">

        <div class="form-group">
            <label><spring:message code="common.status"/></label>
            <input type="text" class="form-control" value="${studentUser.status}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-2">
        <div class="form-group">
            <label><spring:message code="common.advisor"/></label>
            <input type="text" class="form-control"  value="${studentUser.advisor}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-2">
        <div class="form-group">
            <label><spring:message code="common.schoolYear"/></label>
            <input type="text" class="form-control" value="${studentUser.schoolYear}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">


    </div>
</div>
<br/>
<div class="separator separator-solid my-5"></div>
<br/>

<ul class="nav nav-tabs nav-tabs-line">
    <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="common.profile"/></a>
    </li>

    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_3"><spring:message code="common.language"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_4"><spring:message code="common.certificate"/></a>
    </li>

</ul>
<div class="tab-content mt-5" id="myTabContent">
    <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_1">
        <%@include file="/WEB-INF/view/role/student/basic/studentDetailProfile.jsp" %>
    </div>

    <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
        <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailLanguage.jsp" %>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel" aria-labelledby="kt_tab_pane_4">
        <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailCertificate.jsp" %>
    </div>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>