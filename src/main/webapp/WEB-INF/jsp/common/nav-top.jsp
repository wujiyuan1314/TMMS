<!-- 顶部导航栏 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="entity.TmmsUser" %>
	<%TmmsUser user =(TmmsUser)session.getAttribute("tmmsUser"); 
	  int m=0;
	  if(user!=null){
		  m=user.getId();
	  }
	%>
<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-leaf"></i>
							<b>教材管理系统</b>
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->
				

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li><a>
								<i class="icon-user"></i>	欢迎您：<%=user.getUsername() %>
							</a>
						</li>
						
						<li>
							<a href="<%=request.getContextPath() %>/tmmsuser/<%=m%>/tmmsuserinfo">
								<i class="icon-user"></i>个人资料
							</a>
						</li>

						<li>
							<a href="<%=request.getContextPath() %>/tmmsuser/<%=m%>/editpwd">
								<i class="icon-lock"></i>修改密码
							</a>
						</li>

						<li>
							<a href="<%=request.getContextPath() %>/logout">
								<i class="icon-power-off"></i>退出系统
							</a>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
			<div class="ace-settings-container" id="ace-settings-container">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="icon-cog"></i>
					</div>

					<div class="ace-settings-box" id="ace-settings-box">
						<div>
							<div class="pull-left">
								<select id="skin-colorpicker" class="hide">
									<option data-skin="default" value="#438EB9">#438EB9</option>
									<option data-skin="skin-1" value="#222A2D">#222A2D</option>
									<option data-skin="skin-2" value="#C6487E">#C6487E</option>
									<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
								</select>
							</div>
							<span>&nbsp; 选择皮肤</span>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
							<label class="lbl" for="ace-settings-navbar"> 固定导航条</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
							<label class="lbl" for="ace-settings-sidebar"> 固定滑动条</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
							<label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
							<label class="lbl" for="ace-settings-rtl">切换到左边</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
							<label class="lbl" for="ace-settings-add-container">
								切换窄屏
								<b></b>
							</label>
						</div>
					</div>
				</div><!-- /#ace-settings-container -->
		</div>