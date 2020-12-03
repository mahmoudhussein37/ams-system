<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<br/>
<div class="separator separator-solid my-5"></div>
<br/>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="student.gradeDetail"/></h3>

<div class="row">
    <div class="col-md-12">
        <div class="form-group">
            <table class="table rounded">
                <thead>
                <tr class="table-secondary text-center">

                    <td><spring:message code="common.no"/></td>
                    <td>
                        <spring:message code="common.courseCode"/>
                    </td>
                    <td>
                        <spring:message code="common.courseTitle"/>
                    </td>
                    <td>
                        <spring:message code="common.category"/>
                    </td>
                    <td>
                        <spring:message code="common.credit"/>
                    </td>
                    <td>
                        <spring:message code="common.professor"/>
                    </td>
                    <td>
                        <spring:message code="common.divide"/>
                    </td>
                    <td>
                        <spring:message code="common.grade"/>
                    </td>


                </tr>
                </thead>
                <tbody>
                <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                    <tr class="table-light text-center">
                        <td>${varStatus.count}</td>
                        <td>
                            ${sc.course.code}
                        </td>
                        <td>
                                ${sc.course.title}
                        </td>
                        <td>
                            <spring:message code="subj.category.${sc.course.subjCategory}"/>
                        </td>
                        <td>
                            ${sc.course.credit}
                        </td>
                        <td>
                                ${sc.professorCourse.professorUser.getFullName()}
                        </td>
                        <td>
                                ${sc.professorCourse.divide}
                        </td>
                        <td>
                            <spring:message code="student.grade.${sc.grade}"/>
                        </td>

                    </tr>
                </c:forEach>



                </tbody>
            </table>


        </div>
    </div>
</div>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>