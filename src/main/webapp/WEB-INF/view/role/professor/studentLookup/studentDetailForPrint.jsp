<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<!--begin::Content-->
<div class="content  d-flex flex-column flex-column-fluid" id="kt_content">
    <!--begin::Entry-->
    <div class="d-flex flex-column-fluid">
        <!--begin::Container-->
        <div class=" container ">
            <div class="row">
                <div class="col-md-12">
                    <!--begin::Card-->
                    <div class="card card-custom">
                        <div class="card-header">
                        </div>
                        <div class="card-body">
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
                            <div class="card-body">
                            <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailBasic.jsp" %>
                            </div>

                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.profile"/></h3>
                            <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailProfile.jsp" %>
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.courseHistory"/></h3>
                            <%@include file="/WEB-INF/view/role/professor/studentLookup/studentDetailCourseHistory.jsp" %>
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.language"/></h3>
                            <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailLanguage.jsp" %>
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.certificate"/></h3>
                            <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailCertificate.jsp" %>



                            <br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/>

                        </div>
                    </div>
                    <!--end::Card-->
                </div>
            </div>


        </div>
        <!--end::Container-->
    </div>
    <!--end::Entry-->
</div>
<!--end::Content-->



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
    window.print();
    window.onafterprint = function(){
        window.close();
        location.href="${baseUrl}/professor/studentGuidance/studentLookup";
    }

</script>