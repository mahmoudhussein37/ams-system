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
                                                <spring:message code="menu.admin.sub3_1" />
                                            </h3>
                                        </div>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-md-2">
                                                    <spring:message code="common.year" /><br />
                                                    <select id="search-year" class="form-control"
                                                        style="margin-top:10px;">
                                                        <c:forEach var="y" items="${yearList}">
                                                            <option value="${y}" ${y eq year ? 'selected' : '' }>${y}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-md-2">
                                                    <br />
                                                    <button class="btn btn-primary" style="width:100%;margin-top:10px;"
                                                        onclick="search()">
                                                        <spring:message code="common.search" />
                                                    </button>
                                                </div>
                                            </div>
                                            <br /><br />

                                            <%-- Feedback banners --%>
                                                <c:if test="${param.success eq 'uploaded'}">
                                                    <div class="alert alert-success alert-dismissible fade show"
                                                        role="alert">
                                                        <spring:message code="curriculum.success.uploaded" />
                                                        <button type="button" class="close"
                                                            data-dismiss="alert"><span>&times;</span></button>
                                                    </div>
                                                </c:if>
                                                <c:if test="${param.success eq 'updated'}">
                                                    <div class="alert alert-success alert-dismissible fade show"
                                                        role="alert">
                                                        <spring:message code="curriculum.success.updated" />
                                                        <button type="button" class="close"
                                                            data-dismiss="alert"><span>&times;</span></button>
                                                    </div>
                                                </c:if>
                                                <c:if test="${param.error eq 'validation'}">
                                                    <div class="alert alert-warning alert-dismissible fade show"
                                                        role="alert">
                                                        <spring:message code="curriculum.error.validation" />
                                                        <button type="button" class="close"
                                                            data-dismiss="alert"><span>&times;</span></button>
                                                    </div>
                                                </c:if>
                                                <c:if test="${param.error eq 'publish_failed'}">
                                                    <div class="alert alert-danger alert-dismissible fade show"
                                                        role="alert">
                                                        <spring:message code="curriculum.error.publishFailed" />
                                                        <button type="button" class="close"
                                                            data-dismiss="alert"><span>&times;</span></button>
                                                    </div>
                                                </c:if>
                                                <c:if test="${param.error eq 'conflict'}">
                                                    <div class="alert alert-warning alert-dismissible fade show"
                                                        role="alert">
                                                        <spring:message code="curriculum.error.conflict" />
                                                        <button type="button" class="close"
                                                            data-dismiss="alert"><span>&times;</span></button>
                                                    </div>
                                                </c:if>
                                                <c:if test="${param.error eq 'not_found'}">
                                                    <div class="alert alert-danger alert-dismissible fade show"
                                                        role="alert">
                                                        <spring:message code="curriculum.error.notFound" />
                                                        <button type="button" class="close"
                                                            data-dismiss="alert"><span>&times;</span></button>
                                                    </div>
                                                </c:if>

                                                <div class="table-responsive">
                                                    <table class="table table-head-custom table-vertical-center"
                                                        id="curriculum-list">
                                                        <thead>
                                                            <tr class="text-uppercase">
                                                                <th class="pl-0">
                                                                    <spring:message code="common.no" />
                                                                </th>
                                                                <th>
                                                                    <spring:message code="common.year" />
                                                                </th>
                                                                <th>
                                                                    <spring:message code="common.department" />
                                                                </th>
                                                                <th>
                                                                    <spring:message code="common.status" />
                                                                </th>
                                                                <th>
                                                                    <spring:message code="common.uploadedAt" />
                                                                </th>
                                                                <th>
                                                                    <spring:message code="common.uploadedBy" />
                                                                </th>
                                                                <th>
                                                                    <spring:message code="common.actions" />
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="row" items="${curriculumRows}"
                                                                varStatus="vs">
                                                                <c:set var="hasActiveCurriculum"
                                                                    value="${row.status eq 'ACTIVE' and row.uploadedFileId != null}" />
                                                                <tr>
                                                                    <td class="pl-0">${vs.count}</td>
                                                                    <td>${row.academicYear}</td>
                                                                    <td>${row.divisionName}</td>

                                                                    <%-- Status column --%>
                                                                        <td>
                                                                            <c:choose>
                                                                                <c:when test="${hasActiveCurriculum}">
                                                                                    <span class="badge badge-success"
                                                                                        title="<spring:message code='curriculum.tooltip.uploaded' />">
                                                                                        <spring:message
                                                                                            code="curriculum.status.uploaded" />
                                                                                    </span>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <span class="badge badge-secondary"
                                                                                        title="<spring:message code='curriculum.tooltip.notUploaded' />">
                                                                                        <spring:message
                                                                                            code="curriculum.status.notUploaded" />
                                                                                    </span>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </td>

                                                                        <%-- Uploaded At column --%>
                                                                            <td>
                                                                                <c:choose>
                                                                                    <c:when
                                                                                        test="${hasActiveCurriculum and row.uploadedAt != null}">
                                                                                        <fmt:formatDate
                                                                                            value="${row.uploadedAt}"
                                                                                            pattern="yyyy-MM-dd HH:mm" />
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <span
                                                                                            class="text-muted">&mdash;</span>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </td>

                                                                            <%-- Uploaded By column --%>
                                                                                <td>
                                                                                    <c:choose>
                                                                                        <c:when
                                                                                            test="${hasActiveCurriculum and not empty row.uploaderName}">
                                                                                            ${row.uploaderName}
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <span
                                                                                                class="text-muted">&mdash;</span>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </td>

                                                                                <%-- Actions column --%>
                                                                                    <td>
                                                                                        <c:choose>
                                                                                            <c:when
                                                                                                test="${hasActiveCurriculum}">
                                                                                                <spring:url
                                                                                                    var="updateUrl"
                                                                                                    value="/admin/courseManagement/curriculum/uploadCurriculum">
                                                                                                    <spring:param
                                                                                                        name="year"
                                                                                                        value="${row.academicYear}" />
                                                                                                    <spring:param
                                                                                                        name="divisionId"
                                                                                                        value="${row.divisionId}" />
                                                                                                </spring:url>

                                                                                                <a href="${updateUrl}"
                                                                                                    class="btn btn-sm btn-warning mr-2">
                                                                                                    <spring:message
                                                                                                        code="curriculum.updateCurriculum" />
                                                                                                </a>
                                                                                                <a href="${baseUrl}/download?uploadedFileId=${row.uploadedFileId}"
                                                                                                    class="btn btn-sm btn-outline-primary">
                                                                                                    <i
                                                                                                        class="flaticon-download"></i>
                                                                                                    <spring:message
                                                                                                        code="common.download" />
                                                                                                </a>
                                                                                            </c:when>
                                                                                            <c:otherwise>
                                                                                                <spring:url
                                                                                                    var="uploadUrl"
                                                                                                    value="/admin/courseManagement/curriculum/uploadCurriculum">
                                                                                                    <spring:param
                                                                                                        name="year"
                                                                                                        value="${row.academicYear}" />
                                                                                                    <spring:param
                                                                                                        name="divisionId"
                                                                                                        value="${row.divisionId}" />
                                                                                                    <spring:param
                                                                                                        name="mode"
                                                                                                        value="upload" />
                                                                                                    <spring:param
                                                                                                        name="divisionName"
                                                                                                        value="${row.divisionName}" />
                                                                                                </spring:url>
                                                                                                <a href="${uploadUrl}"
                                                                                                    class="btn btn-sm btn-primary">
                                                                                                    <spring:message
                                                                                                        code="curriculum.uploadCurriculum" />
                                                                                                </a>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
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
                                function search() {
                                    var year = parseInt($("#search-year").val().trim(), 10);
                                    if (isNaN(year)) { year = new Date().getFullYear(); }
                                    location.href = "${baseUrl}/admin/courseManagement/curriculum?year=" + year;
                                }

                                $(document).ready(function () {
                                });
                            </script>
        </body>

        </html>