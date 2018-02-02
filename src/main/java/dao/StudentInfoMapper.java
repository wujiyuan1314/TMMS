package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.StudentInfo;
import util.Page;

public interface StudentInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentInfo record);

    int insertSelective(StudentInfo record);

    StudentInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentInfo record);

    int updateByPrimaryKey(StudentInfo record);
    // 下面为自定义方法
 	List<StudentInfo> listStudent(@Param("studentInfo") StudentInfo studentInfo, @Param("page") Page page);
 	//按条件查询学生信息
    List<StudentInfo> selectByParams(@Param("studentInfo") StudentInfo studentInfo);
    
 	int countStudent(StudentInfo studentInfo);
 	//批量插入学生信息
 	void insertStudentInfoBatch(List<StudentInfo> list);
 	
 	StudentInfo selectByStuNo(String studentNo);
}