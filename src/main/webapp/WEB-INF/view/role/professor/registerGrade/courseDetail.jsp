<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold">
        <spring:message code="common.save"/>
    </a>
    <a href="#" class="btn btn-sm btn-light font-weight-bold">
        <spring:message code="common.print"/>
    </a>
</div>
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
                            <select name="grade" data-student-id="1" class="form-control">
                                <option value="A+">A+</option>
                                <option value="A0">A0</option>
                                <option value="B+">B+</option>
                                <option value="B0">B0</option>
                                <option value="C+">C+</option>
                                <option value="C0">C0</option>
                                <option value="D+">D+</option>
                                <option value="D0">D0</option>
                                <option value="F">F</option>
                                <option value="S">S</option>
                                <option value="U">U</option>
                            </select>
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