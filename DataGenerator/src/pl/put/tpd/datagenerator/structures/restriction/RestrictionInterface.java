package pl.put.tpd.datagenerator.structures.restriction;

import java.util.List;

public interface RestrictionInterface {
	public String toString();
	public List<Restriction> getAllRestrictions();
	public void getUsedColumns();
	public String getRestrictionString();
}
