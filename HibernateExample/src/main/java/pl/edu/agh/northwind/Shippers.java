package pl.edu.agh.northwind;

// Generated 2014-05-13 06:34:59 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * Shippers generated by hbm2java
 */
public class Shippers implements java.io.Serializable {

	private BigDecimal shipperid;
	private String companyname;
	private String phone;

	public Shippers() {
	}

	public Shippers(String companyname) {
		this.companyname = companyname;
	}

	public Shippers(String companyname, String phone) {
		this.companyname = companyname;
		this.phone = phone;
	}

	public BigDecimal getShipperid() {
		return this.shipperid;
	}

	public void setShipperid(BigDecimal shipperid) {
		this.shipperid = shipperid;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}