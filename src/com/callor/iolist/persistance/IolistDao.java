package com.callor.iolist.persistance;

import java.util.List;
/*
*  1. 판매내역 등록(장바구니 담기)
*  2. 장바구니 전체 리스트
*  3. 기간별 리스트
*  4. 고객별 리스트
*  5. 상품별 리스트
*  6. 고객 + 거래기간별 리스트
*  */

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.callor.iolist.config.DBContract;
import com.callor.iolist.models.IolistDto;
public interface IolistDao {
	
	@Select(" SELECT * FROM " 
			+ DBContract.TABLE.IOLIST 
			+ " ORDER BY iodate, iotime ")
	public List<IolistDto> selectAll();
	
	/*
	 * mybatis 의 Query Method 에 값을 전달할때
	 * 가능하면 Dto(VO)에 데이터를 담아서 전달하라
	 * 만약 불가피하게 일반 변수(int, String 등등)를 사용하여
	 * 전달할때 1개만 전달할때는 문제가 없다
	 * 하지만 2개이상 전달할때는 반드시 @Param() Annotation 을 사용하여
	 * 변수이름도 함께 전달해 주어야 한다
	 */
	// 기간별리스트
	@Select(" SELECT * FROM " 
			+ DBContract.TABLE.IOLIST 
			+ " WHERE iodate BETWEEN #{sDate} AND #{eDate} "
			+ " ORDER BY iodate, iotime ")
	public List<IolistDto> selectBetwenDate(@Param("sDate") String sDate, @Param("eDate") String eDate);
	
	// 고객별 리스트
	@Select(" SELECT * FROM " 
			+ DBContract.TABLE.IOLIST 
			+ " WHERE iobuid = #{buId} "
			+ " ORDER BY iodate, iotime ")
	public List<IolistDto> selectByBuyer(String buId);
	
	// 상품별 리스트
	@Select(" SELECT * FROM " 
			+ DBContract.TABLE.IOLIST 
			+ " WHERE iopcode = #{pCode} "
			+ " ORDER BY iodate, iotime ")
	public List<IolistDto> selectByProduct(String pCode);
	
	// 고객 + 기간별 리스트
	@Select(" SELECT * FROM " 
			+ DBContract.TABLE.IOLIST 
			+ " WHERE iobuid = #{buid} "
			+ " AND iodate BETWEEN #{sDate} AND #{eDate} "
			+ " ORDER BY iodate, iotime ")
	public List<IolistDto> selectByBuyerBetweenDate(String buId, @Param("sDate") String sDate, @Param("eDate") String eDate);

	// 상품 + 기간별 리스트
	@Select(" SELECT * FROM " 
			+ DBContract.TABLE.IOLIST 
			+ " WHERE iopcode = #{pCode} "
			+ " AND iodate BETWEEN #{sDate} AND #{eDate} "
			+ " ORDER BY iodate, iotime ")
	public List<IolistDto> selectByProductBetweenDate(String pCode, @Param("sDate") String sDate, @Param("eDate") String eDate);
	
	@Insert("INSERT INTO " 
			+ DBContract.TABLE.IOLIST
			+ " ( ioDate, ioTime, ioBuId, ioPCode, ioQuan, ioPrice, ioTotal ) "
			+ " VALUES "
			+ " ( #{ioDate}, #{ioTime}, #{ioBuId}, #{ioPCode}, #{ioQuan}, #{ioPrice}, #{ioTotal} ) ")
	public int insert(IolistDto iolistDto);

	
	
}
