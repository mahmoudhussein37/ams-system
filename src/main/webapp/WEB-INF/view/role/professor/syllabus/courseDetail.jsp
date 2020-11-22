<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="print-div">
    <a href="#" class="btn btn-sm btn-light font-weight-bold">
        <spring:message code="common.print"/>
    </a>
</div>
<ul class="nav nav-tabs nav-tabs-line">
    <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="professor.course.lectureFundamentals"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2"><spring:message code="professor.course.lectureMethod"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_3"><spring:message code="professor.course.lectureContents"/></a>
    </li>
</ul>
<div class="tab-content mt-5" id="myTabContent">
    <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_1">
        <%@include file="/WEB-INF/view/role/professor/syllabus/courseDetailFundamentals.jsp" %>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
        <%@include file="/WEB-INF/view/role/professor/syllabus/courseDetailMethod.jsp" %>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
        <%@include file="/WEB-INF/view/role/professor/syllabus/courseDetailContents.jsp" %>
    </div>

</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
    $(document).ready(function() {
        $("#lecture-fundamental-save").click(function(e) {
            e.preventDefault();
            var rateAttendance = Number($("input[name=rateAttendance]").val());
            var rateAssignment = Number($("input[name=rateAssignment]").val());
            var rateMid = Number($("input[name=rateMid]").val());
            var rateFinal = Number($("input[name=rateFinal]").val());
            var rateOptional = Number($("input[name=rateOptional]").val());

            var total = rateAttendance + rateAssignment + rateMid + rateFinal + rateOptional;

            if(total == 100) {
                $.post('${baseUrl}/professor/classProgress/syllabus/courseDetail/lectureFundamentals?profCourseId=${pc.course.id}', $('#lectureFundamentalsForm').serialize(), function() {
                    alert("<spring:message code="common.success"/>");
                });
            } else {
                alert("<spring:message code="professor.checkRates"/>");
            }


        });

        $("#prof-lecture-method-save").click(function(e) {
            e.preventDefault();
            $.post('${baseUrl}/professor/classProgress/syllabus/courseDetail/profLectureMethod?profCourseId=${pc.course.id}', $('#profLectureMethodForm').serialize(), function() {
                alert("<spring:message code="common.success"/>");
            });
        });
        $("#lecture-contents-save").click(function(e) {
            e.preventDefault();
            $.post('${baseUrl}/professor/classProgress/syllabus/courseDetail/lectureContents?profCourseId=${pc.course.id}', $('#lectureContentsForm').serialize(), function() {
                alert("<spring:message code="common.success"/>");
            });
        });
    });
</script>