package ro.vansoftware.vantiles.model;
import ro.vansoftware.vantiles.shapes.Tile;


public class Note {


    public int line = 0;
    public int column = 0;
    public boolean isVisible = false;
    public String sound = "a3";


    public Note(int line, int column, boolean isVisible, String sound){
        this.line = line;
        this.column = column;
        this.isVisible = isVisible;
        this.sound = sound;
    }

}
