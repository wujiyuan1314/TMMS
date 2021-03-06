<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="util.*" %>
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
							<li class="active">教师信息</li>
						</ul>
						<!-- .breadcrumb -->
					</div>
					<!-- PAGE CONTENT BEGINS -->
					<!-- 要展示的开始 -->
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">

								<sf:form action="teachers" method="post" id="Paramform"
									class="form-horizontal">
									<input type="hidden" name="currentPage" id="currentPage" value="1" />
									<div class=" col-xs-12 table-header">教师列表</div>
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
										<label class="col-sm-1 contorl-label text-right">教职工号</label>
										<div class="col-sm-2">
											<input type="text" class="col-xs-12"
												name="teacherNo" value="${teacherInfo.teacherNo }">
										</div>

										<label class="col-sm-1 text-right">姓名</label>
										<div class="col-sm-2">
											<input type="text" class="col-xs-12" name="teacherName"
												value="${teacherInfo.teacherName }">
										</div>
										
										<label class="col-sm-1 text-right">性别</label>
										<div class="col-sm-2">
										  <select class="col-xs-12" name="teacherSex">
										    <option value=" ">全部</option>
											<c:forEach items="${sexs}" var="sex" varStatus="status">
											  <option value="${sex.itemno}">${sex.itemname}</option>
											</c:forEach>
										  </select>
										</div>
										
										<label class="col-sm-1 text-right">手机号</label> 
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="mobile" value="${teacherInfo.mobile }">
										</div> 
										
										<label class="col-sm-1 text-right">学院</label> 
										<div class="col-sm-2">
											 <select name="collegeId" id="collegeId">
								            <select>
										</div>
										
										<div class="col-xs-12">
											<div class="col-sm-1">
												<input type="button" id="teachers" value="查 询" />
											</div>
											<shiro:hasPermission name="teacher:addbatch">
											<div class="col-sm-1">
												<input type="button" id="teacheraddbatch" value="导入" />
											</div>
											</shiro:hasPermission>
											<shiro:hasPermission name="teacher:export">
											<div class="col-sm-1">
												<input type="button" id="teacherexport" value="导出" />
											</div>
											</shiro:hasPermission>
										</div>
										
										<div class="col-sm-4">
											<div id="dynamic-table_filter" class="dataTables_filter">
											    <shiro:hasPermission name="teacher:add">
												<a href="teacheradd">
													<button type="button" class="btn btn-success btn-sm">
														<span class="icon-plus"></span>&nbsp;&nbsp;新增
													</button>
												</a>&nbsp;&nbsp;
												</shiro:hasPermission>
												<shiro:hasPermission name="teacher:dels">
												 <button class="btn btn-danger btn-sm" onclick="batchDelete('teacher');">
													<span class="icon-trash"></span>&nbsp;&nbsp;删除
												</button>
												</shiro:hasPermission>
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
													<th>教职工号</th>
													<th>姓名</th>
													<th>性别</th>
													<th>手机号</th>
													<th>学院</th>
													<th>职称</th>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${teachers}" var="teacher" varStatus="status">
													<tr>
														<td class="center"><input type="checkbox"
															name="Id" value="${teacher.id}" /></td>
														<td>${status.index + 1}</td>
														<td>${teacher.teacherNo}</td>
														<td>${teacher.teacherName}</td>
														<td><code:itemname codeno="SEX" itemno="${teacher.teacherSex}"></code:itemname></td>
														<td>${teacher.mobile}</td>
														<td><location:collegeName collegeId="${teacher.collegeId}" ></location:collegeName></td>
														<td>${teacher.technicalTitle}</td>
														<td>
															<div class="hidden-sm hidden-xs action-buttons">
																<a class="blue" href="#"> <i
																	class="ace-icon fa fa-search-plus bigger-130"></i>
																</a> 
																<shiro:hasPermission name="teacher:edit">
																<a class="green" href="${teacher.id}/teacheredit">修改 <i
																	class="icon-edit"></i>
																</a>
																</shiro:hasPermission>
																<shiro:hasPermission name="teacher:del">
																 <a class="red" href="${teacher.id}/teacherdel">删除 <i
																	class="icon-trash"></i>
																</a>
																</shiro:hasPermission>
															</div>
														</td>
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
	<script src="<%=basePath2 %>resources/js/common/common_list.js"></script>
</body>
</html>

