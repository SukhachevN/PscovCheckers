package application;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class PscovCheckers extends Application {
    private Tile[][] board = new Tile[7][7];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private boolean restart = false;
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(720, 700);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                Tile tile = new Tile(x, y);
                tile.setInfo(x%2==1 ? y+1  : y+0.5);
                board[x][y] = tile;
                tileGroup.getChildren().add(tile);
                Piece piece = null;
                if (y <= 2) {
                	if((x==0||x==6)&&y==2 || (x==1||x==5 || x==2||x==4)&&y>0 || x==3) {
                		piece = makePiece(PieceType.BLACK, x, y);
                    }
                }
                if (y >= 4 ) {
                	if((x==0||x==6)&&y==5 || (x==1||x==5)&&(y<6) || (x==2||x==4)&&(y>4) || x==3) {
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
        		||(newX==1||newX==5)&&(newY==0||newY==6) || (newY==0 && newX!=3)
        		||(x0%2==1 &&newX%2==0 &&piece.getType().equals(application.PieceType.BLACK) && newY==y0)
        		|| (x0%2==0 && newX%2==1 &&piece.getType().equals(application.PieceType.WHITE) && newY==y0) 
        		||Math.abs(board[newX][newY].getInfo()-board[x0][y0].getInfo())==1.5
        ) {
        	System.out.println("Так ходить нельзя!");
            return new MoveResult(MoveType.NONE);
        }
        if (!board[newX][newY].hasPiece()&&dist<2) {
        	if((piece.getType().equals(application.PieceType.WHITE)&&newY<=y0)||
          		    (piece.getType().equals(application.PieceType.BLACK)&&newY>=y0))
                return new MoveResult(MoveType.NORMAL); 
        }
         if (dist==2&&newX==x0) {
             int x1 = newX;
             int y1 = newY-1;
             if(y1<0) {
             	 y1=newY+1;
             }
             if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType() && newY+1<=6) {
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
             	  if(Math.abs(y0-y1)==1)
             		  return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
              } 
         }
         if(dist==Math.sqrt(5)) {
        	 for (int i = 0;i<3;i++) {
        		 int x1 = (newX - 1 >= 0) ? newX - 1 : newX + 1;
            	 int y1 = newY;
            	 if(i==0) {
            		 y1 = newY;
            	 }
            	 if(i==1) {
            		 if(newY>y0) {
            			 y1 = (newY -1 >=0 ) ? newY -1 : newY;
            		 }
            	 }
            	 if(i==2) {
            		 if(newY<y0) {
            			 y1 = (newY + 1 <= 6 ) ? newY + 1 : newY;
            		 }
            	 }
            	 if(Math.abs(y1-y0)==2) {
            		 continue;
            	 }
            	 if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
            		 if (Math.abs(board[x0][y0].getInfo()-board[x1][y1].getInfo())!=1.5 
            				 && Math.abs(x1 - x0)*Math.abs(x1 - x0) + Math.abs(y1 - y0)*Math.abs(y1 - y0) <= 4 )
            			 return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            	 }
        		 x1 = (newX + 1 <= 6) ? newX + 1 : newX - 1;
        		 System.out.println(x1);
        		 System.out.println(y1);
            	 if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
            		 if (Math.abs(board[x0][y0].getInfo()-board[x1][y1].getInfo())!=1.5 
            				 && Math.abs(x1 - x0)*Math.abs(x1 - x0) + Math.abs(y1 - y0)*Math.abs(y1 - y0) <= 4 )
            			 return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            	 } 	 
        	 }
         }
         System.out.println("Так ходить нельзя!");
         return new MoveResult(MoveType.NONE);
    }
    private int toBoard(double pixel) {
        return (int)(pixel + 100 / 2) / 100;
    }
    private boolean checkLine(int line) {
    	for(int i = 0;i<6;i++) {
    		if (board[i][line].hasPiece() && board[i][line].getPiece().getType().equals(application.PieceType.BLACK)) {
    			 return true;
    		}
    	}
		return false;
    }
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("PscovCheckers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private Piece computer() {
    	int y1 = 0;
    	int x1 = 0;
    	if(restart==true) {
    		for(int i = (int) (Math.random()*6);i>0;i--) {
        		if(checkLine(i)) {
        			y1=i;
        			break;
        		}
        	}
        	 x1 = randomPiece(y1);
    	}
    	else {
    		for(int i = 6;i>0;i--) {
   			 if(checkLine(i)) {
   				 for(int k = 0;k<7;k++) {
   					 if(board[k][i].hasPiece()) {
   						 y1=i;
   						 x1=k;
   					 }
   				 }
   			 }
   		 }
   		 restart=false;
    	}
    	Piece piece = new Piece(application.PieceType.BLACK, x1, y1);
    	if(!board[x1][y1].hasPiece() || board[x1][y1].getPiece().getType()==application.PieceType.WHITE) {
    		try {
    			restart=true;
    		computer();
    		} catch (java.lang.StackOverflowError e) {
    			return null;
    		}
    		return null;
    	}
    	boolean check = false;
    	 int newX = randomX(x1,y1);
    	 int newY = 0;
    	 newY = y1 + randomY(x1,y1);
         if (newY==y1 && newX==x1) {
        	 for(int i=0;i<Math.random()*3;i++) {
        		 newX = randomX(x1,y1);
        		 if(newX!=x1) {
        			 break;
        		 }
        	 }
        	 if (newX==x1) {
        		 newY+=1;
        	 }
         } 
         if(restart==true) {
        	 newX=x1;
        	 newY=y1+1;
         }
         try {
        	 if(y1+2 <= 6 && board[x1][y1+1].hasPiece() && board[x1][y1+1].getPiece().getType()!= piece.getType() &&
            		 !board[x1][y1+2].hasPiece() ) {
            	 newY = y1 + 2;
            	 check=true;
             }else {
            	 if(y1-2 >= 0 && board[x1][y1-1].hasPiece() && board[x1][y1-1].getPiece().getType()!= piece.getType() &&
                		 !board[x1][y1-2].hasPiece() ) {
                	 newY = y1 - 2;
                	 check=true;
                 }
             }
        	 if(check==false) {
        		 for(int i = 0;i<3;i++) {
                	 if(i==0) {
                		 newY=y1;
                	 }
                     if(i==1) {
                		 newY=(y1+1<=7) ? y1+1 : y1;
                	 }
                     if(i==2) {
                    	 newY=(y1-1>=0) ?y1-1 : y1;
                     }
                     if(newY <= 6 && x1-2>=0  && board[x1-1][newY].hasPiece() && board[x1-1][newY].getPiece().getType()!= piece.getType() &&
                    		 !board[x1-2][newY].hasPiece()) {
                    	 newX = x1-2;
                    	 if(board[x1-1][newY-1].hasPiece() && board[x1-1][newY-1].getPiece().getType()== piece.getType() ||
                    			 Math.abs(board[newX][newY].getInfo()-board[x1][y1].getInfo())!=0.5) {
                    		 newX=x1;
                    	 }
                    	 break;
                     }
                     if(newY <= 6 && x1+2>=0  && board[x1+1][newY].hasPiece() && board[x1+1][newY].getPiece().getType()!= piece.getType() &&
                    		 !board[x1+2][newY].hasPiece()) {
                    	 newX = x1+2;
                    	 if(board[x1+1][newY-1].hasPiece() && board[x1+1][newY-1].getPiece().getType()== piece.getType()||
                    			 Math.abs(board[newX][newY].getInfo()-board[x1][y1].getInfo())!=0.5) {
                    		 newX=x1;
                    	 }
                    	 break;
                     }
                 } 
        	 }
         }catch(java.lang.ArrayIndexOutOfBoundsException e) {
        	 System.out.println("Произошла ошибка");
         }
         MoveResult result;
         if (newX < 0 || newY < 0 || newX >= 7 || newY >= 7) {
             result = new MoveResult(MoveType.NONE);
         } else {
             result = tryMove(piece, newX, newY);
         }
         switch (result.getType()) {
             case NONE:
                 piece.abortMove();
                 try {
                	restart=true;
             		computer();
                 }catch (java.lang.StackOverflowError e) {
                	 System.out.println("Ошибка,компьютер не смог найти шашку");
                	 restart=true;
             		 return null;
                 }	
                 break;
             case NORMAL:
            	 board[x1][y1].getPiece().setOpacity(0.0);
            	 pieceGroup.getChildren().remove(piece);
            	 board[x1][y1].setPiece(null);
                 piece.move(newX, newY); 
                 board[newX][newY].setPiece(piece);
                 pieceGroup.getChildren().add(piece);
                 break;
             case KILL:
            	 board[x1][y1].getPiece().setOpacity(0.0);
                 piece.move(newX, newY);
                 board[x1][y1].setPiece(null);
                 pieceGroup.getChildren().remove(piece);
                 board[newX][newY].setPiece(piece);
                 pieceGroup.getChildren().add(piece);
                 Piece otherPiece = result.getPiece();
                 board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                 pieceGroup.getChildren().remove(otherPiece);
                 break;
         }
		 return piece;	 
    }
    private int randomX(int x , int y) {
    	int a = (int) (Math.random()*2 - 1);
    	if(x+1>6) {
    		a = (int) (-1 + Math.random());
    	}
    	if(x-1<0) {
    		a = (int) Math.random();
    	}
    	if(board[x][y].getInfo()%1==0.5 || board[x+a][y].hasPiece()) {
    		a = 0;
    	}
		return (int) (x + a);
    }
    private int randomY(int x, int y) {
    	int random = 0;
    	if(board[x][y].getInfo()%1!=0.5) {
    		random = (int) Math.random();
    	}
		return random;
    }
    private int randomPiece(int line) {
    	int x = 3;
    		if(line==3 ||line==2|| line == 4 || line == 5) {
        		x = (int) (Math.random()*7);
        	}
        	if(line==1) {
        		x = (int) ( 1 + Math.random()*4);
        	}
		return x;
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
                    computer();
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    computer();
                    break;
            }
        });
        return piece;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
