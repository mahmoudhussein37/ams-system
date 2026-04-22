<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<h3 class="card-title font-weight-bolder"><spring:message code="menu.professor.sub1_2_2"/></h3>
<form:form class="form" modelAttribute="counseling" action="${baseUrl}/professor/studentGuidance/counseling/newCounselingDetail?studentId=${studentId}" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="card-body">

        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.year"/></label>
                    <form:input type="number" class="form-control" path="year" value="${currentYear}"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.date"/></label>
                    <form:input type="text" class="form-control date-picker" path="date" id="kt_datepicker_1" readonly="true"/>
                </div>

            </div>

        </div>
        <div class="row">

            <div class="col-md-3">

                <div class="form-group">
                    <label><spring:message code="common.studentsName"/></label>
                    <form:hidden path="studentUserId"/>
                    <input type="text" class="form-control" name="studentUserName" value="${counseling.studentUser.contact.getFullName()}" disabled/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

            <div class="col-md-3">

                <div class="form-group">
                    <label><spring:message code="common.place"/></label>
                    <form:input type="text" class="form-control" path="place"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
            <div class="col-md-3">


            </div>
        </div>
        <div class="row">

            <div class="col-md-12">

                <div class="form-group">
                    <label><spring:message code="professor.consultingContents"/></label>
                    <form:textarea class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" path="contents"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
        </div>
        <div class="row">

            <div class="col-md-12">

                <div class="form-group">
                    <label><spring:message code="professor.suggestions"/></label>
                    <form:textarea class="form-control" dir="${isRTL ? 'rtl' : 'ltr'}" rows="6" path="suggestions"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
        </div>

    </div>
    <div class="card-footer">
        <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.register"/></button>
            <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
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
    });
</script>