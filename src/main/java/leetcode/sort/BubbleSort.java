package leetcode.sort;

import java.util.Arrays;

public class BubbleSort {

    public static int[] bubbleSort(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len -1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int tmp = nums[j+1];
                    nums[j + 1] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,6,7,0};
        int[] newNums = bubbleSort(nums);
        Arrays.stream(newNums).forEach(System.out::print);
    }
}
