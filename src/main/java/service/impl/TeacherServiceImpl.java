package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

import dao.TeacherInfoMapper;
import dao.TmmsUserMapper;
import entity.StudentInfo;
import entity.TeacherInfo;
import entity.TmmsUser;
import exception.ExcelException;
import service.TeacherService;
import util.DateUtil;
import util.JxlExcelUtil;
import util.Page;
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	TeacherInfoMapper teacherInfoMapper;
	@Autowired
	TmmsUserMapper tmmsUserMapper;
	/**
	 * 根据输入信息条件查询教师信息列表，并分页显示
	 * @param teacherInfo
	 * @param page
	 * @return
	 */
	@Override
	public List<TeacherInfo> listTeacher(TeacherInfo teacherInfo, Page page) {
		int totalNumber = teacherInfoMapper.countTeacher(teacherInfo);
		page.setTotalNumber(totalNumber);
		List<TeacherInfo> teachers = teacherInfoMapper.listTeacher(teacherInfo, page);
		return teachers;
	}
	/**
	 * 批量导入教师信息信息
	 * @param file
	 
	@Override
	public void uploadTeacher(File file) {
		// TODO Auto-generated method stub
		List<TeacherInfo> list=new ArrayList<TeacherInfo>();//存放每条数据的List
		try {
			FileInputStream is = new FileInputStream(file);
			HSSFWorkbook wbs = new HSSFWorkbook(is);//读取excel表格
			HSSFSheet childSheet = wbs.getSheetAt(0);//获取该excel中第一个sheet
			if(childSheet.getLastRowNum()>1){
				for (int i = 1; i <= childSheet.getLastRowNum(); i++) {//从1开始，排除表头
					HSSFRow row = childSheet.getRow(i);//获取第i行数据
					TeacherInfo teacherinfo=new TeacherInfo();
					if(row!=null){
						Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
						teacherinfo.setCreateTime(createtime);//创建时间
						teacherinfo.setUpdateTime(createtime);//更新时间(刚导入时创建时间和更新时间一致)
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
	                            	if(j==0){//书名
	                            		teacherinfo.setTeacherName(value.substring(0,value.indexOf(".")));
	                            	}else if(j==1){//书的类型
	                            		teacherinfo.setTeacherKind(value.substring(0,value.indexOf(".")));
	                            	}else if(j==2){//作者
	                            		teacherinfo.setTeacherAuthor(value.substring(0,value.indexOf(".")));
	                            	}else if(j==3){//ISBN
	                            		teacherinfo.setTeacherIsbn(value.substring(0,value.indexOf(".")));
	                            	}else if(j==4){//出版社
	                            		teacherinfo.setTeacherPublish(value.substring(0,value.indexOf(".")));
	                            	}else if(j==5){//出版时间
	                            		teacherinfo.setTeacherPublishTime(DateUtil.parseDate(value));
	                            	}else if(j==6){//价格
	                            		teacherinfo.setTeacherPrice(BigDecimal.valueOf(Double.parseDouble(value.substring(0,value.indexOf(".")))));
	                            	}else if(j==7){//介绍
	                            		teacherinfo.setTeacherIntro(value.substring(0,value.indexOf(".")));
	                            	}
	                                break;   
	                            case HSSFCell.CELL_TYPE_STRING: // 字符串
	                            	String value2 = "";
	                            		value2 = cell.getStringCellValue();
	                            		if(j==0){//书名
	                                		teacherinfo.setTeacherName(value2);
	                                	}else if(j==1){//书的类型
	                                		teacherinfo.setTeacherKind(value2);
	                                	}else if(j==2){//作者
	                                		teacherinfo.setTeacherAuthor(value2);
	                                	}else if(j==3){//ISBN
	                                		teacherinfo.setTeacherIsbn(value2);
	                                	}else if(j==4){//出版社
	                                		teacherinfo.setTeacherPublish(value2);
	                                	}else if(j==5){//出版时间
	                                		teacherinfo.setTeacherPublishTime(DateUtil.parseDate(value2));
	                                	}else if(j==6){//价格
	                                		teacherinfo.setTeacherPrice(BigDecimal.valueOf(Double.parseDouble(value2)));
	                                	}else if(j==7){//介绍
	                                		teacherinfo.setTeacherIntro(value2);
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
				    list.add(teacherinfo);//list中加入一条数据
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
				teacherInfoMapper.insertTeacherBatch(list);//批量插入数据
			}
		}
	}*/
	/**
	 * 批量导入教师信息
	 * @param file
	 */
	@Override
	public void uploadTeacherjxl(File file){//使用jxl导入excel
		List<TeacherInfo> list=new ArrayList<TeacherInfo>();//存放每条数据的List
		try {
			InputStream in = new FileInputStream(file);
			LinkedHashMap<String, String> fieldMap=new LinkedHashMap<String, String>();
			fieldMap.put("教师职工号", "teacherNo");
			fieldMap.put("教师名字", "teacherName");
			fieldMap.put("性别", "teacherSex");
			fieldMap.put("手机号", "mobile");
			fieldMap.put("院系", "collegeId");
			fieldMap.put("职称", "technicalTitle");
			fieldMap.put("状态", "state");
			String uniqueFields[]={"教师信息职工号"};
			list=JxlExcelUtil.excelToList(in, "Sheet1", TeacherInfo.class, fieldMap, uniqueFields);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(TeacherInfo teacherinfo:list){
			Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
			teacherinfo.setTeacherPwd("123456");//密码
			teacherinfo.setCreateTime(createtime);//创建时间
			teacherinfo.setUpdateTime(createtime);//更新时间(刚导入时创建时间和更新时间一致)
		}
		if(list.size()>0){
			for(TeacherInfo teacherInfo:list){
				TmmsUser tmmsUser=new TmmsUser();
				tmmsUser.setUsername(teacherInfo.getTeacherNo());
				tmmsUser.setPassword("123456");
				tmmsUser.setState((byte)1);
				tmmsUser.setRoleId(4);
				Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
				tmmsUser.setCreateTime(createtime);
				tmmsUser.setUpdateTime(createtime);
				tmmsUserMapper.insert(tmmsUser);
			}
			teacherInfoMapper.insertTeacherBatch(list);//批量插入数据
		}
	}
	/**
	 * 按条件查询教师信息信息
	 * @param teacherInfo
	 */
	@Override
	public List<TeacherInfo> selectByParams(TeacherInfo teacherInfo){
		return teacherInfoMapper.selectByParams(teacherInfo);
	}
	/**
	 * 批量导出教师信息信息
	 * @param list
	 * @param response
	 */
	@Override
	public void exportTeacher(List<TeacherInfo> list,HttpServletResponse response) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> fieldMap=new LinkedHashMap<String, String>();
		fieldMap.put("teacherNo", "教师职工号");
		fieldMap.put("teacherName", "教师名字");
		fieldMap.put("teacherSex", "性别");
		fieldMap.put("mobile", "手机号");
		fieldMap.put("collegeId", "院系");
		fieldMap.put("technicalTitle", "职称");
		fieldMap.put("state", "状态");
		fieldMap.put("createTime", "创建时间");
		fieldMap.put("updateTime", "更新时间");
		try {
			JxlExcelUtil.listToExcel(list, fieldMap, "教师信息信息", response);
		} catch (ExcelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 添加一位教师信息
	 * @param teacherInfo
	 */
	@Override
	public int insertTeacher(TeacherInfo teacherInfo) {
		TmmsUser tmmsUser=new TmmsUser();
		tmmsUser.setUsername(teacherInfo.getTeacherNo());
		tmmsUser.setPassword("123456");
		tmmsUser.setState((byte)1);
		tmmsUser.setRoleId(4);
		Date createtime=DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());//创建时间
		tmmsUser.setCreateTime(createtime);
		tmmsUser.setUpdateTime(createtime);
		tmmsUserMapper.insert(tmmsUser);
		return teacherInfoMapper.insertSelective(teacherInfo);

	}
	/**
	 * 修改一位教师信息
	 * @param teacherInfo
	 */
    @Override
    public int editTeacher(TeacherInfo teacherInfo) {
    	// TODO Auto-generated method stub
    	return teacherInfoMapper.updateByPrimaryKeySelective(teacherInfo);
    }

	/**
	 * 删除一位教师信息
	 * @param id
	 */
	@Override
	public int deleteTeacher(int id) {
		// TODO Auto-generated method stub
		return teacherInfoMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 批量删除教师信息
	 * @param ids
	 */
	@Override
	public void deleteTeachers(int[] ids) {
		// TODO Auto-generated method stub
		for(int i=0;i<ids.length;i++){
			teacherInfoMapper.deleteByPrimaryKey(ids[i]);
		}
	}
	/**
	 * 根据ID获取教师信息
	 * @param id
	 * @return
	 */
	@Override
	public TeacherInfo getTeacherByID(int id) {
		// TODO Auto-generated method stub
		return teacherInfoMapper.selectByPrimaryKey(id);
	}
}
