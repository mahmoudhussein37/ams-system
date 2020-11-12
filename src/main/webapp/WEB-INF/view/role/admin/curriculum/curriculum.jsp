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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub3_1"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-2">
                                    <spring:message code="common.year"/><br/>
                                    <select id="search-year" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="y" items="${yearList}">
                                            <option value="${y}" ${y eq year ? 'selected' : ''}>${y}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <%--<div class="col-md-4">
                                    <spring:message code="common.department"/><br/>
                                    <select id="search-division" class="form-control" style="margin-top:10px;"><option value="0">-</option>
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>--%>

                                <div class="col-md-2">
                                    <br/>
                                    <button class="btn btn-primary" style="width:100%;margin-top:10px;" onclick="search()"><spring:message code="common.search"/></button>
                                </div>
                                <%--<div class="col-md-2">
                                    <br/>
                                    <button class="btn btn-primary" style="width:100%;margin-top:10px;"><spring:message code="common.new"/></button>
                                </div>--%>
                                <div class="col-md-2">
                                </div>
                            </div>
                            <br/><br/>



                            <div class="table-div">
                                <table class="table table-head-custom table-vertical-center" id="course-list">
                                    <thead>
                                    <tr class="text-uppercase">

                                        <th class="pl-0"><spring:message code="common.no"/></th>
                                        <th><span class="text-primary"><spring:message code="common.year"/></span></th>
                                        <th><span class="text-primary"><spring:message code="common.department"/></span></th>
                                        <th><span class="text-primary"><spring:message code="common.download"/></span></th>
                                        <th></th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="division" items="${divisions}" varStatus="varStatus">
                                        <tr>

                                            <td class="pl-0">
                                                ${varStatus.count}
                                            </td>
                                            <td>
                                                    ${year}
                                            </td>
                                            <td>
                                                ${division.name}
                                            </td>
                                            <td>

                                                <c:forEach var="uf" items="${uploadedFiles}">
                                                    <c:if test="${uf.year eq year and uf.divisionId eq division.id}">
                                                        <a href="${baseUrl}/download?uploadedFileId=${uf.id}"><spring:message code="common.download"/></a>
                                                    </c:if>
                                                </c:forEach>

                                            </td>
                                            <td>
                                                <a href="${baseUrl}/admin/courseManagement/curriculum/uploadCurriculum?year=${year}&divisionId=${division.id}" class="btn btn-primary mr-2"><spring:message code="common.upload"/></a>
                                                <c:forEach var="uf" items="${uploadedFiles}">
                                                    <c:if test="${uf.year eq year and uf.divisionId eq division.id}">
                                                        <button type="button" class="btn btn-primary mr-2 delete-file" data-id="${uf.id}"><spring:message code="common.delete"/></button>
                                                    </c:if>
                                                </c:forEach>

                                            </td>


                                        </tr>
                                    </c:forEach>



                                    </tbody>
                                </table>

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
        var year = $("#search-year").val().trim();
        location.href="${baseUrl}/admin/courseManagement/curriculum?year=" + year;

        /*$(".table-div").load("${baseUrl}/admin/courseManagement/curriculum/courseTable?year=" + year + "&division=" + division);*/
    }



    $(document).ready(function() {
        $(".delete-file").click(function(e) {
           e.preventDefault();
           var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/deleteFile?uploadedFileId=" + id, function(result) {
               alert("<spring:message code="common.success"/>");
               location.reload();
            });

        });

    });

</script>
</body>
</html>