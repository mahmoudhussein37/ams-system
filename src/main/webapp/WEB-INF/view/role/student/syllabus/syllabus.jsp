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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.student.sub3_1"/></h3>

                        </div>
                        <div class="card-body">
                                <div class="row">
                                    <div class="col-md-2">
                                        <spring:message code="common.year"/><br/>
                                        <select id="search-year" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="y" items="${yearList}">
                                            <option value="${y}">${y}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <spring:message code="common.semester"/><br/>
                                        <select id="search-semester" class="form-control" style="margin-top:10px;">
                                        <option value="1"><spring:message code="common.sem1"/></option>
                                        <option value="2"><spring:message code="common.sem2"/></option>
                                        </select>
                                    </div>

                                    <div class="col-md-3">
                                        <spring:message code="common.division"/><br/>
                                        <select id="search-division" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <spring:message code="common.courseTitle"/><br/>
                                        <input type="text" id="search-title" class="form-control input-enter"  value="" style="margin-top:10px;"/>
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

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>


<%@include file="/WEB-INF/view/include/userPanel.jsp" %>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>

    function searchStudent() {
        var number = $("#search-number").val().trim();
        var name = $("#search-name").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        var major = $("#search-major").children("option:selected").val().trim();

        $(".student-table-div").load("${baseUrl}/professor/studentGuidance/studentLookup/studentTable?number=" + number + "&name=" + name + "&division=" + division + "&major=" + major);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            searchStudent();
        }
    });

    $(document).ready(function() {
        $(".student-table-div").load("${baseUrl}/professor/studentGuidance/studentLookup/studentTable");

    });

</script>
</body>
</html>