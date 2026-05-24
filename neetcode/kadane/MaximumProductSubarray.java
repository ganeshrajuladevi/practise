package neetcode.kadane;

public class MaximumProductSubarray {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 0, 4,2};
        System.out.println(maxProduct(nums));
    }
    private static int maxProduct(int[] nums) {
        int currMax = nums[0];
        int currMin = nums[0];
        int maxProduct = nums[0];

        for (int i = 1;i < nums.length;i++) {
            int a = nums[i];
            int b = currMax * nums[i];
            int c = currMin * nums[i];

            currMax = Math.max(Math.max(a,b),c);
            currMin = Math.min(Math.min(a,b),c);
            maxProduct = Math.max(maxProduct, currMax);
        }

        return maxProduct;
    }
}
