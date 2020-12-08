<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<%--<link href="${resources}/vendor/metronic_assets_7/assets/css/pages/error/error-1.css" rel="stylesheet" type="text/css"/>--%>
<!--end::Head-->

<!--begin::Body-->
<body  id="kt_body"  class="header-fixed header-mobile-fixed subheader-enabled page-loading"  >

<!--begin::Main-->
<div class="d-flex flex-column flex-root">
    <!--begin::Error-->
    <div class="d-flex flex-row-fluid flex-column bgi-size-cover bgi-position-center bgi-no-repeat p-10 p-sm-30" style="text-align:center">
        <br/><br/><br/><br/><br/><br/><br/><br/>
        
        <!--begin::Content-->
        <h1 class="font-weight-boldest text-dark-75 mt-15" style="font-size: 10rem">500</h1>
        <p class="font-size-h3 text-muted font-weight-normal">
            <spring:message code="common.error.msg"/>
        </p>
        <!--end::Content-->
    </div>
    <!--end::Error-->
</div>
<!--end::Main-->

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>