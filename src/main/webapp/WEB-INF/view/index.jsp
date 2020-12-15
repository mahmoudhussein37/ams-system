<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->
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
<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >



<!--begin::Main-->
<!--begin::Header Mobile-->
<div id="kt_header_mobile" class="header-mobile bg-primary  header-mobile-fixed " >
    <!--begin::Logo-->
    <a href="/">
        <img alt="Logo" src="${resources}/images/bst_logo_transparent.png" class="max-h-30px" />
        <img alt="Logo" src="${resources}/images/koika_logo_transparent.png" class="max-h-30px" />
    </a>
    <!--end::Logo-->

    <!--begin::Toolbar-->
    <div class="d-flex align-items-center">

        <button class="btn p-0 burger-icon burger-icon-left ml-4" id="kt_header_mobile_toggle">
            <span></span>
        </button>

        <button class="btn p-0 ml-2" id="kt_header_mobile_topbar_toggle">
			<span class="svg-icon svg-icon-xl"><!--begin::Svg Icon | path:assets/media/svg/icons/General/User.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <polygon points="0 0 24 0 24 24 0 24"/>
        <path d="M12,11 C9.790861,11 8,9.209139 8,7 C8,4.790861 9.790861,3 12,3 C14.209139,3 16,4.790861 16,7 C16,9.209139 14.209139,11 12,11 Z" fill="#000000" fill-rule="nonzero" opacity="0.3"/>
        <path d="M3.00065168,20.1992055 C3.38825852,15.4265159 7.26191235,13 11.9833413,13 C16.7712164,13 20.7048837,15.2931929 20.9979143,20.2 C21.0095879,20.3954741 20.9979143,21 20.2466999,21 C16.541124,21 11.0347247,21 3.72750223,21 C3.47671215,21 2.97953825,20.45918 3.00065168,20.1992055 Z" fill="#000000" fill-rule="nonzero"/>
    </g>
</svg><!--end::Svg Icon--></span>		</button>
    </div>
    <!--end::Toolbar-->
</div>
<!--end::Header Mobile-->

<div class="d-flex flex-column">
    <!--begin::Page-->
    <div class="d-flex flex-row flex-column-fluid page">
        <!--begin::Wrapper-->
        <div class="d-flex flex-column flex-row-fluid wrapper" id="kt_wrapper">
            <!--begin::Header-->
            <div id="kt_header" class="header flex-column  header-fixed " >
                <!--begin::Top-->
                <div class="header-top">
                    <!--begin::Container-->
                    <div class=" container ">
                        <!--begin::Left-->
                        <div class="d-none d-lg-flex align-items-center mr-3">
                            <!--begin::Logo-->
                            <a href="/" class="mr-20">
                                <img alt="Logo" src="${resources}/images/bst_logo_transparent.png" class="max-h-50px" style="border-radius:5px;"/>
                                <img alt="Logo" src="${resources}/images/koika_logo_transparent.png" class="max-h-50px" style="border-radius:5px;"/>
                            </a>
                            <!--end::Logo-->
                            <!--begin::Tab Navs(for desktop mode)-->
                            <%@include file="/WEB-INF/view/include/headerNavDesktop.jsp" %>


                            <!--begin::Tab Navs-->
                        </div>
                        <!--end::Left-->

                        <!--begin::Topbar-->
                        <div class="topbar">

                            <sec:authorize access="!isAuthenticated()">
                                <div class="topbar-item">
                                    <a href="/signup" class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2">
                                        <div class="d-flex flex-column text-right pr-lg-3">
                                            <span class="text-white font-weight-bolder font-size-sm d-none d-md-inline"><spring:message code="common.signup"/></span>
                                        </div>

                                    </a>
                                </div>
                                <div class="topbar-item">
                                    <a  href="/signin" class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2">
                                        <div class="d-flex flex-column text-right pr-lg-3">
                                            <span class="text-white font-weight-bolder font-size-sm d-none d-md-inline"><spring:message code="common.signin"/></span>
                                        </div>

                                    </a>
                                </div>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <!--begin::User-->
                                <div class="topbar-item">
                                    <div class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2" >
                                        <div class="d-flex flex-column text-right pr-lg-3">
                                            <span class="text-white opacity-50 font-weight-bold font-size-sm d-none d-md-inline">${user.number}</span>
                                            <span class="text-white font-weight-bolder font-size-sm d-none d-md-inline">${user.getFullName()}</span>
                                        </div>
                                        <span class="symbol symbol-35">
                                                <%--<span class="symbol-label font-size-h5 font-weight-bold text-white bg-white-o-30"><i class="fa fa-user"></i></span>--%>
                                        </span>
                                    </div>
                                    <div class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2" >
                                        <a href="/signout">
                                    <span class="symbol symbol-sm-30">
									<span class="symbol-label font-size-h5 font-weight-bold text-white bg-white-o-30"><i class="fa fa-sign-out-alt"></i></span>
                                    </span>
                                        </a>
                                    </div>
                                </div>
                            </sec:authorize>
                            <!--end::User-->
                        </div>
                        <!--end::Topbar-->
                    </div>
                    <!--end::Container-->
                </div>
                <!--end::Top-->

                <!--begin::Bottom-->

                <!--end::Bottom-->
            </div>
            <!--end::Header-->
        </div>
    </div>
</div>
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
                            sf
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
                                            <a href="/admin/board/${boardName}/${article.id}">${article.subject}</a>
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
                                <c:set var="boardName" value="notice"/>
                                <c:forEach var="article" items="${deList}" varStatus="varStatus">
                                    <tr>

                                        <td style="width:120px;">

                                            <fmt:formatDate pattern="dd MMM, yyyy" value="${article.registeredDate}"/>
                                        </td>
                                        <td class="pl-0">
                                            <a href="/admin/board/${boardName}/${article.id}">${article.subject}</a>
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
ㄴ
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
                                <c:set var="boardName" value="notice"/>
                                <c:forEach var="article" items="${hireList}" varStatus="varStatus">
                                    <tr>

                                        <td style="width:120px;">

                                            <fmt:formatDate pattern="dd MMM, yyyy" value="${article.registeredDate}"/>
                                        </td>
                                        <td class="pl-0">
                                            <a href="/admin/board/${boardName}/${article.id}">${article.subject}</a>
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