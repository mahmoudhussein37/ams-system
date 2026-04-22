<%@include file="/WEB-INF/view/include/topTag.jsp" %>


<form:form modelAttribute="studentSchoolYearForm" action="${baseUrl}/admin/studentManagement/schoolYear/studentDetail" method="post" class="form">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <form:hidden path="id"/>
    <div id="print-area">
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
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
                <label><spring:message code="common.username"/></label>
                <input type="text" class="form-control" value="${studentUser.username}" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.firstName"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.firstName}" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.lastName"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.lastName}" disabled/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>


    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.department"/></label>
                <select class="form-control" disabled>
                    <c:forEach var="division" items="${divisions}">
                        <option value="${division.id}" ${division.id eq studentUser.divisionId ? 'selected' : ''}>${division.name}</option>
                    </c:forEach>
                </select>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.status"/></label>
                <select class="form-control" disabled>
                    <c:forEach var="s" items="${statusList}">
                        <option value="${s.name()}" ${s.name() eq studentUser.status ? 'selected' : ''}><spring:message code="student.status.${s.name()}"/></option>
                    </c:forEach>

                </select>
                    <%--<input type="text" class="form-control" value="${studentUser.status}" disabled/>--%>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.advisor"/></label>
                <select class="form-control" disabled>
                    <option value="0">Not Assigned</option>
                    <c:forEach var="s" items="${professors}">
                        <option value="${s.id}" ${s.id eq studentUser.advisorId ? 'selected' : ''}>${s.contact.getFullName()} (${s.division.name})</option>
                    </c:forEach>
                </select>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.schoolYear"/></label>
                    <form:select path="schoolYear" class="form-control" >
                    <c:forEach var="s" begin="1" end="4">
                        <form:option value="${s}">${s}</form:option>
                    </c:forEach>
                </form:select>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">


        </div>
    </div>

    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
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
            $('#grad-date-picker').datepicker({
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
        alert("<spring:message code='common.success' javaScriptEscape="true" />");
        location.href="${baseUrl}/admin/studentManagement/studentInformation";
        </c:if>
    });
</script>
