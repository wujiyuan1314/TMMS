package service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import entity.CodeLibrary;
import entity.StuChooseBook;
import util.Page;

public interface StuChooseBookService {
	/**
	 * 根据输入信息条件查询学生选书列表，并分页显示
	 * @param bookInfo
	 * @param page
	 * @return
	 */
	List<StuChooseBook> listStuChooseBook(StuChooseBook stuChooseBook, Page page);
	/**
	 * 修改一本学生选书
	 * @param StuChooseBook
	 */
	int editStuChooseBook(StuChooseBook stuChooseBook);
	/**
	 * 添加一本学生选书
	 * @param StuChooseBook
	 */
	int insertStuChooseBook(StuChooseBook stuChooseBook);
	
	/**
	 * 删除一本学生选书
	 * @param id
	 */
	int deleteStuChooseBook(int id);
	
	/**
	 * 批量删除学生选书
	 * @param ids
	 */
	void deleteStuChooseBooks(int[] ids);
	/**
	 * 根据ID获取学生选书信息
	 * @param id
	 * @return
	 */
	StuChooseBook getStuChooseBookByID(int id);
	/**
	 * 判断该名学生是否选过该书
	 * @param stuId
	 * @param bookId
	 * @return
	 */
	StuChooseBook selectByStuBook(int stuId,int bookId);
	/**
	 * 批量导出学生选书信息
	 * @param list
	 * @param response
	 */
	void exportSCB(List<StuChooseBook> list,HttpServletResponse response);
	/**
	 * 按条件查找全部
	 * @param stuId
	 * @param bookId
	 * @return
	 */
	List<StuChooseBook> selectByParams(StuChooseBook stuChooseBook);
	/**
	 * 查找某一学校的选书信息
	 * @param stuId
	 * @param bookId
	 * @return
	 */
	List<Map<String, Object>> selectBySchool(CodeLibrary codeLibrary);
}
