package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entity.CodeCatalog;
import entity.CodeLibrary;
import service.CodeCatalogService;
import service.CodeLibraryService;
import util.Page;

@Controller
@RequestMapping("/codeCatalog")
public class CodeCatalogController {
	public static Logger logger = Logger.getLogger(CodeCatalogController.class);
	
	@Autowired
	CodeCatalogService codeCatalogService;
	@Autowired
	CodeLibraryService codeLibraryService;
	
	@RequiresPermissions({"codeCatalog:view"})
	@RequestMapping(value="/codeCatalogs")
	public String listCodeCatalog(Model model, @ModelAttribute CodeCatalog CodeCatalog, @ModelAttribute Page page, HttpServletRequest request) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		logger.info(CodeCatalog.toString());
		List<CodeCatalog> codeCatalogs = codeCatalogService.listCodeCatalog(CodeCatalog,page);
		model.addAttribute("codeCatalogs",codeCatalogs);
		return "codecatalog/codecatalog_list";
	}
	/**
	 * 跳转参数信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"codeCatalog:add"})
	@RequestMapping(value="/codeCatalogadd",method=RequestMethod.GET)
	public String addcodeCatalog(Model model){
		model.addAttribute(new CodeCatalog());
		return "codecatalog/codecatalog_add";
	}
   /**
    * 添加参数信息
    * @param CodeCatalog
    * @param br
    * @return
    */
	@RequiresPermissions({"codeCatalog:add"})
    @RequestMapping(value="/codeCatalogadd",method=RequestMethod.POST)
	public String addcodeCatalog(Model model,@Validated CodeCatalog CodeCatalog,BindingResult br){
    	String codeno=CodeCatalog.getCodeno();
    	CodeCatalog CodeCatalog1=codeCatalogService.getCodeCatalogByID(codeno);
    	if(CodeCatalog1!=null){
    		br.rejectValue("codeno", "NotRepeat", "编码重复");
    	}
    	if(br.hasErrors()){
    		return "codecatalog/codecatalog_add";
    	}
    	codeCatalogService.insertCodeCatalog(CodeCatalog);
    	return "redirect:codeCatalogs";
	}
    /**
     * 跳转到批量添加参数页面
     * @return
     */
	@RequiresPermissions({"codeCatalog:addbatch"})
    @RequestMapping(value="/codeCatalogaddbatch",method=RequestMethod.GET)
    public String addCodeCatalogBatch(){
    	return "codecatalog/codecatalog_addbatch";
    }
    /**
	 * 跳转参数修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"codeCatalog:edit"})
	@RequestMapping(value="/{id}/codeCatalogedit",method=RequestMethod.GET)
	public String editCodeCatalog(Model model,@PathVariable String id){
		CodeCatalog codeCatalog=codeCatalogService.getCodeCatalogByID(id);
		model.addAttribute(codeCatalog);
		return "codecatalog/codecatalog_edit";
	}
	
   /**
    * 修改参数信息
    * @param codeCatalog
    * @param br
    * @return
    */
	@RequiresPermissions({"codeCatalog:edit"})
    @RequestMapping(value="/codeCatalogedit",method=RequestMethod.POST)
	public String editCodeCatalog(Model model,@Validated CodeCatalog codeCatalog,BindingResult br){
    	if(br.hasErrors()){
    		return "codecatalog/codecatalog_edit";
    	}
    	int isOk=codeCatalogService.editCodeCatalog(codeCatalog);
		return "redirect:codeCatalogs";
	}
    /**
     * 删除参数信息
     * @param user
     * @param br
     * @return
     */
	@RequiresPermissions({"codeCatalog:del"})
     @RequestMapping(value="/{id}/codeCatalogdel")
 	public String delcodeCatalog(@PathVariable String id){
    	 codeCatalogService.deleteCodeCatalog(id);
 		return "redirect:/codeCatalog/codeCatalogs";
 	}
     /**
      * 批量删除参数信息
      * @param ids
      * @return
      */
	@RequiresPermissions({"codeCatalog:dels"})
	@RequestMapping(value="/dels")
  	public String dels(HttpServletRequest request){
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	codeCatalogService.deleteCodeCatalogs(idArray);;
  		return "redirect:/class/classs";
  	}
      @RequestMapping(value="/{codeno}/codelibrarylist")
    public String selectCodeLibraryByCodeno(@PathVariable String codeno,Model model){
    	List<CodeLibrary> list=codeLibraryService.selectByCodeNo(codeno);
    	model.addAttribute("codeno", codeno);
    	model.addAttribute("codeLibrarys", list);
    	return "codelibrary/codelibrary_list";
    }
}
