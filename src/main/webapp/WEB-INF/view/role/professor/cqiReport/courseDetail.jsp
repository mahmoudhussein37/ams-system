<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold">
        <spring:message code="common.print"/>
    </a>
</div>
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

        </tr>
        <c:forEach var="d" begin="1" end="3">
            <tr>

                <td>
                    0${d}
                </td>
                <td>
                    25
                </td>
                <td>
                    50
                </td>
                <td>
                    20
                </td>
                <td>
                    0
                </td>
                <td>
                    0
                </td>

            </tr>
        </c:forEach>


    </table>
    </div>
</div>
<br/><br/>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.courseLearningObjectives"/></h3>
<div class="row">
    <div class="col-md-12">
        <table class="table table-bordered">
            <tr>
                <td>
                    <spring:message code="common.no"/>
                </td>
                <td>
                    <spring:message code="professor.courseLearningObjectives"/>
                </td>
                <td>
                    2018
                </td>
                <td>
                    <spring:message code="professor.achievementScore"/><br/>
                    2019

                </td>
                <td>
                    2020
                </td>
            </tr>
            <tr>
                <td>
                    1
                </td>
                <td>
                    General Review of...
                </td>
                <td>
                    5
                </td>
                <td>
                    4

                </td>
                <td style="background-color:#37a94b">
                    5
                </td>
            </tr>
            <tr>
                <td>
                    2
                </td>
                <td>
                    Concept of pointers...
                </td>
                <td>
                    4
                </td>
                <td>
                    4

                </td>
                <td style="background-color:#37a94b">
                    4
                </td>
            </tr>
            <tr>
                <td>
                    3
                </td>
                <td>
                    Handling data using...
                </td>
                <td>
                    5
                </td>
                <td>
                    4

                </td>
                <td style="background-color:#37a94b">
                    5
                </td>
            </tr>

        </table>
    </div>
</div>
<br/><br/>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.lectureAnalysis"/></h3>
<div class="row">
    <div class="col-md-12">
        <table class="table table-bordered">
            <tr>
                <td>
                    <spring:message code="professor.previousImprovePlan"/>
                </td>
                <td>
                    <spring:message code="professor.improvementResult"/>
                </td>
            </tr>
            <tr>
                <td>
<textarea rows="6" dir="rtl" class="form-control" disabled>
last data...

</textarea>
                </td>
                <td>
                    <textarea rows="6"  dir="rtl" class="form-control" >

                    </textarea>
                </td>
            </tr>

        </table>
    </div>
</div>
<br/><br/>
<h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.comprehensiveLecture"/></h3>
<div class="row">
    <div class="col-md-12">
        <textarea rows="6" dir="rtl"  class="form-control" >

                    </textarea>
    </div>
</div>
<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
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
            labels: ['Design/Materials', 'Method/Difficulty', 'Evaluation/Feedback', 'Interaction', 'General'],
            datasets: [{
                label: '01',
                data: [4.5, 3.8, 4.3, 2.2, 3.8],
                backgroundColor: [
                    'rgba(255, 99, 132)',
                    'rgba(255, 99, 132)',
                    'rgba(255, 99, 132)',
                    'rgba(255, 99, 132)',
                    'rgba(255, 99, 132)',


                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 99, 132, 1)',

                ],
                borderWidth: 1
            },
                {
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
                }
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
            labels: ['A', 'B', 'C', 'D', 'F'],
            datasets: [{
                label: '01',
                data: [45, 38, 43, 22, 38],
                backgroundColor: [
                    'rgba(255, 99, 132)',
                    'rgba(255, 99, 132)',
                    'rgba(255, 99, 132)',
                    'rgba(255, 99, 132)',
                    'rgba(255, 99, 132)',


                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 99, 132, 1)',

                ],
                borderWidth: 1
            },
                {
                    label: '02',
                    data: [47, 39, 44, 22, 38],
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
                    data: [45, 39, 24, 22, 38],
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
                }
            ]
        },
        options: {
            maintainAspectRatio: false
        }
    });
</script>