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

<c:if test="${isRTL}">
    <!--begin::DataTables Arabic Language-->
    <script>
        $.extend(true, $.fn.dataTable.defaults, {
            "language": {
                "emptyTable": "لا توجد بيانات متاحة",
                "info": "عرض _START_ إلى _END_ من أصل _TOTAL_ سجل",
                "infoEmpty": "عرض 0 إلى 0 من أصل 0 سجل",
                "infoFiltered": "(تمت التصفية من إجمالي _MAX_ سجلات)",
                "lengthMenu": "عرض _MENU_ سجلات",
                "loadingRecords": "جاري التحميل...",
                "processing": "جاري المعالجة...",
                "search": "بحث:",
                "zeroRecords": "لم يتم العثور على نتائج مطابقة",
                "paginate": {
                    "first": "الأول",
                    "last": "الأخير",
                    "next": "التالي",
                    "previous": "السابق"
                }
            }
        });
    </script>
    <!--end::DataTables Arabic Language-->

    <!--begin::DatePicker Arabic Locale-->
    <script>
        if ($.fn.datepicker && $.fn.datepicker.dates) {
            $.fn.datepicker.dates['ar'] = {
                days: ["الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"],
                daysShort: ["أحد", "اثنين", "ثلاثاء", "أربعاء", "خميس", "جمعة", "سبت"],
                daysMin: ["ح", "ن", "ث", "ر", "خ", "ج", "س"],
                months: ["يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"],
                monthsShort: ["يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"],
                today: "اليوم",
                clear: "مسح",
                rtl: true
            };
            $.fn.datepicker.defaults.language = 'ar';
        }
    </script>
    <!--end::DatePicker Arabic Locale-->
</c:if>

<!--begin::Page Scripts(used by this page)-->
<script src="${resources}/vendor/metronic_assets_7/assets/js/pages/widgets.js"></script>
<!--end::Page Scripts-->
<script src="${resources}/vendor/bootstrap3-editable/js/bootstrap-editable.js" type="text/javascript"></script>
<script src="${resources}/js/CustomMap.js" type="text/javascript"></script>
<script>

    function menuGoto(u) {
        location.href = u;
    }

    function logout() {
        $.post("${baseUrl}/logout", function () {
            location.href = "${baseUrl}";
        });
    }
    $(".country-select").click(function (e) {
        e.preventDefault();
        var lang = $(this).attr("data-lang");
        $.get("${baseUrl}?lang=" + lang, function () {
            location.reload();
        });
    });
    var y = document.getElementById("current-year");
    if (y) y.innerHTML = String(new Date().getFullYear());

    function openPage(url) {
        var newPage = window.open(url);
    }


    function printContent(el) {
        var restorePage = $('body').html();
        $(el).css("padding", "100px");
        var pContent = $(el).clone();
        $('body').empty().html(pContent);
        window.print();
        $('body').html(restorePage);
    }

    function setCheckboxAll(checked, checkboxes) {
        var i;
        for (i = 0; i < checkboxes.length; i++) {
            if (checked) {
                $(checkboxes[i]).prop("checked", true);
            } else {
                $(checkboxes[i]).prop("checked", false);
            }
        }
    }

    function parameterize(name, map) {

        var checked = $("input[name=" + name + "]:checked");
        var i;

        var parameter = name + "=";
        for (i = 0; i < checked.length; i++) {
            parameter += $(checked[i]).val();
            if (i < checked.length - 1)
                parameter += ",";
        }
        console.log(parameter);
        return parameter;
    }

    // CSRF: attach token to every AJAX request via header
    (function () {
        var token = $('meta[name="_csrf"]').attr('content');
        var header = $('meta[name="_csrf_header"]').attr('content');
        if (token && header) {
            $(document).ajaxSend(function (e, xhr) {
                xhr.setRequestHeader(header, token);
            });
        }
    })();

</script>
<!--end::Body-->