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
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub3_2"/></h3>

                        </div>
                        <div class="card-body">
                            <div>
                                <form:form modelAttribute="course" action="${baseUrl}/admin/courseManagement/course/editCourse" method="post" class="form">
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="admin.editCourse"/></h3>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.courseCode"/></label>
                                                <form:input path="code" type="text" class="form-control"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">

                                            <div class="form-group">
                                                <label><spring:message code="common.courseTitle"/></label>
                                                <form:input path="title" type="text" class="form-control"/>
                                                    <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.department"/></label>
                                                <form:select path="divisionId" class="form-control">
                                                    <c:forEach var="division" items="${divisions}">
                                                        <form:option value="${division.id}">${division.name}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.schoolYear"/></label>
                                                <form:select path="schoolYear" class="form-control" style="">
                                                    <c:forEach var="y" begin="1" end="4">
                                                        <form:option value="${y}">${y}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">

                                        <div class="col-md-3">

                                            <div class="form-group">
                                                <label><spring:message code="common.subjCategory"/></label>
                                                <form:select path="subjCategory" class="form-control" style="">
                                                    <c:forEach var="c" items="${subjCategoryList}">
                                                        <form:option value="${c.name()}"><spring:message code="subj.category.${c.name()}"/></form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>

                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><spring:message code="common.credit"/></label>
                                                <form:input path="credit" type="number" class="form-control"/>
                                                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label><spring:message code="professor.course.ltlp"/></label>
                                                <div class="form-group">
                                                    <form:input type="number" path="lec" class="form-control" style="width:23%;display:inline" placeholder="Lecture"/>
                                                    <form:input type="number" path="tut" class="form-control" style="width:23%;display:inline" placeholder="Tutorial"/>
                                                    <form:input type="number" path="lab" class="form-control" style="width:23%;display:inline" placeholder="Lab"/>
                                                    <form:input type="number" path="ws" class="form-control" style="width:29%;display:inline" placeholder="Workshop/Practice"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label><spring:message code="professor.learningObjectives"/></label>
                                                <form:textarea rows="5" path="learningObjective" class="form-control" style=""></form:textarea>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label><spring:message code="professor.courseOverview"/></label>
                                                <form:textarea rows="5" path="overview" class="form-control" style=""></form:textarea>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label><spring:message code="professor.achievements"/></label>
                                                <table class="table rounded">
                                                    <thead>
                                                    <tr class="table-secondary text-center">

                                                        <c:forEach var="achi" begin="1" end="10">
                                                            <td>
                                                                    ${achi}
                                                            </td>
                                                        </c:forEach>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr class="table-light text-center">
                                                        <c:forEach var="achi" begin="1" end="10">
                                                            <td>
                                                                <spring:message code="professor.achieve${achi}"/>
                                                            </td>
                                                        </c:forEach>
                                                    </tr>
                                                    <tr class="text-center">
                                                        <c:forEach var="achi" begin="1" end="10">
                                                            <td>
                                                                <form:checkbox path="achieve${achi}"/>
                                                            </td>
                                                        </c:forEach>
                                                    </tr>

                                                    </tbody>
                                                </table>


                                            </div>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
                                </form:form>
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

    $(document).ready(function() {
    });
</script>
</body>
</html>