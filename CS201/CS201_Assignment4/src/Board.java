import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Board implements Serializable {

	private static final long serialVersionUID = -6512839843278646488L;
	
	//Uses even columns for word number and even for the actual word
	protected Tile[][] board;
	
	protected int boardSize = 40;
	
	//Map of all words
	//Keys: Number A/D ... ex. 1D, 2D, 3D, 1A, 4A, 5A
	//Value: Vector of (1) Answer, (2) Question
	protected Map<String, Vector<String>> boardEntry = new ConcurrentHashMap<String, Vector<String>>();
	
	//List of tiles that still need to be placed
	Vector<String> downWordsRemaining = new Vector<String>();
	Vector<String> acrossWordsRemaining = new Vector<String>();
	
	//Remove this (For testing sending board)
	public Board(String s) {
		System.out.println("Generating board");
		board = new Tile[boardSize][boardSize];
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				board[i][j] = new Tile(j, i);
				board[i][j].tileValue = (char)(Math.random()*('z' - 'a')+'a');
			}
		}
		
		if(!chooseBoardFile())
			System.out.println("All board files are invalid");
		System.out.println("BOARD WORKED");
		
		//Fill the across and down vectors
		for(Map.Entry<String, Vector<String>> entry: boardEntry.entrySet()) {
			if(entry.getKey().contains("D"))
				downWordsRemaining.add(entry.getValue().get(0));
			else
				acrossWordsRemaining.add(entry.getValue().get(0));
		}
	}
	
	public Board() {	
		if(!chooseBoardFile())
			System.out.println("All board files are invalid");
		System.out.println("BOARD WORKED");
		
		for(Map.Entry<String, Vector<String>> entry: boardEntry.entrySet())
			System.out.println(entry.getKey() + " " + entry.getValue().get(0) + " " + entry.getValue().get(1));
		
		generateBoard();
		
		printBoard();
	}
	
	private boolean generateBoard() {
		//Create a very large array of null tiles
		board = new Tile[boardSize][boardSize];
		
		for(int i = 0; i < boardSize; i++)
			for(int j = 0; j < boardSize; j++)
				board[i][j] = new Tile(j, i);

		//Fill the across and down vectors
		for(Map.Entry<String, Vector<String>> entry: boardEntry.entrySet()) {
			if(entry.getKey().contains("D"))
				downWordsRemaining.add(entry.getValue().get(0));
			else
				acrossWordsRemaining.add(entry.getValue().get(0));
		}

		boolean finished = false;

		//Try all across combinations
		for(int acrossTimes = 0; acrossTimes < acrossWordsRemaining.size() && !finished; acrossTimes++) {
			String acrossWord = acrossWordsRemaining.remove(0);
			for(int ypos = 0; ypos < boardSize && !finished; ypos++) {
				for(int xpos = 0; xpos < boardSize && !finished; xpos++) {
					if(placeAcross(acrossWord, xpos, ypos)) {
						if(buildFromAcross(board[ypos][xpos]))
							//We got a working one
							finished = true;
					}
				}
			}
			acrossWordsRemaining.add(acrossWord);
		}
		
		
		//Try all down combinations
		for(int downTimes = 0; downTimes < downWordsRemaining.size() && !finished; downTimes++) {
			String downWord = downWordsRemaining.remove(0);
			for(int ypos = 0; ypos < boardSize && !finished; ypos++) {
				for(int xpos = 0; xpos < boardSize && !finished; xpos++) {
					if(placeDown(downWord, xpos, ypos)) {
						if(buildFromDown(board[ypos][xpos]))
							//We got a working one
							finished = true;
					}
				}
			}
			downWordsRemaining.add(downWord);
		}
		
		if(!finished)
			System.out.println("Unable to generate a board");
		else
			System.out.println("Finished");
		
		
		printBoard();
		
		return true;
	}

	
	private boolean buildFromDown(Tile tile) {
		if(acrossWordsRemaining.size() == 0 ^ downWordsRemaining.size() == 0) {
			board[tile.yposition][tile.xposition].removeDownSameWord();
			return false;
		}
		if(acrossWordsRemaining.size() == 0 && downWordsRemaining.size() == 0)
			return true;
		Tile current = tile;
		//For all the letters in the placed down word
		while(current != null) {
			//For all the remaining across words
			Vector<String> acrossWordsTried = new Vector<String>(acrossWordsRemaining);
			while(acrossWordsTried.size() != 0) {
				String currentWord = acrossWordsTried.remove(0);
				acrossWordsRemaining.remove(currentWord);
				//For every letter in this word
				for(int currentChar = 0; currentChar < currentWord.length(); currentChar++) {
					if(placeAcross(currentWord, current.xposition-currentChar, current.yposition)) {
						if(current.yposition >= 0 && current.yposition < boardSize && current.xposition-currentChar >= 0 && current.xposition-currentChar < boardSize)
							if(buildFromAcross(board[current.yposition][current.xposition-currentChar]))
								return true;
					}
							
				}
				acrossWordsRemaining.add(currentWord);
			}
			current = current.downNextTile;
		}
		//If we're here it didn't work
		//Remove the word we just placed
		board[tile.yposition][tile.xposition].removeDownSameWord();
		return false;
		
	}
	
	private boolean buildFromAcross(Tile tile) {
		if(acrossWordsRemaining.size() == 0 ^ downWordsRemaining.size() == 0) {
			board[tile.yposition][tile.xposition].removeAcrossSameWord();
			return false;
		}
		if(acrossWordsRemaining.size() == 0 && downWordsRemaining.size() == 0)
			return true;
		Tile current = tile;
		//For all the letters in the placed across word
		while(current != null) {
			//For all the remaining down words
			Vector<String> downWordsTried = new Vector<String>(downWordsRemaining);
			while(downWordsTried.size() != 0) {
				String currentWord = downWordsTried.remove(0);
				downWordsRemaining.remove(currentWord);
				//For every letter in this word
				for(int currentChar = 0; currentChar < currentWord.length(); currentChar++) {
					if(placeDown(currentWord, current.xposition, current.yposition-currentChar)) {
						if(current.yposition >= 0 && current.yposition < boardSize && current.xposition-currentChar >= 0 && current.xposition-currentChar < boardSize)
							if(buildFromDown(board[current.yposition-currentChar][current.xposition]))
								return true;
					}
				}
				downWordsRemaining.add(currentWord);
			}
			current = current.acrossNextTile;
		}
		//If we're here it didn't work
		//Remove what we just placed
		board[tile.yposition][tile.xposition].removeAcrossSameWord();
		return false;
	}
	
	private void printBoard() {
		//Print board
		for(Tile[] row: board) {
			for(Tile col: row) {
				if(col.tileValue == 0)
					System.out.print("_");
				else
					System.out.print(col.tileValue);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	
	private boolean placeDown(String word, int xpos, int ypos) {
		int i;
		boolean added = false;
		for(i = ypos; i < word.length() + ypos; i++) {
			if(isValid(word.charAt(i-ypos), true, xpos, i)) {				
				if(i == ypos) {
					if(i+1 >= boardSize){
						if(added) {
							if(i >= ypos)
								board[ypos][xpos].removeDownSameWord();
							else
								board[ypos][xpos].removeDownDifferentWord();
						}
						return false;
					}
					if(!board[i][xpos].addDown(word, word.charAt(i-ypos), null, board[i+1][xpos])) {
						if(added) {
							if(i >= ypos)
								board[ypos][xpos].removeDownSameWord();
							else
								board[ypos][xpos].removeDownDifferentWord();
						}
						return false;
					}
					added = true;
				}
				else if(i == (word.length()+ypos-1)) {
					if(i-1 < 0){
						if(added) {
							if(i >= ypos)
								board[ypos][xpos].removeDownSameWord();
							else
								board[ypos][xpos].removeDownDifferentWord();
						}
						return false;
					}
					if(!board[i][xpos].addDown(word, word.charAt(i-ypos), board[i-1][xpos], null)) {
						if(added) {
							if(i >= ypos)
								board[ypos][xpos].removeDownSameWord();
							else
								board[ypos][xpos].removeDownDifferentWord();
						}
						return false;
					}
					added = true;
				}
				else {
					if(i+1 >= boardSize || i-1 < 0) {
						if(added) {
							if(i >= ypos)
								board[ypos][xpos].removeDownSameWord();
							else
								board[ypos][xpos].removeDownDifferentWord();
						}
						return false;
					}
					if(!board[i][xpos].addDown(word, word.charAt(i-ypos), board[i-1][xpos], board[i+1][xpos])) {
						if(added) {
							if(i >= ypos)
								board[ypos][xpos].removeDownSameWord();
							else
								board[ypos][xpos].removeDownDifferentWord();
						}
						return false;
					}
					added = true;
				}
			}
			else {
				if(added) {
					if(i >= ypos)
						board[ypos][xpos].removeDownSameWord();
					else
						board[ypos][xpos].removeDownDifferentWord();
				}
				return false;
			}
		}
		//TODO Remove this
		printBoard();
		return true;
	}
	
	
	private boolean placeAcross(String word, int xpos, int ypos) {
		int i;
		boolean added = false;
		for(i = xpos; i < word.length() + xpos; i++) {
			if(isValid(word.charAt(i-xpos), false, i, ypos)) {
				if(i == xpos) {
					if(i+1 >= boardSize){
						if(added) {
							if(i >= xpos)
								board[ypos][xpos].removeAcrossSameWord();
							else
								board[ypos][xpos].removeAcrossDifferentWord();
						}
						return false;
					}
					if(!board[ypos][i].addAcross(word, word.charAt(i-xpos), null, board[ypos][i+1])) {
						if(added) {
							if(i >= xpos)
								board[ypos][xpos].removeAcrossSameWord();
							else
								board[ypos][xpos].removeAcrossDifferentWord();
						}
						return false;
					}
					added = true;
				}
				else if(i == (word.length()+xpos-1)) {
					if(i-1 < 0) {
						if(added) {
							if(i >= xpos)
								board[ypos][xpos].removeAcrossSameWord();
							else
								board[ypos][xpos].removeAcrossDifferentWord();
						}
						return false;
					}
					if(!board[ypos][i].addAcross(word, word.charAt(i-xpos), board[ypos][i-1], null)) {
						if(added) {
							if(i >= xpos)
								board[ypos][xpos].removeAcrossSameWord();
							else
								board[ypos][xpos].removeAcrossDifferentWord();
						}
						return false;
					}
					added = true;
				}
				else {
					if(i+1 >= boardSize || i-1 < 0) {
						if(added) {
							if(i >= xpos)
								board[ypos][xpos].removeAcrossSameWord();
							else
								board[ypos][xpos].removeAcrossDifferentWord();
						}
						return false;
					}
					if(!board[ypos][i].addAcross(word, word.charAt(i-xpos), board[ypos][i-1], board[ypos][i+1])) {
						if(added) {
							if(i >= xpos)
								board[ypos][xpos].removeAcrossSameWord();
							else
								board[ypos][xpos].removeAcrossDifferentWord();
						}
						return false;
					}
					added = true;
				}
			}
			else {
				if(added) {
					if(i >= xpos)
						board[ypos][xpos].removeAcrossSameWord();
					else
						board[ypos][xpos].removeAcrossDifferentWord();
				}
				return false;
			}
		}
		//TODO Remove this
		printBoard();
		return true;
	}
	
	private boolean isValid(char letter, boolean isDown, int xpos, int ypos) {
		//If it's out of bounds automatic nono
		if(xpos < 0 || xpos >= boardSize || ypos < 0 || ypos >= boardSize)
			return false;
		
		//Automatically fail if the tile is already a double tile
		if(board[ypos][xpos].is2DTile())
			return false;
		
		//Automatically work if it's a null tile
		if(board[ypos][xpos].isNullTile())
			return true;
		
		if(isDown) {
			//If tile already has a down no bueno
			if(board[ypos][xpos].isDownTile())
				return false;
			//If tile has an across and it's the same character we're good
			if(board[ypos][xpos].tileValue == letter)
				return true;
			//If tile has an across and it's NOT the same character no bueno
			return false;
		}
		else {
			//If tile already has an across no bueno
			if(board[ypos][xpos].isAcrossTile())
				return false;
			//If tile has a down and it's the same character we're good
			if(board[ypos][xpos].tileValue == letter)
				return true;
			//If tile has a down and it's NOT the same character no bueno
			return false;
		}
	}

	
	private boolean parseInputFile(File selected) {
		Scanner sc = null;
		try {
			sc = new Scanner(selected);
			boolean isAcross = true;
			String line;
			while((line = sc.nextLine()) != null) {
				if(line.equals("ACROSS")) {
					isAcross = true;
					if(!sc.hasNextLine())
						return false;
					line = sc.nextLine();
					if(line.equals("DOWN")) {
						isAcross = false;
						if(!sc.hasNextLine())
							return false;
						line = sc.nextLine();
					}
				}
				else if(line.equals("DOWN")) {
					isAcross = false;
					if(!sc.hasNextLine())
						return false;
					line = sc.nextLine();
					if(line.equals("ACROSS")) {
						isAcross = true;
						if(!sc.hasNextLine())
							return false;
						line = sc.nextLine();
					}
				}
				if(!parseLine(line, isAcross))
					return false;
			}
			
		} catch(FileNotFoundException fnfe) {
			System.out.println("Board.parseInputFile - Selected file could not be found");
		} catch(NoSuchElementException nsee) {
			return true;
		}
		finally {
			if(sc != null)
				sc.close();
		}
		return true;
	}
	
	private boolean parseLine(String line, boolean isAcross) {
		StringTokenizer st = new StringTokenizer(line, "|");
		
		// Also deals with multiple "ACROSS" or "DOWN" in input file
		if(st.countTokens() != 3)
			return false;
		
		// Check if first entry is an int
		int number;
		try {
			number = Integer.parseInt(st.nextToken());
		} catch (Exception ex) {
			return false;
		}
				
		// Check if answer is one word
		String answer = st.nextToken();
		if(answer.contains(" "))
			return false;
				
		if(isAcross) {
			// Check if start letter matches of other direction of same number 
			if(boardEntry.containsKey(number + "D")) {
				if(boardEntry.get(number + "D").get(0).charAt(0) == answer.charAt(0))
					boardEntry.put(number + "A", new Vector<String>(Arrays.asList(answer, st.nextToken())));
				else
					return false;
			}
			else
				boardEntry.put(number + "A", new Vector<String>(Arrays.asList(answer, st.nextToken())));
		}
		else {
			// Check if start letter matches of other direction of same number 
			if(boardEntry.containsKey(number + "D"))
				if(boardEntry.get(number + "D").get(0).charAt(0) == answer.charAt(0))
					boardEntry.put(number + "A", new Vector<String>(Arrays.asList(answer, st.nextToken())));
				else
					return false;
			else 
				boardEntry.put(number + "D", new Vector<String>(Arrays.asList(answer, st.nextToken())));
		}
		return true;
	}
	
	
	private boolean chooseBoardFile() {
		// Read all the files in the directory
		Vector<File> gamefiles = new Vector<File>(Arrays.asList(new File("gamedata").listFiles()));
		
		// Check if this file works
		File selected = gamefiles.get((int)(Math.random() * gamefiles.size()));
		
		while(!parseInputFile(selected) && gamefiles.size() != 0) {
			gamefiles.remove(selected);
			if(gamefiles.size() == 0)
				return false;
			selected = gamefiles.get((int)(Math.random() * gamefiles.size() + 1));
			boardEntry = new HashMap<String, Vector<String>>();
		}
		return true;
	}
	
	public static void main(String[] args) {
		Board b = new Board();
		while(true) {}
	}

	
}
	