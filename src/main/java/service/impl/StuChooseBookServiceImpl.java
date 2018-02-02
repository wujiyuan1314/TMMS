package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import dao.StuChooseBookMapper;
import entity.BookInfo;
import entity.CodeLibrary;
import entity.StuChooseBook;
import exception.ExcelException;
import service.StuChooseBookService;
import util.DateUtil;
import util.JxlExcelUtil;
import util.Page;
@Service
public class StuChooseBookServiceImpl implements StuChooseBookService {
	@Autowired
	StuChooseBookMapper stuChooseBookMapper;
	static String mDriverClass="com.mysql.jdbc.Driver";//mysql驱动
	static String mJdbcUrl="";//mysql数据库链接地址
	static String mDb_user="";//mysql数据库用户名
	static String mDb_password="";//mysql数据库密码
	static JdbcTemplate mracleJdbcTemplate=null;//mysql数据库操作

	@Override
	public List<StuChooseBook> listStuChooseBook(StuChooseBook stuChooseBook, Page page) {
		// TODO Auto-generated method stub
		int totalNumber = stuChooseBookMapper.countStuChooseBook(stuChooseBook);
		page.setTotalNumber(totalNumber);
		List<StuChooseBook> stuChooseBooks = stuChooseBookMapper.listStuChooseBook(stuChooseBook, page);
		return stuChooseBooks;
	}

	@Override
	public int editStuChooseBook(StuChooseBook stuChooseBook) {
		// TODO Auto-generated method stub
		return stuChooseBookMapper.updateByPrimaryKeySelective(stuChooseBook);
	}

	@Override
	public int insertStuChooseBook(StuChooseBook stuChooseBook) {
		// TODO Auto-generated method stub
		return stuChooseBookMapper.insertSelective(stuChooseBook);
	}

	@Override
	public int deleteStuChooseBook(int id) {
		return stuChooseBookMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteStuChooseBooks(int[] ids) {
		for(int i=0;i<ids.length;i++){
			stuChooseBookMapper.deleteByPrimaryKey(ids[i]);
		}

	}

	@Override
	public StuChooseBook getStuChooseBookByID(int id) {
		// TODO Auto-generated method stub
		return stuChooseBookMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public StuChooseBook selectByStuBook(int stuId,int bookId) {
		return stuChooseBookMapper.selectByStuBook(stuId, bookId);
	}
	
	@Override
	public List<StuChooseBook> selectByParams(StuChooseBook stuChooseBook){
		return stuChooseBookMapper.selectByParams(stuChooseBook);
	}
	/**
	 * 批量导出学生选书信息
	 * @param list
	 * @param response
	 */
	@Override
	public void exportSCB(List<StuChooseBook> list,HttpServletResponse response) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> fieldMap=new LinkedHashMap<String, String>();
		fieldMap.put("stuId", "学生ID");
		fieldMap.put("bookId", "图书ID");
		fieldMap.put("classId", "班级ID");
		fieldMap.put("specialtyid", "专业ID");
		fieldMap.put("collegeId", "学院ID");
		try {
			JxlExcelUtil.listToExcel(list, fieldMap, "学生选书信息", response);
		} catch (ExcelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Map<String, Object>> selectBySchool(CodeLibrary codeLibrary){
		//mysql数据库操作
		mJdbcUrl=codeLibrary.getItemname();
		mDb_user=codeLibrary.getExtend1();
		mDb_password=codeLibrary.getExtend2();
		@SuppressWarnings("deprecation")
		DriverManagerDataSource  mdataSource =new DriverManagerDataSource();
		mdataSource.setDriverClassName(mDriverClass);
		mdataSource.setUrl(mJdbcUrl);
		mdataSource.setUsername(mDb_user);;
		mdataSource.setPassword(mDb_password);
		mracleJdbcTemplate=new JdbcTemplate();
	    mracleJdbcTemplate.setDataSource(mdataSource);
	    String sql  = "select * from stu_choose_book" ;
	    List<Map<String, Object>>  list = mracleJdbcTemplate.queryForList(sql);
	    return list;
	}
}
