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
							<li class="active">菜单信息</li>
						</ul>
						<!-- .breadcrumb -->
					</div>
					<!-- PAGE CONTENT BEGINS -->
					<!-- 要展示的开始 -->
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
									<div class=" col-xs-12 table-header">菜单列表</div>
										<div class="col-sm-3">
										    <div class="tree well">
										       <ul>
										         <c:forEach items="${menuitems}" var="menuitem" varStatus="status">
										          <li style="cursor:pointer;font-size:18px;list-style-type:none;">
										            <span><i class="icon-folder-open" style="color: rgba(255, 153, 51, 0.8);"></i></span> <a class="menuc" style="color:#666;text-decoration:none;" node="${menuitem.id}">${menuitem.menuName}</a>
										            <ul>
										              <menu:children parentId="${menuitem.id}">
										                <c:forEach items="${childrenmenus}" var="childrenmenu" varStatus="status">
										                  <li style="cursor:pointer;font-size:16px;list-style-type:none;">
										                    <span><i class="icon-folder-open" style="color: rgba(255, 153, 51, 0.8);"></i></span> <a class="menuc" style="color:#666;text-decoration:none;" node="${childrenmenu.id}">${childrenmenu.menuName}</a>
										                      <ul>
												              <menu:children parentId="${childrenmenu.id}">
												                <c:forEach items="${childrenmenus}" var="childrenmenu1" varStatus="status">
												                  <li style="cursor:pointer;font-size:16px;list-style-type:none;">
												                    <span><i class="icon-folder-open" style="color: rgba(255, 153, 51, 0.8);"></i></span> <a class="menuc" style="color:#666;text-decoration:none;" node="${childrenmenu1.id}">${childrenmenu1.menuName}</a>
												                  </li>
												                </c:forEach>
												              </menu:children>
												            </ul>
										                  </li>
										                </c:forEach>
										              </menu:children>
										            </ul>
										          </li>
										         </c:forEach>
										       </ul>
										    </div>
										</div>
										
										<div class="col-sm-9">
									        <div class="col-sm-4">
												<div id="dynamic-table_filter" class="dataTables_filter">
													<a href="menuitemadd">
														<button type="button" class="btn btn-success btn-sm">
															<span class="icon-plus"></span>&nbsp;&nbsp;新增
														</button>
													</a>
												</div>
											</div>
											
											<table id="dynamic-table"
												class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center"><input type="checkbox" id="all"
															onclick="selectAll('Id')" /></th>
														<th>序号</th>
														<th>菜单名</th>
														<th>上级菜单</th>
														<th>菜单地址</th>
														<th>菜单图标</th>
														<th>操作</th>
													</tr>
												</thead>
		
												<tbody class="content">
													<c:forEach items="${menuitems}" var="menuitem" varStatus="status">
														<tr>
															<td class="center"><input type="checkbox"
																name="ID" value="${menuitem.id}" /></td>
															<td>${status.index + 1}</td>
															<td>${menuitem.menuName}</td>
															<td>${menuitem.parentId}</td>
															<td>${menuitem.uri}</td>
															<td>${menuitem.icon}</td>
															<td>
																<div class="hidden-sm hidden-xs action-buttons">
																	<a class="blue" href="#"> <i
																		class="ace-icon fa fa-search-plus bigger-130"></i>
																	</a> <a class="green" href="${menuitem.id}/menuitemedit">修改 <i
																		class="icon-edit"></i>
																	</a> <a class="red" href="${menuitem.id}/menuitemdel">删除 <i
																		class="icon-trash"></i>
																	</a>
																</div>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
									    </div>
									</div>
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
	<%@ include file="../common/common-js.jsp"%>
	<script src="<%=basePath2 %>resources/js/common/common_list.js"></script>
    <script src="<%=basePath2 %>resources/js/common/tree.js"></script>
</body>
</html>

