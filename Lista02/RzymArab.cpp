#include <iostream>
#include <cstring>
#include <cstdlib>
using namespace std;

static string roman[13]={"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};
static int arabic[13]={1,4,5,9,10,40,50,90,100,400,500,900,1000};

class RzymArab {
public:
	static int rzym2arab (string rome) throw (string) {
		for( int i = 0; i < rome.length(); i++ ){
        		rome[i]=toupper(rome[i]);
		}
		int arab=0;
		int index=0;
		for (int i=12; i>=0; i--) {
			while (rome.compare( index, roman[i].length(), roman[i] ) == 0) {
				arab+=arabic[i];
				index+=roman[i].length();
			}
		}  
		if (arab<1 || arab>3999) throw (string)("Nieprawidlowa liczba rzymska");
		if (arab2rzym(arab)!=rome) throw (string)("Nieprawidłowa liczba rzymska");
		return arab;
	}
	static string arab2rzym (int arab) throw (string) {
		if (arab<1 || arab>3999) throw (string)("Liczba spoza zakresu");
		int i=12;
		string rome="";
		while (arab>0){
			if (arab>=arabic[i]) {
				rome+=roman[i];
				arab-=arabic[i];
				}
			else
				i--;
		}
		return rome;
	}
};	

int main(int argc, char* argv[]) {
	for(int i=1; i<argc; i++){
		if(atoi(argv[i])!=0 || strcmp(argv[i],"0")==0) {
			try {
				RzymArab::arab2rzym(atoi(argv[i]));
				cout << argv[i] << " " << RzymArab::arab2rzym(atoi(argv[i])) << endl;
			}
			catch(string e){cout << argv[i] << " " << e << endl;}
		}
		else{
			try {
				RzymArab::rzym2arab(argv[i]);
				cout << argv[i] << " " << RzymArab::rzym2arab(argv[i]) << endl;
			}
			catch(string e){cout << argv[i] << " " << e << endl;}
		}
	}
}

