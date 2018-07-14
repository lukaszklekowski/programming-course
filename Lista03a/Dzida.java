public class Dzida{
	static String arr[][]={{" przeddzidzia","Przeddzidzie"},{" sroddzidzia","Sroddzidzie"},{" zadzidzia","Zadzidzie"}};
	public static void spear(int lvl,int k, String str){	
		if (lvl==1){
			if (str=="")
				System.out.println("Dzida sklada sie z"+arr[0][0]+str+","+arr[1][0]+str+" i"+arr[2][0]+str+" dzidy.");
			else{
				System.out.println(arr[k][1]+str.substring(arr[k][0].length())+" sklada sie z"+arr[0][0]+str+","+arr[1][0]+str+" i"+arr[2][0]+str+" dzidy.");
			}
			return;
		}
		else{
            		spear(lvl-1,0,arr[0][0]+str);
			spear(lvl-1,1,arr[1][0]+str);
			spear(lvl-1,2,arr[2][0]+str);
		}
	}
	public static void main(String args[]){
		int lvl=0;
		try{
			lvl=Integer.parseInt(args[0]);
			for(int i=1; i<=lvl; i++)
			spear(i,0, "");
		}
		catch(NumberFormatException ex){System.out.println("Podany argument nie jest liczba");}
	}
}
