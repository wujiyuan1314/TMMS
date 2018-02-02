package entity;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class TeacherInfo {
    @Override
	public String toString() {
		return "TeacherInfo [id=" + id + ", teacherNo=" + teacherNo + ", teacherName=" + teacherName + ", teacherSex="
				+ teacherSex + ", mobile=" + mobile + ", teacherPwd=" + teacherPwd + ", collegeId=" + collegeId
				+ ", technicalTitle=" + technicalTitle + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", state=" + state + ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3=" + extend3 + "]";
	}

	private Integer id;

    private String teacherNo;

    private String teacherName;

    private String teacherSex;

    private String mobile;

    private String teacherPwd;

    private String collegeId;

    private String technicalTitle;

    private Date createTime;

    private Date updateTime;

    private Byte state;

    private String extend1;

    private String extend2;

    private String extend3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @NotEmpty(message="教职工号不能为空")
    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo == null ? null : teacherNo.trim();
    }
    @NotEmpty(message="教师名字不能为空")
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }
    @NotEmpty(message="教师性别不能为空")
    public String getTeacherSex() {
        return teacherSex;
    }

    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex == null ? null : teacherSex.trim();
    }
    @NotEmpty(message="手机号不能为空")
    @Pattern(regexp="^1[3|4|5|7|8][0-9]{9}$",message="手机号格式不正确")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTeacherPwd() {
        return teacherPwd;
    }

    public void setTeacherPwd(String teacherPwd) {
        this.teacherPwd = teacherPwd == null ? null : teacherPwd.trim();
    }
    @NotEmpty(message="专所属学院不能为空")
    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId == null ? null : collegeId.trim();
    }

    public String getTechnicalTitle() {
        return technicalTitle;
    }

    public void setTechnicalTitle(String technicalTitle) {
        this.technicalTitle = technicalTitle == null ? null : technicalTitle.trim();
    }

    public Date getCreateTime() {
        return createTime;
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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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