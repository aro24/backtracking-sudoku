package a3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku {
	static int[][] original=new int [9][9]; //need this for next
	static void saveOriginal(int[][] board){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				original[i][j]=board[i][j];
			}
		}
	}
    static boolean isFullSolution(int[][] board) {
        // TODO: Complete this method
    	for(int i=0;i<9;i++){
    		for(int j=0;j<9;j++){
    			if(board[i][j]==0){
    				return false;

    			}
    		}
    	}
	return true;
    }

    static boolean reject(int[][] board) {
        // TODOKETE: Complete this method
    	List<Integer> remaining = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    	for(int i=0;i<9;i++){
    		for(int j=0;j<9;j++){
    			if(board[i][j]!=0&&remaining.contains(new Integer(board[i][j]))){
    				remaining.remove(new Integer(board[i][j]));
    			}
    			else if(board[i][j]==0);
    			else{
    				return true;
    			}
    		}
    		remaining = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    	}
    	for(int k=0;k<9;k++){
    		for(int l=0;l<9;l++){
    			if(board[l][k]!=0&&remaining.contains(board[l][k])){
    				remaining.remove(new Integer(board[l][k]));
    			}
    			else if(board[l][k]==0); //Don't need to do anything with 0s
    			else{
    				return true;
    			}
    		}
    		remaining = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    	}
    	for(int m=0;m<9;m++){
    		remaining = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    		int x=0, y=0, x2=0, y2=0;
    		if(m==0){
    			x=0;
    			y=0;
    		}
    		if(m==1){
    			x=3;
    			y=0;
    		}
    		if(m==2){
    			x=6;
    			y=0;
    		}
    		if(m==3){
    			x=0;
    			y=3;
    		}
    		if(m==4){
    			x=3;
    			y=3;
    		}
    		if(m==5){
    			x=6;
    			y=3;
    		}
    		if(m==6){
    			x=0;
    			y=6;
    		}
    		if(m==7){
    			x=3;
    			y=6;
    		}
    		if(m==8){
    			x=6;
    			y=6;
    		}
    		int xtemp=x;
    		x2=x+3;
    		y2=y+3;
    		for(;y<y2;y++){
    			for(;x<x2;x++){
    				if(remaining.contains(board[y][x])&&board[y][x]!=0){
    					remaining.remove(new Integer(board[y][x]));
    				}
    				else if (board[y][x]==0);
    				else{
    					return true;
    				}
    			}
    			x=xtemp;
    		}
    	}
        return false;
    }

    static int[][] extend(int[][] board) {
        // TODO: Complete this method
    	int [][] reBoard=new int [9][9];
    	for(int h=0;h<board.length;h++){
    		reBoard[h]=Arrays.copyOf(board[h], board[h].length);
    	}
    	boolean addition=false;
    	int nX=0;
    	int nY=0;
    	List<Integer> remaining = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    	for(int i=0;i<9;i++){
    		for(int j=0;j<9;j++){
    			if(remaining.contains(reBoard[i][j])){
    				remaining.remove(new Integer(reBoard[i][j]));
    			}
    			if(reBoard[i][j]==0 && !addition){
    				nX=j;
    				nY=i;
    				addition=true;
    				//NO BREAKS BECAUSE WE GOTTA GET THOSE OTHER NUMBERS OUT OF THE WAY BRUH
    			}
    		}
    		///Then we add the number in slot 0 of remaining to a new board. Should skip unnecessary rejects for row checking. 
    		//Runtime for method might not be the best, but should definitely be better overall, since we won't be hitting as many dead-ends.
    		if(addition && !remaining.isEmpty()){
    			reBoard[nY][nX]=remaining.get(0);
    			return reBoard;
    		}
    		remaining = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    	}
    		
        return null;
    }

    static int[][] next(int[][] board) {
        // TODO: Complete this method
    	int [][] reBoard=new int [9][9];
    	for(int h=0;h<board.length;h++){
    		reBoard[h]=Arrays.copyOf(board[h], board[h].length);
    	}
    	for(int i=8;i>=0;i--){
    		for(int j=8;j>=0;j--){
    			if(original[i][j]==0&&reBoard[i][j]!=0){
    				if(board[i][j]!=9){
    					int temp=reBoard[i][j];
    					temp++;
    					reBoard[i][j]=temp;
    					return reBoard;
    				}
    				else{
    					return null; //in case newest value=9, and thus can't be modified further
    				}
    			}
    		}
    	}
        return null; //in case no changes were found?
    }

    static void testIsFullSolution() {
        // TODO: Complete this method
    	System.out.println("Full Solution test");
    	int[][]tester=readBoard("easy.su");
    	printBoard(tester);
 
    	System.out.println();
    	System.out.println(isFullSolution(tester));
    }

    static void testReject() {
        // TODO: Complete this method
    	System.out.println("Reject test");
    	int[][]tester=readBoard("easy.su");
    	System.out.println("This board is valid");
    	printBoard(tester);
    	System.out.println();
    	System.out.println(reject(tester));
    	tester=extend(tester);
    	tester=next(tester);
    	System.out.println("This board which was extended and went thru next is invalid");
    	printBoard(tester);
    	System.out.println(reject(tester));
    }

    static void testExtend() {
        // TODO: Complete this method
    	System.out.println("Extend test");
    	int[][]tester=readBoard("easy.su");
    	System.out.println("This was the original board");
    	printBoard(tester);
    	System.out.println();
    	tester=extend(tester);
    	System.out.println("This board was extended once");
    	printBoard(tester);
    	System.out.println();
    	
    }

    static void testNext() {
        // TODO: Complete this method
    	System.out.println("Next test");
    	int[][]tester=readBoard("easy.su");
    	System.out.println("This board was extended once");
    	tester=extend(tester);
    	printBoard(tester);
    	System.out.println();
    	
    	tester=extend(tester);
    	printBoard(tester);
    	System.out.println();
    	
    	tester=next(tester);
    	System.out.println("This board was extended twice, and also called next");
    	printBoard(tester);
    }

    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("No assignment");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        saveOriginal(board); //new line added to readBoard in order for next to work
        return board;
    }

    static int[][] solve(int[][] board) {
        if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        int[][] attempt = extend(board);
        while (attempt != null) {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }

    public static void main(String[] args) {
        if (args[0].equals("-t")) {
           	testIsFullSolution();
            testReject();
            testExtend();
            testNext();

        } else {
            int[][] board = readBoard(args[0]);
            printBoard(solve(board));
        }
    }
}