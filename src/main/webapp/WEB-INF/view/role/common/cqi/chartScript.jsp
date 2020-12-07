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
                    label: '<spring:message code="professor.assignedDivisionAvg"/>',
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
                    label: '<spring:message code="professor.divisionAvg"/>',
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
                label: '<spring:message code="professor.assignedStudents"/>',
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

</script>