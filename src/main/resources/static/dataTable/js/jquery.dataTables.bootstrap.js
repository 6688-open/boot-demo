//http://datatables.net/plug-ins/pagination#bootstrap
$.extend( true, $.fn.dataTable.defaults, {
	"sDom": "<fr>t<'row'<'col-sm-2'l><'col-sm-4'i><'col-sm-6'p>>",
	"sPaginationType": "bootstrap",
	"oLanguage": {
		//"sProcessing" : "正在加载数据...",
		"sLengthMenu" : " _MENU_ 条/页",
		"sZeroRecords" : "没有符合条件的数据...",
		"sEmptyTable" : "没有符合条件的数据...",
		"sInfo" : "显示 _START_ 至 _END_ 共 _TOTAL_ 条",
		"sInfoEmpty" : "显示 0 至 0 共 0 条",
		"sInfoFiltered" : "(_MAX_)"
	},
	"bPaginate" : true, //是否分页
	"bFilter" : false,//是否启用客户端过滤功能
	//"bProcessing" : true, //当datatable获取数据时候是否显示正在处理提示信息。 
	"bLengthChange" : true,//改变每e页显示数据数量
	"iDisplayLength" : 10,
	"bServerSide" : true,
	 "sScrollY": 400,
	"bSort" : true //是否使用排序 
} );


/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
{
    return {
        "iStart":         oSettings._iDisplayStart,
        "iEnd":           oSettings.fnDisplayEnd(),
        "iLength":        oSettings._iDisplayLength,
        "iTotal":         oSettings.fnRecordsTotal(),
        "iFilteredTotal": oSettings.fnRecordsDisplay(),
        "iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
        "iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
    };
}
 
/* Bootstrap style pagination control */
$.extend( $.fn.dataTableExt.oPagination, {
    "bootstrap": {
        "fnInit": function( oSettings, nPaging, fnDraw ) {
            var oLang = oSettings.oLanguage.oPaginate;
            var fnClickHandler = function ( e ) {
                e.preventDefault();
                if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
                    fnDraw( oSettings );
                }
            };
 
            $(nPaging).append(
                '<ul class="pagination">'+
                '<li class="first disabled"><a href="#">首页</a></li>'+
                    '<li class="prev disabled"><a href="#">上一页</a></li>'+
                    '<li class="next disabled"><a href="#">下一页</a></li>'+
                    '<div id="lastPage" hidden="true"><li class="last disabled"><a href="#">尾页</a></li></div>'+   //去掉尾页
                '</ul>'
            );
            var els = $('a', nPaging);
            $(els[0]).bind( 'click.DT', { action: "first" }, fnClickHandler );
            $(els[1]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
            $(els[2]).bind( 'click.DT', { action: "next" }, fnClickHandler );
            $(els[3]).bind( 'click.DT', { action: "last" }, fnClickHandler );     //去掉尾页
        },
 
        "fnUpdate": function ( oSettings, fnDraw ) {
            var iListLength = 5;
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var an = oSettings.aanFeatures.p;
            var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);
 
            if ( oPaging.iTotalPages < iListLength) {
                iStart = 1;
                iEnd = oPaging.iTotalPages;
            }
            else if ( oPaging.iPage <= iHalf ) {
                iStart = 1;
                iEnd = iListLength;
            } else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
                iStart = oPaging.iTotalPages - iListLength + 1;
                iEnd = oPaging.iTotalPages;
            } else {
                iStart = oPaging.iPage - iHalf + 1;
                iEnd = iStart + iListLength - 1;
            }
 
            for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
                // Remove the middle elements
                $('li:gt(1)', an[i]).filter(':not(:eq(-2))').filter(':not(:eq(-1))').remove();
                // Add the new list items and their event handlers
            	for ( j=iStart ; j<=iEnd ; j++ ) {
                    sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
                    $('<li '+sClass+'><a href="#">'+j+'</a></li>')
                        .insertBefore( $('li:eq(-2)', an[i])[0] )
                        .bind('click', function (e) {
                            e.preventDefault();
                            oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
                            fnDraw( oSettings );
                        } );
                }
 
                // Add / remove disabled classes from the static elements
                if ( oPaging.iPage === 0 ) {
                    $('li:first', an[i]).addClass('disabled');
                    $('li:eq(1)', an[i]).addClass('disabled');
                } else {
                    $('li:first', an[i]).removeClass('disabled');
                    $('li:eq(1)', an[i]).removeClass('disabled');
                }
 
                if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                    $('li:last', an[i]).addClass('disabled');                //去掉尾页
                    $('li:eq(-2)', an[i]).addClass('disabled');
                } else {
                    $('li:last', an[i]).removeClass('disabled');             //去掉尾页
                    $('li:eq(-2)', an[i]).removeClass('disabled');
                }
            }
        }
    }
} );