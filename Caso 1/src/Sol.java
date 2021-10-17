public class Sol{
	
	static int[] nums = {4,3,6,7,9};

	public static void main(String[] args) {
    	for(int i = 0; i < nums.length; i++) {
    		System.out.print(nums[i]+", ");
    	}
    	System.out.println("\n");
    	rearrangeArray(nums);
    }
	
	
	public static int[] rearrangeArray(int[] nums) {
        int[] n = new int[nums.length];
        n = nums;
        for(int i = 1; i < n.length-1;i++) {
        	if((n[i-1]+n[i+1])/2==n[i]) {
        		int temp = n[i];
        		n[i] = n[i+1];
        		n[i+1] = temp;
        		i=1;
        	}
        }
        for(int i = 0; i < n.length;i++) {
        	System.out.println(n[i]);
        }
        return n;
       
    }
    
    
}