package controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entity.UserRole;
import entity.TmmsUser;
import service.TmmsUserService;
import service.UserRoleService;
import util.DateUtil;
import util.Function;
import util.Page;

@Controller
@RequestMapping("/tmmsuser")
public class TmmsUserController {
	public static Logger logger = Logger.getLogger(TmmsUserController.class);
	@Autowired
	TmmsUserService tmmsUserService;
	@Autowired
	UserRoleService userRoleService;
	
	@CachePut(value="userCache")
	@RequestMapping(value="/tmmsusers")
	public String listTmmsUser(Model model, @ModelAttribute TmmsUser tmmsUser, @ModelAttribute Page page, HttpServletRequest request) {
		
		String currentPageStr = request.getParameter("currentPage");
		logger.info(currentPageStr + "===========");
		if(currentPageStr != null){
			int currentPage = Integer.parseInt(currentPageStr);
			page.setCurrentPage(currentPage);
		}
		logger.info(page.toString());
		logger.info(tmmsUser.toString());
		List<TmmsUser> tmmsusers = tmmsUserService.listTmmsUser(tmmsUser, page);
		model.addAttribute("tmmsusers",tmmsusers);
		List<UserRole> userroles=userRoleService.selectAll();
		model.addAttribute("userroles",userroles);
		return "tmmsuser/tmmsuser_list";
	}
	/**
	 * 跳转用户信息添加界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/tmmsuseradd",method=RequestMethod.GET)
	public String addtmmsuser(Model model){
		List<UserRole> userroles=userRoleService.selectAll();
		model.addAttribute("userroles", userroles);
		model.addAttribute(new TmmsUser());
		return "tmmsuser/tmmsuser_add";
	}
   /**
    * 添加用户信息
    * @param TmmsUser
    * @param br
    * @return
    */
    @RequestMapping(value="/tmmsuseradd",method=RequestMethod.POST)
	public String addtmmsuser(Model model,@Validated TmmsUser tmmsUser,BindingResult br){
    	List<UserRole> userroles=userRoleService.selectAll();
		model.addAttribute("userroles", userroles);
		TmmsUser tmmsUser1=tmmsUserService.getTmmsUserByID(tmmsUser.getId());
    	if(tmmsUser1!=null){
    		br.rejectValue("username", "NotRepet", "用户名已存在");
    	}
    	if(br.hasErrors()){
    		return "tmmsuser/tmmsuser_add";
    	}
    	Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
    	tmmsUser.setCreateTime(createtime);
    	tmmsUser.setUpdateTime(createtime);
    	tmmsUserService.insertTmmsUser(tmmsUser);
    	return "redirect:tmmsusers";
	}
    /**
     * 跳转到批量添加用户页面
     * @return
     */
    @RequestMapping(value="/tmmsuseraddbatch",method=RequestMethod.GET)
    public String addTmmsUserBatch(){
    	return "tmmsuser/tmmsuser_addbatch";
    }
    /**
   	 * 个人资料
   	 * @param model
   	 * @return
   	 */
   	@RequestMapping(value="/{id}/tmmsuserinfo",method=RequestMethod.GET)
   	public String tmmsUserInfo(Model model,@PathVariable int id){
   		List<UserRole> userroles=userRoleService.selectAll();
   		model.addAttribute("userroles", userroles);
   		TmmsUser tmmsUser=tmmsUserService.getTmmsUserByID(id);
   		model.addAttribute(tmmsUser);
   		return "tmmsuser/userinfo";
   	}
    /**
	 * 跳转用户修改界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/tmmsuseredit",method=RequestMethod.GET)
	public String editTmmsUser(Model model,@PathVariable int id){
		List<UserRole> userroles=userRoleService.selectAll();
		model.addAttribute("userroles", userroles);
		TmmsUser tmmsUser=tmmsUserService.getTmmsUserByID(id);
		model.addAttribute(tmmsUser);
		return "tmmsuser/tmmsuser_edit";
	}
   /**
    * 修改用户信息
    * @param tmmsUser
    * @param br
    * @return
    */
    @RequestMapping(value="/tmmsuseredit",method=RequestMethod.POST)
	public String editTmmsUser(Model model,@Validated TmmsUser tmmsUser,BindingResult br){
    	List<UserRole> userroles=userRoleService.selectAll();
		model.addAttribute("userroles", userroles);
    	if(br.hasErrors()){
    		return "tmmsuser/tmmsuser_edit";
    	}
    	tmmsUser.setState((byte) 1);
    	int isOk=tmmsUserService.editTmmsUser(tmmsUser);
		return "redirect:tmmsusers";
	}
    /**
	 * 跳转用户修改密码界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/editpwd",method=RequestMethod.GET)
	public String editPWDUI(Model model,@PathVariable int id){
		TmmsUser tmmsUser=tmmsUserService.getTmmsUserByID(id);
		model.addAttribute(tmmsUser);
		return "tmmsuser/tmmsuser_editpwd";
	}
	 /**
    * 修改用户信息
    * @param tmmsUser
    * @param br
    * @return
    */
    @RequestMapping(value="/editpwd",method=RequestMethod.POST)
	public String editPWD(Model model,@Validated TmmsUser tmmsUser,BindingResult br){
    	if(br.hasErrors()){
    		return "tmmsuser/tmmsuser_editpwd";
    	}
    	TmmsUser tmmsUser1=tmmsUserService.getTmmsUserByID(tmmsUser.getId());
    	tmmsUser1.setState((byte) 1);
    	tmmsUser1.setPassword(tmmsUser.getPassword());
    	int isOk=tmmsUserService.editTmmsUser(tmmsUser1);
		return "tmmsuser/tmmsuser_editpwd";
	}
    /**
     * 删除用户信息
     * @param user
     * @param br
     * @return
     */
     @RequestMapping(value="/{id}/tmmsuserdel")
 	public String deltmmsuser(@PathVariable int id){
    	 tmmsUserService.deleteTmmsUser(id);
 		return "redirect:/tmmsuser/tmmsusers";
 	}
     /**
      * 批量删除用户信息
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
     	tmmsUserService.deleteTmmsUsers(idArray1);;
   		return "redirect:/book/books";
   	}
}
