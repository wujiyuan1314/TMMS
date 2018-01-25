package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import entity.StuChooseBook;
import util.Page;

public interface StuChooseBookMapper {
    int deleteByPrimaryKey(int id);

    int insert(StuChooseBook record);

    int insertSelective(StuChooseBook record);

    StuChooseBook selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(StuChooseBook record);

    int updateByPrimaryKey(StuChooseBook record);
    //下面为自定义方法
  	List<StuChooseBook> listStuChooseBook(@Param("stuChooseBook") StuChooseBook stuChooseBook, @Param("page") Page page);
     
  	int countStuChooseBook(StuChooseBook stuChooseBook);
}