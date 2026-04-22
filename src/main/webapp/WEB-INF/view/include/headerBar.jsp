<!--begin::Main-->
<!--begin::Header Mobile-->
<div id="kt_header_mobile" class="header-mobile bg-primary  header-mobile-fixed ">
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
            <span class="svg-icon svg-icon-xl"><!--begin::Svg Icon | path:assets/media/svg/icons/General/User.svg--><svg
                    xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px"
                    height="24px" viewBox="0 0 24 24" version="1.1">
                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                        <polygon points="0 0 24 0 24 24 0 24" />
                        <path
                            d="M12,11 C9.790861,11 8,9.209139 8,7 C8,4.790861 9.790861,3 12,3 C14.209139,3 16,4.790861 16,7 C16,9.209139 14.209139,11 12,11 Z"
                            fill="#000000" fill-rule="nonzero" opacity="0.3" />
                        <path
                            d="M3.00065168,20.1992055 C3.38825852,15.4265159 7.26191235,13 11.9833413,13 C16.7712164,13 20.7048837,15.2931929 20.9979143,20.2 C21.0095879,20.3954741 20.9979143,21 20.2466999,21 C16.541124,21 11.0347247,21 3.72750223,21 C3.47671215,21 2.97953825,20.45918 3.00065168,20.1992055 Z"
                            fill="#000000" fill-rule="nonzero" />
                    </g>
                </svg><!--end::Svg Icon--></span> </button>
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
            <div id="kt_header" class="header flex-column  header-fixed ">
                <!--begin::Top-->
                <div class="header-top">
                    <!--begin::Container-->
                    <div class=" container ">
                        <!--begin::Left-->
                        <div class="d-none d-lg-flex align-items-center mr-3">
                            <!--begin::Logo-->
                            <a href="/" class="mr-20">
                                <img alt="Logo" src="${resources}/images/bst_logo_transparent.png" class="max-h-50px"
                                    style="border-radius:5px;" />
                                <img alt="Logo" src="${resources}/images/koika_logo_transparent.png" class="max-h-50px"
                                    style="border-radius:5px;" />
                            </a>
                            <!--end::Logo-->
                            <!--begin::Tab Navs(for desktop mode)-->
                            <%@include file="/WEB-INF/view/include/headerNavDesktop.jsp" %>


                                <!--begin::Tab Navs-->
                        </div>
                        <!--end::Left-->

                        <!--begin::Topbar-->
                        <div class="topbar">

                            <!--begin::Language Switcher-->
                            <c:set var="currentPath"
                                value="${requestScope['javax.servlet.forward.request_uri'] != null ? requestScope['javax.servlet.forward.request_uri'] : pageContext.request.requestURI}" />
                            <div class="topbar-item d-flex align-items-center">
                                <div class="btn-group btn-group-sm" role="group" aria-label="Language"
                                    style="border: 1px solid rgba(255,255,255,0.25); border-radius: 4px;">
                                    <a href="${currentPath}?lang=en"
                                        class="btn btn-sm d-flex align-items-center px-3 py-1 ${locale == 'en' ? 'btn-white' : ''}"
                                        style="${locale == 'en' ? 'color: #6993FF;' : 'color: rgba(255,255,255,0.7); background: transparent;'}">
                                        <img src="${resources}/images/language/en.png" alt=""
                                            style="width: 16px; height: 11px;" />
                                        <span class="font-weight-bold font-size-xs d-none d-md-inline"
                                            style="margin-${isRTL ? 'right' : 'left'}: 4px;">EN</span>
                                    </a>
                                    <a href="${currentPath}?lang=ar"
                                        class="btn btn-sm d-flex align-items-center px-3 py-1 ${locale == 'ar' ? 'btn-white' : ''}"
                                        style="${locale == 'ar' ? 'color: #6993FF;' : 'color: rgba(255,255,255,0.7); background: transparent;'}">
                                        <img src="${resources}/images/language/ar.png" alt=""
                                            style="width: 16px; height: 11px;" />
                                        <span class="font-weight-bold font-size-xs d-none d-md-inline"
                                            style="margin-${isRTL ? 'right' : 'left'}: 4px;">AR</span>
                                    </a>
                                </div>
                            </div>
                            <!--end::Language Switcher-->
                            <sec:authorize access="!isAuthenticated()">
                                <div class="topbar-item">
                                    <a href="/signup"
                                        class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2">
                                        <div class="d-flex flex-column text-right pr-lg-3">
                                            <span class="text-white font-weight-bolder font-size-sm d-none d-md-inline">
                                                <spring:message code="common.signup" />
                                            </span>
                                        </div>

                                    </a>
                                </div>
                                <div class="topbar-item">
                                    <a href="/signin"
                                        class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2">
                                        <div class="d-flex flex-column text-right pr-lg-3">
                                            <span class="text-white font-weight-bolder font-size-sm d-none d-md-inline">
                                                <spring:message code="common.signin" />
                                            </span>
                                        </div>

                                    </a>
                                </div>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <!--begin::User-->
                                <div class="topbar-item">
                                    <div
                                        class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2">
                                        <div class="d-flex flex-column text-right pr-lg-3">
                                            <span
                                                class="text-white opacity-50 font-weight-bold font-size-sm d-none d-md-inline">${user.number}</span>
                                            <span
                                                class="text-white font-weight-bolder font-size-sm d-none d-md-inline">${user.getFullName()}</span>
                                        </div>
                                        <span class="symbol symbol-35">
                                            <%--<span
                                                class="symbol-label font-size-h5 font-weight-bold text-white bg-white-o-30"><i
                                                    class="fa fa-user"></i></span>--%>
                                        </span>
                                    </div>
                                    <div
                                        class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2">
                                        <a href="#" id="logout-link" onclick="logout(); return false;">
                                            <span class="symbol symbol-sm-30">
                                                <span
                                                    class="symbol-label font-size-h5 font-weight-bold text-white bg-white-o-30"><i
                                                        class="fa fa-sign-out-alt"></i></span>
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
                <div class="header-bottom">
                    <!--begin::Container-->
                    <div class=" container ">
                        <!--begin::Header Menu Wrapper-->
                        <div class="header-navs header-navs-left" id="kt_header_navs">
                            <!--begin::Tab Navs(for tablet and mobile modes)-->
                            <%@include file="/WEB-INF/view/include/headerNavMobile.jsp" %>
                                <!--begin::Tab Navs-->

                                <!--begin::Tab Content-->
                                <div class="tab-content">
                                    <!--begin::Tab Pane-->
                                    <c:choose>
                                        <c:when test="${currentRole eq 'professor'}">
                                            <%@include file="/WEB-INF/view/include/headerMenuProfessor.jsp" %>
                                        </c:when>
                                        <c:when test="${currentRole eq 'student'}">
                                            <%@include file="/WEB-INF/view/include/headerMenuStudent.jsp" %>
                                        </c:when>
                                        <c:when test="${currentRole eq 'admin'}">
                                            <%@include file="/WEB-INF/view/include/headerMenuAdmin.jsp" %>
                                        </c:when>
                                    </c:choose>
                                    <!--begin::Tab Pane-->

                                    <!--begin::Tab Pane-->
                                </div>
                                <!--end::Tab Content-->
                        </div>
                        <!--end::Header Menu Wrapper-->
                    </div>
                    <!--end::Container-->
                </div>
                <!--end::Bottom-->
            </div>
            <!--end::Header-->
        </div>
    </div>
</div>