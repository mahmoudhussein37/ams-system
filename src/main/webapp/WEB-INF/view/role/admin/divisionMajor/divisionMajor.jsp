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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub5_2"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="table-div">
                            </div>
                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <form:form modelAttribute="division" action="${baseUrl}/admin/systemManagement/createDivision" method="post">
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createDivision"/></h3>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.name"/></label>
                                            <form:input type="text" path="name" class="form-control"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>

                                </div>
                                <br/>
                                <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.register"/></button>


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
        $(".table-div").load("${baseUrl}/admin/systemManagement/divisionMajor/divisionTable");

        <c:if test="${not empty result}">
        alert("<spring:message code='common.success' javaScriptEscape="true" />");
        location.href="${baseUrl}/admin/systemManagement/divisionMajor";
        </c:if>
    });
</script>
</body>
</html>