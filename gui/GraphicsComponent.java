package gui;

import java.awt.*;

public class GraphicsComponent {

    private final Image image;
    private int x;
    private int y;

    private final int finalX;
    private int incrementX = 0;
    private final int finalY;
    private int incrementY = 0;
    private boolean isStart = false;
    private final boolean left,right,up,down;



    public GraphicsComponent(Image image, int x, int y, int finalX, int finalY, boolean left, boolean right, boolean up, boolean down) {
        this(image, x, y, finalX, finalY, 1, 1, left, right, up, down);
    }

    public GraphicsComponent(Image image, int x, int y, int finalX,
                             int finalY, int incrementX, int incrementY,boolean left, boolean right, boolean up, boolean down) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.finalX = finalX;
        this.incrementX = incrementX;
        this.finalY = finalY;
        this.incrementY = incrementY;
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    public boolean checkMoveX() {
        //return x != finalX ? false : true;
        if(x != finalX) {
            return true;
        } else return false;
    }
    public boolean checkMoveY() {
        //return y != finalY ? false : true;
        if(y != finalY) {
            return true;
        } else return false;
    }

    public void start() {
        isStart = true;
    }

    public boolean isStart() {
        return isStart;
    }

    public void move() {
        moveX();
        moveY();
    }

    private void moveX() {
        if(left) x -= incrementX;
        if(right) x += incrementX;
    }

    private void moveY() {
        if(up) y -= incrementY;
        if(down) y += incrementY;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
