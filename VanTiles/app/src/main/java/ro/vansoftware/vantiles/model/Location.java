package ro.vansoftware.vantiles.model;

public class Location {
    public int top;
    public int left;
    public int bottom;
    public int right;

    public Size size;


    public Location(int top, int left, int bottom, int right){
        this.update(top, left, bottom, right);
    }
    public Location(int top, int left, Size size){
        this.update(top, left, size);
    }

    public Size getSize(){
        return new Size(this.right - this.left,  this.bottom - this.top);
    }

    public void update(int top, int left, int bottom, int right){
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;

        this.size = new Size(this.right - this.left, this.bottom - this.top);
    }

    public void update(int top, int left, Size size){
        this.top = top;
        this.left = left;
        this.bottom = this.top + size.height;
        this.right  = this.left + size.width;
        this.size = size;
    }

}
