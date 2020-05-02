package ro.vansoftware.vantiles.model;
import ro.vansoftware.vantiles.shapes.Tile;


public class Note {


    public int line = 0;
    public int column = 0;
    public boolean isVisible = false;

    public Tile tile;


    public Note(int line, int column, boolean isVisible){
        this.line = line;
        this.column = column;
        this.isVisible = isVisible;
    }

}
