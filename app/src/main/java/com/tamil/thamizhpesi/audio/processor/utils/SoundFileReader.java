package com.tamil.thamizhpesi.audio.processor.utils;


import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.tamil.thamizhpesi.audio.processor.SoundFileFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;


public abstract class SoundFileReader {


    public abstract SoundFileFormat getAudioFileFormat(InputStream stream) throws Exception, IOException;


    public abstract SoundFileFormat getAudioFileFormat(URL url) throws Exception, IOException;


    public abstract SoundFileFormat getAudioFileFormat(File file) throws Exception, IOException;


    public abstract SoundInputStream getAudioInputStream(InputStream stream) throws Exception, IOException;


    public abstract SoundInputStream getAudioInputStream(URL url) throws Exception, IOException;


    public abstract SoundInputStream getAudioInputStream(File file) throws Exception;
}
