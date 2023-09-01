package ar.com.jsuper.dto;

import java.util.Date;

public class AfipDTO {

	private String afipTokenTest;
	private String afipSignTest;
	private String afipToken;
	private String afipSign;
	private Date afipFechaInitToken;
	private Date afipFechaEndToken;
	private Date afipFechaInitTokenTest;
	private Date afipFechaEndTokenTest;
	private String cuitEmpresa;

	public AfipDTO() {
	}

	public String getAfipToken() {
		return afipToken;
	}

	public void setAfipToken(String afipToken) {
		this.afipToken = afipToken;
	}

	public String getAfipSign() {
		return afipSign;
	}

	public void setAfipSign(String afipSign) {
		this.afipSign = afipSign;
	}

	public Date getAfipFechaInitToken() {
		return afipFechaInitToken;
	}

	public void setAfipFechaInitToken(Date afipFechaInitToken) {
		this.afipFechaInitToken = afipFechaInitToken;
	}

	public Date getAfipFechaEndToken() {
		return afipFechaEndToken;
	}

	public void setAfipFechaEndToken(Date afipFechaEndToken) {
		this.afipFechaEndToken = afipFechaEndToken;
	}

	public String getCuitEmpresa() {
		return cuitEmpresa;
	}

	public void setCuitEmpresa(String cuitEmpresa) {
		this.cuitEmpresa = cuitEmpresa;
	}

	public Date getAfipFechaInitTokenTest() {
		return afipFechaInitTokenTest;
	}

	public void setAfipFechaInitTokenTest(Date afipFechaInitTokenTest) {
		this.afipFechaInitTokenTest = afipFechaInitTokenTest;
	}

	public Date getAfipFechaEndTokenTest() {
		return afipFechaEndTokenTest;
	}

	public void setAfipFechaEndTokenTest(Date afipFechaEndTokenTest) {
		this.afipFechaEndTokenTest = afipFechaEndTokenTest;
	}

	public String getAfipTokenTest() {
		return afipTokenTest;
	}

	public void setAfipTokenTest(String afipTokenTest) {
		this.afipTokenTest = afipTokenTest;
	}

	public String getAfipSignTest() {
		return afipSignTest;
	}

	public void setAfipSignTest(String afipSignTest) {
		this.afipSignTest = afipSignTest;
	}

}
