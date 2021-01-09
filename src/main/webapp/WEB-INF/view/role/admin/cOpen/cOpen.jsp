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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub3_4"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-2">
                                    <spring:message code="common.courseCode"/><br/>
                                    <input type="text" id="search-code" class="form-control input-enter" value="" style="margin-top:10px;"/>
                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.courseTitle"/><br/>
                                    <input type="text" id="search-title" class="form-control input-enter"  value="" style="margin-top:10px;"/>
                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.department"/><br/>
                                    <select id="search-division" class="form-control" style="margin-top:10px;"><option value="0">-</option>
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
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
                                <%--<form:form modelAttribute="profCourse" action="${baseUrl}/admin/courseManagement/cOpen" method="post" class="form">
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createCourse"/></h3>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.courseCode"/></label>
                                                <input name="code" type="text" class="form-control"/>
                                                    &lt;%&ndash;<span class="form-text text-muted">Please enter your full name</span>&ndash;%&gt;
                                            </div>
                                        </div>
                                        <div class="col-md-3">

                                            <div class="form-group">
                                                <label><spring:message code="common.divide"/></label>
                                                <form:select path="divide" class="form-control">
                                                    <c:forEach var="d" begin="1" end="5">
                                                        <form:option value="${d}">${d}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                                    &lt;%&ndash;<span class="form-text text-muted">We'll never share your email with anyone else</span>&ndash;%&gt;
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.department"/></label>
                                                <form:select path="userId" class="form-control" >
                                                    <c:forEach var="s" items="${professors}">
                                                        <form:option value="${s.id}">${s.contact.getFullName()}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>

                                    </div>
                                    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.create"/></button>
                                </form:form>--%>
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
        var code = $("#search-code").val().trim();
        var title = $("#search-title").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        $(".table-div").load("${baseUrl}/admin/courseManagement/cOpen/courseTable?division=" + division + "&code=" + code +"&title=" + title);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            search();
        }
    });

    $(document).ready(function() {
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/courseManagement/cOpen";
        </c:if>
        $(".table-div").load("${baseUrl}/admin/courseManagement/cOpen/courseTable");

    });
</script>
</body>
</html>