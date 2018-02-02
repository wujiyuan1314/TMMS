/**
 * 根据ID删除一条信息
 */
function deleteOneConfirm(){
	//alert(document.getElementById("id").value);
	
	var r = confirm("本指令的所有内容也将被删除，确定要删除吗？");
	if(r == true) {	
		return true;
	}
	
	return false;
	
}

/**
 * 批量删除信息
 */
function batchDelete(obj) {
	var r = confirm("确定要删除吗？");
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
		var url=basePath+obj+"/dels";
		var params={ids:ids};
		$.post(url,params,function(res){
			window.location.reload();
		});
	}
}

/**
 * 实现全选功能
 */
function selectAll(obj) {
	var selected = document.getElementById("all");
	
	var IDs = document.getElementsByName(obj);
	if(selected.checked == true) {
		for(var i = 0; i < IDs.length; i++){  
			if(IDs[i].checked == false){  
				IDs[i].checked = true;  
		    }  
		}  
	}
	else{
		for(var i = 0; i < IDs.length; i++){  
			if(IDs[i].checked == true){  
				IDs[i].checked = false;
			}
		}
	}
}


/**
 * 修改当前页码，调用后台重新查询
 */
function changeCurrentPage(currentPage){
	
	document.getElementById("currentPage").value = currentPage;
	//alert(document.getElementById("currentPage").value);
	//alert(currentPage);
	document.forms[0].submit();
}

/**
 * 页面跳转
 */
function changeCurrentPage2(){
	
	document.getElementById("currentPage").value = document.getElementById("currentPageText").value;
	//alert(document.getElementById("currentPage").value);
	//alert(document.getElementById("currentPageText").value);
	document.forms[0].submit();
	//document.getElementById("collegeForm").submit();
}

/**
 * js实现reset功能，添加指令页面
 */

function reset(){
	document.getElementById(collegeForm).reset();
}

/**
 * 提交表单
 * 
 */

function doSubmit(){
	document.getElementById("addForm").submit();
}
/**
 * list页面根据id的值判断查询,导入还是导出
 * 
 */
$("input[type=button]").click(function(){
	var type=$(this).attr("id");
	if(type.indexOf("addbatch")==-1){
		$("#Paramform").attr("action",type).submit();
	}else{
		window.location.href=type;
	}
})