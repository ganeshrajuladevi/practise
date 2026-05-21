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

## Java snippet - Trapping Rain Water (Prefix/Suffix Arrays) (Time complexity:O(n), Space:O(n))

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

## When to use
- Finding optimal subarray/substring with a specific property
- "Longest/shortest substring/subarray with condition X"
- Can avoid nested loops by maintaining a window

## Types

### Variable-size window (two pointers)
- Window grows/shrinks based on condition
- `right` expands, `left` shrinks when invalid
- Use `while` loop to shrink until valid

## Template (Variable Window)
```java
int left = 0, result = 0;
Set/Map tracker = ...;

for (int right = 0; right < n; right++) {
    // Add right element to window
    
    // Shrink window while invalid
    while (window is invalid) {
        // Remove left element
        left++;
    }
    
    // Update result
    result = Math.max(result, right - left + 1);
}
```

## Key trick
- Right pointer always moves forward (outer loop)
- Left pointer moves only when needed (inner while)
- Use Set/Map to track window state incrementally

#### General sliding window thinking pattern
1. When implementing sliding window, follow this mental checklist:
2. Expand the window (move right forward)
3. Check validity (does the window violate the constraint?)
4. If invalid, shrink until valid ← This is almost always a while loop
5. Update answer (max/min length, count, etc.)

## Problems
- Best Time to Buy and Sell Stock (single-pass variant)
- Longest Substring Without Repeating Characters

## Java snippet - Longest Substring Without Repeating Characters
```java
public int lengthOfLongestSubstring(String s) {
    int left = 0, maxLength = 0;
    Set<Character> charsInWindow = new HashSet<>();

    for (int right = 0;right < s.length();right++) {
        while (charsInWindow.contains(s.charAt(right))) {
            charsInWindow.remove(s.charAt(left));
            left++;
        }
        charsInWindow.add(s.charAt(right));
        maxLength = Math.max(maxLength, right - left + 1);
    }
    return maxLength;
}
```


