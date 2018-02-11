package com.tamil.thamizhpesi.cluster;

import java.util.List;

import com.tamil.thamizhpesi.constants.TamilTTSConstants;
import com.tamil.thamizhpesi.model.ResultModel;
import com.tamil.thamizhpesi.model.TimeDataModel;
import com.tamil.thamizhpesi.utils.TamilTTSUtils;

public class ClusterSearch 
{
	public static ResultModel searchCluster(String word, List<TimeDataModel> modelList, boolean longSearch)
	{
		TimeDataModel model = null;
		ResultModel resultModel = null;
		List<String> letterList = TamilTTSUtils.getLetterList(word);
		boolean found = true;
		int longer = 0;
		outer:
		for(int i=0; i < modelList.size(); i++)
		{
			model = modelList.get(i);
			if(model.getSentence().contains(word))
			{
				
				inner:
				for(int j = 0; j < model.getLetterList().size(); j++)
				{
					if(letterList.get(0).equals(model.getLetterList().get(j)))
					{
						if(!((letterList.size() - 1 + j) <= (model.getLetterList().size() -1)))
						{
							break;
						}
						for(int k = 0 ; k < letterList.size(); k++)
						{
							if(letterList.get(k).equals(model.getLetterList().get(j + k)))
							{
								found = true;
								continue;
							}
							else
							{
								found = false;
								break;
							}
						}
						
						if(found)
						{
							
							int startTime = model.getStartTime().get(j);
							int endTime = model.getEndTime().get(j + letterList.size() - 1);
							//if(letterList.size() == 1)
							{
								//System.out.println("Finding one letter**************************************************************************************");
								int duration = calculateDuration(word);
								//if(longSearch && ((endTime - startTime) > duration && (endTime - startTime) < (duration + 100)))
								{
									resultModel = new ResultModel();
									resultModel.setStartTime(startTime );
									resultModel.setEndTime(endTime );
									resultModel.setFileName(model.getFileName());
									resultModel.setAudioPath(model.getAudioPath());
									resultModel.setWord(word);
									break outer;
								}
								/*else
								{
									continue;
								}*/
								
							}
							/*if(longSearch)
							{
								if(longer < (endTime - startTime))
								{
									longer = endTime - startTime;
									
									resultModel = new ResultModel();
									resultModel.setStartTime(model.getStartTime().get(j) - 10);
									resultModel.setEndTime(model.getEndTime().get(j + letterList.size() - 1));
									resultModel.setFileName(model.getFileName());
									resultModel.setAudioPath(model.getAudioPath());
									resultModel.setWord(word);
								}
								else
								{
									break inner;
								}
							}
							else
							{
								//System.out.println(word);
								//System.out.println("	FileName: "+model.getFileName());
								//System.out.println(model.getSentence());
								
								//System.out.println("	Start Time: "+startTime);
								//System.out.println("	End Time: "+endTime);
								//System.out.println(model.getStartTime().get(j));
								//System.out.println(model.getEndTime().get(j + letterList.size() - 1));
								resultModel = new ResultModel();
								resultModel.setStartTime(startTime);
								resultModel.setEndTime(endTime);
								resultModel.setFileName(model.getFileName());
								resultModel.setAudioPath(model.getAudioPath());
								resultModel.setWord(word);
								break outer;
							}*/
						}
					}
				}
				/*String sentence = TamilTTSUtils.getString(0, model.getLetterList().size(), model.getLetterList());
				resultModel = new ResultModel();
				int startTime = model.getStartTime().get(sentence.indexOf(word) - letterList.size());
				int endTime = model.getEndTime().get(sentence.indexOf(word) - 1);
				resultModel.setStartTime(startTime);
				resultModel.setEndTime(endTime);
				resultModel.setFileName(model.getFileName());
				
				System.out.println(sentence.indexOf(word));
				System.out.println(sentence.indexOf(word) - letterList.size());
				System.out.println(model.getStartTime().get(sentence.indexOf(word) - letterList.size()));
				System.out.println(model.getEndTime().get(sentence.indexOf(word) - 1));*/
			}
		}
		
		return resultModel;
	}
	
	
	public static int calculateDuration(String word)
	   {
			word = word.replace("_", "");
			List<String> letterList = TamilTTSUtils.getLetterList(word);
			long duration = 0;
			for(int i = 0; letterList != null && i < letterList.size(); i++)
			{
				if(TamilTTSUtils.findPresence(TamilTTSConstants.BODY_WORD, letterList.get(i)))
				{
					duration += TamilTTSConstants.BODY_SOUND;
					continue;
				}
				if (TamilTTSUtils.findPresence(TamilTTSConstants.O_word, letterList.get(i))) 
				{
					duration += TamilTTSConstants.O_sound;

					continue;
				}
				if (TamilTTSUtils.findPresence(TamilTTSConstants.AI_WORD, letterList.get(i))) 
				{
					duration += TamilTTSConstants.ai_sound;

					continue;
				}
				if (TamilTTSUtils.findPresence(TamilTTSConstants.நெடில், letterList.get(i))) 
				{
					duration += TamilTTSConstants.LONG_SOUND;

					continue;
				} 
				else if(TamilTTSUtils.findPresence(TamilTTSConstants.special, letterList.get(i)))
				{
					duration += TamilTTSConstants.special_duration;

					continue;
				}
				else 
				{
					duration += TamilTTSConstants.SHORT_SOUND;

					continue;
				}
			}
			//System.out.println("Word: "+ word);
			//System.out.println("Duration: "+ duration);
			return (int) duration;
		}
	
}
