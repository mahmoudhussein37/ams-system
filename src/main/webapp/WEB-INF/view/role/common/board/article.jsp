<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>


<%--<sec:authorize access="!hasRole('ROLE_ADMIN')">
    <style>

        .header-bottom {
            display:none !important;
        }
        @media (min-width: 992px) {
            .header-fixed .header {
                height:70px;
            }
            .header-fixed .wrapper {
                padding-top: 100px;
            }
        }

        @media (max-width: 991.98px) {

            .header-mobile-fixed .wrapper {
                padding-top: 70px;
            }
        }
    </style>
</sec:authorize>--%>
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



                            <h3 class="card-title font-weight-bolder">

                                <%@include file="/WEB-INF/view/role/common/board/boardName.jsp" %>
                            </h3>

                        </div>
                        <div class="card-body">
                            <form:form modelAttribute="article" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="row">
                                    <div class="col-md-12">

                                        <div class="form-group">
                                            <label><spring:message code="admin.board.subject"/></label>
                                            <form:input type="text" path="subject" disabled="true" class="form-control"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>


                                </div>
                                <div class="row">
                                    <div class="col-md-12">

                                        <div class="form-group">
                                            <label><spring:message code="admin.board.contents"/></label>
                                            <form:textarea path="content" rows="6" disabled="true"  class="form-control"/>
                                        </div>
                                    </div>


                                </div>

                                <br/>

                                <a href="/board/${boardName}/list" class="btn btn-primary mr-2"><spring:message code="common.list"/></a>
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

    $(document).ready(function() {

    });
</script>
</body>