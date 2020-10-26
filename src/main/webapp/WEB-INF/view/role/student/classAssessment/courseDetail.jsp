<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<form:form class="form" modelAttribute="lectureFundamentals" id="lectureFundamentalsForm" action="${baseUrl}/professor/classProgress/syllabus/courseDetail?courseId=${course.id}" method="post">
<div class="card-body">

    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <table class="table rounded">
                    <thead>
                    <tr class="table-secondary text-center">

                        <td><spring:message code="common.no"/></td>
                        <td>
                            Analytical Standard
                        </td>
                        <td>
                            Question
                        </td>
                        <td>
                            Very Negative (1pt)
                        </td>
                        <td>
                            Negative (2pt)
                        </td>
                        <td>
                            Normal (3pt)
                        </td>
                        <td>
                            Positive (4pt)
                        </td>
                        <td>
                            Very Positive (5pt)
                        </td>
                        <td>
                            Total of response
                        </td>
                        <td>
                            <spring:message code="common.avg"/>
                        </td>

                    </tr>
                    </thead>
                    <tbody>
                    <tr class="table-light text-center">
                        <td>1</td>
                        <td>
                            Design/Material
                        </td>
                        <td>
                            It proceed according to the proposed syllabus
                        </td>
                        <td>
                            <input type="radio"/>
                        </td>
                        <td>
                            <input type="radio"/>
                        </td>
                        <td>
                            <input type="radio"/>
                        </td>
                        <td>
                            <input type="radio"/>
                        </td>
                        <td>
                            <input type="radio"/>
                        </td>
                        <td>
                            <input type="radio"/>
                        </td>
                        <td>
                            <input type="radio"/>
                        </td>
                    </tr>


                    </tbody>
                </table>


            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">


                <label><spring:message code="common.comment"/></label>
                <textarea class="form-control" rows="6"></textarea>
            </div>
        </div>
    </div>
</div>
<div class="card-footer">
    <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.submit"/></button>
</div>

</form:form>
<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {
   $("#lecture-fundamental-save").click(function(e) {
     e.preventDefault();
       $.post('${baseUrl}/student/classInformation/classAssessment/courseDetail?courseId=${course.id}', $('#lectureFundamentalsForm').serialize(), function() {
         alert("<spring:message code="common.success"/>");
       });
   });
});
</script>