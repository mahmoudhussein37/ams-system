<%@include file="/WEB-INF/view/include/topTag.jsp" %>

    <table class="table table-head-custom table-vertical-center" id="course-list">
        <thead>
            <tr class="text-uppercase">

                <th class="pl-0">
                    <spring:message code="common.no" />
                </th>
                <th><span class="text-primary">
                        <spring:message code="common.year" />
                    </span></th>
                <th><span class="text-primary">
                        <spring:message code="common.department" />
                    </span></th>
                <th><span class="text-primary">
                        <spring:message code="common.download" />
                    </span></th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="division" items="${divisions}" varStatus="varStatus">
                <tr>

                    <td class="pl-0">
                        ${varStatus.count}
                    </td>
                    <td>
                        ${year}
                    </td>
                    <td>
                        ${division.name}
                    </td>
                    <td>

                        <c:forEach var="uf" items="${uploadedFiles}">
                            <c:if test="${uf.year eq year and uf.divisionId eq division.id}">
                                <a href="${baseUrl}/download?uploadedFileId=${uf.id}">
                                    <spring:message code="common.download" />
                                </a>
                            </c:if>
                        </c:forEach>

                    </td>



                </tr>
            </c:forEach>



        </tbody>
    </table>