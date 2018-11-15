/*
David Wiebe
V00875342
QuickSelect.java
Oct 1 2018
*/
/**
 *
 * @author Rahnuma Islam Nishat
 * September 19, 2018
 * CSC 226 - Fall 2018
 */
import java.util.*;
import java.io.*;

public class QuickSelect {
    
    
    public static int QuickSelect(int[] A, int k) {
		
		int low = 0;	//low index for the current working section
		int high = A.length - 1;	//high index for the current working section
		int pivot = -1;
		
		while(pivot != k - 1 && low != high){
			
			pivot = getMedian(A, low, high);	//find the median of medians	
			pivot = partition(A, low, high, pivot);	//partition the list using the pivot
			
			//adjust the high or low index based on the partition
			if(k - 1 < A[pivot]){
				high = A[pivot] - 1;
			} else{
				low = A[pivot] + 1;
			}
		}
		
        return A[pivot];
    }

	private static int getMedian(int[] A, int low, int high){
		//returns the index of the median of medians in array A
		
		int[] medians = new int[(high - low) / 7 + 1];	//create list for the medians
		
		
		if(high - low >= 7){	//if the working section has at least 7 elements
		
			//for each group of 7 elements, sort them and find the median
			for(int a = 0; a < (high - low) / 7; a++){
				for(int b = low + 7 * a; b < low + 7 * (a + 1) - 1; b++){
					for(int c = low + 7 * a; c < low + 7 * (a + 1) - 1; c++){
						if(A[c] > A[c + 1]){
							swap(A, c, c + 1);
						}
					}
				}
				medians[a] = low + 7 * a + 3;	//add the median to the medians list
			}
		}
		
		//sorts the remaining group of less than 7 elements
		for(int a = low + ((high - low) / 7) * 7; a < high; a++){
			for(int b = high; b > a; b--){
				if(A[b] < A[b - 1]){
					swap(A, b, b - 1);
				}
			}
		}
		
		//add the median of the remaining group to the list of medians
		medians[medians.length - 1] = low + (((high - low) / 7) * 7) +  (high - (((high - low) / 7) * 7)) / 2;
		
		//sort the list of medians and find the median of medians
		for(int a = 0; a < medians.length; a++){
			for(int b = 0; b < medians.length - a - 1; b++){
				if(A[medians[b]] > A[medians[b + 1]]){
					swap(medians, b, b + 1);
				}
			}
		}
		return medians[medians.length / 2];
		
	}
	
	private static int partition(int[] A, int low, int high, int pivot){
		//partitions the array within the low and high limits about the pivot
		//standard quicksort partition step
		swap(A, pivot, high);
		pivot = high;
		
		int a = low;
		int b = high - 1;
		while(a != b){
			if(A[a] > A[pivot] && A[b] < A[pivot]){
				swap(A, a, b);
			} else if(A[a] > A[pivot] && A[b] > A[pivot]){
				b--;
			} else{
				a++;
			}
			
		}
		swap(A, a, pivot);
		pivot = a;
		return pivot;
	}
	
	private static void swap(int[] A, int x, int y){
		//swaps elements at index x and y in array A
		int temp = A[x];
		A[x] = A[y];
		A[y] = temp;
	
	}
    
    public static void main(String[] args) {
        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
	    try{
		s = new Scanner(new File(args[0]));
		int n = s.nextInt();
		array = new int[n];
		for(int i = 0; i < n; i++){
		    array[i] = s.nextInt();
		}
	    } catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n", args[0]);
        }
	else {
	    s = new Scanner(System.in);
	    System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
	    int temp = s.nextInt();
	    ArrayList<Integer> a = new ArrayList();
	    while(temp >= 0) {
		a.add(temp);
		temp=s.nextInt();
	    }
	    array = new int[a.size()];
	    for(int i = 0; i < a.size(); i++) {
		array[i]=a.get(i);
	    }
	    
	    System.out.println("Enter k");
        }
        k = s.nextInt();
        System.out.println("The " + k + "th smallest number is the list is "
			   + QuickSelect(array,k));	
    }
}
