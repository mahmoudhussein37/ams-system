


<!--begin::Main-->
<!--begin::Header Mobile-->
<div id="kt_header_mobile" class="header-mobile bg-primary  header-mobile-fixed " >
    <!--begin::Logo-->
    <a href="/">
        <img alt="Logo" src="${resources}/images/bst_logo.png" class="max-h-30px" />
        <img alt="Logo" src="${resources}/images/koika_logo.png" class="max-h-30px" />
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
                                <img alt="Logo" src="${resources}/images/koika_logo.png" class="max-h-50px" style="border-radius:5px;"/>
                            </a>
                            <!--end::Logo-->
                            <!--begin::Tab Navs(for desktop mode)-->
                            <c:choose>
                                <c:when test="${currentRole eq 'professor'}">
                                    <ul class="header-tabs nav align-self-end font-size-lg" role="tablist">
                                        <!--begin::Item-->
                                        <li class="nav-item">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/professor/studentGuidance') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.professor.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <!--begin::Item-->
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/professor/classProgress') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
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
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/courseGuide') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.student.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <!--begin::Item-->
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/register') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                <spring:message code="menu.student.main2"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/classInformation') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                                                <spring:message code="menu.student.main3"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/grades') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_4" role="tab">
                                                <spring:message code="menu.student.main4"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/graduation') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                                                <spring:message code="menu.student.main5"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->
                                    </ul>
                                </c:when>
                                <c:when test="${currentRole eq 'admin'}">
                                    <ul class="header-tabs nav align-self-end font-size" role="tablist">
                                        <!--begin::Item-->
