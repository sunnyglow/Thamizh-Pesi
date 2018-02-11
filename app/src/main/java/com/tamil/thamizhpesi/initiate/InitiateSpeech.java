package com.tamil.thamizhpesi.initiate;

import java.util.ArrayList;
import java.util.List;


import com.tamil.thamizhpesi.builder.InitializeObjects;
import com.tamil.thamizhpesi.cluster.ClusterSearch;
import com.tamil.thamizhpesi.constants.TamilTTSConstants;
import com.tamil.thamizhpesi.dsp.filter.WavFilterFisher;
import com.tamil.thamizhpesi.model.ResultModel;
import com.tamil.thamizhpesi.utils.NumberSynthesizer;
import com.tamil.thamizhpesi.utils.SynthesizeSpeech;
import com.tamil.thamizhpesi.utils.TamilTTSUtils;



public class InitiateSpeech 
{
	static List<ResultModel> rlist = new ArrayList<ResultModel>();
	static ResultModel rmodel = null;
	//static Map<String, String> map = new HashMap<String, String>();
	/*public static void main(String args[]) throws Exception
	{
		
		//InitializeObjects.initializeTimeDataObjects();
		BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\Sureshkumar\\Documents\\work\\thamizhpesi.txt")));
		String currentLine = "";
		
		*//*while((currentLine = reader.readLine()) != null)
		{
			try
			{
				speakTamizh(currentLine);
			}
			catch(Exception exp)
			{
				System.out.println(currentLine);
				exp.printStackTrace();
			}
		}*//*
		
		reader.close();
		initiateSpeech("இது தவறு என நீங்கள் கருதினால் இங்கே தெரிவியுங்கள் அல்லது எனக்கு மின்னஞ்சல் செய்யுங்கள்");
		if(rlist.size() > 0)
		{
			SynthesizeSpeech.speak(rlist);
			WavFilterFisher.lowPassFilter();
			//SynthesizeSpeech.adjustAudio();
			//SynthesizeSpeech.normalizeAudio();
			//SynthesizeSpeech.mergeFiles();
		}
		
	}
	*/
	
