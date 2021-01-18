<div class="card-body">
    <div class="row">
        <div class="col-md-12">
            <p style="text-align:right">
                <a class="btn btn-primary" href="${baseUrl}/admin/studentManagement/studentProfile/addLangCert?studentId=${studentUser.id}"><spring:message code="common.new"/></a>
            </p>

            <br/><br/>
            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.language"/></h3>

            <table class="table">
                <thead>
                <tr>
                    <th>
                        <spring:message code="common.no"/>
                    </th>
                    <th>
                        <spring:message code="common.type"/>
                    </th>
                    <th>
                        <spring:message code="common.scoreLevel"/>
                    </th>
                    <th>
                        <spring:message code="common.acquisitionDate"/>
                    </th>
                    <th>

                    </th>
                </tr>
                </thead>
                <tbody>
                <c:set var="count" value="1"/>
                <c:forEach var="langCert"  items="${langCerts}">
                    <c:if test="${langCert.type eq 'lang'}">
                        <tr>
                            <td>
                                    ${count}
                            </td>
                            <td>
                                    ${langCert.lType}
                            </td>
                            <td>
                                    ${langCert.level}
                            </td>
                            <td>
                                    ${langCert.date}
                            </td>
                            <td>
                                <button class="btn btn-secondary delete-lang-cert" data-id="${langCert.id}"><spring:message code="common.delete"/></button>
                            </td>

                        </tr>
                    </c:if>
                    <c:set var="count" value="${count + 1}"/>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>