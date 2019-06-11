package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Tile extends Polygon {
	private Piece piece;
	private Double info;

	public boolean hasPiece() {
		return piece != null;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Double getInfo() {
		return info;
	}

	public void setInfo(Double x) {
		this.info = x;
	}

	public Tile(int x, int y) {
		int y1 = (x % 2 == 0) ? y * 100 - 50 : y * 100;
		int a = 15 - 5 * x;
		if (((x == 0 || x == 6) && y > 1 && y < 6) || ((x == 1 || x == 5) && y > 0 && y < 6)
				|| ((x == 2 || x == 4) && y > 0) || x == 3) {
			getPoints().addAll(
					new Double[] { 0.0, 50.0, 31.25, 0.0, 93.75, 0.0, 125.0, 50.0, 93.75, 100.0, 31.25, 100.0, });
			relocate(x * 100 + a, y1);
		}
		if(y%2==0) {
		setFill(Color.ANTIQUEWHITE);
		}else {
			setFill(Color.WHITE);
		}
		setStroke(Color.BLACK);
		setStrokeWidth(2);
	}
}
