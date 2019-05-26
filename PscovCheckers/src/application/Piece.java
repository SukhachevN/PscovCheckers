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
        crcl.setFill(type == PieceType.BLACK ? Color.BLACK : Color.WHITE);
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
    	for(int i = 0;i<7;i++) {
    		int b = 25-5*x;
    		if(x==i) {
    			oldX =x*100+b;
        		if(i%2==0) {
        			oldY = y * 100-50;
        		}
        		else {
        			oldY = y * 100;
        	    }
        	}
        }
        relocate(oldX, oldY);
    }
    public void abortMove() {
        relocate(oldX, oldY);
    }
}
