import java.util.*;
import java.lang.*;
public class maxheap{
	public static boolean insert(int[] a,int n){
		int i=n;
		int item=a[n];
		while(i>1 && item>a[Math.floorDiv(i,2)]){
			a[i]=a[Math.floorDiv(i,2)];
			i=Math.floorDiv(i,2);
		}
		a[i]=item;
		return true;
	}
	public static int delMax(int a[],int i,int n){
		int x;
		if(n==0){
			System.out.println("Heap is empty");
			return -1;
		}
		x=a[1];
		a[i]=a[n];
		adjust(a,1,n-1);
		return x;
	}
	public static void adjust(int[]a, int i,int n){
		int j=2*i;
		int item=a[i];
		while(j<=n){
			if(j<n && (a[j]<a[j+1]))
				j=j+1;
			if(item>=a[j])
				break;
			a[Math.floorDiv(j,2)]=a[j];
			j=2*j;
		}
		a[Math.floorDiv(j,2)]=item;
	}	
		
	
	
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
        
		System.out.print("Enter the number of elements to insert into the heap: ");
		int numElements = scanner.nextInt();
		

		int[] a = new int[numElements + 1];
		
	       
		System.out.println("Enter the elements:");
		for (int i = 1; i <= numElements; i++) {
		    a[i] = scanner.nextInt();
		    insert(a, i);
		}
		/*for(int i=1;i<a.length;i++){
			insert(a,i);
		}*/
		for(int i=1;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		int del=delMax(a,1,a.length-1);
		System.out.println("Deleted element is :"+del);
		System.out.println("after deletion heap is:");
		for(int i=1;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
	}
}
