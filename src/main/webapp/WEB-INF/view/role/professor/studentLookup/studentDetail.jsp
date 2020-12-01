<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold print">
        <spring:message code="common.print"/>
    </a>
</div>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
<%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailBasic.jsp" %>
<br/>
<div class="separator separator-solid my-5"></div>
<br/>

<ul class="nav nav-tabs nav-tabs-line">
    <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="common.profile"/></a>
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
        <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailProfile.jsp" %>
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
    $("body").on('click', '.print', function (e) {
        e.preventDefault();
        openPage("${baseUrl}/professor/studentGuidance/studentLookup/studentDetail?print=true&studentId=${studentUser.id}");
    });
</script>