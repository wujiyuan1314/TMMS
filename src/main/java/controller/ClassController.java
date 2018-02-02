package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entity.ClassInfo;
import entity.CodeLibrary;
import net.sf.json.JSONArray;
import service.ClassService;
import service.CodeLibraryService;
import util.DateUtil;
import util.FileUploadUtils;
import util.Function;
import util.Page;

@Controller
@RequestMapping("/class")
public class ClassController {
	public static Logger logger = Logger.getLogger(ClassController.class);
	
	@Autowired
	ClassService classService;
	@Autowired
	CodeLibraryService codeLibraryService;
	
	@RequiresPermissions({"class:view"})
	@RequestMapping(value="/classs")
	public String listClass(Model model, @ModelAttribute ClassInfo classInfo, @ModelAttribute Page page, HttpServletRequest request) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("grades",listgrade);
		List<ClassInfo> classs = classService.listClass(classInfo, page);//班级列表
		model.addAttribute("classs",classs);
		return "class/class_list";
	}
	/**
	 * 跳转班级信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"class:add"})
	@RequestMapping(value="/classadd",method=RequestMethod.GET)
	public String addClass(Model model){
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("grades",listgrade);
		model.addAttribute(new ClassInfo());
		return "class/class_add";
	}
   /**
    * 添加班级信息
    * @param classInfo
    * @param model
    * @param br
    * @return
    */
	@RequiresPermissions({"class:add"})
    @RequestMapping(value="/classadd",method=RequestMethod.POST)
	public String addClass(Model model,@Validated ClassInfo classInfo,BindingResult br){
    	List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("grades",listgrade);
    	if(br.hasErrors()){
    		return "class/class_add";
    	}
    	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
    	String classId=Function.getUUID();
    	classInfo.setClassId(classId);
    	classInfo.setCreateTime(createtime);
    	classInfo.setUpdateTime(createtime);
    	int isOk=classService.insertClass(classInfo);
    	return "redirect:classs";
	}
    /**
     * 跳转到批量添加班级页面
     * @return
     */
	@RequiresPermissions({"class:addbatch"})
    @RequestMapping(value="/classaddbatch",method=RequestMethod.GET)
    public String addBookBatch(){
    	return "class/class_addbatch";
    }
    /**
     * 批量添加班级信息
     * @param request
     * @return
     */
    @RequestMapping(value="/classaddbatch",method=RequestMethod.POST)
    public String addBookBatch(HttpServletRequest request){
		String filepath=FileUploadUtils.tranferFile(request, "/userfiles/xls");
    	File file=new File(filepath);
    	classService.uploadClassjxl(file);
    	return "class/class_list";
    }
    /**
	 * 跳转班级修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"class:edit"})
	@RequestMapping(value="/{id}/classedit",method=RequestMethod.GET)
	public String editClass(Model model,@PathVariable String id){
		ClassInfo classInfo=classService.getClassByID(id);
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("grades",listgrade);
		model.addAttribute(classInfo);
		return "class/class_edit";
	}
	/**
     * 导出班级信息
     * @param filepath
     * @return
     */
	@RequiresPermissions({"class:export"})
    @RequestMapping(value="/classexport",method=RequestMethod.POST)
    public String exportBooks(ClassInfo classInfo,HttpServletResponse response){
        List<ClassInfo> list=classService.selectByParams(classInfo);
        if(list.size()>0){
        	classService.exportClass(list, response);
        }
    	return "class/class_list";
    }
   /**
    * 修改班级信息
    * @param classInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"class:edit"})
    @RequestMapping(value="/classedit",method=RequestMethod.POST)
	public String editClass(Model model,@Validated ClassInfo classInfo,BindingResult br){
    	List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("grades",listgrade);
    	if(br.hasErrors()){
    		return "class/class_edit";
    	}
    	int isOk=classService.editClass(classInfo);
		return "redirect:classs";
	}
    /**
     * 删除班级信息
     * @param user
     * @param br
     * @return
     */
	@RequiresPermissions({"class:del"})
     @RequestMapping(value="/{id}/classdel")
 	public String delClass(@PathVariable String id){
    	 classService.deleteClass(id);
 		return "redirect:/class/classs";
 	}
     /**
      * 批量删除班级信息
      * @param ids
      * @return
      */
	@RequiresPermissions({"class:dels"})
	@RequestMapping(value="/dels")
  	public String dels(HttpServletRequest request){
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	classService.deleteClasss(idArray);;
  		return "redirect:/class/classs";
  	}
      /**
       * 按照specialtyid查找班级
       * @param request
       * @param response
       */
      @RequestMapping(value="/selectbyspecialtyid")
      public void selectBySpecialtyId(HttpServletRequest request,HttpServletResponse response){
       	try {
       		response.setCharacterEncoding("utf-8");
  			PrintWriter out=response.getWriter();
  			String specialtyId=request.getParameter("specialtyId");
  			List<ClassInfo> list=classService.selectBySpecialtyId(specialtyId);
  			JSONArray jsonArray=JSONArray.fromObject(list);
  			out.print(jsonArray);
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
       }
}
