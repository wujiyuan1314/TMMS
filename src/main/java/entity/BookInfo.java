package entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
@Repository
public class BookInfo {
    private Integer id;

    private String bookName;

    private String bookKind;

    private String bookAuthor;

    private String bookIsbn;

    private String bookPublish;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bookPublishTime;

    private BigDecimal bookPrice;

    private String bookIntro;

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
    @NotEmpty(message="书名不能为空")
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }
    @NotEmpty(message="类型不能为空")
    public String getBookKind() {
        return bookKind;
    }

    public void setBookKind(String bookKind) {
        this.bookKind = bookKind == null ? null : bookKind.trim();
    }
    @NotEmpty(message="作者不能为空")
    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor == null ? null : bookAuthor.trim();
    }
    @NotEmpty(message="ISBN不能为空")
    @Pattern(regexp="^(\\d[- ]*){9}[\\dxX]$",message="ISBN的格式不正确")
    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn == null ? null : bookIsbn.trim();
    }
    @NotEmpty(message="出版社不能为空")
    public String getBookPublish() {
        return bookPublish;
    }

    public void setBookPublish(String bookPublish) {
        this.bookPublish = bookPublish == null ? null : bookPublish.trim();
    }
    @NotNull(message="出版时间不能为空")
    public Date getBookPublishTime() {
        return bookPublishTime;
    }

    public void setBookPublishTime(Date bookPublishTime) {
        this.bookPublishTime = bookPublishTime;
    }
    @NotNull(message="价格不能为空")
    @DecimalMin("0")
    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro == null ? null : bookIntro.trim();
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

	@Override
	public String toString() {
		return "BookInfo [id=" + id + ", bookName=" + bookName + ", bookKind=" + bookKind + ", bookAuthor=" + bookAuthor
				+ ", bookIsbn=" + bookIsbn + ", bookPublish=" + bookPublish + ", bookPublishTime=" + bookPublishTime
				+ ", bookPrice=" + bookPrice + ", bookIntro=" + bookIntro + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3="
				+ extend3 + "]";
	}
    
    
}