<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>
<!--begin::Content-->
<div class="content  d-flex flex-column flex-column-fluid" id="kt_content">
    <!--begin::Entry-->
    <div class="d-flex flex-column-fluid">
        <!--begin::Container-->
        <div class=" container ">
            <div class="row">
                <div class="col-md-12">
                    <!--begin::Card-->
                    <div class="card card-custom">

                        <div class="card-header">


                            <h3 class="card-title font-weight-bolder"><a class="btn btn-light" href="/admin/courseManagement/alternative"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a> &nbsp;&nbsp;&nbsp;${course.code}: ${course.title}</h3>

                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-2">
                                    <spring:message code="common.courseCode"/><br/>
                                    <input type="text" id="search-code" class="form-control input-enter" value="" style="margin-top:10px;"/>
                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.courseTitle"/><br/>
                                    <input type="text" id="search-title" class="form-control input-enter"  value="" style="margin-top:10px;"/>
                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.department"/><br/>
                                    <select id="search-division" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-1">
                                    <br/>
                                    <button class="btn btn-primary" style="width:100%;margin-top:10px;" onclick="search()"><spring:message code="common.search"/></button>
                                </div>
                                <div class="col-md-1">
                                    <br/>
                                    <button class="btn btn-light" style="width:100%;margin-top:10px;" onclick="javascript:location.reload()"><spring:message code="common.reset"/></button>
                                </div>
                            </div>
                            <br/><br/>
                            <div class="table-div">


                            </div>
                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <div class="detail-div">

                                <ul class="nav nav-tabs nav-tabs-line">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="common.alternative"/></a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2"><spring:message code="common.prerequisite"/></a>
                                    </li>

                                </ul>
                                <div class="tab-content mt-5" id="myTabContent">
                                    <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_1">

                                        <table class="table rounded">
                                            <thead>
                                            <tr class="table-secondary text-center">

                                                <td>
                                                    <spring:message code="common.no"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.oldCourseCode"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.oldCourseTitle"/>
                                                </td>

                                                <td>
                                                    <spring:message code="common.subjCategory"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.registeredDate"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.use"/>
                                                </td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="altCount" value="1"/>
                                            <c:forEach var="ac" items="${altCourses}" varStatus="varStatus">
                                                <c:if test="${ac.type eq 'alternative'}">
                                                <tr class="table-light text-center">

                                                    <td>
                                                        ${altCount}
                                                    </td>
                                                    <td>
                                                        ${ac.course.code}
                                                    </td>
                                                    <td>
                                                            ${ac.course.title}
                                                    </td>
                                                    <td>
                                                        <spring:message code="subj.category.${ac.course.subjCategory}"/>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate pattern="dd-MMM-yyyy" value="${ac.course.registeredDate}"/>
                                                    </td>
                                                    <td>
                                                        ${ac.course.enabled ? 'Y' : 'N'}
                                                    </td>

                                                </tr>
                                                    <c:set var="altCount" value="${altCount + 1}"/>
                                                </c:if>
                                            </c:forEach>
                                            <%--<tr class="table-light text-center">
                                                <td>
                                                    1
                                                </td>
                                                <td>
                                                    IFA140
                                                </td>
                                                <td>
                                                    Electronic circuit
                                                </td>
                                                <td>
                                                    Major
                                                </td>

                                                <td>
                                                    5-3-2-0
                                                </td>
                                                <td>
                                                    YYYY-MM-dd
                                                </td>
                                                <td>
                                                    Y
                                                </td>

                                            </tr>--%>


                                            </tbody>
                                        </table>


                                    </div>
                                    <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
                                        <table class="table rounded">
                                            <thead>
                                            <tr class="table-secondary text-center">

                                                <td>
                                                    <spring:message code="common.no"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.oldCourseCode"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.oldCourseTitle"/>
                                                </td>

                                                <td>
                                                    <spring:message code="common.subjCategory"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.registeredDate"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.use"/>
                                                </td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="preCount" value="1"/>
                                            <c:forEach var="ac" items="${altCourses}" varStatus="varStatus">
                                                <c:if test="${ac.type eq 'prerequisite'}">
                                                    <tr class="table-light text-center">

                                                        <td>
                                                                ${preCount}
                                                        </td>
                                                        <td>
                                                                ${ac.course.code}
                                                        </td>
                                                        <td>
                                                                ${ac.course.title}
                                                        </td>
                                                        <td>
                                                            <spring:message code="subj.category.${ac.course.subjCategory}"/>
                                                        </td>
                                                        <td>
                                                            <fmt:formatDate pattern="dd-MMM-yyyy" value="${ac.course.registeredDate}"/>
                                                        </td>
                                                        <td>
                                                                ${ac.course.enabled ? 'Y' : 'N'}
                                                        </td>

                                                    </tr>
                                                    <c:set var="preCount" value="${preCount + 1}"/>
                                                </c:if>
                                            </c:forEach>
                                            <%--<tr class="table-light text-center">
                                                <td>
                                                    1
                                                </td>
                                                <td>
                                                    IFA140
                                                </td>
                                                <td>
                                                    Electronic circuit
                                                </td>
                                                <td>
                                                    IFA130
                                                </td>
                                                <td>
                                                    Electronic Circuit Practice
                                                </td>
                                                <td>
                                                    Major
                                                </td>

                                                <td>
                                                    5-3-2-0
                                                </td>
                                                <td>
                                                    YYYY-MM-dd
                                                </td>
                                                <td>
                                                    Y
                                                </td>

                                            </tr>--%>


                                            </tbody>
                                        </table>
                                    </div>


                                </div>
                            </div>

                        </div>
                    </div>
                    <!--end::Card-->
                </div>
            </div>


        </div>
        <!--end::Container-->
    </div>
    <!--end::Entry-->
</div>
<!--end::Content-->

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>


<%@include file="/WEB-INF/view/include/userPanel.jsp" %>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>

    function search() {
        var code = $("#search-code").val().trim();
        var title = $("#search-title").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        $(".table-div").load("${baseUrl}/admin/courseManagement/alternative/altCourseTable?targetCourseId=${course.id}&division=" + division + "&code=" + code +"&title=" + title);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            search();
        }
    });

    $(document).ready(function() {


        $(".table-div").load("${baseUrl}/admin/courseManagement/alternative/altCourseTable?targetCourseId=${course.id}");
        //changeMajor("#search-division", "#search-major", true);
        //changeMajor("#divisionId", "#majorId", true);
    });

</script>
</body>
</html>