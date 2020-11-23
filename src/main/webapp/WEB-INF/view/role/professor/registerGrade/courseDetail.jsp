<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<div class="print-div">
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
                            <spring:message code="professor.grade.attendance"/><br/>(${lectureFundamentals.rateAttendance})
                        </td>
                        <td>
                            <spring:message code="professor.grade.assignment"/><br/>(${lectureFundamentals.rateAssignment})
                        </td>
                        <td>
                            <spring:message code="professor.grade.midTerm"/><br/>(${lectureFundamentals.rateMid})
                        </td>
                        <td>
                            <spring:message code="professor.grade.finalTerm"/><br/>(${lectureFundamentals.rateFinal})
                        </td>
                        <td>
                            <spring:message code="professor.grade.options"/><br/>(${lectureFundamentals.rateOptional})
                        </td>
                        <td>
                            <spring:message code="professor.grade.sum"/>
                        </td>
                        <td>
                            <spring:message code="common.grade"/>
                        </td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                        <tr class="table-light text-center" style="${not sc.valid? 'color:red' : ''}">
                            <td>${varStatus.count}</td>
                            <td>
                                ${sc.course.code}
                            </td>
                            <td>
                                    ${sc.professorCourse.divide}
                            </td>

                            <td>
                                ${sc.studentUser.schoolYear}
                            </td>
                            <td>
                                    ${sc.studentUser.number}
                            </td>
                            <td>
                                    ${sc.studentUser.getFullName()}
                            </td>
                            <td>
                                <spring:message code="student.status.${sc.studentUser.status}"/>
                            </td>

                            <td>
                                <a href="#" class="course-editable" data-type="number" data-name="scoreAttendance" data-url="${baseUrl}/professor/classProgress/registerGrade/gradeEditable" data-pk="${sc.id}" data-original-title="<spring:message code="professor.grade.attendance"/>">${sc.scoreAttendance}</a>
                            </td>
                            <td>
                                <a href="#" class="course-editable" data-type="number" data-name="scoreAssignment" data-url="${baseUrl}/professor/classProgress/registerGrade/gradeEditable" data-pk="${sc.id}" data-original-title="<spring:message code="professor.grade.assignment"/>">${sc.scoreAssignment}</a>
                            </td>
                            <td>
                                <a href="#" class="course-editable" data-type="number" data-name="scoreMid" data-url="${baseUrl}/professor/classProgress/registerGrade/gradeEditable" data-pk="${sc.id}" data-original-title="<spring:message code="professor.grade.midTerm"/>">${sc.scoreMid}</a>
                            </td>
                            <td>
                                <a href="#" class="course-editable" data-type="number" data-name="scoreFinal" data-url="${baseUrl}/professor/classProgress/registerGrade/gradeEditable" data-pk="${sc.id}" data-original-title="<spring:message code="professor.grade.finalTerm"/>">${sc.scoreFinal}</a>
                            </td>
                            <td>
                                <a href="#" class="course-editable" data-type="number" data-name="scoreOptions" data-url="${baseUrl}/professor/classProgress/registerGrade/gradeEditable" data-pk="${sc.id}" data-original-title="<spring:message code="professor.grade.options"/>">${sc.scoreOptions}</a>
                            </td>
                            <td>
                                <input type="number" class="form-control grade-total${sc.id}" value="${sc.scoreTotal}" disabled/>
                            </td>
                            <td style="width:100px;">
                                <select name="grade" data-sc-id="${sc.id}" class="select-grade form-control">
                                    <option value="Ap" ${sc.grade eq 'Ap' ? 'selected' : ''}>A+</option>
                                    <option value="A0" ${sc.grade eq 'A0' ? 'selected' : ''}>A0</option>
                                    <option value="Bp" ${sc.grade eq 'Bp' ? 'selected' : ''}>B+</option>
                                    <option value="B0" ${sc.grade eq 'B0' ? 'selected' : ''}>B0</option>
                                    <option value="Cp" ${sc.grade eq 'Cp' ? 'selected' : ''}>C+</option>
                                    <option value="C0" ${sc.grade eq 'C0' ? 'selected' : ''}>C0</option>
                                    <option value="Dp" ${sc.grade eq 'Dp' ? 'selected' : ''}>D+</option>
                                    <option value="D0" ${sc.grade eq 'D0' ? 'selected' : ''}>D0</option>
                                    <option value="F" ${sc.grade eq 'F' ? 'selected' : ''}>F</option>
                                    <option value="S" ${sc.grade eq 'S' ? 'selected' : ''}>S</option>
                                    <option value="U" ${sc.grade eq 'U' ? 'selected' : ''}>U</option>
                                </select>
                            </td>
                        </tr>
                    </c:forEach>



                    </tbody>
                </table>
                <div id="ratio-div">

                </div>

            </div>
        </div>
    </div>

</div>
<div class="card-footer">
    <button type="submit" id="lecture-fundamental-save" class="btn btn-primary mr-2"><spring:message code="common.save"/></button>
    <%--<button type="reset" class="btn btn-secondary">Cancel</button>--%>
</div>

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
    $.fn.editable.defaults.mode = 'inline';
$(document).ready(function() {
    $("#ratio-div").load("${baseUrl}/professor/classProgress/registerGrade/ratioDetail?courseId=${pc.id}");

    $('.course-editable').editable({
        success: function(response, newValue) {
            var result = response;
            var split = result.split("_");
            var gid = split[0];
            $(".grade-total" + gid).val(split[1]);
        }
    });
    $(".select-grade").change(function() {
        var id = $(this).attr("data-sc-id");
        var selected = $(this).children("option:selected").val();
        $.post("${baseUrl}/professor/classProgress/registerGrade/gradeEditable?pk=" + id + "&name=grade&value=" + selected, function() {
            $("#ratio-div").load("${baseUrl}/professor/classProgress/registerGrade/ratioDetail?courseId=${pc.id}");
        });
    });
   $("#lecture-fundamental-save").click(function(e) {
     e.preventDefault();
       $.post('${baseUrl}/professor/classProgress/registerGrade/courseDetail?courseId=${pc.id}', function(result) {

           if(result) {
              alert("<spring:message code="common.success"/>");
           } else {
               alert("<spring:message code="professor.checkGrades"/>");

           }
           $(".detail-div").load("${baseUrl}/professor/classProgress/registerGrade/courseDetail?courseId=${pc.id}");


       });
   });
});
</script>