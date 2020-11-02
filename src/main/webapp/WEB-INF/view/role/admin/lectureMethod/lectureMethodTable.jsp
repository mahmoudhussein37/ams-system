<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="table-secondary text-center">
        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><spring:message code="professor.methods"/></th>
        <th style=""></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="lectureMethod" items="${lectureMethodList}" varStatus="varStatus">
        <c:if test="${lectureMethod.enabled}">
            <tr class="text-center">

                <td class="pl-0">
                        ${varStatus.count}
                </td>
                <td class="pl-0">
                    <a href="#" class="course-editable" data-type="text" data-name="name" data-url="${baseUrl}/admin/systemManagement/lectureMethod/lectureMethodEditable" data-pk="${lectureMethod.id}" data-original-title="<spring:message code="common.name"/>">${lectureMethod.name}</a>
                </td>


                <td>
                    <button class="btn btn-light btm-sm delete-lecture" data-id="${lectureMethod.id}"><spring:message code="common.delete"/></button>
                </td>
            </tr>
        </c:if>

    </c:forEach>
    <c:forEach var="lectureMethod" items="${lectureMethodList}" varStatus="varStatus">
        <c:if test="${not lectureMethod.enabled}">
            <tr class="text-center disabled-tr">

                <td class="pl-0">
                        ${varStatus.count}
                </td>
                <td class="pl-0">
                    <a href="#" class="course-editable" data-type="text" data-name="name" data-url="${baseUrl}/admin/systemManagement/lectureMethod/lectureMethodEditable" data-pk="${lectureMethod.id}" data-original-title="<spring:message code="common.name"/>">${lectureMethod.name}</a>
                </td>


                <td>
                    <button class="btn btn-light btm-sm enable-lecture" data-id="${lectureMethod.id}"><spring:message code="common.enable"/></button>
                </td>
            </tr>
        </c:if>

    </c:forEach>
    


    </tbody>
</table>

<script>
    $(document).ready(function() {
        $("#course-list").DataTable();
        $('.course-editable').editable();

        $("body").on("click", ".delete-lecture", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/systemManagement/lectureMethod/deleteLectureMethod?id=" + id, function(result) {
                alert("<spring:message code="common.success"/>");
                location.reload();
            });
        });

        $("body").on("click", ".enable-lecture", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/systemManagement/lectureMethod/enableLectureMethod?id=" + id, function(result) {
                if(result == true) {
                    alert("<spring:message code="common.success"/>");
                    location.reload();
                }

            });
        });
    });



</script>