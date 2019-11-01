import java.io.Serializable;

public class Tile implements Serializable {

	private static final long serialVersionUID = -716743153872590239L;
	protected char tileValue = 0;
	protected Tile acrossPreviousTile = null;
	protected Tile downPreviousTile = null;
	protected Tile acrossNextTile = null;
	protected Tile downNextTile = null;
	protected String horizontalWord = "";
	protected String verticalWord = "";
	protected int xposition;
	protected int yposition;
	
	
	//Makes a null tile
	public Tile(int xposition, int yposition) {
		this.xposition = xposition;
		this.yposition = yposition;
	}
	
	//Make 1D tile
	public Tile(String word, char tileValue, boolean isDown, Tile previousTile, Tile nextTile, int xposition, int yposition) {
		if(isDown) {
			this.verticalWord = word;
			this.tileValue = tileValue;
			this.downPreviousTile = previousTile;
			this.downNextTile = nextTile;
			this.xposition = xposition;
			this.yposition = yposition;
		}
		else {
			this.horizontalWord = word;
			this.tileValue = tileValue;
			this.acrossPreviousTile = previousTile;
			this.acrossNextTile = nextTile;
			this.xposition = xposition;
			this.yposition = yposition;
		}
	}
	
	protected boolean isAcrossTile() {
		return (acrossPreviousTile != null || acrossNextTile != null);
	}
	
	protected boolean isDownTile() {
		return (downPreviousTile != null || downNextTile != null);
	}
	
	protected boolean isNullTile() {
		return tileValue == 0;
	}
	
	protected boolean is2DTile() {
		return (isAcrossTile() && isDownTile());
	}
	
	//Removes all tiles of a horizontal word
	protected void removeAcrossSameWord() {
		//Go to start of horizontal word
		//Check if that's to the left or right		
		Tile start = this;
		if(this.acrossNextTile != null && this.acrossNextTile.horizontalWord.equals(this.horizontalWord))
			start = this.acrossNextTile;
		else if(this.acrossPreviousTile != null && this.acrossPreviousTile.horizontalWord.equals(this.horizontalWord))
			start = this.acrossPreviousTile;
		while(start.acrossPreviousTile != null && start.acrossPreviousTile.horizontalWord.equals(start.horizontalWord))
			start = start.acrossPreviousTile;
		
		start.removeAcrossSameWordHelper();
	}
	
	// Recursively removes all tiles from an across word
	// Called from removeAcross()
	private void removeAcrossSameWordHelper() {
		//Build a stack frame for each tile
		if(acrossNextTile != null && acrossNextTile.horizontalWord.equals(this.horizontalWord))
			acrossNextTile.removeAcrossSameWordHelper();
				
		//Remove the connecting properties of each tile
		if(!isDownTile()) 
			tileValue = 0;
		if(acrossPreviousTile != null)
			acrossPreviousTile.acrossNextTile = null;
		acrossPreviousTile = null;
		if(acrossNextTile != null)
			acrossNextTile.acrossPreviousTile = null;
		acrossNextTile = null;
		horizontalWord = "";
	}
	
	protected void removeDownSameWord() {		
		// Go to the top of horizontal word
		// Check if that's up or down
		Tile top = this;
		if(this.downPreviousTile != null && this.downPreviousTile.verticalWord.equals(this.horizontalWord))
			top = this.downPreviousTile;
		else if(this.downNextTile != null && this.downNextTile.verticalWord.equals(this.verticalWord))
			top = this.downNextTile;
		while(top.downPreviousTile != null && top.downPreviousTile.verticalWord.equals(top.verticalWord))
			top = top.downPreviousTile;
		
		top.removeDownSameWordHelper();
	}
	
	private void removeDownSameWordHelper() {
		//Build a stack from for each tile
		if(downNextTile != null && downNextTile.verticalWord.equals(this.verticalWord))
			downNextTile.removeDownSameWordHelper();
		
		//Remove the connecting properties of each tile
		if(!isAcrossTile())
			tileValue = 0;
		if(downPreviousTile != null)
			downPreviousTile.downNextTile = null;
		downPreviousTile = null;
		if(downNextTile != null)
			downNextTile.downPreviousTile = null;
		downNextTile = null;
		verticalWord = "";
	}
	
