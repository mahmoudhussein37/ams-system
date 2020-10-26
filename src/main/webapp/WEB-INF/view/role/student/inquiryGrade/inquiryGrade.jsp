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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.student.sub4_1"/></h3>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-3">

                                    <div class="form-group">
                                        <label><spring:message code="common.studentNumber"/></label>
                                        <input type="text" class="form-control" value="${studentUser.number}" disabled/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>

                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.division"/></label>
                                        <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
                                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.name"/></label>
                                        <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
                                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.major"/></label>
                                        <input type="text" class="form-control" value="${studentUser.major.name}" disabled/>
                                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.status"/></label>
                                        <input type="text" class="form-control" value="<spring:message code="student.status.${studentUser.status}"/>" disabled/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.advisor"/></label>
                                        <input type="text" class="form-control" value="AAA" disabled/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.schoolYear"/></label>
                                        <input type="text" class="form-control" value="${studentUser.schoolYear}" disabled/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/>
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="student.semesterGrade"/></h3>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <table class="table rounded">
                                            <thead>
                                            <tr class="table-secondary text-center">

                                                <td><spring:message code="common.no"/></td>
                                                <td>
                                                    <spring:message code="common.year"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.semester"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.schoolYear"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.apply"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.complete"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.score"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.grade"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.percentile"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.warning"/>
                                                </td>

                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr class="table-light text-center">
                                                <td>1</td>
                                                <td>
                                                    2011
                                                </td>
                                                <td>
                                                    2
                                                </td>
                                                <td>
                                                    4
                                                </td>
                                                <td>
                                                    17
                                                </td>
                                                <td>
                                                    17
                                                </td>
                                                <td>
                                                    72.5
                                                </td>
                                                <td>
                                                    4.26
                                                </td>
                                                <td>
                                                    87.12
                                                </td>
                                                <td>
                                                </td>
                                            </tr>


                                            </tbody>
                                        </table>


                                    </div>
                                </div>
                            </div>
                            <div class="detail-div">

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
        $(".detail-div").load("${baseUrl}/student/grades/inquiryGrade/gradeDetail");
    });
</script>
</body>
</html>