<div class="row">
    <div class="col-md-2">
        <spring:message code="common.studentsNumber"/><br/>
        <input type="text" id="search-number" class="form-control input-enter" value="" style="margin-top:10px;"/>
    </div>
    <div class="col-md-3">
        <spring:message code="common.studentsName"/><br/>
        <input type="text" id="search-name" class="form-control input-enter"  value="" style="margin-top:10px;"/>
    </div>
    <div class="col-md-3">
        <spring:message code="common.department"/><br/>
        <select id="search-division" class="form-control" style="margin-top:10px;">
            <c:forEach var="division" items="${divisions}">
                <option value="${division.id}">${division.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-md-2">
        <spring:message code="common.major"/><br/>
        <select id="search-major" class="form-control" style="margin-top:10px;">


        </select>
    </div>
    <div class="col-md-1">
        <br/>
        <button class="btn btn-primary" style="width:100%;margin-top:10px;" onclick="search()"><spring:message code="common.search"/></button>
    </div>
    <div class="col-md-1">
        <br/>
        <button class="btn btn-light" style="width:100%;margin-top:10px;" onclick="javascript:location.reload()"><spring:message code="common.reset"/></button>
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