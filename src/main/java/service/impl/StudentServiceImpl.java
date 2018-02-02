package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.StudentInfoMapper;
import dao.TmmsUserMapper;
import entity.StudentInfo;
import entity.TmmsUser;
import exception.ExcelException;
import service.StudentService;
import util.DateUtil;
import util.JxlExcelUtil;
import util.Page;
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentInfoMapper studentInfoMapper;
	@Autowired
	TmmsUserMapper tmmsUserMapper;
	/**
	 * 根据输入信息条件查询学生列表，并分页显示
	 * @param bookInfo
	 * @param page
	 * @return
	 */
	@Override
	public List<StudentInfo> listStudent(StudentInfo studentInfo, Page page) {
		int totalNumber = studentInfoMapper.countStudent(studentInfo);
		page.setTotalNumber(totalNumber);
		List<StudentInfo> students = studentInfoMapper.listStudent(studentInfo, page);
		return students;
	}
	/**
	 * 批量导入学生信息
	 * @param file
	 */
	@Override
	public void uploadStudent(File file) {
		// TODO Auto-generated method stub
		List<StudentInfo> list=new ArrayList<StudentInfo>();//存放每条数据的List
		try {
			FileInputStream is = new FileInputStream(file);
			HSSFWorkbook wbs = new HSSFWorkbook(is);//读取excel表格
			HSSFSheet childSheet = wbs.getSheetAt(0);//获取改excel中第一个sheet
			if(childSheet.getLastRowNum()>1){
				for (int i = 1; i <= childSheet.getLastRowNum(); i++) {//从1开始，排除表头
					HSSFRow row = childSheet.getRow(i);//获取第i行数据
					StudentInfo studentInfo=new StudentInfo();
					if(row!=null){
						Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
						studentInfo.setCreateTime(createtime);//创建时间
						studentInfo.setUpdateTime(createtime);//更新时间(刚导入时创建时间和更新时间一致)
						for(int j=0;j<row.getLastCellNum();j++){
							HSSFCell cell=row.getCell((short)j,Row.RETURN_NULL_AND_BLANK);//获取第i行第j列数据
							if (null != cell) {
	                            switch (cell.getCellType()) {   
	                            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
	                            	String value = "";
	                            	if(HSSFDateUtil.isCellDateFormatted(cell)){
	                            		double d = cell.getNumericCellValue();
	                            		Date date = HSSFDateUtil.getJavaDate(d);
	                            		value = DateUtil.format(date);
	                            	}else{
	                            		value = String.valueOf(cell.getNumericCellValue());
	                            	}
	                            	if(j==0){//学号
	                            		studentInfo.setStudentNo(value.substring(0,value.indexOf(".")));
	                            	}else if(j==1){//学生姓名
	                            		studentInfo.setStudentName(value.substring(0,value.indexOf(".")));
	                            	}else if(j==2){//性别
	                            		studentInfo.setStudentSex(value.substring(0,value.indexOf(".")));
	                            	}else if(j==3){//手机号
	                            		studentInfo.setMobile(value.substring(0,value.indexOf(".")));
	                            	}else if(j==4){//密码
	                            		studentInfo.setStudentPwd(value.substring(0,value.indexOf(".")));
	                            	}else if(j==5){//年级
	                            		studentInfo.setStudentGrade(value.substring(0,value.indexOf(".")));
	                            	}else if(j==6){//班级
	                            		studentInfo.setClassId(value.substring(0,value.indexOf(".")));
	                            	}else if(j==7){//专业
	                            		studentInfo.setSpecialtyId(value.substring(0,value.indexOf(".")));
	                            	}else if(j==8){//学院
	                            		studentInfo.setCollegeId(value.substring(0,value.indexOf(".")));
	                            	}else if(j==9){//入学时间
	                            		studentInfo.setEnterTime(DateUtil.parseDate(value));
	                            	}else if(j==10){//付款状态
	                            		studentInfo.setPayStatus(value.substring(0,value.indexOf(".")));
	                            	}else if(j==11){//初始缴费金额
	                            		studentInfo.setInitialAmount(BigDecimal.valueOf(Double.parseDouble(value.substring(0,value.indexOf(".")))));
	                            	}else if(j==12){//状态(0:在校 1:毕业)
	                            		studentInfo.setState(value);
	                            	}
	                                break;   
	                            case HSSFCell.CELL_TYPE_STRING: // 字符串
	                            	String value2 = "";
	                        		value2 = cell.getStringCellValue();
	                        		if(j==0){//学号
	                            		studentInfo.setStudentNo(value2);
	                            	}else if(j==1){//学生姓名
	                            		studentInfo.setStudentName(value2);
	                            	}else if(j==2){//性别
	                            		studentInfo.setStudentSex(value2);
	                            	}else if(j==3){//手机号
	                            		studentInfo.setMobile(value2);
	                            	}else if(j==4){//密码
	                            		studentInfo.setStudentPwd(value2);
	                            	}else if(j==5){//年级
	                            		studentInfo.setStudentGrade(value2);
	                            	}else if(j==6){//班级
	                            		studentInfo.setClassId(value2);
	                            	}else if(j==7){//专业
	                            		studentInfo.setSpecialtyId(value2);
	                            	}else if(j==8){//学院
	                            		studentInfo.setCollegeId(value2);
	                            	}else if(j==9){//入学时间
	                            		studentInfo.setEnterTime(DateUtil.parseDate(value2));
	                            	}else if(j==10){//付款状态
	                            		studentInfo.setPayStatus(value2);
	                            	}else if(j==11){//初始缴费金额
	                            		studentInfo.setInitialAmount(BigDecimal.valueOf(Double.parseDouble(value2)));
	                            	}else if(j==12){//状态(0:在校 1:毕业)
	                            		studentInfo.setState(value2);
	                            	}
	                                break; 
	                            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean   
	                                //System.out.println("Boolean："+cell.getBooleanCellValue()   + "   ");   
	                                break;   
	                            case HSSFCell.CELL_TYPE_FORMULA: // 公式   
	                               // System.out.print("公式："+cell.getCellFormula() + "   ");   
	                                break;   
	                            case HSSFCell.CELL_TYPE_BLANK: // 空值   
	                                //System.out.println(" 空值 ");   
	                                break;   
	                            case HSSFCell.CELL_TYPE_ERROR: // 故障   
	                               // System.out.println("故障 ");   
	                                break;   
	                            default:   
	                                System.out.print("未知类型   ");   
	                                break;   
	                            }   
	                        }else{
	                        	 continue;
	                        } 
						}
					}else{
						continue;
					}
				    list.add(studentInfo);//list中加入一条数据
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(list.size()>0){
				for(StudentInfo studentInfo:list){
					TmmsUser tmmsUser=new TmmsUser();
					tmmsUser.setUsername(studentInfo.getStudentNo());
					tmmsUser.setPassword("123456");
					tmmsUser.setState((byte)1);
					tmmsUser.setRoleId(1);
					Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
					tmmsUser.setCreateTime(createtime);
					tmmsUser.setUpdateTime(createtime);
					tmmsUserMapper.insert(tmmsUser);
				}
				studentInfoMapper.insertStudentInfoBatch(list);//批量插入数据
			}
		}
	}
	/**
	 * 添加一位学生信息
	 * @param bookInfo
	 */
	@Override
	public int insertStudent(StudentInfo studentInfo) {
		TmmsUser tmmsUser=new TmmsUser();
		tmmsUser.setUsername(studentInfo.getStudentNo());
		tmmsUser.setPassword("123456");
		tmmsUser.setState((byte)1);
		tmmsUser.setRoleId(1);
		Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
		tmmsUser.setCreateTime(createtime);
		tmmsUser.setUpdateTime(createtime);
		tmmsUserMapper.insert(tmmsUser);
		return studentInfoMapper.insertSelective(studentInfo);

	}
	/**
	 * 修改一位学生信息
	 * @param studentInfo
	 * @return
	 */
    @Override
    public int editStudent(StudentInfo studentInfo) {
    	// TODO Auto-generated method stub
    	return studentInfoMapper.updateByPrimaryKey(studentInfo);
    }
    /**
	 * 删除一位学生信息
	 * @param id
	 */
	@Override
	public int deleteStudent(int id) {
		// TODO Auto-generated method stub
		return studentInfoMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除学生信息
	 * @param ids
	 */
	@Override
	public void deleteStudents(int[] ids) {
		// TODO Auto-generated method stub
		for(int i=0;i<ids.length;i++){
			studentInfoMapper.deleteByPrimaryKey(ids[i]);
		}
	}

	/**
	 * 根据ID获取学生信息
	 * @param id
	 * @return
	 */
	@Override
	public StudentInfo getStudentByID(int id) {
		// TODO Auto-generated method stub
		return studentInfoMapper.selectByPrimaryKey(id);
	}
	/**
	 * 批量导入学生信息
	 * @param file
	 */
	@Override
	public void uploadStudentjxl(File file) {
		// TODO Auto-generated method stub
		List<StudentInfo> list=new ArrayList<StudentInfo>();//存放每条数据的List
		try {
			InputStream in = new FileInputStream(file);
			LinkedHashMap<String, String> fieldMap=new LinkedHashMap<String, String>();
			fieldMap.put("学号", "studentNo");
			fieldMap.put("学生姓名", "studentName");
			fieldMap.put("性别", "studentSex");
			fieldMap.put("手机号", "mobile");
			fieldMap.put("年级", "studentGrade");
			fieldMap.put("班级", "classId");
			fieldMap.put("专业", "specialtyId");
			fieldMap.put("学院", "collegeId");
			fieldMap.put("入学时间", "enterTime");
			fieldMap.put("付款状态", "payStatus");
			fieldMap.put("初始缴费金额", "initialAmount");
			fieldMap.put("状态(0:在校 1:毕业)", "state");
			String uniqueFields[]={"学号"};
			list=JxlExcelUtil.excelToList(in, "Sheet1", StudentInfo.class, fieldMap, uniqueFields);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(StudentInfo studentinfo:list){
			Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
			studentinfo.setStudentPwd("123456");//学生密码
			studentinfo.setCreateTime(createtime);//创建时间
			studentinfo.setUpdateTime(createtime);//更新时间(刚导入时创建时间和更新时间一致)
		}
		if(list.size()>0){
			studentInfoMapper.insertStudentInfoBatch(list);//批量插入数据
		}
	}
	/**
	 * 按条件查询学生信息
	 * @param studentInfo
	 * @return
	 */
	@Override
	public List<StudentInfo> selectByParams(StudentInfo studentInfo) {
		// TODO Auto-generated method stub
		return studentInfoMapper.selectByParams(studentInfo);
	}
	/**
	 * 批量导出学生信息
	 * @param list
	 * @param response
	 */
	@Override
	public void exportStudent(List<StudentInfo> list, HttpServletResponse response) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> fieldMap=new LinkedHashMap<String, String>();
		fieldMap.put("studentNo", "学号");
		fieldMap.put("studentName","学生姓名");
		fieldMap.put("studentSex", "性别");
		fieldMap.put("mobile", "手机号");
		fieldMap.put("studentPwd", "密码");
		fieldMap.put("studentGrade", "年级");
		fieldMap.put("classId", "班级");
		fieldMap.put("specialtyId", "专业");
		fieldMap.put("collegeId", "学院");
		fieldMap.put("enterTime", "入学时间");
		fieldMap.put("payStatus", "付款状态");
		fieldMap.put("initialAmount", "初始缴费金额");
		fieldMap.put("state", "状态(0:在校 1:毕业)");
		fieldMap.put("createTime", "创建时间");
		fieldMap.put("updateTime", "更新时间");
		try {
			JxlExcelUtil.listToExcel(list, fieldMap, "学生信息", response);
		} catch (ExcelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public StudentInfo selectByStuNo(String studentNo){
		return studentInfoMapper.selectByStuNo(studentNo);
	}
}
