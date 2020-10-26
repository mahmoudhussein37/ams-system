<%@include file="/WEB-INF/view/include/topTag.jsp" %>

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
        <%@include file="/WEB-INF/view/role/student/syllabus/courseDetailFundamentals.jsp" %>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
        <%@include file="/WEB-INF/view/role/student/syllabus/courseDetailMethod.jsp" %>
    </div>
    <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
        <%@include file="/WEB-INF/view/role/student/syllabus/courseDetailContents.jsp" %>
    </div>

</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {

});
</script>