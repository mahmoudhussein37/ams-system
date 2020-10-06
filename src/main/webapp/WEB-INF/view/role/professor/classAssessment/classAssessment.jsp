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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.professor.sub2_5"/></h3>
                        </div>
                        <div class="card-body">
                                <div class="row">

                                    <div class="col-md-2">
                                        <spring:message code="common.year"/><br/>
                                        <select id="search-year" class="form-control" style="margin-top:10px;"/>
                                        <c:forEach var="y" items="${yearList}">
                                            <option value="${y}">${y}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <spring:message code="common.semester"/><br/>
                                        <select id="search-semester" class="form-control" style="margin-top:10px;"/>
                                        <option value="1"><spring:message code="common.sem1"/></option>
                                        <option value="2"><spring:message code="common.sem2"/></option>
                                        </select>
                                    </div>

                                    <div class="col-md-2">
                                        <br/>
                                        <button class="btn btn-primary" style="width:100%;margin-top:10px;" onclick="searchCourse()"><spring:message code="common.search"/></button>
                                    </div>
                                    <div class="col-md-2">

                                    </div>




                                </div>
                            <br/><br/>



                            <div class="course-table-div">


                            </div>





                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <div class="course-detail-div">

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

    function searchCourse() {
        var year = $("#search-year").children("option:selected").val().trim();
        var semester = $("#search-semester").children("option:selected").val().trim();
        $(".course-table-div").load("/professor/classProgress/syllabus/courseTable?year=" + year + "&semester=" + semester);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            searchCourse();
        }
    });

    $(document).ready(function() {
        $(".course-table-div").load("/professor/classProgress/syllabus/courseTable");
    });

</script>
</body>
</html>