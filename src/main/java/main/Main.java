package main;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;
import tools.URLGenerator;
import tools.XMLParser;


public class Main {
	public static void main(String[] args) throws IOException, ParseException {
		test();
	}

	public static void test() throws IOException, ParseException {
		URLGenerator gen = new URLGenerator(); // Create URLGenerator Object
		System.out.println("url 생성완료");
		Vector<String> v = gen.getVector(); // Get Vector Object that contains Program ID

		XMLParser xp = new XMLParser(); // Create XMLParser Object for Parse data

		for(int i = 0; i < v.size(); i++) {
			xp.setCode(v.get(i)); // Set Program ID for Use API
			System.out.println(i + 1 + "."); // Print Num
			xp.getInfoByAPI(); // Get Information By Using API
			System.out.println("정보 출력합니다");
			xp.getInfoTest(); // Print Information(for Test)

			System.out.println();
		}
	}
}