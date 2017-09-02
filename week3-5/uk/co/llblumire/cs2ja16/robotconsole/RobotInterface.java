package uk.co.llblumire.cs2ja16.robotconsole;

import java.util.Scanner;

public class RobotInterface {
	Scanner s;
	RobotArena myArena;
	
	public RobotInterface() {
		this.s = new Scanner(System.in);
		this.myArena = new RobotArena(20, 6, 5);
		
		char ch = ' ';
		do {
			System.out.print("Enter (A)dd robot, give (I)nformation or e(X)it > ");
			ch = this.s.next().charAt(0);
			this.s.nextLine();
			switch(ch) {
			case 'A':
			case 'a':
				this.myArena.addRobot();
				break;
			case 'I':
			case 'i':
				System.out.println(myArena.toString());
				break;
			case 'X':
			case 'x':
				ch = 'X';
				break;
			}
		} while (ch != 'X');
		s.close();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		RobotInterface ri = new RobotInterface();
	}

}
