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
<%--            <!--begin::Info-->
            <div class="d-flex align-items-center flex-wrap mr-1">

                <!--begin::Page Heading-->
                <div class="d-flex align-items-baseline flex-wrap mr-5">
                    <!--begin::Page Title-->
                    <h5 class="text-dark font-weight-bold my-1 mr-5">
                        Utilities	                	            </h5>
                    <!--end::Page Title-->

                    <!--begin::Breadcrumb-->
                    <ul class="breadcrumb breadcrumb-transparent breadcrumb-dot font-weight-bold p-0 my-2 font-size-sm">
                        <li class="breadcrumb-item">
                            <a href="" class="text-muted">
                                Features	                        	</a>
                        </li>
                        <li class="breadcrumb-item">
                            <a href="" class="text-muted">
                                Custom	                        	</a>
                        </li>
                        <li class="breadcrumb-item">
                            <a href="" class="text-muted">
                                Utilities	                        	</a>
                        </li>
                    </ul>
                    <!--end::Breadcrumb-->
                </div>
                <!--end::Page Heading-->
            </div>
            <!--end::Info-->--%>
            <div class="row">
                <div class="col-md-12">
                    <!--begin::Card-->

                    <div class="card card-custom">
                        <div class="card-header">
                            <h3 class="card-title font-weight-bolder"><a class="btn btn-light" href="${baseUrl}/admin/courseManagement/cOpen"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a> &nbsp;&nbsp;&nbsp;${course.code}: ${course.title}</h3>

                        </div>
                        <div class="card-body">
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createdDivide"/></h3>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <table class="table rounded" id="course-list">
                                            <thead>
                                            <tr class="table-secondary text-center">
                                                <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                                <td>
                                                    <spring:message code="common.semester"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.courseCode"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.courseTitle"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.divide"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.professor"/>
                                                </td>
                                                <td>
                                                    <spring:message code="professor.course.limitStudent"/>

                                                </td>
                                                <td>
                                                    <spring:message code="professor.course.numStudent"/>

                                                </td>
                                                <td>

                                                </td>


                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="pc" items="${professorCourseList}" varStatus="varStatus">
                                            <c:if test="${pc.enabled}">
                                            <tr class="text-center">
                                                <td>
                                                    ${varStatus.count}
                                                </td>
                                                <td>
                                                        ${pc.semester.year} - ${pc.semester.semester}
                                                </td>
                                                <td>
                                                        ${pc.course.code}
                                                </td>
                                                <td>
                                                        ${pc.course.title}
                                                </td>
                                                <td>
                                                        ${pc.divide}
                                                </td>
                                                <td>
                                                        ${pc.professorUser.contact.getFullName()}
                                                </td>
                                                <td>
                                                        ${pc.limitStudent}
                                                </td>
                                                <td>
                                                        ${pc.numStudent}
                                                </td>
                                                <td>
                                                    <a style="width:100%;margin-bottom:10px;" href="${baseUrl}/admin/courseManagement/cOpen/editDivide?profCourseId=${pc.id}" class="btn btn-light btm-sm" data-id="${pc.id}"><spring:message code="admin.editDivide"/></a><br/>
                                                    <a style="width:100%;margin-bottom:10px;" href="${baseUrl}/admin/courseManagement/cOpen/manageTime?profCourseId=${pc.id}" class="btn btn-light btm-sm" data-id="${pc.id}"><spring:message code="admin.manageTimeTable"/></a><br/>
                                                    <a style="width:100%;margin-bottom:10px;" href="${baseUrl}/admin/courseManagement/cOpen/manageStudent?profCourseId=${pc.id}" class="btn btn-light btm-sm" data-id="${pc.id}"><spring:message code="admin.registerStudents"/></a><br/>
                                                    <button style="width:100%;margin-bottom:10px;" class="btn btn-light btm-sm change-status-row-btn" data-id="${pc.id}" data-to-status="false"><spring:message code="common.disable"/></button><br/>
                                                    <button style="width:100%;margin-bottom:10px;" class="btn btn-light btm-sm delete-row-btn" data-id="${pc.id}"><spring:message code="common.delete"/></button>
                                                </td>
                                            </tr>
                                            </c:if>
                                            <c:if test="${not pc.enabled}">
                                            <tr class="text-center disabled-tr">
                                                <td>
                                                        ${varStatus.count}
                                                </td>
                                                <td>
                                                        ${pc.semester.year} - ${pc.semester.semester}
                                                </td>
                                                <td>
                                                        ${pc.course.code}
                                                </td>
                                                <td>
                                                        ${pc.course.title}
                                                </td>
                                                <td>
                                                        ${pc.divide}
                                                </td>
                                                <td>
                                                        ${pc.professorUser.contact.getFullName()}
                                                </td>
                                                <td>
                                                        ${pc.limitStudent}
                                                </td>
                                                <td>
                                                        ${pc.numStudent}
                                                </td>
                                                <td>
                                                    <button class="btn btn-light btm-sm change-status-row-btn" data-id="${pc.id}" data-to-status="true"><spring:message code="common.enable"/></button>
                                                </td>
                                            </tr>
                                            </c:if>
                                            </c:forEach>

                                        </table>


                                    </div>
                                </div>
                            </div>

                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <form:form modelAttribute="profCourse" action="${baseUrl}/admin/courseManagement/cOpen/manageDivide?courseId=${course.id}" method="post" class="form">
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.createDivide"/></h3>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.courseCode"/></label>
                                                <input type="text" class="form-control" disabled value="${course.code}"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.courseTitle"/></label>
                                                <input type="text" class="form-control" disabled value="${course.title}"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.schoolYear"/></label>
                                                <form:input type="text" path="schoolYear" class="form-control" disabled="true" value="${course.schoolYear}"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.semester"/></label>
                                                <form:select path="semesterId" class="form-control" >
                                                    <c:forEach var="s" items="${semesters}">
                                                        <form:option value="${s.id}">${s.year} - ${s.semester}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-3">

                                            <div class="form-group">
                                                <label><spring:message code="common.divide"/></label>
                                                <form:select path="divide" class="form-control">
                                                    <c:forEach var="d" begin="1" end="5">
                                                        <form:option value="${d}">${d}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.professor"/></label>
                                                <form:select path="userId" class="form-control" >
                                                    <c:forEach var="s" items="${professors}">
                                                        <form:option value="${s.id}">${s.contact.getFullName()}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.lectureLanguage"/></label>
                                                <form:input type="text" path="language" class="form-control"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                    </div>
                                <div class="row">

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="professor.course.limitStudent"/></label>
                                            <form:input type="number" path="limitStudent" class="form-control"/>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.classRoom"/></label>
                                            <form:select path="classroom" class="form-control" >
                                                <form:option value="0">-</form:option>
                                                <c:forEach var="c" items="${classroomList}">
                                                    <form:option value="${c.id}">${c.code}</form:option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.engAccreditation"/></label>
                                            <form:select path="engAccreditation" class="form-control" >
                                                <form:option value="true">Y</form:option>
                                                <form:option value="false">N</form:option>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="professor.secondTestDivide"/></label>
                                            <form:select path="secondTest" class="form-control" >
                                                <form:option value="true">Y</form:option>
                                                <form:option value="false">N</form:option>
                                            </form:select>
                                        </div>
                                    </div>

                                </div>
                                    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.create"/></button>
                                </form:form>


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

    $("#course-list").DataTable();

    $(document).ready(function() {
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/courseManagement/cOpen/manageDivide?courseId=${course.id}";
        </c:if>
        $("body").on("click", ".delete-row-btn", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/courseManagement/cOpen/manageDivide/deleteDivide?id=" + id, function(result) {
                if(result != true) {
                    alert("<spring:message code="admin.deleteProfCourseAlert"/>");
                }

                location.reload();

            });
        });

        $("body").on("click", ".change-status-row-btn", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var status = $(this).attr("data-to-status");
            $.post("${baseUrl}/admin/courseManagement/cOpen/manageDivide/changeStatus?id=" + id + "&status=" + status, function(result) {
                if(result == true) {
                    location.reload();
                }

            });
        });
    });

</script>
</body>
</html>