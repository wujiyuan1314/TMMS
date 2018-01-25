package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.StuChooseBookMapper;
import entity.BookInfo;
import entity.StuChooseBook;
import service.StuChooseBookService;
import util.Page;
@Service
public class StuChooseBookServiceImpl implements StuChooseBookService {
	@Autowired
	StuChooseBookMapper stuChooseBookMapper;
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

}
