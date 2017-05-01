package cifafenxi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;

public class fenxi {

	public static void main(String[] args)  {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		HashMap<String, Integer> keyvalue = new HashMap<String, Integer>();
		keyvalue = readFileByLines("keyword.txt");
		
		System.out.println("������Դ�ļ���");
		String filename = input.nextLine();
		File file = new File(filename);
		String fString = filename.substring(0, 3);
		fString = fString.concat(".txt");
		BufferedReader reader = null;
		BufferedWriter outputStream = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fString)));
			
			String tempString = null;
			while((tempString = reader.readLine()) != null)
			{
				String aString = fenxiByLine(tempString, keyvalue);
				if (aString != null) {
					outputStream.write(aString);
				}
				else
				{
					System.out.println("���ִ���");
				}
			}
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
		finally {
			if (reader != null) {
				try
				{
					reader.close();
				}
				catch(Exception exception)
				{
					exception.printStackTrace();
				}
			}
			try {
				outputStream.close();
				System.out.println("ת������");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * ����Ϊ��λ��ȡ�ļ�
	 * ��ȡ��ŵ��ļ�����
	 * 
	 */
	public static HashMap<String, Integer> readFileByLines(String filename)
	{
		File file = new File(filename);
		BufferedReader reader = null;
		HashMap<String, Integer> keyvalue = new HashMap<String, Integer>();
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while((tempString = reader.readLine()) != null)
			{
				String[] line = tempString.split(" ");
				try
				{
					int a = Integer.parseInt(line[0]);
					keyvalue.put(line[1], a);
				}
				catch (Exception exception)
				{
					exception.printStackTrace();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return keyvalue;
	}
	
	/*
	 * �ʷ���������
	 * 
	 *
	public static String fenxiByLine(String string, HashMap<String, Integer> keyvalue)
	{
		String[] str = string.split(" ");
		String returnString = new String();
		Object object = null;
		for(int i = 0;i < str.length;i++)
		{
			if ((object = keyvalue.get(str[i])) != null) {
				returnString = returnString.concat(String.valueOf((int)object)+" "+str[i]+"\r\n ");
			}
			else if (isVariable(str[i])) {
				returnString = returnString.concat("9  ����"+str[i]+"\r\n ");
			}
			else {
				try {
					Integer.parseInt(str[i]);
					returnString = returnString.concat("10 ����"+str[i]+"\r\n ");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("����ʶ��:"+str[i]);
				}
			}
		}
		return returnString;
	}*/
	
	/*
	 * ��һ�ַ�����ʵ�ַ���ÿһ��
	 * 
	 */
	public static String fenxiByLine(String string, HashMap<String, Integer> keyvalue)
	{
		String returnString = new String();
		int a = 0;
		boolean judge = false;
		String str = new String();
		for(int i = 0;i < string.length();i++)
		{
			if (isLetter(string.charAt(i))) {
				str = str.concat(String.valueOf(string.charAt(i)));
			}
			else if (isNumber(string.charAt(i))) {
				int n = i+1;
				for(;n < string.length();i++)
				{
					if (!isNumber(string.charAt(n))) {
						break;
					}
				}
				if (n == i+1) {
					returnString = returnString.concat("10 ���� "+string.charAt(i)+"\r\n");
				}
				else {
					String number = string.substring(i, n);
					returnString = returnString.concat("10 ���� "+number+"\r\n");
				}	
			}
			else if (string.charAt(i) == ' ') {
				if (!str.equals("") && (a = searchHashMap(str, keyvalue)) != 0) {
					returnString = returnString.concat(String.valueOf(a)+" "+str+"\r\n");
				}
				else if (isVariable(str)) {
					returnString = returnString.concat("9  ����"+str+"\r\n");
				}
				else {
					for(int i1 = 0;i1 < str.length();i1++)
					{
						if (isNumber(str.charAt(i1))) {
							judge = true;
						}
						else							
						{	
							judge = false;
							System.out.println("����ʶ��:"+str);
						}
					}
					if (judge == true) {
						judge = false;
						returnString = returnString.concat("10 ���� "+str+"\r\n");
					}
				}
				str = "";
			}
			else if (isOperator1(string.charAt(i))) {
				if (!str.equals("") && (a = searchHashMap(str, keyvalue)) != 0) {
					returnString = returnString.concat(String.valueOf(a)+" "+str+"\r\n");
				}
				else if (isVariable(str)) {
					returnString = returnString.concat("9  ���� "+str+"\r\n");
				}
				else {
					for(int i3 = 0;i3 < str.length();i3++)
					{
						if (isNumber(str.charAt(i3))) {
							judge = true;
						}
						else							
						{	
							judge = false;
							System.out.println("����ʶ��:"+str);
						}
					}
					if (judge == true) {
						judge = false;
						returnString = returnString.concat("10 ���� "+str+"\r\n");
					}
				}
				if ((i != string.length()-1) && isOperator2(string.charAt(i+1))) {
					String string2 = string.substring(i, i+2);		
					if ((a = searchHashMap(string2, keyvalue)) != 0) {
						returnString = returnString.concat(String.valueOf(a)+" "+string2+"\r\n");
						i++;
					}
					else {
						System.out.println("��������");
					}
				}
				returnString = returnString.concat(String.valueOf(searchHashMap(String.valueOf(string.charAt(i)), keyvalue)+" "+string.charAt(i))+"\r\n");
				str = "";
			}
		}
		return returnString;
	}
	
	/*
	 * searchHashMap
	 * ����HashMap�е�value
	 * 
	 */
	public static int searchHashMap(String string, HashMap<String, Integer> keyvalue)
	{
		Object object = null;
		int a = 0;
		if((object = keyvalue.get(string)) != null)
		{
			a = (int)object;
		}
		return a;
	}
	
	/*
	 * �ж��Ƿ��Ǳ�����
	 * 
	 */
	public static boolean isVariable(String string)
	{
		if (string.equals("")) {
			return false;
		}
		if (isLetter(string.charAt(0)) || string.charAt(0) == '_') {
			for(int i = 1;i < string.length();i++)
			{
				if (!(isLetter(string.charAt(i)) || isNumber(string.charAt(i)) || string.charAt(i) == '_')) {
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*
	 * isLetter
	 * �ж��Ƿ�Ϊ��ĸ
	 * 
	 */
	public static boolean isLetter(char ch)
	{
		if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * isNumber
	 * �ж��Ƿ�������
	 * 
	 */
	public static boolean isNumber(char ch)
	{
		if (ch >= '0' && ch <= '9') {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*
	 * isOperator1
	 * �ж��Ƿ�Ϊ�����
	 * 
	 */
	public static boolean isOperator1(char ch)
	{
		if (ch == '=' || ch == '<' || ch == '>') {
			return true;
		}
		else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
			return true;
		}
		else if (ch == '!' || ch == '|' || ch == '%' || ch == '&') {
			return true;
		}
		else if (ch == '(' || ch == ')' || ch == '{' || ch == '}') {
			return true;
		}
		else if (ch == ';') {
			return true;
		}
		return false;
	}
	
	/*
	 * isOperator2
	 * �жϵڶ����ַ��Ƿ��������
	 * 
	 */
	public static boolean isOperator2(char ch)
	{
		if (ch == '=' || ch == '<' || ch == '>') {
			return true;
		}
		else if (ch == '+' || ch == '-') {
			return true;
		}
		else if (ch == '|' || ch == '&') {
			return true;
		}
		else if (ch == ';') {
			return true;
		}
		return false;
	}
}
 