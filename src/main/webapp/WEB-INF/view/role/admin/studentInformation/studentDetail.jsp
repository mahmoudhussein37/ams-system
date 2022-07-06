<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold print">
        <spring:message code="common.print"/>
    </a>
</div>

<form:form modelAttribute="studentUser" action="${baseUrl}/admin/studentManagement/studentInformation/studentDetail" method="post" class="form" enctype="multipart/form-data">
    <div id="print-area">
        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
        <div class="row">
            <div class="col-md-3" style="text-align:center">
                <c:choose>
                    <c:when test="${not empty studentUser.profile}">
                        <img src="${baseUrl}/download?uploadedFileId=${studentUser.profile.id}" style="max-width:100px"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${resources}/images/user.png" style="max-width:100px"/>
                    </c:otherwise>
                </c:choose>
                <br/><br/>
                <input type="file" name="file"/>
            </div>
        </div>
        <br/><br/>
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
                    <form:input type="text" class="form-control"  path="username"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.firstName"/></label>
                    <form:input type="text" class="form-control"  path="contact.firstName"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.lastName"/></label>
                    <form:input type="text" class="form-control"  path="contact.lastName"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>

            </div>


        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.department"/></label>
                    <form:select path="divisionId" class="form-control" style="">
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
                    <form:select path="status" class="form-control" >
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
                    <form:select path="advisorId" class="form-control" >
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
        <br/>
        <div class="separator separator-solid my-5"></div>
        <br/>

        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.graduationInformation"/></h3>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.graduationYear"/></label>
                    <form:input path="contact.gradYear" type="text" class="form-control"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
            <div class="col-md-3">

                <div class="form-group">
                    <label><spring:message code="common.graduationSemester"/></label>
                    <form:input path="contact.gradSemester" type="text" class="form-control"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.graduationDate"/></label>
                    <form:input path="contact.gradDate" type="text" class="form-control" id="grad-date-picker"  readonly="true"/>
                        <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.graduationDegree"/></label>
                    <form:input path="contact.gradDegree" type="text" class="form-control"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label><spring:message code="common.degreeNumber"/></label>
                    <form:input path="contact.degreeNumber" type="text" class="form-control"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>
            </div>
            <div class="col-md-3">

                <div class="form-group">
                    <label><spring:message code="common.certificateNumber"/></label>
                    <form:input path="contact.certNumber" type="text" class="form-control"/>
                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                </div>
            </div>
        </div>
    </div>
    <br/>
    <div class="separator separator-solid my-5"></div>
    <br/>

    <ul class="nav nav-tabs nav-tabs-line">
        <li class="nav-item">
            <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="common.profile"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2"><spring:message code="common.courseHistory"/></a>
        </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_3"><spring:message code="common.language"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_4"><spring:message code="common.certificate"/></a>
            </li>

    </ul>
    <div class="tab-content mt-5" id="myTabContent">
        <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_1">
            <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailProfile.jsp" %>
        </div>
        <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
            <%@include file="/WEB-INF/view/role/common/studentProfile/studentDetailCourseHistory.jsp" %>
        </div>
            <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
                <%@include file="/WEB-INF/view/role/admin/studentProfile/studentDetailLanguage.jsp" %>
            </div>
            <div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel" aria-labelledby="kt_tab_pane_4">
                <%@include file="/WEB-INF/view/role/admin/studentProfile/studentDetailCertificate.jsp" %>
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
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/studentManagement/studentInformation";
        </c:if>
        $(".delete-lang-cert").click(function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/studentManagement/studentInformation/deleteLangCert?langCertId=" + id, function(result) {
                alert("<spring:message code="common.success"/>");
                location.reload();
            });
        });
        $("body").on('click', '.print', function (e) {
            e.preventDefault();
            location.href="${baseUrl}/admin/studentManagement/studentInformation/studentDetailForPrint?studentId=${studentUser.id}";

        });
    });
</script>