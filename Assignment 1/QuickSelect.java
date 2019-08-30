//Che-Chi (Jack) Liu
//V00850558

//Quickselect with the median-of-medians pivot, where the medians are of groups of size 7.
//	Input: An array A of n integers and an integer k in the set {1, 2, . . . , n}.
//	Output: The kth smallest element of A.

//For an input array containing n values, the optimal implementation is O(n).

import java.util.*;

public class QuickSelect {
   	public static int num_subgroup;
	
   	public static int QuickSelect(int[] A, int k) {
    		return partition(A, grouping(A), k);
 	}

 	public static int grouping(int[] input) {
    		num_subgroup = (int)Math.floor(input.length/7);
		
    		int[] output = new int[7];
    		int[] median;

    		if(input.length % 7 != 0) {
    			median = new int[num_subgroup + 1];  
    		}
    		else {
   			median = new int[num_subgroup];
    		}
    	  	
		int count = 0;
        	int sub_count = 1;		
        	int end = 0;
		
       	 	while(sub_count <= num_subgroup) {
			System.arraycopy(input, count, output, 0, 7);		
			
			Arrays.sort(output);
			median[sub_count - 1] = output[3];
			
			count = count+7;
			sub_count++;
        	}
    
		end = 6+((sub_count-2)*7);

		int x = input.length - (7 * num_subgroup);
		
   		if((input.length % 7) != 0) {
     			output = new int[x];
     			System.arraycopy(input, end+1, output, 0, x);

     			Arrays.sort(output);
     		 		
     			if(((x + 1)%2) != 0) {
     				median[sub_count-1] = output[(int)(Math.floor(x / 2))];  
     			}
     			else {
     				median[sub_count-1] = output[(int)(Math.floor((x + 1) / 2)) - 1];
     			}
     		}
		
     		if(median.length > 1) {
     			return grouping(median);
     		}
		
     		return median[0];
	}
	
	public static int partition(int[] input, int mom, int k) {
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();

		Integer m = new Integer(mom);
		int x;
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] <= mom) {
				left.add(input[i]);
			}
			else {
				right.add(input[i]);
			}
		}
		
		if(left.contains(m)) {
			left.remove(m);
		}
		
		x = left.size() + 1;
		
		int[] lefta = new int[left.size()];
		for(int i = 0; i < lefta.length; i++) {
			lefta[i] = left.get(i).intValue();
		}

		int[] righta = new int[right.size()];;
		for(int i = 0; i < righta.length; i++) {
			righta[i] = right.get(i).intValue();
		}

		if(k < x && left.size() >= k) {
			return QuickSelect(lefta, k);
		}
		else if(k > x && right.size() >= k) {
			return QuickSelect(righta, k);
		}
		else if(k > x && right.size() < k) {
			return QuickSelect(righta, k-x);
		}

		return mom;	
	}
	
   	public static void main(String[] args) {
		int[] A = {23, 31, 3, 2, 19, 3, 100};
		
		System.out.println("The input int array is: ");
		for(int a: A) {
			System.out.print(a+" ");
		}
		
		System.out.println("\n");
    		System.out.println("The median is " + QuickSelect(A, (A.length+1)/2));
	}
}
