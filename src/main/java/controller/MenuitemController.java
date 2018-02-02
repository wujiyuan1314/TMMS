package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entity.Menuitem;
import net.sf.json.JSONArray;
import service.MenuitemService;
import util.DateUtil;
import util.Function;

@Controller
@RequestMapping("/menuitem")
public class MenuitemController {
	public static Logger logger = Logger.getLogger(MenuitemController.class);
	
	@Autowired
	MenuitemService menuitemService;
	
	/**
	 * 跳转菜单信息添加界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/menuitemadd",method=RequestMethod.GET)
	public String addmenuitem(Model model){
		List<Menuitem> menuitems=menuitemService.selectByParentId(0);
    	model.addAttribute("menuitems", menuitems);
		model.addAttribute(new Menuitem());
		return "menuitem/menuitem_add";
	}
   /**
    * 添加菜单信息
    * @param Menuitem
    * @param br
    * @return
    */
    @RequestMapping(value="/menuitemadd",method=RequestMethod.POST)
	public String addmenuitem(Model model,@Validated Menuitem menuitem,BindingResult br){
    	List<Menuitem> menuitems=menuitemService.selectByParentId(0);
    	model.addAttribute("menuitems", menuitems);
    	if(br.hasErrors()){
    		return "menuitem/menuitem_add";
    	}
    	Menuitem menuitemp=menuitemService.getMenuitemByID(menuitem.getParentId());
    	if(menuitemp!=null){
    		menuitemp.setExtend1("1");
    		menuitemService.editMenuitem(menuitemp);
    	}
    	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
    	menuitem.setCreateTime(createtime);
    	menuitem.setUpdataTime(createtime);
    	menuitemService.insertMenuitem(menuitem);
    	return "redirect:menuitems";
	}
    /**
	 * 跳转菜单修改界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/menuitemedit",method=RequestMethod.GET)
	public String editMenuitem(Model model,@PathVariable int id){
		List<Menuitem> menuitems=menuitemService.selectByParentId(0);
    	model.addAttribute("menuitems", menuitems);
		Menuitem menuitem=menuitemService.getMenuitemByID(id);
		model.addAttribute(menuitem);
		return "menuitem/menuitem_edit";
	}
   /**
    * 修改菜单信息
    * @param menuitem
    * @param br
    * @return
    */
    @RequestMapping(value="/menuitemedit",method=RequestMethod.POST)
	public String editMenuitem(Model model,@Validated Menuitem menuitem,BindingResult br){
    	List<Menuitem> menuitems=menuitemService.selectByParentId(0);
    	model.addAttribute("menuitems", menuitems);
    	if(br.hasErrors()){
    		return "menuitem/menuitem_edit";
    	}
    	Menuitem menuitemp=menuitemService.getMenuitemByID(menuitem.getParentId());
    	if(menuitemp!=null){
    		menuitemp.setExtend1("1");
    		menuitemService.editMenuitem(menuitemp);
    	}
    	int isOk=menuitemService.editMenuitem(menuitem);
		return "redirect:menuitems";
	}
    /**
     * 删除菜单信息
     * @param user
     * @param br
     * @return
     */
     @RequestMapping(value="/{id}/menuitemdel")
 	public String delmenuitem(@PathVariable int id){
    	 menuitemService.deleteMenuitem(id);
 		return "redirect:/menuitem/menuitems";
 	}
     /**
      * 批量删除菜单信息
      * @param ids
      * @return
      */
     @RequestMapping(value="/dels")
   	 public String dels(HttpServletRequest request){
     	String ids=request.getParameter("ids");
     	String idArray[]=ids.split(",");
     	int[] idArray1=new int[idArray.length];
     	for(int i=0;i<idArray.length;i++){
     		idArray1[i]=Function.getInt(idArray[i], 0);
     	}
     	menuitemService.deleteMenuitems(idArray1);;
   		return "redirect:/book/books";
   	 }
	  /**
	   * 按照父ID查找菜单
	   * @param request
	   * @param model
	   * @return
	   */
    @RequestMapping(value="/menuitems")
    public String selectByParentId(HttpServletRequest request,Model model){
    	int parentId=Function.getInt(request.getParameter("parentId"), 0);
    	List<Menuitem> menuitems=menuitemService.selectByParentId(parentId);
    	model.addAttribute("menuitems", menuitems);
    	return "menuitem/menuitem_list";
    }
    /**
	   * 按照父ID查找菜单
	   * @param request
	   * @param response
	   * @param model
	   * @return
	   */
  @RequestMapping(value="/menuitemnode")
  public void selectByParentId(HttpServletRequest request,HttpServletResponse response,Model model){
  	int parentId=Function.getInt(request.getParameter("parentId"), 0);
  	List<Menuitem> menuitems=menuitemService.selectByParentId(parentId);
  	model.addAttribute("menuitems", menuitems);
  	try {
   		response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			JSONArray jsonArray=JSONArray.fromObject(menuitems);
			out.print(jsonArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
}
