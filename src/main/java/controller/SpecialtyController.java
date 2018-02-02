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

import entity.SpecialtyInfo;
import net.sf.json.JSONArray;
import service.SpecialtyService;
import util.DateUtil;
import util.FileUploadUtils;
import util.Function;
import util.Page;

@Controller
@RequestMapping("/specialty")
public class SpecialtyController {
	public static Logger logger = Logger.getLogger(SpecialtyController.class);
	
	@Autowired
	SpecialtyService specialtyService;

	@RequiresPermissions({"specialty:view"})
	@RequestMapping(value="/specialtys")
	public String listSpecialty(Model model, @ModelAttribute SpecialtyInfo specialtyInfo, @ModelAttribute Page page, HttpServletRequest request) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		
		List<SpecialtyInfo> specialtys = specialtyService.listSpecialty(specialtyInfo, page);//专业列表
		model.addAttribute("specialtys",specialtys);
		return "specialty/specialty_list";
	}
	/**
	 * 跳转专业信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"specialty:add"})
	@RequestMapping(value="/specialtyadd",method=RequestMethod.GET)
	public String addSpecialty(Model model){
		model.addAttribute(new SpecialtyInfo());
		return "specialty/specialty_add";
	}
   /**
    * 添加专业信息
    * @param specialtyInfo
    * @param model
    * @param br
    * @return
    */
	@RequiresPermissions({"specialty:add"})
    @RequestMapping(value="/specialtyadd",method=RequestMethod.POST)
	public String addSpecialty(Model model,@Validated SpecialtyInfo specialtyInfo,BindingResult br){
    	if(br.hasErrors()){
    		return "specialty/specialty_add";
    	}
    	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
    	String specialtyId=Function.getUUID();
    	specialtyInfo.setSpecialtyId(specialtyId);
    	specialtyInfo.setCreateTime(createtime);
    	specialtyInfo.setUpdateTime(createtime);
    	int isOk=specialtyService.insertSpecialty(specialtyInfo);
    	return "redirect:specialtys";
	}
    /**
     * 跳转到批量添加专业页面
     * @return
     */
	@RequiresPermissions({"specialty:addbatch"})
    @RequestMapping(value="/specialtyaddbatch",method=RequestMethod.GET)
    public String addBookBatch(){
    	return "specialty/specialty_addbatch";
    }
    /**
     * 批量添加专业信息
     * @param request
     * @return
     */
	@RequiresPermissions({"specialty:addbatch"})
    @RequestMapping(value="/specialtyaddbatch",method=RequestMethod.POST)
    public String addBookBatch(HttpServletRequest request){
		String filepath=FileUploadUtils.tranferFile(request, "/userfiles/xls");
    	File file=new File(filepath);
    	specialtyService.uploadSpecialtyjxl(file);
    	return "specialty/specialty_list";
    }
    /**
	 * 跳转专业修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"specialty:edit"})
	@RequestMapping(value="/{id}/specialtyedit",method=RequestMethod.GET)
	public String editSpecialty(Model model,@PathVariable String id){
		SpecialtyInfo specialtyInfo=specialtyService.getSpecialtyByID(id);
		model.addAttribute(specialtyInfo);
		return "specialty/specialty_edit";
	}
	/**
     * 导出专业信息
     * @param filepath
     * @return
     */
	@RequiresPermissions({"specialty:export"})
    @RequestMapping(value="/specialtyexport",method=RequestMethod.POST)
    public String exportBooks(SpecialtyInfo specialtyInfo,HttpServletResponse response){
        List<SpecialtyInfo> list=specialtyService.selectByParams(specialtyInfo);
        if(list.size()>0){
        	specialtyService.exportSpecialty(list, response);
        }
    	return "specialty/specialty_list";
    }
   /**
    * 修改专业信息
    * @param specialtyInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"specialty:edit"})
    @RequestMapping(value="/specialtyedit",method=RequestMethod.POST)
	public String editSpecialty(Model model,@Validated SpecialtyInfo specialtyInfo,BindingResult br){
    	if(br.hasErrors()){
    		return "specialty/specialty_edit";
    	}
    	int isOk=specialtyService.editSpecialty(specialtyInfo);
		return "redirect:specialtys";
	}
    /**
     * 删除专业信息
     * @param user
     * @param br
     * @return
     */
	@RequiresPermissions({"specialty:del"})
     @RequestMapping(value="/{id}/specialtydel")
 	public String delSpecialty(@PathVariable String id){
    	 specialtyService.deleteSpecialty(id);
 		return "redirect:/specialty/specialtys";
 	}
     /**
      * 批量删除专业信息
      * @param ids
      * @return
      */
	@RequiresPermissions({"specialty:dels"})
	@RequestMapping(value="/dels")
  	 public String dels(HttpServletRequest request){
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	specialtyService.deleteSpecialtys(idArray);;
  		return "redirect:/book/books";
  	 }
    /**
     * 根据collegeId选择专业
     * @param request
     * @param response
     */
    @RequestMapping(value="/selectbycollegeid")
    public void selectByCollegeId(HttpServletRequest request,HttpServletResponse response){
     	try {
     		response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			String collegeId=request.getParameter("collegeId");
			List<SpecialtyInfo> list=specialtyService.selectByCollegeId(collegeId);
			JSONArray jsonArray=JSONArray.fromObject(list);
			out.print(jsonArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
}
