<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">
        <th class="pl-0" style=""><input type="checkbox" class="table-checkbox-all" name="tableCheckboxAll" onClick="setTableCheckboxAll()"></th>
        <th class="pl-0" style="min-width: 100px"><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.year"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.studentNumber"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.studentsName"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.advisor"/></span>
            <%--<th style=""><span class="text-primary"><spring:message code="common.major"/></span>--%>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="plan" items="${plans}" varStatus="varStatus">
        <tr>
            <td class="pl-0">
                <input type="checkbox" class="plan-checkbox" name="tableCheckbox" value="${plan.id}">
            </td>
            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                    ${plan.year}
            </td>
            <td>
                <a href="#" class="plan-detail" data-plan-id="${plan.id}">
                        ${plan.user.number}
                </a>
            </td>
            <td>
                    ${plan.user.getFullName()}
            </td>
            <td>
                    ${plan.user.division.name}
            </td>
            <td>
                    ${plan.user.advisor.getFullName()}
            </td>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script>
    function setTableCheckboxAll() {
        var checked = $("input[name=tableCheckboxAll]").is(":checked");
        var checkboxes = $("input[name=tableCheckbox]");
        setCheckboxAll(checked, checkboxes);
    }
    $(document).ready(function() {
        $("#student-list").DataTable();

        <c:if test="${not empty firstOne}">
        $(".detail-div").load("${baseUrl}/admin/profManagement/graduationResearch/planDetail?planId=${firstOne.id}");
        </c:if>
        $("body").on('click', '.plan-detail', function (e) {
            e.preventDefault();
            var planId = $(this).attr("data-plan-id");
            $(".detail-div").load("${baseUrl}/admin/profManagement/graduationResearch/planDetail?planId=" + planId);

        });

        $("body").on('click', '.print', function (e) {
            e.preventDefault();
            var checkedAll = $("input[name=tableCheckboxAll]").is(":checked");
            var year = $("#search-year").children("option:selected").val().trim();
            var division = $("#search-division").children("option:selected").val().trim();
            var advisor = $("#search-advisor").children("option:selected").val().trim();
            var name = $("#search-name").val().trim();
            var number = $("#search-number").val().trim();
            var size = $("input[name=tableCheckbox]:checked").length;
            if (!checkedAll && size == 0) {
                alert("<spring:message code="common.pleaseSelectItems"/>");
            } else {
                openPage("${baseUrl}/admin/profManagement/graduationResearch/planDetailForPrint?checkAll=" + checkedAll + "&year=" + year + "&division=" + division + "&advisor=" + advisor + "&name=" + name + "&number=" + number + "&" + parameterize("tableCheckbox"));
            }
        });
    });
</script>