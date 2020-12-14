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

                            <h3 class="card-title font-weight-bolder">

                                <%@include file="/WEB-INF/view/role/common/board/boardName.jsp" %>
                            </h3>

                        </div>
                        <div class="card-body">
                            <a href="/admin/board/${boardName}/form" class="btn btn-primary"><spring:message code="admin.board.write"/></a>
                            <br/><br/><br/>


                            <table class="table table-head-custom table-vertical-center" id="course-list">
                                <thead>
                                <tr class="text-uppercase">

                                    <%--<th class="pl-0" style=""><spring:message code="common.date"/></th>--%>
                                        <th class="pl-0" style=""><spring:message code="admin.board.date"/></th>
                                    <th style=""><span class="text-primary"><spring:message code="admin.board.schedule"/></span></th>

                                    <th style=""></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="article" items="${articleList}" varStatus="varStatus">
                                    <tr>

                                        <%--<td class="pl-0" style="width:10%">

                                            <fmt:formatDate pattern="dd-MMM-yyyy hh:mm:ss" value="${article.registeredDate}"/>
                                        </td>--%>
                                            <td style="width:10%">
                                                    ${article.content}
                                            </td>
                                        <td class="pl-0" style="width:60%">
                                                ${article.subject}
                                        </td>

                                        <td style="width:20%">
                                            <a href="/admin/board/${boardName}/edit/${article.id}" class="btn btn-secondary"><spring:message code="common.edit"/></a>
                                            <button class="btn btn-secondary delete-btn" data-article-id="${article.id}"><spring:message code="common.delete"/></button>
                                        </td>
                                    </tr>
                                </c:forEach>


                                </tbody>
                            </table>

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
    $("#course-list").DataTable({
        "order": [[ 0, "desc" ]]
    });
    $(document).ready(function() {


        $(".delete-btn").click(function(e) {
           e.preventDefault();
            var r = confirm("<spring:message code="admin.board.confirm"/>");
            if (r == true) {
                var articleId = $(this).attr("data-article-id");
                $.post("/admin/board/${boardName}/delete/" + articleId, function() {
                   location.reload();
                });
            }



        });

    });
</script>
</body>