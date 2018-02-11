package com.tamil.thamizhpesi.utils;

import com.tamil.thamizhpesi.constants.TamilTTSConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class NumberSynthesizer {
	
	public static NumberSynthesizer instance = null;
	public static Map<String, String> wordMap1 = new HashMap<String, String>();
	public static Map<String, String> wordMap2 = new HashMap<String, String>();
	public static Map<String, String> wordMap3 = new HashMap<String, String>();
	public static Map<String, String> wordMap4 = new HashMap<String, String>();
	public static Map<String, String> wordMap5 = new HashMap<String, String>();
	public static Map<String, String> wordMap6 = new HashMap<String, String>();
	public static Map<String, String> wordMap8 = new HashMap<String, String>();

	public static NumberSynthesizer getInstance()
	{
		if(instance == null)
		{
			instance = new NumberSynthesizer();
		}
		return instance;
	}
	
	static
	{
		wordMap1.put("0", "பூஜ்ஜியம்");
		wordMap1.put("1", "ஒன்று");
		wordMap1.put("_1", "ஒரு");
		wordMap1.put("2", "இரண்டு");
		wordMap1.put("_2", "இரு");
		wordMap1.put("3", "மூன்று");
		wordMap1.put("4", "நான்கு");
		wordMap1.put("5", "ஐந்து");
		wordMap1.put("6", "ஆறு");
		wordMap1.put("7", "ஏழு");
		wordMap1.put("8", "எட்டு");
		wordMap1.put("9", "ஒன்பது");
		wordMap2.put("1", "பத்து");
		wordMap2.put("10", "பத்து");
		wordMap2.put("_1", "பதின்");
		//wordMap2.put("1", "பதினொன்று");
		wordMap2.put("_11", "பதினோர்");
		wordMap2.put("12", "பனிரெண்டு");
		wordMap2.put("13", "பதிமூன்று");
		wordMap2.put("14", "பதினான்கு");
		wordMap2.put("15", "பதினைந்து");
		wordMap2.put("16", "பதினாறு");
		wordMap2.put("17", "பதினேழு");
		wordMap2.put("18", "பதினெட்டு");
		wordMap2.put("19", "பத்தொன்பது");		
		wordMap2.put("2", "இருபது");
		wordMap2.put("_2", "இருபத்து");
		wordMap2.put("3", "முப்பது");
		wordMap2.put("_3", "முப்பத்து");
		wordMap2.put("4", "நாற்பது");
		wordMap2.put("_4", "நாற்பத்து");
		wordMap2.put("5", "ஐம்பது");
		wordMap2.put("_5", "ஐம்பத்து");
		wordMap2.put("6", "அறுபது");
		wordMap2.put("_6", "அறுபத்து");
		wordMap2.put("7", "எழுபது");
		wordMap2.put("_7", "எழுபத்து");
		wordMap2.put("8", "என்பது");
		wordMap2.put("_8", "எண்பத்து");
		wordMap2.put("9", "தொன்னூறு");
		wordMap2.put("_9", "தொன்னுற்று");
		wordMap3.put("1", "நூறு");
		wordMap3.put("_1", "நூற்று");
		wordMap3.put("2", "இருநூறு");
		wordMap3.put("_2", "இருநூற்று");
		wordMap3.put("3", "முன்னூறு");
		wordMap3.put("_3", "முன்நூற்று");
		wordMap3.put("4", "நானூறு");
		wordMap3.put("_4", "நாநூற்று");
		wordMap3.put("5", "ஐநூறு");
		wordMap3.put("_5", "ஐநூற்று");
		wordMap3.put("6", "அறுநூறு");
		wordMap3.put("_6", "அறுநூற்று");
		wordMap3.put("7", "எழுநூறு");
		wordMap3.put("_7", "ஏழுநூற்று");
		wordMap3.put("8", "எட்நூறு");
		wordMap3.put("_8", "எட்நூற்று");
		wordMap3.put("9", "தொள்ளாயிரம்");
		wordMap3.put("_9", "தொள்ளயிரத்து");
		wordMap4.put("1", "ஆயிரம்");
		wordMap4.put("_1", "ஆயிரத்து");
		wordMap4.put("2", "இரண்டாயிரம்");
		wordMap4.put("_2", "இரண்டாயிரத்து");
		wordMap4.put("3", "மூன்றாயிரம்");
		wordMap4.put("_3", "மூன்றாயிரத்து");
		wordMap4.put("4", "நான்காயிரம்");
		wordMap4.put("_4", "நான்காயிரத்து");
		wordMap4.put("5", "ஐந்தாயிரம்");
		wordMap4.put("_5", "ஐந்தாயிரத்து");
		wordMap4.put("6", "ஆறாயிரம்");
		wordMap4.put("_6", "ஆறாயிரத்து");
		wordMap4.put("7", "ஏழாயிரம்");
		wordMap4.put("_7", "எழாயிரத்து");
		wordMap4.put("8", "எட்டாயிரம்");
		wordMap4.put("_8", "எட்டாயிரத்து");
		wordMap4.put("9", "ஒன்பதாயிரம்");
		wordMap4.put("_9", "ஒன்பதாயிரத்து");
		wordMap5.put("1", "பத்தாயிரம்");
		wordMap5.put("_1", "பத்தாயிரத்து");
		wordMap6.put("லச்சம்", "லச்சம்");
		wordMap6.put("லச்சத்து", "லச்சத்து");
		wordMap8.put("கோடி", "கோடி");
		wordMap8.put("கோடியே", "கோடியே");
	}
	
	public static String checkForNumber(String  word)
	{
		String wordBuffer="";
		try
		{
			word = word.replaceAll(",", "");
			word = word.replaceAll("-", "");
			String words[] = {};
			String pronunciation = "";
			if(TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், word))
			{
				if(checkOnlyNumbers(word))
				{
					return getPronuciation(word);
				}
				else
				{
					String numberBuffer="";
					String wholeNumber = "";
					String fraction = "";
					boolean alreadyFound = false;
					
					int startIndex = 0;
					
					if(word.contains("."))
					{
						words = word.split("\\.");
					
					
						if(words != null && words.length > 0)
						{
							for(String value : words)
							{
								if(!alreadyFound && TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், word))
								{
									wordBuffer += getPartialPronunciation(value);
									alreadyFound = true;
									continue;
								}
								
								if(alreadyFound && TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், word))
								{
									wordBuffer += "புள்ளி ";
									char insideSplit[] = value.toCharArray();
									
									for(int i = 0; i < insideSplit.length; i ++)
									{
										if(TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், String.valueOf(insideSplit[i])))
										{
											wordBuffer += getPartialPronunciation(String.valueOf(insideSplit[i]));
											continue;
										}
										else
										{
											wordBuffer += String.valueOf(insideSplit[i]); 
											continue;
										}
									}
								}
							}
						}
					}
					else
					{
						wordBuffer += getPartialPronunciation(word);
					}
					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return wordBuffer;
	}
	
	public static String getPartialPronunciation(String word)
	{
		String numberBuffer="";
		String wordBuffer="";
		int startIndex = 0;
		for(int i = 1; i <= word.length(); i++)
		{
			try
			{
				Integer.parseInt(word.substring(startIndex, i));
				numberBuffer += word.substring(startIndex++, i);
			}
			catch(Exception e)
			{
				if(numberBuffer.length() > 0)
				{
					wordBuffer += " "+getPronuciation(numberBuffer)+" ";
					numberBuffer = "";
				}
				
				wordBuffer += word.substring(startIndex, i);
				startIndex = i;
			}
		}
		if(numberBuffer.length() > 0)
		{
			wordBuffer += " "+getPronuciation(numberBuffer)+" ";
			numberBuffer = "";
		}
		
		return wordBuffer;
	}
	public static String getPronuciation(String str)
	{
		char digits[] = str.toCharArray();
		int length = digits.length;
		//for(int i = digits.length; i >= 0; i--)
		List<String> numberList = new ArrayList<String>();
		boolean wholeNumber = true;
		{
			if(length >= 3)
			{
				System.out.println(""+digits[length - 3]+digits[length - 2]+digits[length - 1]);
				String value = ""+digits[length - 3]+digits[length - 2]+digits[length - 1];
				if(!value.equalsIgnoreCase("000")) {
					wholeNumber = false;
				}
				System.out.println(findPronunciation(value));
				numberList.add(findPronunciation(value));
			}
			else
			{
				System.out.println(str);
				String value = str;
				if(!value.equalsIgnoreCase("00")) {
					wholeNumber = false;
				}
				System.out.println(findPronunciation(value));
				numberList.add(findPronunciation(value));
			}

			if(length >= 5)
			{
				System.out.println(""+digits[length -5]+digits[length -4]);
				String value = ""+digits[length -5]+digits[length -4];
				if(!value.substring(1, value.length()).equalsIgnoreCase("0")) {
					wholeNumber = false;
				}

				if(!value.equalsIgnoreCase("00"))
				{
					if(wholeNumber ) {
						numberList.add("ஆயிரம்");
					}
					else {
						numberList.add("ஆயிரத்து");
					}

					System.out.println(findPronunciation(value));
					numberList.add(findPronunciation(value));
				}


			}
			else if(length == 4)
			{
				System.out.println(""+digits[length -4]);
				String value = ""+digits[length -4];

				if(!value.equalsIgnoreCase("0"))
				{
					if(wholeNumber) {
						numberList.add("ஆயிரம்");
					}
					else {
						numberList.add("ஆயிரத்து");
					}


					System.out.println(findPronunciation(value));
					if(!value.equalsIgnoreCase("1"))
						numberList.add(findPronunciation(value));
				}
			}

			if(length >= 7)
			{
				System.out.println(""+digits[length -7]+digits[length -6]);
				String value = ""+digits[length -7]+digits[length -6];
				if(!value.substring(1, value.length()).equalsIgnoreCase("0")) {
					wholeNumber = false;
				}

				if(!value.equalsIgnoreCase("00"))
				{
					if(wholeNumber) {
						numberList.add("லட்சம்");
					}
					else {
						numberList.add("லட்சத்து");
					}

					System.out.println(findPronunciation(value));
					numberList.add(findPronunciation(value));
				}
			}
			else if(length == 6)
			{
				System.out.println(""+digits[length -6]);
				String value = ""+digits[length -6];

				if(!value.equalsIgnoreCase("0"))
				{
					if(wholeNumber) {
						numberList.add("லட்சம்");
					}
					else {
						numberList.add("லட்சத்து");
					}

					System.out.println(findPronunciation(value));
					numberList.add(findPronunciation(value));
				}
			}

			if(length >= 8)
			{
				String finalDigits = "";
				boolean flag = false;
				for(int i = 0; i <= length - 7 - 1; i++)
				{
					finalDigits += ""+digits[i];
					if(digits[i] != '0')
					{
						flag = true;

						if(i > 0)
						{
							wholeNumber = false;
						}
					}
				}
				System.out.println(""+finalDigits);
				String value = ""+finalDigits;


				if(flag)
				{
					if(wholeNumber) {
						numberList.add("கோடி");
					}
					else {
						numberList.add("கோடியே");
					}

					//System.out.println(findPronunciation(value));
					numberList.add(findPronunciation(value));
				}
			}

		}
		
		String finalPronunciation = "";
		for(int i = numberList.size() - 1; i >= 0; i--)
		{
			finalPronunciation += numberList.get(i)+" ";
		}
		return finalPronunciation;
	}
	
	public static String findPronunciation(String word)
	{
		List<String> pronunciation = new ArrayList<String>();
		char[] character = word.toCharArray();
		boolean wholeNumber = true;
		int position = 0;
		for(int i = character.length - 1; i >= 0; i--)
		{
			position++;
			if(character[i] == '0')
			{
				continue;
			}
			
						
			if(position == 1) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap1.get(""+character[i]));
					wholeNumber = false;
				}
				else
				{
					pronunciation.add(wordMap1.get("_"+(character[i])));
				}
				
				continue;
			}
			
			if(position == 2) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap2.get(""+character[i]));
					wholeNumber = false;
				}
				else
				{
					pronunciation.add(wordMap2.get("_"+(character[i])));
				}	
				continue;
			}
			
			if(position == 3) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap3.get(""+character[i]));
					wholeNumber = false;
				}
				else
				{
					pronunciation.add(wordMap3.get("_"+(character[i])));
				}	
				continue;
			}
			
			if(position == 4 && !(character.length > 4)) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap4.get(""+character[i]));
					wholeNumber = false;
				}
				else
				{
					pronunciation.add(wordMap4.get("_"+(character[i])));
				}	
				continue;
			}
			
			if(position == 5) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap4.get("1"));
					pronunciation.add(wordMap2.get(""+(character[i])));
					wholeNumber = false;
				}
				else
				{
					if(character[i + 1] != '0')
					{
						pronunciation.add(wordMap4.get("1"));
					}
					else {
						pronunciation.add(wordMap4.get("_1"));
					}
					if(character[i + 1] != '0')
					{
						pronunciation.add(wordMap1.get(""+(character[i + 1])));
					}
					pronunciation.add(wordMap2.get(""+(character[i])));
				}	
				continue;
			}
			
			if(position == 6 && !(character.length > 6)) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap6.get("லச்சம்"));
					pronunciation.add(wordMap1.get(""+(character[i])));
					wholeNumber = false;
				}
				else
				{
					pronunciation.add(wordMap6.get("லச்சத்து"));
					pronunciation.add(wordMap1.get(""+(character[i])));
				}	
				continue;
			}
			
			if(position == 7) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap6.get("லச்சம்"));
					if(character[i + 1] != '0')
					{
						pronunciation.add(wordMap1.get(""+(character[i + 1])));
					}
					pronunciation.add(wordMap2.get(""+(character[i])));
					wholeNumber = false;
				}
				else
				{
					if(character[i + 1] != '0')
					{
						pronunciation.add(wordMap6.get("லச்சம்"));
					}
					else
					{
						pronunciation.add(wordMap6.get("லச்சத்து"));
					}
					if(character[i + 1] != '0')
					{
						pronunciation.add(wordMap1.get(""+(character[i + 1])));
					}
					if(character[i + 1] != '0')
					{
						pronunciation.add(wordMap2.get("_"+(character[i])));
					}
					else
					{
						pronunciation.add(wordMap2.get(""+(character[i])));
					}
				}	
				continue;
			}
			
			if(position == 8 && !(character.length > 8)) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap8.get("கோடி"));
					pronunciation.add(wordMap1.get(""+(character[i])));
					wholeNumber = false;
				}
				else
				{
					pronunciation.add(wordMap8.get("கோடியே"));
					pronunciation.add(wordMap1.get("_"+(character[i])));
				}	
				continue;
			}
			
			if(position == 9 && !(character.length > 9)) 
			{
				if(wholeNumber)
				{
					pronunciation.add(wordMap8.get("கோடி"));
					pronunciation.add(wordMap2.get(""+(character[i])));
					wholeNumber = false;
				}
				else
				{
					pronunciation.add(wordMap8.get("கோடியே"));
					pronunciation.add(wordMap2.get("_"+(character[i])));
				}	
				continue;
			}
			
			if(position == 10) 
			{
				{	
					if(character[i + 2] == '0')
					{
						pronunciation.add(wordMap8.get("கோடி"));
					}
					else if(character[i + 3] == '0')
					{
						pronunciation.add(wordMap8.get("கோடி"));
					}
					else
					{
						pronunciation.add(wordMap8.get("கோடியே"));
					}
					if(character[i + 2] != '0')
					{
						pronunciation.add(wordMap1.get(""+(character[i + 2])));
					}
					if(character[i + 1] != '0')
					{
						if(character[i + 2] != '0') {
							pronunciation.add(wordMap2.get("_"+(character[i + 1])));
						}
						else {
							pronunciation.add(wordMap2.get(""+(character[i + 1])));
						}
							
					}
					if(character[i + 1] == '0' && character[i + 2] == '0')
					{
						pronunciation.add(wordMap3.get(""+(character[i])));
					}
					else
					{
						pronunciation.add(wordMap3.get("_"+(character[i])));
					}
				}	
				continue;
			}
		}
		
		String finalPronunciation ="";
		for(int j =  pronunciation.size() - 1 ; j >= 0 ; j--)
		{
			finalPronunciation += pronunciation.get(j)+"  ";
		}
		return finalPronunciation;
	}
	/*public static String getPronuciation(String word)
	{
		List<String> pronunciation = new ArrayList<String>();
		char[] character = word.toCharArray();
		
		String subWord = "";
		if(!TamilTTSUtils.checkContains(TamilTTSConstants.பூஜ்யம்_அல்லாத_எண்கள், word))
		{
			for(int i = 0; i < word.length(); i++)
			{
				pronunciation.add(wordMap1.get("0"));
			}
			
			
		}
		else
		{
			for(int i = character.length - 1; i >= 0; i--)
			{
				
				
				if(character.length >= 2 && i == character.length - 1 && character[character.length - 2] == '1')
				{
					pronunciation.add( wordMap2.get(""+(character[character.length - 2] +""+character[character.length - 1])));
					i--;
					continue;
				}
				
				if(character[i] == '0')
				{
					continue;
				}
				
				if(i == character.length - 1 )
				{
					pronunciation.add( wordMap1.get(""+(character[i])));
					continue;
				}
				if( true || TamilTTSUtils.checkContains(TamilTTSConstants.பூஜ்யம்_அல்லாத_எண்கள், word.substring(i+1, character.length)))
				{
					if(character.length - i == 1)
					{
						pronunciation.add( wordMap1.get("_"+(character[i])));
						continue;
					}
					if(character.length - i == 2)
					{
						if(character[i+1] == '0'){
							pronunciation.add( wordMap2.get(""+(character[i])));
							continue;
						}
						pronunciation.add( wordMap2.get("_"+(character[i])));
						continue;
					}
					if(character.length - i == 3)
					{
						if(character[i+1] == '0' && character[i+2] == '0'){
							pronunciation.add( wordMap3.get(""+(character[i])));
							continue;
						}
						pronunciation.add( wordMap3.get("_"+(character[i])));
						continue;
					}
					if(character.length - i == 4 && character.length <= 4)
					{
						if(character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0'){
							pronunciation.add( wordMap4.get(""+(character[i])));
							continue;
						}
						pronunciation.add( wordMap4.get("_"+(character[i])));
						continue;
					}
					if(character.length - i == 5)
					{
						if(i >= 0 && character[i] == '1' && character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0'){
							pronunciation.add( wordMap5.get(""+(character[i])));
							continue;
						}
						if(i >= 0 && character[i] == '1' && character[i + 1] == '0')
						{
							pronunciation.add( wordMap5.get("_"+(character[i])));
							continue;
						}
						else if(i >= 0 && character[i + 1] == '0')
						{
							pronunciation.add( wordMap4.get("_1"));
							pronunciation.add( wordMap2.get(""+(character[i])));
							continue;
						}
						if(character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0'){
							pronunciation.add( wordMap4.get("1"));
						}
						else{
							pronunciation.add( wordMap4.get("_1"));
						}
						pronunciation.add( wordMap1.get((""+character[i+1])));
						pronunciation.add( wordMap2.get("_"+(character[i])));
						continue;
					}
					if(character.length - i == 6 && character.length <= 6)
					{
						if(i >= 0 && character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0' && character[i+5] == '0'){
							pronunciation.add( wordMap6.get("லச்சம்"));
						}
						else
						{
							pronunciation.add(wordMap6.get("லச்சத்து"));
						}
						
						if(character[i] != '1')
						{
							pronunciation.add(wordMap1.get(""+(character[i])));
							continue;
						}
						else
						{
							pronunciation.add(wordMap1.get("_"+(character[i])));
							continue;
						}
					}
					if(character.length - i == 7)
					{
						
						if(i >= 0 && character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0' && character[i+5] == '0'  && character[i+6] == '0'){
							pronunciation.add( wordMap6.get("லச்சம்"));
						}
						else
						{
							pronunciation.add(wordMap6.get("லச்சத்து"));
						}
											
						if(character[i+1] != '0')
						{
							pronunciation.add(wordMap1.get(""+(character[i+1])));
							pronunciation.add(wordMap2.get("_"+(character[i])));
							continue;
						}
						else
						{
							pronunciation.add(wordMap2.get(""+(character[i])));
							continue;
						}
					}
					if(character.length - i == 8 && character.length <= 8)
					{
						if(i >= 0 && character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0' && character[i+5] == '0'  && character[i+6] == '0' 
								&& character[i+7] == '0'){
							pronunciation.add( wordMap8.get("கோடி"));
						}
						else
						{
							pronunciation.add(wordMap8.get("கோடியே"));
						}
						
						if(character[i] != '1')
						{
							pronunciation.add(wordMap1.get(""+(character[i])));
							continue;
						}
						else
						{
							pronunciation.add(wordMap1.get("_"+(character[i])));
							continue;
						}
						
						
					}
					
					if(character.length - i == 9 && character.length <= 9)
					{
						if(i >= 0 && character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0' && character[i+5] == '0'  
								&& character[i+6] == '0' && character[i+7] == '0'  && character[i+7] == '0' && character[i+8] == '0'){
							pronunciation.add( wordMap8.get("கோடி"));
						}
						else
						{
							pronunciation.add(wordMap8.get("கோடியே"));
						}
						
						if(character[i+1] != '0')
						pronunciation.add(wordMap1.get(""+(character[i+1])));
						pronunciation.add(wordMap2.get(""+(character[i])));
						continue;
					}
					
					if(character.length - i == 10 && character.length <= 10)
					{
						if(i >= 0 && character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0' && character[i+5] == '0'  
								&& character[i+6] == '0' && character[i+7] == '0'  && character[i+7] == '0' && character[i+8] == '0' && character[i+9] == '0'){
							pronunciation.add( wordMap8.get("கோடி"));
						}
						else
						{
							pronunciation.add(wordMap8.get("கோடியே"));
						}
						
						if(character[i+2] != '0')
						pronunciation.add(wordMap1.get(""+(character[i+2])));
						if(character[i+1] != '0')
						pronunciation.add(wordMap2.get("_"+(character[i+1])));
						if(character[i+1] != '0' && character[i+2] != '0')
							pronunciation.add(wordMap3.get("_"+(character[i])));
						else
							pronunciation.add(wordMap3.get(""+(character[i])));
						continue;
					}
					
					if(character.length - i == 11 && character.length <= 11)
					{
						
						if(i >= 0 && character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0' && character[i+5] == '0'  
								&& character[i+6] == '0' && character[i+7] == '0'  && character[i+7] == '0' && character[i+8] == '0' && character[i+9] == '0' &&  character[i+10] == '0'){
							pronunciation.add( wordMap8.get("கோடி"));
						}
						else
						{
							pronunciation.add(wordMap8.get("கோடியே"));
						}
						
						if(character[i+1] != '0')
						pronunciation.add(wordMap1.get(""+(character[i+3])));
						if(character[i+2] != '0')
						pronunciation.add(wordMap2.get("_"+(character[i+2])));
						if(character[i+1] != '0')
						pronunciation.add(wordMap3.get("_"+(character[i+1])));
						if(character[i+1] != '0'){
							pronunciation.add(wordMap4.get("_"+(character[i])));
						}
						else{
							pronunciation.add(wordMap4.get(""+(character[i])));
						}
							
	
						continue;
					}
					
					if(character.length - i == 12 && character.length <= 12)
					{
						if(i >= 0 && character[i+1] == '0' && character[i+2] == '0' && character[i+3] == '0' && character[i+4] == '0' && character[i+5] == '0'  
								&& character[i+6] == '0' && character[i+7] == '0'  && character[i+7] == '0' && character[i+8] == '0' && character[i+9] == '0' &&  character[i+10] == '0' && character[i+11] == '0'){
							pronunciation.add( wordMap8.get("கோடி"));
						}
						else
						{
							pronunciation.add(wordMap8.get("கோடியே"));
						}
						
						if(character[i+1] != '0')
						pronunciation.add(wordMap1.get(""+(character[i+4])));
						if(character[i+3] != '0')
						pronunciation.add(wordMap2.get("_"+(character[i+3])));
						if(character[i+2] != '0')
						pronunciation.add(wordMap3.get("_"+(character[i+2])));
						if(character[i+2] != '0'){
							pronunciation.add(wordMap4.get("_1"));
						}
						else{
							pronunciation.add(wordMap4.get("1"));
						}
						if(character[i+2] != '0'){
							pronunciation.add(wordMap1.get(""+(character[i+1])));
						}
						if(character[i+1] != '0'){
							pronunciation.add(wordMap2.get("_"+(character[i])));
						}
						else{
							pronunciation.add(wordMap2.get(""+(character[i])));
						}
						continue;
					}
				}
				*//*else
				{
					if(character.length - i == 1)
					{
						pronunciation.add( wordMap1.get(""+(character[i])));
						continue;
					}
					if(character.length - i == 2)
					{
						pronunciation.add( wordMap2.get(""+(character[i])));
						continue;
					}
					if(character.length - i == 3)
					{
						pronunciation.add( wordMap3.get(""+(character[i])));
						continue;
					}
					if(character.length - i == 4)
					{
						pronunciation.add( wordMap4.get(""+(character[i])));
						continue;
					}
					if(character.length - i == 5)
					{
						pronunciation.add( wordMap5.get(""+(character[i])));
						continue;
					}
					if(character.length - i == 6)
					{
						if(character[i] == '1'){
							pronunciation.add( wordMap1.get(""+(character[i]))+" "+wordMap6.get("லச்சத்து"));
						}
						else{
							pronunciation.add( wordMap1.get(""+(character[i]))+" "+wordMap6.get("லச்சம்"));
						}
						continue;
					}
					if(character.length - i == 7)
					{
						pronunciation.add( wordMap2.get(""+(character[i]))+" "+wordMap6.get("லச்சம்"));
						continue;
					}
					if(character.length - i == 8)
					{
						if(character[i] == '1'){
							pronunciation.add( wordMap1.get("_"+(character[i]))+" "+wordMap8.get("கோடியே"));
						}
						else{
							pronunciation.add( wordMap1.get(""+(character[i]))+" "+wordMap8.get("கோடி"));
						}
						continue;
					}
					
					if(character.length - i == 9)
					{
						pronunciation.add( wordMap2.get(""+(character[i]))+" "+wordMap8.get("கோடி"));
						continue;
					}
					
				}*//*
				
				//System.out.println(pronunciation);
			}
		}
		String finalPronunciation ="";
		for(int j =  pronunciation.size() - 1 ; j >= 0 ; j--)
		{
			finalPronunciation += pronunciation.get(j)+"  ";
		}
		return finalPronunciation;
	}*/
	
	public static boolean checkOnlyNumbers(String word)
	{
		try
		{
			Integer.parseInt(word);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}

	
	
}
