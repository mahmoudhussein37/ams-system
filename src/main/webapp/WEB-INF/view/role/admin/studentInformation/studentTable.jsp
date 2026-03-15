<%@include file="/WEB-INF/view/include/topTag.jsp" %>

    <table class="table table-head-custom table-vertical-center" id="student-list">
        <thead>
            <tr class="text-uppercase">

                <th class="pl-0" style="">
                    <spring:message code="common.no" />
                </th>
                <th style=""><span class="text-primary">
                        <spring:message code="common.studentNumber" />
                    </span></th>
                <th style=""><span class="text-primary">
                        <spring:message code="common.name" />
                    </span>
                <th style=""><span class="text-primary">
                        <spring:message code="common.department" />
                    </span>
                <th style=""><span class="text-primary">
                        <spring:message code="admin.accountStatus" />
                    </span></th>
                <th style="text-align:center;"><span class="text-primary">
                        <spring:message code="common.manage" />
                    </span></th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="studentUser" items="${userList}" varStatus="varStatus">
                <tr>

                    <td class="pl-0">
                        ${varStatus.count}
                    </td>
                    <td>
                        ${studentUser.number}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${studentUser.contact != null}">${studentUser.contact.fullName}</c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${studentUser.division != null}">${studentUser.division.name}</c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${studentUser.username != null && studentUser.username != ''}">
                                <span class="label label-light-success label-inline font-weight-bold">
                                    <spring:message code="student.status.active" />
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-light-warning label-inline font-weight-bold">
                                    <spring:message code="student.status.pending" />
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align:center;">
                        <a href="#" class="btn btn-sm btn-light-primary btn-edit-student"
                            data-student-id="${studentUser.id}" title="<spring:message code='common.edit'/>">
                            <i class="fa fa-edit"></i>
                        </a>
                        <a href="#" class="btn btn-sm btn-light-danger btn-delete-student"
                            data-student-id="${studentUser.id}"
                            data-student-name="<c:choose><c:when test='${studentUser.contact != null}'>${studentUser.contact.fullName}</c:when><c:otherwise>${studentUser.number}</c:otherwise></c:choose>"
                            title="<spring:message code='admin.deleteStudent'/>">
                            <i class="fa fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>


        </tbody>
    </table>

    <%-- Delete Confirmation Modal --%>
        <div class="modal fade" id="deleteStudentModal" tabindex="-1" role="dialog"
            aria-labelledby="deleteStudentModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteStudentModalLabel">
                            <spring:message code="admin.deleteStudent" />
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <spring:message code="admin.confirmDeleteStudent" />
                        <br /><br />
                        <strong id="delete-student-name"></strong>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <spring:message code="common.cancel" />
                        </button>
                        <button type="button" class="btn btn-danger" id="btn-confirm-delete">
                            <spring:message code="common.delete" />
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $("#student-list").DataTable();

            // Show placeholder message initially - don't auto-load any student
            $(".detail-div").html('<div class="alert alert-light text-center py-10"><i class="fa fa-info-circle text-muted mr-2"></i><spring:message code="admin.selectStudentToEdit" javaScriptEscape="true" /></div>');

            // Edit button click handler - only this loads the detail form
            $(document).off('click', '.btn-edit-student').on('click', '.btn-edit-student', function (e) {
                e.preventDefault();
                var studentId = $(this).attr("data-student-id");
                $(".detail-div").load("${baseUrl}/admin/studentManagement/studentInformation/studentDetail?studentId=" + studentId);
            });

            // Delete button click handler
            var deleteStudentId = null;
            $(document).off('click', '.btn-delete-student').on('click', '.btn-delete-student', function (e) {
                e.preventDefault();
                deleteStudentId = $(this).attr("data-student-id");
                var studentName = $(this).attr("data-student-name");
                $("#delete-student-name").text(studentName);
                $("#deleteStudentModal").modal('show');
            });

            // Confirm delete button
            $(document).off('click', '#btn-confirm-delete').on('click', '#btn-confirm-delete', function () {
                if (deleteStudentId) {
                    $.post("${baseUrl}/admin/studentManagement/studentInformation/deleteStudent", {
                        studentId: deleteStudentId,
                        "${_csrf.parameterName}": "${_csrf.token}"
                    }, function (response) {
                        $("#deleteStudentModal").modal('hide');
                        if (response.success) {
                            // Show success message and refresh table
                            alert("<spring:message code='admin.delete.success' javaScriptEscape="true" />");
                            var number = $("#search-number").val() ? $("#search-number").val().trim() : '';
                            var name = $("#search-name").val() ? $("#search-name").val().trim() : '';
                            var division = $("#search-division").val() ? $("#search-division").val().trim() : '0';
                            $(".table-div").load("${baseUrl}/admin/studentManagement/studentInformation/studentTable?number=" + number + "&name=" + name + "&division=" + division);
                            // Reset detail view
                            $(".detail-div").html('<div class="alert alert-light text-center py-10"><i class="fa fa-info-circle text-muted mr-2"></i><spring:message code="admin.selectStudentToEdit" javaScriptEscape="true" /></div>');
                        } else {
                            if (response.message === 'hasRelatedRecords') {
                                alert("<spring:message code='admin.delete.error.hasRecords' javaScriptEscape="true" />" + " (" + response.recordCount + " <spring:message code='admin.delete.courseRecords' javaScriptEscape="true" />)");
                            } else {
                                alert("<spring:message code='admin.delete.error.generic' javaScriptEscape="true" />");
                            }
                        }
                    }, 'json').fail(function () {
                        $("#deleteStudentModal").modal('hide');
                        alert("<spring:message code='admin.delete.error.generic' javaScriptEscape="true" />");
                    });
                }
            });
        </script>