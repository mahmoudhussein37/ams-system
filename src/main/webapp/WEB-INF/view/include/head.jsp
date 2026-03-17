<!--end::Head-->

<!DOCTYPE html>
<c:choose>
    <c:when test="${isRTL}">
        <html lang="${locale}" dir="rtl">
    </c:when>
    <c:otherwise>
        <html lang="${locale}" dir="ltr">
    </c:otherwise>
</c:choose>

<!--begin::Head-->

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
    <meta http-equiv='Expires' content='-1'>
    <meta http-equiv='cache-control' content='no-cache'>
    <meta http-equiv='pragma' content='no-cache'>
    <% response.setHeader("Cache-Control","no-cache"); response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",-1); response.setHeader("Cache-Control", "no-store" ); //HTTP 1.1 %>
        <base href="">
        <title>
            <spring:message code="common.siteTitle" />
        </title>
        <meta name="description" content="<spring:message code=" common.siteTitle" />"/>
        <!--begin::Fonts-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
        <c:if test="${isRTL}">
            <link rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Tajawal:wght@300;400;500;700&display=swap" />
        </c:if>
        <!--end::Fonts-->

        <c:choose>
            <c:when test="${isRTL}">
                <link
                    href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/fullcalendar/fullcalendar.bundle.rtl.css"
                    rel="stylesheet" type="text/css" />
                <link href="${resources}/vendor/metronic_assets_7/assets/plugins/global/plugins.bundle.rtl.css"
                    rel="stylesheet" type="text/css" />
                <link href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/prismjs/prismjs.bundle.rtl.css"
                    rel="stylesheet" type="text/css" />
                <link
                    href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/datatables/datatables.bundle.rtl.css"
                    rel="stylesheet" type="text/css" />
                <link href="${resources}/vendor/metronic_assets_7/assets/css/style.bundle.rtl.css" rel="stylesheet"
                    type="text/css" />
            </c:when>
            <c:otherwise>

                <link
                    href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/fullcalendar/fullcalendar.bundle.css"
                    rel="stylesheet" type="text/css" />
                <link href="${resources}/vendor/metronic_assets_7/assets/plugins/global/plugins.bundle.css"
                    rel="stylesheet" type="text/css" />
                <link href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/prismjs/prismjs.bundle.css"
                    rel="stylesheet" type="text/css" />
                <link
                    href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/datatables/datatables.bundle.css"
                    rel="stylesheet" type="text/css" />
                <link href="${resources}/vendor/metronic_assets_7/assets/css/style.bundle.css" rel="stylesheet"
                    type="text/css" />

            </c:otherwise>
        </c:choose>

        <link rel="shortcut icon" href="${resources}/vendor/metronic_assets_7/assets/media/logos/favicon.ico" />
        <style>
            .table.table-head-custom thead tr,
            .table.table-head-custom thead th {
                text-transform: none;
            }

            <c:if test="${currentRole eq 'admin'}">.header-menu .menu-nav>.menu-item {
                padding-top: 0;
                padding-bottom: 0;
                max-width: 120px;
            }

            .header-menu .menu-nav>.menu-item>.menu-link {
                padding-top: 0;
                padding-bottom: 0;
            }

            .header-menu .menu-nav>.menu-item>.menu-link .menu-text {
                font-size: 12px;
            }

            </c:if>.notice-table td {
                border-top: 0 !important;
            }

            .print-div {
                text-align: $ {
                    isRTL ? 'left': 'right'
                }

                ;
            }

            @media print {
                .div-to-print {
                    background-color: white;
                    height: 100%;
                    width: 100%;
                    position: fixed;
                    top: 0;
                    left: 0;
                    margin: 0;
                    padding: 15px;
                    font-size: 14px;
                    line-height: 18px;
                }
            }

            .disabled-tr {
                color: grey;
                background-color: #e7e5e5;

            }

            /* .header .header-top {
            background-color: #82a8d2;

        }*/

            <c:if test="${isRTL}">

            /* RTL: Arabic font */
            body,
            h1,
            h2,
            h3,
            h4,
            h5,
            h6,
            .menu-text,
            .nav-link,
            .btn,
            label,
            input,
            textarea,
            select,
            option,
            .form-control,
            .dataTables_wrapper {
                font-family: 'Tajawal', 'Segoe UI', Tahoma, sans-serif !important;
            }

            /* RTL: Input and textarea direction */
            input[type="text"],
            input[type="search"],
            input[type="email"],
            input[type="password"],
            input[type="number"],
            input[type="tel"],
            textarea,
            select,
            .form-control {
                direction: rtl;
                text-align: start;
            }

            /* RTL: DataTables search input */
            .dataTables_filter input {
                direction: rtl;
                text-align: start;
            }

            /* RTL: DataTables pagination direction */
            .dataTables_paginate {
                direction: rtl;
            }

            .dataTables_paginate .paginate_button {
                float: right;
            }

            /* RTL: Fix directional margin utility */
            .marginRight30 {
                margin-right: 0 !important;
                margin-left: 30px !important;
            }

            </c:if>
        </style>
        <!--begin::JavaScript i18n object-->
        <script>
            window.i18n = {
                confirmDelete: '<spring:message code="common.confirmDelete" javaScriptEscape="true"/>',
                yes: '<spring:message code="common.yes" javaScriptEscape="true"/>',
                no: '<spring:message code="common.no" javaScriptEscape="true"/>',
                cancel: '<spring:message code="common.cancel" javaScriptEscape="true"/>',
                success: '<spring:message code="common.success" javaScriptEscape="true"/>',
                error: '<spring:message code="common.error" javaScriptEscape="true"/>',
                loading: '<spring:message code="common.loading" javaScriptEscape="true"/>',
                saveError: '<spring:message code="common.save.error" javaScriptEscape="true"/>',
                unknownError: '<spring:message code="common.unknown.error" javaScriptEscape="true"/>',
                search: '<spring:message code="common.search" javaScriptEscape="true"/>'
            };
            Object.freeze(window.i18n);
        </script>
        <!--end::JavaScript i18n object-->
        <!--begin::CSRF meta tags (read by AJAX setup in footerScript.jsp)-->
        <meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
        <!--end::CSRF meta tags-->
</head>