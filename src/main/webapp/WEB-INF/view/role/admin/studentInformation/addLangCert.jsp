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
                            <h3 class="card-title font-weight-bolder"><a class="btn btn-light" href="${baseUrl}/admin/studentManagement/studentProfile"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a></h3>

                        </div>
                        <div class="card-body">

                            <form:form modelAttribute="langCert" action="${baseUrl}/admin/studentManagement/studentInformation/addLangCert?studentId=${studentUser.id}" method="post" class="form">
                                    <form:hidden path="userId" value="${studentUser.id}"/>

                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.addLangCert"/></h3>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.type"/></label>
                                                <form:select path="type" class="form-control">
                                                    <form:option value="lang"><spring:message code="common.language"/></form:option>
                                                    <form:option value="cert"><spring:message code="common.certificate"/></form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">

                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.langCertName"/></label>
                                                <form:input type="text" path="lType" class="form-control"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.level"/></label>
                                                <form:input type="text" path="level" class="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.agency"/></label>
                                                <form:input type="text" path="agency" class="form-control"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.date"/></label>
                                                <form:input type="text" path="date" class="form-control date-picker"/>
                                            </div>
                                        </div>
                                    </div>

                                    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.create"/></button>
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

    var KTBootstrapDatepicker = function () {

        var arrows;
        if (KTUtil.isRTL()) {
            arrows = {
                leftArrow: '<i class="la la-angle-right"></i>',
                rightArrow: '<i class="la la-angle-left"></i>'
            }
        } else {
            arrows = {
                leftArrow: '<i class="la la-angle-left"></i>',
                rightArrow: '<i class="la la-angle-right"></i>'
            }
        }

        // Private functions
        var demos = function () {
            // minimum setup
            $('.date-picker').datepicker({
                rtl: KTUtil.isRTL(),
                todayHighlight: true,
                orientation: "bottom left",
                templates: arrows,
                format: 'yyyy-mm-dd',
            }).on('changeDate', function(e){
                $(this).datepicker('hide');
            })


        }

        return {
            // public functions
            init: function() {
                demos();
            }
        };
    }();
    $(document).ready(function() {
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success' javaScriptEscape="true" />");
        location.href="${baseUrl}/admin/studentManagement/studentInformation";
        </c:if>
        KTBootstrapDatepicker.init();
    });

</script>
</body>
</html>