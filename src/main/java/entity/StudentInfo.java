package entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class StudentInfo {
    private Integer id;

    private String studentNo;

    private String studentName;

    private String studentSex;

    private String mobile;

    private String studentPwd;

    private String studentGrade;

    private String classId;

    private String specialtyId;

    private String collegeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enterTime;

    private String payStatus;

    private BigDecimal initialAmount;

    private String state;

    private Date createTime;

    private Date updateTime;

    private String extend1;

    private String extend2;

    private String extend3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @NotEmpty(message="学号不能为空")
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo == null ? null : studentNo.trim();
    }
    @NotEmpty(message="学生姓名不能为空")
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }
    @NotEmpty(message="性别不能为空")
    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex == null ? null : studentSex.trim();
    }
    @NotEmpty(message="手机号不能为空")
    @Pattern(regexp="^1[3|4|5|7|8][0-9]{9}$",message="手机号格式不正确")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getStudentPwd() {
        return studentPwd;
    }

    public void setStudentPwd(String studentPwd) {
        this.studentPwd = studentPwd == null ? null : studentPwd.trim();
    }
    @NotEmpty(message="年级不能为空")
    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade == null ? null : studentGrade.trim();
    }
    @NotEmpty(message="班级不能为空")
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }
    @NotEmpty(message="专业不能为空")
    public String getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(String specialtyId) {
        this.specialtyId = specialtyId == null ? null : specialtyId.trim();
    }
    @NotEmpty(message="学院不能为空")
    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId == null ? null : collegeId.trim();
    }
    @NotNull(message="入学时间不能为空")
    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }
    @NotNull(message="初始缴费金额不能为空")
    @DecimalMin("0")
    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    @Override
	public String toString() {
		return "StudentInfo [id=" + id + ", studentNo=" + studentNo + ", studentName=" + studentName + ", studentSex="
				+ studentSex + ", mobile=" + mobile + ", studentPwd=" + studentPwd + ", studentGrade=" + studentGrade
				+ ", classId=" + classId + ", specialtyId=" + specialtyId + ", collegeId=" + collegeId + ", enterTime="
				+ enterTime + ", payStatus=" + payStatus + ", initialAmount=" + initialAmount + ", state=" + state
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", extend1=" + extend1 + ", extend2="
				+ extend2 + ", extend3=" + extend3 + "]";
	}

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1 == null ? null : extend1.trim();
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2 == null ? null : extend2.trim();
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3 == null ? null : extend3.trim();
    }
}