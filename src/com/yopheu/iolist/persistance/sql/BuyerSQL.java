package com.yopheu.iolist.persistance.sql;

import com.yopheu.iolist.config.DBContract;

public class BuyerSQL {
	
	public static final String INSERT 
		= " INSERT INTO tbl_buyer(buid, buname, butel, buaddr) "
				+ " VALUES(#{buid}, #{buname}, #{butel}, #{buaddr}) ";

	public static final String UPDATE 
		= " UPDATE " + DBContract.TABLE.BUYER + " tbl_buyer set "
			+ " buName	= #{buName}, "
			+ " buTel	= #{buTel}, "
			+ " buAddr	= #{buAddr} "
			+ " WHERE buId	= #{buId} "; 
}
