import java.util.Scanner;
import java.util.Stack;

class MyRegEx {
	static char[] dnaStringCharArray;
	static int cp = 0;
	static Stack<Character> mstack = new Stack<Character>();
	static boolean valid;

	public static void pushR() {
		char c1, c2;
		mstack.push('R');
		if (mstack.size() > 1) {
			c2 = mstack.pop();
			c1 = mstack.pop();
			if (c1 == 'R') {
				mstack.push('R');
				// System.out.println("rule R -> RR");
			} else if (c1 == '|') {
				// mstack.push(c1);
				// mstack.push('R');
				// System.out.println("rule R -> R|R");
			} else {
				mstack.push(c1);
				mstack.push('R');
			}
		}
	}

	public static boolean isValidExp(String line) {
		char[] la = line.toCharArray();
		char c1, c2;
		int i = 0;
		while (i < line.length()) {
			if (la[i] == '(' || la[i] == '|') {
				mstack.push(la[i]);
				// System.out.println("shift");
				if (la[i + 1] == '*') {
					System.out.println("Regex is INVALID!!!");
					return false;
				}
			}
			if (la[i] == 'A' || la[i] == 'C' || la[i] == 'G' || la[i] == 'T') {
				if (i + 1 != line.length() && la[i + 1] == '*') {
					// System.out.println("rule R -> S*");
					i++;
				} else {
					// System.out.println("rule R -> S");
				}
				pushR();
			}
			if (la[i] == ')') {
				c2 = mstack.pop();
				c1 = mstack.pop();

				if (i + 1 != line.length() && la[i + 1] == '*') {
					// System.out.println("rule R -> (R)*");
					i++;
				} // else {
					// //System.out.println("rule R -> (R)");
					// }
				pushR();
			}
			// System.out.println(i + "  " + mstack.toString());
			i++;
		}

		if (i == line.length() && mstack.peek() == 'R' && mstack.size() == 1) {
			System.out.println("Regex is VALID!!!");
			return true;
		} else
			System.out.println("Regex is INVALID!!!");
		return false;

	}

	public static boolean examineRegExSansPrenorOR(String regularexpression) {
		System.out
				.println("regex has neithor Prenthisis or Or in it, analizing!");
		char[] regExArray = regularexpression.toCharArray();
		int i = 0;
		int c2 = cp;
		while (i < regularexpression.length() && cp < dnaStringCharArray.length) {
			System.out.println(i + "   " + cp);
			if (regExArray[i] == dnaStringCharArray[cp]) {
				cp++;
				i++;
			}
			// the below statement is never activated because of the double
			// itteration in the folowing else if statemtn
			// else if (regExArray[i] == '*') {
			// i--;
			// }
			else if (regExArray[i + 1] == '*') {
				if (i + 2 < regularexpression.length()) // if follows format -
														// 'I'*ES is valid
					i = i + 2;
				else { // ...'I'*T
					cp = c2;
					return false;
				}
			} else {
				cp = c2;
				return false;
			}
		}
		return true;
	}

