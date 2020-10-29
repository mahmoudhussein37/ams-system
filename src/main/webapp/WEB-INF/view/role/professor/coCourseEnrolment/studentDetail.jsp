<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold">
        <spring:message code="common.print"/>
    </a>
</div>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.basicInformation"/></h3>
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
            <label><spring:message code="common.name"/></label>
            <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.department"/></label>
            <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <%--<div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.major"/></label>
            <input type="text" class="form-control" value="${studentUser.major.name}" disabled/>
            &lt;%&ndash;<span class="form-text text-muted">We'll never share your email with anyone else</span>&ndash;%&gt;
        </div>
    </div>--%>
</div>
<div class="row">
    <div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.status"/></label>
            <input type="text" class="form-control" value="${studentUser.status}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.advisor"/></label>
            <input type="text" class="form-control"  value="${studentUser.advisor}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.academicYear"/></label>
            <input type="text" class="form-control" value="" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.approve"/></label>
            <input type="text" class="form-control" value="" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
</div>
<div class="row">
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.completeSemester"/></label>
            <input type="text" class="form-control" value="" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.schoolYear"/></label>
            <input type="text" class="form-control" value="${studentUser.schoolYear}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>

    </div>
    <div class="col-md-3">


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
                <td colspan="2">
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
                <td rowspan="3" class="table-light"><spring:message code="common.liberal"/></td>
                <td><spring:message code="common.require"/></td>
                <td>10</td>
                <td>8</td>
                <td>-2</td>
            </tr>
            <tr>
                <td><spring:message code="common.option"/></td>
                <td>6</td>
                <td>13</td>
                <td>7</td>
            </tr>
            <tr class="table-light">
                <td><spring:message code="common.subSum"/></td>
                <td>6</td>
                <td>13</td>
                <td>7</td>
            </tr>
            <tr>
                <td rowspan="3">MSC</td>
                <td><spring:message code="common.require"/></td>
                <td>10</td>
                <td>8</td>
                <td>-2</td>
            </tr>
            <tr class="table-light">
                <td><spring:message code="common.option"/></td>
                <td>6</td>
                <td>13</td>
                <td>7</td>
            </tr>
            <tr>
                <td><spring:message code="common.subSum"/></td>
                <td>6</td>
                <td>13</td>
                <td>7</td>
            </tr>
            <tr class="table-light">
                <td rowspan="3" class="table-light"><spring:message code="common.major"/></td>
                <td><spring:message code="common.require"/></td>
                <td>10</td>
                <td>8</td>
                <td>-2</td>
            </tr>
            <tr>
                <td><spring:message code="common.option"/></td>
                <td>6</td>
                <td>13</td>
                <td>7</td>
            </tr>
            <tr class="table-light">
                <td><spring:message code="common.subSum"/></td>
                <td>6</td>
                <td>13</td>
                <td>7</td>
            </tr>


        </table>


        <br/>
        <div class="separator separator-solid my-5"></div>
        <br/>

        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.applicationSubject"/></h3>


        <table class="table">
            <thead>
            <tr>
                <th>
                    <spring:message code="common.no"/>
                </th>
                <th>
                    <spring:message code="common.courseCode"/>
                </th>
                <th>
                    <spring:message code="common.courseTitle"/>
                </th>
                <th>
                    <spring:message code="common.category"/>
                </th>
                <th>
                    <spring:message code="common.credit"/>
                </th>

            </tr>
            </thead>
            <tbody>
            <tr>
                <td>

                </td>
                <td>

                </td>
                <td>

                </td>
                <td>

                </td>
                <td>

                </td>

            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="row">

    <div class="col-md-12">

        <div class="form-group">
            <label><spring:message code="professor.counselingComments"/></label>
            <textarea class="form-control" rows="6" disabled></textarea>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="form-group" style="text-align:right">
            <button type="button" class="btn btn-secondary mr-2"><spring:message code="common.return"/></button>
            <button type="button" class="btn btn-primary mr-2"><spring:message code="common.approve"/></button>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>