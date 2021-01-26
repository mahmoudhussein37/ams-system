<div class="card-body">
    <div class="row">
        <div class="col-md-12">
            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.certificate"/></h3>

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
                        <spring:message code="common.level"/>
                    </th>
                    <th>
                        <spring:message code="common.agency"/>
                    </th>
                    <th>
                        <spring:message code="common.acquisitionDate"/>
                    </th>


                </tr>
                </thead>
                <tbody>
                <c:set var="count" value="1"/>
                <c:forEach var="langCert"  items="${langCerts}">
                    <c:if test="${langCert.type eq 'cert'}">
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
                                    ${langCert.agency}
                            </td>
                            <td>
                                    ${langCert.date}
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