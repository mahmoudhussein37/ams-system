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
                            <h3 class="card-title font-weight-bolder">
                                <%--<a class="btn btn-light" href="${baseUrl}/admin/courseManagement/cOpen"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a> &nbsp;&nbsp;&nbsp;${profCourse.course.code}: ${profCourse.course.title}--%>

                                <a class="btn btn-light" href="${baseUrl}/admin/courseManagement/cOpen/manageDivide?courseId=${profCourse.courseId}"><i class="fa fa-arrow-left"></i> <spring:message code="common.back"/></a> &nbsp;&nbsp;&nbsp;[${profCourse.semester.year} - ${profCourse.semester.semester}] ${profCourse.course.code}: ${profCourse.course.title} - <spring:message code="common.divide"/> : ${profCourse.divide}</h3>

                        </div>
                        <div class="card-body">


                            <form:form modelAttribute="profCourse" action="${baseUrl}/admin/courseManagement/cOpen/editDivide?profCourseId=${profCourse.id}" method="post" class="form">
                                <form:hidden path="id" value="${profCourse.id}"/>
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.editDivide"/></h3>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.courseCode"/></label>
                                                <input type="text" class="form-control" disabled value="${profCourse.course.code}"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.courseTitle"/></label>
                                                <input type="text" class="form-control" disabled value="${profCourse.course.title}"/>
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
                                                <label><spring:message code="common.schoolYear"/></label>
                                                <form:input type="number" path="schoolYear" class="form-control"/>
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
                                            <label><spring:message code="common.language"/></label>
                                            <form:input type="text" path="language" class="form-control"/>
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
                                </div>
                                    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.edit"/></button>
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


    $(document).ready(function() {

    });

</script>
</body>
</html>