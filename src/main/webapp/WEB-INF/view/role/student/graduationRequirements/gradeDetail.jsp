<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="card-body">
    <div class="row">
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.studentNumber"/></label>
                <input type="text" class="form-control" value="${studentUser.number}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>

        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.department"/></label>
                <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.name"/></label>
                <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>

    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.status"/></label>
                <input type="text" class="form-control" value="<spring:message code="student.status.${studentUser.status}"/>" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.advisor"/></label>
                <input type="text" class="form-control" value="AAA" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.academicYear"/></label>
                <input type="text" class="form-control" value="${studentUser.contact.admissionYear}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.completeSemester"/></label>
                <input type="text" class="form-control" value="" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
    </div>

    <br/>
    <div class="separator separator-solid my-5"></div>
    <br/>
    <div class="row">
        <div class="col-md-12">
            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.curriculumStatus"/></h3>
            <table class="table">
                <tr>
                    <td>
                        <spring:message code="common.classification"/>
                    </td>
                    <td>
                        <spring:message code="common.criteria"/>
                    </td>
                    <td>
                        <spring:message code="common.acquire"/>
                    </td>
                    <td>
                        <spring:message code="common.result"/>
                    </td>
                </tr>
                <tr class="table-light">
                    <td class="table-light"><spring:message code="common.liberal"/></td>
                    <%--<td><spring:message code="common.require"/></td>--%>
                    <td>10</td>
                    <td>8</td>
                    <td>-2</td>
                </tr>

                <tr>
                    <td >MSC</td>
                    <%--<td><spring:message code="common.require"/></td>--%>
                    <td>10</td>
                    <td>8</td>
                    <td>-2</td>
                </tr>

                <tr class="table-light">
                    <td class="table-light"><spring:message code="common.major"/></td>
                    <%--<td><spring:message code="common.require"/></td>--%>
                    <td>10</td>
                    <td>8</td>
                    <td>-2</td>
                </tr>



            </table>


            
        </div>
    </div>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>