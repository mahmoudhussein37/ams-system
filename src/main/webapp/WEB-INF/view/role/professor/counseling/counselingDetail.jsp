<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold">
        <spring:message code="common.print"/>
    </a>
</div>

<ul class="nav nav-tabs nav-tabs-line">
    <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="menu.professor.sub1_2"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2"><spring:message code="common.courseHistory"/></a>
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
                    <input type="text" class="form-control" value="" disabled/>
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
                    <textarea class="form-control" rows="6" disabled></textarea>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
        </div>
        <div class="row">

            <div class="col-md-12">

                <div class="form-group">
                    <label><spring:message code="professor.suggestions"/></label>
                    <textarea class="form-control" rows="6" disabled></textarea>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
        </div>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
        <%@include file="/WEB-INF/view/role/professor/studentLookup/studentDetailCourseHistory.jsp" %>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
        <%@include file="/WEB-INF/view/role/professor/studentLookup/studentDetailLanguage.jsp" %>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel" aria-labelledby="kt_tab_pane_4">
        <%@include file="/WEB-INF/view/role/professor/studentLookup/studentDetailCertificate.jsp" %>
    </div>
</div>








<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>