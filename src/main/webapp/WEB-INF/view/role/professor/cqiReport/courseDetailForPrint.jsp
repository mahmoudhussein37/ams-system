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

                            <div class="detail-div">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.cqiReport"/></h3>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td>
                                                    <spring:message code="common.year"/>
                                                </td>
                                                <td>
                                                    ${pc.semester.year}
                                                </td>
                                                <td>
                                                    <spring:message code="common.semester"/>
                                                </td>
                                                <td>
                                                    ${pc.semester.semester}
                                                </td>
                                                <td>
                                                    <spring:message code="common.professor"/>
                                                </td>
                                                <td colspan="3">
                                                    ${pc.professorUser.getFullName()}
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="common.courseTitle"/>
                                                </td>
                                                <td colspan="3">
                                                    ${pc.course.title}
                                                </td>
                                                <td>
                                                    <spring:message code="common.courseCode"/>
                                                </td>
                                                <td>
                                                    ${pc.course.code}
                                                </td>
                                                <td>
                                                    <spring:message code="professor.course.ltlp"/>
                                                </td>
                                                <td>
                                                    ${pc.course.lec}-${pc.course.tut}-${pc.course.lab}-${pc.course.ws}
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>
                                                    <spring:message code="common.courseTarget"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.department"/>
                                                </td>
                                                <td colspan="2">
                                                    ${pc.course.division.name}
                                                </td>
                                                <td>
                                                    <spring:message code="common.schoolYear"/>
                                                </td>
                                                <td>
                                                    ${pc.schoolYear}
                                                </td>
                                                <td>
                                                    <spring:message code="common.subjCategory"/>
                                                </td>
                                                <td>
                                                    <spring:message code="subj.category.${pc.course.subjCategory}"/>
                                                </td>

                                            </tr>
                                            <tr>
                                                <%--                <td>
                                                                    <spring:message code="common.compCategory"/>
                                                                </td>
                                                                <td>
                                                                    <spring:message code="comp.category.${course.compCategory}"/>
                                                                </td>
                                                                <td>
                                                                    <spring:message code="common.subjCategory"/>
                                                                </td>
                                                                <td>
                                                                    <spring:message code="subj.category.${course.subjCategory}"/>
                                                                </td>--%>
                                                <td>
                                                    <spring:message code="professor.engAccreditation"/>
                                                </td>
                                                <td colspan="3">
                                                    ${pc.engAccreditation eq true ? 'Y' : 'N'}
                                                </td>
                                                <td colspan="4">
                                                </td>


                                            </tr>
                                        </table>

                                        <table class="table table-bordered">
                                            <tr>
                                                <td style="width:150px">
                                                    <spring:message code="common.divide2"/>
                                                </td>
                                                <c:forEach var="d" items="${professorCourseList}">
                                                    <td>
                                                            ${d.divide}
                                                    </td>
                                                </c:forEach>
                                                <c:forEach var="d" begin="1" end="${12 - fn:length(professorCourseList)}">
                                                    <td>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td style="width:150px">
                                                    <spring:message code="professor.course.numStudent"/>
                                                </td>
                                                <c:forEach var="d" items="${professorCourseList}">
                                                    <td>
                                                            ${d.numStudent}
                                                    </td>
                                                </c:forEach>
                                                <c:forEach var="d" begin="1" end="${12 - fn:length(professorCourseList)}">
                                                    <td>
                                                    </td>
                                                </c:forEach>

                                            </tr>

                                        </table>

                                        <br/>

                                    </div>



                                </div>

                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.classResponsivenessStatistics"/></h3>
                                <div class="row">
                                    <div class="col-md-6">

                                        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.averageAssignedDivide"/></h3>
                                        <canvas id="chart1"></canvas>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td width="100px">
                                                    <spring:message code="common.year"/>
                                                </td>
                                                <c:forEach var="entry" items="${averageAssignedMap}">
                                                    <td>
                                                            ${entry.key}
                                                    </td>
                                                </c:forEach>

                                            </tr>
                                            <tr>
                                                <td width="100px">
                                                    <spring:message code="professor.course.numStudent"/>
                                                </td>
                                                <c:forEach var="entry" items="${averageAssignedMap}">
                                                    <td>
                                                            ${entry.value}
                                                    </td>
                                                </c:forEach>

                                            </tr>
                                        </table>

                                    </div>
                                    <div class="col-md-6">
                                        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.averageAssignedDivideSemester"/></h3>
                                        <canvas id="chart2"></canvas>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td>
                                                    <spring:message code="common.divide2"/>
                                                </td>
                                                <td>
                                                    <spring:message code="professor.course.numStudent"/>
                                                </td>

                                            </tr>
                                            <c:forEach var="d" items="${professorCourseList}">
                                                <tr>

                                                    <td>
                                                            ${d.divide}
                                                    </td>
                                                    <td>
                                                            ${d.numStudent}
                                                    </td>

                                                </tr>

                                            </c:forEach>


                                        </table>


                                    </div>
                                </div>
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.averageEvaluationSemester"/></h3>
                                <div class="row">
                                    <div class="col-md-12">
                                        <canvas id="chart3" style="height:300px"></canvas>
                                    </div>
                                </div>
                                <br/>
                                <br/>
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.gradeRatioDistribution"/></h3>
                                <div class="row">
                                    <div class="col-md-12">
                                        <canvas id="chart4" style="height:300px"></canvas>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <table class="table table-bordered">
                                            <tr>
                                                <td>
                                                    <spring:message code="common.divide2"/>
                                                </td>
                                                <td>
                                                    A
                                                </td>
                                                <td>
                                                    B
                                                </td>
                                                <td>
                                                    C
                                                </td>
                                                <td>
                                                    D
                                                </td>
                                                <td>
                                                    F
                                                </td>
                                                <td>
                                                    S
                                                </td>
                                                <td>
                                                    U
                                                </td>

                                            </tr>
                                            <c:forEach var="professorCourse" items="${professorCourseList}">
                                                <tr>

                                                    <td>
                                                        0${professorCourse.divide}
                                                    </td>
                                                    <td>
                                                        <c:set var="count" value="0"/>
                                                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                                                            <c:if test="${sc.grade eq 'Ap' or sc.grade eq 'A0'}">
                                                                <c:set var="count" value="${count + 1}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                            ${count}
                                                    </td>
                                                    <td>
                                                        <c:set var="count" value="0"/>
                                                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                                                            <c:if test="${sc.grade eq 'Bp' or sc.grade eq 'B0'}">
                                                                <c:set var="count" value="${count + 1}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                            ${count}
                                                    </td>
                                                    <td>
                                                        <c:set var="count" value="0"/>
                                                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                                                            <c:if test="${sc.grade eq 'Cp' or sc.grade eq 'c0'}">
                                                                <c:set var="count" value="${count + 1}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                            ${count}
                                                    </td>
                                                    <td>
                                                        <c:set var="count" value="0"/>
                                                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                                                            <c:if test="${sc.grade eq 'Dp' or sc.grade eq 'D0'}">
                                                                <c:set var="count" value="${count + 1}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                            ${count}
                                                    </td>
                                                    <td>
                                                        <c:set var="count" value="0"/>
                                                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                                                            <c:if test="${sc.grade eq 'F'}">
                                                                <c:set var="count" value="${count + 1}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                            ${count}
                                                    </td>
                                                    <td>
                                                        <c:set var="count" value="0"/>
                                                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                                                            <c:if test="${sc.grade eq 'S'}">
                                                                <c:set var="count" value="${count + 1}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                            ${count}
                                                    </td>
                                                    <td>
                                                        <c:set var="count" value="0"/>
                                                        <c:forEach var="sc" items="${professorCourse.studentCourseList}">
                                                            <c:if test="${sc.grade eq 'U'}">
                                                                <c:set var="count" value="${count + 1}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                            ${count}
                                                    </td>

                                                </tr>
                                            </c:forEach>


                                        </table>
                                    </div>
                                </div>
                                <br/><br/>

                                <form:form class="form" modelAttribute="cqi" id="cqiForm" action="${baseUrl}/professor/classProgress/cqiReport/courseDetail?profCourseId=${pc.id}" method="post">
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.courseLearningObjectives"/></h3>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-bordered">
                                                <tr>
                                                    <td rowspan="2" style="text-align:center">
                                                        <spring:message code="common.no"/>
                                                    </td>
                                                    <td rowspan="2" style="text-align:center">
                                                        <spring:message code="professor.courseLearningObjectives"/>
                                                    </td>

                                                    <td colspan="3" style="text-align:center">
                                                        <spring:message code="professor.achievementScore"/> (1 ~ 5)<br/>

                                                    </td>

                                                </tr>
                                                <tr style="text-align:center">
                                                    <c:forEach var="entry" items="${cqiMap}">
                                                        <td>
                                                                ${entry.key}
                                                        </td>
                                                    </c:forEach>
                                                    <td>
                                                            ${currentYear}
                                                    </td>

                                                </tr>
                                                <c:forEach var="d" begin="1" end="6">
                                                    <tr style="text-align:center">
                                                        <td>
                                                                ${d}
                                                        </td>
                                                        <td>
                                                                ${lectureFundamentals.getClo(d)}
                                                        </td>
                                                        <c:forEach var="entry" items="${cqiMap}">
                                                            <td>
                                                                    ${entry.value.getScore(d)}
                                                            </td>
                                                        </c:forEach>

                                                        <td>
                                                            <input class="form-control" type="number" min="1" max="5" name="score${d}" value="${cqi.getScore(d)}" ${menuAccess.cqi ? '' : 'disabled'}/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>

                                            </table>
                                        </div>
                                    </div>
                                    <br/><br/>
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.lectureAnalysis"/></h3>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-bordered">
                                                <tr>
                                                    <td style="width:50%">
                                                        <spring:message code="professor.previousImprovePlan"/>
                                                    </td>
                                                    <td style="width:50%">
                                                        <spring:message code="professor.improvementResult"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
