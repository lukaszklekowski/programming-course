class RzymArabException extends Exception {
	RzymArabException(String message) {
		super(message);
	}	
};

public class RzymArab {
	private static String[] roman={"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};
	private static int[] arabic={1,4,5,9,10,40,50,90,100,400,500,900,1000};
	public static int rzym2arab (String rome) throws RzymArabException {
		rome=rome.toUpperCase();
		int arab=0;
		int index=0;
		for (int i=roman.length-1; i>=0; i--) {
			while (rome.startsWith(roman[i], index)) {
				arab+=arabic[i];
				index+=roman[i].length();
			}
		}  
		if (arab<1 || arab>3999) throw new RzymArabException("Nieprawidlowa liczba rzymska");
		if (rome.compareTo(arab2rzym(arab))!=0) throw new RzymArabException("Nieprawidłowa liczba rzymska");
		return arab;
	}
	public static String arab2rzym (int arab) throws RzymArabException {
		if (arab<1 || arab>3999) throw new RzymArabException("Liczba spoza zakresu");
		int i=roman.length-1;
		String rome="";
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
	public static void main(String[] args) {
		int number=0;
		for(int i=0; i<args.length; i++){
			try {
				number=Integer.parseInt(args[i]);
				try {
					arab2rzym(number);
					System.out.println(arab2rzym(number));
				}
				catch(RzymArabException e){System.out.println(e.getMessage());}
			}
			catch(NumberFormatException ex){
				try {
					rzym2arab(args[i]);
					System.out.println(rzym2arab(args[i]));
				}
				catch(RzymArabException e){System.out.println(e.getMessage());}
			}
		}
	}
}
