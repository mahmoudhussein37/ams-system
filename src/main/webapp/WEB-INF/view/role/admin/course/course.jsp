<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>
<!--begin::Content-->
<div class="content  d-flex flex-column flex-column-fluid" id="kt_content">
    <!--begin::Entry-->
    <div class="d-flex flex-column-fluid">
        <!--begin::Container-->
        <div class=" container ">
            <div class="row">
                <div class="col-md-12">
                    <!--begin::Card-->
                    <div class="card card-custom">
                        <div class="card-header">
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub3_2"/></h3>

                        </div>
                        <div class="card-body">
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
                                <div class="col-md-2">
                                    <spring:message code="common.major"/><br/>
                                    <select id="search-major" class="form-control" style="">
                                        <%--<c:forEach var="major" items="${majors}">
                                            <option value="${major.id}">${major.name}</option>
                                        </c:forEach>--%>
                                    </select>
                                </div>

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
                            <div>



                                <form:form modelAttribute="course" action="${baseUrl}/admin/courseManagement/course" method="post" class="form">
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createCourse"/></h3>
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
                                                <label><spring:message code="common.department"/></label>
                                                <form:select path="divisionId" class="form-control">
                                                    <c:forEach var="division" items="${divisions}">
                                                        <form:option value="${division.id}">${division.name}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.major"/></label>
                                                <form:select path="majorId" class="form-control">
                                                    <%--<c:forEach var="major" items="${majors}">
                                                        <form:option value="${major.id}">${major.name}</form:option>
                                                    </c:forEach>--%>
                                                </form:select>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.compCategory"/></label>
                                                <form:select path="compCategory" class="form-control" style="">
                                                    <c:forEach var="c" items="${compCategoryList}">
                                                        <form:option value="${c.name()}"><spring:message code="comp.category.${c.name()}"/></form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
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
                                                <label><spring:message code="common.divide"/></label>
                                                <form:select path="divide" class="form-control" style="">
                                                    <c:forEach var="d" begin="1" end="10">
                                                        <option value="${d}">${d}</option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>

                                    </div>
                                    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.create"/></button>
                                </form:form>
                            </div>

                        </div>
                    </div>
                    <!--end::Card-->
                </div>
            </div>


        </div>
        <!--end::Container-->
    </div>
    <!--end::Entry-->
</div>
<!--end::Content-->

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>


<%@include file="/WEB-INF/view/include/userPanel.jsp" %>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>

    function search() {
        var year = $("#search-year").children("option:selected").val().trim();
        var semester = $("#search-semester").children("option:selected").val().trim();
        //var major = $("#search-major").children("option:selected").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        $(".table-div").load("${baseUrl}/admin/courseManagement/course/courseTable?year=" + year + "&semester=" + semester + "&division=" + division);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            search();
        }
    });

    $(document).ready(function() {
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/courseManagement/course";
        </c:if>

        $(".table-div").load("${baseUrl}/admin/courseManagement/course/courseTable");
        changeMajor("#search-division", "#search-major", true);
        changeMajor("#divisionId", "#majorId", true);
    });

</script>
</body>
</html>