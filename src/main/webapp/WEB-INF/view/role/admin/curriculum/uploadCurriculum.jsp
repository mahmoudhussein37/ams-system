<%@include file="/WEB-INF/view/include/topTag.jsp" %>
    <%@include file="/WEB-INF/view/include/head.jsp" %>
        <!--end::Head-->

        <!--begin::Body-->

        <body id="kt_body" class="header-fixed header-mobile-fixed page-loading">
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
                                            <h3 class="card-title font-weight-bolder">
                                                <c:choose>
                                                    <c:when test="${curriculumExists}">
                                                        <spring:message code="curriculum.updateCurriculum" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <spring:message code="curriculum.uploadCurriculum" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </h3>
                                        </div>
                                        <%-- Guard: reject invalid context --%>
                                            <c:if
                                                test="${empty divisionId or empty year or divisionId <= 0 or year <= 0}">
                                                <div class="card-body">
                                                    <div class="alert alert-danger mb-0">
                                                        <spring:message code="curriculum.invalidContext" />
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if
                                                test="${not empty divisionId and not empty year and divisionId > 0 and year > 0}">
                                                <div class="card-body">
                                                    <%-- Common header: division + year + state --%>
                                                        <div class="mb-6">
                                                            <div class="d-flex align-items-center mb-3">
                                                                <span class="font-weight-bold mr-2">
                                                                    <spring:message code="common.department" />:
                                                                </span>
                                                                <span>
                                                                    <c:out value="${divisionName}" default="—" />
                                                                </span>
                                                            </div>
                                                            <div class="d-flex align-items-center mb-3">
                                                                <span class="font-weight-bold mr-2">
                                                                    <spring:message code="common.year" />:
                                                                </span>
                                                                <span>${year}</span>
                                                            </div>
                                                            <div class="d-flex align-items-center mb-4">
                                                                <c:choose>
                                                                    <c:when test="${curriculumExists}">
                                                                        <span class="badge badge-success">
                                                                            <spring:message
                                                                                code="curriculum.state.hasFile" />
                                                                        </span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-secondary">
                                                                            <spring:message
                                                                                code="curriculum.state.noFile" />
                                                                        </span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>

                                                        <%-- Description --%>
                                                            <p class="text-muted mb-5">
                                                                <c:choose>
                                                                    <c:when test="${curriculumExists}">
                                                                        <spring:message
                                                                            code="curriculum.updateCurriculum" />
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <spring:message
                                                                            code="curriculum.uploadCurriculum" />
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </p>

                                                            <%-- Current file panel (Update mode only) --%>
                                                                <c:if test="${curriculumExists}">
                                                                    <div class="card card-body bg-light-primary mb-5">
                                                                        <h6 class="font-weight-bold mb-3">
                                                                            <spring:message
                                                                                code="curriculum.update.currentFile" />
                                                                        </h6>
                                                                        <div class="d-flex flex-column">
                                                                            <div class="mb-1">
                                                                                <span class="text-muted mr-2">
                                                                                    <spring:message
                                                                                        code="common.fileName" />:
                                                                                </span>
                                                                                <span class="font-weight-bold">
                                                                                    <c:out value="${currentFileName}"
                                                                                        default="—" />
                                                                                </span>
                                                                            </div>
                                                                            <div class="mb-1">
                                                                                <span class="text-muted mr-2">
                                                                                    <spring:message
                                                                                        code="common.uploadedBy" />:
                                                                                </span>
                                                                                <span>
                                                                                    <c:out value="${uploaderName}"
                                                                                        default="—" />
                                                                                </span>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:if>

                                                                <%-- Route to correct endpoint based on mode --%>
                                                                    <%-- Route to correct endpoint based on curriculum
                                                                        existence --%>
                                                                        <c:choose>
                                                                            <c:when test="${curriculumExists}">
                                                                                <c:set var="formAction"
                                                                                    value="${baseUrl}/admin/courseManagement/curriculum/update" />
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:set var="formAction"
                                                                                    value="${baseUrl}/admin/courseManagement/curriculum/upload" />
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <form:form id="file-form"
                                                                            commandName="uploadedFile"
                                                                            action="${formAction}?year=${year}&divisionId=${divisionId}"
                                                                            cssClass="form-horizontal"
                                                                            enctype="multipart/form-data">
                                                                            <input type="hidden"
                                                                                name="${_csrf.parameterName}"
                                                                                value="${_csrf.token}" />
                                                                            <input type="hidden" name="divisionId"
                                                                                value="${divisionId}" />
                                                                            <input type="hidden" name="academicYear"
                                                                                value="${year}" />

                                                                            <div class="form-group">
                                                                                <label class="font-weight-bold">
                                                                                    <spring:message
                                                                                        code="professor.uploadFile" />
                                                                                </label>
                                                                                <input type="file" name="file"
                                                                                    class="form-control" required />
                                                                                <small class="form-text text-muted">
                                                                                    <spring:message
                                                                                        code="curriculum.acceptedFormats" />
                                                                                </small>
                                                                            </div>

                                                                            <%-- Replace note (Update mode only) --%>
                                                                                <c:if test="${curriculumExists}">
                                                                                    <div
                                                                                        class="alert alert-warning py-3 mb-5">
                                                                                        <i
                                                                                            class="flaticon-warning mr-2"></i>
                                                                                        <spring:message
                                                                                            code="curriculum.update.replaceNote" />
                                                                                    </div>
                                                                                </c:if>

                                                                                <div
                                                                                    class="form-group d-flex justify-content-end mt-5">
                                                                                    <a href="${baseUrl}/admin/courseManagement/curriculum?year=${year}"
                                                                                        class="btn btn-secondary mr-3">
                                                                                        <spring:message
                                                                                            code="common.cancel" />
                                                                                    </a>
                                                                                    <c:choose>
                                                                                        <c:when
                                                                                            test="${curriculumExists}">
                                                                                            <button type="submit"
                                                                                                class="btn btn-warning">
                                                                                                <spring:message
                                                                                                    code="curriculum.updateCurriculum" />
                                                                                            </button>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <button type="submit"
                                                                                                class="btn btn-primary">
                                                                                                <spring:message
                                                                                                    code="curriculum.uploadCurriculum" />
                                                                                            </button>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </div>
                                                                        </form:form>
                                                </div>
                                            </c:if>
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

                <%@include file="/WEB-INF/view/include/footerBar.jsp" %>


                    <%@include file="/WEB-INF/view/include/userPanel.jsp" %>


                        <%@include file="/WEB-INF/view/include/footerScript.jsp" %>

                            <script>
                                $(document).ready(function () {
                                });
                            </script>
        </body>

        </html>