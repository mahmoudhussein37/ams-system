<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.basicInformation"/></h3>
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
            <label><spring:message code="common.division"/></label>
            <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.major"/></label>
            <input type="text" class="form-control" value="${studentUser.major.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.status"/></label>
            <input type="text" class="form-control" value="${studentUser.status}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.advisor"/></label>
            <input type="text" class="form-control"  value="${studentUser.advisor}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.academicYear"/></label>
            <input type="text" class="form-control" value="" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.approve"/></label>
            <input type="text" class="form-control" value="" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
</div>
<div class="row">
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.completeSemester"/></label>
            <input type="text" class="form-control" value="" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.schoolYear"/></label>
            <input type="text" class="form-control" value="${studentUser.schoolYear}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">


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
                <spring:message code="professor.researchPlan"/>
                </td>
                <td>
                    <input type="text" class="form-control" disabled/>
                </td>


            </tr>
            <tr>
                <td>
                    <spring:message code="professor.outcomeType"/>
                </td>
                <td>
                    <input type="text" class="form-control" disabled/>
                </td>


            </tr>
            <tr>
                <td>
                    <spring:message code="professor.studyPeriod"/>
                </td>
                <td>
                    <input type="text" class="form-control date-picker" style="width:49%;display:inline"/> ~

                    <input type="text" class="form-control date-picker" style="width:49%;display:inline"/>

                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="professor.studyPurpose"/>
                </td>
                <td>
                    <input type="text" class="form-control" disabled/>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="professor.scopeOfStudy"/>
                </td>
                <td>
                    <textarea rows="5" class="form-control"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="professor.processAndMethodOfResearch"/>
                </td>
                <td>
                    <input type="text" class="form-control" disabled/>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="professor.projectImplementation"/>
                </td>
                <td>
                    <input type="text" class="form-control" disabled/>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="common.schedule"/>
                </td>
                <td>
                    <input type="text" class="form-control" disabled/>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="professor.references"/>
                </td>
                <td>
                    <textarea rows="5" class="form-control"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="professor.etc"/>
                </td>
                <td>
                    <input type="text" class="form-control" disabled/>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="professor.dateOfSubmission"/>
                </td>
                <td>
                    <input type="text" class="form-control date-picker"/>
                </td>
            </tr>
        </table>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="form-group" style="text-align:right">
            <button type="button" class="btn btn-secondary mr-2"><spring:message code="common.return"/></button>
            <button type="button" class="btn btn-primary mr-2"><spring:message code="common.approve"/></button>
        </div>
    </div>
</div>

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