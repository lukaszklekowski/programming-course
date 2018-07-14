class Test{
	public static void main(String[] args){
		int n=0, m=0;
		if(args.length>0){
			try {n=Integer.parseInt(args[0]);}
			catch(NumberFormatException ex){
				System.out.println(args[0] + " Nieprawidlowa dana");
				return;
			}
			if(n<2){
				System.out.println(n + " Nieprawidlowy zakres");		
				return;
			}
			LiczbyPierwsze licz=new LiczbyPierwsze(n);
			for(int i=1; i<args.length; i++){
				try{
					m=Integer.parseInt(args[i]);
					if(licz.liczba(m)==1) { System.out.println(m + " liczba spoza zakresu"); }
					else {System.out.println(m + " " + licz.liczba(m));}
				}
				catch(NumberFormatException ex) {System.out.println(args[i] + " nieprawidlowa dana");}
			}
		}
	}
}
