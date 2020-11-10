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
<%--            <!--begin::Info-->
            <div class="d-flex align-items-center flex-wrap mr-1">

                <!--begin::Page Heading-->
                <div class="d-flex align-items-baseline flex-wrap mr-5">
                    <!--begin::Page Title-->
                    <h5 class="text-dark font-weight-bold my-1 mr-5">
                        Utilities	                	            </h5>
                    <!--end::Page Title-->

                    <!--begin::Breadcrumb-->
                    <ul class="breadcrumb breadcrumb-transparent breadcrumb-dot font-weight-bold p-0 my-2 font-size-sm">
                        <li class="breadcrumb-item">
                            <a href="" class="text-muted">
                                Features	                        	</a>
                        </li>
                        <li class="breadcrumb-item">
                            <a href="" class="text-muted">
                                Custom	                        	</a>
                        </li>
                        <li class="breadcrumb-item">
                            <a href="" class="text-muted">
                                Utilities	                        	</a>
                        </li>
                    </ul>
                    <!--end::Breadcrumb-->
                </div>
                <!--end::Page Heading-->
            </div>
            <!--end::Info-->--%>
            <div class="row">
                <div class="col-md-12">
                    <!--begin::Card-->

                    <div class="card card-custom">
                        <div class="card-header">
                            <h3 class="card-title font-weight-bolder"><a class="btn btn-light" href="${baseUrl}/admin/courseManagement/cOpen/manageDivide?courseId=${profCourse.courseId}"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a> &nbsp;&nbsp;&nbsp;${profCourse.semester.year} - ${profCourse.semester.semester}&nbsp;&nbsp;&nbsp;${profCourse.course.code}: ${profCourse.course.title} (<spring:message code="common.divide"/> : ${profCourse.divide})</h3>

                        </div>
                        <div class="card-body">
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
        var number = $("#search-number").val().trim();
        var name = $("#search-name").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        //var major = $("#search-major").children("option:selected").val().trim();

        $(".table-div").load("${baseUrl}/admin/courseManagement/cOpen/manageStudent/studentTable?number=" + number + "&name=" + name + "&division=" + division);
    }



    $(document).ready(function() {
        $(".table-div").load("${baseUrl}/admin/courseManagement/cOpen/manageStudent/studentTable");

        $(".input-enter").keydown(function(key) {
            if (key.keyCode == 13) {
                search();
            }
        });

        <c:if test="${not empty result}">
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/studentManagement/studentInformation";
        </c:if>
    });

</script>
</body>
</html>