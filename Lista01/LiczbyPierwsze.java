import java.util.ArrayList;

public class LiczbyPierwsze{
	ArrayList<Integer> pierwsze = new ArrayList<Integer>();
	LiczbyPierwsze(int n){
		int i=0, j=0, pierw=0;
		int liczby[] = new int[n];
		pierw =(int) Math.sqrt(n);
		for (i=0; i<=n-2; i++) {liczby[i]=i+2;}
		for (i=0; i<=pierw; i++) {
			if (liczby[i] != 0) {
				j = i + (i + 2);
				while (j < n) {
					liczby[j] = 0;
					j += (i + 2);

				}
			}
		}
		for (i=0; i<=n-1; i++){
			if(liczby[i]!=0){
				pierwsze.add(liczby[i]);
			}
		}
	}
	public int liczba(int m){
		if(m>=0 && m<pierwsze.size()) {return pierwsze.get(m);}
		else {return 1;}
	}	
}

		
		
		
