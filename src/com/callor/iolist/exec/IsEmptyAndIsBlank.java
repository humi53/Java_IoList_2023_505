package com.callor.iolist.exec;

public class IsEmptyAndIsBlank {
	public static void main(String[] args) {
		String nation1 = "";
		// 다른 문자열이 없이 space 만 있는 문자열
		String nation2 = "    ";
		
		System.out.println(nation1.equals(""));
		
		System.out.println(nation1.isEmpty());
		System.out.println(nation1.isBlank());
		
		System.out.println(nation2.isEmpty());
		System.out.println(nation2.isBlank());
		
		System.out.println(nation2.trim().isEmpty());
		
		
	}
}
