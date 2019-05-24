package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PscovCheckers extends Application {

    

    private Tile[][] board = new Tile[7][7];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(720, 700);
        root.getChildren().addAll(tileGroup, pieceGroup);

        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                Tile tile = new Tile(x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2) {
                	if((x==0||x==6)&&y==2) {
                		piece = makePiece(PieceType.BLACK, x, y);
                    }
                	if((x==1||x==5)&&y>0) {
                		piece = makePiece(PieceType.BLACK, x, y);
                    }
                	if((x==2||x==4)&&y>0) {
                		piece = makePiece(PieceType.BLACK, x, y);
                    }
                	if((x==3)&&y<=2) {
                		piece = makePiece(PieceType.BLACK, x, y);
                    }
                }

                if (y >= 3 ) {
                	if((x==0||x==6)&&y==5) {
                    piece = makePiece(PieceType.WHITE, x, y);
                	}
                	if((x==1||x==5)&&(y>3&&y<6)) {
                        piece = makePiece(PieceType.WHITE, x, y);
                    }
                	if((x==2||x==4)&&(y>4)) {
                        piece = makePiece(PieceType.WHITE, x, y);
                    }
                	if((x==3)&&(y>3)) {
                        piece = makePiece(PieceType.WHITE, x, y);
                    }
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY) {
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());
        double dist = Math.sqrt(Math.abs(newX - x0)*Math.abs(newX - x0) + Math.abs(newY - y0)*Math.abs(newY - y0));
        System.out.println(dist);
        if (board[newX][newY].hasPiece()|| ((newX==0||newX==6)&&(newY==0||newY==1||newY==6))
        		||(newX==1||newX==5)&&(newY==0||newY==6) || (x0%2==1 &&newX%2==0 &&piece.getType().equals(application.PieceType.BLACK) && newY==y0)
        				|| (x0%2==0 &&newX%2==1 &&piece.getType().equals(application.PieceType.WHITE) && newY==y0)
        ) {
        	System.out.println("Так ходить нельзя!");
            return new MoveResult(MoveType.NONE);
        }

        if (!board[newX][newY].hasPiece()&&dist<2) {
       	 if((piece.getType().equals(application.PieceType.WHITE)&&newY<=y0)||(piece.getType().equals(application.PieceType.BLACK)&&newY>=y0))
            return new MoveResult(MoveType.NORMAL);
        }
         if (dist==2&&newX==x0) {

            int x1 = newX;
            int y1 = newY-1;
            if(y1<0) {
            	y1=newY+1;
            }

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
            	if(board[newX][newY+1].hasPiece()&&board[newX][newY+1].getPiece().getType()!= piece.getType()) {
            		y1=newY+1;
            	}
            	if(Math.abs(y0-y1)<=2)
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
             x1 = newX;
             y1 = newY+1;
             if(y1>6) {
            	 y1=newY-1;
             }

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
            	if(board[newX][newY-1].hasPiece()&&board[newX][newY-1].getPiece().getType()!= piece.getType()) {
            		y1=newY-1;
            	}
            	if(Math.abs(y0-y1)<=2)
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
           
        }
         if(dist==Math.sqrt(5)) {
        	 int checks = 0;
        	 int x1 = newX-1;
             int y1 = newY;
        	 do {
        		 x1=newX-1;
        		 System.out.println(y1);
             if(newX-1<0) {
            	 x1=newX+1;
             }
             if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
            	 if(newX+1<=6)
            	 if(board[newX+1][newY].hasPiece()&&board[newX+1][newY].getPiece().getType()!= piece.getType()) {
             		x1=newX+1;
             	}
            	 if((x1-x0)*(x1-x0)+(y1-y0)*(y1-y0)<=5)
            	 return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
             }
             x1 = newX+1;
             if(x1>6) {
            	 x1=newX-1;
             }
             if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
            	 if(board[newX-1][newY].hasPiece()&&board[newX-1][newY].getPiece().getType()!= piece.getType()) {
              		x1=newX-1;
              	}
            	 if((x1-x0)*(x1-x0)+(y1-y0)*(y1-y0)<=5)
            	 return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
             }
             if (checks==1 && newY-1>=0) {
             y1=newY-1;
             }
             if (checks==2 && newY+1<=6) {
                 y1=newY+1;
                 }
             checks++;
             
             } while (checks<3);
         }
         if(dist==Math.sqrt(8)) {
        	 
         }
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int)(pixel + 100 / 2) / 100;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("PscovCheckers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Piece makePiece(PieceType type, int x, int y) {
    	
        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= 7 || newY >= 7) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }
        });

        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
