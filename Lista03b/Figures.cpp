#include <iostream>
#include <cstring>
#include <cstdlib>
#include <cmath>
using namespace std;

class Figure{
public:
	virtual double calculate_area()=0;
	virtual double calculate_perimeter()=0;
};
class Quadrangle : public Figure{};
class Circle : public Figure{
	double radius;
public:
	Circle(double rad){
		radius=rad;
	}
	double calculate_area(){
		double area=M_PI*radius*radius;
		cout<<"Pole kola dla promienia "<<radius<< " wynosi: ";
		return area;
	}
	double calculate_perimeter(){
		double perimeter=2*M_PI*radius;
		cout<<"Obwod kola dla promienia "<<radius<< " wynosi: ";
		return perimeter;
	}
};
class Pentagon : public Figure{
	double side;
public:
	Pentagon(double si){
		side=si;
	}
	double calculate_area(){
		double area=(5/4)*side*side*(1/tan(36*M_PI/180));
		cout<<"Pole pieciokota dla boku "<<side<< " wynosi: ";
		return area;
	}
	double calculate_perimeter(){
		double perimeter=5*side;
		cout<<"Obwod pieciokota dla boku "<<side<< " wynosi: ";
		return perimeter;
	}
};
class Square : public Quadrangle{
	double side;
public:
	Square(double si){
		side=si;
	}
	double calculate_area(){
		double area=side*side;
		cout<<"Pole kwadratu dla boku "<<side<< " wynosi: ";
		return area;
	}
	double calculate_perimeter(){
		double perimeter=4*side;
		cout<<"Obwod kwadratu dla boku "<<side<< " wynosi: ";
		return perimeter;
	}
};
class Rectangle : public Quadrangle{
	double side1, side2;
public:
	Rectangle(double si1,double si2){
		side1=si1;
		side2=si2;
	}
	double calculate_area(){
		double area=side1*side2;
		cout<<"Pole prostokata dla bokow "<<side1<<" i "<<side2<<" wynosi: ";
		return area;
	}
	double calculate_perimeter(){
		double perimeter=2*side1+2*side2;
		cout<<"Obwod prostokata dla bokow "<<side1<<" i "<<side2<<" wynosi: ";
		return perimeter;
	}
};
class Lozenge : public Quadrangle{
	double side, angle;
public:
	Lozenge(double si,double an){
		side=si;
		angle=an;
	}
	double calculate_area(){
		double area=side*side*sin(angle*M_PI/180);
		cout<<"Pole rombu dla boku "<<side<<" oraz kata "<<angle<<" wynosi: ";
		return area;
	}
	double calculate_perimeter(){
		double perimeter=4*side;
		cout<<"Obwod rombu dla boku "<<side<<" wynosi: ";
		return perimeter;
	}
};

int main (int argc, char* argv[]){
	int param=0, ind=0;
	try{
		if(argc<2) throw (string) "Nie podano parametrow";
		string fig=argv[1];
		int arr[argc-2];
		for(int i=0; i<argc-2; i++){
			if(atoi(argv[i+2])!=0 || strcmp(argv[i+2],"0")==0)
				arr[i]=atoi(argv[i+2]);
		else throw (string) ("Nieprawidlowy parametr "+(string)argv[i+2]);
		}
		for(int i=0; i<fig.length(); i++){
			if(fig[i]=='o'||fig[i]=='p') param+=1;
			else
				if(fig[i]=='c') param+=5;
				else throw (string) ("Zly argument okreslajacy figure "+fig);	
		}
		if(param+2!=argc) throw (string) "Zla ilosc parametrow";
		Figure* figures[fig.length()];
		for(int i=0; i<fig.length(); i++){
			if(fig[i]=='p'){
				if (arr[ind]<=0) throw (string) ("Zla wartosc boku dla pieciokata "+(string)argv[ind+2]);
				figures[i]= new Pentagon(arr[ind]);
				ind+=1;
			}
			else
				if(fig[i]=='o'){
					if (arr[ind]<=0) ("Zla wartosc promienia dla kola "+(string)argv[ind+2]);
					figures[i]= new Circle(arr[ind]);
					ind+=1;
				}
				else
					if(fig[i]=='c'){
						if(((arr[ind]==arr[ind+1])&&(arr[ind+2]==arr[ind+3])&&(arr[ind]==arr[ind+3]))&&(arr[ind+4]!=90)){
							if (arr[ind]<=0) throw (string) ("Zla wartosc boku dla rombu "+(string)argv[ind+2]);
							if (arr[ind+4]<=0 || arr[ind+4]>=180) throw (string) ("Zla wartosc kata "+(string)argv[ind+6]);
							figures[i]= new Lozenge(arr[ind],arr[ind+4]);
							ind+=5;
						}
						else
							if((arr[ind]==arr[ind+1])&&(arr[ind+2]==arr[ind+3])&&(arr[ind]==arr[ind+3])){
								if (arr[ind]<=0) throw (string) ("Zla wartosc boku dla kwadratu "+(string)argv[ind+2]);
								figures[i]= new Square(arr[ind]);
								ind+=5;
							}
							else
							if((((arr[ind]==arr[ind+1])&&(arr[ind+2]==arr[ind+3]))||((arr[ind]==arr[ind+2])&&(arr[ind+1]==arr[ind+3]))||((arr[ind]==arr[ind+3])&&(arr[ind+2]==arr[ind+1])))&&(arr[ind+4]==90)){
									int temp=1;
									while(arr[ind]==arr[ind+temp]){
										temp++;
									}
								if (arr[ind]<=0 || arr[ind+temp]<=0) throw (string) ("Zla wartosc bokow dla prostokata "+(string)argv[ind+2]+" "+(string)argv[ind+temp+2]);
								figures[i]= new Rectangle(arr[ind],arr[ind+temp]);
								ind+=5;
							}
							else throw (string) ("Zle podane boki czworokata "+(string)argv[ind+2]+" "+(string)argv[ind+3]+" "+(string)argv[ind+4]+" "+(string)argv[ind+5]);
			}		
		}
		for(int i=0; i<fig.length(); i++){
		cout<<figures[i]->calculate_perimeter()<<endl;
		cout<<figures[i]->calculate_area()<<endl;
		}
	}
	catch(string m){cout<< m<<endl;}
}