	public void initiateSpeech(String paragraph, String audioFileName)
	{
		
		try
		{
			//InitializeObjects.initializeTimeDataObjects();
			String currentLine = "";
			String[] wordArray = paragraph.split("\n");
			rlist = new ArrayList<ResultModel>();
			for(int i = 0; i < wordArray.length; i++)
			{
				currentLine = wordArray[i];
				if(currentLine.trim().equals("")){
					continue;
				}
				try
				{
					if(TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், currentLine)){
						currentLine = NumberSynthesizer.checkForNumber(currentLine);
					}
					synthesizeSpeech(currentLine);
				}
				catch(Exception exp)
				{
					System.out.println(currentLine);
					exp.printStackTrace();
				}
			}
			
			if(rlist.size() > 0)
			{
				SynthesizeSpeech.speak(rlist, "tmp.wav");
				WavFilterFisher.lowPassFilter(audioFileName);
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		
	}
	
	public static void synthesizeSpeech(String line) throws Exception
	{
		line = line.trim();
		String[] words = line.split(" ");
		String word = null;
		List<String> letterList = null;
		boolean periodFlag = false;
		for(int ix =0; ix < words.length; ix++)
		{
			periodFlag = false;
			//System.out.println(words[ix]);

			word = words[ix];
			word = word.trim();
			if(!(word.trim().length() > 0))
			{
				continue;
			}
			if(word.contains(".") || word.contains("?")  || word.contains("!"))
			{
				periodFlag = true;				
			}
			
		    if(TamilTTSUtils.checkContains(TamilTTSConstants.ENGLISH_LETTERS, word) || TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், word))
		    {
			   continue;
		    }

			word = word.replace(".", "");
			word = word.replace(",", "");
			word = word.replace("?", "");
			word = word.replace("!", "");
			word = word.replace("\'", "");
			word = word.replace("\"", "");
			word = word.replace(".", "");
			word = word.replace("”", "");
			word = word.replace("“", "");
			word = word.replace("*", "");
			
			word = word.replace("‘", "");
			word = word.replace("’", "");
			word = word.replace("-", "");
			word = word.replace("\\", "");
			word = word.replace("/", "");
			word = word.replace(":", "");
			word = word.replace("…", "");
			word = word.replace("(", "");
			word = word.replace(")", "");
			word = word.replace(";", "");
			word = word.replace("–", "");
			
			if(word.equalsIgnoreCase(""))
			{
				continue;
			}
			letterList = TamilTTSUtils.getLetterList(word);
			letterList = applyGrammer(letterList);
			/*int bodyCounter = 0;
			for(int x = 0; x < letterList.size(); x++)
			{
				if(letterList.get(x).contains("்"))
				{
					//System.out.println("working");
					bodyCounter++;
				}
			}*/
			
			/*if(bodyCounter >= (letterList.size() / 2))
			{
				//System.out.println("Found: "+word);
				letterList = TamilTTSUtils.getNormalizedLetterList(letterList);
			}
			else
			{
				//System.out.println("Skipping: "+ word);
			}*/
			int verifyIndex = 0;
			List<String> bufferList = null;
			
			
			for(int i = letterList.size() ; i >= verifyIndex; i--)
			{
				String wordBuffer = TamilTTSUtils.getString(verifyIndex, i, letterList);
				bufferList = TamilTTSUtils.getLetterList(wordBuffer);
				
				
				//System.out.println(wordBuffer);
				
				/*if(bufferList.size() == 1 && !TamilTTSUtils.checkContains(TamilTTSConstants.BODY_WORD, wordBuffer) &&  !TamilTTSUtils.checkContains(TamilTTSConstants.NORTH_SOUND, wordBuffer)
						&& !TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், wordBuffer))
				{
					System.out.println("Size one");
					rmodel = new ResultModel();
					rmodel.setFileName(wordBuffer.trim());
					rmodel.setCompleteSound(true);
					rlist.add(rmodel);
					rmodel = null;
					
					if(i == letterList.size())
					{
						break; //Full word found
					}
					
					else
					{
						verifyIndex = i;
						i = letterList.size() + 1;
					}
					
				}
				else */
				{
					/*if(bufferList.size() <= 3 && !TamilTTSUtils.checkContains(TamilTTSConstants.BODY_WORD, wordBuffer))
					{
						System.out.println("**********************: "+ wordBuffer);
						rmodel = ClusterSearch.searchCluster(wordBuffer, InitializeObjects.twoThreeLetterModelList, false);
						//System.out.println("****************************************************************Successfully found...!");
					}*/
					
					if(rmodel == null)
					{
						/*if(bufferList.size() == 1  && !TamilTTSUtils.checkContains(TamilTTSConstants.NORTH_SOUND, wordBuffer))
						{
							rmodel = ClusterSearch.searchCluster(wordBuffer, InitializeObjects.twoThreeLetterModelList, false);
							System.out.println(wordBuffer);
						}
						else*/
						{
							

						}
						
						/*if(bufferList.size() <= 2)
						{
							rmodel = TrieFrontClusterSearch.searchCluster(wordBuffer, InitializeObjects.clusterModelList, false);
							if(rmodel != null)
							{
								System.out.println("	 Front Found: "+ wordBuffer);
							}
						}*/
						if(rmodel == null)
						{
							rmodel = ClusterSearch.searchCluster(wordBuffer, InitializeObjects.clusterModelList, false);
							if(rmodel != null)
							{
								System.out.println("	Found: "+ wordBuffer);
							}
						}
					}
					
					/*if(rmodel == null)
					{
						
						if(bufferList.size() <= 2 && !TamilTTSUtils.checkContains(TamilTTSConstants.BODY_WORD, wordBuffer))
						{
							
							if(map.get(wordBuffer) == null)
							{
								//if(!TamilTTSUtils.checkContains(TamilTTSConstants.BODY_WORD, bufferList.get(0)))
								{
									//if(TamilTTSUtils.checkContains(TamilTTSConstants.NORTH_SOUND, wordBuffer))
									{
										map.put(wordBuffer, wordBuffer);
										//System.out.println(word);
										//System.out.println(wordBuffer);
									}
								}
								
							}
							
						}
						
					}
					*/
					if(rmodel != null)
					{
						//System.out.println("	Found: "+ wordBuffer);
						rlist.add(rmodel);
						rmodel = null;
						
						if(i == letterList.size())
						{
							break; //Full word found
						}
						
						else
						{
							verifyIndex = i;
							i = letterList.size() + 1;
						}
					}
					else if(i == verifyIndex)
					{
						verifyIndex++;
						if(verifyIndex >= letterList.size())
						{
							break;
						}
						i = letterList.size() + 1;
					}
					
				}
			}
			


			{
				rmodel = null;
				rmodel = new ResultModel();
				rmodel.setFileName("SPACE");
				//rmodel.setAudioPath(TamilTTSConstants.AUDIO_PATH);
				rlist.add(rmodel);
			}
			
			
			rmodel = null;
		}

		{
			rmodel = null;
			rmodel = new ResultModel();
			rmodel.setFileName("FULL_STOP");
			//rmodel.setAudioPath(TamilTTSConstants.AUDIO_PATH);
			rlist.add(rmodel);
			rmodel = null;
		}
		/*if(rlist.size() > 0)
		{
			SynthesizeSpeech.speak(rlist);
		}*/
		
		
	}
	
	public static List<String> applyGrammer(List<String> letterList)
	{
		List<String> grammerList = new ArrayList<String>();
		for(int i = 0; i < letterList.size(); i++)
		{
			String letter = letterList.get(i);
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("ட்") && letterList.get(i+1).contains("ட"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("ற்") && letterList.get(i+1).contains("ற"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("த்") && letterList.get(i+1).contains("த"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("ச்") && letterList.get(i+1).contains("ச"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("க்") && letterList.get(i+1).contains("க"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}

			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("ப்") && letterList.get(i+1).contains("ப"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			grammerList.add(letter);
		}
		
		return grammerList;
	}
	
	public static void play()
	{
		/*try
		{
			
			SoundInputStream ais = SoundSystem.getAudioInputStream(new File(TamilTTSConstants.OUTPUT_VOICE_PATH+TamilTTSConstants.FINAL_OUTPUT_VOICE_FILE_NAME));
			if (ais == null) {
				return;
			}
						
			SoundFormat aif = ais.getFormat();
			System.out.println("Sample Rate: "+aif.getSampleRate());
			
			Clip synthesizedVoice = SoundSystem.getClip();

			synthesizedVoice.open(ais);

			synthesizedVoice.start();
			

			while (!synthesizedVoice.isRunning())
				Thread.sleep(10);
			while (synthesizedVoice.isRunning())
				Thread.sleep(10);

			synthesizedVoice.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
	}
}
