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
                    <%--style="background-image: url('${resources}/images/certificate.png');background-repeat: no-repeat;background-size: cover;"--%>
                    <div class="card card-custom">

                        <div class="card-body" style="padding:100px;">

                            <div class="row">
                                <div class="col-md-12" style="text-align:left">
                                    No. ${certificate.getNo()}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12" style="text-align:center">
                                    <br/><br/><br/><br/>
                                    <h1><spring:message code="common.cert.title"/></h1>
                                    <br/><br/><br/><br/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <table style="width:100%">
                                        <tr>
                                            <td style="width:35%;text-align:left;"><label><strong><spring:message code="common.department"/></strong></label>: ${studentUser.division.name}</td>
                                        </tr>
                                        <tr>
                                            <td style="width:35%;text-align:left;"><label><strong><spring:message code="common.admissionYear"/></strong></label>: ${studentUser.contact.admissionYear}</td>
                                        </tr>
                                        <tr>
                                            <td style="width:35%;text-align:left;"> <label><strong><spring:message code="common.studentNumber"/></strong></label>: ${studentUser.number}</td>
                                        </tr>
                                        <tr>
                                            <td style="width:35%;text-align:left;"><label><strong><spring:message code="common.name"/></strong></label>: ${studentUser.getFullName()}</td>
                                        </tr>

                                    </table>


                                </div>


                            </div>
                           <%-- <div class="row">

                                <div class="col-md-6 text-center">
                                    <div class="form-group">
                                        <label><strong><spring:message code="common.studentNumber"/></strong></label>: ${studentUser.number}

                                        &lt;%&ndash;<span class="form-text text-muted">We'll never share your email with anyone else</span>&ndash;%&gt;
                                    </div>
                                </div>
                                <div class="col-md-6 text-center">
                                    <div class="form-group">
                                        <label><strong><spring:message code="common.name"/></strong></label>: ${studentUser.getFullName()}
                                    </div>
                                </div>

                            </div>--%>
                            <br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/>
                            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="student.semesterGrade"/></h3>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <table class="table rounded">
                                            <thead>
                                            <tr class="table-secondary text-center">

                                                <td><spring:message code="common.no"/></td>
                                                <td>
                                                    <spring:message code="common.year"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.semester"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.schoolYear"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.apply"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.complete"/>
                                                </td>
                                                <%--<td>
                                                    <spring:message code="common.score"/>
                                                </td>--%>
                                                <td>
                                                    <spring:message code="common.grade"/>
                                                </td>
                                                <%--<td>
                                                    <spring:message code="common.percentile"/>
                                                </td>
                                                <td>
                                                    <spring:message code="common.warning"/>
                                                </td>--%>

                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="totalApplied" value="0"/>
                                            <c:set var="totalComplete" value="0"/>
                                            <c:set var="totalGrade" value="0"/>
                                            <c:set var="totalGradeTotal" value="0"/>
                                            <c:set var="totalGradeCount" value="0"/>

                                            <c:forEach var="entry" items="${courseMap}" varStatus="varStatus">
                                                <c:set var="semester" value="${entry.key}"/>
                                                <tr class="table-light text-center">
                                                    <td>${varStatus.count}</td>
                                                    <td>
                                                            ${entry.key.year}
                                                    </td>
                                                    <td>
                                                            ${entry.key.semester}
                                                    </td>
                                                    <td>
                                                        <c:set var="schoolYear" value="0"/>
                                                        <c:forEach var="sc" items="${entry.value}">
                                                            <c:set var="schoolYear" value="${sc.schoolYear}"/>
                                                        </c:forEach>
                                                            ${schoolYear}
                                                    </td>
                                                    <td>
                                                        <c:set var="applyCount" value="0"/>
                                                        <c:forEach var="sc" items="${courseMap.get(entry.key)}">
                                                            <c:set var="applyCount" value="${applyCount + sc.course.credit}"/>

                                                        </c:forEach>
                                                            ${applyCount}
                                                        <c:set var="totalApplied" value="${totalApplied + applyCount}"/>
                                                    </td>
                                                    <td>
                                                        <c:set var="completeCount" value="0"/>
                                                        <c:forEach var="sc" items="${courseMap.get(entry.key)}">
                                                            <c:if test="${sc.valid and sc.grade ne 'U' and sc.grade ne 'F'}">
                                                                <c:set var="completeCount" value="${completeCount + sc.course.credit}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                            ${completeCount}
                                                        <c:set var="totalComplete" value="${totalComplete + completeCount}"/>
                                                    </td>

                                                    <td>
                                                        <c:set var="gradeCount" value="0"/>
                                                        <c:set var="gradeTotal" value="0.0"/>
                                                        <c:forEach var="sc" items="${courseMap.get(entry.key)}">
                                                            <c:if test="${sc.valid and sc.grade ne 'U' and sc.grade ne 'S'}">
                                                                <c:set var="currentGrade" value="${sc.course.credit * sc.getGradeScore()}"/>
                                                                <c:set var="gradeTotal" value="${gradeTotal + currentGrade}"/>
                                                                <c:set var="gradeCount" value="${gradeCount + sc.course.credit}"/>
                                                            </c:if>

                                                        </c:forEach>
                                                        <c:set var="totalGradeTotal" value="${totalGradeTotal + gradeTotal}"/>
                                                        <c:set var="totalGradeCount" value="${totalGradeCount + gradeCount}"/>
                                                        <c:choose>
                                                            <c:when test="${gradeCount == 0 or gradeTotal == 0.0}">
                                                                0.0
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${gradeTotal / gradeCount}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>

                                                </tr>

                                            </c:forEach>



                                            </tbody>
                                        </table>


                                    </div>
                                </div>
                            </div>
                            <div class="detail-div">

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
                                                        <spring:message code="common.year"/>
                                                    </td>
                                                    <td>
                                                        <spring:message code="common.semester"/>
                                                    </td>
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




                                                <c:forEach var="entry" items="${courseMap}" varStatus="varStatus">
                                                    <c:set var="semester" value="${entry.key}"/>

                                                    <c:forEach var="sc" items="${entry.value}">
                                                        <tr class="table-light text-center">
                                                            <td>${varStatus.count}</td>
                                                            <td>
                                                                    ${semester.year}
                                                            </td>
                                                            <td>
                                                                    ${semester.semester}
                                                            </td>
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

                                                </c:forEach>



                                                </tbody>
                                            </table>


                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="separator separator-solid my-5"></div>
                                <br/>
                                <div class="row">
                                    <div class="col-md-6 text-center">

                                        <div class="form-group">
                                            <label><strong><spring:message code="common.cert.complete"/></strong></label>: ${totalComplete}
                                        </div>

                                    </div>
                                    <div class="col-md-6 text-center">

                                        <div class="form-group">
                                            <label><strong><spring:message code="common.cert.score"/></strong></label>: <c:choose>
                                            <c:when test="${totalGradeCount == 0 or totalGradeTotal == 0.0}">
                                                0.0
                                            </c:when>
                                            <c:otherwise>
                                                ${totalGradeTotal / totalGradeCount}
                                            </c:otherwise>
                                        </c:choose>
                                        </div>

                                    </div>

                                </div>

                            </div>
                            <div class="row">
                                <div class="col-md-12" style="text-align:center">
                                    <br/><br/>
                                    <h3>${today}</h3>
                                    <br/><br/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12" style="text-align:center">
                                    <br/><br/>
                                    <img alt="Logo" src="${resources}/images/bst_logo_transparent.png" class="max-h-70px"/>
                                    <br/><br/><br/><br/>
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