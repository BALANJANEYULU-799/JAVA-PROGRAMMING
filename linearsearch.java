import java.util.Scanner;
public class linearsearch{
    public static int linearsearch(int[] arr,int target){
for(int i=0;i<arr.length;i++)
    if(arr[i]==target)
    {
        return i;
    }
return -1;

    }

public static void main(String[] args)
{
    Scanner in=new Scanner(System.in);
    System.out.println("enter the size of the array");
    int n=in.nextInt();
    int arr[]=new int[n];
    System.out.println("enter the elements of the array:");
    for(int i=0;i<arr.length;i++){
        arr[i]=in.nextInt();
    }
    System.out.println("enter the targeted number to search");
    int target=in.nextInt();
    int result=linearsearch(arr,target);
    if(result!=-1)
    {
        System.out.println("element is found at index"+result);
    }
    else
        System.out.println("element not found");
}}