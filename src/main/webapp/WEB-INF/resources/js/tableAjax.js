/*
 * tableAjax.js requires function.js
 */
var TableAjax = (function () {
  var _locale;

  var handleRecords = function (
    tableObject,
    ajaxUrl,
    locale,
    dom,
    excel,
    pageLength,
  ) {
    var columns = parseColumns(tableObject);
    var buttons = generateButtons(excel, locale);
    var pageLengthButtons = getPageLength(pageLength, excel);
    var initLength = getInitLength(pageLength, excel);
    var grid = new Datatable();
    var tableDom =
      "<'row'<'col-md-8 col-sm-12'l><'col-md-4 col-sm-12'<'table-group-actions pull-right'>f>r><'table-responsive't><'row'<'col-md-4 col-sm-12'i><'col-md-8 col-sm-12 cellRight'p>>"; // datatable layout
    if (dom) tableDom = dom;

    grid.init({
      src: tableObject,
      loadingMessage: " ",
      dataTable: {
        // here you can define a typical datatable settings from http://datatables.net/reference/option/
        deferRender: true,
        dom: tableDom,
        pageLength: initLength,
        lengthMenu: pageLengthButtons,
        language:
          _locale === "ar"
            ? {
                lengthMenu: "_MENU_",
                search: "",
                info: "_START_ - _END_ / _TOTAL_",
                infoEmpty: "",
                sInfoFiltered: "",
                emptyTable: "لا توجد بيانات",
                zeroRecords: "لم يتم العثور على نتائج",
                paginate: {
                  first: "الأول",
                  last: "الأخير",
                  next: "التالي",
                  previous: "السابق",
                },
              }
            : {
                lengthMenu: "_MENU_",
                search: "",
                info: "_START_ - _END_ / _TOTAL_",
                infoEmpty: "",
                sInfoFiltered: "",
                emptyTable: "-",
                zeroRecords: "",
              },
        pagingType: "bootstrap_full_number", // pagination type(bootstrap, bootstrap_full_number or bootstrap_extended)
        ajax: {
          url: ajaxUrl,
          contentType: "application/json; charset=utf-8",
          data: function (data) {
            return JSON.stringify(data);
          },
        },

        columns: columns,
        order: columns.order,
        buttons: buttons,
      },
    });
    $(".dataTables_length select").addClass(
      "form-control input-small marginRight30",
    ); // modify table per page dropdown
    $(".dataTables_length select").select2(); // initialize select2 dropdown
    if (!excel) {
      $(".table-responsive").css("overflow-x", "visible"); // for firefox
    }

    /*$(".dataTables_paginate").css("float", "right !important");*/

    /*$(tableObject).find('tbody').on('click', 'tr', function() {
         });*/
    var table = $(tableObject).DataTable();
    var tableId = $(tableObject).attr("id");
    var selector = "#" + tableId + "_filter";

    $(selector).find("input").attr("placeholder", window.i18n.search); //search for each table
    $(selector).find("input").off();
    $(selector)
      .find("input")
      .on("keyup", function (e) {
        if (!$(this).val() || e.which == 13) {
          table.search($(this).val()).draw();
        }
      });

    $("#" + tableId)
      .find(".form-filter")
      .on("keyup", function (e) {
        //search for each column
        if (!$(this).val() || e.which == 13) {
          var columnName = $(this).attr("data-column-name");
          var value = $(this).val();
          var column = table.column(columnName + ":name");
          if (column) column.search(value).draw();
        }
      });
  };

  // parsing custom column-* attributes based on html5 data-* attributes
  function parseColumns(tableObject) {
    var columns = [];
    columns.order = [];

    $(tableObject)
      .find("tbody > tr > td")
      .each(function (index) {
        /*
             in html.
             data-column-data="productId"

             in original datatables option.
             column: [
             {
             data: "productId",
             }
             ]
             */
        var column = {
          name: $(this).data("column-name"),
          data: $(this).data("column-data"),
          visible: $(this).data("column-visible"),
          searchable: $(this).data("column-searchable"),
          orderable: $(this).data("column-orderable"),
          width: $(this).data("column-width"),
          sClass: $(this).data("column-class") || "cellCenter",
          render: eval($(this).data("column-render")) || null,
        };
        columns.push(column);

        /*
             in html.
             data-column-order="desc"

             in original datatables option. 순서 주의. 현재 작성 순서대로 정렬 우선순위.
             order: [
             [1, "desc"]
             ]
             */
        if ($(this).data("column-order")) {
          columns.order.push([index, $(this).data("column-order")]);
        }
      });

    return columns;
  }

  function generateButtons(excel, locale) {
    if (excel) {
      var buttons = [];
      buttons.push({
        extend: "excelHtml5",
        text: '<i class="fa fa-file-excel-o"></i>',
        title: getExportTitle(),
        exportOptions: getExportOptions(),
      });
      /*if(locale == 'en') {
                buttons.push({
                    extend: 'pdfHtml5',
                    text: '<i class="fa fa-file-pdf-o"></i>',
                    title: getExportTitle(),
                    exportOptions: getExportOptions(),
                    customize: function(doc) {
                        doc.pageSize = "A4";
                        doc.pageOrientation = 'landscape';
                        doc.pageMargins = [ 40, 20, 20, 40 ];
                        //var columnLengths = doc.content[1].table.body[0].length;
                        doc.styles.tableHeader.fontSize = 8;
                        doc.styles.tableBodyEven.fontSize = 8;
                        doc.styles.tableBodyOdd.fontSize = 8;
                    }
                });
            }*/

      buttons.push({
        extend: "print",
        text: '<i class="fa fa-print"></i>',
        title: getExportTitle(),
        autoPrint: true,
        customize: function (window) {
          $(window.document.body).css("overflow-x", "scroll");
          $(window.document.body).children("table").css("font-size", "10px");
          $(window.document.body).find("td").css("padding", "0");
          $(window.document.body).find("td").css("text-align", "center");
        },
      });
      return buttons;
    }
  }

  function getInitLength(pageLength, excel) {
    if (pageLength) return pageLength[0];
    else {
      if (excel) {
        return 50;
      } else return 20;
    }
  }

  function getPageLength(pageLength, excel) {
    if (pageLength) return pageLength;
    else {
      if (excel) {
        return [20, 100, 500, 1000];
      } else return [20, 50, 100, 200];
    }
  }

  function getExportTitle() {
    return "manuscript-export-";
  }

  function getExportOptions() {
    return {
      modifier: {
        page: "all",
      },
      selected: true,
      columns: ":visible",
      trim: true,
    };
  }

  // 어떤 함수든 global로 작성한 후 그냥 data-column-render="renderCustom" 이렇게 실행 할 수 있음. 위의 renderXXX는 predefined라고 보면 됨.

  return {
    init: function (tableObject, ajaxUrl, locale, dom, excel, pageLength) {
      _locale = locale;

      handleRecords(tableObject, ajaxUrl, locale, dom, excel, pageLength);
    },
  };
})();
