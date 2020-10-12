
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

<!--begin::Page Vendors(used by this page)-->
<script src="${resources}/vendor/metronic_assets_7/assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
<!--begin::Page Vendors(used by this page)-->
<script src="${resources}/vendor/metronic_assets_7/assets/plugins/custom/datatables/datatables.bundle.js"></script>
<!--end::Page Vendors-->
<!--end::Page Vendors-->

<!--begin::Page Scripts(used by this page)-->
<script src="${resources}/vendor/metronic_assets_7/assets/js/pages/widgets.js"></script>
<!--end::Page Scripts-->
<script src="${resources}/vendor/bootstrap3-editable/js/bootstrap-editable.js" type="text/javascript"></script>
<script>

    var y = document.getElementById("current-year");
    if(y) y.innerHTML = String(new Date().getFullYear());

    function changeMajor(divisionSelector, majorSelector, enabled) {
        var divisionId = $(divisionSelector).children("option:selected").val();
        $.get("${baseUrl}/majorList?enabled=" + enabled + "&divisionId=" + divisionId, function(html) {
            $(majorSelector).html(html);
        });
        $(divisionSelector).change(function() {
            divisionId = $(divisionSelector).children("option:selected").val();
            $.get("${baseUrl}/majorList?enabled=" + enabled + "&divisionId=" + divisionId, function(html) {
                $(majorSelector).html(html);
            });
        });
    }
</script>
<!--end::Body-->