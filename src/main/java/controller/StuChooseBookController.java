package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import entity.StuChooseBook;
import service.StuChooseBookService;
import util.Page;

@Controller
@RequestMapping("/stuchoosebook")
public class StuChooseBookController {
	public static Logger logger = Logger.getLogger(StuChooseBookController.class);
	
	@Autowired
	StuChooseBookService stuChooseBookService;
	
	@RequiresPermissions({"stuchoosebook:view"})
	@RequestMapping(value="/stuchoosebooks")
	public String listStuchoosebook(Model model, @ModelAttribute StuChooseBook stuChooseBook, @ModelAttribute Page page, HttpServletRequest request) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		logger.info(stuChooseBook.toString());
		List<StuChooseBook> stuChooseBooks = stuChooseBookService.listStuChooseBook(stuChooseBook,page);
		model.addAttribute("stuChooseBooks",stuChooseBooks);
		return "stuchoosebook/stuchoosebook_list";
	}
	/**
	 * 跳转学生选书信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"stuchoosebook:add"})
	@RequestMapping(value="/stuchoosebookadd",method=RequestMethod.GET)
	public String addstuchoosebook(Model model){
		model.addAttribute(new StuChooseBook());
		return "stuchoosebook/stuchoosebook_add";
	}
   /**
    * 添加学生选书信息
    * @param BookInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"stuchoosebook:add"})
    @RequestMapping(value="/stuchoosebookadd",method=RequestMethod.POST)
	public String addstuchoosebook(Model model,@Validated StuChooseBook stuChooseBook,BindingResult br){
    	if(br.hasErrors()){
    		return "stuchoosebook/stuchoosebook_add";
    	}
    	stuChooseBookService.insertStuChooseBook(stuChooseBook);
    	return "redirect:stuchoosebooks";
	}
    /**
	 * 跳转学生选书修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"stuchoosebook:edit"})
	@RequestMapping(value="/{id}/stuchoosebookedit",method=RequestMethod.GET)
	public String editStuChooseBook(Model model,@PathVariable int id){
		StuChooseBook stuChooseBook=stuChooseBookService.getStuChooseBookByID(id);
		model.addAttribute(stuChooseBook);
		return "stuchoosebook/stuchoosebook_edit";
	}
   /**
    * 修改学生选书信息
    * @param bookInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"stuchoosebook:edit"})
    @RequestMapping(value="/stuchoosebookedit",method=RequestMethod.POST)
	public String editStuChooseBook(Model model,@Validated StuChooseBook stuChooseBook,BindingResult br){
    	if(br.hasErrors()){
    		return "stuchoosebook/stuchoosebook_edit";
    	}
    	int isOk=stuChooseBookService.editStuChooseBook(stuChooseBook);
		return "redirect:stuchoosebooks";
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
      * 批量删除学生选书信息
      * @param ids
      * @return
      */
	@RequiresPermissions({"stuchoosebook:dels"})
      @RequestMapping(value="/stuchoosebooksdel")
  	public String delstuchoosebooks(int ids[]){
		stuChooseBookService.deleteStuChooseBooks(ids);
  		return "stuchoosebook/stuchoosebook_list";
  	}
}
