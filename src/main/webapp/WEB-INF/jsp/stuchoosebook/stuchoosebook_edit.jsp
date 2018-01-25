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
							<li class="active">教材书目</li>
						</ul>
						<!-- .breadcrumb -->
					</div>
					<!-- PAGE CONTENT BEGINS -->
					<!-- 要展示的开始 -->
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">

								<sf:form action="/tmms/book/bookedit" method="post" id="Paramform"
									class="form-horizontal" commandName="bookInfo">
									<div class=" col-xs-12 table-header">书籍修改<a href="/tmms/book/books" style="float:right;color:#fff;">返回书籍列表</a></div>
                                    <table id="dynamic-table">
                                    <sf:hidden path="id"/>
								      <tr>
								        <td>书名:</td>
								        <td><sf:input path="bookName"/><sf:errors path="bookName" cssClass="errors" style="color:red;"></sf:errors> </td>
								      </tr>
								      <tr>
								        <td>类型:</td>
								        <td>
								            <sf:select path="bookKind" items="${codelist}" itemLabel="itemname" itemValue="itemno">
								            </sf:select>
								        </td>
								      </tr>
								      <tr>
								        <td>作者:</td>
								        <td><sf:input path="bookAuthor"/><sf:errors path="bookAuthor" cssClass="errors" style="color:red;"></sf:errors> </td>
								      </tr>
								      <tr>
								        <td>ISBN:</td>
								        <td><sf:input path="bookIsbn"/><sf:errors path="bookIsbn" cssClass="errors" style="color:red;"></sf:errors> </td>
								      </tr>
								      <tr>
								        <td>出版社:</td>
								        <td><sf:input path="bookPublish"/><sf:errors path="bookPublish" cssClass="errors" style="color:red;"></sf:errors> </td>
								      </tr>
								      <tr>
								        <td>出版时间:</td>
								        <td><sf:input path="bookPublishTime" class="ECalendar" id="ECalendar_date" readonly="true"/><sf:errors path="bookPublishTime" cssClass="errors" style="color:red;"></sf:errors> </td>
								      </tr>
								      <tr>
								        <td>价格:</td>
								        <td><sf:input path="bookPrice"/><sf:errors path="bookPrice" cssClass="errors" style="color:red;"></sf:errors> </td>
								      </tr>
								      <tr>
								        <td>简介:</td>
								        <td><sf:textarea path="bookIntro"/></td>
								      </tr>
								      <tr>
								        <td></td>
								        <td><input type="submit" value="提交"></td>
								      </tr>
									</table>
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
<script type="text/javascript">
$(function(){
	$("#ECalendar_date").ECalendar({
		 type:"time",   //模式，time: 带时间选择; date: 不带时间选择;
		 stamp : false,   //是否转成时间戳，默认true;
		 offset:[0,0],   //弹框手动偏移量;
		 format:"yyyy-mm-dd",   //时间格式 默认 yyyy-mm-dd hh:ii;
		 skin:2,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
		 step:10,   //选择时间分钟的精确度;
		 callback:function(v,e){} //回调函数
	});
})
</script>
</body>
</html>

