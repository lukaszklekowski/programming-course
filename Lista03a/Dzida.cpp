#include <iostream>
#include <cstring>
#include <cstdlib>

using namespace std;

static string arr[3][2]={{" przeddzidzia","Przeddzidzie"},{" sroddzidzia","Sroddzidzie"},{" zadzidzia","Zadzidzie"}};

class Dzida{
public:
	static void spear(int lvl,int k, string str){		
		if (lvl==1){
			if (str=="")
				cout<<"Dzida składa się z"<<arr[0][0]+str<<","<<arr[1][0]+str<<" i"<<arr[2][0]+str<<" dzidy."<<endl;
			else{
			cout<<arr[k][1]+str.substr(arr[k][0].length())<<" sklada sie z"<<arr[0][0]<<str<<","<<arr[1][0]<<str<<" i"<<arr[2][0]<<str<<" dzidy."<<endl;
			}
			return;
		}
		else{
            		spear(lvl-1,0,arr[0][0]+str);
			spear(lvl-1,1,arr[1][0]+str);
			spear(lvl-1,2,arr[2][0]+str);
		}
	}
};

int main(int argc, char* argv[]){
		if(atoi(argv[1])>0) {
			for(int i=1; i<=atoi(argv[1]); i++)
			Dzida::spear(i,0, "");
		}
		else{cout<<"Zly argument"<<endl;}
}
