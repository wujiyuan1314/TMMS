package controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import entity.BookInfo;
import entity.CodeLibrary;
import service.BookService;
import service.CodeLibraryService;
import util.FileUploadUtils;
import util.Function;
import util.Page;

@Controller
@RequestMapping("/book")
public class BookController {
	public static Logger logger = Logger.getLogger(BookController.class);
	
	@Autowired
	BookService bookService;
	@Autowired
	CodeLibraryService codeLibraryService;
	
	@RequiresPermissions({"book:view"})
	@RequestMapping(value="/books")
	public String listBook(Model model, @ModelAttribute BookInfo BookInfo, @ModelAttribute Page page, HttpServletRequest request) {
		
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
		return "book/book_list";
	}
	/**
	 * 跳转书籍信息添加界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"book:add"})
	@RequestMapping(value="/bookadd",method=RequestMethod.GET)
	public String addbook(Model model){
		List<CodeLibrary> codelist=codeLibraryService.selectByCodeNo("BOOKTYPE");
		model.addAttribute("codelist", codelist);
		model.addAttribute(new BookInfo());
		return "book/book_add";
	}
   /**
    * 添加书籍信息
    * @param BookInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"book:add"})
    @RequestMapping(value="/bookadd",method=RequestMethod.POST)
	public String addbook(Model model,@Validated BookInfo BookInfo,BindingResult br){
    	List<CodeLibrary> codelist=codeLibraryService.selectByCodeNo("BOOKTYPE");
		model.addAttribute("codelist", codelist);
    	if(br.hasErrors()){
    		return "book/book_add";
    	}
    	bookService.insertBook(BookInfo);
    	return "redirect:books";
	}
    /**
     * 跳转到批量添加书籍页面
     * @return
     */
	@RequiresPermissions({"book:addbatch"})
    @RequestMapping(value="/bookaddbatch",method=RequestMethod.GET)
    public String addBookBatch(){
    	return "book/book_addbatch";
    }
    /**
     * 批量添加书籍信息
     * @param request
     * @return
     */
	@RequiresPermissions({"book:addbatch"})
    @RequestMapping(value="/bookaddbatch",method=RequestMethod.POST)
    public String addBookBatch(HttpServletRequest request){
    	//filepath="F://books/books.xls";
		String filepath=FileUploadUtils.tranferFile(request, "/userfiles/xls");
    	File file=new File(filepath);
    	bookService.uploadBookjxl(file);
    	return "redirect:books";
    }
    /**
	 * 跳转书籍修改界面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({"book:edit"})
	@RequestMapping(value="/{id}/bookedit",method=RequestMethod.GET)
	public String editBook(Model model,@PathVariable int id){
		BookInfo bookInfo=bookService.getBookByID(id);
		List<CodeLibrary> codelist=codeLibraryService.selectByCodeNo("BOOKTYPE");
		model.addAttribute("codelist", codelist);
		model.addAttribute(bookInfo);
		return "book/book_edit";
	}
	/**
     * 导出书籍信息
     * @param filepath
     * @return
     */
	@RequiresPermissions({"book:export"})
    @RequestMapping(value="/bookexport",method=RequestMethod.POST)
    public String exportBooks(BookInfo BookInfo,HttpServletResponse response){
        List<BookInfo> list=bookService.selectByParams(BookInfo);
        if(list.size()>0){
        	bookService.exportBook(list, response);
        }
    	return null;
    }
   /**
    * 修改书籍信息
    * @param bookInfo
    * @param br
    * @return
    */
	@RequiresPermissions({"book:edit"})
    @RequestMapping(value="/bookedit",method=RequestMethod.POST)
	public String editBook(Model model,@Validated BookInfo bookInfo,BindingResult br){
    	List<CodeLibrary> codelist=codeLibraryService.selectByCodeNo("BOOKTYPE");
		model.addAttribute("codelist", codelist);
    	if(br.hasErrors()){
    		return "book/book_edit";
    	}
    	int isOk=bookService.editBook(bookInfo);
		return "redirect:books";
	}
    /**
     * 删除书籍信息
     * @param user
     * @param br
     * @return
     */
	@RequiresPermissions({"book:del"})
     @RequestMapping(value="/{id}/bookdel")
 	public String delbook(@PathVariable Integer id){
    	 bookService.deleteBook(id);
 		return "redirect:/book/books";
 	}
     /**
      * 批量删除书籍信息
      * @param ids
      * @return
      */
	@RequiresPermissions({"book:dels"})
	@RequestMapping(value="/dels")
  	public String dels(HttpServletRequest request){
    	String ids=request.getParameter("ids");
    	String idArray[]=ids.split(",");
    	int[] idArray1=new int[idArray.length];
    	for(int i=0;i<idArray.length;i++){
    		idArray1[i]=Function.getInt(idArray[i], 0);
    	}
    	bookService.deleteBooks(idArray1);;
  		return "redirect:/book/books";
  	}
}
