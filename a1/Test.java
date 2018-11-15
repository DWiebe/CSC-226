public class Test{

	public static void main(String[] args){
		int low = 0;
		int high = 49;
		int[] A = new int[50];
		
		int y = 49;
		for(int x = 0; x < 50; x++){
			A[x] = y--;
		}	
		
		int[] medians = new int[(high - low) / 7 + 1];
		
		for(int a = 0; a < (high - low) / 7; a++){
			for(int b = low + 7 * a; b < low + 7 * (a + 1) - 1; b++){
				for(int c = low + 7 * a; c < low + 7 * (a + 1) - 1; c++){
					if(A[c] > A[c + 1]){
						swap(A, c, c + 1);
						System.out.println("yes: a = " + a + ", b = " + b + ", c = " + c);
					} else{
						System.out.println("no: a = " + a + ", b = " + b + ", c = " + c);
					}
				}
			}
			medians[a] = low + 7 * a + 4;
		}
		
		for(int a = low + ((high - low) / 7) * 7; a < high; a++){
			for(int b = high; b > a; b--){
				if(A[b] < A[b - 1]){
					swap(A, b, b - 1);
				}
			}
		}
		
		medians[medians.length - 1] = low + (((high - low) / 7) * 7) +  (high - (((high - low) / 7) * 7)) / 2;
		
		
		
		for(int a = 0; a < medians.length; a++){
			for(int b = 0; b < medians.length - a - 1; b++){
				if(A[medians[b]] > A[medians[b + 1]]){
					swap(medians, b, b + 1);
					System.out.println("swap");
				}
			}
		}
		
		for(int x = 0; x < A.length; x++){
			System.out.print(A[x] + ", ");
		}
		System.out.println();
		for(int x = 0; x < medians.length; x++){
			System.out.println(medians[x]);
		}
		System.out.println(medians[medians.length / 2]);
	
	}
	private static void swap(int[] A, int x, int y){
		//swaps elements at index x and y in array A
		int temp = A[x];
		A[x] = A[y];
		A[y] = temp;
	
	}
}


	