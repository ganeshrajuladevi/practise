package neetcode.prefixsuffix;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithoutRepeatableChars {
    public static void main(String[] args) {
        String s="abcabcbb";
        System.out.println("Expected out is 3:" + lengthOfLongestSubstring(s));
    }

    public static int lengthOfLongestSubstring(String s) {
        int left = 0, maxLength = 0;
        Set<Character> setOfChars = new HashSet<>();

        for (int right = 0;right < s.length();right++) {
            while(setOfChars.contains(s.charAt(right))) {
                setOfChars.remove(s.charAt(left));
                left++;
            }
            setOfChars.add(s.charAt(right));
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}
