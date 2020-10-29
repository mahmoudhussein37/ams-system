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
                            <%@include file="/WEB-INF/view/include/studentTableSearchDiv.jsp" %>

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

        $(".table-div").load("${baseUrl}/admin/studentManagement/studentProfile/studentTable?number=" + number + "&name=" + name + "&division=" + division);
    }



    $(document).ready(function() {
        $(".table-div").load("${baseUrl}/admin/studentManagement/studentProfile/studentTable");

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