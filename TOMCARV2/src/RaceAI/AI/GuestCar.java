package RaceAI.AI;
import java.awt.Point;
public class GuestCar {
	int xIndex,yIndex;
	int direction;
	int lastDirection=0;
	int triggleDirection[]  ;
	Point trigglePossion[];
	int lengthTriggle=0;
	GuestCar(){}
	public void updateData(int xIndex,int yIndex,int direction){
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.direction = direction;
		if (changeDirectionHorizontalAndHertial()){
			if (reduceLengthIfExitInArray()){
				lengthTriggle--;
			}else{
				saveTriggle();
			}
			
		}
		this.lastDirection = direction;
	}
	public boolean changeDirectionHorizontalAndHertial(){
		if ((this.lastDirection + this.direction )%2==1){
			return true;
		}
		return false;
	}
	private boolean reduceLengthIfExitInArray(){
		if(trigglePossion[lengthTriggle-1].x ==xIndex && trigglePossion[lengthTriggle-1].y ==yIndex){
			return true;
		}
		return false;
	}
	private void saveTriggle(){
		this.trigglePossion[lengthTriggle]= new Point(xIndex,yIndex);
		this.triggleDirection[lengthTriggle++] = this.direction;
	}
}
