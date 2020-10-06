<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<div class="card-body">
    <div class="row">
        <div class="col-md-6">

            <div class="form-group">
                <label><spring:message code="common.studentNumber"/></label>
                <input type="text" class="form-control" value="${studentUser.number}" disabled/>
                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
            </div>


            <div class="form-group">
                <label><spring:message code="common.division"/></label>
                <input type="text" class="form-control" value="${user.division.name}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>




        </div>
        <div class="col-md-6">
            <div class="form-group">
                <label><spring:message code="common.name"/></label>
                <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
            <div class="form-group">
                <label><spring:message code="common.major"/></label>
                <input type="text" class="form-control" value="${user.major.name}" disabled/>
                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
            </div>
        </div>
    </div>

    <br/>
    <div class="separator separator-solid my-5"></div>
    <br/>

</div>



<%@include file="/WEB-INF/view/include/footerScript.jsp" %>
<script>

</script>