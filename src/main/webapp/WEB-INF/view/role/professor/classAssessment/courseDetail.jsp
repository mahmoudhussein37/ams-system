<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold print">
        <spring:message code="common.print"/>
    </a>
</div>
<%@include file="/WEB-INF/view/role/common/professorCourse/basicInfo.jsp" %>
<c:choose>
    <c:when test="${isViewable}">
        <ul class="nav nav-tabs nav-tabs-line">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="common.result"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2"><spring:message code="common.comment"/></a>
            </li>

        </ul>
        <div class="tab-content mt-5" id="myTabContent">
            <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_1">
                <%@include file="/WEB-INF/view/role/common/assessment/result.jsp" %>
            </div>
            <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
                <%@include file="/WEB-INF/view/role/common/assessment/comment.jsp" %>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="alert  alert-custom alert-success" role="alert">
            <div class="alert-icon"><i class="flaticon-warning"></i></div>
            <div class="alert-text"><h3><spring:message code="professor.period.assessment"/></h3></div>
        </div>
    </c:otherwise>
</c:choose>




<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
    <c:choose>
    <c:when test="${isViewable}">
    $(document).ready(function() {
        $("body").on('click', '.print', function (e) {
            e.preventDefault();
            openPage("${baseUrl}/professor/classProgress/classAssessment/courseDetail?print=true&profCourseId=${pc.id}");
        });
    });
    </c:when>
    <c:otherwise>

    </c:otherwise>
    </c:choose>
</script>