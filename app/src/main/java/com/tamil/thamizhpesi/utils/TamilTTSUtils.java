package com.tamil.thamizhpesi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tamil.thamizhpesi.constants.TamilTTSConstants;




public class TamilTTSUtils 
{
	  public static boolean checkPresence(String[] escapeWordArray, String word)
	  {
		for(String value: escapeWordArray)
		{
			if(value.trim().equalsIgnoreCase(word))
			{
				return true;
			}
		}
		return false;
	  }
	   
	   public static boolean findPresence(String[] escapeWordArray, String word)
	   {
		   for(String value: escapeWordArray)
			{
				if(word.contains(value.trim()))
				{
					return true;
				}
			}
			return false;
	   }
	   
	   public static List<String> getLetterList(String word)
	   {
		   word = word.trim();
		   char[] character = word.toCharArray();
		   int i = 0;
		   boolean abbrevation = false;
		   List<String> lettersList = new ArrayList<String>();
		   
		   if(character.length <= 0){return null;}
		   
		   if(checkPresence(TamilTTSConstants.உயிர்_எழுத்துக்கள், String.valueOf(character[i])))
		   {
			   lettersList.add(word.substring(i, i+1));
			   i=1;
			   
		   }
		   for(; i < character.length; i++)
		   {
			    
			   if(character[i] == ')' || character[i] == '(' || character[i] == '”' || character[i] =='“' || character[i] == '‘' || character[i] == '’' || character[i] == '-' || character[i] == '`'
					   || character[i] == '\'')
			   {
				   continue;
			   }
			   if((character[i] == '.' && i == character.length - 1) || character[i] == '\n')
			   {
			   		
			   	
			   		continue;
			   }
			   
			   if(character[i] == '.' )
			   {
			   	   continue;
			   }
			   
			   if(character[i] == '?')
			   {
			   		
			   		continue;
			   }
			   if(String.valueOf(character[i]).trim().equals(""))
			   {
				   continue;
			   }
			   
			   if(String.valueOf(character[i]).trim().equals(","))
			   {
				   
				   continue;
			   }
			   if(String.valueOf(character[i]).trim().equals(";"))
			   {
				  
				   continue;
			   }
			   
			   if(String.valueOf(character[i]).trim().equals("!"))
			   {
				  
				   continue;
			   }

			   if(i <= character.length - 2 && checkPresence(TamilTTSConstants.உதவி_எழுத்துக்கள், String.valueOf(character[i+1])))
			   {
				   lettersList.add(word.substring(i, i+2));
				   i+=1;
				   continue;
				   
			   }
			   lettersList.add(word.substring(i,i+1));
		   }
		   
		   return lettersList;
	   }
	   public static String getString(int start, int end, List<String> wordList)
	   {
		   String word = "";
		   if(start == end)
		   {
			   return wordList.get(start);
		   }
		   for(int i = start; i < end; i++)
		   {
			   word += wordList.get(i);
		   }
		   
		   return word.trim();
	   }
	   public static String getWord(List<String> letterList)
	   {
		   String str="";
		   
		   for(int i=0; i < letterList.size(); i++)
		   {
			   str += letterList.get(i);
		   }
		   
		   return str.trim();
	   }
	   
		public static boolean checkContains(String[] escapeWordArray, String word)
		{
			for(String value: escapeWordArray)
			{
				if(word.contains(value))
				{
					return true;
				}
			}
			return false;
		}
		
		public static String getContains(String[] escapeWordArray, String word)
		{
			for(String value: escapeWordArray)
			{
				if(word.contains(value))
				{
					return value;
				}
			}
			return null;
		}
}
