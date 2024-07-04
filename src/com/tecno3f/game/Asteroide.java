package com.tecno3f.game;

import java.awt.*;

/**
 * Esta clase sirve para representar objeto Asteroide
 * @author FabrizioAG
 */
public class Asteroide {
    int x, y;
    int width, height;
    int speed;

    public Asteroide(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = 50;
        this.height = 50;
    }
    
   
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        //g.fillRect(x, y, width, height);
        g.fillOval(x, y, width, height);
    }
    
   
    public void move() {
        x -= speed;
    }
    
   
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