	//Removes all tiles of a horizontal word
		protected void removeAcrossDifferentWord() {
			//Go to start of horizontal word
			//Check if that's to the left or right
			Tile start = null;
			if(this.acrossNextTile != null && !this.acrossNextTile.horizontalWord.equals(this.horizontalWord))
				start = this.acrossNextTile;
			else if(this.acrossPreviousTile != null && !this.acrossPreviousTile.horizontalWord.equals(this.horizontalWord))
				start = this.acrossPreviousTile;
			else {
				System.out.println("Didn't find correct word");
				return;
			}
			while(start.acrossPreviousTile != null && !start.acrossPreviousTile.horizontalWord.equals(start.horizontalWord))
				start = start.acrossPreviousTile;
			
			start.removeAcrossDifferentWordHelper();
		}
		
		// Recursively removes all tiles from an across word
		// Called from removeAcross()
		private void removeAcrossDifferentWordHelper() {
			//Build a stack frame for each tile
			if(acrossNextTile != null && !acrossNextTile.horizontalWord.equals(this.horizontalWord))
				acrossNextTile.removeAcrossDifferentWordHelper();
					
			//Remove the connecting properties of each tile
			if(!isDownTile()) 
				tileValue = 0;
			if(acrossPreviousTile != null)
				acrossPreviousTile.acrossNextTile = null;
			acrossPreviousTile = null;
			if(acrossNextTile != null)
				acrossNextTile.acrossPreviousTile = null;
			acrossNextTile = null;
			horizontalWord = "";
		}
		
		protected void removeDownDifferentWord() {		
			// Go to the top of horizontal word
			// Check if that's up or down
			Tile top = null;
			if(this.downNextTile != null && !this.downNextTile.verticalWord.equals(this.verticalWord))
				top = this.downNextTile;
			else if(this.downPreviousTile != null && !this.downPreviousTile.verticalWord.equals(this.horizontalWord))
				top = this.downPreviousTile;
			else {
				System.out.println("Didn't find correct word");
				return;
			}
			while(top.downPreviousTile != null && !top.downPreviousTile.verticalWord.equals(top.verticalWord))
				top = top.downPreviousTile;
			
			top.removeDownDifferentWordHelper();
		}
		
		private void removeDownDifferentWordHelper() {
			//Build a stack from for each tile
			if(downNextTile != null && !downNextTile.verticalWord.equals(this.verticalWord))
				downNextTile.removeDownDifferentWordHelper();
			
			//Remove the connecting properties of each tile
			if(!isAcrossTile())
				tileValue = 0;
			if(downPreviousTile != null)
				downPreviousTile.downNextTile = null;
			downPreviousTile = null;
			if(downNextTile != null)
				downNextTile.downPreviousTile = null;
			downNextTile = null;
			verticalWord = "";
		}
	
	// A return false of this method means the isValid method did not work correctly
	protected boolean addAcross(String word, char letter, Tile acrossPreviousTile, Tile acrossNextTile) {
		if(is2DTile())
			return false;
		this.horizontalWord = word;
		if(this.tileValue == 0)
			this.tileValue = letter;
		if(acrossPreviousTile != null)
			acrossPreviousTile.acrossNextTile = this;
		this.acrossPreviousTile = acrossPreviousTile;
		if(acrossNextTile != null)
			acrossNextTile.acrossPreviousTile = this;
		this.acrossNextTile = acrossNextTile;
		return true;
		
	}
	
	protected boolean addDown(String word, char letter, Tile downPreviousTile, Tile downNextTile) {
		if(is2DTile())
			return false;
		this.verticalWord = word;
		if(this.tileValue == 0)
			this.tileValue = letter;
		if(downPreviousTile != null)
			downPreviousTile.downNextTile = this;
		this.downPreviousTile = downPreviousTile;
		if(downNextTile != null)
			downNextTile.downPreviousTile = this;
		this.downNextTile = downNextTile;
		return true;
	}
}