<%--                                        <li class="nav-item">
                                            <a href="#" class="nav-link py-4 px-6 active" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.admin.home"/>
                                            </a>
                                        </li>--%>
                                        <!--end::Item-->
                                        <li class="nav-item">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/admin/studentManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.admin.main1"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/admin/profManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                <spring:message code="menu.admin.main2"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/admin/courseManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                                                <spring:message code="menu.admin.main3"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/admin/academicManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_4" role="tab">
                                                <spring:message code="menu.admin.main4"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-3">
                                            <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/admin/systemManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                                                <spring:message code="menu.admin.main5"/>
                                            </a>
                                        </li>
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
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/professor/studentGuidance') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.professor.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/professor/classProgress') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
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
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/student/courseGuide') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.student.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->

                                        <!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/student/register') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                <spring:message code="menu.student.main2"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/student/classInformation') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                                                <spring:message code="menu.student.main3"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/student/grades') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_4" role="tab">
                                                <spring:message code="menu.student.main4"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/student/graduation') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                                                <spring:message code="menu.student.main5"/>
                                            </a>
                                        </li>
                                    </ul>
                                </c:when>
                                <c:when test="${currentRole eq 'admin'}">
                                    <ul class="header-tabs p-5 p-lg-0 d-flex d-lg-none nav nav-bold nav-tabs" role="tablist">
                                        <!--begin::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/admin/studentManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                                                <spring:message code="menu.admin.main1"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->


                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/admin/profManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_2" role="tab">
                                                <spring:message code="menu.admin.main2"/>
                                            </a>
                                        </li>
                                        <!--end::Item-->
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/admin/courseManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                                                <spring:message code="menu.admin.main3"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/admin/academicManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_4" role="tab">
                                                <spring:message code="menu.admin.main4"/>
                                            </a>
                                        </li>
                                        <li class="nav-item mr-2">
                                            <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/admin/systemManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                                                <spring:message code="menu.admin.main5"/>
                                            </a>
                                        </li>
                                    </ul>
                                </c:when>
                            </c:choose>
                            <!--begin::Tab Navs-->

                            <!--begin::Tab Content-->
                            <div class="tab-content">
                                <!--begin::Tab Pane-->
                                <c:choose>
                                    <c:when test="${currentRole eq 'professor'}">
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/professor/studentGuidance') ? 'show active' : ''}" id="kt_header_tab_1">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu" class="header-menu header-menu-mobile header-menu-layout-default ">

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  ${fn:contains(requestUri, '/professor/studentGuidance/studentLookup') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/studentGuidance/studentLookup" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub1_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/studentGuidance/counseling') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/studentGuidance/counseling" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub1_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/studentGuidance/coCourseEnrolment') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/studentGuidance/coCourseEnrolment" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub1_3"/></span></a>
                                                    </li>

                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <!--begin::Tab Pane-->
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/professor/classProgress') ? 'show active' : ''}" id="kt_header_tab_2">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu2" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/classProgress/attendance') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/classProgress/attendance" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/classProgress/inquiryCourse') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/classProgress/inquiryCourse" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/classProgress/syllabus') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/classProgress/syllabus" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/classProgress/makeupClass') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/classProgress/makeupClass" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_4"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/classProgress/classAssessment') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/classProgress/classAssessment" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_5"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/classProgress/registerGrade') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/classProgress/registerGrade" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_6"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/classProgress/cqiReport') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/classProgress/cqiReport" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_7"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/professor/classProgress/graduationResearchPlan') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/professor/classProgress/graduationResearchPlan" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub2_8"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                    </c:when>
                                    <c:when test="${currentRole eq 'student'}">
                                        <div class="tab-pane py-5 p-lg-0  ${fn:contains(requestUri, '/student/courseGuide') ? 'show active' : ''}" id="kt_header_tab_1">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu" class="header-menu header-menu-mobile header-menu-layout-default ">

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/student/courseGuide/yearlyCurriculum') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/courseGuide/yearlyCurriculum" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub1_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/student/courseGuide/courseInfo') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/courseGuide/courseInfo" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub1_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/student/courseGuide/alternative') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/courseGuide/alternative" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub1_3"/></span></a>
                                                    </li>



                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <!--begin::Tab Pane-->
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/student/register') ? 'show active' : ''}" id="kt_header_tab_2">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu2" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/student/register/basic') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/register/basic" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub2_1"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/student/classInformation') ? 'show active' : ''}" id="kt_header_tab_3">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu3" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/student/classInformation/syllabus') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/classInformation/syllabus" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/student/classInformation/enrolment') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/classInformation/enrolment" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/student/classInformation/counselingCourseEnrolment') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/student/classInformation/counselingCourseEnrolment" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/student/classInformation/assessment') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/student/classInformation/assessment" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_4"/></span></a>
                                                    </li>

                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/student/grades') ? 'show active' : ''}" id="kt_header_tab_4">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu4" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  ${fn:contains(requestUri, '/student/grades/inquiryGrade') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/grades/inquiryGrade" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub4_1"/></span></a>
                                                    </li>

                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/student/graduation') ? 'show active' : ''}" id="kt_header_tab_5">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu5" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/student/graduation/graduationRequirements') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/graduation/graduationRequirements" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub5_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/student/graduation/graduationResearchPlan') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/student/graduation/graduationResearchPlan" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub5_2"/></span></a>
                                                    </li>

                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                    </c:when>
                                    <c:when test="${currentRole eq 'admin'}">
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/admin/studentManagement') ? 'show active' : ''}" id="kt_header_tab_1">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu" class="header-menu header-menu-mobile header-menu-layout-default ">
                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/studentManagement/studentRegistration') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                                                        <a  href="/admin/studentManagement/studentRegistration" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub1_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/studentManagement/studentInformation') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/studentManagement/studentInformation" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub1_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/studentManagement/studentProfile') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/studentManagement/studentProfile" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub1_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/studentManagement/studentCounseling') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/studentManagement/studentCounseling" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub1_4"/></span></a>
                                                    </li>

                                                    <%--<li class="menu-item  menu-item-submenu menu-item-rel menu-item-active"  data-menu-toggle="click" aria-haspopup="true">
                                                        <a  href="javascript:;" class="menu-link menu-toggle">
                                                            <span class="menu-text">Admin</span><span class="menu-desc"></span><i class="menu-arrow"></i>
                                                        </a>
                                                        <div class="menu-submenu menu-submenu-classic menu-submenu-left" >
                                                            <ul class="menu-subnav">
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main1"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/studentManagement/studentRegistration" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub1_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/studentManagement/studentInformation" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub1_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/studentManagement/studentProfile" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub1_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/studentManagement/studentCounseling" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub1_4"/></span></a></li>

                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main2"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/profManagement/profRegistration" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub2_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/profManagement/profInformation" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub2_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/profManagement/graduationResearch" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub2_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/profManagement/studentEnrolment" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub2_4"/></span></a></li>

                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main3"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/courseManagement/curriculum" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/courseManagement/course" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/courseManagement/alternative" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/courseManagement/cOpen" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_4"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/courseManagement/attendance" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_5"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/courseManagement/syllabus" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_6"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/courseManagement/makeupClass" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub3_7"/></span></a></li>

                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main4"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/academicManagement/studentGrade" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/academicManagement/graduationCriteria" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/academicManagement/assessmentFactor" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/academicManagement/assessmentResult" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_4"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/academicManagement/cqi" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub4_5"/></span></a></li>
                                                                        </ul>
                                                                    </div>
                                                                </li>
                                                                <li class="menu-item  menu-item-submenu"  data-menu-toggle="hover" aria-haspopup="true"><a  href="javascript:;" class="menu-link menu-toggle"><span class="svg-icon menu-icon"></span><span class="menu-text"><spring:message code="menu.admin.main5"/></span><i class="menu-arrow"></i></a>
                                                                    <div class="menu-submenu menu-submenu-classic menu-submenu-right" >
                                                                        <ul class="menu-subnav">
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/yearSemester" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_1"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/divisionMajor" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_2"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/lectureMethod" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_3"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/evaluationMethod" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_4"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/eduType" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_5"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/equipment" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_6"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/classroom" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_7"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/menu" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_8"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/addAdmin" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_9"/></span></a></li>
                                                                            <li class="menu-item "  aria-haspopup="true"><a  href="/admin/systemManagement/errorReport" class="menu-link "><i class="menu-bullet menu-bullet-dot"><span></span></i><span class="menu-text"><spring:message code="menu.admin.sub5_10"/></span></a></li>
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
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/admin/profManagement') ? 'show active' : ''}" id="kt_header_tab_2">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu2" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/profManagement/profRegistration') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/profManagement/profRegistration" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub2_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/profManagement/profInformation') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/profManagement/profInformation" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub2_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/profManagement/graduationResearch') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/profManagement/graduationResearch" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub2_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/profManagement/studentEnrolment') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/profManagement/studentEnrolment" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub2_4"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/admin/courseManagement') ? 'show active' : ''}" id="kt_header_tab_3">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu3" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/courseManagement/curriculum') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/courseManagement/curriculum" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub3_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/courseManagement/course') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/courseManagement/course" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub3_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/courseManagement/alternative') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/courseManagement/alternative" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub3_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/courseManagement/cOpen') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/courseManagement/cOpen" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub3_4"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/courseManagement/attendance') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/courseManagement/attendance" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub3_5"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/courseManagement/syllabus') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/courseManagement/syllabus" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub3_6"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/courseManagement/makeupClass') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/courseManagement/makeupClass" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub3_7"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/admin/academicManagement') ? 'show active' : ''}" id="kt_header_tab_4">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu4" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/academicManagement/studentGrade') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/academicManagement/studentGrade" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub4_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/academicManagement/graduationCriteria') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/academicManagement/graduationCriteria" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub4_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/academicManagement/assessmentFactor') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/academicManagement/assessmentFactor" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub4_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/academicManagement/assessmentResult') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/academicManagement/assessmentResult" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub4_4"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/academicManagement/cqi') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/academicManagement/cqi" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub4_5"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
                                        <div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/admin/systemManagement') ? 'show active' : ''}" id="kt_header_tab_5">
                                            <!--begin::Menu-->
                                            <div id="kt_header_menu5" class="header-menu header-menu-mobile  header-menu-layout-default " >

                                                <!--begin::Nav-->
                                                <ul class="menu-nav ">
                                                    <li class="menu-item ${fn:contains(requestUri, '/admin/systemManagement/yearSemester') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/yearSemester" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_1"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '//admin/systemManagement/divisionMajor') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/divisionMajor" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_2"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/lectureMethod') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/lectureMethod" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_3"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/evaluationMethod') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/evaluationMethod" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_4"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/eduType') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/eduType" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_5"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/equipment') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/equipment" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_6"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/classroom') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/classroom" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_7"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/menu') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/menu" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_8"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/addAdmin') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/addAdmin" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_9"/></span></a>
                                                    </li>
                                                    <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/errorReport') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                                                        <a  href="/admin/systemManagement/errorReport" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_10"/></span></a>
                                                    </li>
                                                </ul>
                                                <!--end::Nav-->
                                            </div>
                                            <!--end::Menu-->
                                        </div>
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