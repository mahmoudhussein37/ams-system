<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>
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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub3_1_2"/></h3>

                        </div>
                        <div class="card-body">


                            <form:form id="file-form" commandName="uploadedFile" action="${baseUrl}/admin//courseManagement/curriculum/uploadCurriculum?year=${year}&divisionId=${divisionId}" cssClass="form-horizontal" enctype="multipart/form-data">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label><spring:message code="professor.uploadFile"/></label>
                                            <input type="file" name="file" class="form-control"/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>


                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group" style="text-align:right">
                                            <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.upload"/></button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
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

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>


<%@include file="/WEB-INF/view/include/userPanel.jsp" %>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>

    function search() {
        var year = $("#search-year").val().trim();
        location.href="${baseUrl}/admin/courseManagement/curriculum?year=" + year;

        /*$(".table-div").load("${baseUrl}/admin/courseManagement/curriculum/courseTable?year=" + year + "&division=" + division);*/
    }



    $(document).ready(function() {
        /*$(".table-div").load("${baseUrl}/admin/courseManagement/curriculum/courseTable");*/

    });

</script>
</body>
</html>