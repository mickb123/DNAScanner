//import java.util.Scanner;


public class semanticAnalyser {

	static char[] DNAChars;
	   static int cp = 0;

	   public static boolean exregexb(String re) {
	      char[] ra = re.toCharArray();
	      int i = 0;
	      int c2 = cp;
	      while (i < re.length() && cp < DNAChars.length) {
	         System.out.println(i + "   " + cp);
	         if (ra[i] == DNAChars[cp]) {
	             cp++;
	             i++;
	         }
	         else if (ra[i] == '*') {
	             i--;
	         }
	         else if (ra[i+1] == '*') {
	             if (i + 2 < re.length())
	               i = i + 2;
	             else {
	               cp = c2;
	               return false;
	             }
	         }
	         else {
	           cp = c2;
	           return false;         
	         }
	      }
	      return true;
	   }
	   
	   public static boolean parre(String re) {
	      if (re.indexOf('(') < 0 && re.indexOf('|') < 0 ) 
	         return exregexb(re);
	      else {
	         char[] regularExpressionArray = re.toCharArray();
	         //Substring
	         int subString = 0;
	         //begin
	         int begin = 0;
	         //end
	         int end = 0;
	         
	         int m = 0;
	         //True
	         boolean truthValue = true;
	         //Go through all the letters in the expression.
	         for (int i = 0; i < regularExpressionArray.length; i++) {
	        	 //If you see an open parenth, and its the first. Begin substring.
	             if (regularExpressionArray[i] == '(' && m == 0) {
	                if (subString == 0) begin = i+1;
	                subString++;
	             }
	             //You see an open parenth before a close parenth. Substring still ends.
	             else if (regularExpressionArray[i] == '(' && m == 1) {
	                subString--;
	                //Sub string ends on parenth.
	                end = i;
	                m = 0;
	                System.out.println(re.substring(begin, end) + " s = " + subString + " m = " + m);
	                //Check to see if previous substring was false.
	                truthValue = parre(re.substring(begin, end));
	                if (truthValue == false) return truthValue;
	                subString++;
	                begin = i+1;
	                end = 0;
	             }
	            /* else if(regularExpressionArray[i]=='|'&& m==0)
	             {
	            	 truthValue=exregexb(re.substring((i-1),(i-1)));
	             }*/
	             else if (regularExpressionArray[i] == ')') {
	                subString--;
	                if (subString == 0) {
	                   if (i < regularExpressionArray.length -1 && regularExpressionArray[i+1] == '*') {
	                      int c2 = cp;
	                      end = i;
	                      String r2 = re.substring(begin, end);
	                      System.out.println(r2 + " s = " + subString + " m = " + m);
	                      truthValue = parre(r2);
	                      while (truthValue && cp < DNAChars.length) {
	                         System.out.println(cp);
	                         c2 = cp;
	                         truthValue = parre(r2);
	                      }
	                      if (!truthValue && cp < DNAChars.length) { 
	                         cp = c2;
	                         truthValue = true;
	                      }
	                   }

	                   else {
	                      end = i+1;
	                      System.out.println(re.substring(begin, end) + " s = " + subString + " m = " + m);
	                      truthValue = parre(re.substring(begin, end));
	                      if (truthValue == false) return truthValue;
	                   }
	                }
	             }
	             else if (regularExpressionArray[i] != '*' && subString == 0 && m == 0) {
	                 begin = i;
	                 m = 1;
	                 subString++;
	                 System.out.println("oh my");
	             }
	             else if (i == regularExpressionArray.length - 1 && m == 1) {
	                 end = regularExpressionArray.length;
	                 subString--;
	                 System.out.println(re.substring(begin, end) + " s = " + subString);
	                 truthValue = parre(re.substring(begin, end));
	                 if (truthValue == false) return truthValue;
	             }
	         }
	         if (cp == DNAChars.length)
	             return truthValue;
	         else return false;
	      }
	  }   
	   public void analyse(String expression, String sequence)
	   {
		      //System.out.println("Enter the reg expression:");
		      //Scanner keyb = new Scanner(System.in);
		     // String reg = keyb.nextLine();
		    // String reg = "AC*GT";
		      String regularExpression=expression;
		     // System.out.println("Enter a DNA sequence:");
		      //String ts = keyb.nextLine();
		     // String ts = "ACCCGT";
		      String ts= sequence;
		      DNAChars = ts.toCharArray();
		      System.out.println(parre(regularExpression));
		   }

}
