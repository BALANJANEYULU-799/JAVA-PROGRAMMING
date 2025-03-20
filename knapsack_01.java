//using Dynamic programming
import java.util.*;
public  class knapsack_01{
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		System.out.println("enter the no.of objects:");
		int n=sc.nextInt();
		int w[]=new int[n];
		int p[]=new int[n];
		System.out.println("Enter the weight And Profit of each object:");
		for(int i=0;i<n;i++){
			w[i]=sc.nextInt();
			p[i]=sc.nextInt();
		}
		System.out.println("enter the capacity of bag:");
		int m=sc.nextInt();
		int maxProfit=maxValue(w,p,m,n);
		System.out.println("MAXIMU PROFIT::"+maxProfit);
	}
	public static int maxValue(int w[],int p[],int m,int n){
		int k[][]=new int[n+1][m+1];
		for(int i=0;i<=n;i++){
			for(int j=0;j<=m;j++){
				if(i==0||j==0){
					k[i][j]=0;
				}else if(w[i-1]<=j){
					k[i][j]=Math.max(k[i-1][j],k[i-1][j-w[i-1]]+p[i-1]);
				}else{
					k[i][j]=k[i-1][j];
				}
			}
		}
		return k[n][m];
	}
}
			
				
