<%--<div class="tab-pane py-5 p-lg-0 ${not empty home and home eq true ? 'show active' : ''}" id="kt_header_tab_00">
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
</div>--%>
<div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/board/') ? 'show active' : ''}" id="kt_header_tab_0">
    <!--begin::Menu-->
    <div id="kt_header_menu0" class="header-menu header-menu-mobile header-menu-layout-default ">

        <!--begin::Nav-->
        <ul class="menu-nav ">
            <li class="menu-item  ${fn:contains(requestUri, '/board/notice') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/board/notice/list" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub0_1"/></span></a>
            </li>
            <li class="menu-item ${fn:contains(requestUri, '/board/de') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/board/de/list" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub0_2"/></span></a>
            </li>
            <li class="menu-item ${fn:contains(requestUri, '/board/hire') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/board/hire/list" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub0_3"/></span></a>
            </li>
        </ul>
        <!--end::Nav-->
    </div>
    <!--end::Menu-->
</div>
<div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/professor/studentGuidance') ? 'show active' : ''}" id="kt_header_tab_1">
    <!--begin::Menu-->
    <div id="kt_header_menu1" class="header-menu header-menu-mobile header-menu-layout-default ">

        <!--begin::Nav-->
        <ul class="menu-nav ">
            <li class="menu-item  ${fn:contains(requestUri, '/professor/studentGuidance/studentLookup') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/professor/studentGuidance/studentLookup" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub1_1"/></span></a>
            </li>
            <li class="menu-item ${fn:contains(requestUri, '/professor/studentGuidance/counseling') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/professor/studentGuidance/counseling" class="menu-link "><span class="menu-text"><spring:message code="menu.professor.sub1_2"/></span></a>
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