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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub5_8"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="table-div">
                                <table class="table table-head-custom table-vertical-center" id="course-list">
                                    <thead>
                                    <tr class="table-secondary text-center">
                                        <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                        <th style=""><spring:message code="admin.menu"/></th>
                                        <th style=""><spring:message code="common.active"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr class="text-center">
                                        <td class="pl-0">
                                            1
                                        </td>
                                        <td class="pl-0">
                                            Course enrolment
                                        </td>
                                        <td class="pl-0">
                                            <select class="form-control">
                                                <option value="true">Y</option>
                                                <option value="true">N</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr class="text-center">
                                        <td class="pl-0">
                                            2
                                        </td>
                                        <td class="pl-0">
                                            Course assessment
                                        </td>
                                        <td class="pl-0">
                                            <select class="form-control">
                                                <option value="true">Y</option>
                                                <option value="true">N</option>
                                            </select>
                                        </td>
                                    </tr>

                                    </tbody>
                                </table>
                            </div>


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
    $(document).ready(function() {
    });
</script>
</body>
</html>