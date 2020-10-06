<%@include file="/WEB-INF/view/include/top-tag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/header-bar.jsp" %>
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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.professor.sub1_2"/></h3>
                            <%--<div class="card-toolbar">
                                <div class="dropdown dropdown-inline">
                                    <a href="#" class="btn btn-transparent-white btn-sm font-weight-bolder dropdown-toggle px-5" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Export
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">
                                        <!--begin::Navigation-->
                                        <ul class="navi navi-hover">
                                            <li class="navi-header pb-1">
                                                <span class="text-primary text-uppercase font-weight-bold font-size-sm">Add new:</span>
                                            </li>
                                            <li class="navi-item">
                                                <a href="#" class="navi-link">
                                                    <span class="navi-icon"><i class="flaticon2-shopping-cart-1"></i></span>
                                                    <span class="navi-text">Order</span>
                                                </a>
                                            </li>
                                            <li class="navi-item">
                                                <a href="#" class="navi-link">
                                                    <span class="navi-icon"><i class="flaticon2-calendar-8"></i></span>
                                                    <span class="navi-text">Event</span>
                                                </a>
                                            </li>
                                            <li class="navi-item">
                                                <a href="#" class="navi-link">
                                                    <span class="navi-icon"><i class="flaticon2-graph-1"></i></span>
                                                    <span class="navi-text">Report</span>
                                                </a>
                                            </li>
                                            <li class="navi-item">
                                                <a href="#" class="navi-link">
                                                    <span class="navi-icon"><i class="flaticon2-rocket-1"></i></span>
                                                    <span class="navi-text">Post</span>
                                                </a>
                                            </li>
                                            <li class="navi-item">
                                                <a href="#" class="navi-link">
                                                    <span class="navi-icon"><i class="flaticon2-writing"></i></span>
                                                    <span class="navi-text">File</span>
                                                </a>
                                            </li>
                                        </ul>
                                        <!--end::Navigation-->
                                    </div>
                                </div>
                            </div>--%>
                        </div>
                        <div class="card-body">
                                <div class="row">
                                    <div class="col-md-3">
                                        <spring:message code="common.studentsNumber"/><br/>
                                        <input type="text" id="search-number" class="form-control input-enter" value="" style="margin-top:10px;"/>
                                    </div>
                                    <div class="col-md-3">
                                        <spring:message code="common.studentsName"/><br/>
                                        <input type="text" id="search-name" class="form-control input-enter"  value="" style="margin-top:10px;"/>
                                    </div>
                                    <div class="col-md-2">
                                        <spring:message code="common.division"/><br/>
                                        <select id="search-division" class="form-control" style="margin-top:10px;"/>
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <spring:message code="common.major"/><br/>
                                        <select id="search-major" class="form-control" style="margin-top:10px;"/>
                                        <c:forEach var="major" items="${majors}">
                                            <option value="${major.id}">${major.name}</option>
                                        </c:forEach>

                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <br/>
                                        <button class="btn btn-primary" style="width:100%;margin-top:10px;" onclick="searchStudent()">Search</button>
                                    </div>

                                </div>
                            <br/><br/>



                            <div class="student-table-div">


                            </div>





                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <div class="student-detail-div">

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

<%@include file="/WEB-INF/view/include/footer-bar.jsp" %>


<%@include file="/WEB-INF/view/include/user-panel.jsp" %>


<%@include file="/WEB-INF/view/include/footer-script.jsp" %>

<script>

    function searchStudent() {
        var number = $("#search-number").val().trim();
        var name = $("#search-name").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        var major = $("#search-major").children("option:selected").val().trim();

        $(".student-table-div").load("/professor/studentTable?number=" + number + "&name=" + name + "&division=" + division + "&major=" + major);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            searchStudent();
        }
    });

    $(document).ready(function() {
        $(".student-table-div").load("/professor/studentTable");

    });

</script>
</body>
</html>