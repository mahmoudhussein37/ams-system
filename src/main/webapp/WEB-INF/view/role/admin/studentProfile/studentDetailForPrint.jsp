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
                            <c:forEach var="studentUser" items="${studentList}">
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
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
                                            <label><spring:message code="common.name"/></label>
                                            <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.department"/></label>
                                            <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                        <%--<div class="col-md-3">

                                            <div class="form-group">
                                                <label><spring:message code="common.major"/></label>
                                                <input type="text" class="form-control" value="${studentUser.major.name}" disabled/>
                                                &lt;%&ndash;<span class="form-text text-muted">We'll never share your email with anyone else</span>&ndash;%&gt;
                                            </div>
                                        </div>--%>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.status"/></label>
                                            <input type="text" class="form-control" value="${studentUser.status}" disabled/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.advisor"/></label>
                                            <input type="text" class="form-control"  value="${advisor.contact.getFullName()}" disabled/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.schoolYear"/></label>
                                            <input type="text" class="form-control" value="${studentUser.schoolYear}" disabled/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                    <div class="col-md-3">


                                    </div>
                                </div>

                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.profile"/></h3>
                                <%@include file="/WEB-INF/view/role/admin/studentProfile/studentDetailProfile.jsp" %>
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.courseHistory"/></h3>
                                <%@include file="/WEB-INF/view/role/admin/studentProfile/studentDetailCourseHistory.jsp" %>
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.language"/></h3>
                                <%@include file="/WEB-INF/view/role/admin/studentProfile/studentDetailLanguage.jsp" %>
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.certificate"/></h3>
                                <%@include file="/WEB-INF/view/role/admin/studentProfile/studentDetailCertificate.jsp" %>



                                <br/>
                                <div class="separator separator-solid my-5"></div>
                                <br/>
                            </c:forEach>

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