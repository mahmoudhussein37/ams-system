<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>
<%--<sec:authorize access="!hasRole('ROLE_ADMIN')">
    <style>

        .header-bottom {
            display:none !important;
        }
        @media (min-width: 992px) {
            .header-fixed .header {
                height:70px;
            }
            .header-fixed .wrapper {
                padding-top: 100px;
            }
        }

        @media (max-width: 991.98px) {

            .header-mobile-fixed .wrapper {
                padding-top: 70px;
            }
        }
    </style>
</sec:authorize>--%>
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
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <a href="/board/${boardName}/form" class="btn btn-primary"><spring:message code="admin.board.write"/></a>
                            <br/><br/><br/>
                            </sec:authorize>

                            <table class="table table-head-custom table-vertical-center" id="course-list">
                                <thead>
                                <tr class="text-uppercase">

                                    <th class="pl-0" style=""><spring:message code="common.date"/></th>
                                    <th style=""><span class="text-primary"><spring:message code="admin.board.subject"/></span></th>
                                    <th class="pl-0" style=""><spring:message code="admin.board.hit"/></th>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th style=""></th>
                                    </sec:authorize>
                                </tr>
                                </thead>
                                <tbody>


                                <tr>
                                    <td data-column-data="registeredDate" data-column-searchable="false" data-column-orderable="true" data-column-order="desc" data-column-class="pl-0" data-column-width="10%"></td>
                                    <%--<td data-column-data="typeId" data-column-render="renderManuscriptType" data-column-orderable="true" data-column-class="cellCenter" data-column-width="50px"></td>
                                    <c:if test="${cnid != 'icic' && cnid != 'icsmb' && cnid != 'icsmb2020' }">
                                        <td data-column-data="presentationTypeId" data-column-render="renderPresentationType" data-column-orderable="true" data-column-class="cellCenter" data-column-width="50px"></td>
                                    </c:if>
                                    <td data-column-data="trackId" data-column-searchable="false" data-column-render="renderTrack" data-column-visible="${conference.co.manageTrack}" data-column-width="70px"></td>
                                    <td data-column-render="renderAuthors" data-column-orderable="false" data-column-searchable="false" data-column-width="70px"></td>
                                    <td data-column-data="title" data-column-render="renderTitleLink" data-column-width="120px"></td>
                                    <td data-column-data="submitDate" data-column-render="renderDate" data-column-class="cellCenter" data-column-width="70px"></td>
                                    <td data-column-data="status" data-column-render="renderStatus" data-column-width="70px"></td>
                                    <td data-column-data="id" data-column-render="renderButtonCompleted" data-column-orderable="false" data-column-searchable="false" data-column-class="cellCenter" data-column-width="70px"></td>--%>
                                </tr>
                                <%--<c:forEach var="article" items="${articleList}" varStatus="varStatus">
                                    <tr>

                                        <td class="pl-0" style="width:10%">

                                            <fmt:formatDate pattern="dd-MMM-yyyy hh:mm:ss" value="${article.registeredDate}"/>
                                        </td>
                                        <td class="pl-0" style="width:60%">
                                            <a href="/board/${boardName}/${article.id}">${article.subject}</a>
                                        </td>
                                        <td style="width:10%">
                                                ${article.hit}
                                        </td>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <td style="width:20%">
                                            <a href="/board/${boardName}/edit/${article.id}" class="btn btn-secondary"><spring:message code="common.edit"/></a>
                                            <button class="btn btn-secondary delete-btn" data-article-id="${article.id}"><spring:message code="common.delete"/></button>
                                        </td>
                                        </sec:authorize>
                                    </tr>
                                </c:forEach>--%>


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
<script src="${resources}/js/tableAjax.js" type="text/javascript"></script>
<script>

    TableAjax.init($('#course-list'), '/board/${boardName}/listAjax', "${locale}");


/*    $("#course-list").DataTable({
        "order": [[ 0, "desc" ]]
    });*/
    $(document).ready(function() {


        $(".delete-btn").click(function(e) {
           e.preventDefault();
            var r = confirm("<spring:message code="admin.board.confirm" javaScriptEscape="true" />");
            if (r == true) {
                var articleId = $(this).attr("data-article-id");
                $.post("/board/${boardName}/delete/" + articleId, function() {
                   location.reload();
                });
            }



        });

    });
</script>
</body>