<%@include file="/WEB-INF/view/include/topTag.jsp" %>

<table class="table table-head-custom table-vertical-center" id="student-list">
    <thead>
    <tr class="text-uppercase">
        <th class="pl-0" style=""><input type="checkbox" class="table-checkbox-all" name="tableCheckboxAll" onClick="setTableCheckboxAll()"></th>
        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><span class="text-primary"><spring:message code="common.schoolYear"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.studentNumber"/></span></th>
        <th style=""><span class="text-primary"><spring:message code="common.name"/></span>
        <th style=""><span class="text-primary"><spring:message code="common.department"/></span>
        <%--<th style=""><span class="text-primary"><spring:message code="common.advisor"/></span>--%>
        <%--<th style="min-width: 150px"><span class="text-primary"><spring:message code="common.major"/></span>--%>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="studentUser" items="${userList}" varStatus="varStatus">
        <tr>
            <td class="pl-0">
                    <input type="checkbox" class="table-checkbox" name="tableCheckbox" value="${studentUser.id}">
            </td>
            <td class="pl-0">
                    ${varStatus.count}
            </td>
            <td>
                    ${studentUser.schoolYear}
            </td>
            <td>
                <a href="#" class="student-detail" data-student-id="${studentUser.id}">
                        ${studentUser.number}
                </a>
            </td>
            <td>
                    ${studentUser.getFullName()}
            </td>
            <td>
                    ${studentUser.division.name}
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

        <c:if test="${not empty firstUser}">
        $(".detail-div").load("${baseUrl}/admin/studentManagement/schoolYear/studentDetail?studentId=${firstUser.id}");
        </c:if>
        $("body").on('click', '.student-detail', function (e) {
            e.preventDefault();
            var studentId = $(this).attr("data-student-id");
            $(".detail-div").load("${baseUrl}/admin/studentManagement/schoolYear/studentDetail?studentId=" + studentId);

        });
        $("body").on('click', '.print', function (e) {
            e.preventDefault();
            var checkedAll = $("input[name=tableCheckboxAll]").is(":checked");
            var schoolYear = $("#search-school-year").children("option:selected").val().trim();
            var advisor = $("#search-advisor").children("option:selected").val().trim();
            var division = $("#search-division").children("option:selected").val().trim();
            var size = $("input[name=tableCheckbox]:checked").length;
            if (!checkedAll && size == 0) {
                alert("<spring:message code="common.pleaseSelectItems"/>");
            } else {
                location.href="${baseUrl}/admin/studentManagement/studentProfile/studentDetailForPrint?checkAll=" + checkedAll + "&schoolYear=" + schoolYear + "&advisor=" + advisor + "&division=" + division + "&" + parameterize("tableCheckbox");
            }

        });
        $("body").on('click', '.increase-grade', function (e) {
            e.preventDefault();
            var checkedAll = $("input[name=tableCheckboxAll]").is(":checked");
            var schoolYear = $("#search-school-year").children("option:selected").val().trim();
            var advisor = $("#search-advisor").children("option:selected").val().trim();
            var division = $("#search-division").children("option:selected").val().trim();
            var size = $("input[name=tableCheckbox]:checked").length;
            if (!checkedAll && size == 0) {
                alert("<spring:message code="common.pleaseSelectItems"/>");
            } else {
                $.post("${baseUrl}/admin/studentManagement/schoolYear/increaseYear?checkAll=" + checkedAll + "&schoolYear=" + schoolYear + "&advisor=" + advisor + "&division=" + division + "&" + parameterize("tableCheckbox"), function() {
                    alert("<spring:message code="common.success"/>");
                    location.reload();
                });
            }

        });

    });

</script>