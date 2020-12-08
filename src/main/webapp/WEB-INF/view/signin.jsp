<%@include file="/WEB-INF/view/include/topTag.jsp"%>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<link href="${resources}/vendor/metronic_assets_7/assets/css/pages/login/login-1.css" rel="stylesheet" type="text/css"/>


<!--begin::Body-->
<body  id="kt_body"  class="header-fixed header-mobile-fixed subheader-enabled page-loading"  >

<!--begin::Main-->
<div class="d-flex flex-column flex-root">
    <!--begin::Login-->
    <div class="login login-1 login-signin-on d-flex flex-column flex-lg-row flex-column-fluid bg-white" id="kt_login">
        <!--begin::Aside-->
        <div class="login-aside d-flex flex-column flex-row-auto" style="background-color: #F2C98A;">
            <!--begin::Aside Top-->
            <div class="d-flex flex-column-auto flex-column pt-lg-40 pt-15">
                <!--begin::Aside header-->
                <a href="#" class="text-center mb-10">
                    <%--<img src="${resources}/vendor/metronic_assets_7/assets/media/logos/logo-letter-1.png" class="max-h-70px" alt=""/>--%>
                </a>
                <!--end::Aside header-->

                <!--begin::Aside title-->
                <h3 class="font-weight-bolder text-center font-size-h4 font-size-h1-lg" style="color: #986923;">
                    <spring:message code="common.signup.welcome"/>
                </h3>
                <!--end::Aside title-->
            </div>
            <!--end::Aside Top-->

            <!--begin::Aside Bottom-->
            <div class="aside-img d-flex flex-row-fluid bgi-no-repeat bgi-position-y-bottom bgi-position-x-center" style="background-image: url(${resources}/vendor/metronic_assets_7/assets/media/svg/illustrations/login-visual-4.svg)"></div>
            <!--end::Aside Bottom-->
        </div>
        <!--begin::Aside-->

        <!--begin::Content-->
        <div class="login-content flex-row-fluid d-flex flex-column justify-content-center position-relative overflow-hidden p-7 mx-auto">
            <!--begin::Content body-->
            <div class="d-flex flex-column-fluid flex-center">
                <!--begin::Signin-->
                <div class="login-form login-signin">
                    <!--begin::Form-->
                    <form class="form" novalidate="novalidate" id="kt_login_signin_form" action="j_spring_security_check" method="post">
                        <!--begin::Title-->
                        <div class="pb-13 pt-lg-0 pt-5">
                            <h3 class="font-weight-bolder text-dark font-size-h4 font-size-h1-lg"><spring:message code="common.signin"/></h3>
                            <%--<span class="text-muted font-weight-bold font-size-h4">New Here? <a href="javascript:;" id="kt_login_signup" class="text-primary font-weight-bolder">Create an Account</a></span>--%>
                        </div>
                        <!--begin::Title-->

                        <!--begin::Form group-->
                        <div class="form-group">
                            <label class="font-size-h6 font-weight-bolder text-dark"><spring:message code="common.username"/></label>
                            <input class="form-control form-control-solid h-auto py-7 px-6 rounded-lg" type="text" name="j_username"/>
                        </div>
                        <!--end::Form group-->

                        <!--begin::Form group-->
                        <div class="form-group">
                            <div class="d-flex justify-content-between mt-n5">
                                <label class="font-size-h6 font-weight-bolder text-dark pt-5"><spring:message code="common.password"/></label>


                            </div>

                            <input class="form-control form-control-solid h-auto py-7 px-6 rounded-lg" type="password" name="j_password"/>
                            <br/>
                            <%--<a href="javascript:;" class="text-primary font-size-h6 font-weight-bolder text-hover-primary pt-5" id="kt_login_forgot">
                                Forgot Password ?
                            </a>--%>
                        </div>
                        <!--end::Form group-->

                        <!--begin::Action-->
                        <div class="pb-lg-0 pb-5">
                            <button type="submit" <%--id="kt_login_signin_submit" --%>class="btn btn-primary font-weight-bolder font-size-h6 px-8 py-4 my-3 mr-3"><spring:message code="common.signin"/></button>

                            <a href="/signup" class="btn btn-light-primary font-weight-bolder px-8 py-4 my-3 font-size-lg">
                            <span class="svg-icon svg-icon-md"><!--end::Svg Icon--></span>                            <spring:message code="common.signup"/>
                            </a>
                        </div>
                        <!--end::Action-->
                    </form>
                    <!--end::Form-->
                </div>
                <!--end::Signin-->


                <!--begin::Forgot-->
                <%--<div class="login-form login-forgot">
                    <!--begin::Form-->
                    <form class="form" novalidate="novalidate"  id="kt_login_forgot_form">
                        <!--begin::Title-->
                        <div class="pb-13 pt-lg-0 pt-5">
                            <h3 class="font-weight-bolder text-dark font-size-h4 font-size-h1-lg">Forgotten Password ?</h3>
                            <p class="text-muted font-weight-bold font-size-h4">Enter your email to reset your password</p>
                        </div>
                        <!--end::Title-->

                        <!--begin::Form group-->
                        <div class="form-group">
                            <input class="form-control form-control-solid h-auto py-7 px-6 rounded-lg font-size-h6" type="email" placeholder="Email" name="email" autocomplete="off"/>
                        </div>
                        <!--end::Form group-->

                        <!--begin::Form group-->
                        <div class="form-group d-flex flex-wrap pb-lg-0">
                            <button type="button" id="kt_login_forgot_submit" class="btn btn-primary font-weight-bolder font-size-h6 px-8 py-4 my-3 mr-4">Submit</button>
                            <button type="button" id="kt_login_forgot_cancel" class="btn btn-light-primary font-weight-bolder font-size-h6 px-8 py-4 my-3">Cancel</button>
                        </div>
                        <!--end::Form group-->
                    </form>
                    <!--end::Form-->
                </div>--%>
                <!--end::Forgot-->
            </div>
            <!--end::Content body-->

            <!--begin::Content footer-->
            <%--<div class="d-flex justify-content-lg-start justify-content-center align-items-end py-7 py-lg-0">
                <a href="#" class="text-primary font-weight-bolder font-size-h5">Terms</a>
                &lt;%&ndash;<a href="#" class="text-primary ml-10 font-weight-bolder font-size-h5">Plans</a>&ndash;%&gt;
                <a href="#" class="text-primary ml-10 font-weight-bolder font-size-h5">Contact Us</a>
            </div>--%>
            <!--end::Content footer-->
        </div>
        <!--end::Content-->
    </div>
    <!--end::Login-->
