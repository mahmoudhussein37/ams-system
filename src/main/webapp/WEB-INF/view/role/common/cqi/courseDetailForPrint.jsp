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
                                <%@include file="/WEB-INF/view/role/common/cqi/chartDiv.jsp" %>

                                <form:form class="form" modelAttribute="cqi" id="cqiForm" action="${baseUrl}/professor/classProgress/cqiReport/courseDetail?courseId=${pc.id}" method="post">
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
<%@include file="/WEB-INF/view/role/common/cqi/chartScript.jsp" %>
<script>
    $(document).ready(function() {
        setTimeout(function(){
            window.print();
            window.onafterprint = function(){
                window.close();
            }
        }, 2000);

    });
</script>
</body>
</html>