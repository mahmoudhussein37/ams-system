<div class="tab-pane py-5 p-lg-0 ${not empty home and home eq true ? 'show active' : ''}" id="kt_header_tab_00">
    <!--begin::Menu-->
    <div id="kt_header_menu00" class="header-menu header-menu-mobile header-menu-layout-default ">
        <!--begin::Nav-->
        <ul class="menu-nav ">
            <li class="menu-item  ${not empty home and home eq true ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.dashboard"/></span></a>
            </li>

        </ul>
        <!--end::Nav-->
    </div>
    <!--end::Menu-->
</div>
<div class="tab-pane py-5 p-lg-0  ${fn:contains(requestUri, '/board/') ? 'show active' : ''}" id="kt_header_tab_0">
    <!--begin::Menu-->
    <div id="kt_header_menu0" class="header-menu header-menu-mobile header-menu-layout-default ">

        <!--begin::Nav-->
        <ul class="menu-nav ">

            <li class="menu-item ${fn:contains(requestUri, '/board/notice') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/board/notice/list" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub0_1"/></span></a>
            </li>
            <li class="menu-item ${fn:contains(requestUri, '/board/de') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/board/de/list" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub0_2"/></span></a>
            </li>
            <li class="menu-item ${fn:contains(requestUri, '/board/hire') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/board/hire/list" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub0_3"/></span></a>
            </li>

        </ul>
        <!--end::Nav-->
    </div>
    <!--end::Menu-->
</div>
<div class="tab-pane py-5 p-lg-0  ${fn:contains(requestUri, '/student/courseGuide') ? 'show active' : ''}" id="kt_header_tab_1">
    <!--begin::Menu-->
    <div id="kt_header_menu1" class="header-menu header-menu-mobile header-menu-layout-default ">

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
            <li class="menu-item  ${fn:contains(requestUri, '/student/classInformation/classAssessment') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/student/classInformation/classAssessment" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub3_4"/></span></a>
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