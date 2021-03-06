import static java.lang.Math.*;

class MyException extends Exception {
	MyException(String message) {
		super(message);
	}	
};

abstract class Figure{
	abstract double calculate_area();
	abstract double calculate_perimeter();
}
abstract class Quadrangle extends Figure{}
class Circle extends Figure{
	double radius;
	Circle(double rad){
		radius=rad;
	}
	double calculate_area(){
		double area=PI*radius*radius;
		System.out.print("Pole kola dla promienia "+radius+ " wynosi: ");
		return area;
	}
	double calculate_perimeter(){
		double perimeter=2*PI*radius;
		System.out.print("Obwod kola dla promienia "+radius+ " wynosi: ");
		return perimeter;
	}
}
class Pentagon extends Figure{
	double side;
	Pentagon(double si){
		side=si;
	}
	double calculate_area(){
		double area=(5/4)*side*side*(1/tan(toRadians(36)));
		System.out.print("Pole pieciokota dla boku "+side+ " wynosi: ");
		return area;
	}
	double calculate_perimeter(){
		double perimeter=5*side;
		System.out.print("Pole pieciokota dla boku "+side+ " wynosi: ");
		return perimeter;
	}
}
class Square extends Quadrangle{
	double side;
	Square(double si){
		side=si;
	}
	double calculate_area(){
		double area=side*side;
		System.out.print("Pole kwadratu dla boku "+side+ " wynosi: ");
		return area;
	}
	double calculate_perimeter(){
		double perimeter=4*side;
		System.out.print("Obwod kwadratu dla boku "+side+ " wynosi: ");
		return perimeter;
	}
}
class Rectangle extends Quadrangle{
	double side1, side2;
	Rectangle(double si1,double si2){
		side1=si1;
		side2=si2;
	}
	double calculate_area(){
		double area=side1*side2;
		System.out.print("Pole prostokata dla bokow "+side1+" i "+side2+" wynosi: ");
		return area;
	}
	double calculate_perimeter(){
		double perimeter=2*side1+2*side2;
		System.out.print("Obwod prostokata dla bokow "+side1+" i "+side2+" wynosi: ");
		return perimeter;
	}
}
class Lozenge extends Quadrangle{
	double side, angle;
	Lozenge(double si,double an){
		side=si;
		angle=an;
	}
	double calculate_area(){
		double area=side*side*sin(toRadians(angle));
		System.out.print("Pole rombu dla boku "+side+" oraz kata "+angle+" wynosi: ");
		return area;
	}
	double calculate_perimeter(){
		double perimeter=4*side;
		System.out.print("Obwod rombu dla boku "+side+" wynosi: ");
		return perimeter;
	}
}

public class Figures{
	public static void main (String[] args) throws MyException{
        if (args.length==0) throw new MyException("Nie podano argumnetow");
		int param=0, ind=0;
		int[] arr = new int[args.length-1];
		for(int i=0; i<args.length-1; i++){
			try{arr[i]=Integer.parseInt(args[i+1]);}
			catch(NumberFormatException ex){
				System.out.println(" Nieprawidlowa dana"+args[i+1]);
				return;
			}
		}
		for(int i=1; i<=args[0].length(); i++){
			if(args[0].startsWith("p", i-1)||args[0].startsWith("o", i-1)) param+=1;
			else
				if(args[0].startsWith("c", i-1)) param+=5;
				else throw new MyException("Podano zly argument "+args[0]);	
		}
		if(param+1!=args.length) throw new MyException("Zla ilosc parametrow");
		Figure[] figures=new Figure[args[0].length()];
		for(int i=1; i<=args[0].length(); i++){
			if(args[0].startsWith("p", i-1)){
				if (arr[ind]<=0) throw new MyException ("Zla wartosc boku dla pieciokata "+arr[ind]);
				figures[i-1]= new Pentagon(arr[ind]);
				ind+=1;
			}
			else
				if(args[0].startsWith("o", i-1)){
					if (arr[ind]<=0) throw new MyException ("Zla wartosc promienia dla kola "+arr[ind]);
					figures[i-1]= new Circle(arr[ind]);
					ind+=1;
				}
				else
					if(args[0].startsWith("c", i-1)){
						if(((arr[ind]==arr[ind+1])&&(arr[ind+2]==arr[ind+3])&&(arr[ind]==arr[ind+3]))&&(arr[ind+4]!=90)){
							if (arr[ind]<=0) throw new MyException ("Zla wartosc boku dla rombu "+arr[ind]);
							if (arr[ind+4]<=0 || arr[ind+4]>=180) throw new MyException ("Zla wartosc kata dla rombu " +arr[ind+4]);
							figures[i-1]= new Lozenge(arr[ind],arr[ind+4]);
							ind+=5;
						}
						else
							if((arr[ind]==arr[ind+1])&&(arr[ind+2]==arr[ind+3])&&(arr[ind]==arr[ind+3])){
								if (arr[ind]<=0) throw new MyException ("Zla wartosc boku dla kwadratu "+arr[ind]);
								figures[i-1]= new Square(arr[ind]);
								ind+=5;
							}
							else
								if((((arr[ind]==arr[ind+1])&&(arr[ind+2]==arr[ind+3]))||((arr[ind]==arr[ind+2])&&(arr[ind+1]==arr[ind+3]))||((arr[ind]==arr[ind+3])&&(arr[ind+2]==arr[ind+1])))&&(arr[ind+4]==90)){
									int temp=1;
									while(arr[ind]==arr[ind+temp]){
										temp++;
									}
									if (arr[ind]<=0 || arr[ind+temp]<=0) throw new MyException ("Zla wartosc bokow dla prostokata "+arr[ind]+" "+arr[ind+temp]);
									figures[i-1]= new Rectangle(arr[ind],arr[ind+temp]);
									ind+=5;
								}
								else throw new MyException("Zle podane boki czworokata "+arr[ind]+" "+arr[ind+1]+" "+arr[ind+2]+" "+arr[ind+3]);
			}		
		}
		for(int i=0; i<args[0].length(); i++){
		System.out.println(figures[i].calculate_perimeter());
		System.out.println(figures[i].calculate_area());
		}
	}
}
