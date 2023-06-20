package com.callor.iolist.service.impl;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.callor.iolist.config.DBConnection;
import com.callor.iolist.config.HelpMessage;
import com.callor.iolist.config.Line;
import com.callor.iolist.models.ProductDto;
import com.callor.iolist.persistance.ProductDao;
import com.callor.iolist.service.ProductService;

public class ProductServiceImplV1 implements ProductService{

	protected ProductDao productDao;
	protected Scanner scan;
	
	public ProductServiceImplV1() {
		productDao = DBConnection.getSessionFactory().openSession(true).getMapper(ProductDao.class);
		scan = new Scanner(System.in);
	}
	
	@Override
	public void insert() {
		ProductDto productDto = null;
		while(true) {
			HelpMessage.CONFIRM("상품정보를 확인합니다\n"+
		"상품코드를 입력해주세요 QUIT : 종료", "상품코드 >> ");
			String strPCode = scan.nextLine();
			if(strPCode.isBlank()){
				HelpMessage.ERROR("상품코드를 입력해 주세요 ");
				continue;
			}
			if(strPCode.equals("QUIT")) return;
			productDto = productDao.findById(strPCode);
			
			if(productDto == null) {
				productDto = new ProductDto();
				productDto.pCode = strPCode;
			}else if(productDto != null) {
				String message = String.format("이미 등록된 상품 입니다\n"
						+ "\t상품이름 : %s\n"
						+ "\t매입단가 : %d", 
						productDto.pName,
						productDto.getIPrice());
				String prompt = "상품정보를 수정할까요? (Y:수정) >> ";
				HelpMessage.CONFIRM(message, prompt);
				String yesNo = scan.nextLine();
				if(!yesNo.equalsIgnoreCase("Y")) continue;
			}
			break;
		} // 상품코드 입력 while end
		HelpMessage.ALERT("입력한 상품코드 : " + productDto.pCode);
		
		// 상품명 입력받기
		while(true) {
			String strName = this.inputValue("상품명", productDto.pName);
			if(strName.equals("QUIT")) return;
			else if(strName.equals("RE")) continue;
			else if(productDto.pName == null && strName.isEmpty()) {
				HelpMessage.ERROR("상품명은 반드시 입력해야 합니다");
				continue;
			}
			break;
		}
		HelpMessage.ALERT("입력받은 상품명 : " + productDto.pName);
		
		// 품목 입력받기
		while(true) {
			String strItem = this.inputValue("품목명", productDto.pItem);
			if(strItem.equals("QUIT")) return;
			else if(strItem.equals("RE")) continue;
			else if(productDto.pItem == null && strItem.isEmpty()) {
				HelpMessage.ERROR("품목명은 반드시 입력해야 합니다");
				continue;
			}
			break;
		}
		HelpMessage.ALERT("입력받은 품목명 : " + productDto.pItem);
		
		// 매입단가 입력하기
		while(true) {
			String strIPrice = productDto.getIPrice() == 0 ? null : productDto.getIPrice() + "";
			strIPrice = this.inputValue("매입단가", strIPrice);
			if(strIPrice.equals("QUIT")) return;
			if(strIPrice.equals("RE")) continue;
			try {
				int iPrice = Integer.valueOf(strIPrice);
				productDto.setIPrice(iPrice);
				break;
			} catch (Exception e) {
				HelpMessage.ERROR("매입단가는 정수로 입력해야 합니다");
				continue;
			}
		} // 매입단가 입력 while end
		HelpMessage.ALERT("입력한 매입단가 : " + productDto.getIPrice());
		HelpMessage.ALERT("계산한 매입단가 : " + productDto.pOPrice);
		
		int result = 0;
		try {
			result = productDao.insert(productDto);
			if(result > 0) HelpMessage.OK("상품정보 추가 OK");
		} catch (Exception e) {
			result = productDao.update(productDto);
			if(result > 0 ) HelpMessage.OK("상품정보 수정 ok");
		}
		if(result < 1) {
			HelpMessage.ERROR("상품정보 추가, 수정 중에 문제 발생", "상품정보 추가, 수정 실패");
		}
	}

	protected String inputValue(String title, String value) {
		String prompt = value == null ? String.format("새로운 %s", title) : String.format("%s 수정(%s) : 유지하려면 Enter >> ", title, value);
		System.out.print(prompt);
		String strValue = scan.nextLine();
		if(strValue.equals("QUIT")) return strValue;
		if(value == null && strValue.isEmpty()) {
			HelpMessage.ERROR(title + "는 반드시 입력해야 합니다");
			return "RE";
		} else if(value != null && strValue.isEmpty()) {
			return value;
		}
		return strValue;
	}
	
	@Override
	public void printList() {
		
		List<ProductDto> productList = productDao.selectAll();
		this.printList(productList);
	}

	@Override
	public void printList(List<ProductDto> productList) {
		System.out.println(Line.dLine(70));
		System.out.println("상품코드\t상품명\t품목명\t매입단가\t매출단가");
		System.out.println(Line.sLine(70));
		for(ProductDto dto : productList) {
			this.printProduct(dto);
		}
		System.out.println(Line.dLine(70));
	}

	@Override
	public void printProduct(ProductDto productDto) {
		System.out.printf("%s\t", productDto.pCode);
		System.out.printf("%s\t", productDto.pName);
		System.out.printf("%s\t", productDto.pItem);
		System.out.printf("%s\t", productDto.getIPrice());
		System.out.printf("%s\t", productDto.pOPrice);
		System.out.println();
		
	}

	@Override
	public ProductDto findByPCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDto findByPName() {
		// TODO Auto-generated method stub
		return null;
	}

}
