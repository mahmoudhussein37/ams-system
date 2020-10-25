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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub5_7"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="table-div">
                                <table class="table table-head-custom table-vertical-center" id="course-list">
                                    <thead>
                                    <tr class="table-secondary text-center">
                                        <th class="pl-0" style=""></th>
                                        <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                        <th style=""><spring:message code="admin.classroomCode"/></th>
                                        <th style=""><spring:message code="admin.classroomName"/></th>
                                        <th style=""><spring:message code="common.use"/></th>
                                        <th style=""></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr class="text-center">
                                        <td class="pl-0">
                                            <input type="checkbox" name=""/>
                                        </td>
                                        <td class="pl-0">
                                            B308
                                        </td>
                                        <td class="pl-0">
                                            B308
                                        </td>


                                        <td>
                                            Y
                                        </td>
                                        <td>
                                        </td>
                                    </tr>


                                    </tbody>
                                </table>
                            </div>

                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>

                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createClassroom"/></h3>
                            <div class="row">
                                <div class="col-md-3">

                                    <div class="form-group">
                                        <label><spring:message code="admin.classroomCode"/></label>
                                        <input type="number" name="year" class="form-control"/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                                <div class="col-md-3">

                                    <div class="form-group">
                                        <label><spring:message code="admin.classroomName"/></label>
                                        <input type="number" name="semester" class="form-control"/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                                <div class="col-md-3">

                                    <div class="form-group">
                                        <label><spring:message code="common.use"/></label>
                                        <select class="form-control">
                                            <option value="true">Y</option>
                                            <option value="false">N</option>
                                        </select>

                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.register"/></button>

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