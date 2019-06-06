package application;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
public class Piece extends StackPane {
    private PieceType type;
    private double mouseX, mouseY;
    private double oldX, oldY;
    public PieceType getType() {
        return type;
    }
    public double getOldX() {
        return oldX;
    }
    public double getOldY() {
        return oldY;
    }
    public Piece(PieceType type, int x, int y) {
        this.type = type;
        move(x, y);
        Circle crcl = new Circle(30);
        if(type==PieceType.BLACK) {
        	 crcl.setFill(Color.BLACK);
        }
        if(type==PieceType.WHITE) {
       	 crcl.setFill(Color.WHITE);
       }
        crcl.setStroke(Color.BLACK);
        crcl.setStrokeWidth(3);
        crcl.setTranslateX(20);
        crcl.setTranslateY(20);
        getChildren().addAll(crcl);
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        
        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }
    public void move(int x, int y) {
    		int b = 25-5*x;
    			oldX =x*100+b;
        		if(x%2==0) {
        			oldY = y * 100-50;
        		}
        		else {
        			oldY = y * 100;
        	    }
        relocate(oldX, oldY);
    }
    public void abortMove() {
        relocate(oldX, oldY);
    }
}
