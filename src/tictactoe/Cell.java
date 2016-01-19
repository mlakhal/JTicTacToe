package tictactoe;

public class Cell {  
   
   Seed content; // content of this cell of type Seed.
   private boolean _win;
   
   public Cell() {
      clear();  
      _win = false;
   }

    public void setWin(boolean _win) {
        this._win = _win;
    }

    public boolean isWin() {
        return _win;
    }
 
   public void clear() {
      content = Seed.EMPTY;
   }
 
}