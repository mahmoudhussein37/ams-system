<div class="tab-pane py-5 p-lg-0 ${not empty home and home eq true ? 'show active' : ''}" id="kt_header_tab_00">
    <!--begin::Menu-->
    <div id="kt_header_menu0" class="header-menu header-menu-mobile header-menu-layout-default ">
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
<div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/admin/studentManagement') ? 'show active' : ''}" id="kt_header_tab_1">
    <!--begin::Menu-->
    <div id="kt_header_menu1" class="header-menu header-menu-mobile header-menu-layout-default ">
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
            <li class="menu-item  ${fn:contains(requestUri, '/admin/studentManagement/inquiryGrade') ? 'menu-item-active' : ''} "  aria-haspopup="true">
                <a  href="/admin/studentManagement/inquiryGrade" class="menu-link "><span class="menu-text"><spring:message code="menu.student.sub4_1"/></span></a>
            </li>
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
            <%--<li class="menu-item ${fn:contains(requestUri, '/admin/profManagement/studentEnrolment') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/admin/profManagement/studentEnrolment" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub2_4"/></span></a>
            </li>--%>
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
            <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/divisionMajor') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/admin/systemManagement/divisionMajor" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_2"/></span></a>
            </li>
            <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/lectureMethod') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/admin/systemManagement/lectureMethod" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_3"/></span></a>
            </li>
            <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/evaluationMethod') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/admin/systemManagement/evaluationMethod" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_4"/></span></a>
            </li>
            <li class="menu-item  ${fn:contains(requestUri, '/admin/systemManagement/educationalMedium') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/admin/systemManagement/educationalMedium" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub5_5"/></span></a>
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
<div class="tab-pane py-5 p-lg-0 ${fn:contains(requestUri, '/board/') ? 'show active' : ''}" id="kt_header_tab_6">
    <!--begin::Menu-->
    <div id="kt_header_menu6" class="header-menu header-menu-mobile  header-menu-layout-default " >

        <!--begin::Nav-->
        <ul class="menu-nav ">
            <li class="menu-item ${(fn:contains(requestUri, '/board/notice') or (not empty home and home eq true)) ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/board/notice/list" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub6_1"/></span></a>
            </li>
            <li class="menu-item ${fn:contains(requestUri, '/board/de') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/board/de/list" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub6_2"/></span></a>
            </li>
            <li class="menu-item ${fn:contains(requestUri, '/board/hire') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/board/hire/list" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub6_3"/></span></a>
            </li>
            <li class="menu-item ${fn:contains(requestUri, '/board/schedule') ? 'menu-item-active' : ''}"  aria-haspopup="true">
                <a  href="/board/schedule/list" class="menu-link "><span class="menu-text"><spring:message code="menu.admin.sub6_4"/></span></a>
            </li>

        </ul>
        <!--end::Nav-->
    </div>
    <!--end::Menu-->
</div>