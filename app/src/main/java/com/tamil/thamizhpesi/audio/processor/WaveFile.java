package com.tamil.thamizhpesi.audio.processor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WaveFile {
    public final int NOT_SPECIFIED =  -1;
    public final int INT_SIZE = 4;

    private int sampleSize = NOT_SPECIFIED;
    private long framesCount = NOT_SPECIFIED;
    private int sampleRate = NOT_SPECIFIED;
    private int channelsNum;
    private byte[] data;      // wav bytes
    private SoundInputStream ais;
    private SoundFormat af;
    private boolean canPlay;

    public WaveFile(SoundInputStream audioStream) throws Exception {

        ais = audioStream;

        af = ais.getFormat();

        framesCount = ais.getFrameLength();

        sampleRate = (int) af.getSampleRate();

        sampleSize = af.getSampleSizeInBits() / 8;

        channelsNum = af.getChannels();

        long dataLength = framesCount * af.getSampleSizeInBits() * af.getChannels() / 8;

        data = new byte[(int) dataLength];
        ais.read(data);


       
    }

    public boolean isCanPlay() {
        return canPlay;
    }


    public SoundFormat getAudioFormat() {
        return af;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public double getDurationTime() {
        return getFramesCount() / getAudioFormat().getFrameRate();
    }

    public long getFramesCount() {
        return framesCount;
    }


    /**
     * Returns sample (amplitude value). Note that in case of stereo samples
     * go one after another. I.e. 0 - first sample of left channel, 1 - first
     * sample of the right channel, 2 - second sample of the left channel, 3 -
     * second sample of the rigth channel, etc.
     */
    public double getSampleInt(int sampleNumber) {
        


        byte[] sampleBytes = new byte[4]; //4byte = int

        for (int i = 0; i < sampleSize; i++) {
            sampleBytes[i] = data[sampleNumber * sampleSize * channelsNum + i];
        }

        double sample = ByteBuffer.wrap(sampleBytes)
                .order(ByteOrder.LITTLE_ENDIAN).getInt();
        return sample;
    }

    public int getSampleRate() {
        return sampleRate;
    }


}