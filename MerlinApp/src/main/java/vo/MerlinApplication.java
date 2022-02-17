package vo;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class MerlinApplication {

	private static final String runeStoneFile = "./runeStone.csv";
	
	public static void main(String[] args) throws IOException, CsvValidationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Reader reader = Files.newBufferedReader(Paths.get(runeStoneFile));
		CSVReader headerReader = new CSVReader(reader);
		String[] headerLine = headerReader.readNext();
		reader.reset();
		//headerReader.close();   //this line might be a problem
		CsvToBean<RuneStone> runeRecords = new CsvToBeanBuilder<RuneStone>(reader).withType(RuneStone.class).withSkipLines(0).build();
		List<RuneStone> runeRecordList = runeRecords.parse();   //creates arraylist of the 
		
		String decipheredMessage = ""; //better to start as an mt string than a null string
		//nested for
		for (RuneStone r : runeRecordList) {
		for (int i = 0; i < headerLine.length; i++) {
			//System.out.println(r.getScribe0());
			
			try {
				//System.out.println(r.getScribe0());
				
				Method m = RuneStone.class.getMethod("get" + headerLine[i].substring(0,1).toUpperCase() + headerLine[i].substring(1));
				String phrase = (String) m.invoke(r);  //here we are just throwing the exception
				String decipheredLetter = phrase.substring(i, i+1);
				decipheredMessage = decipheredMessage + decipheredLetter; // or use +=
				
				
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		System.out.println(decipheredMessage);
		headerReader.close();
	}

}
