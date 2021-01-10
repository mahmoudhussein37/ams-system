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
                    <spring:message code="common.registeredDate"/>
                </td>
                <td>
                    <spring:message code="common.use"/>
                </td>
            </tr>
            </thead>
            <tbody>
            <c:set var="altCount" value="1"/>
            <c:forEach var="ac" items="${altCourses}" varStatus="varStatus">
                <c:if test="${ac.type eq 'alternative'}">
                    <tr class="table-light text-center">

                        <td>
                                ${altCount}
                        </td>
                        <td>
                                ${ac.course.code}
                        </td>
                        <td>
                                ${ac.course.title}
                        </td>
                        <td>
                            <spring:message code="subj.category.${ac.course.subjCategory}"/>
                        </td>
                        <td>
                            <fmt:formatDate pattern="dd-MMM-yyyy" value="${ac.course.registeredDate}"/>
                        </td>
                        <td>
                                ${ac.course.enabled ? 'Y' : 'N'}
                        </td>


                    </tr>
                    <c:set var="altCount" value="${altCount + 1}"/>
                </c:if>
            </c:forEach>
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
                    <spring:message code="common.oldCourseCode"/>
                </td>
                <td>
                    <spring:message code="common.oldCourseTitle"/>
                </td>

                <td>
                    <spring:message code="common.subjCategory"/>
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
            <c:set var="preCount" value="1"/>
            <c:forEach var="ac" items="${altCourses}" varStatus="varStatus">
                <c:if test="${ac.type eq 'prerequisite'}">
                    <tr class="table-light text-center">

                        <td>
                                ${preCount}
                        </td>
                        <td>
                                ${ac.course.code}
                        </td>
                        <td>
                                ${ac.course.title}
                        </td>
                        <td>
                            <spring:message code="subj.category.${ac.course.subjCategory}"/>
                        </td>
                        <td>
                            <fmt:formatDate pattern="dd-MMM-yyyy" value="${ac.course.registeredDate}"/>
                        </td>
                        <td>
                                ${ac.course.enabled ? 'Y' : 'N'}
                        </td>

                    </tr>
                    <c:set var="preCount" value="${preCount + 1}"/>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>
    $(document).ready(function() {

    });
</script>