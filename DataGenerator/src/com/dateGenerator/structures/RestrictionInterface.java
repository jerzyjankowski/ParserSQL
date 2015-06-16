package com.dateGenerator.structures;

import java.util.List;

public interface RestrictionInterface {
	public String toString();
	public List<Restriction> getAllRestrictions();
	public void getUsedColumns();
}
