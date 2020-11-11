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
                            <h3 class="card-title font-weight-bolder"><a class="btn btn-light" href="${baseUrl}/admin/courseManagement/cOpen/manageDivide?courseId=${profCourse.courseId}"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a> &nbsp;&nbsp;&nbsp;${profCourse.semester.year} - ${profCourse.semester.semester}&nbsp;&nbsp;&nbsp;${profCourse.course.code}: ${profCourse.course.title} (<spring:message code="common.divide"/> : ${profCourse.divide})</h3>

                        </div>
                        <div class="card-body">
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6">(<spring:message code="common.option"/> 1) <spring:message code="admin.registerByUpload"/></h3>

                            <div class="row">
                                <div class="col-md-12">
                                    <form:form id="file-form" commandName="uploadedFile" action="${baseUrl}/admin/courseManagement/cOpen/manageStudent/uploadStudent?profCourseId=${profCourse.id}" cssClass="form-horizontal" enctype="multipart/form-data">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label><spring:message code="admin.uploadRegistrationForm"/></label>
                                                    <input type="file" name="file" class="form-control"/>
                                                        <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>


                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group" style="text-align:right">
                                                    <a href="${resources}/form/registration_form.xlsx" class="btn btn-primary btm-sm change-status-row-btn" data-id="" data-to-status="true">1. <spring:message code="admin.downloadRegistrationForm"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="btn btn-primary mr-2">2. <spring:message code="admin.uploadRegistrationForm"/></button>
                                                </div>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>

                            </div>




                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>

                            <h3 class="font-size-lg text-dark font-weight-bold mb-6">(<spring:message code="common.option"/> 2) <spring:message code="admin.registerBySearch"/></h3>
                            <div class="row">
                                <div class="col-md-2">
                                    <spring:message code="common.studentsNumber"/><br/>
                                    <input type="text" id="search-number" class="form-control input-enter" value="" style="margin-top:10px;"/>
                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.studentsName"/><br/>
                                    <input type="text" id="search-name" class="form-control input-enter"  value="" style="margin-top:10px;"/>
                                </div>
                                <div class="col-md-3">
                                    <spring:message code="common.department"/><br/>
                                    <select id="search-division" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="division" items="${divisions}">
                                            <option value="${division.id}">${division.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <%--<div class="col-md-2">
                                    <spring:message code="common.major"/><br/>
                                    <select id="search-major" class="form-control" style="margin-top:10px;">
                                        <c:forEach var="major" items="${majors}">
                                            <option value="${major.id}">${major.name}</option>
                                        </c:forEach>

                                    </select>
                                </div>--%>
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
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>

                            <div class="table-div">


                            </div>




                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>
                            <div class="detail-div">
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.registeredStudents"/></h3>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <table class="table table-head-custom table-vertical-center" id="student-list">
                                                <thead>
                                                <tr class="text-uppercase">

                                                    <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                                    <th style=""><span class="text-primary"><spring:message code="common.studentNumber"/></span></th>
                                                    <th style=""><span class="text-primary"><spring:message code="common.name"/></span></th>
                                                    <th style=""></th>
                                                    <%--<th style="min-width: 150px"><span class="text-primary"><spring:message code="common.major"/></span>--%>

                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="stCourse" items="${studentCourseList}" varStatus="varStatus">
                                                    <tr>

                                                        <td class="pl-0">
                                                                ${varStatus.count}
                                                        </td>
                                                        <td>
                                                                ${stCourse.studentUser.number}
                                                        </td>
                                                        <td>
                                                                ${stCourse.studentUser.contact.getFullName()}
                                                        </td>

                                                        <td>
                                                            <%--<button class="btn btn-light btm-sm add-to-btn" data-id="${studentUser.id}" data-to-status="true"><spring:message code="admin.addToDivide"/></button>--%>
                                                        </td>
                                                    </tr>
                                                </c:forEach>


                                                </tbody>
                                            </table>


                                        </div>
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
        var number = $("#search-number").val().trim();
        var name = $("#search-name").val().trim();
        var division = $("#search-division").children("option:selected").val().trim();
        //var major = $("#search-major").children("option:selected").val().trim();

        $(".table-div").load("${baseUrl}/admin/courseManagement/cOpen/manageStudent/studentTable?profCourseId=${profCourseId}&number=" + number + "&name=" + name + "&division=" + division);
    }



    $(document).ready(function() {
        $(".table-div").load("${baseUrl}/admin/courseManagement/cOpen/manageStudent/studentTable?profCourseId=${profCourseId}");

        $(".input-enter").keydown(function(key) {
            if (key.keyCode == 13) {
                search();
            }
        });

        <c:if test="${not empty result}">
        alert("<spring:message code='common.success'/>");
        location.href="${baseUrl}/admin/studentManagement/studentInformation";
        </c:if>
    });

</script>
</body>
</html>