<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
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
                                            <c:set var="totalCount" value="0"/>
                                            <c:set var="totalScore" value="0.0"/>
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
                                                        <c:set var="totalScore" value="${totalScore + sc.getGradeScore()}"/>
                                                        <c:set var="totalCount" value="${totalCount + 1}"/>
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
                                                </tr>
                                            </c:forEach>



                                            </tbody>
                                        </table>
                                        <div class="row">
                                            <div class="col-md-12 text-left">

                                                <div class="form-group">
                                                    <label><strong><spring:message code="professor.course.averageScore"/></strong></label>:
                                                    <c:choose>
                                                        <c:when test="${totalScore == 0.0 or totalCount == 0}">
                                                            0.0
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${totalScore / totalCount}" />
                                                        </c:otherwise>
                                                    </c:choose>

                                                </div>

                                            </div>
                                        </div>
                                        <br/>
                                        <br/>
                                        <div id="ratio-div">
                                            <%@include file="/WEB-INF/view/role/common/grade/ratioDetailDiv.jsp" %>
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


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>
    window.print();
    window.onafterprint = function(){
        window.close();
    }
</script>
</body>
</html>