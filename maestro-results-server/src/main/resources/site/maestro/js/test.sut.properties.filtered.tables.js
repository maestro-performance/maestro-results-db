$(document).ready(function() {
    $('[data-datatables]').DataTable({
        columns: dbColumns,
        ajax: {
            url: $('[data-datatables]').attr('data-api'),
            dataSrc:  ''
        },
        dom: "t",
        order: [[ 1, 'asc' ]],
        pfConfig: {
            emptyStateSelector: "#emptyState1",
            filterCaseInsensitive: true,
            filterCols: [
              null,
              null,
              {
                default: true,
                optionSelector: "#filter1",
                placeholder: "Filter By Product Name..."
              }, {
                optionSelector: "#filter2",
                placeholder: "Filter By Product Version..."
              }, {
                optionSelector: "#filter3",
                placeholder: "Filter By Test Name..."
              }, {
                optionSelector: "#filter4",
                placeholder: "Filter By Test Result..."
              }, {
                 optionSelector: "#filter5",
                 placeholder: "Filter By API Name..."
              }, {
                optionSelector: "#filter6",
                placeholder: "Filter By API Version..."
              }, {
                optionSelector: "#filter7",
                placeholder: "Filter By Protocol..."
              }, {
                optionSelector: "#filter8",
                placeholder: "Filter By Connection Count..."
              },
              null,
              {
                 optionSelector: "#filter9",
                 placeholder: "Filter By Durability..."
              },
              {
                optionSelector: "#filter10",
                placeholder: "Filter By Limit Destinations..."
              }, {
                 optionSelector: "#filter11",
                 placeholder: "Filter By Message Size..."
              },
              null,
              null,
              {
                optionSelector: "#filter12",
                placeholder: "Filter By SUT Tags..."
              }, {
                optionSelector: "#filter13",
                placeholder: "Filter By Test Tags..."
              }
            ],
            paginationSelector: "#pagination1",
            toolbarSelector: "#toolbar1",
            //selectAllSelector: 'th:first-child input[type="checkbox"]',
            colvisMenuSelector: '.table-view-pf-colvis-menu'
          },
    });

    var findTableViewUtil = function (config) {
      // Upon clicking the find button, show the find dropdown content
      $(".btn-find").click(function () {
        $(this).parent().find(".find-pf-dropdown-container").toggle();
      });

      // Upon clicking the find close button, hide the find dropdown content
      $(".btn-find-close").click(function () {
        $(".find-pf-dropdown-container").hide();
      });
    };

    new findTableViewUtil();
});