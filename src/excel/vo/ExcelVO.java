package excel.vo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ExcelVO {
	
	private String title;
	private String author;
	private String company;
	private String isbn;
	private String imgUrl;
	
	public ExcelVO() {
		
	}

	public ExcelVO(String title, String author, String company, String isbn, String imgUrl) {
		super();
		this.title = title;
		this.author = author;
		this.company = company;
		this.isbn = isbn;
		this.imgUrl = imgUrl;
	}
	
	public ExcelVO(String title,String author, String company) {
		super();
		this.title = title;
		this.author = author;
		this.company = company;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "ExcelVO [title=" + title + ", author=" + author + ", company=" + company + ", isbn=" + isbn
				+ ", imgUrl=" + imgUrl + "]";
	}

}
