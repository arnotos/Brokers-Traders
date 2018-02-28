import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVTools
{
	static String name = "data.csv";
	
	public static void createFile()
	{
		Boolean isAlreadyCreate = new File(name).isFile();
		
		if(isAlreadyCreate)
		{
			PrintWriter pw;
			try {
				pw = new PrintWriter(new File(name));
		        StringBuilder sb = new StringBuilder();
		        sb.append("sizeSocket");
		        sb.append(";");
		        sb.append("type");
		        sb.append(';');
		        sb.append("actionName");
		        sb.append(';');
		        sb.append("quantity");
		        sb.append(';');
		        sb.append("price");
		        sb.append('\n');

		        pw.write(sb.toString());
		        pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
    }
	
	public static void addRows(String data)
	{
		String tmp = "";
		String[] splitData = data.split("//");
		for (String str : splitData)
		{
			tmp += str + ";";
		}
		
		tmp = tmp.substring(0, tmp.length() - 1);
		
		try{
            FileWriter fw = new FileWriter(name, true);
            BufferedWriter fbw = new BufferedWriter(fw);
            fbw.write(tmp);
            fbw.newLine();
            fbw.close();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
	}

	public static void main(String[] args)
	{
		createFile();
		addRows("21//B//AZER//1234//1235.32");
		addRows("22//B//AZER//1234//1235.32");
		addRows("23//B//AZER//1234//1235.32");
		
		try {
			ReadCSVWithScanner();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ReadCSVWithScanner() throws IOException
	{
		// open file input stream
		BufferedReader reader = new BufferedReader(new FileReader(name));
		// read file line by line
		String line = null;
		Scanner scanner = null;
		int index = 0;
		//List<Employee> empList = new ArrayList<>();

		while ((line = reader.readLine()) != null) {
			scanner = new Scanner(line);
			scanner.useDelimiter(";");
			while (scanner.hasNext()) {
				String data = scanner.next();
				/*if (index == 0)
					emp.setId(Integer.parseInt(data));
				else if (index == 1)
					emp.setName(data);
				else if (index == 2)
					emp.setRole(data);
				else if (index == 3)
					emp.setSalary(data);
				else
					System.out.println("invalid data::" + data);
				*/
				//if(index == )
				
				index++;
				System.out.println(data);
			}
			index = 0;
		}
		
		//close reader
		reader.close();			
	}
}