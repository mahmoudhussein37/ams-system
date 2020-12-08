<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading">
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

                        <div class="card-body">
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.course.lectureFundamentals"/></h3>
                            <%@include file="/WEB-INF/view/role/common/syllabus/courseDetailFundamentals.jsp" %>

                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.course.lectureMethod"/></h3>

                            <%@include file="/WEB-INF/view/role/common/syllabus/courseDetailMethod.jsp" %>
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.course.lectureContents"/></h3>

                            <%@include file="/WEB-INF/view/role/common/syllabus/courseDetailContents.jsp" %>


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
        }

</script>