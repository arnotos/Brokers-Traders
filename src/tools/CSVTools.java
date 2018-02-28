package tools;
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
	static String name =  "BrokerTraders.csv";
	
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
	
	public static ArrayList<String[]> readCSV() {
		String csvFile = name;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		ArrayList<String[]> allActionFromCsv = new ArrayList<String[]>();
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] action = line.split(cvsSplitBy);
				allActionFromCsv.add(action);
				System.out.println("Action [code= " + action[1] + " , name=" + action[2] + " , quantity=" + action[3]
						+ " , price=" + action[4] + "]");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return allActionFromCsv;
		}

	}
}