package com.yopheu.iolist.config;

public class HelpMessage {
	public static final void ERROR(String message) {
		ERROR(message, "");
	}
	public static final void ERROR(String message, String data) {
		System.out.println(message);
		System.out.println(data);
	}
}
