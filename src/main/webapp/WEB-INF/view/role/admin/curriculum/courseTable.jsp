<%@include file="/WEB-INF/view/include/topTag.jsp" %>

    <table class="table table-head-custom table-vertical-center align-middle" id="curriculum-table">
        <thead>
            <tr class="text-uppercase">
                <th class="pl-0">
                    <spring:message code="common.no" />
                </th>
                <th>
                    <spring:message code="common.department" />
                </th>
                <th>
                    <spring:message code="common.status" />
                </th>
                <th>
                    <spring:message code="common.publishedAt" />
                </th>
                <th>
                    <spring:message code="common.publishedBy" />
                </th>
                <th>
                    <spring:message code="common.actions" />
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="row" items="${curriculumRows}" varStatus="vs">
                <tr>
                    <td class="pl-0">${vs.count}</td>
                    <td>${row.divisionName}</td>

                    <%-- Status column --%>
                        <td>
                            <c:choose>
                                <c:when test="${row.status eq 'ACTIVE'}">
                                    <span class="badge badge-success">ACTIVE</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">&mdash;</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <%-- Published At: visible only if ACTIVE --%>
                            <td>
                                <c:if test="${row.status eq 'ACTIVE' and row.uploadedAt != null}">
                                    <fmt:formatDate value="${row.uploadedAt}" pattern="yyyy-MM-dd HH:mm" />
                                </c:if>
                                <c:if test="${row.status ne 'ACTIVE' or row.uploadedAt == null}">
                                    <span class="text-muted">&mdash;</span>
                                </c:if>
                            </td>

                            <%-- Published By: visible only if ACTIVE --%>
                                <td>
                                    <c:if test="${row.status eq 'ACTIVE' and not empty row.uploaderName}">
                                        ${row.uploaderName}
                                    </c:if>
                                    <c:if test="${row.status ne 'ACTIVE' or empty row.uploaderName}">
                                        <span class="text-muted">&mdash;</span>
                                    </c:if>
                                </td>

                                <%-- Actions column --%>
                                    <td class="actions-cell">
                                        <div class="actions-wrapper">
                                            <%-- Publish New Version: always visible --%>
                                                <a href="${baseUrl}/admin/courseManagement/curriculum/uploadCurriculum?year=${row.academicYear}&divisionId=${row.divisionId}"
                                                    class="btn btn-sm btn-primary action-btn"
                                                    title="Publish a new curriculum version for this department">
                                                    <spring:message code="common.publishNewVersion" />
                                                </a>

                                                <%-- Download: visible only if ACTIVE --%>
                                                    <c:if
                                                        test="${row.status eq 'ACTIVE' and row.uploadedFileId != null}">
                                                        <a href="${baseUrl}/download?uploadedFileId=${row.uploadedFileId}"
                                                            class="btn btn-sm btn-outline-primary action-btn">
                                                            <i class="flaticon-download"></i>
                                                            <spring:message code="common.download" />
                                                        </a>
                                                    </c:if>
                                        </div>
                                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>