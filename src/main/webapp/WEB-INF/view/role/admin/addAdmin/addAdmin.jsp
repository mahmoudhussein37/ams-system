<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>
<!--begin::Content-->
<div class="content  d-flex flex-column flex-column-fluid" id="kt_content">
    <!--begin::Entry-->
    <div class="d-flex flex-column-fluid">
        <!--begin::Container-->
        <div class=" container ">
            <div class="row">
                <div class="col-md-12">
                    <!--begin::Card-->
                    <div class="card card-custom">
                        <div class="card-header">
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub5_9"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="table-div">
                            </div>
                            <br/><br/>
                            <div class="separator separator-solid my-5"></div>
                            <br/><br/>

                            <form:form modelAttribute="adminUser" method="post" id="signup-form">
                                <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.information"/></h3>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.firstName"/></label>
                                            <form:input type="text" path="contact.firstName" class="form-control"/>
                                                <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><spring:message code="common.lastName"/></label>
                                            <form:input type="text" path="contact.lastName" class="form-control"/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>

                                    </div>
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.username"/></label>
                                            <form:input type="text" path="username" class="form-control"/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.password"/></label>
                                            <form:input type="password" path="password" class="form-control"/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">

                                        <div class="form-group">
                                            <label><spring:message code="common.passwordAgain"/></label>
                                            <input type="password" name="passwordAgain" class="form-control"/>
                                                <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                                        </div>
                                    </div>
                                    <div class="col-md-3">


                                    </div>
                                </div>
                                <br/>
                                <button type="submit" class="btn btn-primary mr-2"><spring:message code="common.register"/></button>


                            </form:form>
                        </div>
                    </div>
                    <!--end::Card-->
                </div>
            </div>


        </div>
        <!--end::Container-->
    </div>
    <!--end::Entry-->
</div>
<!--end::Content-->

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>


<%@include file="/WEB-INF/view/include/userPanel.jsp" %>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>
    var KTFormControls = function () {
        // Private functions
        var _userValidation = function () {
            FormValidation.formValidation(
                document.getElementById('signup-form'),
                {
                    fields: {
                        username: {
                            validators: {
                                notEmpty: {
                                    message: 'Username is required'
                                },
                                emailAddress: {
                                    message: 'The value is not a valid email address'
                                }
                            }
                        },
                        password: {
                            validators: {
                                notEmpty: {
                                    message: 'Password is required'
                                }
                            }
                        },
                        "passwordAgain": {
                            validators: {
                                identical: {
                                    compare: function() {
                                        var form = document.getElementById('signup-form');
                                        return form.querySelector('[name="password"]').value;
                                    },
                                    message: 'The password and its confirm are not the same'
                                }
                            }
                        },
                        "contact.firstName": {
                            validators: {
                                notEmpty: {
                                    message: 'First name is required'
                                }
                            }
                        },
                        "contact.lastName": {
                            validators: {
                                notEmpty: {
                                    message: 'Last name is required'
                                }
                            }
                        },


                        role: {
                            validators: {
                                notEmpty: {
                                    message: 'Please select an option'
                                }
                            }
                        },


                    },

                    plugins: { //Learn more: https://formvalidation.io/guide/plugins
                        trigger: new FormValidation.plugins.Trigger(),
                        // Bootstrap Framework Integration
                        bootstrap: new FormValidation.plugins.Bootstrap(),
                        // Validate fields when clicking the Submit button
                        submitButton: new FormValidation.plugins.SubmitButton(),
                        // Submit the form when all fields are valid
                        defaultSubmit: new FormValidation.plugins.DefaultSubmit(),

                    }
                }
            );
        }



        return {
            // public functions
            init: function() {
                _userValidation();
            }
        };
    }();
    $(document).ready(function() {

        $(".table-div").load("${baseUrl}/admin/systemManagement/addAdmin/adminTable");
        KTFormControls.init();
        <c:if test="${not empty result}">
        alert("<spring:message code='common.success' javaScriptEscape="true" />");
        location.href="${baseUrl}/admin/systemManagement/addAdmin";
        </c:if>
    });
</script>
</body>
</html>