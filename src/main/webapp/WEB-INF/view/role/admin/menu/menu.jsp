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
<form:form modelAttribute="menuAccess" action="${baseUrl}/admin/systemManagement/menu" method="post" class="form">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <table class="table table-head-custom table-vertical-center" id="course-list">
                                    <thead>
                                    <tr class="table-secondary text-center">
                                        <th style=""><spring:message code="admin.menu"/></th>
                                        <th style=""><spring:message code="common.active"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr class="text-center">

                                        <td class="pl-0">
                                            Register grade
                                        </td>
                                        <td class="pl-0">
                                            <form:select path="grade" class="form-control">
                                                <form:option value="true">Y</form:option>
                                                <form:option value="false">N</form:option>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr class="text-center">

                                        <td class="pl-0">
                                            Course assessment
                                        </td>
                                        <td class="pl-0">
                                            <form:select path="assessment" class="form-control">
                                                <form:option value="true">Y</form:option>
                                                <form:option value="false">N</form:option>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr class="text-center">

                                        <td class="pl-0">
                                            Syllabus
                                        </td>
                                        <td class="pl-0">
                                            <form:select path="syllabus" class="form-control">
                                                <form:option value="true">Y</form:option>
                                                <form:option value="false">N</form:option>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr class="text-center">

                                        <td class="pl-0">
                                            CQI
                                        </td>
                                        <td class="pl-0">
                                            <form:select path="cqi" class="form-control">
                                                <form:option value="true">Y</form:option>
                                                <form:option value="false">N</form:option>
                                            </form:select>
                                        </td>
                                    </tr>

                                    </tbody>
                                </table>

                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group" style="text-align:right">
                                        <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
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
    $(document).ready(function() {
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success' javaScriptEscape="true" />");
        location.href="${baseUrl}/admin/systemManagement/menu";
        </c:if>
    });
</script>
</body>
</html>