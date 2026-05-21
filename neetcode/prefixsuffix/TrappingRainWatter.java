package neetcode.prefixsuffix;

public class TrappingRainWatter {
    public static void main(String[] args) {

    }

    public static int trap(int[] height) {
        // Calculate maxLeft array at each index to store max height from left till ith index
        int[] maxLeft = new int[height.length];
        int maxLeftHeight = 0;
        for (int i = 0;i < height.length;i++) {
            maxLeftHeight = Math.max(maxLeftHeight, height[i]);
            maxLeft[i] = maxLeftHeight;
        }

        // Calculate maxRight array at each index to store max height from right from i-1th index to 0
        int[] maxRight = new int[height.length];
        int maxRightHeight = 0;
        for (int i = height.length - 1;i >= 0;i--) {
            maxRightHeight = Math.max(maxRightHeight, height[i]);
            maxRight[i] = maxRightHeight;
        }

        // Calculate water trapped at each index by taking min(maxLeft[i],maxRight[i]) - height[i]
        int[] waterTrapped = new int[height.length];
        for (int i = 0;i < height.length;i++) {
            int level = Math.min(maxLeft[i],maxRight[i]);
            if (level - height[i] > 0)
                waterTrapped[i] = level - height[i];
            else
                waterTrapped[i] = 0;
        }

        // Calculate total water trapped
        int totalWater = 0;
        for (int i = 0;i < waterTrapped.length;i++) {
            totalWater += waterTrapped[i];
        }
        return totalWater;
    }
}
