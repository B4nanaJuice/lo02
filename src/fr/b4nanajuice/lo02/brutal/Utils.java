package fr.b4nanajuice.lo02.brutal;

import java.util.Scanner;

public class Utils {
	
	private final static Scanner scan = new Scanner(System.in);
	
	/*
	 * This function allows the player to get an input from the player in the console.
	 * The function takes as an input the message that will be shown to the player.
	 * This function return the input of the player.
	 */
	public static String input(String m) {
		String resp;
		System.out.println(m);
		resp = scan.nextLine();
		return resp;
	}
	
	/*
	 *This function allows the user to see if an object o is in a list of objects.
	 *The functions takes as an input the object and the list of objects.
	 *Tis function return a boolean value. 
	 */
	public static boolean in(Object o, Object[] os) {
		boolean resp = false;
		for (Object obj: os) {
			if (obj.equals(o)) {
				resp = true;
			}
		}
		return resp;
	}
	
	/*
	 * The function allows the user to know if a string is an integer.
	 * It takes as an input the string.
	 * This function return a boolean value.
	 */
	public static boolean isInt(String s) {
		if (s == null) return false;
		try {
			Integer.parseInt(s);
		} catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/*
	 * This function allows the user to calculate the sum of a list of integers.
	 * The function takes as an input a list of integers.
	 * This function returns the sum of the list.
	 */
	public static int sum(int[] i) {
		int sum = 0;
		for (int nb: i) {
			sum += nb;
		}
		return sum;
	}
}
