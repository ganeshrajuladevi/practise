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
- Longest Repeating Character Replacement

## Java snippet - Best Time to Buy and Sell Stock (single-pass variant)
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

### Java snippet - Longest Repeating Character Replacement

```java
public int characterReplacement(String s, int k) {
    int left = 0, maxLength = 0, maxFrequency = 0;
    Map<Character, Integer> count = new HashMap<>();

    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        count.put(c, count.getOrDefault(c, 0) + 1);
        maxFrequency = Math.max(maxFrequency, count.get(c));
        
        int windowLength = right - left + 1;
        int replacementsNeeded = windowLength - maxFrequency;
        
        while (replacementsNeeded > k) {
            char leftChar = s.charAt(left);
            count.put(leftChar, count.get(leftChar) - 1);
            left++;
            windowLength = right - left + 1;
            replacementsNeeded = windowLength - maxFrequency;
        }
        
        maxLength = Math.max(maxLength, windowLength);
    }
    return maxLength;
}
```

## Two Pointers Pattern

### When to use:
- Array is sorted (or can be sorted)
- Looking for pairs/triplets with specific property
- Need O(1) space
- Comparing elements from opposite ends or different speeds

### Core technique:
- Pointers at opposite ends (most common)
- Move based on comparison logic
- Skip duplicates when needed (for unique results)

### Template (Basic Two Pointers)
```java
int left = 0, right = array.length - 1;

while (left < right) {
    // Calculate current result
    int sum = array[left] + array[right];
    
    // Make decision based on comparison
    if (sum < target) {
        left++;   // Need larger value
    } else if (sum > target) {
        right--;  // Need smaller value
    } else {
        // Found answer
        return result;
    }
}
```

### Problems:
1. Two Sum II (Easy) - Basic two pointers
2. 3Sum (Medium) - Loop + two pointers, duplicate handling
3. Container With Most Water (Medium) - Greedy two pointers

---

### Two Pointers - Two Sum II (Sorted Array)

**Problem:** Given a sorted array, find two numbers that sum to target. Return 1-indexed positions.

**Key insight:** Use sorted property - move left for larger sum, move right for smaller sum

**Complexity:** Time O(n), Space O(1)

```java
public int[] twoSum(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int sum = nums[left] + nums[right];
        
        if (sum < target) {
            left++;   // Need larger sum
        } else if (sum > target) {
            right--;  // Need smaller sum
        } else {
            return new int[]{left + 1, right + 1};  // 1-indexed
        }
    }
    
    return new int[]{};  // No solution found
}
```

**Why it works:**
- Array is sorted, so moving left increases sum, moving right decreases sum
- Each step eliminates one possibility - no backtracking needed
- O(n) because each pointer moves at most n times

---

### Two Pointers - 3Sum

**Problem:** Find all unique triplets [a, b, c] that sum to 0

**Key insight:** Fix one number, use two pointers for the other two (converts to Two Sum II)

**Challenge:** Avoid duplicate triplets by skipping duplicate values

**Complexity:** Time O(n²), Space O(1) (excluding output)

```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);  // MUST sort first
    List<List<Integer>> result = new ArrayList<>();
    
    for (int i = 0; i < nums.length; i++) {
        // Skip duplicate first element
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }
        
        // Two pointer search for remaining two numbers
        int left = i + 1;
        int right = nums.length - 1;
        int target = -nums[i];  // Need two numbers that sum to -nums[i]
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else {
                // Found valid triplet
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                
                // Skip duplicate left values
                while (left < right && nums[left] == nums[left + 1]) {
                    left++;
                }
                left++;
                
                // Skip duplicate right values
                while (left < right && nums[right] == nums[right - 1]) {
                    right--;
                }
                right--;
            }
        }
    }
    
    return result;
}
```

**Three places to skip duplicates:**
1. **Outer loop (i):** Skip if same as previous i
2. **After finding triplet (left):** Skip duplicate left values
3. **After finding triplet (right):** Skip duplicate right values

**Why sort?** Two pointers only works on sorted arrays - need to make smart decisions about which pointer to move

**Time complexity breakdown:**
- Sorting: O(n log n)
- Outer loop: O(n)
- Inner two pointers: O(n)
- Total: O(n²)

---

### Two Pointers - Container With Most Water

**Problem:** Find two lines that form container holding most water

**Key insight:** Greedy approach - always move the pointer pointing to the shorter line

**Why?** Width always decreases when moving inward, so we need taller height to potentially increase area

**Complexity:** Time O(n), Space O(1)

```java
public int maxArea(int[] heights) {
    int left = 0, right = heights.length - 1;
    int maxWater = 0;
    
    while (left < right) {
        // Calculate current area
        int area = (right - left) * Math.min(heights[left], heights[right]);
        maxWater = Math.max(maxWater, area);
        
        // Move pointer with shorter height (greedy choice)
        if (heights[left] <= heights[right]) {
            left++;
        } else {
            right--;
        }
    }
    
    return maxWater;
}
```

**Why move shorter pointer?**
- Water level limited by shorter line
- Moving taller pointer won't help (still limited by shorter line)
- Moving shorter pointer might find taller line → potential for larger area