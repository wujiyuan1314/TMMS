<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
	//System.out.println(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="code" uri="/code-tags"%>
<%@ taglib prefix="location" uri="/location-tags"%>
<%@ taglib prefix="menu" uri="/menu-tags"%>
<!-- 公共css文件 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<!-- basic styles -->
<link href="<%=basePath1 %>resources/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=basePath1 %>resources/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="<%=basePath1 %>resources/css/font-awesome-ie7.min.css" />
<![endif]-->
<!-- page specific plugin styles -->
<!-- ace styles -->
<link rel="stylesheet" href="<%=basePath1 %>resources/css/ace.min.css" />
<link rel="stylesheet" href="<%=basePath1 %>resources/css/ace-rtl.min.css" />
<link rel="stylesheet" href="<%=basePath1 %>resources/css/ace-skins.min.css" />
<link rel="stylesheet" href="<%=basePath1 %>resources/css/style.css" />
<!--[if lte IE 8]>
  <link rel="stylesheet" href="<%=basePath1 %>resources/css/ace-ie.min.css" />
<![endif]-->
<!-- inline styles related to this page -->
<!-- ace settings handler -->
<script src="<%=basePath1 %>resources/js/ace-extra.min.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="<%=basePath1 %>resources/js/html5shiv.js"></script>
<script src="<%=basePath1 %>resources/js/respond.min.js"></script>
<![endif]-->