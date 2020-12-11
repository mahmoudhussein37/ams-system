<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold print">
        <spring:message code="common.print"/>
    </a>
</div>
<div class="row">
    <div class="col-md-3">

        <div class="form-group">
            <label><spring:message code="common.studentNumber"/></label>
            <input type="text" class="form-control" value="${studentUser.number}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>

    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.department"/></label>
            <input type="text" class="form-control" value="${studentUser.division.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.name"/></label>
            <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>
    </div>
    <%--<div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.major"/></label>
            <input type="text" class="form-control" value="${studentUser.major.name}" disabled/>
            &lt;%&ndash;<span class="form-text text-muted">We'll never share your email with anyone else</span>&ndash;%&gt;
        </div>
    </div>--%>
</div>
<div class="row">
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.status"/></label>
            <input type="text" class="form-control" value="<spring:message code="student.status.${studentUser.status}"/>" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.advisor"/></label>
            <input type="text" class="form-control" value="${studentUser.advisor.getFullName()}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
    <div class="col-md-3">
        <div class="form-group">
            <label><spring:message code="common.schoolYear"/></label>
            <input type="text" class="form-control" value="${studentUser.schoolYear}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
</div>
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

                <c:forEach var="entry" items="${courseMap}" varStatus="varStatus">
                    <c:set var="semester" value="${entry.key}"/>
                    <tr class="table-light text-center">
                        <td>
                            <a href="#" class="course-detail" data-semester-id="${semester.id}">
                                    ${varStatus.count}
                            </a>
                        </td>
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
                        </td>
                        <td>
                            <c:set var="completeCount" value="0"/>
                            <c:forEach var="sc" items="${courseMap.get(entry.key)}">
                                <c:if test="${sc.valid and sc.grade ne 'U' and sc.grade ne 'F'}">
                                    <c:set var="completeCount" value="${completeCount + sc.course.credit}"/>
                                </c:if>

                            </c:forEach>
                                ${completeCount}
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


<div class="detail-div2">

</div>

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {
    <c:if test="${not empty firstSemester}">
    $(".detail-div2").load("${baseUrl}/admin/studentManagement/inquiryGrade/gradeDetail?studentId=${studentUser.id}&semesterId=${firstSemester.id}");
    </c:if>
    $("body").on('click', '.course-detail', function (e) {
        e.preventDefault();
        var courseId = $(this).attr("data-semester-id");
        $(".detail-div2").load("${baseUrl}/admin/studentManagement/inquiryGrade/gradeDetail?studentId=${studentUser.id}&semesterId=" + courseId);

    });

    $("body").on('click', '.print', function (e) {
        e.preventDefault();
        openPage("${baseUrl}/admin/studentManagement/inquiryGrade/gradeDetailForPrint?studentId=${studentUser.id}");
    });
});
</script>