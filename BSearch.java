
 
public class BSearch {

    
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            
            if (arr[mid] > target) {
                right = mid - 1;
            }

           
            else {
                left = mid + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        int target = 23;

        int result = binarySearch(arr, target);

        if (result != -1) {
            System.out.println("Target element found at index " + result);
        } else {
            System.out.println("Target element not found in the array");
        }
    }
}