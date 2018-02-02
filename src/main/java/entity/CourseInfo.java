package entity;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class CourseInfo {
    @Override
	public String toString() {
		return "CourseInfo [id=" + id + ", courseNo=" + courseNo + ", courseName=" + courseName + ", courseGrade="
				+ courseGrade + ", courseTerm=" + courseTerm + ", collegeId=" + collegeId + ", specialtyId="
				+ specialtyId + ", courseKind=" + courseKind + ", createtime=" + createtime + ", updatetime="
				+ updatetime + ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3=" + extend3 + "]";
	}

	private Integer id;

    private String courseNo;

    private String courseName;

    private String courseGrade;

    private String courseTerm;

    private String collegeId;

    private String specialtyId;

    private String courseKind;

    private Date createtime;

    private Date updatetime;

    private String extend1;

    private String extend2;

    private String extend3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotEmpty(message="课程号不能为空")
    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo == null ? null : courseNo.trim();
    }
    @NotEmpty(message="课程名字不能为空")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }
    @NotEmpty(message="课程年级不能为空")
    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade == null ? null : courseGrade.trim();
    }
    @NotEmpty(message="学期不能为空")
    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm == null ? null : courseTerm.trim();
    }
    @NotEmpty(message="所属学院不能为空")
    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId == null ? null : collegeId.trim();
    }
    @NotEmpty(message="所属专业不能为空")
    public String getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(String specialtyId) {
        this.specialtyId = specialtyId == null ? null : specialtyId.trim();
    }
    @NotEmpty(message="课程类型不能为空")
    public String getCourseKind() {
        return courseKind;
    }
    public void setCourseKind(String courseKind) {
        this.courseKind = courseKind == null ? null : courseKind.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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