//Che-Chi Jack Liu
//V00850558


import java.util.*;

public class QuickSelect {

   	public static int num_subgroup;

   	public static int QuickSelect(int[] A, int k){
    	return partition(A, grouping(A), k);
 	}

 	public static int grouping(int[] input) {
    	num_subgroup = (int)Math.floor(input.length/7);

    	int[] output = new int[7];
    	int[] median;

    	if(input.length % 7 != 0) {
    		median = new int[num_subgroup + 1];  
    	}
    	else{
   			median = new int[num_subgroup];
    	}
    	

        int count=0;
        int sub_count=1;		
        int end=0;

        
        while(sub_count <= num_subgroup) {
			System.arraycopy(input, count, output, 0, 7);		

			Arrays.sort(output);
			median[sub_count - 1] = output[3];

			/*for(int i=0; i<output.length; i++) {
				System.out.println(output[i]);
			}*/

			count = count+7;
			sub_count++;
        }
    
		end = 6+((sub_count-2)*7);

		int x = input.length - (7 * num_subgroup);
		
   		if((input.length % 7) != 0) {
     		output = new int[x];
     		System.arraycopy(input, end+1, output, 0, x);

     		Arrays.sort(output); 
     		 		
     		if(((x + 1)%2)!= 0) { 
     			median[sub_count-1] = output[(int)(Math.floor(x / 2))];  
     		}
     		else{
     			median[sub_count-1] = output[ (int)(Math.floor((x + 1) / 2)) - 1 ];
     		}
     	}

     	
     	//System.out.println(median[i]+"eeee");
     	
     	
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

		for(int i=0; i < input.length; i++) {
			if(input[i] <= mom) {
				left.add(input[i]);
			}
			else{
				right.add(input[i]);
			}
		}

		if(left.contains(m)) {
			left.remove(m);
		}

		x = left.size() + 1;

		int[] lefta;
		lefta = toArray(left);

		int[] righta;
		righta = toArray(right);

		if( k < x && left.size() >= k) {
			return QuickSelect(lefta, k);
		}
		else if (k > x && right.size() >= k) {
			return QuickSelect(righta, k);
		}
		else if(k > x && right.size() < k ) {
			return QuickSelect(righta, k-x);
		}

		return mom;	

	}

	public static int[] toArray (List<Integer> input)	{
    	int[] x = new int[input.size()];
   		for (int i=0; i < x.length; i++) {
        	x[i] = input.get(i).intValue();
    	}
   		return x;

	}

    public static void main(String[] args) {
    	int[] A = {8, 7, 6, 5, 4, 3, 2};

    	System.out.println("The median is " + QuickSelect(A, (A.length+1)/2));
    	System.out.println(A.length);

    	//System.out.println(grouping(A));
		//System.out.println("The mom is "+QuickSelect(A, (A.length+1)/2));
	}
}