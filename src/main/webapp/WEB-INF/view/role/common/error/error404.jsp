<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<link href="${resources}/vendor/metronic_assets_7/assets/css/pages/error/error-6.css" rel="stylesheet" type="text/css"/>
<!--end::Head-->

<!--begin::Body-->
<body  id="kt_body"  class="header-fixed header-mobile-fixed subheader-enabled page-loading"  >

<!--begin::Main-->
<div class="d-flex flex-column flex-root">
    <!--begin::Error-->
    <div class="error error-6 d-flex flex-row-fluid bgi-size-cover bgi-position-center" style="background-image: url(${resources}/vendor/metronic_assets_7/assets/media/error/bg6.jpg);">
        <!--begin::Content-->
        <div class="d-flex flex-column flex-row-fluid text-center">
            <h1 class="error-title font-weight-boldest text-white mb-12" style="margin-top: 12rem;">Oops...</h1>
            <p class="display-4 font-weight-bold text-white">
                Looks like something went wrong.</br>
                We're working on it
            </p>
        </div>
        <!--end::Content-->
    </div>
    <!--end::Error-->
</div>
<!--end::Main-->

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>