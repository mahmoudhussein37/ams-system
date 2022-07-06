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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub1_4"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="row">


                                <%--<div class="col-md-2">
                                    <button class="btn btn-secondary" style="width:100%;" ><spring:message code="common.delete"/></button>
                                </div>
                                <div class="col-md-2">
                                    <button class="btn btn-secondary" style="width:100%;" ><spring:message code="common.save"/></button>
                                </div>--%>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-md-3">
                                    <spring:message code="common.year"/><br/>
                                    <select id="search-year" class="form-control" style="margin-top:10px;">
<option value="0">-</option>
                                        <c:forEach var="y" items="${yearList}">

<option value="${y}">${y}</option>
                                        </c:forEach>
                                        </select>

                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.studentsName"/><br/>
                                    <input type="text" id="search-name" class="form-control input-enter"  value="" style="margin-top:10px;"/>
                                </div>
                                <div class="col-md-2">
                                    <br/>
                                    <button class="btn btn-primary" style="width:100%;margin-top:10px;" onclick="search()"><spring:message code="common.search"/></button>
                                </div>
                                <div class="col-md-1">
                                    <br/>
                                    <button class="btn btn-light" onclick="printChecked()" style="width:100%;margin-top:10px;"> <spring:message code="common.print"/></button>
                                </div>
                            </div>
                            <br/><br/>

                            <div class="table-div"></div>
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
        var year = $("#search-year").children("option:selected").val().trim();
        var name = $("#search-name").val().trim();
        $(".table-div").load("${baseUrl}/admin/studentManagement/studentCounseling/counselingTable?year=" + year + "&name=" + name);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            search();
        }
    });

    $(document).ready(function() {
        $(".table-div").load("${baseUrl}/admin/studentManagement/studentCounseling/counselingTable");

    });
</script>
</body>
</html>