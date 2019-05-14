package application;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
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
        Ellipse ellipse = new Ellipse(30, 30);
        ellipse.setFill(type == PieceType.BLACK
                ? Color.BLACK : Color.WHITE);

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(3);
        ellipse.setTranslateX(20);
        ellipse.setTranslateY(20);

        getChildren().addAll(ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        if(x==0) {
        	oldX = x * 100+25;
            oldY = y * 100-50;
            }
        if(x==1) {
        	oldX = x * 100+20;
            oldY = y * 100;
            }
        if(x==2) {
        	oldX = x * 100+15;
            oldY = y * 100-50;
            }
        if(x==3) {
        	oldX = x * 100+10;
            oldY = y * 100;
        }
        if(x==4) {
        	oldX = x * 100+5;
            oldY = y * 100-50;
        }
        if(x==5) {
        	oldX = x * 100;
            oldY = y * 100;
            }
        if(x==6) {
        	oldX = x * 100-5;
            oldY = y * 100-50;
            }
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
