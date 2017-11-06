package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Generators {

public static List<String> imionaM = new ArrayList<String>();	
public static List<String> imionaZ = new ArrayList<String>();	
public static List<String> nazwiskaM = new ArrayList<String>();	
	

public static String genPostalCode(){
	
   return "0"+Utils.random(1, 9)+"-"+Utils.random(111, 999);
	
}	
	
public static String genPhoneNumberHome(){
	
	return Utils.random(111111111,999999999)+"";	
	
}

public static String genPhoneNumberMobile(){
	
	return Utils.random(111111111,999999999)+"";	
	
}


//public static String genEmail(){
//	
//	return Utils.randomString(5)+"@"+Utils.randomString(5)+".com";
//	
//	}


public static String genPesel() {
    int wagi[] = new int[] { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3 };
    int Kontrolna;
    int rok = (int) (Math.random() * 100);
    int miesiac = (int) (Math.random() * 12 +1 );
    int dzien = (int) (Math.random() * 28 +1); // uproszczone
    int numerEwidencyjny = (int) (Math.random() * 999);
    int plec = (int) (Math.random() * 9);

    String pesel = String.format("%02d%02d%02d%03d%d", rok, miesiac, dzien, numerEwidencyjny, plec);
    System.out.println(pesel);
    char[] peselElem = pesel.toCharArray();
    int sumaKontrolna = 0;
    for (int i = 0; i < wagi.length; i++) {
        sumaKontrolna += wagi[i] * Integer.parseInt(""+peselElem[i]);
    }
///System.out.println(sumaKontrolna%10);
    if ((sumaKontrolna%10)==0){
    	Kontrolna=(sumaKontrolna%10);
    	
    } else {
    	Kontrolna = 10- (sumaKontrolna%10);
    	
    }
    
    
    return pesel + Kontrolna;
}

public static int calculateControlForNip(int niprandom){
	
	String nip = Integer.toString(niprandom);	
	int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
	int sum = 0;
	for (int i = 0; i < weights.length; i++) {
		sum += Integer.parseInt(nip.substring(i, i + 1)) * weights[i];
	}
	
	int kontrolna=(sum % 11);
	return kontrolna;
}

public static String genNrbNumber(){
	
	int wagi[] = new int[] {1, 10, 3, 30, 9, 90, 27, 76, 81, 34, 49, 5, 50, 15, 53, 45, 62, 38, 89, 17, 73, 51, 25, 56, 75, 71, 31, 19, 93, 57};	
	String kodKraju="2521";	
	String nrb = String.valueOf(Utils.random(11111111, 99999999)) + String.valueOf(Utils.random(11111111, 99999999)) + String.valueOf(Utils.random(11111111, 99999999))  + kodKraju+"00";
		
	int sum = 0;

	for (int i = wagi.length-1; i >=0; i--) {
		
		sum += Integer.parseInt(nrb.substring(i, i + 1)) * wagi[29-i];
	
	}
	
	
	String kontrolna =String.valueOf(98-(sum%97));	
	if (kontrolna.length()==1) kontrolna= "0"+kontrolna;
	
	return kontrolna+nrb.substring(0, 24);
	
	
}

public static String genNip() {
	int kontrolna;
	String nip;
	//int niprandom=(int) Math.floor(100000000 + Math.random() * 900000000);
	int niprandom=Utils.random(111111111, 999999999);
	kontrolna = calculateControlForNip(niprandom);
	nip = Integer.toString(niprandom);
	if ( kontrolna == 10 ){
		niprandom = niprandom+1;
		kontrolna=calculateControlForNip(niprandom);
		nip = Integer.toString(niprandom);
	} 
	 
	
	return nip+kontrolna;
	
	
}

public static String genIdNumber(){
	String idNumber = genIdNumberForCheck();
	while(idNumber.length()!=9){
		
		idNumber = genIdNumberForCheck();	
	}	
	
	
	return idNumber;
}


public static String genIdNumberForCheck() {
    int wagi[] = new int[] { 7, 3, 1, 7, 3, 1, 7, 3 };

    int lit1 = (int) (Math.random() * 25 + 10);
    int lit2 = (int) (Math.random() * 25 + 10);
    int lit3 = (int) (Math.random() * 25 + 10);
    int nbr = (int) (Math.random() * 99999 + 1);

    char[] nbrAsch = String.format("%05d", nbr).toCharArray();
    int sumaKontrolna = wagi[0] * lit1 + wagi[1] * lit2 + wagi[2] * lit3;
    for (int i = 3; i < wagi.length; i++) {
        final int parseInt = Integer.parseInt("" + nbrAsch[i-3]);
        sumaKontrolna += wagi[i] * parseInt;
    }
    return (char)(lit1+55)+""+(char)(lit2+55)+""+(char)(lit3+55) + ""+(sumaKontrolna%10)+""+nbr;

}

public static String genStreetFlatNumber(){
	
	return Utils.random(1,999)+"";	
	
}

public static List<String> getDataFromFile(String filename, List<String> lista) throws FileNotFoundException	{
	
	Scanner in = new Scanner(new File(filename));

	while (in.hasNext()) { 
	    String line = in.nextLine();
	    lista.add(line);
	}

	in.close(); 
	return lista;
}
	
public static String genFirstMaleName() throws FileNotFoundException{
	getDataFromFile("src/test/resources/ImionaM.txt",imionaM );
	
	return imionaM.get(Utils.random(0, imionaM.size()-1));
	
}

public static String genLastMaleName() throws FileNotFoundException{
	getDataFromFile("src/test/resources/NazwiskaM.txt",nazwiskaM );
	
	return nazwiskaM.get(Utils.random(0, nazwiskaM.size()-1));
	
}

public static String genFirstFemaleName() throws FileNotFoundException{
	
	getDataFromFile("src/test/resources/ImionaZ.txt",imionaZ );
	
	return imionaZ.get(Utils.random(0, imionaZ.size()-1));
	
}	

public static String genDate(String format,String zakresZmiany,int zmiana){
	
	Calendar calendar = Calendar.getInstance();
	switch(zakresZmiany){
	case "Y":
	calendar.add(Calendar.YEAR, zmiana);
	break;
	case "M":
	calendar.add(Calendar.MONTH, zmiana);
	break;
	case "D":
	calendar.add(Calendar.DAY_OF_MONTH, zmiana);
	break;
	}
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	return sdf.format(calendar.getTime()); 
	
	}
	
}
