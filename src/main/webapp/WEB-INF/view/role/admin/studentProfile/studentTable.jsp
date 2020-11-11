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

/*    function setCheckboxAll(checked, checkboxes, map) {
        for(var i=0; i<checkboxes.length; i++) {
            if(checked) {
                $(checkboxes[i]).prop("checked", true);
                $(checkboxes[i]).parent("span").addClass("checked");
                map.put($(checkboxes[i]).val(), true);
            } else {
                $(checkboxes[i]).prop("checked", false);
                $(checkboxes[i]).parent("span").removeClass("checked");
                map.put($(checkboxes[i]).val(), false);
            }
        }
    }*/
    function setTableCheckboxAll() {
        var checked = $("input[name=tableCheckboxAll]").is(":checked");
        var checkboxes = $("input[name=tableCheckbox]");
        setCheckboxAll(checked, checkboxes, tableMap);
    }
    $(document).ready(function() {
        $("#student-list").DataTable();

        <c:if test="${not empty firstUser}">
        $(".detail-div").load("${baseUrl}/admin/studentManagement/studentProfile/studentDetail?studentId=${firstUser.id}");
        </c:if>
        $("body").on('click', '.student-detail', function (e) {
            e.preventDefault();
            var studentId = $(this).attr("data-student-id");
            $(".detail-div").load("${baseUrl}/admin/studentManagement/studentProfile/studentDetail?studentId=" + studentId);

        });
        $("body").on('click', 'input[name=tableCheckbox]', function (e) {
            var value = $(this).val();
            tableMap.put(value, $(this).is(":checked"));
        });
        $("body").on('click', '.print', function (e) {
            e.preventDefault();
            var checkedAll = $("input[name=tableCheckboxAll]").is(":checked");
            var schoolYear = $("#search-school-year").children("option:selected").val().trim();
            var advisor = $("#search-advisor").children("option:selected").val().trim();
            var division = $("#search-division").children("option:selected").val().trim();
            openPage("${baseUrl}/admin/studentManagement/studentProfile/studentDetailsForPrint?checkAll=" + checkedAll + "&schoolYear=" + schoolYear + "&advisor=" + advisor + "&division=" + division + "&" + parameterize("tableCheckbox", tableMap));
        });


    });

</script>