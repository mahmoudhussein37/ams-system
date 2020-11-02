<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<link href="${resources}/vendor/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css"/>
<table class="table table-head-custom table-vertical-center" id="course-list">
    <thead>
    <tr class="table-secondary text-center">
        <th class="pl-0" style=""><spring:message code="common.no"/></th>
        <th style=""><spring:message code="common.code"/></th>
        <th style=""><spring:message code="common.name"/></th>
        <th style=""></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="classroom" items="${classroomList}" varStatus="varStatus">
        <c:if test="${classroom.enabled}">
            <tr class="text-center">

                <td class="pl-0">
                        ${varStatus.count}
                </td>
                <td class="pl-0">
                    <a href="#" class="course-editable" data-type="text" data-name="code" data-url="${baseUrl}/admin/systemManagement/classroom/classroomEditable" data-pk="${classroom.id}" data-original-title="<spring:message code="common.code"/>">${classroom.code}</a>
                </td>
                <td class="pl-0">
                    <a href="#" class="course-editable" data-type="text" data-name="name" data-url="${baseUrl}/admin/systemManagement/classroom/classroomEditable" data-pk="${classroom.id}" data-original-title="<spring:message code="common.name"/>">${classroom.name}</a>
                </td>


                <td>
                    <button class="btn btn-light btm-sm disable-lecture" data-id="${classroom.id}"><spring:message code="common.disable"/></button>
                    <button class="btn btn-light btm-sm delete-lecture" data-id="${classroom.id}"><spring:message code="common.delete"/></button>
                </td>
            </tr>
        </c:if>

    </c:forEach>
    <c:forEach var="classroom" items="${classroomList}" varStatus="varStatus">
        <c:if test="${not classroom.enabled}">
            <tr class="text-center disabled-tr">

                <td class="pl-0">
                        ${varStatus.count}
                </td>
                <td class="pl-0">
                    <a href="#" class="course-editable" data-type="text" data-name="code" data-url="${baseUrl}/admin/systemManagement/classroom/classroomEditable" data-pk="${classroom.id}" data-original-title="<spring:message code="common.code"/>">${classroom.code}</a>
                </td>
                <td class="pl-0">
                    <a href="#" class="course-editable" data-type="text" data-name="name" data-url="${baseUrl}/admin/systemManagement/classroom/classroomEditable" data-pk="${classroom.id}" data-original-title="<spring:message code="common.name"/>">${classroom.name}</a>
                </td>


                <td>
                    <button class="btn btn-light btm-sm enable-lecture" data-id="${classroom.id}"><spring:message code="common.enable"/></button>
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
            $.post("${baseUrl}/admin/systemManagement/classroom/deleteClassroom?id=" + id, function(result) {
                alert("<spring:message code="common.success"/>");
                location.reload();
            });
        });

        $("body").on("click", ".enable-lecture", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/systemManagement/classroom/enableClassroom?id=" + id, function(result) {
                if(result == true) {
                    alert("<spring:message code="common.success"/>");
                    location.reload();
                }

            });
        });
        $("body").on("click", ".disable-lecture", function(e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            $.post("${baseUrl}/admin/systemManagement/classroom/disableClassroom?id=" + id, function(result) {
                if(result == true) {
                    alert("<spring:message code="common.success"/>");
                    location.reload();
                }

            });
        });
    });



</script>