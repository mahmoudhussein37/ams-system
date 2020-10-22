<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<div class="row">
    <div class="col-md-6">
        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.makeupClassList"/></h3>
        <table class="table">

            <tr>
                <td>
                    <spring:message code="common.date"/>
                </td>
                <td>
                    2020-09-14
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                </td>
            </tr>

        </table>
    </div>
    <div class="col-md-6">
        <h3 class="font-size-lg text-dark font-weight-bold mb-6">&nbsp;</h3>
        <table class="table">

            <tr>
                <td>
                    <spring:message code="professor.class"/>
                </td>
                <td>
                    <spring:message code="common.date"/>
                </td>
            </tr>
            <tr>
                <td>
                    01A
                </td>
                <td>
                    09:00~09:30
                </td>
            </tr>
            <tr>
                <td>
                    01B
                </td>
                <td>
                    09:30~10:00
                </td>
            </tr>
            <tr>
                <td>
                    02A
                </td>
                <td>
                    10:00~10:30
                </td>
            </tr>
            <tr>
                <td>
                    02B
                </td>
                <td>
                    10:30~11:00
                </td>
            </tr>

        </table>
        <button type="button" class="btn btn-primary mr-2"><spring:message code="common.delete"/></button>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="professor.makeupClass"/></h3>
        <spring:message code="professor.originalDate"/>
        <table class="table">

            <tr>
                <td>
                    <spring:message code="common.date"/>
                </td>

            </tr>
            <c:forEach var="d" begin="1" end="9">
                <tr>
                    <td>
                        2020-09-0${d}
                    </td>

                </tr>
            </c:forEach>

        </table>
    </div>
    <div class="col-md-6">
        <h3 class="font-size-lg text-dark font-weight-bold mb-6">&nbsp;</h3>
        <spring:message code="professor.originalTime"/>
        <table class="table">

            <tr>
                <td></td>
                <td>
                    <spring:message code="professor.class"/>
                </td>
                <td>
                    <spring:message code="common.date"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox"/>
                </td>
                <td>
                    01A
                </td>
                <td>
                    09:00~09:30
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox"/>
                </td>
                <td>
                    01B
                </td>
                <td>
                    09:30~10:00
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox"/>
                </td>
                <td>
                    02A
                </td>
                <td>
                    10:00~10:30
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox"/>
                </td>
                <td>
                    02B
                </td>
                <td>
                    10:30~11:00
                </td>
            </tr>

        </table>
    </div>
</div>
<br/>
<br/>
<br/>
<div class="row">
    <div class="col-md-6">

        <spring:message code="professor.makeupDate"/>
        <table class="table">

            <tr>
                <td>
                    <spring:message code="common.date"/>
                </td>

            </tr>
            <c:forEach var="d" begin="1" end="9">
                <tr>
                    <td>
                        2020-09-0${d}
                    </td>

                </tr>
            </c:forEach>

        </table>
    </div>
    <div class="col-md-6">
        <spring:message code="professor.makeupTime"/>
        <table class="table">

            <tr>
                <td></td>
                <td>
                    <spring:message code="professor.class"/>
                </td>
                <td>
                    <spring:message code="common.date"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox"/>
                </td>
                <td>
                    01A
                </td>
                <td>
                    09:00~09:30
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox"/>
                </td>
                <td>
                    01B
                </td>
                <td>
                    09:30~10:00
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox"/>
                </td>
                <td>
                    02A
                </td>
                <td>
                    10:00~10:30
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox"/>
                </td>
                <td>
                    02B
                </td>
                <td>
                    10:30~11:00
                </td>
            </tr>

        </table>
        <button type="button" class="btn btn-primary mr-2"><spring:message code="common.submit"/></button>
    </div>
</div>

<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {
   $("#lecture-fundamental-save").click(function(e) {
     e.preventDefault();
       $.post('${baseUrl}/professor/classProgress/syllabus/courseDetail?courseId=${course.id}', $('#lectureFundamentalsForm').serialize(), function() {
         alert("<spring:message code="common.success"/>");
       });
   });
});
</script>