</div>
<!--end::Main-->


<script>var HOST_URL = "https://preview.keenthemes.com/metronic/theme/html/tools/preview";</script>
<!--begin::Global Config(global config for global JS scripts)-->
<script>
    var KTAppSettings = {
        "breakpoints": {
            "sm": 576,
            "md": 768,
            "lg": 992,
            "xl": 1200,
            "xxl": 1200
        },
        "colors": {
            "theme": {
                "base": {
                    "white": "#ffffff",
                    "primary": "#6993FF",
                    "secondary": "#E5EAEE",
                    "success": "#1BC5BD",
                    "info": "#8950FC",
                    "warning": "#FFA800",
                    "danger": "#F64E60",
                    "light": "#F3F6F9",
                    "dark": "#212121"
                },
                "light": {
                    "white": "#ffffff",
                    "primary": "#E1E9FF",
                    "secondary": "#ECF0F3",
                    "success": "#C9F7F5",
                    "info": "#EEE5FF",
                    "warning": "#FFF4DE",
                    "danger": "#FFE2E5",
                    "light": "#F3F6F9",
                    "dark": "#D6D6E0"
                },
                "inverse": {
                    "white": "#ffffff",
                    "primary": "#ffffff",
                    "secondary": "#212121",
                    "success": "#ffffff",
                    "info": "#ffffff",
                    "warning": "#ffffff",
                    "danger": "#ffffff",
                    "light": "#464E5F",
                    "dark": "#ffffff"
                }
            },
            "gray": {
                "gray-100": "#F3F6F9",
                "gray-200": "#ECF0F3",
                "gray-300": "#E5EAEE",
                "gray-400": "#D6D6E0",
                "gray-500": "#B5B5C3",
                "gray-600": "#80808F",
                "gray-700": "#464E5F",
                "gray-800": "#1B283F",
                "gray-900": "#212121"
            }
        },
        "font-family": "Poppins"
    };
</script>
<!--end::Global Config-->

<!--begin::Global Theme Bundle(used by all pages)-->
<script src="${resources}/vendor/metronic_assets_7/assets/plugins/global/plugins.bundle.js"></script>
<script src="${resources}/vendor/metronic_assets_7/assets/plugins/custom/prismjs/prismjs.bundle.js"></script>
<script src="${resources}/vendor/metronic_assets_7/assets/js/scripts.bundle.js"></script>
<!--end::Global Theme Bundle-->


<!--begin::Page Scripts(used by this page)-->
<%--<script src="${resources}/vendor/metronic_assets_7/assets/js/pages/custom/login/login-general.js"></script>--%>
<!--end::Page Scripts-->
</body>
<!--end::Body-->
</html>