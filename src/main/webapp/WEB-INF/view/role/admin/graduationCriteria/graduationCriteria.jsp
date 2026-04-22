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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub4_2"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-2">
                                    <spring:message code="common.year"/><br/>
                                    <select id="search-year" class="form-control" style="margin-top:10px;">
                                        <option value="0">-</option>
                                        <c:forEach var="y" items="${yearList}">

                                            <option value="${y}">${y}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.department"/><br/>
                                    <select id="search-division" class="form-control" style="margin-top:10px;"><option value="0">-</option>
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <%--<div class="col-md-2">
                                    <spring:message code="common.major"/><br/>
                                    <select id="search-major" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="major" items="${majors}">
                                            <option value="${major.id}">${major.name}</option>
                                        </c:forEach>

                                    </select>
                                </div>--%>
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
                                <form:form modelAttribute="graduationCriteria" action="${baseUrl}/admin/academicManagement/graduationCriteria" method="post" class="assessment-factor-form">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createGraduationCriteria"/></h3>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.year"/></label>
                                                <form:input path="year" type="number" class="form-control"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
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
                                                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="subj.category.msc"/></label>
                                                <form:input path="msc" type="number" class="form-control"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="subj.category.liberal"/></label>
                                                <form:input path="liberal" type="number" class="form-control"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="subj.category.major"/></label>
                                                <form:input path="major" type="number" class="form-control"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                    </div>

                                    <button type="submit" class="btn btn-primary mr-2 submit-assessment-factor"><spring:message code="common.submit"/></button>
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
        var division = $("#search-division").children("option:selected").val().trim();
        var year = $("#search-year").children("option:selected").val().trim();

        $(".table-div").load("${baseUrl}/admin/academicManagement/graduationCriteria/criteriaTable?division=" + division + "&year=" + year);
    }



    $(document).ready(function() {
        $(".table-div").load("${baseUrl}/admin/academicManagement/graduationCriteria/criteriaTable");

        $(".input-enter").keydown(function(key) {
            if (key.keyCode == 13) {
                search();
            }
        });


    });
</script>
</body>
</html>