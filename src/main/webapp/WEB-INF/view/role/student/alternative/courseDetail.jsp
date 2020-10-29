<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<ul class="nav nav-tabs nav-tabs-line">
    <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1"><spring:message code="common.alternative"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2"><spring:message code="common.prerequisite"/></a>
    </li>

</ul>
<div class="tab-content mt-5" id="myTabContent">
    <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_1">

        <table class="table rounded">
            <thead>
            <tr class="table-secondary text-center">

                    <td>
                            <spring:message code="common.no"/>
                    </td>
                <td>
                    <spring:message code="common.oldCourseCode"/>
                </td>
                <td>
                    <spring:message code="common.oldCourseTitle"/>
                </td>

                <td>
                    <spring:message code="common.subjCategory"/>
                </td>
                <td>
                    <spring:message code="professor.course.ltlp"/>
                </td>
                <td>
                    <spring:message code="common.registeredDate"/>
                </td>
                <td>
                    <spring:message code="common.use"/>
                </td>
            </tr>
            </thead>
            <tbody>
            <tr class="table-light text-center">
                <td>
                    1
                </td>
                <td>
                    IFA140
                </td>
                <td>
                    Electronic circuit
                </td>
                <td>
                    Major
                </td>

                <td>
                    5-3-2-0
                </td>
                <td>
                    YYYY-MM-dd
                </td>
                <td>
                    Y
                </td>

            </tr>


            </tbody>
        </table>


    </div>
    <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
        <table class="table rounded">
            <thead>
            <tr class="table-secondary text-center">

                <td>
                    <spring:message code="common.no"/>
                </td>
                <td>
                    <spring:message code="common.postCourseCode"/>
                </td>
                <td>
                    <spring:message code="common.postCourseTitle"/>
                </td>
                <td>
                    <spring:message code="common.preCourseCode"/>
                </td>
                <td>
                    <spring:message code="common.preCourseTitle"/>
                </td>

                <td>
                    <spring:message code="common.subjCategory"/>
                </td>
                <td>
                    <spring:message code="professor.course.ltlp"/>
                </td>
                <td>
                    <spring:message code="common.specifiedDate"/>
                </td>

                <td>
                    <spring:message code="common.use"/>
                </td>
            </tr>
            </thead>
            <tbody>
            <tr class="table-light text-center">
                <td>
                    1
                </td>
                <td>
                    IFA140
                </td>
                <td>
                    Electronic circuit
                </td>
                <td>
                    IFA130
                </td>
                <td>
                    Electronic Circuit Practice
                </td>
                <td>
                    Major
                </td>

                <td>
                    5-3-2-0
                </td>
                <td>
                    YYYY-MM-dd
                </td>
                <td>
                    Y
                </td>

            </tr>


            </tbody>
        </table>
    </div>


</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
$(document).ready(function() {

});
</script>