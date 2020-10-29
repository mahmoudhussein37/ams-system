<div class="row">
    <div class="col-md-2">
        <spring:message code="common.year"/><br/>
        <select id="search-year" class="form-control" style="">
            <c:forEach var="y" items="${yearList}">
                <option value="${y}">${y}</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-md-2">
        <spring:message code="common.semester"/><br/>
        <select id="search-semester" class="form-control" style="">
            <option value="1"><spring:message code="common.sem1"/></option>
            <option value="2"><spring:message code="common.sem2"/></option>
        </select>
    </div>
    <div class="col-md-4">
        <spring:message code="common.department"/><br/>
        <select id="search-division" class="form-control" style="">
            <c:forEach var="division" items="${divisions}">
                <option value="${division.id}">${division.name}</option>
            </c:forEach>
        </select>
    </div>
    <%--<div class="col-md-2">
        <spring:message code="common.major"/><br/>
        <select id="search-major" class="form-control" style="">
            &lt;%&ndash;<c:forEach var="major" items="${majors}">
                <option value="${major.id}">${major.name}</option>
            </c:forEach>&ndash;%&gt;
        </select>
    </div>--%>

    <div class="col-md-2">
        <br/>
        <button class="btn btn-primary" style="width:100%;" onclick="search()"><spring:message code="common.search"/></button>
    </div>
</div>
<br/><br/>
<div class="table-div">


</div>
<br/><br/>
<div class="separator separator-solid my-5"></div>
<br/><br/>
<div class="detail-div">

</div>