# 1. Prefix/Suffix Pattern

## When to use
- Need product/sum/max of all elements except current
- Combine information from both sides

## Template
1. Left pass: build prefix
2. Right pass: build suffix  
3. Combine

## Key trick
prefix[i] = everything before i
suffix[i] = everything after i

## Problems
- Product Except Self
- Trapping Rain Water

## Java snippet - Product Except Self

```java
import java.util.Arrays;

public class ProductOfArrayExceptSelf {
    public static void main(String[] args) {
        int nums[] = new int[]{-1,0,1,2,3};
        nums = new int[]{1,2,4,6};
        System.out.println(Arrays.toString(productExceptSelf(nums)));
    }

    private static int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];

        int prefix = 1;
        for (int i = 0; i < nums.length; i++) {
            result[i] = prefix;
            prefix = prefix * nums[i];
        }

        int suffix = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] = result[i] * suffix;
            suffix = suffix * nums[i];
        }

        return result;
    }
}
```

## Java snippet - Trapping Rain Water (Prefix/Suffix Arrays)

```java
public class TrappingRainWater {
    public static int trap(int[] height) {
        int[] maxLeft = new int[height.length];
        int maxLeftHeight = 0;
        for (int i = 0; i < height.length; i++) {
            maxLeftHeight = Math.max(maxLeftHeight, height[i]);
            maxLeft[i] = maxLeftHeight;
        }

        int[] maxRight = new int[height.length];
        int maxRightHeight = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            maxRightHeight = Math.max(maxRightHeight, height[i]);
            maxRight[i] = maxRightHeight;
        }

        int totalWater = 0;
        for (int i = 0; i < height.length; i++) {
            int level = Math.min(maxLeft[i], maxRight[i]);
            totalWater += Math.max(0, level - height[i]);
        }
        return totalWater;
    }
}
```

## Java snippet - Trapping Rain Water (Two Pointers - O(1) space)

```java
public int trap(int[] height) {
    int left = 0, right = height.length - 1;
    int maxLeft = 0, maxRight = 0;
    int totalWater = 0;
    
    while (left <= right) {
        if (height[left] <= height[right]) { // left is bottleneck
            if (height[left] >= maxLeft) {
                maxLeft = height[left];
            } else {
                totalWater += (maxLeft - height[left]);
            }
            left++;
        } else { // right is bottleneck
            if (height[right] > maxRight) {
                maxRight = height[right];
            } else {
                totalWater += (maxRight - height[right]);
            }
            right--;
        }
    }
    return totalWater;
}
```

---

# 2. Sliding Window Pattern

## Sliding Window - Variable (Single Pass)

### When to use
- Finding optimal buy/sell, min/max profit in a sequence
- One pass with tracking min/max profit seen so far

### Template
1. Initialize tracker (minSoFar for price, maxSoFar for profit to sell)
2. Single loop through array
3. Update tracker or compute result at each step

### Key trick
Track the "best opportunity so far" and compare current element against it

### Problems
- Best Time to Buy and Sell Stock

Given an array prices where prices[i] is the price of a stock on day i, find the maximum profit you can make by buying on one day and selling on a later day.
You can only buy once and sell once.
If no profit is possible, return 0.

Example:
    Input: prices = [7, 1, 5, 3, 6, 4]
    Output: 5 (buy at 1, sell at 6)

### Java snippet
```java
public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int i = 0;i < prices.length;i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                int profit = prices[i] - minPrice;
                maxProfit = Math.max(maxProfit, profit);
            }
        }
        return maxProfit;
    }
```