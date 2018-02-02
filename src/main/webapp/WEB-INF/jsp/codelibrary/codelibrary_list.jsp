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
							<li class="active">参数${codeno}的类别信息</li>
						</ul>
						<!-- .breadcrumb -->
					</div>
					<!-- PAGE CONTENT BEGINS -->
					<!-- 要展示的开始 -->
					<div class="page-content">

								<sf:form action="codeCatalogs" method="post" id="Paramform"
									class="form-horizontal">
									<input type="hidden" name="currentPage" id="currentPage" value="1" />
									<div class=" col-xs-12 table-header">参数${codeno}的类别列表<a href="../codeCatalogs" style="float:right;color:#fff;">返回参数列表</a></div>
										<div class="col-sm-4">
											<div id="dynamic-table_filter" class="dataTables_filter">
												<a href="/tmms/codeLibrary/${codeno}/codeLibraryadd">
													<button type="button" class="btn btn-success btn-sm">
														<span class="icon-plus"></span>&nbsp;&nbsp;新增
													</button>
												</a>
											</div>
										</div>
									</div>
								</sf:form>
								<table id="dynamic-table"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>类别编号</th>
											<th>类别名</th>
											<th>类别值</th>
											<th>操作</th>
										</tr>
									</thead>

									<tbody>
										<c:forEach items="${codeLibrarys}" var="codeLibrary" varStatus="status">
											<tr>
												<td>${codeLibrary.itemno}</td>
												<td>${codeLibrary.itemname}</td>
												<td>${codeLibrary.extend1}</td>
												<td>
													<div class="hidden-sm hidden-xs action-buttons">
														<a class="blue" href="#"> <i
															class="ace-icon fa fa-search-plus bigger-130"></i>
														</a> <a class="green" href="/tmms/codeLibrary/${codeLibrary.id}/codeLibraryedit">修改<i
															class="icon-edit"></i>
														</a> <a class="red" href="/tmms/codeLibrary/${codeno }/${codeLibrary.id}/codeLibrarydel">删除<i
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

