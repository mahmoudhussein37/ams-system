<%@include file="/WEB-INF/view/include/topTag.jsp" %>


<form:form modelAttribute="studentUser" action="${baseUrl}/admin/studentManagement/schoolYear/studentDetail" method="post" class="form">
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
                <form:input type="text" class="form-control"  path="username" disabled="true"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.firstName"/></label>
                <form:input type="text" class="form-control"  path="contact.firstName" disabled="true"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.lastName"/></label>
                <form:input type="text" class="form-control"  path="contact.lastName" disabled="true"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>


    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.department"/></label>
                <form:select path="divisionId" class="form-control"  disabled="true">
                    <c:forEach var="division" items="${divisions}">
                        <form:option value="${division.id}">${division.name}</form:option>
                    </c:forEach>
                </form:select>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>

        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.status"/></label>
                <form:select path="status" class="form-control" disabled="true">
                    <c:forEach var="s" items="${statusList}">
                        <form:option value="${s.name()}"><spring:message code="student.status.${s.name()}"/></form:option>
                    </c:forEach>

                </form:select>
                    <%--<input type="text" class="form-control" value="${studentUser.status}" disabled/>--%>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.advisor"/></label>
                <form:select path="advisorId" class="form-control"  disabled="true">
                    <c:forEach var="s" items="${professors}">
                        <form:option value="${s.id}">${s.contact.getFullName()} (${s.division.name})</form:option>
                    </c:forEach>
                </form:select>
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