<c:choose>
    <c:when test="${currentRole eq 'professor'}">
        <ul class="header-tabs nav align-self-end font-size-lg" role="tablist">
            <!--begin::Item-->
            <li class="nav-item">
                <a href="#" class="nav-link py-4 px-6 ${not empty home and home eq true ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_00" role="tab">
                    <spring:message code="menu.admin.home"/>
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/board/') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_0" role="tab">
                    <spring:message code="menu.professor.main0"/>
                </a>
            </li>
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
            <li class="nav-item">
                <a href="#" class="nav-link py-4 px-6 ${not empty home and home eq true ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_00" role="tab">
                    <spring:message code="menu.admin.home"/>
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/board/') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_0" role="tab">
                    <spring:message code="menu.student.main0"/>
                </a>
            </li>
            <!--begin::Item-->
            <li class="nav-item">
                <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/courseGuide') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_1" role="tab">
                    <spring:message code="menu.student.main1"/>
                </a>
            </li>
            <!--end::Item-->

            <!--begin::Item-->
            <li class="nav-item mr-3">
                <a href="/student/register/basic" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/register/basic') ? 'active' : ''}">
                    <spring:message code="menu.student.main2"/>
                </a>
            </li>
            <li class="nav-item mr-3">
                <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/classInformation') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_3" role="tab">
                    <spring:message code="menu.student.main3"/>
                </a>
            </li>
            <li class="nav-item mr-3">
                <a href="/student/grades/inquiryGrade" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/student/grades/inquiryGrade') ? 'active' : ''}" >
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

            <li class="nav-item">
                <a href="#" class="nav-link py-4 px-6 ${not empty home and home eq true ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_00" role="tab">
                    <spring:message code="menu.admin.home"/>
                </a>
            </li>
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
            <li class="nav-item mr-3">
                <a href="#" class="nav-link py-4 px-6 ${fn:contains(requestUri, '/board/') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_6" role="tab">
                    <spring:message code="menu.admin.main6"/>
                </a>
            </li>

        </ul>
    </c:when>
</c:choose>