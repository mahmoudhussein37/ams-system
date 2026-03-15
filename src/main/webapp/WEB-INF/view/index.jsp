<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>
<%--<sec:authorize access="!hasRole('ROLE_ADMIN')">
    <style>


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
            <!--begin::Dashboard-->
            <!--begin::Row-->
            <div class="row">
                <div class="col-lg-8">
                    <!--begin::Base Table Widget 2-->
                    <div class="card card-custom card-stretch gutter-b">
                        <!--begin::Header-->
                        <div class="card-header border-0 pt-5">
                            <h3 class="card-title align-items-start flex-column">
                                <span class="card-label font-weight-bolder text-dark"><spring:message code="common.board.schedule"/></span>
                            </h3>

                        </div>
                        <!--end::Header-->

                        <!--begin::Body-->
                        <div class="card-body pt-0">
                            <table class="table table-head-custom table-vertical-center notice-table">
                                <tbody>
                                    <c:forEach var="article" items="${scheduleList}" varStatus="varStatus">
                                        <tr>
                                            <td style="width:120px;">
                                                    ${article.content}
                                            </td>
                                            <td class="pl-0">
                                                <strong>${article.subject}</strong>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!--end::Body-->
                    </div>
                    <!--end::Base Table Widget 2-->
                </div>
                <div class="col-lg-4">
                    <!--begin::Mixed Widget 16-->
                    <div class="card card-custom card-stretch gutter-b">
                        <!--begin::Header-->
                        <div class="card-header border-0 pt-5">
                            <div class="card-title">
                                <div class="card-label">
                                    <div class="font-weight-bolder"><spring:message code="common.board.univInfo"/></div>
                                </div>
                            </div>
                        </div>
                        <!--end::Header-->

                        <!--begin::Body-->
                        <div class="card-body pt-0">
                            <spring:message code="common.universityDescription"/>
                        </div>
                        <!--end::Body-->
                    </div>
                    <!--end::Mixed Widget 16-->
                </div>

            </div>
            <!--end::Row-->
            <!--begin::Row-->
            <div class="row">
                <div class="col-xl-4">
                    <!--begin::Mixed Widget 2-->
                    <div class="card card-custom gutter-b card-stretch">
                        <!--begin::Header-->
                        <div class="card-header border-0 pt-5">
                            <div class="card-title font-weight-bolder">
                                <div class="card-label">
                                    <spring:message code="common.board.notice"/>

                                </div>
                            </div>

                        </div>
                        <!--end::Header-->

                        <!--begin::Body-->
                        <div class="card-body pt-0">
                            <table class="table table-head-custom table-vertical-center notice-table">
                                <tbody>
                                <c:set var="boardName" value="notice"/>
                                <c:forEach var="article" items="${noticeList}" varStatus="varStatus">
                                    <tr>

                                        <td style="width:120px;">

                                            <fmt:formatDate pattern="dd MMM, yyyy" value="${article.registeredDate}"/>
                                        </td>
                                        <td class="pl-0">
                                            <a href="/board/${boardName}/${article.id}">${article.subject}</a>
                                        </td>
                                    </tr>
                                </c:forEach>


                                </tbody>
                            </table>
                        </div>
                        <!--end::Body-->
                    </div>
                    <!--end::Mixed Widget 2-->
                </div>
                <div class="col-xl-4">
                    <!--begin::Mixed Widget 18-->
                    <div class="card card-custom gutter-b card-stretch">
                        <!--begin::Header-->
                        <div class="card-header border-0 pt-5">
                            <div class="card-title font-weight-bolder">
                                <div class="card-label">
                                    <spring:message code="common.board.de"/>

                                </div>
                            </div>

                        </div>
                        <!--end::Header-->

                        <!--begin::Body-->
                        <div class="card-body pt-0">
                            <table class="table table-head-custom table-vertical-center notice-table">
                                <tbody>
                                <c:set var="boardName" value="de"/>
                                <c:forEach var="article" items="${deList}" varStatus="varStatus">
                                    <tr>

                                        <td style="width:120px;">

                                            <fmt:formatDate pattern="dd MMM, yyyy" value="${article.registeredDate}"/>
                                        </td>
                                        <td class="pl-0">
                                            <a href="/board/${boardName}/${article.id}">${article.subject}</a>
                                        </td>
                                    </tr>
                                </c:forEach>


                                </tbody>
                            </table>
                        </div>
                        <!--end::Body-->
                    </div>
                    <!--end::Mixed Widget 18-->
                </div>
                <div class="col-xl-4">
                    <!--begin::List Widget 7-->
                    <div class="card card-custom gutter-b card-stretch">
                        <!--begin::Header-->
                        <div class="card-header border-0 pt-5">
                            <h3 class="card-title font-weight-bolder text-dark"><spring:message code="common.board.link"/></h3>
                        </div>
                        <!--end::Header-->

                        <!--begin::Body-->
                        <div class="card-body pt-0">
                            <table class="table table-head-custom table-vertical-center notice-table">
                                <tbody>
                                <tr>
                                    <td class="pl-0">
                                        - <a target="_blank" href="https://btu.edu.eg/en/home/"><spring:message code="common.universityLink"/></a>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="pl-0">
                                        - <a target="_blank" href="https://www.koreatech.ac.kr">Koreatech</a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                        </div>
                        <!--end::Body-->
                    </div>
                    <!--end::List Widget 7-->
                </div>
            </div>
            <!--end::Row-->
            <div class="row">
                <div class="col-lg-8">
                    <!--begin::Base Table Widget 2-->
                    <div class="card card-custom card-stretch gutter-b">
                        <!--begin::Header-->
                        <div class="card-header border-0 pt-5">
                            <h3 class="card-title align-items-start flex-column">
                                <span class="card-label font-weight-bolder text-dark"><spring:message code="common.board.hire"/></span>

                            </h3>

                        </div>
                        <!--end::Header-->

                        <!--begin::Body-->
                        <div class="card-body pt-0">
                            <table class="table table-head-custom table-vertical-center notice-table">
                                <tbody>
                                <c:set var="boardName" value="hire"/>
                                <c:forEach var="article" items="${hireList}" varStatus="varStatus">
                                    <tr>

                                        <td style="width:120px;">

                                            <fmt:formatDate pattern="dd MMM, yyyy" value="${article.registeredDate}"/>
                                        </td>
                                        <td class="pl-0">
                                            <a href="/board/${boardName}/${article.id}">${article.subject}</a>
                                        </td>
                                    </tr>
                                </c:forEach>


                                </tbody>
                            </table>
                        </div>
                        <!--end::Body-->
                    </div>
                    <!--end::Base Table Widget 2-->
                </div>


            </div>


            <!--end::Dashboard-->
        </div>
        <!--end::Container-->
    </div>
    <!--end::Entry-->
</div>
<!--end::Content-->

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>
</div>
<!--end::Wrapper-->
</div>
<!--end::Page-->
</div>
<!--end::Main-->

<%@include file="/WEB-INF/view/include/userPanel.jsp" %>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
</body>
</html>