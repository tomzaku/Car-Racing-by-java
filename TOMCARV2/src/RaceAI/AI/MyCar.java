package RaceAI.AI;

import java.awt.Point;
public class MyCar {
	public boolean turnback = false;
	int xIndex,yIndex;
	int direction;
	Point next,last;
	int[] ix = {1,0,-1,0};
	int[] iy = {0,1,0,-1};
	Point[] priorityDirection;
	int downPriority[] = {1,0,2};
	int upPriority[] = {0,3,2};
	int leftPriority[] = {1,2,3};
	int rightPriority[] = {0,1,3};
	Point trigger[];
	Point positionNextTrigger[];
	int currentIndexTrigger =0 ;
	
	MyCar(int max_triggle){
		trigger = new Point[max_triggle];
		positionNextTrigger = new Point[max_triggle];
	}
	private void updateData(int xIndex,int yIndex,int direction){
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.direction = direction;
	}
	public Point findNextDirection(MyMap myMap,int xIndex, int yIndex, int direction){
		updateData(xIndex,yIndex,direction);
		// 
		//redure currentIndexTriggle if end line 
		//
		while(currentIndexTrigger>0&& trigger[currentIndexTrigger-1].x==this.xIndex && trigger[currentIndexTrigger-1].y==this.yIndex 
				&& myMap.checkDeadEnd(positionNextTrigger[currentIndexTrigger-1].x,positionNextTrigger[currentIndexTrigger-1].y)){
			myMap.setBlockKind(positionNextTrigger[currentIndexTrigger-1].x,positionNextTrigger[currentIndexTrigger-1].y, '1');
			currentIndexTrigger--;
		}
		//Go follow trigger if position exit in trigger
		if( currentIndexTrigger > 0 && trigger[currentIndexTrigger-1].x==this.xIndex && trigger[currentIndexTrigger-1].y==this.yIndex){
			turnback = false;
			currentIndexTrigger--;
			next = positionNextTrigger[currentIndexTrigger];
			System.out.println("bbbbbbbbbbbb>>>>>>>>>>>>>");
		}else {
			goNextDirection(myMap);
		}
		return next;
	}
	private void goNextDirection(MyMap myMap) {
		// TODO Auto-generated method stub
		boolean find = false;
		int priority=0;
		int posNextX;
		int posNextY;
		for(int i =0 ;i<3;i++){
			switch(direction){
				case 0: {priority = rightPriority[i];break;}
				case 1: {priority = downPriority[i];break;}
				case 2: {priority = leftPriority[i];break;}
				case 3: priority = upPriority[i];
			}
			posNextX = xIndex + ix[priority];
			posNextY = yIndex + iy[priority];
//			System.out.println("Direction >>> "+ direction);
//			System.out.println(">>>>>||" + xIndex +"|| "+ yIndex+	"aaaa>>>>  "+ posNextX + " >>>aa>> "+ posNextY);
			if(myMap.blockKind(posNextX, posNextY) != '1'){
//				System.out.println(">>>>>>>");
				if(turnback||!myMap.checkDeadEnd(posNextX, posNextY)){
					if(!find){
						find = true;
						this.next = new Point(posNextX,posNextY);
					}else{
						System.out.println("Trigger Direction >>> "+ direction);
						System.out.println(">>>>>||" + xIndex +"|| "+ yIndex+	"aaaa>>>>  "+ posNextX + " >>>aa>> "+ posNextY);
						this.trigger[currentIndexTrigger] = new Point(this.xIndex,this.yIndex);
						this.positionNextTrigger[currentIndexTrigger++]  = new Point(posNextX,posNextY); 
					}
				}else{
					myMap.setBlockKind(posNextX, posNextY, '1');
				}
			}
		}
		if(!find){
			System.out.println(">>>>>>>false");
			turnback = true;
			this.next = this.last;
		}else{
			this.last= new Point(xIndex,yIndex);
		}
	}

}
