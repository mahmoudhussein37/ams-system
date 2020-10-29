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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub2_4"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-2">
                                    <spring:message code="common.year"/><br/>
                                    <select id="search-year" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="y" items="${yearList}">
                                            <option value="${y}">${y}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <spring:message code="common.semester"/><br/>
                                    <select id="search-semester" class="form-control" style="margin-top:10px;">
                                        <option value="1"><spring:message code="common.sem1"/></option>
                                        <option value="2"><spring:message code="common.sem2"/></option>
                                    </select>
                                </div>

                                <div class="col-md-3">
                                    <spring:message code="common.department"/><br/>
                                    <select id="search-division" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.courseTitle"/><br/>
                                    <input type="text" id="search-title" class="form-control input-enter"  value="" style="margin-top:10px;"/>
                                </div>
                                <div class="col-md-2">
                                    <br/>
                                    <button class="btn btn-primary" style="width:100%;margin-top:10px;" onclick="search()"><spring:message code="common.search"/></button>
                                </div>

                            </div>
                            <br/><br/>


                            <h4><spring:message code="student.listOfOpenCourses"/></h4>
                            <div class="table-div">


                            </div>


                            <br/><br/>
                            <hr/>
                            <br/><br/>
                            <div class="print-div">
                                <a href="#" class="btn btn-sm btn-light font-weight-bold">
                                    <spring:message code="common.print"/>
                                </a>
                            </div>
                            <h4><spring:message code="student.listOfAppliedCourses"/></h4>
                            <table class="table table-head-custom table-vertical-center" style="width:100% !important;">
                                <thead>
                                <tr class="text-uppercase">

                                    <th style=""><spring:message code="common.no"/></th>
                                    <th style=""><span class="text-primary"><spring:message code="common.courseCode"/></span></th>
                                    <th style=""><span class="text-primary"><spring:message code="common.courseTitle"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.divide2"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.compCategory"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.subjCategory"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="professor.course.ltlp"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.professor"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.lectureTime"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.classRoom"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.department"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.syllabus"/></span>
                                    <th style=""><span class="text-primary"><spring:message code="common.cancel"/></span>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach var="courseElement" items="${courseList}" varStatus="varStatus">
                                    <tr>

                                        <td class="pl-0">
                                                ${varStatus.count}
                                        </td>
                                        <td>
                                            <a href="#" class="course-detail" data-course-id="${courseElement.id}">
                                                    ${courseElement.code}
                                            </a>
                                        </td>
                                        <td>
                                                ${courseElement.title}

                                        </td>
                                        <td>
                                        </td>
                                        <td>
                                            <spring:message code="comp.category.${courseElement.compCategory}"/>
                                        </td>
                                        <td>
                                            <spring:message code="subj.category.${courseElement.subjCategory}"/>
                                        </td>
                                        <td>

                                        </td>
                                        <td>

                                        </td>

                                        <td>

                                        </td>
                                        <td>
                                            B308
                                        </td>

                                        <td>
                                                ${courseElement.division.name}
                                        </td>
                                        <td>
                                            View
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-primary mr-2"><spring:message code="common.cancel"/></button>
                                        </td>

                                    </tr>
                                </c:forEach>


                                </tbody>
                            </table>


                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <div class="detail-div">

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
        var number = $("#search-number").val().trim();
        var name = $("#search-name").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        var major = $("#search-major").children("option:selected").val().trim();

        $(".table-div").load("${baseUrl}/admin/profManagement/studentEnrolment/courseTable?number=" + number + "&name=" + name + "&division=" + division + "&major=" + major);
    }

    $(".input-enter").keydown(function(key) {
        if (key.keyCode == 13) {
            search();
        }
    });

    $(document).ready(function() {
        $(".table-div").load("${baseUrl}/admin/profManagement/studentEnrolment/courseTable");

    });

</script>
</body>
</html>