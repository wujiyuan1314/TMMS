package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.BookInfo;
import entity.ClassInfo;
import entity.CodeLibrary;
import entity.CollegeInfo;
import entity.SpecialtyInfo;
import entity.StuChooseBook;
import entity.StudentInfo;
import entity.TmmsUser;
import net.sf.json.JSONObject;
import service.BookService;
import service.ClassService;
import service.CodeLibraryService;
import service.CollegeService;
import service.SpecialtyService;
import service.StuChooseBookService;
import service.StudentService;
import util.DateUtil;
import util.Function;
import util.HttpClientUtil;
import util.JsonUtil;
import util.Page;

@Controller
@RequestMapping("/stuchoosebook")
public class StuChooseBookController {
	public static Logger logger = Logger.getLogger(StuChooseBookController.class);
	
	@Autowired
	StuChooseBookService stuChooseBookService;
	@Autowired
	BookService bookService;
	@Autowired
	StudentService studentService;
	@Autowired
	CollegeService collegeService;
	@Autowired
	SpecialtyService specialtyService;
	@Autowired
	ClassService classService;
	@Autowired
	CodeLibraryService codeLibraryService;
	
	@RequiresPermissions({"stuchoosebook:view"})
	@RequestMapping(value="/stuchoosebooks")
	public String listStuchoosebook(Model model, @ModelAttribute StuChooseBook stuChooseBook, @ModelAttribute Page page, HttpServletRequest request,HttpServletResponse response) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		logger.info(stuChooseBook.toString());
		response.setCharacterEncoding("UTF-8");
    	HttpSession session=request.getSession();
		TmmsUser user=(TmmsUser)session.getAttribute("tmmsUser");
		if(user!=null){
			StudentInfo student=studentService.selectByStuNo(user.getUsername());
			if(student!=null){
				stuChooseBook.setStuId(student.getId());
			}
		}
		List<StuChooseBook> stuChooseBooks = stuChooseBookService.listStuChooseBook(stuChooseBook,page);
		for(StuChooseBook stuChooseBook1:stuChooseBooks){
			BookInfo bookInfo=bookService.getBookByID(stuChooseBook1.getBookId());
			if(bookInfo!=null){
				stuChooseBook1.setExtend1(bookInfo.getBookName());
			}else{
				stuChooseBook1.setExtend1("");
			}
			StudentInfo student=studentService.getStudentByID(stuChooseBook1.getStuId());
			if(student!=null){
				stuChooseBook1.setExtend2(student.getStudentName());
			}else{
				stuChooseBook1.setExtend2("");
			}
			CollegeInfo college=collegeService.getCollegeByID(stuChooseBook1.getCollegeId());
			if(college!=null){
				stuChooseBook1.setExtend3(college.getCollegeName());
			}else{
				stuChooseBook1.setExtend3("");
			}
			SpecialtyInfo specialty=specialtyService.getSpecialtyByID(stuChooseBook1.getSpecialtyId());
			if(specialty!=null){
				stuChooseBook1.setExtend4(specialty.getSpecialtyName());
			}else{
				stuChooseBook1.setExtend4("");
			}
			ClassInfo classInfo=classService.getClassByID(stuChooseBook1.getClassId());
			if(classInfo!=null){
				stuChooseBook1.setExtend5(classInfo.getClassName());
			}else{
				stuChooseBook1.setExtend5("");
			}
		}
		model.addAttribute("stuChooseBooks",stuChooseBooks);
		return "stuchoosebook/stuchoosebook_list";
	}
	 /**
     * 学生选书界面
     * @return
     */
	@RequiresPermissions({"stuchoosebook:add"})
    @RequestMapping(value="/stuchoosebookadd",method=RequestMethod.GET)
    public String add(Model model, @ModelAttribute BookInfo BookInfo, @ModelAttribute Page page, HttpServletRequest request) {
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		logger.info(BookInfo.toString());
		List<BookInfo> books = bookService.listBook(BookInfo,page);
		model.addAttribute("books",books);
    	return "stuchoosebook/stuchoosebook_add";
    }
   /**
    * 添加学生选书信息
    * @param BookInfo
    * @param br
    * @return
 * @throws IOException 
    */
	@RequiresPermissions({"stuchoosebook:add"})
	@RequestMapping(value="/add")
  	public String dels(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	HttpSession session=request.getSession();
    	JSONObject json = new JSONObject();
    	
    	TmmsUser user=(TmmsUser)session.getAttribute("tmmsUser");
    	StudentInfo studentInfo=studentService.selectByStuNo(user.getUsername());
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	int[] idArray1=new int[idArray.length];
    	if(studentInfo!=null){
    		for(int i=0;i<idArray.length;i++){
            	StuChooseBook stuChooseBook=new StuChooseBook();
            	stuChooseBook.setStuId(studentInfo.getId());
            	stuChooseBook.setClassId(studentInfo.getClassId());
            	stuChooseBook.setSpecialtyId(studentInfo.getSpecialtyId());
            	stuChooseBook.setCollegeId(studentInfo.getCollegeId());
            	idArray1[i]=Function.getInt(idArray[i], 0);
            	stuChooseBook.setBookId(idArray1[i]);
            	StuChooseBook stuChooseBook1=stuChooseBookService.selectByStuBook(studentInfo.getId(),idArray1[i]);
        		if(stuChooseBook1!=null){
        			json.put("success","0");
        			BookInfo bookInfo=bookService.getBookByID(idArray1[i]);
    				json.put("msg", "该学生已经选过"+bookInfo.getBookName()+"！");
    				response.getWriter().append(json.toString());
    				return null;
        		}
        		
            	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
            	stuChooseBook.setCreateTime(createtime);
            	stuChooseBook.setUpdataTime(createtime);
            	stuChooseBookService.insertStuChooseBook(stuChooseBook);
        	}
    	}else{
    		json.put("success","0");
    		json.put("msg", "该名学生不存在");
    		response.getWriter().append(json.toString());
    		return null;
    	}
    	
    	json.put("success","1");
		json.put("msg", "选择书籍成功");
		response.getWriter().append(json.toString());
    	return null;
  	}
	/**
     * 导出学生选书信息
     * @param filepath
     * @return
     */
	@RequiresPermissions({"stuchoosebook:export"})
    @RequestMapping(value="/stuchoosebookexport",method=RequestMethod.POST)
    public String exportBooks(StuChooseBook stuChooseBook,HttpServletResponse response){
        List<StuChooseBook> list=stuChooseBookService.selectByParams(stuChooseBook);
        if(list.size()>0){
        	stuChooseBookService.exportSCB(list, response);
        }
    	return null;
    }
    /**
     * 删除学生选书信息
     * @param user
     * @param br
     * @return
     */
	@RequiresPermissions({"stuchoosebook:del"})
     @RequestMapping(value="/{id}/stuchoosebookdel")
 	public String delstuchoosebook(@PathVariable Integer id){
		stuChooseBookService.deleteStuChooseBook(id);
 		return "redirect:/stuchoosebook/stuchoosebooks";
 	}
	/**
	 * 查询其他系统订单
	 * @return
	 */
	@RequestMapping(value="/selectOtherOrder")
	public String selectOtherOrder(Model model,@ModelAttribute StuChooseBook stuChooseBook){
		String url="";//其他学校系统地址
		List<CodeLibrary> codeLibrarys=codeLibraryService.selectByCodeNo("SCHOOLURL");
		for(CodeLibrary codeLibrary:codeLibrarys){
			if(stuChooseBook.getExtend1()!=null&&StringUtils.equals(codeLibrary.getExtend1(), stuChooseBook.getExtend1())){
				url=codeLibrary.getExtend1()+"/stuchoosebook/otherOrders";
				break;
			}
		}
		if(StringUtils.equals(url, "")){
			url="http://127.0.0.1:8080/tmms/stuchoosebook/otherOrders";
		}
		model.addAttribute("codeLibrarys", codeLibrarys);
		String json=HttpClientUtil.httpPostRequest(url);
		return "order/order_list";
	}
	/**
	 * 供其他系统调用查询订单
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/otherOrders",method=RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody List<StuChooseBook> selectByParams(String retjson) throws IOException {
		StuChooseBook stuChooseBook=new StuChooseBook();
		Map<String,Object> retMap=JsonUtil.getMap4Json(retjson);
		if(retMap!=null){
			if(retMap.get("classId")!=null){
				stuChooseBook.setClassId(retMap.get("classId").toString());
			}
			if(retMap.get("specialtyId")!=null){
				stuChooseBook.setClassId(retMap.get("specialtyId").toString());
			}
			if(retMap.get("collegeId")!=null){
				stuChooseBook.setClassId(retMap.get("collegeId").toString());
			}
		}
		List<StuChooseBook> stuChooseBooks = stuChooseBookService.selectByParams(stuChooseBook);
		return stuChooseBooks;
	}
}
