//Che-Chi Jack Liu
//V00850558


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class  KMP 
{
    private static String pattern;
    private static int patlen;
    private static int[][] arr;

    public KMP(String pattern){
    	patlen = pattern.length();

    	//ASCII character, 0 to 255, 7 bits
    	//can be 85 sinec our biggest alphabet is T and 84 represent T
    	arr = new int[256][patlen];
    	arr[pattern.charAt(0)][0] = 1;

    	//Buliding DFA for input pattern
    	int prev = 0;
    	for(int i=1; i<patlen; i++) {
    		for(int j = 0; j<256; j++) {
    			arr[j][i] = arr[j][prev];
    		}
    		arr[pattern.charAt(i)][i] = i+1;
    		prev = arr[pattern.charAt(i)][prev];
    	}
    }
    
    public static int search(String txt){ 
    	int length = txt.length();
    	int index=0;
    	int i;

    	//begin searching 
    	for(i=0; i<length && index < patlen; i++) {
    		index = arr[txt.charAt(i)][index];
    	} 

    	//index starts counting at stage 0
    	//matached pattern
    	if(index == patlen) {
    		return i - patlen;
    	}

    	//not found, went throught the whole text
    	else{
			System.out.println("The length of the input text is "+ txt.length());

			//Testing for length, 1 extra because of the white space at the end of the input txt.
			/*for (int l = 0; l < txt.length(); l++) {
				System.out.print("#"+l+": ");
    			System.out.println(txt.charAt(l));
			}*/
    	}

    	return txt.length();
    }
    
    
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    try{
		s = new Scanner(new File(args[0]));
	    } catch(java.io.FileNotFoundException e){
		System.out.println("Unable to open "+args[0]+ ".");
		return;
	    }
	    System.out.println("Opened file "+args[0] + ".");
	    String text = "";
	    while(s.hasNext()){
		text += s.next() + " ";
	    }
	    
	    for(int i = 1; i < args.length; i++){
		KMP k = new KMP(args[i]);
		int index = search(text);
		if(index >= text.length())System.out.println(args[i] + " was not found.");
		else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
	    }
	    
	    //System.out.println(text);
	    
	}
	else{
	    System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
	}
	
	
    }
}
