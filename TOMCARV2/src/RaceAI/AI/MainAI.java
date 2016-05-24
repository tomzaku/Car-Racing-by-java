package RaceAI.AI;

import java.awt.Point;

import java.util.Random;
import java.util.Vector;

import RaceAI.RaceClient.Car;
import RaceAI.RaceClient.Race;


public class MainAI {
	Race race;
	Vector<Car> All_cars;
	Car Mycar;
	int row,column;
	int direction;
	Point now,next;
	public String key = "0000"; // Go-Back-Left-Right (Up - Down - Left - Right)
	
	MyMap myMap;  
	MyCar myCar;
	
	public MainAI(Race race, Vector<Car> cars, Car Mycar){
		this.race = race;
		this.Mycar = Mycar;
		this.All_cars = cars;
		row =race.BlockRow();
		column = race.BlockColumn();
		myMap =  new MyMap(row,column);
		myCar = new MyCar(GlobalVariables.max_trigger);
	}
	//last position
	double lx=0,ly=0;
	// last speed
	double speed = 0;
	// your AI
	public int toDirection(int x,int y){
		switch(x){
			case 1: return 0; //right
			case -1: return 2; // left
		}
		switch(y){
			case 1 : return 1; //down
			case -1: return 3; //top
		}
		return 0;
	}
	public void AI(){
		// AI example
		//Block Index
		int x = (int) (this.Mycar.getx() / this.race.BlockSize());
		int y = (int) (this.Mycar.gety() / this.race.BlockSize());
		
		double speed_now = Math.sqrt((this.Mycar.getx()-lx)*(this.Mycar.getx()-lx)+(this.Mycar.gety()-ly)*(this.Mycar.gety()-ly));
		speed = (speed*2+speed_now)/3;
		lx=this.Mycar.getx();
		ly=this.Mycar.gety();
		//System.out.println(speed+ ", "+this.race.BlockSize()*0.01);
		if (speed>this.race.BlockSize()*0.01) {
			this.key = "0000"; //stop
			return;
		}
	
		// End Stop when the car rotation
		this.now = new Point(x,y);


		if (this.next==null) this.next = this.now;
		
		//Next Block Center Coordinate
		double block_center_x= (this.next.x + 0.5) * this.race.BlockSize();
		double block_center_y= (this.next.y + 0.5) * this.race.BlockSize();
		//Car's Direction
		double v_x = Math.cos(this.Mycar.getalpha() * Math.PI/180);
		double v_y = Math.sin(this.Mycar.getalpha() * Math.PI/180);
		//Vector to Next Block Center from Car's position
		double c_x = block_center_x - this.Mycar.getx();
		double c_y = block_center_y - this.Mycar.gety();

		direction= toDirection((int)Math.round(v_x),(int)Math.round(v_y));
		double distance2center = Math.sqrt(c_x*c_x+c_y*c_y);
		if (distance2center!=0) {
			//vector normalization
			c_x/=distance2center;
			c_y/=distance2center;
		}
		
		
		if (distance2center<this.race.BlockSize()*0.4){
			this.key = "0000"; //stop
				
			if(!myCar.turnback){
				myMap.saveData(race, this.now );
			}else{
				myMap.addWall(this.now,direction);
			}			
			this.next = myCar.findNextDirection(myMap, x, y, direction);
			myMap.printMap();
		}
		else {
			// Go to next block center
			double inner = v_x*c_x + v_y*c_y;
			double outer = v_x*c_y - v_y*c_x;
			if (inner > 0.995){
					this.key = "1000"; //go
			} else {
				if (inner < 0){
					this.key = "0001"; //turn right
				}
				else {
					if (this.race.BlockKind(x, y)!='3')
						if (outer > 0) this.key = "0001"; //turn right
						else this.key = "0010"; //turn left
					else 
						if (outer > 0) this.key = "0010"; //turn right
						else this.key = "0001"; //turn left
				}
			}
		}
	}
}
