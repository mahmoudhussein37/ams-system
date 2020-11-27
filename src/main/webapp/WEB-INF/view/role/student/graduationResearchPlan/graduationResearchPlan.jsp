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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.student.sub5_2"/></h3>
                        </div>
<form:form modelAttribute="stored" class="form">
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
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.completeSemester"/></label>
                                        <input type="text" class="form-control" value="" disabled/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
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
                                        <label><spring:message code="common.academicYear"/></label>
                                        <input type="text" class="form-control" value="${studentUser.contact.admissionYear}" disabled/>
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
                            <div class="row">

                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.approve"/></label>
                                        <input type="text" class="form-control" value="${stored.enabled ? 'Y' : 'N'}" disabled/>
                                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/>

                            <div class="row">
                                <div class="col-md-12">
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.researchPlan"/></h3>
                                    <table class="table">

                                        <tr>
                                            <td>
                                                <spring:message code="professor.researchTitle"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="title" class="form-control" disabled="true"/>
                                            </td>


                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.outcomeType"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="type" class="form-control" disabled="true"/>
                                            </td>


                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.studyPeriod"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="startDate" class="form-control date-picker" style="width:49%;display:inline" disabled="true"/> ~

                                                <form:input type="text" path="endDate" class="form-control date-picker" style="width:49%;display:inline" disabled="true"/>

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.studyPurpose"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="purpose" class="form-control" disabled="true"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.scopeOfStudy"/>
                                            </td>
                                            <td>
                                                <form:textarea path="scope" class="form-control" dir="rtl" rows="6" disabled="true"></form:textarea>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.processAndMethodOfResearch"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="method" class="form-control" disabled="true"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.projectImplementation"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="impl" class="form-control" disabled="true"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="common.schedule"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="schedule" class="form-control" disabled="true"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.references"/>
                                            </td>
                                            <td>
                                                <form:textarea path="ref" class="form-control" dir="rtl" rows="6" disabled="true"></form:textarea>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.etc"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="etc" class="form-control" disabled="true"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.dateOfSubmission"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="submitDate" class="form-control date-picker" disabled="true"/>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>

                        </div>

</form:form>
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
    <c:if test="${not empty result}">
    alert("<spring:message code='common.success'/>");
    location.href="${baseUrl}/student/graduation/graduationResearchPlan";
    </c:if>
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
        KTBootstrapDatepicker.init();

    });
</script>

</body>
</html>