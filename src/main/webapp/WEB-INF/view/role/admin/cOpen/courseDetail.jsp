<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<form:form modelAttribute="course" action="${baseUrl}/admin/courseManagement/cOpen/courseDetail?courseId=${course.id}" method="post" class="form">
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.editCourse"/></h3>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.courseCode"/></label>
                <form:input path="code" type="text" class="form-control"/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.courseTitle"/></label>
                <form:input path="title" type="text" class="form-control"/>
                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.status"/></label>
                <form:select path="enabled" class="form-control">
                    <option value="true"><spring:message code="common.opened"/></option>
                    <option value="false"><spring:message code="common.closed"/></option>
                </form:select>
            </div>
        </div>
    </div>
    <div class="row">

        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.department"/></label>
                <form:select path="divisionId" class="form-control">
                    <c:forEach var="division" items="${divisions}">
                        <form:option value="${division.id}">${division.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
<%--        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.major"/></label>
                <form:select path="majorId" class="form-control">
                    &lt;%&ndash;<c:forEach var="major" items="${majors}">
                        <form:option value="${major.id}">${major.name}</form:option>
                    </c:forEach>&ndash;%&gt;
                </form:select>
            </div>
        </div>--%>
<%--        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.compCategory"/></label>
                <form:select path="compCategory" class="form-control" style="">
                    <c:forEach var="c" items="${compCategoryList}">
                        <form:option value="${c.name()}"><spring:message code="comp.category.${c.name()}"/></form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>--%>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.subjCategory"/></label>
                <form:select path="subjCategory" class="form-control" style="">
                    <c:forEach var="c" items="${subjCategoryList}">
                        <form:option value="${c.name()}"><spring:message code="subj.category.${c.name()}"/></form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.year"/></label>
                <form:select path="year" class="form-control" style="">
                    <c:forEach var="y" items="${yearList}">
                        <option value="${y}">${y}</option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.semester"/></label>
                <form:select path="semester" class="form-control" style="">
                    <option value="1"><spring:message code="common.sem1"/></option>
                    <option value="2"><spring:message code="common.sem2"/></option>
                </form:select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.schoolYear"/></label>
                <form:select path="schoolYear" class="form-control" style="">
                    <c:forEach var="y" begin="1" end="4">
                        <form:option value="${y}">${y}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label><spring:message code="common.credit"/></label>
                <form:input path="credit" type="number" class="form-control"/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>
        </div>
    </div>
    <div class="row">

        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="common.role.professor"/></label>
                <form:select path="profUserId" class="form-control" style="">

                </form:select>
            </div>
        </div>
        <div class="col-md-3">

            <div class="form-group">
                <label><spring:message code="admin.maxStudent"/></label>
                <form:input path="maxStudent" type="number" class="form-control"/>
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
</form:form>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>




    var divisionId = $("#divisionId").children("option:selected").val();
    $.get("${baseUrl}/profList?defaultSelected=${course.profUserId}&divisionId=" + divisionId, function(html) {
        $("#profUserId").html(html);
    });



    $("#divisionId").change(function() {
        var divisionId = $("#divisionId").children("option:selected").val();
        console.log(divisionId);
        $.get("${baseUrl}/profList?divisionId=" + divisionId, function(html) {
            console.log(html);
            $("#profUserId").html(html);
        });
    });
</script>