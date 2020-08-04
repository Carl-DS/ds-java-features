package leetcode.binarysearch;

import java.util.Arrays;

/**
 * 二分搜索模板
 * 给一个有序数组和目标值，找第一次/最后一次/任何一次出现的索引，如果没有出现返回-1
 * <p>
 * 模板四点要素
 * <p>
 * 1、初始化：start=0、end=len-1
 * 2、循环条件：start <= end
 * 3、比较中点和目标值：mid = left + (right - left)/2; A[mid] ==、 <、> target
 * 4、判断最后两个元素是否符合：A[start]、A[end] ? target
 * 时间复杂度 O(logn)，使用场景一般是有序数组的查找
 */
public class BinarySearch {

    public static int search(int[] nums, int target) {
        int mid, left = 0, right = nums.length - 1;

        while (left + 1 < right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // 最后剩下两个元素，手动判断
        if (nums[left] == target) {
            return left;
        }
        if (nums[right] == target) {
            return right;
        }
        return -1;
    }

    public static int search1(int[] nums, int target) {
        int mid, left = 0, right = nums.length - 1;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static int[] searchRange(int[] nums, int target) {
        int[] ans = new int[]{-1, -1};
        if (nums.length == 0 || nums[0] > target) {
            return ans;
        }

        int low = 0, high = nums.length - 1;
        // 用二分查找，找到第一次出现 target 的位置
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] >= target) {
                if (mid == 0 || (nums[mid] == target && nums[mid - 1] < target)) {
                    ans[0] = mid;
                    break;
                } else {
                    high = mid - 1;
                }
            } else {
                low = mid + 1;
            }
        }

        if (ans[0] != -1 && nums[ans[0]] == target) {
            if (ans[0] == nums.length - 1) {
                ans[1] = ans[0];
            } else {
                // 往下遍历找到第二次出现 target的位置
                for (int i = ans[0] + 1; i < nums.length; ++i) {
                    if (nums[i] != target) {
                        ans[1] = i - 1;
                        break;
                    }
                    if (i == nums.length - 1 && nums[i] == target) {
                        ans[1] = i;
                    }
                }
            }
        }
        return ans;
    }

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {
        int left =0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left)/2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            } else {
                return mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5};
        // 二分搜索最常用模板
        System.out.println(search(nums, 5));
        // 无重复元素搜索时，更方便
        System.out.println(search1(nums, 4));

        // 给定一个包含 n 个整数的排序数组，找出给定目标值 target 的起始和结束位置。 如果目标值不在数组中，则返回[-1, -1]
        int[] numsRange = new int[]{1, 2, 3, 4, 4, 4, 4, 5};
        Arrays.stream(searchRange(numsRange, 4)).forEach(System.out::print);
    }
}
