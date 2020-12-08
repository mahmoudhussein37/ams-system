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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub1_3"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-2">
                                    <spring:message code="common.schoolYear"/><br/>
                                    <select id="search-school-year" class="form-control" style="margin-top:10px;">
                                        <option value="0">-</option>
                                        <c:forEach var="sy" begin="1" end="4">
                                            <option value="${sy}">${sy}</option>
                                        </c:forEach>
                                    </select>
                                </div>


                                <div class="col-md-3">
                                    <spring:message code="common.advisor"/><br/>
                                    <select id="search-advisor" class="form-control" style="margin-top:10px;">
                                        <option value="0">-</option>
                                        <c:forEach var="s" items="${professors}">
                                            <option value="${s.id}">${s.contact.getFullName()}</option>
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
                                <div class="col-md-1">
                                    <br/>
                                    <button class="btn btn-light print" style="width:100%;margin-top:10px;"> <spring:message code="common.print"/></button>
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
        var schoolYear = $("#search-school-year").children("option:selected").val().trim();
        var advisor = $("#search-advisor").children("option:selected").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        //var major = $("#search-major").children("option:selected").val().trim();
        $(".table-div").load("${baseUrl}/admin/studentManagement/studentProfile/studentTable?schoolYear=" + schoolYear + "&advisor=" + advisor + "&division=" + division);
    }



    $(document).ready(function() {
        var schoolYear = $("#search-school-year").children("option:selected").val().trim();
        var advisor = $("#search-advisor").children("option:selected").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        $(".table-div").load("${baseUrl}/admin/studentManagement/studentProfile/studentTable?schoolYear=" + schoolYear + "&advisor=" + advisor + "&division=" + division);

        $(".input-enter").keydown(function(key) {
            if (key.keyCode == 13) {
                search();
            }


        });

        //changeMajor("#search-division", "#search-major", true);
    });
</script>
</body>
</html>