#include <iostream>
#include <vector>
#include <cmath>
#include <cstdlib>
#include <cstring>
using namespace std;

class LiczbyPierwsze{
public:
	vector < int > pierwsze;
	LiczbyPierwsze(int n){
		int i=0, j=0, pierw=0;
		int liczby[n];
		pierw =(int) sqrt(n);
		for (i=0; i<=n-2; i++) {liczby[i]=i+2;}
		for (i=0; i<=pierw; i++) {
			if (liczby[i] != 0) {
				j=i+(i+2);
				while (j<n) {
					liczby[j]=0;
					j+=(i+2);
				}
			}
		}
		for (i=0; i<=n-1; i++){
			if(liczby[i]!=0){
				pierwsze.push_back(liczby[i]);
			}
		}
	}
	~LiczbyPierwsze(){ cout << "Usunięto klasę" << endl;}
	int liczba(int m){
		if(m>=0 && m<pierwsze.size()) {return *(pierwsze.begin()+m);}
		else {	
			return 1;}
	}	
};
int main(int argc, char* argv[]){
	int n=0, m=0;
		if(atoi(argv[1])==0 && strcmp(argv[1], "0") != 0 ){
			cout << argv[1] << " Nieprawidlowa dana" << endl;
			return 0;
		}
		if(atoi(argv[1])<2){
			cout << argv[1] << " Nieprawidlowy zakres" << endl;
			return 0;
		}
		LiczbyPierwsze * licz=new LiczbyPierwsze(atoi(argv[1]));
		for(int i=3; i<=argc; i++){
			if(atoi(argv[i-1])==0 && strcmp(argv[i-1], "0") != 0 ){
				cout << argv[i-1] << " Nieprawidlowa dana" << endl;
			}
			else{
				if(licz->liczba(atoi(argv[i-1]))==1){
					cout << argv[i-1] << " Liczba spoza zakresu" << endl;
				}
				else{
					cout << argv[i-1] << " " << licz->liczba(atoi(argv[i-1])) << endl;
				}
			}
		}
	delete licz;
}
