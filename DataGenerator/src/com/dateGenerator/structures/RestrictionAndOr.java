package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;

public class RestrictionAndOr implements RestrictionInterface {
	private RestrictionInterface restrictionL;
	private RestrictionInterface restrictionR;
	private String type;
	private String restrictionString;

	public RestrictionAndOr(String type, String restrictionString) {
		this.type = type;
		this.restrictionString = restrictionString;
	}

	@Override
	public List<Restriction> getAllRestrictions() {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.addAll(restrictionL.getAllRestrictions());
		restrictions.addAll(restrictionR.getAllRestrictions());
		return restrictions;
	}

	@Override
	public void getUsedColumns() {
		restrictionL.getUsedColumns(); 
		restrictionR.getUsedColumns();
	}

	public RestrictionInterface getRestrictionL() {
		return restrictionL;
	}

	public void setRestrictionL(RestrictionInterface restrictionL) {
		this.restrictionL = restrictionL;
	}

	public RestrictionInterface getRestrictionR() {
		return restrictionR;
	}

	public void setRestrictionR(RestrictionInterface restrictionR) {
		this.restrictionR = restrictionR;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRestrictionString() {
		return restrictionString;
	}

	public void setRestrictionString(String restrictionString) {
		this.restrictionString = restrictionString;
	}

	@Override
	public String toString() {
		return "RestrictionAndOr [restrictionL=" + restrictionL
				+ ", restrictionR=" + restrictionR + ", type=" + type
				+ ", restrictionString=" + restrictionString + "]";
	}

}
