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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub1_1"/></h3>

                        </div>
                        <div class="card-body">
                            <form:form modelAttribute="studentUser" method="post">
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.studentNumber"/></label>
                                            <form:input type="text" path="number" class="form-control"/>
                                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.department"/></label>
                                            <form:select id="search-division" path="divisionId" class="form-control" >
                                                <c:forEach var="division" items="${divisions}">
                                                    <option value="${division.id}">${division.name}</option>
                                                </c:forEach>
                                            </form:select>
                                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                    <%--<div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.major"/></label>
                                            <form:select id="search-major" path="majorId" class="form-control" >

                                            </form:select>
                                            &lt;%&ndash;<span class="form-text text-muted">We'll never share your email with anyone else</span>&ndash;%&gt;
                                        </div>
                                    </div>--%>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.admissionYear"/></label>
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
                                            <label><spring:message code="common.admissionDate"/></label>
                                            <form:input type="text" path="contact.admissionDate" id="admission-date" class="form-control" readonly="true" />
                                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.advisor"/></label>
                                            <form:select path="advisorId" class="form-control" >
                                                <c:forEach var="s" items="${professors}">
                                                    <option value="${s.id}">${s.contact.getFullName()} (${s.division.name})</option>
                                                </c:forEach>
                                            </form:select>
                                            <%--<form:input type="text" path="advisor" class="form-control"/>--%>
                                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.status"/></label>
                                            <form:select path="status" class="form-control" >
                                                <c:forEach var="s" items="${statusList}">
                                                    <option value="${s.name()}"><spring:message code="student.status.${s.name()}"/></option>
                                                </c:forEach>
                                            </form:select>

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
                        <%--<div class="card-footer">

                        </div>--%>
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
            $('#admission-date').datepicker({
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
        <c:choose>
        <c:when test="${result eq 'success'}">
        alert("<spring:message code='common.success'/>");
        </c:when>
        <c:otherwise>
        alert("<spring:message code='admin.duplicatedStudentNumber'/>");
        </c:otherwise>
        </c:choose>
        location.href="${baseUrl}/admin/studentManagement/studentRegistration";
        </c:if>


        //changeMajor("#search-division", "#search-major", true);

    });
</script>
</body>
</html>