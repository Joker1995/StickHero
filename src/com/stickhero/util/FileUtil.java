package com.stickhero.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtil {

	public static ArrayList<String> readFileToString(String filePath) throws IOException {
		ArrayList<String> rulesList = new ArrayList<String>();
		BufferedReader br = null;
    	try{
    		br = new BufferedReader(new FileReader(filePath));
    		String temp = null;
		 while((temp = br.readLine()) != null)
		 {
			  rulesList.add(temp);
		 }
    	}
    	catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		return rulesList;
	
    }

    public static void writeScore(int score, String filePath) throws FileNotFoundException, IOException {
    	//将数据文件中的用户信息取出,进行对比后,加入玩家信息(数据按降序排序)
		ArrayList<String> userList = new ArrayList<String>();
		BufferedReader br = null;
		BufferedWriter bw = null;

		try 
		{
			 br = new BufferedReader(new FileReader(filePath));
			 String temp = null;
			 while((temp = br.readLine()) != null)
				  userList.add(temp);
			 
			 int[] value = new int[userList.size()-1];
			 for(int i = 1; i <=value.length; i++)
			 {
				 value[i-1] = Integer.parseInt(userList.get(i));
			 }	 
			 dascSort(value);
			 
			 for(int i = 1; i < value.length; i++)
			 {	
				 
				if(score<=value[value.length-1])
					break;
				else if(score>value[0]){
					for(int j=1;j<value.length-1;j++){
						value[j+1]=value[j];
					}
					value[0]=score;
					break;
					}
				else if(score<value[i-1]&&score>value[i]){
					for(int j=i;j<value.length-1;j++){
						if(j==9)
							break;
						value[j+1]=value[j];
					}
					value[i]=score;
					break;
				}
			 }
			 
			 for(int i = 1; i < userList.size(); i ++)
			 {
				 temp = Integer.toString(value[i-1]); 
				 userList.set(i, temp);
			 }	 
			 

			 //重新写入数据文件
			 bw = new BufferedWriter(new FileWriter(filePath));
			 for(int i = 0; i < userList.size();i ++)
			 {
				 bw.write((String)userList.get(i));
				 bw.newLine();
			 }
			 } 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(br != null && bw != null)
			{
				try {
					br.close();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
    }

    public static ArrayList<String> readScore(String filePath)  {
    	ArrayList<String> userList = new ArrayList<String>();
		BufferedReader br = null;
    	try{
    		br = new BufferedReader(new FileReader(filePath));
    		String temp = null;
		 while((temp = br.readLine()) != null)
		 {
			  userList.add(temp);
		 }
    	}
    	catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		return userList;
	
    }
    
    /** 选择降序排序 **/
    public static int[] dascSort(int[] param) {
    int in, out;
    int max;
    int temp;
    for (out = 0; out < param.length; out++) {
    // 默认最大数的位置
    max = out;
    for (in = out + 1; in < param.length; in++) {
    if (param[max] < param[in]) {
    // 获取最大数的位置
    max = in;
    }
    }
    // 当默认位置的最大数并不是实际的最大数时，和实际的最大数交换位置
    if (out != max) {
    temp = param[out];
    param[out] = param[max];
    param[max] = temp;
    }
    }
    return param;
    }
}