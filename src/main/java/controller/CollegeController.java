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

import entity.CollegeInfo;
import net.sf.json.JSONArray;
import service.CollegeService;
import util.DateUtil;
import util.FileUploadUtils;
import util.Function;
import util.Page;

@Controller
@RequestMapping("/college")
public class CollegeController {
	public static Logger logger = Logger.getLogger(CollegeController.class);
	
	@Autowired
	CollegeService collegeService;

	/**
	 * 院系列表分页查询
	 * @param model
	 * @param bookInfo
	 * @param page
	 * @param request
	 * @return
	 */
	@RequiresPermissions({"college:view"})
	@RequestMapping(value="/colleges")
	public String listCollege(Model model, @ModelAttribute CollegeInfo collegeInfo, @ModelAttribute Page page, HttpServletRequest request) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		List<CollegeInfo> colleges = collegeService.listCollege(collegeInfo, page);//院系列表
		model.addAttribute("colleges",colleges);
		return "college/college_list";
	}
	/**
	 * 跳转院系信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"college:add"})
	@RequestMapping(value="/collegeadd",method=RequestMethod.GET)
	public String addCollege(Model model){
		model.addAttribute(new CollegeInfo());
		return "college/college_add";
	}
   /**
    * 添加院系信息
    * @param collegeInfo
    * @param model
    * @param br
    * @return
    */
	@RequiresPermissions({"college:add"})
    @RequestMapping(value="/collegeadd",method=RequestMethod.POST)
	public String addCollege(Model model,@Validated CollegeInfo collegeInfo,BindingResult br){
    	if(br.hasErrors()){
    		return "college/college_add";
    	}
    	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
    	String collegeId=Function.getUUID();
    	collegeInfo.setCollegeId(collegeId);
    	collegeInfo.setCreateTime(createtime);
    	collegeInfo.setUpdateTime(createtime);
    	int isOk=collegeService.insertCollege(collegeInfo);
    	return "redirect:colleges";
	}
    /**
     * 跳转到批量添加院系页面
     * @return
     */
	@RequiresPermissions({"college:addbatch"})
    @RequestMapping(value="/collegeaddbatch",method=RequestMethod.GET)
    public String addBookBatch(){
    	return "college/college_addbatch";
    }
    /**
     * 批量添加院系信息
     * @param request
     * @return
     */
	@RequiresPermissions({"college:addbatch"})
    @RequestMapping(value="/collegeaddbatch",method=RequestMethod.POST)
    public String addBookBatch(HttpServletRequest request){
		String filepath=FileUploadUtils.tranferFile(request, "/userfiles/xls");
    	File file=new File(filepath);
    	collegeService.uploadCollegejxl(file);
    	return "college/college_list";
    }
    /**
	 * 跳转院系修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"college:edit"})
	@RequestMapping(value="/{id}/collegeedit",method=RequestMethod.GET)
	public String editCollege(Model model,@PathVariable String id){
		CollegeInfo collegeInfo=collegeService.getCollegeByID(id);
		model.addAttribute(collegeInfo);
		return "college/college_edit";
	}
	/**
     * 导出院系信息
     * @param filepath
     * @return
     */
	@RequiresPermissions({"college:export"})
    @RequestMapping(value="/collegeexport",method=RequestMethod.POST)
    public String exportBooks(CollegeInfo collegeInfo,HttpServletResponse response){
        List<CollegeInfo> list=collegeService.selectByParams(collegeInfo);
        if(list.size()>0){
        	collegeService.exportCollege(list, response);
        }
    	return "college/college_list";
    }
   /**
    * 修改院系信息
    * @param collegeInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"college:edit"})
    @RequestMapping(value="/collegeedit",method=RequestMethod.POST)
	public String editCollege(Model model,@Validated CollegeInfo collegeInfo,BindingResult br){
    	if(br.hasErrors()){
    		return "college/college_edit";
    	}
    	int isOk=collegeService.editCollege(collegeInfo);
		return "redirect:colleges";
	}
    /**
     * 删除院系信息
     * @param user
     * @param br
     * @return
     */
	@RequiresPermissions({"college:del"})
     @RequestMapping(value="/{id}/collegedel")
 	public String delCollege(@PathVariable String id){
    	 collegeService.deleteCollege(id);
 		return "redirect:/college/colleges";
 	}
     /**
      * 批量删除院系信息
      * @param ids
      * @return
      */
	@RequiresPermissions({"college:dels"})
	@RequestMapping(value="/dels")
  	public String dels(HttpServletRequest request){
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	collegeService.deleteColleges(idArray);;
  		return "redirect:/book/books";
  	}
      /**
       * 查找所有学院
       * @param request
       * @param response
       */
    @RequestMapping(value="/selectall")
    public void selectall(HttpServletRequest request,HttpServletResponse response){
     	try {
     		response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			List<CollegeInfo> list=collegeService.selectAll();
			JSONArray jsonArray=JSONArray.fromObject(list);
			out.print(jsonArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
}
