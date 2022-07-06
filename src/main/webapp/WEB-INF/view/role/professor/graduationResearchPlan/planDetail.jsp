<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%--<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold">
        <spring:message code="common.print"/>
    </a>
</div>--%>
<form:form modelAttribute="stored" class="form">

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
                <c:set var="approveStatus" value="-"/>
                <c:choose>
                    <c:when test="${stored.approve eq 1}">
                        <c:set var="approveStatus" value="Y"/>
                    </c:when>
                    <c:when test="${stored.approve eq 0}">
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
                        <spring:message code="common.status"/>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${stored.approve eq -1}">
                                <span style="color:red">
                                <spring:message code="professor.graduateResearchPlan.status.returned"/>
                                    </span>
                            </c:when>
                            <c:when test="${stored.approve eq 0}">
                                <spring:message code="professor.graduateResearchPlan.status.submitted"/>
                            </c:when>
                            <c:when test="${stored.approve eq 1}">
                            <span style="color:green">
                                <spring:message code="professor.graduateResearchPlan.status.approved"/>
                            </span>
                            </c:when>
                        </c:choose>
                    </td>


                </tr>
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
                        <form:textarea path="scope" class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" disabled="true"></form:textarea>
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
                        <form:textarea path="ref" class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" disabled="true"></form:textarea>
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
                <tr>
                    <td>
                    </td>
                    <td style="text-align:right">
                        <c:if test="${stored.approve ne 1}">
                        <button type="button" class="btn btn-primary mr-2 plan-btn" data-plan-id="${stored.id}" data-type="1"><spring:message code="common.approve"/></button>
                        <c:if test="${stored.approve ne -1}">
                        <button type="button" class="btn btn-primary mr-2 plan-btn" data-plan-id="${stored.id}" data-type="-1"><spring:message code="common.return"/></button>
                        </c:if>
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
    </div>


</form:form>


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
        $(".plan-btn").click(function(e) {
            e.preventDefault();
            var type = $(this).attr("data-type");
            var planId = $(this).attr("data-plan-id");
            $.post("${baseUrl}/professor/classProgress/graduationResearchPlan/planDetail?planId=" + planId + "&type=" + type, function() {
              alert("<spring:message code="common.success"/>");
              location.reload();
            });

        });

    });
</script>