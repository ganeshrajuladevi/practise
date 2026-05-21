package neetcode.slidingwindow;

public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        int[] prices = new int[]{10,1,5,6,7,1};
        System.out.println("maxProfit is suppose to be 6:"+maxProfit(prices));

        prices = new int[]{10,8,7,5,2};
        System.out.println("maxProfit is suppose to be 0:"+maxProfit(prices));
    }

    private static int maxProfit(int[] prices) {
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
}
