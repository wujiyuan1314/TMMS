package entity;

import java.util.Date;

public class StuChooseBook {
    @Override
	public String toString() {
		return "StuChooseBook [id=" + id + ", stuId=" + stuId + ", bookId=" + bookId + ", createTime=" + createTime
				+ ", updataTime=" + updataTime + ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3="
				+ extend3 + ", extend4=" + extend4 + ", classId=" + classId + ", specialtyId=" + specialtyId
				+ ", collegeId=" + collegeId + ", extend5=" + extend5 + ", extend6=" + extend6 + ", extend7=" + extend7
				+ ", extend8=" + extend8 + "]";
	}

	private Integer id;

    private Integer stuId;

    private Integer bookId;

    private Date createTime;

    private Date updataTime;

    private String extend1;

    private String extend2;

    private String extend3;

    private String extend4;

    private String classId;

    private String specialtyId;

    private String collegeId;

    private String extend5;

    private String extend6;

    private String extend7;

    private String extend8;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
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

    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4 == null ? null : extend4.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public String getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(String specialtyId) {
        this.specialtyId = specialtyId == null ? null : specialtyId.trim();
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId == null ? null : collegeId.trim();
    }

    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5 == null ? null : extend5.trim();
    }

    public String getExtend6() {
        return extend6;
    }

    public void setExtend6(String extend6) {
        this.extend6 = extend6 == null ? null : extend6.trim();
    }

    public String getExtend7() {
        return extend7;
    }

    public void setExtend7(String extend7) {
        this.extend7 = extend7 == null ? null : extend7.trim();
    }

    public String getExtend8() {
        return extend8;
    }

    public void setExtend8(String extend8) {
        this.extend8 = extend8 == null ? null : extend8.trim();
    }
}