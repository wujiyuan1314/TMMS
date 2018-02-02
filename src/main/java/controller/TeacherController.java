package controller;

import java.io.File;
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

import entity.CodeLibrary;
import entity.TeacherInfo;
import service.CodeLibraryService;
import service.CollegeService;
import service.TeacherService;
import util.DateUtil;
import util.FileUploadUtils;
import util.Function;
import util.Page;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	public static Logger logger = Logger.getLogger(TeacherController.class);
	
	@Autowired
	TeacherService teacherService;
	@Autowired
	CodeLibraryService codeLibraryService;
	@Autowired
	CollegeService collegeService;
	/**
	 * 教职工列表分页查询
	 * @param model
	 * @param bookInfo
	 * @param page
	 * @param request
	 * @return
	 */
	@RequiresPermissions({"teacher:view"})
	@RequestMapping(value="/teachers")
	public String listTeacher(Model model, @ModelAttribute TeacherInfo teacherInfo, @ModelAttribute Page page, HttpServletRequest request) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		
		List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		List<TeacherInfo> teachers = teacherService.listTeacher(teacherInfo, page);//教职工列表
		model.addAttribute("sexs",listsex);
		model.addAttribute("teachers",teachers);
		return "teacher/teacher_list";
	}
	/**
	 * 跳转教职工信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"teacher:add"})
	@RequestMapping(value="/teacheradd",method=RequestMethod.GET)
	public String addTeacher(Model model){
		List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		model.addAttribute("sexs",listsex);
		model.addAttribute(new TeacherInfo());
		return "teacher/teacher_add";
	}
   /**
    * 添加教职工信息
    * @param teacherInfo
    * @param model
    * @param br
    * @return
    */
	@RequiresPermissions({"teacher:add"})
    @RequestMapping(value="/teacheradd",method=RequestMethod.POST)
	public String addTeacher(Model model,@Validated TeacherInfo teacherInfo,BindingResult br){
    	List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		model.addAttribute("sexs",listsex);
    	if(br.hasErrors()){
    		return "teacher/teacher_add";
    	}
    	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
    	teacherInfo.setCreateTime(createtime);
    	teacherInfo.setUpdateTime(createtime);
    	int isOk=teacherService.insertTeacher(teacherInfo);
    	return "redirect:teachers";
	}
    /**
     * 跳转到批量添加教职工页面
     * @return
     */
	@RequiresPermissions({"teacher:addbatch"})
    @RequestMapping(value="/teacheraddbatch",method=RequestMethod.GET)
    public String addBookBatch(){
    	return "teacher/teacher_addbatch";
    }
    /**
     * 批量添加教职工信息
     * @param request
     * @return
     */
	@RequiresPermissions({"teacher:addbatch"})
    @RequestMapping(value="/teacheraddbatch",method=RequestMethod.POST)
    public String addBookBatch(HttpServletRequest request){
		String filepath=FileUploadUtils.tranferFile(request, "/userfiles/xls");
    	File file=new File(filepath);;
    	teacherService.uploadTeacherjxl(file);
    	return "teacher/teacher_list";
    }
    /**
	 * 跳转教职工修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"teacher:edit"})
	@RequestMapping(value="/{id}/teacheredit",method=RequestMethod.GET)
	public String editTeacher(Model model,@PathVariable int id){
		TeacherInfo teacherInfo=teacherService.getTeacherByID(id);
		List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		model.addAttribute("sexs",listsex);
		model.addAttribute(teacherInfo);
		return "teacher/teacher_edit";
	}
	/**
     * 导出教职工信息
     * @param filepath
     * @return
     */
	@RequiresPermissions({"teacher:export"})
    @RequestMapping(value="/teacherexport",method=RequestMethod.POST)
    public String exportBooks(TeacherInfo teacherInfo,HttpServletResponse response){
        List<TeacherInfo> list=teacherService.selectByParams(teacherInfo);
        if(list.size()>0){
        	teacherService.exportTeacher(list, response);
        }
    	return null;
    }
   /**
    * 修改教职工信息
    * @param teacherInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"teacher:edit"})
    @RequestMapping(value="/teacheredit",method=RequestMethod.POST)
	public String editTeacher(Model model,@Validated TeacherInfo teacherInfo,BindingResult br){
    	List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		model.addAttribute("sexs",listsex);
    	if(br.hasErrors()){
    		return "teacher/teacher_edit";
    	}
    	int isOk=teacherService.editTeacher(teacherInfo);
		return "redirect:teachers";
	}
    /**
     * 删除教职工信息
     * @param user
     * @param br
     * @return
     */
	@RequiresPermissions({"teacher:del"})
     @RequestMapping(value="/{id}/teacherdel")
 	public String delTeacher(@PathVariable int id){
    	 teacherService.deleteTeacher(id);
 		return "redirect:/teacher/teachers";
 	}
     /**
      * 批量删除教职工信息
      * @param ids
      * @return
      */
	@RequiresPermissions({"teacher:dels"})
	 @RequestMapping(value="/dels")
  	public String dels(HttpServletRequest request){
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	int[] idArray1=new int[idArray.length];
    	for(int i=0;i<idArray.length;i++){
    		idArray1[i]=Function.getInt(idArray[i], 0);
    	}
    	teacherService.deleteTeachers(idArray1);;
  		return "redirect:/book/books";
  	}
}
