//Navbar active highlighting
$(document).ready(function () {
    $('ul.nav > li a').click(function (e) {
        $('ul.nav > li').removeClass('active');
        $(this).closest('li').addClass('active');
    });
});

//Scrolling active Navbar highlighting
$(document).ready(function(){
    // $sections includes all of the container divs that relate to menu items.
    var $sections = $('.content');

    // The user scrolls
    $(window).scroll(function(){
        // We check the position of each of the divs compared to the windows scroll position
        $sections.each(function(){

            var docViewTop = $(window).scrollTop();
            var docViewBottom = docViewTop + $(window).height();

            var currentSection = $(this);
            var id = currentSection.attr('id');

            var elem = $('#'+id);
            var elemTop = $(elem).offset().top;
            var elemBottom = elemTop + $(elem).height();
            if ((elemBottom <= docViewBottom) && (elemTop >= docViewTop)) {
                var activeId = $(".nav").find("li.active a").attr("href");
                if("#"+id != activeId){
                    $(".nav").find(".active").removeClass("active");
                    $(".nav").find("[href=\\#"+id+"]").parent().addClass('active');
                }
            }
        })

    });
});
// Builds the HTML Table out of List.
function buildHtmlTable(selector, aList) {
    var columns = addAllColumnHeaders(aList, selector);

    for (var i = 0 ; i < aList.length ; i++) {
        var row$ = $('<tr/>');
        for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
            var cellValue = aList[i][columns[colIndex]];

            if (cellValue == null) { cellValue = ""; }

            row$.append($('<td/>').html(cellValue));
        }
        $(selector).append(row$);
    }
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records
function addAllColumnHeaders(aList, selector)
{
    var columnSet = [];
    var headerTr$ = $('<tr/>');

    for (var i = 0 ; i < aList.length ; i++) {
        var rowHash = aList[i];
        for (var key in rowHash) {
            if ($.inArray(key, columnSet) == -1){
                columnSet.push(key);
                headerTr$.append($('<th/>').html(key));
            }
        }
    }
    $(selector).append(headerTr$);
    return columnSet;
}