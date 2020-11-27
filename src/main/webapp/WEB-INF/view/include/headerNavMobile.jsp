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
            <%--<li class="nav-item mr-2">
                <a href="#" class="nav-link btn btn-clean ${fn:contains(requestUri, '/admin/boardManagement') ? 'active' : ''}" data-toggle="tab" data-target="#kt_header_tab_5" role="tab">
                    <spring:message code="menu.admin.main6"/>
                </a>
            </li>--%>
        </ul>
    </c:when>
</c:choose>