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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub3_3"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-4">
                                    <spring:message code="common.department"/><br/>
                                    <select id="search-division" class="form-control" style="">
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <%--<div class="col-md-2">
                                    <spring:message code="common.major"/><br/>
                                    <select id="search-major" class="form-control" style="">
                                        &lt;%&ndash;<c:forEach var="major" items="${majors}">
                                            <option value="${major.id}">${major.name}</option>
                                        </c:forEach>&ndash;%&gt;
                                    </select>
                                </div>--%>

                                <div class="col-md-2">
                                    <br/>
                                    <button class="btn btn-primary" style="width:100%;" onclick="search()"><spring:message code="common.search"/></button>
                                </div>
                            </div>
                            <br/><br/>
                            <div class="table-div">


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
        $(".table-div").load("${baseUrl}/admin/courseManagement/alternative/courseTable?division=" + division);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            search();
        }
    });

    $(document).ready(function() {
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/courseManagement/alternative";
        </c:if>

        $(".table-div").load("${baseUrl}/admin/courseManagement/alternative/courseTable");
        //changeMajor("#search-division", "#search-major", true);
        //changeMajor("#divisionId", "#majorId", true);
    });

</script>
</body>
</html>