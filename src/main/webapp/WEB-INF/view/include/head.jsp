<!--end::Head-->

<!DOCTYPE html>
<c:choose>
    <c:when test="${isRTL}">
        <html lang="en" direction="rtl" style="direction: rtl;">
    </c:when>
    <c:otherwise>
        <html lang="en">
    </c:otherwise>
</c:choose>

<!--begin::Head-->
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
    <meta http-equiv='Expires' content='-1'>
    <meta http-equiv='cache-control' content='no-cache'>
    <meta http-equiv='pragma' content='no-cache'>
    <%
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",-1);
    %>
    <base href="">
    <title>Beni Seuf Technological University</title>
    <meta name="description" content="Beni Seuf Technological University"/>
    <!--begin::Fonts-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>        <!--end::Fonts-->

    <c:choose>
    <c:when test="${isRTL}">
    <link href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/fullcalendar/fullcalendar.bundle.rtl.css" rel="stylesheet" type="text/css"/>
    <link href="${resources}/vendor/metronic_assets_7/assets/plugins/global/plugins.bundle.rtl.css" rel="stylesheet" type="text/css"/>
    <link href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/prismjs/prismjs.bundle.rtl.css" rel="stylesheet" type="text/css"/>
    <link href="${resources}/vendor/metronic_assets_7/assets/css/style.bundle.rtl.css" rel="stylesheet" type="text/css"/>
    </c:when>
    <c:otherwise>
        <link href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css"/>
        <link href="${resources}/vendor/metronic_assets_7/assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css"/>
        <link href="${resources}/vendor/metronic_assets_7/assets/plugins/custom/prismjs/prismjs.bundle.css" rel="stylesheet" type="text/css"/>
        <link href="${resources}/vendor/metronic_assets_7/assets/css/style.bundle.css" rel="stylesheet" type="text/css"/>
    </c:otherwise>
    </c:choose>




    <link rel="shortcut icon" href="${resources}/vendor/metronic_assets_7/assets/media/logos/favicon.ico"/>
</head>