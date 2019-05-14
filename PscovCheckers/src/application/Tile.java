package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
public class Tile extends Polygon {

    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(int x, int y) {
    	if(x==0 || x==6) {
    		if(y>1 && y<6)
        	getPoints().addAll(new Double[]{
        			0.0, 50.0,
        		    31.25, 0.0,
        		    93.75, 0.0 ,
        		    125.0,50.0,
        		    93.75,100.0,
        		    31.25,100.0,
        	});
    		if(x==0) {
            relocate(x * 100+15, y * 100-50);
    		}
    		else {
    			 relocate(x * 100-15, y * 100-50);
    		}
            }
    	if(x==1 || x==5) {
    		if(y>0&&y<6)
        	getPoints().addAll(new Double[]{
        			0.0, 50.0,
        		    31.25, 0.0,
        		    93.75, 0.0 ,
        		    125.0,50.0,
        		    93.75,100.0,
        		    31.25,100.0,
        	});
    		if(x==1) {
            relocate(x * 100+10, y * 100);
    		}
    		else {
    			relocate(x * 100-10, y * 100);
    		}
            }
    	if(x==2 || x==4) {
    		if(y>0)
        	getPoints().addAll(new Double[]{
        			0.0, 50.0,
        		    31.25, 0.0,
        		    93.75, 0.0 ,
        		    125.0,50.0,
        		    93.75,100.0,
        		    31.25,100.0,
        	});
    		if(x==2) {
            relocate(x * 100+5, y *100-50);
    		}
    		else {
    			relocate(x * 100-5, y * 100-50);
    		}
            }
    	if(x==3) {
    	getPoints().addAll(new Double[]{
    		    0.0, 50.0,
    		    31.25, 0.0,
    		    93.75, 0.0 ,
    		    125.0,50.0,
    		    93.75,100.0,
    		    31.25,100.0,
    	});
        relocate(x * 100, y * 100);
        }
    	if(y%2==0) {
        setFill(Color.WHITE);
    	}
    	else {
    		setFill(Color.ORANGERED);
    	}
        setStroke(Color.BLACK);
        setStrokeWidth(2);
    }
}
