package com.lzq.othellogame;


public class GameUtils {
	private static int a[][];
	private static int mCount;

	public static void init(int chessBoard[][],int count) {
		// TODO Auto-generated method stub
		//1白色 2黑色
		mCount = count;
		a = chessBoard;
		int index = mCount/2-1;
		for (int i = 0; i < mCount; i++) {
			for (int j = 0; j < mCount; j++) {
				if((i==index&j==index)||(i==index+1&j==index+1))
				{
					a[i][j] = 1;
				}else if((i==index&j==index+1)||(i==index+1&j==index)){
					a[i][j] = 2;
				}else
				{
					a[i][j] = 0;
				}
			}
		}
	}
	public static boolean removeUp(int x, int y, int z)
	{
		if (a[x][y]==0) {
			a[x][y]=z;
			if ((rightUp(x, y, z))||(rightDown(x, y, z))||(leftDown(x, y, z))||(leftUp(x, y, z))
					||(xDown(x, y, z))||(xUp(x, y, z))||(yLeft(x, y, z))||(yRight(x, y, z))) {
				
				rightDown(x, y, z);
				rightUp(x, y, z);
				xDown(x, y, z);
				xUp(x, y, z);
				yLeft(x, y, z);
				yRight(x, y, z);
				leftDown(x, y, z);
				leftUp(x, y, z);
				return true;
			}else
			{
				a[x][y]=0;
			}
		}
		return false;
	}
	public static void upDate()
	{
		for (int j = 0; j < mCount; j++) {
			for (int j2 = 0; j2 < mCount; j2++) {
				System.out.print(a[j][j2]+" ");
			}
			System.out.println();
		}
	}
	public static int end()
	{
		int red = 0;
		int black = 0;
		int sucess = 0;
		for (int i = 0; i < mCount; i++) {
			for (int j = 0; j < mCount; j++) {
				if(a[i][j] == 2) red++;
				if (a[i][j] == 1) black++;	
			}
			
		}
		if(red+black==64)
		{
			if (red>black) {
				sucess =2;
			}else if(red<black)
			{
				sucess =1;
			}else
			{
				sucess =3;
			}
		}
		return sucess;
	}
	public static boolean xDown(int x , int y,int z)
	{
		for (int i = x; i < mCount; i++) {
			if (i<mCount) {
				if (a[i][y]!=z&&a[i][y]!=0) 
				{
					continue;
				}else if(a[i][y]==z&&i!=x+1&&i!=x)
				{
					for (int j = x; j <= i; j++) {
						a[j][y]=z;
					}
					//System.out.println("success");
					return true;
				}else if(a[i][y]==0){
					break;
				}else if(a[i][y]==z&&i==x+1)
				{
					break;
				}
				
			}else{
				break;
			}
		}
		//System.out.println("lose");
		return false;
	}
	public static boolean xUp(int x , int y,int z)
	{
		for (int i = x; i >= 0; i--) {
			if (i>=0) {
				if (a[i][y]!=z&&a[i][y]!=0) 
				{
					continue;
				}else if(a[i][y]==z&&i!=x-1&&i!=x)
				{
					for (int j = x; j >= i; j--) {
						a[j][y]=z;
					}
					//System.out.println("success");
					return true;
				}else if(a[i][y]==0){
					break;
				}else if(a[i][y]==z&&i==x-1)
				{
					break;
				}
				
			}else{
				break;
			}
		}
		//System.out.println("lose");
		return false;
	}
	public static boolean yRight(int x , int y,int z)
	{
		for (int i = y; i < mCount; i++) {
			if (i<mCount) {
				if (a[x][i]!=z&&a[x][i]!=0) 
				{
					continue;
				}else if(a[x][i]==z&&i!=y+1&&i!=y)
				{
					for (int j = y; j <= i; j++) {
						a[x][j]=z;
					}
					//System.out.println("success");
					return true;
				}else if(a[x][i]==0){
					break;
				}else if(a[x][i]==z&&i==y+1)
				{
					break;
				}
				
			}else{
				break;
			}
		}
		//System.out.println("lose");
		return false;
	}
	public static boolean yLeft(int x , int y,int z)
	{
		for (int i = y; i >= 0; i--) {
			if (i>=0) {
				if (a[x][i]!=z&&a[x][i]!=0) 
				{
					continue;
				}else if(a[x][i]==z&&i!=y-1&&i!=y)
				{
					for (int j = y; j >= i; j--) {
						a[x][j]=z;
					}
					//System.out.println("success");
					return true;
				}else if(a[x][i]==0){
					break;
				}else if(a[x][i]==z&&i==y-1)
				{
					break;
				}
				
			}else{
				break;
			}
		}
		return false;
	}
	public static boolean leftUp(int x , int y,int z)
	{
		for (int i = y,j = x; i >= 0&&j>=0; i--,j--) {
			if (i>=0&&j>=0) {
				if (a[j][i]!=z&&a[j][i]!=0) 
				{
					continue;
				}else if(a[j][i]==z&&(i!=y-1&&j!=x-1)&&(i!=y&&j!=x))
				{
					for (int j1=x,j2 = y; j2 >= i&&j1>=j; j2--,j1--) {
						a[j1][j2]=z;
					}
					//System.out.println("success");
					return true;
				}else if(a[j][i]==0){
					break;
				}else if(a[j][i]==z&&(i==y-1&&j==x-1))
				{
					break;
				}
				
			}else{
				break;
			}
		}
		return false;
	} 
	public static boolean rightUp(int x , int y,int z)
	{
		for (int i = y,j = x; i < mCount&&j>=0; i++,j--) {
			if (i<mCount&&j>=0) {
				if (a[j][i]!=z&&a[j][i]!=0) 
				{
					continue;
				}else if(a[j][i]==z&&(i!=y+1&&j!=x-1)&&(i!=y&&j!=x))
				{
					for (int j1=x,j2 = y; j2 <= i&&j1>=j; j2++,j1--) {
						a[j1][j2]=z;
					}
					//System.out.println("success");
					return true;
				}else if(a[j][i]==0){
					break;
				}else if(a[j][i]==z&&(i==y+1&&j==x-1))
				{
					break;
				}
				
			}else{
				break;
			}
		}
		return false;
	} 
	public static boolean leftDown(int x , int y,int z)
	{
		for (int i = y,j = x; i >= 0&&j<mCount; i--,j++) {
			if (i>=0&&j<mCount) {
				if (a[j][i]!=z&&a[j][i]!=0) 
				{
					continue;
				}else if(a[j][i]==z&&(i!=y-1&&j!=x+1)&&(i!=y&&j!=x))
				{
					for (int j1=x,j2 = y; j2 >= i&&j1<=j; j2--,j1++) {
						a[j1][j2]=z;
					}
					//System.out.println("success");
					return true;
				}else if(a[j][i]==0){
					break;
				}else if(a[j][i]==z&&(i==y-1&&j==x+1))
				{
					break;
				}
				
			}else{
				break;
			}
		}
		return false;
	} 
	public static boolean rightDown(int x , int y,int z)
	{
		for (int i = y,j = x; i<mCount&&j<mCount; i++,j++) {
			if (i<mCount&&j<=mCount) {
				if (a[j][i]!=z&&a[j][i]!=0) 
				{
					continue;
				}else if(a[j][i]==z&&(i!=y+1&&j!=x+1)&&(i!=y&&j!=x))
				{
					for (int j1=x,j2 = y; j2<=i&&j1<=j; j2++,j1++) {
						a[j1][j2]=z;
					}
					return true;
				}else if(a[j][i]==0){
					break;
				}else if(a[j][i]==z&&(i==y+1&&j==x+1))
				{
					break;
				}
				
			}else{
				break;
			}
		}
		return false;
	}
}
