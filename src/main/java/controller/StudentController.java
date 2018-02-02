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
import entity.StudentInfo;
import service.CodeLibraryService;
import service.CollegeService;
import service.StudentService;
import util.DateUtil;
import util.FileUploadUtils;
import util.Function;
import util.Page;

@Controller
@RequestMapping("/student")
public class StudentController {
	public static Logger logger = Logger.getLogger(StudentController.class);
	
	@Autowired
	StudentService studentService;
	@Autowired
	CodeLibraryService codeLibraryService;
	@Autowired
	CollegeService collegeService;
	/**
	 * 学生列表分页查询
	 * @param model
	 * @param bookInfo
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/students")
	public String listStudent(Model model, @ModelAttribute StudentInfo studentInfo, @ModelAttribute Page page, HttpServletRequest request) {
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		
		List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		List<StudentInfo> students = studentService.listStudent(studentInfo, page);//学生列表
		model.addAttribute("sexs",listsex);
		model.addAttribute("grades",listgrade);
		model.addAttribute("students",students);
		return "student/stu_list";
	}
	/**
	 * 跳转学生信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"student:add"})
	@RequestMapping(value="/studentadd",method=RequestMethod.GET)
	public String addStudent(Model model){
		List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("sexs",listsex);
		model.addAttribute("grades",listgrade);
		model.addAttribute(new StudentInfo());
		return "student/stu_add";
	}
   /**
    * 添加学生信息
    * @param studentInfo
    * @param model
    * @param br
    * @return
    */
	@RequiresPermissions({"student:add"})
    @RequestMapping(value="/studentadd",method=RequestMethod.POST)
	public String addStudent(Model model,@Validated StudentInfo studentInfo,BindingResult br){
    	List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("sexs",listsex);
		model.addAttribute("grades",listgrade);
    	if(br.hasErrors()){
    		return "student/stu_add";
    	}
    	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
    	studentInfo.setCreateTime(createtime);
    	studentInfo.setUpdateTime(createtime);
    	int isOk=studentService.insertStudent(studentInfo);
    	return "redirect:students";
	}
    /**
     * 跳转到批量添加学生页面
     * @return
     */
	@RequiresPermissions({"student:addbatch"})
    @RequestMapping(value="/studentaddbatch",method=RequestMethod.GET)
    public String addBookBatch(){
    	return "student/stu_addbatch";
    }
    /**
     * 批量添加学生信息
     * @param request
     * @return
     */
	@RequiresPermissions({"student:addbatch"})
    @RequestMapping(value="/studentaddbatch",method=RequestMethod.POST)
    public String addBookBatch(HttpServletRequest request){
		String filepath=FileUploadUtils.tranferFile(request, "/userfiles/xls");
    	File file=new File(filepath);
    	studentService.uploadStudentjxl(file);
    	return "student/student_list";
    }
    /**
	 * 跳转学生修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"student:edit"})
	@RequestMapping(value="/{id}/studentedit",method=RequestMethod.GET)
	public String editStudent(Model model,@PathVariable int id){
		StudentInfo studentInfo=studentService.getStudentByID(id);
		List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("sexs",listsex);
		model.addAttribute("grades",listgrade);
		model.addAttribute(studentInfo);
		return "student/stu_edit";
	}
	/**
     * 导出学生信息
     * @param filepath
     * @return
     */
	@RequiresPermissions({"student:export"})
    @RequestMapping(value="/studentexport",method=RequestMethod.POST)
    public String exportBooks(StudentInfo studentInfo,HttpServletResponse response){
        List<StudentInfo> list=studentService.selectByParams(studentInfo);
        if(list.size()>0){
        	studentService.exportStudent(list, response);
        }
    	return null;
    }
   /**
    * 修改学生信息
    * @param studentInfo
    * @param br
    * @return
    */
    @RequiresPermissions({"student:edit"})
    @RequestMapping(value="/studentedit",method=RequestMethod.POST)
	public String editStudent(Model model,@Validated StudentInfo studentInfo,BindingResult br){
    	List<CodeLibrary> listsex=codeLibraryService.selectByCodeNo("SEX");//性别列表
		List<CodeLibrary> listgrade=codeLibraryService.selectByCodeNo("GRADE");//年级列表
		model.addAttribute("sexs",listsex);
		model.addAttribute("grades",listgrade);
		StudentInfo studentInfo1=studentService.selectByStuNo(studentInfo.getStudentNo());
		if(studentInfo1!=null){
			br.rejectValue("studentNo", "HSAEXIST", "该学号已存在");
		}
    	if(br.hasErrors()){
    		return "student/student_edit";
    	}
    	int isOk=studentService.editStudent(studentInfo);
		return "redirect:students";
	}
    /**
     * 删除学生信息
     * @param user
     * @param br
     * @return
     */
    @RequiresPermissions({"student:del"})
     @RequestMapping(value="/{id}/studentdel")
 	public String delStudent(@PathVariable int id){
    	 studentService.deleteStudent(id);
 		return "redirect:/stu/students";
 	}
     /**
      * 批量删除学生信息
      * @param ids
      * @return
      */
    @RequiresPermissions({"student:dels"})
    @RequestMapping(value="/dels")
  	public String dels(HttpServletRequest request){
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	int[] idArray1=new int[idArray.length];
    	for(int i=0;i<idArray.length;i++){
    		idArray1[i]=Function.getInt(idArray[i], 0);
    	}
    	studentService.deleteStudents(idArray1);;
  		return "redirect:/book/books";
  	}
}
