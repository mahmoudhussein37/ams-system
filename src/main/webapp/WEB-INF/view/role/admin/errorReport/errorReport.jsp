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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub5_10"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="table-div">
                                <table class="table table-head-custom table-vertical-center" id="course-list">
                                    <thead>
                                    <tr class="table-secondary text-center">
                                        <th class="pl-0" style=""><spring:message code="common.date"/></th>
                                        <th style=""><spring:message code="common.number"/></th>
                                        <th style=""><spring:message code="common.name"/></th>
                                        <th style=""><spring:message code="common.email"/></th>
                                        <th style=""><spring:message code="admin.errorDetail"/></th>
                                        <th style=""></th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach var="feedback" items="${feedbackList}" varStatus="varStatus">
                                        <tr class="text-center">

                                            <td class="pl-0">
                                                <fmt:formatDate pattern="dd-MMM-yyyy" value="${feedback.datetime}"/>
                                            </td>
                                            <td class="pl-0">
                                                ${feedback.number}
                                            </td>
                                            <td class="pl-0">
                                                    ${feedback.name}
                                            </td>
                                            <td class="pl-0">
                                                    ${feedback.email}
                                            </td>
                                            <td>

                                                ${feedback.contents}

                                            </td>
                                            <td>
                                                <button class="btn btn-light delete-fb" data-id="${feedback.id}"><spring:message code="common.delete"/></button>
                                            </td>

                                        </tr>
                                    </c:forEach>



                                    </tbody>
                                </table>
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
    $(document).ready(function() {
        $("#course-list").DataTable();
        $(".delete-fb").click(function(e) {
           e.preventDefault();
           var id = $(this).attr("data-id");
           $.post("${baseUrl}/admin/systemManagement/errorReport/deleteEr?id=" + id, function() {
              alert("<spring:message code="common.success" javaScriptEscape="true" />");
              location.reload();
           });
        });
    });
</script>
</body>
</html>