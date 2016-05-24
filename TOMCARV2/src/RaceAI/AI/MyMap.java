package RaceAI.AI;

import java.awt.Point;

import RaceAI.RaceClient.Race;

public class MyMap {
	static char arraymap[][]= new char[60][60];
	char arraymapTemp[][]= new char[60][60];
	int[] ix = {1,0,-1,0};
	int[] iy = {0,1,0,-1};
	int row,column;
	boolean foundFinish =false;
	MyMap(int row,int column){
		this.row= row;
		this.column = column;
		for(int i =0;i<row;i++){
			for(int j=0;j<column;j++){
				if(i==0||j==0||i==row-1||j==column-1){
					arraymap[i][j]='1';
				}else{
					arraymap[i][j]='?';
				}
			}
		}
	}
	public void saveData(Race race ,Point pos){
		int xtemp,ytemp;
		for(int i=0;i<4;i++){
			xtemp= pos.x+ix[i];
			ytemp = pos.y+iy[i];
			if(arraymap[xtemp][ytemp]=='?')
				arraymap[xtemp][ytemp]=race.BlockKind(xtemp,ytemp);	
			System.out.println(race.BlockKind(xtemp,ytemp) + " ");	
		}
		arraymap[pos.x][pos.y]=race.BlockKind(pos.x,pos.y);
	}
	public void printMap(){
		for(int i=0;i<row;i++){
			System.out.println();
			for(int j=0;j<column;j++){
				System.out.print(arraymap[j][i]+ " ");
			}
		}
	}
	public void clearTempMap(){
		for(int i =0;i<row;i++){
			for(int j=0;j<column;j++){
				if(i==0||j==0||i==row-1||j==column-1){
					arraymapTemp[i][j]='1';
				}else{
					arraymapTemp[i][j]='?';
				}
			}
		}
	}
	public char blockKind(int x,int y){
		return arraymap[x][y];
	}
	public void setBlockKind(int x, int y, char kind){
		System.out.println("SetBlockKind "+ x +y + " <<<<<<<<<<<<<");
		arraymap[x][y]=kind;
	}
	public void addWall(Point curr, int direction){
		arraymap[curr.x+ix[(direction+2)%4]][curr.y+iy[(direction+2)%4]]='1';
		
	}

	public boolean checkDeadEnd(int x,int y){
		foundFinish=false;
		clearTempMap();
		checkDeadEndRescusive(x, y);
		return !foundFinish;
	}
	private int checkDeadEndRescusive(int x,int y){
		if(foundFinish) return 1;
		if(x == (row-2) && y == (column-2)){
			foundFinish =true;

		}
		for(int index = 0 ;index < 4; index++){
			if(arraymap[x+ix[index]][y+iy[index]]=='?'&&arraymapTemp[x+ix[index]][y+iy[index]]=='?'){
				arraymapTemp[x+ix[index]][y+iy[index]]='1';
				checkDeadEndRescusive(x+ix[index],y+iy[index]);		
			}
		}
		return 0;
	}	
}
