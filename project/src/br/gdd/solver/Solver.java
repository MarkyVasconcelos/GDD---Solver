package br.gdd.solver;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Solver {
	private char[] fooLetters;
	private char[] barLetters;
	private List<Character> lexicalOrder;
	private String text;

	public Solver(char[] fooLetters) {
		this.fooLetters = fooLetters;

		barLetters = new char[26 - fooLetters.length];
		int idx = 0;
		for (char letter = 'a'; letter <= 'z'; letter++)
			if (!isFooLetter(letter))
				barLetters[idx++] = letter;
	}

	public void setLexicalOrder(Character[] charArray) {
		this.lexicalOrder = Arrays.asList(charArray);
	}

	public void setText(String text) {
		this.text = text;
	}

	private boolean isFooLetter(char c) {
		return contains(c, fooLetters);
	}

	private boolean contains(char c, char[] letters) {
		for (char foo : letters)
			if (foo == c)
				return true;
		return false;
	}

	public int countPrepositions() {
		String[] words = text.split(" ");
		int count = 0;
		for (String word : words) {
			if (word.length() != 5 || contains('n', word.toCharArray()))
				continue;
			if (contains(word.charAt(word.length() - 1), barLetters))
				count++;
		}

		return count;
	}

	public int countVerbs() {
		String[] words = text.split(" ");
		int count = 0;
		for (String word : words) {
			if (word.length() < 8
					|| !contains(word.charAt(word.length() - 1), fooLetters))
				continue;
			count++;
		}

		return count;
	}

	public int countSingVerbs() {
		String[] words = text.split(" ");
		int count = 0;
		for (String word : words) {
			if (word.length() < 8
					|| !contains(word.charAt(word.length() - 1), fooLetters))
				continue;
			if (contains(word.charAt(0), barLetters))
				count++;
		}

		return count;
	}

	public String getVocabularyListAsString() {
		Set<String> list = getVocabularyList();
		StringBuilder builder = new StringBuilder();
		for (String s : list)
			builder.append(s).append(" ");
		return builder.toString();
	}

	
	public Set<String> getVocabularyList() {
		Set<String> set = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int len1 = o1.length();
				int len2 = o2.length();
				int n = Math.min(len1, len2);
				char v1[] = o1.toCharArray();
				char v2[] = o2.toCharArray();
				int i = 0;
				int j = 0;

				if (i == j) {
					int k = i;
					int lim = n + i;
					while (k < lim) {
						char c1 = v1[k];
						char c2 = v2[k];
						if (c1 != c2)
							return lexicalOrder.indexOf(c1)
									- lexicalOrder.indexOf(c2);
						k++;
					}
				} else {
					while (n-- != 0) {
						char c1 = v1[i++];
						char c2 = v2[j++];
						if (c1 != c2) {
							return lexicalOrder.indexOf(c1)
									- lexicalOrder.indexOf(c2);
						}
					}
				}
				return len1 - len2;
			}
		});
		set.addAll(Arrays.asList(text.split(" ")));

		return set;
	}

	public int getBeautifullDistinctNumbersCount() {
		String[] words = text.split(" ");
		int count = 0;
		for (String word : words) {
			long gNumber = toGooglonNumber(word.toCharArray());
			if (isBeautifull(gNumber))
				count++;
		}

		return count;
	}

	private long toGooglonNumber(char[] s) {
		long number = 0;

		for (int i = 0; i < s.length; i++) {
			long x = lexicalOrder.indexOf(s[i]);
			number += x * Math.pow(20, i);
		}

		return number;
	}

	private boolean isBeautifull(long number) {
		return number >= 538741l && number % 3l == 0;
	}

}