	public static boolean parenthesesMatchUp(String regularExpression) {
		boolean kaliene = false;
		if (regularExpression.indexOf('*') < 0) {

		} else {
			kaliene = true;
		}
		if (regularExpression.indexOf('(') < 0
				&& regularExpression.indexOf('|') < 0) {
			return examineRegExSansPrenorOR(regularExpression);
		}
		else if (regularExpression.indexOf('(') < 0
				&& regularExpression.indexOf('|') > 0) {
			return examineRegExwithOR(regularExpression, kaliene);
		}

		else {
			char[] regExArray = regularExpression.toCharArray();
			int ss = 0;
			int beginSubString = 0;
			int endSubString = 0;
			int m = 0;
			boolean ttt = true;
			for (int u = 0; u < regExArray.length; u++) {
				System.out.println("Line 134: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt + ", U = " + u);
				if (regExArray[u] == '(' && m == 0) {
					if (ss == 0) {
						beginSubString = u + 1;
					}
					ss++;
				} else if (regExArray[u] == '(' && m == 1) {
					ss--;
					endSubString = u;
					m = 0;
					System.out.println("Line 144: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt + ", U = " + u);
					System.out.println(regularExpression.substring(
							beginSubString, endSubString)
							+ " s = "
							+ ss
							+ " m = " + m);
					ttt = parenthesesMatchUp(regularExpression.substring(
							beginSubString, endSubString));
					if (!ttt) {
						return ttt;
					}
					ss++;
					beginSubString = u + 1;
					endSubString = 0;
					System.out.println("Line 158: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt + ", U = " + u);
					
				} else if (regExArray[u] == ')') {
					ss--;
					if (ss == 0) {
						if (u < regExArray.length - 1
								&& regExArray[u + 1] == '*') {
							int c2 = cp;
							endSubString = u;
							String r2 = regularExpression.substring(
									beginSubString, endSubString);
							System.out.println("print 2");
							System.out.println(r2 + " s = " + ss + " m = " + m);
							ttt = parenthesesMatchUp(r2);
							while (ttt && cp < dnaStringCharArray.length) {

								System.out.println("print 3");
								System.out.println(cp);
								c2 = cp;
								ttt = parenthesesMatchUp(r2);
							}
							if (!ttt && cp < dnaStringCharArray.length) {
								cp = c2;
								ttt = true;
							}
						}

						else {
							endSubString = u;
							System.out.println("line 207: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt);

							System.out.println("print 4");
							System.out.println(regularExpression.substring(
									beginSubString, endSubString)
									+ " s = "
									+ ss + " m = " + m);
							ttt = parenthesesMatchUp(regularExpression
									.substring(beginSubString, endSubString));
							if (ttt == false)
								return ttt;
						}
					}
				} else if (regExArray[u] != '*' && ss == 0 && m == 0) {
					beginSubString = u;
					m = 1;
					ss++;
					
					
					System.out.println("line 206: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt + ", U = " + u);
				} else if (u == regExArray.length - 1 && m == 1) {
					endSubString = regExArray.length;
					ss--;

					System.out.println("line 211: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt + ", U = " + u);
					System.out.println(regularExpression.substring(
							beginSubString, endSubString) + " s = " + ss);
					ttt = parenthesesMatchUp(regularExpression.substring(
							beginSubString, endSubString));
					if (ttt == false) {
						System.out.println("line 217: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt + ", U = " + u);
						return ttt;
					}
				}
			}
			System.out.println("line 222: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt + ", cp = " + cp + ", dnaStringCharArray.length = " + dnaStringCharArray.length  );
			if (cp == dnaStringCharArray.length)
				return ttt;
			else
				System.out.println("line 225: ss = " + ss + ", Bsubstring = " + beginSubString + ", Esubstring = " + endSubString + ", m = " + m + ", ttt = " + ttt );
				return false;
		}
	}

	private static boolean examineRegExwithOR(String regularExpression,
			boolean kaliene) {
		// this method is incomplete!
		// this method will examine a substring with OR operands
		
		//the following If Else statement checks to see if a kaliene star is used on the end of 
		System.out.println("Has Kaliene?" + kaliene);
		if (kaliene){
			if (cp == dnaStringCharArray.length){
				System.out.println("The OR statements are not Used!!! thanks Kaliene");
			}
		}else {
			if (dnaStringCharArray.length == cp){
		    System.out.println("Error, no kaliene is used, but the or statement does not appear in the DNA sequince!!!");
			return false;
			}
		}
		System.out.println("Substring has OR, analizing!");
		if (regularExpression.indexOf('*') > 0) {
			System.out
					.println("Substring has Kaliene Star, this are getting complicated!");
		}
		System.out.println(regularExpression);
		// System.out.println(dnaStringCharArray);
		// System.out.println(cp);
		char[] regExArray = regularExpression.toCharArray();
		int i = 0;
		int c2 = cp; // dnastring pointer
		int k = 0;
		int f = 0;
		char[][] substringSansOr = new char[15][15];
		boolean valid = false;
		for (int j = 0; j < regularExpression.length(); j++) {
			if (regExArray[j] == '|') {
				// System.out.println("this charictor is an OR!!!");
				f++;
				k = 0;
			} else {
				// System.out.println("this charictor is not an or");
				substringSansOr[f][k] = regExArray[j];
				//System.out.println("Substring Sans Or = "
						//+ substringSansOr[f][k]);
				//System.out.println("RegExArray = " + regExArray[j]);
				k++;
				// for (int t = 0; t < substringSansOr.length; t++) {
				// if (substringSansOr[t] == '\u0000') {
				// }else{
				// System.out.println("Substring Sans Or = " +
				// substringSansOr[t]);
				// System.out.println("RegExArray = " + regExArray[j]);
				// }
			}

		}

		// the following prints a 2D array of the or statement, each OR case is
		// on its own line, and can have up to 15 charictors
		for (int z = 0; z < 15; z++) {
			for (int x = 0; x < 15; x++) {
				if (substringSansOr[z][x] == '\u0000') {
				} else {
					System.out.print(substringSansOr[z][x]);
				}
			}
			if (substringSansOr[z][0] == '\u0000') {
			} else {
				System.out.println(" ");
			}
		}
		// the following determines if any of the or statements in the 2D array
		// match the dnaString

		if (kaliene) {
			for (int z = 0; z < 15; z++) {
				for (int x = 0; x < 15; x++) {
					if (substringSansOr[z][x] == '\u0000') {
					} else {
						// check if it matches
						if (substringSansOr[z][x] == dnaStringCharArray[cp]) {
							valid = true;
							cp++;
						} else {
							valid = false;
						}

					}
				}
				if (valid) {
					return valid;
				} else {
					System.out.println("the OR statement was not used! thanks Kaliene for waisting my time!!!");
				}

			}
		} else {
			for (int z = 0; z < 15; z++) {
				for (int x = 0; x < 15; x++) {
					if (substringSansOr[z][x] == '\u0000') {
					} else {
						// check if it matches
						System.out.println("check if it matches!!!");
						if (substringSansOr[z][x] == dnaStringCharArray[cp]) {
							valid = true;
							cp++;
						} else {
							valid = false;
						}

					}
				}
				if (valid) {
					System.out.println("It Matches!!!!");
					return valid;
				} else {
					return valid;
				}

			}
		}

		if (regExArray[i + 1] == '*') {
			int sameCount = 0;

			for (int j = 0; j > dnaStringCharArray.length; j++) {
				if (dnaStringCharArray[c2] == regExArray[i]) {
					c2++;
				} else {
					sameCount = j;
					j = dnaStringCharArray.length + 1;
				}
			}
			if (sameCount == 0) {
				return false;
			}

		}

		cp = cp + c2;
		return valid;
	}

	public static void main(String[] args) {
		// System.out.println("Enter the reg expression:");
		// Scanner keyb = new Scanner(System.in);
		// String reg = keyb.nextLine();
		String reg = "TAGC(A|T)";
		// System.out.println("Enter a DNA sequence:");
		// String ts = keyb.nextLine();
		String ts = "TAGCT";
		dnaStringCharArray = ts.toCharArray();

		if (isValidExp(reg)) {
			System.out.println(parenthesesMatchUp(reg));
		} else {
			System.out.println("Expression is invalid!");
		}
		// System.out.println("_________________________________");

		// System.out.println(exregexb(reg));

	}
}