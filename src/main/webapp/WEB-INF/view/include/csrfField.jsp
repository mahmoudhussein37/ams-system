<%-- Include inside every <form> tag that submits via POST --%>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
