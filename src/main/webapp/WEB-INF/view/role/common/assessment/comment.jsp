<div class="card-body">

    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <table class="table rounded">
                    <thead>
                    <tr class="table-secondary text-center">

                        <td><spring:message code="common.no"/></td>
                        <td>
                            <spring:message code="common.comment"/>
                        </td>


                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="am" items="${assessments}" varStatus="varStatus">
                    <tr class="table-light text-center">
                        <td>${varStatus.count}</td>
                        <td>
                            ${am.comment}
                        </td>

                    </tr>
                    </c:forEach>


                    </tbody>
                </table>


            </div>
        </div>
    </div>

</div>