<textarea rows="6" dir="rtl" class="form-control" disabled>
        ${prevCqi.plan}

</textarea>
                                                    </td>
                                                    <td>
                                                        <form:textarea path="problem" rows="6"  dir="rtl" class="form-control" disabled="${menuAccess.cqi ? false : true}"/>

                                                    </td>
                                                </tr>

                                            </table>
                                        </div>
                                    </div>
                                    <br/><br/>
                                    <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.comprehensiveLecture"/></h3>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <form:textarea path="plan" rows="6" dir="rtl"  class="form-control" disabled="${menuAccess.cqi ? false : true}"/>
                                        </div>
                                    </div>


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




<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script src="${resources}/vendor/chartjs/chart.min.js"></script>
<script src="${resources}/vendor/chartjs/util.js"></script>
<script>
    var colors = ['rgba(255, 99, 132)','rgba(54, 162, 235)', "#5a9997","#8950FC","#FFA800",'rgb(235,217,54)','rgb(217,200,136)','#1BC5BD', '#3699FF', '#1BC5BD'];
    var ctx = document.getElementById('chart1').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [
                <c:forEach var="entry" items="${averageAssignedMap}">
                '${entry.key}',
                </c:forEach>

            ],
            datasets: [
                {
                    label: 'Assigned  Division Avg.',
                    data: [0, 0, ${pc.numStudent}],
                    backgroundColor: [
                        'rgba(255, 99, 132)',
                        'rgba(255, 99, 132)',
                        'rgba(255, 99, 132)',

                    ],
                    borderColor: [
                        'rgba(255, 99, 132)',
                        'rgba(255, 99, 132)',
                        'rgba(255, 99, 132)',
                    ],
                    borderWidth: 1
                },
                {
                    label: 'Divide Avg.',
                    data: [
                        <c:forEach var="entry" items="${averageAssignedMap}">
                        ${entry.value},
                        </c:forEach>
                    ],
                    backgroundColor: [
                        <c:forEach var="entry" items="${averageAssignedMap}">
                        'rgba(54, 162, 235)',

                        </c:forEach>




                    ],
                    borderColor: [
                        <c:forEach var="entry" items="${averageAssignedMap}">
                        'rgba(54, 162, 235)',
                        </c:forEach>

                    ],
                    borderWidth: 1
                }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

    var ctx2 = document.getElementById('chart2').getContext('2d');
    var myChart2 = new Chart(ctx2, {
        type: 'bar',
        data: {
            labels: [
                <c:forEach var="d" items="${professorCourseList}">
                '${d.divide}',
                </c:forEach>

            ],
            datasets: [{
                label: 'Divide Avg.',
                data: [
                    <c:forEach var="d" items="${professorCourseList}">
                    ${d.numStudent},
                    </c:forEach>
                ],
                backgroundColor: [
                    <c:forEach var="d" items="${professorCourseList}">
                    'rgba(255, 99, 132)',
                    </c:forEach>




                ],
                borderColor: [
                    <c:forEach var="d" items="${professorCourseList}">
                    'rgba(255, 99, 132)',
                    </c:forEach>

                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

    var ctx3 = document.getElementById('chart3').getContext('2d');
    var myChart3 = new Chart(ctx3, {
        type: 'bar',
        data: {
            labels: [
                <c:forEach var="af" items="${pc.getFilteredAssessmentFactors(assessmentFactors)}">
                '${af.title}',
                </c:forEach>

            ],
            datasets: [
                <c:forEach var="profCourse" items="${professorCourseList}">
                {
                    label: '0${profCourse.divide}',
                    data: [
                        <c:forEach var="af" items="${pc.getFilteredAssessmentFactors(assessmentFactors)}">
                        ${profCourse.getAssessmentFactorAverage(af)},
                        </c:forEach>
                    ],
                    backgroundColor: [
                        <c:forEach var="af" items="${pc.getFilteredAssessmentFactors(assessmentFactors)}">
                        colors[${profCourse.divide-1}],
                        </c:forEach>



                    ],
                    borderColor: [
                        <c:forEach var="af" items="${pc.getFilteredAssessmentFactors(assessmentFactors)}">
                        colors[${profCourse.divide-1}],
                        </c:forEach>

                    ],
                    borderWidth: 1
                },
                </c:forEach>

                /*{
                    label: '02',
                    data: [4.7, 3.9, 4.4, 2.2, 3.8],
                    backgroundColor: [
                        'rgba(54, 162, 235)',
                        'rgba(54, 162, 235)',
                        'rgba(54, 162, 235)',
                        'rgba(54, 162, 235)',
                        'rgba(54, 162, 235)',

                    ],
                    borderColor: [
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                    ],
                    borderWidth: 1
                },
                {
                    label: '03',
                    data: [4.5, 3.9, 2.4, 2.2, 3.8],
                    backgroundColor: [
                        'rgb(235,217,54)',
                        'rgb(235,217,54)',
                        'rgb(235,217,54)',
                        'rgb(235,217,54)',
                        'rgb(235,217,54)',

                    ],
                    borderColor: [
                        'rgb(217,200,136)',
                        'rgb(217,200,136)',
                        'rgb(217,200,136)',
                        'rgb(217,200,136)',
                        'rgb(217,200,136)',
                    ],
                    borderWidth: 1
                }*/
            ]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            responsive: true,
            maintainAspectRatio: false
        }
    });
    var ctx4 = document.getElementById('chart4').getContext('2d');
    var myChart4 = new Chart(ctx4, {
        type: 'bar',
        data: {
            labels: [
                <c:forEach var="professorCourse" items="${professorCourseList}" varStatus="varStatus">
                <c:if test="${varStatus.count eq 1}">
                <c:forEach var="entry" items="${professorCourse.getNumGradeMap()}">
                '${entry.key}',
                </c:forEach>
                </c:if>
                </c:forEach>

            ],
            datasets: [
                <c:forEach var="professorCourse" items="${professorCourseList}">
                {
                    label: '0${professorCourse.divide}',
                    data: [

                        <c:forEach var="entry" items="${professorCourse.getNumGradeMap()}">
                        ${entry.value},
                        </c:forEach>


                    ],
                    backgroundColor: [
                        <c:forEach var="entry" items="${professorCourse.getNumGradeMap()}">
                        colors[${professorCourse.divide - 1}],
                        </c:forEach>



                    ],
                    borderColor: [
                        <c:forEach var="entry" items="${professorCourse.getNumGradeMap()}">
                        colors[${professorCourse.divide - 1}],
                        </c:forEach>

                    ],
                    borderWidth: 1
                },
                </c:forEach>
            ]
        },
        options: {
            maintainAspectRatio: false
        }
    });
    $(document).ready(function() {
        window.print();
        window.onafterprint = function(){
            window.close();
        }
    });
</script>
</body>
</html>