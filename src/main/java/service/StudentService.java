package service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import entity.StudentInfo;
import util.Page;

public interface StudentService {
	/**
	 * 根据输入信息条件查询学生列表，并分页显示
	 * @param studentInfo
	 * @param page
	 * @return
	 */
	List<StudentInfo> listStudent(StudentInfo studentInfo, Page page);
	
	/**
	 * 批量导入学生信息
	 * @param file
	 */
	void uploadStudent(File file);
	/**
	 * 批量导入学生信息
	 * @param file
	 */
	void uploadStudentjxl(File file);
	/**
	 * 按条件查询学生信息
	 * @param studentInfo
	 * @return
	 */
	List<StudentInfo> selectByParams(StudentInfo studentInfo);
	/**
	 * 批量导出学生信息
	 * @param list
	 * @param response
	 */
	void exportStudent(List<StudentInfo> list,HttpServletResponse response);
	/**
	 * 修改一位学生信息
	 * @param studentInfo
	 * @return
	 */
	int editStudent(StudentInfo studentInfo);
	/**
	 * 添加一位学生信息
	 * @param bookInfo
	 */
	int insertStudent(StudentInfo studentInfo);
	
	/**
	 * 删除一位学生信息
	 * @param id
	 */
	int deleteStudent(int id);
	
	/**
	 * 批量删除学生信息
	 * @param ids
	 */
	void deleteStudents(int[] ids);
	
	/**
	 * 根据ID获取学生信息
	 * @param id
	 * @return
	 */
	StudentInfo getStudentByID(int id);
	/**
	 * 根据学号查询学生
	 * @param studentNo
	 * @return
	 */
	StudentInfo selectByStuNo(String studentNo);
}
