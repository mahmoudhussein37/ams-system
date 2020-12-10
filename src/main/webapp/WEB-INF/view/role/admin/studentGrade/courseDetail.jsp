<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold print">
        <spring:message code="common.print"/>
    </a>
</div>
<div class="card-body">
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.courseInformation"/></h3>

    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label><spring:message code="common.courseCode"/></label>
                            <input type="text" class="form-control" disabled value="${pc.course.code}"/>
                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label><spring:message code="common.courseTitle"/></label>
                            <input type="text" class="form-control" disabled value="${pc.course.title}"/>
                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label><spring:message code="common.semester"/></label>
                            <input type="text" class="form-control" disabled value="${pc.semester.year} - ${pc.semester.semester}"/>

                        </div>
                    </div>
                    <div class="col-md-3">

                        <div class="form-group">
                            <label><spring:message code="common.divide"/></label>
                            <input type="text" class="form-control" disabled value="${pc.divide}"/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label><spring:message code="common.professor"/></label>
                            <input type="text" class="form-control" disabled value="${pc.professorUser.getFullName()}"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.result"/></h3>
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

                            <c:choose>
                                <c:when test="${menuAccess.grade}">
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
                                </c:when>
                                <c:otherwise>
                                    <td>
                                            ${sc.scoreAttendance}
                                    </td>
                                    <td>
                                            ${sc.scoreAssignment}
                                    </td>
                                    <td>
                                            ${sc.scoreMid}
                                    </td>
                                    <td>
                                            ${sc.scoreFinal}
                                    </td>
                                    <td>
                                            ${sc.scoreOptions}
                                    </td>
                                    <td>
                                            ${sc.scoreTotal}
                                    </td>
                                    <td style="width:100px;">

                                        <select name="grade" data-sc-id="${sc.id}" class="select-grade form-control" disabled>
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
                                </c:otherwise>
                            </c:choose>

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


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
    $(document).ready(function() {
        $("#ratio-div").load("${baseUrl}/admin/academicManagement/studentGrade/ratioDetail?profCourseId=${pc.id}");

        $("body").on('click', '.print', function (e) {
            e.preventDefault();
            openPage("${baseUrl}/admin/academicManagement/studentGrade/courseDetailForPrint?profCourseId=${pc.id}");
        });
    });
</script>