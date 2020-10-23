<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<div class="card-body">

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
                            <spring:message code="common.divide"/>
                        </td>
                        <td>
                            <spring:message code="common.division"/>
                        </td>
                        <td>
                            <spring:message code="common.schoolYear"/>
                        </td>
                        <td>
                            <spring:message code="common.studentNumber"/>
                        </td>
                        <td>
                            <spring:message code="common.name"/>
                        </td>
                        <td>
                            <spring:message code="common.status"/>
                        </td>
                        <td>
                            <spring:message code="common.grade"/>
                        </td>
                        <td>
                            Attendance<br/>(20)
                        </td>
                        <td>
                            Assignment<br/>(10)
                        </td>
                        <td>
                            Mid-Term<br/>(30)
                        </td>
                        <td>
                            Final-Term<br/>(30)
                        </td>
                        <td>
                            Options<br/>(10)
                        </td>
                        <td>
                            Sum
                        </td>

                    </tr>
                    </thead>
                    <tbody>
                    <tr class="table-light text-center">
                        <td>1</td>
                        <td>
                            ${course.title}
                        </td>
                        <td>
                            01
                        </td>
                        <td>
                            ${course.division.name}
                        </td>
                        <td>
                            3
                        </td>
                        <td>
                            1234567890
                        </td>
                        <td>
                            AAAAA
                        </td>
                        <td>
                            Attending
                        </td>
                        <td>
                            A+
                        </td>
                        <td>
                            19
                        </td>
                        <td>
                            8
                        </td>
                        <td>
                            30
                        </td>
                        <td>
                            15
                        </td>
                        <td>
                            5
                        </td>
                        <td>
                            77
                        </td>
                    </tr>


                    </tbody>
                </table>


            </div>
        </div>
    </div>

</div>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {
   $("#lecture-fundamental-save").click(function(e) {
     e.preventDefault();
       $.post('${baseUrl}/professor/classProgress/classAssessment/courseDetail?courseId=${course.id}', $('#lectureFundamentalsForm').serialize(), function() {
         alert("<spring:message code="common.success"/>");
       });
   });
});
</script>