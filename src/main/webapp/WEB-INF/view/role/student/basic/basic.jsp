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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.student.sub2_1"/></h3>
                        </div>
                        <div class="card-body">

                            <div class="detail-div">
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

                                    <div class="col-md-3" style="text-align:center">
                                        <br/>
                                        <c:choose>
                                            <c:when test="${not empty studentUser.profile}">
                                                <img src="${baseUrl}/download?uploadedFileId=${studentUser.profile.id}" style="max-width:100px"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${resources}/images/user.png" style="max-width:100px"/>
                                            </c:otherwise>
                                        </c:choose>

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
                                            <input type="text" class="form-control"  value="${studentUser.advisor.getFullName()}" disabled/>
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
                                <br/>
                                <br/>

                                <ul class="nav nav-tabs nav-tabs-line">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="common.profile"/></a>
                                    </li>

                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_3"><spring:message code="common.language"/></a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_4"><spring:message code="common.certificate"/></a>
                                    </li>

                                </ul>
                                <div class="tab-content mt-5" id="myTabContent">
                                    <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_1">
                                        <%@include file="/WEB-INF/view/role/student/basic/studentDetailProfile.jsp" %>
                                    </div>

                                    <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
                                        <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailLanguage.jsp" %>
                                    </div>
                                    <div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel" aria-labelledby="kt_tab_pane_4">
                                        <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailCertificate.jsp" %>
                                    </div>
                                </div>
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