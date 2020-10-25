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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub5_1"/></h3>

                        </div>
                        <div class="card-body">

                            <div class="table-div">
                                <table class="table table-head-custom table-vertical-center" id="course-list">
                                    <thead>
                                    <tr class="text-uppercase">

                                        <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                        <th style=""><span class="text-primary"><spring:message code="common.year"/></span></th>
                                        <th style=""><span class="text-primary"><spring:message code="common.semester"/></span></th>
                                        <th style=""><span class="text-primary"></span>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>

                                        <td class="pl-0">
                                            1
                                        </td>
                                        <td class="pl-0">
                                            2020
                                        </td>
                                        <td>

                                            1

                                        </td>

                                        <td>
                                            <a href="#" class="course-detail">
                                                <i class="far fa-file-alt"></i>
                                            </a>
                                        </td>
                                    </tr>


                                    </tbody>
                                </table>

                            </div>
                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>

                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createYearSemester"/></h3>
                            <div class="row">
                                <div class="col-md-3">

                                    <div class="form-group">
                                        <label><spring:message code="common.year"/></label>
                                        <input type="number" name="year" class="form-control"/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                                <div class="col-md-3">

                                    <div class="form-group">
                                        <label><spring:message code="common.semester"/></label>
                                        <input type="number" name="semester" class="form-control"/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>

                            </div>
                            <br/>
                            <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.register"/></button>


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

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>


<%@include file="/WEB-INF/view/include/userPanel.jsp" %>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>
    $(document).ready(function() {
    });
</script>
</body>
</html>