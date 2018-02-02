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

import entity.CourseInfo;
import entity.CodeLibrary;
import service.CourseService;
import service.CodeLibraryService;
import util.DateUtil;
import util.FileUploadUtils;
import util.Function;
import util.Page;

@Controller
@RequestMapping("/course")
public class CourseController {
	public static Logger logger = Logger.getLogger(CourseController.class);
	
	@Autowired
	CourseService courseService;
	@Autowired
	CodeLibraryService codeLibraryService;
	
	@RequiresPermissions({"course:view"})
	@RequestMapping(value="/courses")
	public String listCourse(Model model, @ModelAttribute CourseInfo CourseInfo, @ModelAttribute Page page, HttpServletRequest request) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		logger.info(CourseInfo.toString());
		List<CodeLibrary> listcoursetrem=codeLibraryService.selectByCodeNo("COURSETERM");//学期列表
		List<CodeLibrary> listcoursekind=codeLibraryService.selectByCodeNo("COURSEKIND");//课程类型
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		List<CourseInfo> courses = courseService.listCourse(CourseInfo,page);
		model.addAttribute("coursetrems",listcoursetrem);
		model.addAttribute("coursekinds",listcoursekind);
		model.addAttribute("grades",listgrade);
		model.addAttribute("courses",courses);
		return "course/course_list";
	}
	/**
	 * 跳转课程信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"course:add"})
	@RequestMapping(value="/courseadd",method=RequestMethod.GET)
	public String addcourse(Model model){
		List<CodeLibrary> listcoursetrem=codeLibraryService.selectByCodeNo("COURSETERM");//学期列表
		List<CodeLibrary> listcoursekind=codeLibraryService.selectByCodeNo("COURSEKIND");//课程类型
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("coursetrems",listcoursetrem);
		model.addAttribute("coursekinds",listcoursekind);
		model.addAttribute("grades",listgrade);
		model.addAttribute(new CourseInfo());
		return "course/course_add";
	}
   /**
    * 添加课程信息
    * @param CourseInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"course:add"})
    @RequestMapping(value="/courseadd",method=RequestMethod.POST)
	public String addcourse(Model model,@Validated CourseInfo courseInfo,BindingResult br){
    	List<CodeLibrary> listcoursetrem=codeLibraryService.selectByCodeNo("COURSETERM");//学期列表
		List<CodeLibrary> listcoursekind=codeLibraryService.selectByCodeNo("COURSEKIND");//课程类型
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("coursetrems",listcoursetrem);
		model.addAttribute("coursekinds",listcoursekind);
		model.addAttribute("grades",listgrade);
    	if(br.hasErrors()){
    		return "course/course_add";
    	}
    	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
    	courseInfo.setCreatetime(createtime);
    	courseInfo.setUpdatetime(createtime);
    	courseService.insertCourse(courseInfo);
    	return "redirect:courses";
	}
    /**
     * 跳转到批量添加课程页面
     * @return
     */
	@RequiresPermissions({"course:addbatch"})
    @RequestMapping(value="/courseaddbatch",method=RequestMethod.GET)
    public String addCourseBatch(){
    	return "course/course_addbatch";
    }
    /**
     * 批量添加课程信息
     * @param request
     * @return
     */
	@RequiresPermissions({"course:addbatch"})
    @RequestMapping(value="/courseaddbatch",method=RequestMethod.POST)
    public String addCourseBatch(HttpServletRequest request){
    	//filepath="F://courses/courses.xls";
		String filepath=FileUploadUtils.tranferFile(request, "/userfiles/xls");
    	File file=new File(filepath);
    	courseService.uploadCoursejxl(file);
    	return "course/course_list";
    }
    /**
	 * 跳转课程修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"course:edit"})
	@RequestMapping(value="/{id}/courseedit",method=RequestMethod.GET)
	public String editCourse(Model model,@PathVariable int id){
		CourseInfo courseInfo=courseService.getCourseByID(id);
		List<CodeLibrary> listcoursetrem=codeLibraryService.selectByCodeNo("COURSETERM");//学期列表
		List<CodeLibrary> listcoursekind=codeLibraryService.selectByCodeNo("COURSEKIND");//课程类型
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("coursetrems",listcoursetrem);
		model.addAttribute("coursekinds",listcoursekind);
		model.addAttribute("grades",listgrade);
		model.addAttribute(courseInfo);
		return "course/course_edit";
	}
	/**
     * 导出课程信息
     * @param filepath
     * @return
     */
	@RequiresPermissions({"course:export"})
    @RequestMapping(value="/courseexport",method=RequestMethod.POST)
    public String exportCourses(CourseInfo CourseInfo,HttpServletResponse response){
        List<CourseInfo> list=courseService.selectByParams(CourseInfo);
        if(list.size()>0){
        	courseService.exportCourse(list, response);
        }
    	return "course/course_list";
    }
   /**
    * 修改课程信息
    * @param courseInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"course:edit"})
    @RequestMapping(value="/courseedit",method=RequestMethod.POST)
	public String editCourse(Model model,@Validated CourseInfo courseInfo,BindingResult br){
    	List<CodeLibrary> listcoursetrem=codeLibraryService.selectByCodeNo("COURSETERM");//学期列表
		List<CodeLibrary> listcoursekind=codeLibraryService.selectByCodeNo("COURSEKIND");//课程类型
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("coursetrems",listcoursetrem);
		model.addAttribute("coursekinds",listcoursekind);
		model.addAttribute("grades",listgrade);
    	if(br.hasErrors()){
    		return "course/course_edit";
    	}
    	int isOk=courseService.editCourse(courseInfo);
		return "redirect:courses";
	}
    /**
     * 删除课程信息
     * @param user
     * @param br
     * @return
     */
	@RequiresPermissions({"course:del"})
     @RequestMapping(value="/{id}/coursedel")
 	public String delcourse(@PathVariable Integer id){
    	 courseService.deleteCourse(id);
 		return "redirect:/course/courses";
 	}
     /**
      * 批量删除课程信息
      * @param ids
      * @return
      */
	@RequiresPermissions({"course:dels"})
	@RequestMapping(value="/dels")
  	public String dels(HttpServletRequest request){
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	int[] idArray1=new int[idArray.length];
    	for(int i=0;i<idArray.length;i++){
    		idArray1[i]=Function.getInt(idArray[i], 0);
    	}
    	courseService.deleteCourses(idArray1);;
  		return "redirect:/book/books";
  	}
}
