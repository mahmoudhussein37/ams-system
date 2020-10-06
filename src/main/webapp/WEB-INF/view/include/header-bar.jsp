


<!--begin::Main-->
<!--begin::Header Mobile-->
<div id="kt_header_mobile" class="header-mobile bg-primary  header-mobile-fixed " >
    <!--begin::Logo-->
    <a href="/">
        <img alt="Logo" src="${resources}/images/bst_logo.png" class="max-h-30px" />
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
                                <img alt="Logo" src="${resources}/images/bst_logo.png" class="max-h-50px" style="border-radius:5px;"/>
                            </a>
                            <!--end::Logo-->
                            <!--begin::Tab Navs(for desktop mode)-->
                            <c:choose>
                                <c:when test="${currentRole eq 'professor'}">
                                    <ul class="header-tabs nav align-self-end font-size-lg" role="tablist">
                                        <!--begin::Item-->
                                        <li class="nav-item">
                                            <a href="#" class="nav-link py-4 px-6 active" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.professor.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <!--begin::Item-->
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                <spring:message code="menu.professor.main2"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->
                                    </ul>
                                </c:when>
                                <c:when test="${currentRole eq 'student'}">
                                    <ul class="header-tabs nav align-self-end font-size-lg" role="tablist">
                                        <!--begin::Item-->
                                        <li class="nav-item">
                                            <a href="#" class="nav-link py-4 px-6 active" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.student.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <!--begin::Item-->
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                <spring:message code="menu.student.main2"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                                                <spring:message code="menu.student.main3"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_4" role="tab">
                                                <spring:message code="menu.student.main4"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                                                <spring:message code="menu.student.main5"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->
                                    </ul>
                                </c:when>
                                <c:when test="${currentRole eq 'admin'}">
                                    <ul class="header-tabs nav align-self-end font-size-lg" role="tablist">
                                        <!--begin::Item-->
                                        <li class="nav-item">
                                            <a href="#" class="nav-link py-4 px-6 active" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.admin.home"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <%--<li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                Prof. user management
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                                                Course management
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_4" role="tab">
                                                Academic management
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                                                System management
                                            </a>
                                        </li>--%>
                                    </ul>
                                </c:when>
                            </c:choose>


                            <!--begin::Tab Navs-->
                        </div>
                        <!--end::Left-->

                        <!--begin::Topbar-->
                        <div class="topbar bg-primary">

                            <sec:authorize access="!isAuthenticated()">
                                <div class="topbar-item">
                                    <a href="/signup" class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2">
                                        <div class="d-flex flex-column text-right pr-lg-3">
                                            <span class="text-white font-weight-bolder font-size-sm d-none d-md-inline">Signup</span>
                                        </div>

                                    </a>
                                </div>
                                <div class="topbar-item">
                                    <a  href="/signin" class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2">
                                        <div class="d-flex flex-column text-right pr-lg-3">
                                            <span class="text-white font-weight-bolder font-size-sm d-none d-md-inline">Signin</span>
                                        </div>

                                    </a>
                                </div>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                            <!--begin::User-->
                            <div class="topbar-item">
                                <div class="btn btn-icon btn-hover-transparent-white w-lg-auto d-flex align-items-center btn-lg px-2" id="kt_quick_user_toggle">
                                    <div class="d-flex flex-column text-right pr-lg-3">
                                        <span class="text-white opacity-50 font-weight-bold font-size-sm d-none d-md-inline">${user.number}</span>
                                        <span class="text-white font-weight-bolder font-size-sm d-none d-md-inline">${user.getFullName()}</span>
                                    </div>
                                    <span class="symbol symbol-35">
									<span class="symbol-label font-size-h5 font-weight-bold text-white bg-white-o-30"><i class="fa fa-user"></i></span>
								</span>
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

                            <c:choose>
                                <c:when test="${currentRole eq 'professor'}">
                                    <ul class="header-tabs p-5 p-lg-0 d-flex d-lg-none nav nav-bold nav-tabs" role="tablist">
                                        <!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean active" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.professor.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                <spring:message code="menu.professor.main2"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->
                                    </ul>
                                </c:when>
                                <c:when test="${currentRole eq 'student'}">
                                    <ul class="header-tabs p-5 p-lg-0 d-flex d-lg-none nav nav-bold nav-tabs" role="tablist">
                                        <!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean active" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.student.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                <spring:message code="menu.student.main2"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                                                <spring:message code="menu.student.main3"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_4" role="tab">
                                                <spring:message code="menu.student.main4"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                                                <spring:message code="menu.student.main5"/>
                                            </a>
                                        </li>
                                    </ul>
                                </c:when>
                                <c:when test="${currentRole eq 'admin'}">
                                    <ul class="header-tabs p-5 p-lg-0 d-flex d-lg-none nav nav-bold nav-tabs" role="tablist">
                                        <!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean active" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.admin.home"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <%--!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                Prof. user management
                                            </a>
                                        </li>
                                        <!--end::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                                                Course management
                                            </a>
                                        </li>
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_4" role="tab">
                                                Academic management
                                            </a>
                                        </li>
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                                                System management
                                            </a>
                                        </li>--%>
                                    </ul>
                                </c:when>
                            </c:choose>
                            <!--begin::Tab Navs-->

                            <!--begin::Tab Content-->
                            <div class="tab-content">
                                <!--begin::Tab Pane-->
                                <c:choose>
                                    <c:when test="${currentRole eq 'professor'}">
                                        <div class="tab-pane py-5 p-lg-0 show active" id="kt_header_tab_1">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu" class="header-menu header-menu-mobile header-menu-layout-default ">

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  ${fn:contains(requestUri, '/professor/studentLookup') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/studentLookup" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub1_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/counseling') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/counseling" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub1_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub1_3"/></span></a>
                                                    </li>


                                                        <%--<li class="menu-item  menu-item-submenu menu-item-rel menu-item-active"  data-menu-toggle="click" aria-haspopup="true">
                                                            <a  href="javascript:;" class="menu-link menu-toggle">
                                                                <span class="menu-text">Features</span><span class="menu-desc"></span><i class="menu-arrow"></i>
                                                            </a>
                                                            <div class="menu-submenu menu-submenu-classic menu-submenu-left" >
                                                                <ul class="menu-subnav">
                                                                    <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text">Bootstrap</span><i class="menu-arrow"></i></a>
                                                                        <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                            <ul class="menu-subnav">
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/typography.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Typography</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/buttons.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Buttons</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/button-group.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Button Group</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Dropdown</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/navs.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Navs</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/tables.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Tables</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/progress.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Progress</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/modal.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Modal</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/alerts.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Alerts</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/popover.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Popover</span></a></li>
                                                                                <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/tooltip.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text">Tooltip</span></a></li>
                                                                            </ul>
                                                                        </div>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </li>--%>

                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <!--begin::Tab Pane-->
                                        <div class="tab-pane py-5 p-lg-0" id="kt_header_tab_2">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu2" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_4"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_5"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_6"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_7"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_8"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                    </c:when>
                                    <c:when test="${currentRole eq 'student'}">
                                        <div class="tab-pane py-5 p-lg-0 show active" id="kt_header_tab_1">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu" class="header-menu header-menu-mobile header-menu-layout-default ">

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub1_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub1_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub1_3"/></span></a>
                                                    </li>



                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <!--begin::Tab Pane-->
                                        <div class="tab-pane py-5 p-lg-0" id="kt_header_tab_2">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu2" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub2_1"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0" id="kt_header_tab_3">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu3" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_4"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0" id="kt_header_tab_4">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu4" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub4_1"/></span></a>
                                                    </li>

                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0" id="kt_header_tab_5">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu5" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub5_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub5_2"/></span></a>
                                                    </li>

                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                    </c:when>
                                    <c:when test="${currentRole eq 'admin'}">
                                        <div class="tab-pane py-5 p-lg-0 show active" id="kt_header_tab_1">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu" class="header-menu header-menu-mobile header-menu-layout-default ">
                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
<%--                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Student registration</span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Student information Management</span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Student Profile Synthesis</span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Student Counseling Synthesis</span></a>
                                                    </li>--%>

                                                    <li class="menu-item  menu-item-submenu menu-item-rel menu-item-active"  data-menu-toggle="click" aria-haspopup="true">
                                                        <a  href="javascript:;" class="menu-link menu-toggle">
                                                            <span class="menu-text">Admin</span><span class="menu-desc"></span><i class="menu-arrow"></i>
                                                        </a>
                                                        <div class="menu-submenu menu-submenu-classic menu-submenu-left" >
                                                            <ul class="menu-subnav">
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main1"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/typography.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub1_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/buttons.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub1_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/button-group.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub1_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub1_4"/></span></a></li>

                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main2"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/typography.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub2_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/buttons.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub2_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/button-group.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub2_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub2_4"/></span></a></li>

                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main3"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/typography.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/buttons.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/button-group.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_4"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_5"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_6"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_7"/></span></a></li>

                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main4"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/typography.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/buttons.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/button-group.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_4"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_5"/></span></a></li>
                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main5"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/typography.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/buttons.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/button-group.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_4"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_5"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_6"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_7"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_8"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_9"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="features/bootstrap/dropdown.html" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_10"/></span></a></li>
                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </li>

                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <!--begin::Tab Pane-->
                                        <%--<div class="tab-pane py-5 p-lg-0" id="kt_header_tab_2">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu2" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Professor registration</span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Professor information Management</span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Graduation Research Plan Synthesis</span></a>
                                                    </li>
                                                    <li class="menu-item "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Student Enrolment Counseling Synthesis</span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0" id="kt_header_tab_3">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu3" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Curriculum Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Course Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Alternative and Prerequisite Course Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Course Open Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Attendance Synthesis</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Syllabus Synthesis</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Makeup Class List Synthesis</span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0" id="kt_header_tab_4">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu4" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Student's Grade Synthesis</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Graduation Criteria Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Course Assessment Factor Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Course Assessment Result Synthesis</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">CQI-Report Synthesis</span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0" id="kt_header_tab_5">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu5" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  menu-item-active "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Year-Semester Registration</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Department(division) / Major Hierarchy Registration</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Lecture Method Setting</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Evaluation Method Setting</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Type of Educational Medium Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Equipment and Tools Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Class room Management</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Menu access period setting</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Add Administrator ID</span></a>
                                                    </li>
                                                    <li class="menu-item  "  aria-haspopup="true">
                                                        <a  href="index.html" class="menu-link "><span class="menu-text">Error Report</span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>--%>
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