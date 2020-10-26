<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="row">

    <div class="col-md-4">

        <div class="form-group">
            <label><spring:message code="common.approve"/></label>
            <input type="text" value="Y"/>
        </div>
    </div>
</div>
<div class="row">

    <div class="col-md-12">

        <div class="form-group">
            <label><spring:message code="student.consultation"/></label>
            <textarea class="form-control" rows="6" disabled></textarea>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>
    </div>
</div>




<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>