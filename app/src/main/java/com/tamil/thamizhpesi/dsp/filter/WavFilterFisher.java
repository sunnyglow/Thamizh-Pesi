package com.tamil.thamizhpesi.dsp.filter;

import java.io.File;
import java.io.FileOutputStream;

import com.blogspot.vayalumvazhvum.activity.SplashScreenActivity;
import com.tamil.thamizhpesi.audio.processor.SoundFileFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;
import com.tamil.thamizhpesi.audio.processor.SoundSystem;
import com.tamil.thamizhpesi.constants.TamilTTSConstants;

public class WavFilterFisher {

	public static void lowPassFilter(String output) throws Exception
	{
		String inputFileName = SplashScreenActivity.GENERATED_VOICE_PATH + "/tmp.wav";
		FilterPassType filterPassType = FilterPassType.valueOf(TamilTTSConstants.FILTER_TYPE);
		FilterCharacteristicsType filterCharacteristicsType = FilterCharacteristicsType.valueOf("butterworth");
		int filterOrder = Integer.valueOf("4");
		double ripple = Double.valueOf("-0.5");
		double fcf1 = Double.valueOf("2000");
		double fcf2 = Double.valueOf("3000");
		String outputFileName = SplashScreenActivity.GENERATED_VOICE_PATH +"/"+output;

		filterWavFile(inputFileName, filterPassType, filterCharacteristicsType, filterOrder, ripple, fcf1, fcf2,
				outputFileName);
	}

	private static void filterWavFile(String inputFileName, FilterPassType filterPassType,
			FilterCharacteristicsType filterCharacteristicsType, int filterOrder, double ripple, double fcf1,
			double fcf2, String outputFileName) throws Exception 
	{
		SoundInputStream inputStream = SoundSystem.getAudioInputStream(new File(inputFileName));
		SoundInputStream filterStream = IirFilterAudioInputStreamFisher.getAudioInputStream(inputStream, filterPassType,
				filterCharacteristicsType, filterOrder, ripple, fcf1, fcf2);
		FileOutputStream fileOutpu = new FileOutputStream(new File(outputFileName), false);
		SoundSystem.write(filterStream, SoundFileFormat.Type.WAVE, fileOutpu);
	}
}
