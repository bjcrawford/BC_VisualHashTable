package bc_main;

import bc_hashtable.BC_HashTable;
import java.awt.Color;
import java.util.Random;
import simplegui.*;

/**
 *
 * @author Brett Crawford (Brett.Crawford@temple.edu)
 */
public class BC_Main implements SGMouseListener {
    
    private final int SIZE = 100;
    
    private int[] collisions;
    private SimpleGUI gui;
    private Random rand;
    private BC_HashTable myHashTable;
    

    private BC_Main() {
        
        initialize();
        drawUI();
    }
    
    private void initialize() {
        
        collisions = new int[SIZE];
        for(int i = 0; i < SIZE; i++)
           collisions[i] = -1;
        gui = new SimpleGUI(640, 200, false);
        rand = new Random();
        myHashTable = new BC_HashTable(SIZE);
        gui.registerToMouse(this);
        gui.setTitle("Hash Table Collision Visualization");
        gui.drawImage("res/title.png", 120, 10);
    }
    
    private void drawUI() {
        gui.drawFilledBox(0, 150, 640, 50, Color.GRAY, 1.0, "");
        
        gui.drawLine(0, 150, 640, 150);
        gui.drawLine(213, 150, 213, 200);
        gui.drawLine(426, 150, 426, 200);
        
        gui.drawText("Insert 10 Random Doubles", 32, 180);
        gui.drawText("Reset HashTable", 273, 180);
        gui.drawText("Average Collisions ( - )", 465, 180, Color.BLACK, 1.0, "avg_col");
        
        
        drawCells();
    }
    
    private void drawCells() {
        
        int xOffset = 20;
        
        for(int i = 0; i < SIZE; i++) {
            gui.drawFilledBox(xOffset, 100, 5, 5, Color.BLACK, 1.0, "col_" + i);
            if(i % 10 == 0)
                gui.drawText("" + i, xOffset - 2, 85);
            if(i == SIZE - 1)
                gui.drawText("" + i, xOffset - 2, 85);
            xOffset = xOffset + 6;
        }
    }
    
    private void updateCellColor(int pos) {
        int x = gui.getDrawable("col_" + pos).posX;
        int y = gui.getDrawable("col_" + pos).posY;
        gui.eraseSingleDrawable("col_" + pos);
        gui.drawFilledBox(x, y, 5, 5, computeCellColor(collisions[pos]), 1.0, "col_" + pos);
    }
    
    private void randomInsert() {
        double toInsert;
        int elementInfo[];
        
        for(int i = 0; i < 10; i++) {
            toInsert = rand.nextDouble() * 1000;
            System.out.println("Inserting " + toInsert);
            elementInfo = myHashTable.insert(toInsert);
            System.out.println("Position:" + elementInfo[0]);
            System.out.println("Collisions: " + elementInfo[1]);
            System.out.println();
            if(elementInfo[0] != -1 && collisions[elementInfo[0]] == -1) {
                collisions[elementInfo[0]] = elementInfo[1];
                updateCellColor(elementInfo[0]);
            }
        }
    }
    
    private void resetVisualization() {
        myHashTable = new BC_HashTable(SIZE);
        collisions = new int[SIZE];
        for(int i = 0; i < SIZE; i++) {
           collisions[i] = -1;
           gui.eraseSingleDrawable("col_" + i);
        }
        drawCells();
        updateAverageCollisions(true);
    }
    
    private void updateAverageCollisions(boolean reset) {
        
        if(!reset) {
            int total = 0;
            for(int i = 0; i < SIZE; i++) {
                if(collisions[i] != -1)
                    total += collisions[i];
            }
            gui.eraseSingleDrawable("avg_col");
            gui.drawText("Average Collisions ( " + total / SIZE + " )", 465, 180, Color.BLACK, 1.0, "avg_col");
        }
        else {
            gui.eraseSingleDrawable("avg_col");
            gui.drawText("Average Collisions ( - )", 465, 180, Color.BLACK, 1.0, "avg_col");
        }
    }
    
    private Color computeCellColor(int collisions) {
        int r = (int) (255.0 / 7.0 * collisions);
        r = (int) Math.min(r , 255);
        int g = 255 - r;
        int b = 0;
        
        return new Color(r, g, b);
    }

    @Override
    public void reactToMouseClick(int x, int y) {
        
        if(x > 0 && x < 213 && y > 150 && y < 200) {
            System.out.println("You pressed the random insert button");
            randomInsert();
        }
        if(x > 213 && x < 426 && y > 150 && y < 200) {
            System.out.println("You pressed the reset button");
            resetVisualization();
        }
        if(x > 426 && x < 640 && y > 150 && y < 200) {
            System.out.println("You pressed the average collision button");
            updateAverageCollisions(false);
        }
    }
    
    public static void main(String[] args) {
        new BC_Main();
    }
}
