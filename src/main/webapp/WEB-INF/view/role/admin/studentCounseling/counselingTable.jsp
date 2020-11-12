<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">

        <th class="pl-0" style=""><input type="checkbox" class="table-checkbox-all" name="tableCheckboxAll" onClick="setTableCheckboxAll()"></th>
        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.year"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.date"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.studentNumber"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.name"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="counseling" items="${counselingList}" varStatus="varStatus">
        <tr>
            <td class="pl-0">
                <input type="checkbox" class="counseling-checkbox" name="tableCheckbox" value="${counseling.id}">
            </td>
            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                    ${counseling.year}
            </td>
            <td>
                    ${counseling.date}
            </td>
            <td>
                <a href="#" class="student-detail" data-counseling-id="${counseling.id}">
                        ${counseling.studentUser.number}
                </a>
            </td>
            <td>
                    ${counseling.studentUser.getFullName()}
            </td>
            <td>
                    ${counseling.studentUser.division.name}
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

        <c:if test="${not empty firstCounseling}">
        $(".detail-div").load("${baseUrl}/admin/studentManagement/studentCounseling/counselingDetail?counselingId=${firstCounseling.id}");
        </c:if>
        $("body").on('click', '.student-detail', function (e) {
            e.preventDefault();
            var counselingId = $(this).attr("data-counseling-id");
            $(".detail-div").load("${baseUrl}/admin/studentManagement/studentCounseling/counselingDetail?counselingId=" + counselingId);

        });

        $("body").on('click', '.print', function (e) {
            e.preventDefault();
            var checkedAll = $("input[name=tableCheckboxAll]").is(":checked");
            var year = $("#search-year").children("option:selected").val().trim();
            var name = $("#search-name").val().trim();
            var size = $("input[name=tableCheckbox]:checked").length;
            if (!checkedAll && size == 0) {
                alert("<spring:message code="common.pleaseSelectItems"/>");
            } else {
                openPage("${baseUrl}/admin/studentManagement/studentCounseling/counselingDetailsForPrint?checkAll=" + checkedAll + "&year=" + year + "&name=" + name + "&" + parameterize("tableCheckbox"));
            }
        });
    });

</script>