<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta charset="utf-8" />
<title>教材管理系统后台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- basic styles -->
<%@ include file="../common/common-css.jsp"%>
</head>

<body>
	<%@ include file="../common/nav-top.jsp"%>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container" id="main-container">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>

			<%@ include file="../common/nav-left.jsp"%>

			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try {
								ace.settings.check('breadcrumbs', 'fixed')
							} catch (e) {
							}
						</script>

						<ul class="breadcrumb">
							<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li>
							<li class="active">教材书目</li>
						</ul>
						<!-- .breadcrumb -->
					</div>
					<!-- PAGE CONTENT BEGINS -->
					<!-- 要展示的开始 -->
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">

								<sf:form action="stuchoosebookadd" method="get" id="Paramform"
									class="form-horizontal">
									<input type="hidden" name="currentPage" id="currentPage" value="1" />
									<div class=" col-xs-12 table-header">书籍列表</div>
									<div class="form-group col-sm-2">
										<label> 每页条数 <select name="pageNumber">
												<option value="10"
													<c:if test="${page.pageNumber == 10}"> selected="selected"</c:if>>10</option>
												<option value="25"
													<c:if test="${page.pageNumber == 25}"> selected="selected"</c:if>>25</option>
												<option value="50"
													<c:if test="${page.pageNumber == 50}"> selected="selected"</c:if>>50</option>
												<option value="100"
													<c:if test="${page.pageNumber == 100}"> selected="selected"</c:if>>100</option>
										</select>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-1 contorl-label text-right">ISBN编号</label>
										<div class="col-sm-2">
											<input type="text" class="col-xs-12" id="isbn"
												name="bookIsbn" value="${bookInfo.bookIsbn }">
										</div>

										<label class="col-sm-1 text-right">书籍名称</label>
										<div class="col-sm-2">
											<input type="text" class="col-xs-12" name="bookName"
												value="${bookInfo.bookName }">
										</div>

										<div class="col-sm-4">
											<div id="dynamic-table_filter" class="dataTables_filter">
												<button class="btn btn-success btn-sm" onclick="batchChoose('stuchoosebook');">
													<span class="icon-plus"></span>&nbsp;&nbsp;选择
												</button>
												
											</div>
										</div>
										
										<div class="col-xs-12">
											<label class="col-sm-1 contorl-label text-right">出版社</label> 
											<div class="col-sm-2">
												<input type="text" class="form-control input-sm" name="bookPublish" value="${bookInfo.bookPublish }">
											</div> 
											<label class="col-sm-1 text-right">作者</label> 
											<div class="col-sm-2">
												<input type="text" class="form-control input-sm" name="bookAuthor"
													value="${bookInfo.bookAuthor }">
											</div>
											
											<div class="col-sm-1">
												<input type="button" id="stuchoosebookadd" value="查 询" />
											</div>
										</div>
									</div>

									<table id="dynamic-table"
											class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th class="center"><input type="checkbox" id="all"
														onclick="selectAll('Id')" /></th>
													<th>序号</th>
													<th>ISBN号</th>
													<th>书名</th>
													<th>出版社</th>
													<th>作者</th>
													<th>价格</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${books}" var="book" varStatus="status">
													<tr>
														<td class="center"><input type="checkbox"
															name="Id" value="${book.id}" /></td>
														<td>${status.index + 1}</td>
														<td>${book.bookIsbn}</td>
														<td>${book.bookName}</td>
														<td>${book.bookPublish}</td>
														<td>${book.bookAuthor}</td>
														<td>${book.bookPrice}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>

										<!--begin分页工具栏-->
										<div class="row">
											<div class="col-xs-4">
												<div class="dataTables_info" id="dynamic-table_info">共
													${page.totalNumber} 条记录</div>
											</div>
											<div class="col-xs-6">
												<div class="dataTables_paginate paging_simple_numbers"
													id="dynamic-table_paginate">
													<ul class="pagination">
														<li class="paginate_button "><a
															href="javascript:changeCurrentPage('1')">首页</a></li>
														<li class="paginate_button "><a
															href="javascript:changeCurrentPage('${page.currentPage -1}')">上一页</a></li>
														<li class="paginate_button "><a href="#">当前第<span>${page.currentPage}/${page.totalPage}</span>页
														</a></li>
														<li class="paginate_button "><a
															href="javascript:changeCurrentPage('${page.currentPage+1}')">下一页</a></li>
														<li class="paginate_button "><a
															href="javascript:changeCurrentPage('${page.totalPage}')">尾页</a></li>
													</ul>
												</div>
											</div>

											<div class="col-xs-2"
												style="line-height: 1.5; padding-top: 4px;">
												跳至第&nbsp; <input id="currentPageText" type='text'
													value='${page.currentPage}'
													style="width: 40px; height: 26px;" />&nbsp;页&nbsp; <a
													href="javascript:changeCurrentPage2()">GO</a>
											</div>
										</div>

								</sf:form>
							</div>
						</div>

					</div>
					<!-- 展示内容结束 -->
					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.main-content -->

			</div>
		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<%@ include file="../common/common-js.jsp"%>
</body>
</html>
<script type="text/javascript">
function batchChoose(obj) {
	var r = confirm("确定要选择吗？");
	if(r == true) {
		var ids="";
		$("input[name='Id']:checked").each(function() {
              if(ids==''){
            	  ids+=$(this).val();  
              }else{
            	  ids+=","+$(this).val(); 
              }
	    });
		if(ids==""){
			alert("请至少选择一条数据");
			return;
		}
		var url=basePath+obj+"/add";
		var params={ids:ids};
		$.post(url,params,function(res){
			var data=eval("("+res+")");
			var success=data.success;
			var msg=data.msg;
			if(success==0){
				alert(msg);
			}else if(success==1){
				alert(msg);
			}
		});
	}
}
</script>
