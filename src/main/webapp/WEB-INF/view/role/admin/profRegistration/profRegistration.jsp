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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub2_1"/></h3>

                        </div>
                        <div class="card-body">
                            <form:form modelAttribute="profUser" method="post">
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.profNumber"/></label>
                                            <form:input type="text" path="number" class="form-control"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.division"/></label>
                                            <form:select id="search-division" path="divisionId" class="form-control" >
                                                <c:forEach var="division" items="${divisions}">
                                                    <option value="${division.id}">${division.name}</option>
                                                </c:forEach>
                                            </form:select>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.major"/></label>
                                            <form:select id="search-major" path="majorId" class="form-control" >
                                                <c:forEach var="major" items="${majors}">
                                                    <option value="${major.id}">${major.name}</option>
                                                </c:forEach>

                                            </form:select>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.enteringYear"/></label>
                                            <form:input type="number" path="contact.admissionYear" class="form-control"/>
                                                <%--<select class="form-control" >
                                                    <c:forEach var="y" begin="2010" end="2030">
                                                        <option value="${y}">${y}</option>
                                                    </c:forEach>

                                                </select>--%>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.enteringDate"/></label>
                                            <form:input type="text" path="contact.admissionDate" class="form-control" id="kt_datepicker_1" readonly="true" />
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.status"/></label>
                                            <form:input type="text" path="status" class="form-control"/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                    <div class="col-md-3">


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
            $('#kt_datepicker_1').datepicker({
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
        KTBootstrapDatepicker.init();
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/profManagement/profRegistration";
        </c:if>
    });
</script>
</body>
</html>