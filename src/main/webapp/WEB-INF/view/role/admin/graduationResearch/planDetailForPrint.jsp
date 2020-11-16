<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading">
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

                        <div class="card-body">
                            <c:forEach var="stored" items="${planList}">

                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.studentNumber"/></label>
                                            <input type="text" class="form-control" value="${stored.user.number}" disabled/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>

                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.name"/></label>
                                            <input type="text" class="form-control"  value="${stored.user.getFullName()}" disabled/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.department"/></label>
                                            <input type="text" class="form-control" value="${stored.user.division.name}" disabled/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>
                                    </div>

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
                                            <input type="text" class="form-control" value="<spring:message code="student.status.${stored.user.status}"/>" disabled/>
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
                                            <input type="text" class="form-control" value="${stored.user.contact.admissionYear}" disabled/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.schoolYear"/></label>
                                            <input type="text" class="form-control" value="${stored.user.schoolYear}" disabled/>
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
                                                    <input type="text" value="${stored.title}" class="form-control" disabled/>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.outcomeType"/>
                                                </td>
                                                <td>
                                                    <input type="text" value="${stored.type}" class="form-control" disabled/>
                                                </td>


                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.studyPeriod"/>
                                                </td>
                                                <td>
                                                    <input type="text" value="${stored.startDate}" class="form-control date-picker" style="width:49%;display:inline" disabled/> ~

                                                    <input type="text" value="${stored.endDate}" class="form-control date-picker" style="width:49%;display:inline" disabled/>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.studyPurpose"/>
                                                </td>
                                                <td>
                                                    <input type="text" value="${stored.purpose}" class="form-control" disabled/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.scopeOfStudy"/>
                                                </td>
                                                <td>
                                                    <textarea class="form-control" dir="rtl" rows="6" disabled>${stored.scope}</textarea>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.processAndMethodOfResearch"/>
                                                </td>
                                                <td>
                                                    <input type="text" value="${stored.method}" class="form-control" disabled/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.projectImplementation"/>
                                                </td>
                                                <td>
                                                    <input type="text" value="${stored.impl}" class="form-control" disabled/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="common.schedule"/>
                                                </td>
                                                <td>
                                                    <input type="text" value="${stored.schedule}" class="form-control" disabled/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.references"/>
                                                </td>
                                                <td>
                                                    <textarea class="form-control" dir="rtl" rows="6" disabled>${stored.ref}</textarea>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.etc"/>
                                                </td>
                                                <td>
                                                    <input type="text" value="${stored.etc}" class="form-control" disabled/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="professor.dateOfSubmission"/>
                                                </td>
                                                <td>
                                                    <input type="text" value="${stored.submitDate}" class="form-control date-picker" disabled/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>



                                <br/>
                                <div class="separator separator-solid my-5"></div>
                                <br/>
                            </c:forEach>

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



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
    window.print();

</script>