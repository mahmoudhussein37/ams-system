<%@include file="/WEB-INF/view/include/topTag.jsp" %>
    <%@include file="/WEB-INF/view/include/head.jsp" %>
        <!--end::Head-->

        <!--begin::Body-->

        <body id="kt_body" class="header-fixed header-mobile-fixed page-loading">
            <%@include file="/WEB-INF/view/include/headerBar.jsp" %>
                <!--begin::Content-->
                <div class="content d-flex flex-column flex-column-fluid" id="kt_content">
                    <!--begin::Entry-->
                    <div class="d-flex flex-column-fluid">

                        <!--begin::Container-->
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12">
                                    <!--begin::Card-->
                                    <div class="card card-custom">
                                        <div class="card-header">
                                            <h3 class="card-title font-weight-bolder">
                                                <a class="btn btn-light"
                                                    href="${baseUrl}/admin/courseManagement/cOpen/manageDivide?courseId=${profCourse.courseId}">
                                                    <i class="fa fa-arrow-left"></i>
                                                    <spring:message code="common.back" />
                                                </a>
                                                &nbsp;&nbsp;&nbsp;
                                                [${profCourse.semester.year} - ${profCourse.semester.semester}]
                                                ${profCourse.course.code}: ${profCourse.course.title} -
                                                <spring:message code="common.divide" />: ${profCourse.divide}
                                            </h3>
                                        </div>
                                        <div class="card-body">

                                            <!-- ====================================== -->
                                            <!-- REGISTER STUDENTS SECTION (NEW UI) -->
                                            <!-- ====================================== -->
                                            <h3 class="font-size-lg text-dark font-weight-bold mb-6">
                                                <i class="fa fa-user-plus text-primary mr-2"></i>
                                                <spring:message code="admin.registerStudents" />
                                            </h3>

                                            <!-- Filter Row: Division + School Year + Load Students -->
                                            <div class="card card-custom card-border shadow-sm mb-6">
                                                <div class="card-body py-4">
                                                    <div class="row align-items-end">
                                                        <div class="col-md-4">
                                                            <div class="form-group mb-0">
                                                                <label class="font-weight-bold">
                                                                    <spring:message code="common.department" />
                                                                    <span class="text-danger">*</span>
                                                                </label>
                                                                <select id="filter-division" class="form-control"
                                                                    required>
                                                                    <option value="">
                                                                        <spring:message code="common.select" />...
                                                                    </option>
                                                                    <c:forEach var="division" items="${divisions}">
                                                                        <option value="${division.id}">${division.name}
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <div class="form-group mb-0">
                                                                <label class="font-weight-bold">
                                                                    <spring:message code="common.schoolYear" />
                                                                    <span class="text-danger">*</span>
                                                                </label>
                                                                <select id="filter-schoolYear" class="form-control"
                                                                    required>
                                                                    <option value="">
                                                                        <spring:message code="common.select" />...
                                                                    </option>
                                                                    <c:forEach var="y" begin="1" end="5">
                                                                        <option value="${y}">${y}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <button type="button" id="btn-load-students"
                                                                class="btn btn-primary btn-block"
                                                                onclick="loadEligibleStudents()">
                                                                <i class="fa fa-search mr-1"></i>
                                                                <spring:message code="admin.loadStudents" />
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Eligible Students Table (loaded via AJAX) -->
                                            <div id="eligible-students-container" style="display: none;">
                                                <div class="d-flex justify-content-between align-items-center mb-4">
                                                    <h5 class="font-weight-bold text-dark mb-0">
                                                        <spring:message code="admin.eligibleStudents" />
                                                        <span id="eligible-count"
                                                            class="badge badge-primary ml-2">0</span>
                                                    </h5>
                                                    <div>
                                                        <button type="button" id="btn-register-selected"
                                                            class="btn btn-success mr-2"
                                                            onclick="registerSelectedStudents()" disabled>
                                                            <i class="fa fa-check mr-1"></i>
                                                            <spring:message code="admin.registerSelected" />
                                                        </button>
                                                        <button type="button" id="btn-clear-selection"
                                                            class="btn btn-secondary" onclick="clearSelection()">
                                                            <i class="fa fa-times mr-1"></i>
                                                            <spring:message code="common.clearSelection" />
                                                        </button>
                                                    </div>
                                                </div>

                                                <div class="table-responsive">
                                                    <table class="table table-head-custom table-vertical-center"
                                                        id="eligible-students-table">
                                                        <thead>
                                                            <tr class="text-uppercase">
                                                                <th style="width: 40px;">
                                                                    <label class="checkbox checkbox-single">
                                                                        <input type="checkbox"
                                                                            id="select-all-students" />
                                                                        <span></span>
                                                                    </label>
                                                                </th>
                                                                <th><span class="text-primary">
                                                                        <spring:message code="common.studentNumber" />
                                                                    </span></th>
                                                                <th><span class="text-primary">
                                                                        <spring:message code="common.name" />
                                                                    </span></th>
                                                                <th><span class="text-primary">
                                                                        <spring:message code="common.accountStatus" />
                                                                    </span></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="eligible-students-body">
                                                            <!-- Filled via JavaScript -->
                                                            <tr id="no-students-message">
                                                                <td colspan="4" class="text-center text-muted py-5">
                                                                    <i class="fa fa-info-circle mr-2"></i>
                                                                    <spring:message code="admin.selectFiltersToLoad" />
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>

                                            <!-- Separator -->
                                            <div class="separator separator-solid my-7"></div>

                                            <!-- ====================================== -->
                                            <!-- REGISTERED STUDENTS SECTION -->
                                            <!-- ====================================== -->
                                            <div class="detail-div">
                                                <h3 class="font-size-lg text-dark font-weight-bold mb-6">
                                                    <i class="fa fa-users text-success mr-2"></i>
                                                    <spring:message code="admin.registeredStudents" />
                                                    <span
                                                        class="badge badge-success ml-2">${fn:length(studentCourseList)}</span>
                                                </h3>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="table-responsive">
                                                            <table class="table table-head-custom table-vertical-center"
                                                                id="student-list">
                                                                <thead>
                                                                    <tr class="text-uppercase">
                                                                        <th class="pl-0">
                                                                            <spring:message code="common.no" />
                                                                        </th>
                                                                        <th><span class="text-primary">
                                                                                <spring:message
                                                                                    code="common.studentNumber" />
                                                                            </span></th>
                                                                        <th><span class="text-primary">
                                                                                <spring:message code="common.name" />
                                                                            </span></th>
                                                                        <th><span class="text-primary">
                                                                                <spring:message
                                                                                    code="common.schoolYear" />
                                                                            </span></th>
                                                                        <th><span class="text-primary">
                                                                                <spring:message
                                                                                    code="common.accountStatus" />
                                                                            </span></th>
                                                                        <th></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach var="stCourse"
                                                                        items="${studentCourseList}"
                                                                        varStatus="varStatus">
                                                                        <tr>
                                                                            <td class="pl-0">${varStatus.count}</td>
                                                                            <td>${stCourse.studentUser.number}</td>
                                                                            <td>${stCourse.studentUser.contact.getFullName()}
                                                                            </td>
                                                                            <td>${stCourse.studentUser.schoolYear}</td>
                                                                            <td>
                                                                                <c:choose>
                                                                                    <c:when
                                                                                        test="${stCourse.studentUser.accountState == 'ACTIVE'}">
                                                                                        <span
                                                                                            class="label label-success label-inline font-weight-bold">
                                                                                            <spring:message
                                                                                                code="student.status.active" />
                                                                                        </span>
                                                                                    </c:when>
                                                                                    <c:when
                                                                                        test="${stCourse.studentUser.accountState == 'DISABLED'}">
                                                                                        <span
                                                                                            class="label label-danger label-inline font-weight-bold">
                                                                                            <spring:message
                                                                                                code="student.status.disabled" />
                                                                                        </span>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <span
                                                                                            class="label label-warning label-inline font-weight-bold">
                                                                                            <spring:message
                                                                                                code="student.status.pending" />
                                                                                        </span>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </td>
                                                                            <td>
                                                                                <button
                                                                                    class="btn btn-light-danger btn-sm remove-from-btn"
                                                                                    data-id="${stCourse.studentUser.id}">
                                                                                    <i class="fa fa-trash-alt"></i>
                                                                                    <spring:message
                                                                                        code="admin.removeFromDivide" />
                                                                                </button>
                                                                            </td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                    <c:if test="${empty studentCourseList}">
                                                                        <tr>
                                                                            <td colspan="6"
                                                                                class="text-center text-muted py-5">
                                                                                <i class="fa fa-info-circle mr-2"></i>
                                                                                <spring:message
                                                                                    code="admin.noRegisteredStudents" />
                                                                            </td>
                                                                        </tr>
                                                                    </c:if>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

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
                                // Track selected students
                                var selectedStudents = [];

                                /**
                                 * Load eligible students based on Division + School Year filters
                                 */
                                function loadEligibleStudents() {
                                    var divisionId = $('#filter-division').val();
                                    var schoolYear = $('#filter-schoolYear').val();

                                    // Validate both filters are selected
                                    if (!divisionId || !schoolYear) {
                                        alert('<spring:message code="admin.selectBothFilters" javaScriptEscape="true" />');
                                        return;
                                    }

                                    // Clear previous selections
                                    clearSelection();

                                    // Show loading state
                                    $('#btn-load-students').prop('disabled', true).html('<i class="fa fa-spinner fa-spin mr-1"></i> <spring:message code="common.loading" javaScriptEscape="true" />...');

                                    // Show the container
                                    $('#eligible-students-container').show();

                                    // AJAX call to load eligible students
                                    $.ajax({
                                        url: '${baseUrl}/admin/courseManagement/cOpen/loadEligibleStudents',
                                        method: 'GET',
                                        data: {
                                            divisionId: divisionId,
                                            schoolYear: schoolYear,
                                            profCourseId: ${ profCourseId }
                                        },
                                dataType: 'json',
                                    success: function(response) {
                                        if (response.success && response.students && response.students.length > 0) {
                                            var html = '';
                                            $.each(response.students, function (index, student) {
                                                var statusBadge;
                                                if (student.accountState === 'ACTIVE') {
                                                    statusBadge = '<span class="label label-success label-inline font-weight-bold"><spring:message code="student.status.active" javaScriptEscape="true" /></span>';
                                                } else if (student.accountState === 'DISABLED') {
                                                    statusBadge = '<span class="label label-danger label-inline font-weight-bold"><spring:message code="student.status.disabled" javaScriptEscape="true" /></span>';
                                                } else {
                                                    statusBadge = '<span class="label label-warning label-inline font-weight-bold"><spring:message code="student.status.pending" javaScriptEscape="true" /></span>';
                                                }

                                                html += '<tr>' +
                                                    '<td><label class="checkbox checkbox-single"><input type="checkbox" name="student-checkbox" value="' + student.userId + '"/><span></span></label></td>' +
                                                    '<td>' + (student.number || '') + '</td>' +
                                                    '<td>' + (student.name || '') + '</td>' +
                                                    '<td>' + statusBadge + '</td>' +
                                                    '</tr>';
                                            });
                                            $('#eligible-students-body').html(html);
                                            $('#eligible-count').text(response.students.length);
                                        } else {
                                            $('#eligible-students-body').html(
                                                '<tr>' +
                                                '<td colspan="4" class="text-center text-muted py-5">' +
                                                '<i class="fa fa-info-circle mr-2"></i>' +
                                                '<spring:message code="admin.noEligibleStudents" javaScriptEscape="true" />' +
                                                '</td>' +
                                                '</tr>'
                                            );
                                            $('#eligible-count').text('0');
                                        }
                                    },
                                error: function(xhr, status, error) {
                                    console.error('Load students error:', error);
                                    $('#eligible-students-body').html(
                                        '<tr>' +
                                        '<td colspan="4" class="text-center text-danger py-5">' +
                                        '<i class="fa fa-exclamation-triangle mr-2"></i>' +
                                        '<spring:message code="common.error" javaScriptEscape="true" />' +
                                        '</td>' +
                                        '</tr>'
                                    );
                                    $('#eligible-count').text('0');
                                },
                                complete: function() {
                                    // Reset button state
                                    $('#btn-load-students').prop('disabled', false).html('<i class="fa fa-search mr-1"></i> <spring:message code="admin.loadStudents" javaScriptEscape="true" />');
                                }
                                    });
                                }

                                /**
                                 * Register selected students to the course
                                 */
                                function registerSelectedStudents() {
                                    if (selectedStudents.length === 0) {
                                        alert('<spring:message code="admin.selectAtLeastOne" javaScriptEscape="true" />');
                                        return;
                                    }

                                    // Disable button and show loading
                                    $('#btn-register-selected').prop('disabled', true).html('<i class="fa fa-spinner fa-spin mr-1"></i> <spring:message code="common.loading" javaScriptEscape="true" />...');

                                    // Prepare request data
                                    var requestData = {
                                        courseId: ${ profCourse.courseId },
                                        profCourseId: ${ profCourseId },
                                        schoolYear: parseInt($('#filter-schoolYear').val()),
                                            studentIds: selectedStudents.map(function (id) { return parseInt(id); })
                                };

                                // POST to register students
                                $.ajax({
                                    url: '${baseUrl}/admin/courseManagement/cOpen/registerSelectedStudents',
                                    method: 'POST',
                                    contentType: 'application/json',
                                    data: JSON.stringify(requestData),
                                    dataType: 'json',
                                    success: function (response) {
                                        if (response.success || response.status === 'success' || response.status === 'warning') {
                                            var message = '<spring:message code="admin.registrationSuccess" javaScriptEscape="true" />'
                                                .replace('{0}', response.inserted);
                                            if (response.duplicates > 0) {
                                                message += '\n<spring:message code="admin.duplicatesSkipped" javaScriptEscape="true" />'.replace('{0}', response.duplicates);
                                            }
                                            alert(message);
                                            // Reload page to show updated registered students list
                                            location.reload();
                                        } else {
                                            alert('<spring:message code="common.error" javaScriptEscape="true" />: ' + (response.message || response.error || i18n.unknownError));
                                            $('#btn-register-selected').prop('disabled', false).html('<i class="fa fa-check mr-1"></i> <spring:message code="admin.registerSelected" javaScriptEscape="true" />');
                                        }
                                    },
                                    error: function (xhr, status, error) {
                                        console.error('Register students error:', error, xhr.responseText);
                                        // Try to parse error response from backend
                                        var errorMessage = '<spring:message code="common.error" javaScriptEscape="true" />';
                                        try {
                                            var errorResponse = JSON.parse(xhr.responseText);
                                            if (errorResponse.message) {
                                                errorMessage = errorResponse.message;
                                            }
                                        } catch (e) {
                                            // Use default error message
                                        }
                                        alert(errorMessage);
                                        $('#btn-register-selected').prop('disabled', false).html('<i class="fa fa-check mr-1"></i> <spring:message code="admin.registerSelected" javaScriptEscape="true" />');
                                    }
                                });
                                }

                                /**
                                 * Clear all selected students
                                 */
                                function clearSelection() {
                                    selectedStudents = [];
                                    $('input[name="student-checkbox"]').prop('checked', false);
                                    $('#select-all-students').prop('checked', false);
                                    updateRegisterButton();
                                }

                                /**
                                 * Update Register button state based on selection
                                 */
                                function updateRegisterButton() {
                                    $('#btn-register-selected').prop('disabled', selectedStudents.length === 0);
                                }

                                $(document).ready(function () {
                                    // Initialize DataTable for registered students
                                    if ($('#student-list tbody tr').length > 1 || $('#student-list tbody tr td').length > 1) {
                                        $('#student-list').DataTable({
                                            "pageLength": 25,
                                            "order": [[0, "asc"]]
                                        });
                                    }

                                    // Select All checkbox handler
                                    $('#select-all-students').on('change', function () {
                                        var isChecked = $(this).is(':checked');
                                        $('input[name="student-checkbox"]').prop('checked', isChecked);

                                        selectedStudents = [];
                                        if (isChecked) {
                                            $('input[name="student-checkbox"]').each(function () {
                                                selectedStudents.push($(this).val());
                                            });
                                        }
                                        updateRegisterButton();
                                    });

                                    // Individual checkbox handler (delegated for dynamically loaded content)
                                    $(document).on('change', 'input[name="student-checkbox"]', function () {
                                        var studentId = $(this).val();
                                        if ($(this).is(':checked')) {
                                            if (selectedStudents.indexOf(studentId) === -1) {
                                                selectedStudents.push(studentId);
                                            }
                                        } else {
                                            selectedStudents = selectedStudents.filter(function (id) {
                                                return id !== studentId;
                                            });
                                            $('#select-all-students').prop('checked', false);
                                        }
                                        updateRegisterButton();
                                    });

                                    // Remove student from divide handler
                                    $(document).on('click', '.remove-from-btn', function (e) {
                                        e.preventDefault();
                                        var id = $(this).attr('data-id');
                                        if (confirm('<spring:message code="common.confirmAction" javaScriptEscape="true" />')) {
                                            $.post('${baseUrl}/admin/courseManagement/cOpen/manageStudent/removeFromDivide?id=' + id + '&profCourseId=${profCourseId}', function (result) {
                                                location.reload();
                                            });
                                        }
                                    });
                                });
                            </script>
        </body>

        </html>