package com.hackBulgaria.problem1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Main {

	private static final String THE_MARK = "*";

	public static void main(String[] args) {

		String[] a = { "yesterday", "Dog", "food", "walk" };
		String text = "sYesterday, I took my dogg for a walk.\n It was crazy! My dog wanted only food.";
		System.out.println(maskOutWords(a, text));

	}

	private static String maskOutWords(String[] words, String text) {
		for (String word : words) {
			if (word.length() > text.length()) {
				throw new IllegalArgumentException();
			}
		}
		Pattern p;
		Matcher m;
		String result = new String(text);
		for (String word : words) {
			p = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
			m = p.matcher(result);
			result = m.replaceAll(StringUtils.repeat("*", word.length()));
		}
		result = repairMistakes("(\\*[a-zA-Z])", result, text, -1);
		result = repairMistakes("([a-zA-Z]\\*)", result, text, 1);
		return result;
	}
	
	private static String repairMistakes(String regex, String result, String originalText, int wordEnd ){
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(result);
		while (m.find()) {
			int fin = result.indexOf(m.group(1));
			int start = result.indexOf(m.group(1)) + wordEnd;
			while (true) {

				if (result.charAt(start) != '*' || start == 0 || start == result.length()) {
					if (start > fin) {
						if (start + 2 >= result.length()) {
							result = result.replace(result.subSequence(fin, result.length()) , originalText.subSequence(fin, result.length()));
							break;
						}else{
							result = result.replace(result.subSequence(fin, start + 2) , originalText.subSequence(fin, start + 2));
							break;
						}
					}
					result = result.replace(result.subSequence(start, fin + 2) , originalText.subSequence(start, fin + 2));
					break;
				}
				start+= wordEnd;
			}
		}
		return result;
	}
}
