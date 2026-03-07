<%@include file="/WEB-INF/view/include/topTag.jsp" %>
    <div class="print-div">
        <a href="#" class="btn btn-sm btn-light font-weight-bold print">
            <spring:message code="common.print" />
        </a>
    </div>
    <ul class="nav nav-tabs nav-tabs-line">
        <li class="nav-item">
            <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1">
                <spring:message code="professor.course.lectureFundamentals" />
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2">
                <spring:message code="professor.course.lectureMethod" />
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_3">
                <spring:message code="professor.course.lectureContents" />
            </a>
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
            $(document).ready(function () {
                $("#lecture-fundamental-save").click(function (e) {
                    e.preventDefault();
                    var $btn = $(this);

                    // Prevent double-submit
                    if ($btn.prop('disabled')) return;
                    $btn.prop('disabled', true).text('Saving...');

                    var rateAssignment = Number($("input[name=rateAssignment]").val()) || 0;
                    var rateMid = Number($("input[name=rateMid]").val()) || 0;
                    var rateFinal = Number($("input[name=rateFinal]").val()) || 0;
                    var rateOptional = Number($("input[name=rateOptional]").val()) || 0;

                    var total = rateAssignment + rateMid + rateFinal + rateOptional;

                    // Server-side validation handles the 100% check now to ensure consistent localized messages

                    $.ajax({
                        url: '${baseUrl}/professor/classProgress/syllabus/courseDetail/lectureFundamentals?profCourseId=${pc.id}',
                        type: 'POST',
                        data: $('#lectureFundamentalsForm').serialize(),
                        dataType: 'json',
                        success: function (response) {
                            if (response.success) {
                                alert("<spring:message code="common.success"/>");
                            } else {
                                alert(response.message || "An error occurred while saving.");
                            }
                            $btn.prop('disabled', false).text('<spring:message code="common.save"/>');
                        },
                        error: function (xhr, status, error) {
                            alert("Error: " + (xhr.responseText || error || "Failed to save. Please try again."));
                            $btn.prop('disabled', false).text('<spring:message code="common.save"/>');
                        }
                    });
                });

                $("#prof-lecture-method-save").click(function (e) {
                    e.preventDefault();
                    $.post('${baseUrl}/professor/classProgress/syllabus/courseDetail/profLectureMethod?profCourseId=${pc.id}', $('#profLectureMethodForm').serialize(), function () {
                        alert("<spring:message code="common.success"/>");
                    });
                });
                $("#lecture-contents-save").click(function (e) {
                    e.preventDefault();
                    $.post('${baseUrl}/professor/classProgress/syllabus/courseDetail/lectureContents?profCourseId=${pc.id}', $('#lectureContentsForm').serialize(), function () {
                        alert("<spring:message code="common.success"/>");
                    });
                });
                $("body").on('click', '.print', function (e) {
                    e.preventDefault();
                    location.href = "${baseUrl}/professor/classProgress/inquiryCourse/courseDetail?print=true&profCourseId=${pc.id}";
                });
            });
        </script>