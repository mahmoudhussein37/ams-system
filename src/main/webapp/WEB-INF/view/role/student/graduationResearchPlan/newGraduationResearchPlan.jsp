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
                        <form:form modelAttribute="graduationResearchPlan" action="${baseUrl}/student/graduation/graduationResearchPlan" method="post" class="form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
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
                                        <input type="text" class="form-control" value="${completeSemester}" disabled/>
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
                                        <c:choose>
                                            <c:when test="${studentUser.advisorId gt 0 and not empty studentUser.advisor}">
                                                <input type="text" class="form-control" value="${studentUser.advisor.getFullName()}" disabled/>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" class="form-control" value="<spring:message code="common.notAssigned"/>" disabled/>
                                            </c:otherwise>
                                        </c:choose>
                                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label><spring:message code="common.admissionYear"/></label>
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
                                        <c:set var="approveStatus" value="-"/>
                                        <c:choose>
                                            <c:when test="${stored.approve eq 1}">
                                                <c:set var="approveStatus" value="Y"/>
                                            </c:when>
                                            <c:when test="${stored.approve eq -1}">
                                                <c:set var="approveStatus" value="N"/>
                                            </c:when>
                                        </c:choose>
                                        <input type="text" class="form-control" value="${approveStatus}" disabled/>
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
                                                <form:input type="text" path="title" class="form-control"/>
                                            </td>


                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.outcomeType"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="type" class="form-control"/>
                                            </td>


                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.studyPeriod"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="startDate" class="form-control date-picker" style="width:49%;display:inline"/> ~

                                                <form:input type="text" path="endDate" class="form-control date-picker" style="width:49%;display:inline"/>

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.studyPurpose"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="purpose" class="form-control"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.scopeOfStudy"/>
                                            </td>
                                            <td>
                                                <textarea name="scope" class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" ></textarea>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.processAndMethodOfResearch"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="method" class="form-control"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.projectImplementation"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="impl" class="form-control"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="common.schedule"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="schedule" class="form-control"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.references"/>
                                            </td>
                                            <td>
                                                <textarea name="ref" class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" ></textarea>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.etc"/>
                                            </td>
                                            <td>
                                                <form:input type="text" path="etc" class="form-control"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <spring:message code="professor.dateOfSubmission"/>
                                            </td>
                                            <td>
                                                <input type="text" name="submitDate" class="form-control date-picker"/>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>

                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.submit"/></button>
                        </div>
                    </div>
                    </form:form>
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
        KTBootstrapDatepicker.init();

    });
</script>

</body>
</html>
