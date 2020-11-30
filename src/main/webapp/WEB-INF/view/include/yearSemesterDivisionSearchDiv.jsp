<div class="row">
    <div class="col-md-2">
        <spring:message code="common.year"/><br/>
        <select id="search-year" class="form-control" style="">
            <option value="0">-</option>
            <c:forEach var="y" items="${yearList}">
                <option value="${y}" ${y eq year ? 'selected' : ''}>${y}</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-md-2">
        <spring:message code="common.semester"/><br/>
        <select id="search-semester" class="form-control" style="">
            <option value="0">-</option>
            <option value="1" ${s eq 1 ? 'selected' : ''}><spring:message code="common.sem1"/></option>
            <option value="2" ${s eq 2 ? 'selected' : ''}><spring:message code="common.sem2"/></option>
        </select>
    </div>
    <div class="col-md-3">
        <spring:message code="common.department"/><br/>
        <select id="search-division" class="form-control" style="">
            <option value="0">-</option>
            <c:forEach var="d" items="${divisions}">
                <option value="${d.id}" ${d eq division ? 'selected' : ''}>${d.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-md-2">
        <br/>
        <button class="btn btn-primary" style="width:100%;" onclick="search()"><spring:message code="common.search"/></button>
    </div>
    <div class="col-md-2">
        <br/>
        <button class="btn btn-light" style="width:100%;" onclick="javascript:location.reload()"><spring:message code="common.reset"/></button>
    </div>
</div>