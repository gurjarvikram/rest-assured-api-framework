package resources;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

	}
	
	//Identify Testcases column by scaning the entire 1st row
	//Once column is identify then scan entire testcases column to identify purchase testcase row
	//After you grab purchase testcase row = pull all the data of that row and feed into test
	
	public ArrayList<String> getData(String testCaseName, String sheetName) throws IOException
	{
		//FileIputStream argument
		ArrayList<String> a = new ArrayList<String>();
		
		
		FileInputStream fis = new FileInputStream("/home/b/Desktop/API-Contract/dataDemo.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		int sheets = workbook.getNumberOfSheets();
		for(int i= 0; i < sheets; i++)
		{
			if(workbook.getSheetName(i).equalsIgnoreCase(sheetName))
			{
				XSSFSheet sheet = workbook.getSheetAt(i);
				//Identify Testcases column by scaning the entire 1st row
				
				Iterator<Row> rows = sheet.iterator();  // Sheet is collection of rows
				Row firstrow = rows.next();
				Iterator<Cell> ce = firstrow.cellIterator(); //row is collection of cells
				
				int k =0;
				int coloumn = 0;
				while(ce.hasNext())
				{
					Cell value = ce.next();
					if(value.getStringCellValue().equalsIgnoreCase("TestCases"))
					{
						//Desired coloumn 
						coloumn = k;
					}
					k++;
				}
				System.out.println(coloumn);
				
				//Once column is identify then scan entire testcases column to identify purchase testcase row
				while(rows.hasNext())
				{
					Row r = rows.next();
					if(r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testCaseName))
					{
						//After you grab purchase testcase row = pull all the data of that row and feed into test
						Iterator<Cell> cv = r.cellIterator();
						while(cv.hasNext())
						{
							Cell c = cv.next();
							if(c.getCellType()== CellType.STRING)
							{
								a.add(c.getStringCellValue());
							}
							else
							{
								a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
							
						}
						break; // Stop after finding the first matching row
					}
					
				}
				
				
			}
		
		}
		return a;
	}

}
