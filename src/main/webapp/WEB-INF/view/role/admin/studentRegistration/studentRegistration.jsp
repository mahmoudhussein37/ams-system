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
                                                <spring:message code="menu.admin.sub1_1" />
                                            </h3>

                                        </div>
                                        <div class="card-body">
                                            <%-- Option 1: Import Students by Excel --%>
                                                <h3 class="font-size-lg text-dark font-weight-bold mb-6">(
                                                    <spring:message code="common.option" /> 1)
                                                    <spring:message code="admin.importStudentsByExcel" />
                                                </h3>


                                                <%-- Result Message Area (populated by JavaScript from URL params) --%>
                                                    <div id="import-result-area"></div>

                                                    <div class="card card-custom card-border shadow-sm mb-8">
                                                        <div class="card-body">
                                                            <form id="import-form"
                                                                action="${baseUrl}/admin/studentManagement/studentRegistration/importStudents"
                                                                method="post" enctype="multipart/form-data">
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                    value="${_csrf.token}" />
                                                                <div class="row mb-4">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label>
                                                                                <spring:message
                                                                                    code="common.department" /> <span
                                                                                    class="text-danger">*</span>
                                                                            </label>
                                                                            <select id="import-division"
                                                                                name="divisionId" class="form-control"
                                                                                required>
                                                                                <option value="">--
                                                                                    <spring:message
                                                                                        code="common.department" /> --
                                                                                </option>
                                                                                <c:forEach var="division"
                                                                                    items="${divisions}">
                                                                                    <option value="${division.id}">
                                                                                        ${division.name}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label>
                                                                                <spring:message
                                                                                    code="common.schoolYear" /> <span
                                                                                    class="text-danger">*</span>
                                                                            </label>
                                                                            <select id="import-schoolYear"
                                                                                name="schoolYear" class="form-control"
                                                                                required>
                                                                                <option value="">--
                                                                                    <spring:message
                                                                                        code="common.schoolYear" /> --
                                                                                </option>
                                                                                <c:forEach var="d" begin="1" end="4">
                                                                                    <option value="${d}">${d}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row mb-4">
                                                                    <div class="col-md-12">
                                                                        <div class="form-group">
                                                                            <label>
                                                                                <spring:message code="common.file" />
                                                                                <span class="text-danger">*</span>
                                                                            </label>
                                                                            <input type="file" id="import-file"
                                                                                name="file" class="form-control"
                                                                                accept=".xlsx" required />
                                                                            <span class="form-text text-muted">
                                                                                <spring:message
                                                                                    code="admin.importInfo" />
                                                                            </span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-12" style="text-align:right">
                                                                        <a href="${resources}/form/student_import_template.xlsx"
                                                                            class="btn btn-light btn-sm mr-2" download>
                                                                            <i class="fa fa-download"></i>
                                                                            <spring:message
                                                                                code="admin.downloadTemplate" />
                                                                        </a>
                                                                        <button type="submit"
                                                                            class="btn btn-primary btn-sm"
                                                                            id="btn-import">
                                                                            <i class="fa fa-upload"></i>
                                                                            <spring:message
                                                                                code="admin.uploadFilledExcel" />
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>

                                                    <%-- Separator --%>
                                                        <div class="separator separator-solid my-7"></div>

                                                        <%-- Option 2: Manual Student Registration --%>
                                                            <h3 class="font-size-lg text-dark font-weight-bold mb-6">(
                                                                <spring:message code="common.option" /> 2)
                                                                <spring:message
                                                                    code="admin.manualStudentRegistration" />
                                                            </h3>

                                                            <form:form modelAttribute="studentUser" method="post"
                                                                id="manualRegForm">
                                                                <p class="text-muted mb-4"><small>
                                                                        <spring:message code="admin.requiredFields" />
                                                                    </small></p>

                                                                <!-- Row 1: Name & Student Number (Required) -->
                                                                <div class="row mb-4">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message
                                                                                    code="common.firstName" />
                                                                                <span class="text-danger">*</span>
                                                                            </label>
                                                                            <form:input type="text"
                                                                                path="contact.firstName"
                                                                                class="form-control" required="required"
                                                                                placeholder="..." />
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message
                                                                                    code="common.lastName" />
                                                                                <span class="text-danger">*</span>
                                                                            </label>
                                                                            <form:input type="text"
                                                                                path="contact.lastName"
                                                                                class="form-control" required="required"
                                                                                placeholder="..." />
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message
                                                                                    code="common.studentNumber" />
                                                                                <span class="text-danger">*</span>
                                                                            </label>
                                                                            <form:input type="text" path="number"
                                                                                class="form-control" required="required"
                                                                                placeholder="..." />
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <!-- Row 2: Department, School Year, Status -->
                                                                <div class="row mb-4">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message
                                                                                    code="common.department" />
                                                                            </label>
                                                                            <form:select id="search-division"
                                                                                path="divisionId" class="form-control">
                                                                                <c:forEach var="division"
                                                                                    items="${divisions}">
                                                                                    <option value="${division.id}">
                                                                                        ${division.name}</option>
                                                                                </c:forEach>
                                                                            </form:select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message
                                                                                    code="common.schoolYear" />
                                                                            </label>
                                                                            <form:select path="schoolYear"
                                                                                class="form-control">
                                                                                <c:forEach var="d" begin="1" end="5">
                                                                                    <form:option value="${d}">${d}
                                                                                    </form:option>
                                                                                </c:forEach>
                                                                            </form:select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message code="common.status" />
                                                                            </label>
                                                                            <form:select path="status"
                                                                                class="form-control">
                                                                                <c:forEach var="s"
                                                                                    items="${statusList}">
                                                                                    <option value="${s.name()}">
                                                                                        <spring:message
                                                                                            code="student.status.${s.name()}" />
                                                                                    </option>
                                                                                </c:forEach>
                                                                            </form:select>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <!-- Row 3: Admission Info & Advisor -->
                                                                <div class="row mb-4">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message
                                                                                    code="common.admissionYear" />
                                                                            </label>
                                                                            <form:input type="number"
                                                                                path="contact.admissionYear"
                                                                                class="form-control"
                                                                                placeholder="2024" />
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message
                                                                                    code="common.admissionDate" />
                                                                            </label>
                                                                            <form:input type="text"
                                                                                path="contact.admissionDate"
                                                                                id="admission-date" class="form-control"
                                                                                readonly="true"
                                                                                placeholder="Select date" />
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group mb-0">
                                                                            <label class="font-weight-bold">
                                                                                <spring:message code="common.advisor" />
                                                                            </label>
                                                                            <form:select path="advisorId"
                                                                                class="form-control">
                                                                                <form:option value="0">-- Select Advisor --</form:option>
                                                                                <c:forEach var="s"
                                                                                    items="${professors}">
                                                                                    <option value="${s.id}">
                                                                                        ${s.contact.getFullName()}
                                                                                        (${s.division.name})
                                                                                    </option>
                                                                                </c:forEach>
                                                                            </form:select>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <!-- Submit Button -->
                                                                <div class="row">
                                                                    <div class="col-md-12 text-right">
                                                                        <button type="submit" class="btn btn-primary">
                                                                            <i class="fa fa-user-plus mr-1"></i>
                                                                            <spring:message code="common.register" />
                                                                        </button>
                                                                    </div>
                                                                </div>

                                                            </form:form>


                                        </div>
                                        <%--<div class="card-footer">

                                    </div>--%>
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
                                        $('#admission-date').datepicker({
                                            rtl: KTUtil.isRTL(),
                                            todayHighlight: true,
                                            orientation: "bottom left",
                                            templates: arrows,
                                            format: 'yyyy-mm-dd',
                                        }).on('changeDate', function (e) {
                                            $(this).datepicker('hide');
                                        })


                                    }

                                    return {
                                        // public functions
                                        init: function () {
                                            demos();
                                        }
                                    };
                                }();
                                $(document).ready(function () {
                                    KTBootstrapDatepicker.init();

                                    // Handle import result from URL parameters
                                    var urlParams = new URLSearchParams(window.location.search);
                                    var importResult = urlParams.get('importResult');
                                    var inserted = urlParams.get('inserted');
                                    var duplicates = urlParams.get('duplicates');
                                    var invalid = urlParams.get('invalid');
                                    var errorMessage = urlParams.get('errorMessage');
                                    // Detailed invalid reasons
                                    var missingSN = urlParams.get('sn');
                                    var missingFN = urlParams.get('fn');
                                    var missingLN = urlParams.get('ln');

                                    if (importResult) {
                                        var alertClass = 'danger';
                                        var message = '';

                                        // Build invalid details string
                                        var invalidDetails = '';
                                        if (missingSN && parseInt(missingSN) > 0) {
                                            invalidDetails += '<spring:message code="admin.import.missing.studentNumber" javaScriptEscape="true" />: ' + missingSN;
                                        }
                                        if (missingFN && parseInt(missingFN) > 0) {
                                            if (invalidDetails) invalidDetails += ', ';
                                            invalidDetails += '<spring:message code="admin.import.missing.firstName" javaScriptEscape="true" />: ' + missingFN;
                                        }
                                        if (missingLN && parseInt(missingLN) > 0) {
                                            if (invalidDetails) invalidDetails += ', ';
                                            invalidDetails += '<spring:message code="admin.import.missing.lastName" javaScriptEscape="true" />: ' + missingLN;
                                        }


                                        if (importResult === 'success') {
                                            alertClass = 'success';
                                            message = '<strong><spring:message code="common.success" javaScriptEscape="true" />!</strong> ';
                                            message += '<spring:message code="admin.import.success" javaScriptEscape="true" />'.replace('{0}', inserted);
                                            // Show warnings for duplicates/invalid if any
                                            var warnings = [];
                                            if (duplicates && parseInt(duplicates) > 0) {
                                                warnings.push('<spring:message code="admin.import.duplicatesSkipped" javaScriptEscape="true" />: ' + duplicates);
                                            }
                                            if (invalid && parseInt(invalid) > 0) {
                                                var invalidMsg = '<spring:message code="admin.import.invalidRowsSkipped" javaScriptEscape="true" />: ' + invalid;
                                                if (invalidDetails) {
                                                    invalidMsg += ' (' + invalidDetails + ')';
                                                }
                                                warnings.push(invalidMsg);
                                            }
                                            if (warnings.length > 0) {
                                                message += '<br><small class="text-dark font-weight-bold">' + warnings.join(' | ') + '</small>';
                                            }
                                        } else if (importResult === 'warning') {
                                            alertClass = 'warning';
                                            message = '<strong><spring:message code="common.warning" javaScriptEscape="true" />!</strong> ';
                                            message += '<spring:message code="admin.import.allDuplicates" javaScriptEscape="true" /> (' + duplicates + ')';
                                        } else {
                                            // error
                                            message = '<strong><spring:message code="common.error.exception" javaScriptEscape="true" />!</strong> ';
                                            if (errorMessage === 'empty_file') {
                                                message += '<spring:message code="admin.import.error.emptyFile" javaScriptEscape="true" />';
                                            } else if (errorMessage === 'invalid_format') {
                                                message += '<spring:message code="admin.import.error.invalidFormat" javaScriptEscape="true" />';
                                            } else if (errorMessage === 'no_data') {
                                                message += '<spring:message code="admin.import.error.noData" javaScriptEscape="true" />';
                                            } else if (errorMessage === 'all_invalid') {
                                                message += '<spring:message code="admin.import.error.allInvalid" javaScriptEscape="true" />'.replace('{0}', invalid);
                                                if (invalidDetails) {
                                                    message += '<br><small>' + invalidDetails + '</small>';
                                                }
                                            } else {
                                                message += '<spring:message code="admin.import.error.processing" javaScriptEscape="true" />';
                                            }
                                        }


                                        var alertHtml = '<div class="alert alert-' + alertClass + ' alert-dismissible fade show" role="alert">';
                                        alertHtml += message;
                                        alertHtml += '<button type="button" class="close" data-dismiss="alert" aria-label="Close">';
                                        alertHtml += '<span aria-hidden="true">&times;</span></button></div>';

                                        $('#import-result-area').html(alertHtml);

                                        // Clean URL without reload
                                        if (window.history.replaceState) {
                                            var cleanUrl = window.location.href.split('?')[0];
                                            window.history.replaceState({}, document.title, cleanUrl);
                                        }
                                    }


                                    < c:if test="${not empty result}" >
                                        <c:choose>
                                            <c:when test="${result eq 'success'}">
                                                alert("<spring:message code='common.success' javaScriptEscape="true" />");
                                            </c:when>
                                            <c:otherwise>
                                                alert("<spring:message code='admin.duplicatedStudentNumber' javaScriptEscape="true" />");
                                            </c:otherwise>
                                        </c:choose>
                                        location.href = "${baseUrl}/admin/studentManagement/studentRegistration";
                                    </c:if>



                                });

                            </script>
        </body>

        </html>
