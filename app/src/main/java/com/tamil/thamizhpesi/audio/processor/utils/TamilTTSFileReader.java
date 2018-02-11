package com.tamil.thamizhpesi.audio.processor.utils;



import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.net.URL;

import com.tamil.thamizhpesi.audio.processor.SoundFileFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;


/**
 * Abstract File Reader class.
 *
 * @author Jan Borgersen
 */
abstract class TamilTTSFileReader extends SoundFileReader {

    // buffer size for temporary input streams
    protected static final int bisBufferSize = 4096;

    /**
     * Constructs a new TamilTTSFileReader object.
     */
    public TamilTTSFileReader() {
    }



    abstract public SoundFileFormat getAudioFileFormat(InputStream stream) throws Exception, IOException;



    abstract public SoundFileFormat getAudioFileFormat(URL url) throws Exception, IOException;


    abstract public SoundFileFormat getAudioFileFormat(File file) throws Exception, IOException;



    abstract public SoundInputStream getAudioInputStream(InputStream stream) throws Exception, IOException;



    abstract public SoundInputStream getAudioInputStream(URL url) throws Exception, IOException;



    abstract public SoundInputStream getAudioInputStream(File file) throws Exception, IOException;


    // HELPER METHODS



    protected int rllong(DataInputStream dis) throws IOException {

        int b1, b2, b3, b4 ;
        int i = 0;

        i = dis.readInt();

        b1 = ( i & 0xFF ) << 24 ;
        b2 = ( i & 0xFF00 ) << 8;
        b3 = ( i & 0xFF0000 ) >> 8;
        b4 = ( i & 0xFF000000 ) >>> 24;

        i = ( b1 | b2 | b3 | b4 );

        return i;
    }


    protected int big2little(int i) {

        int b1, b2, b3, b4 ;

        b1 = ( i & 0xFF ) << 24 ;
        b2 = ( i & 0xFF00 ) << 8;
        b3 = ( i & 0xFF0000 ) >> 8;
        b4 = ( i & 0xFF000000 ) >>> 24;

        i = ( b1 | b2 | b3 | b4 );

        return i;
    }


    protected short rlshort(DataInputStream dis)  throws IOException {

        short s=0;
        short high, low;

        s = dis.readShort();

        high = (short)(( s & 0xFF ) << 8) ;
        low = (short)(( s & 0xFF00 ) >>> 8);

        s = (short)( high | low );

        return s;
    }


    protected short big2littleShort(short i) {

        short high, low;

        high = (short)(( i & 0xFF ) << 8) ;
        low = (short)(( i & 0xFF00 ) >>> 8);

        i = (short)( high | low );

        return i;
    }


    /** Calculates the frame size for PCM frames.
     * Note that this method is appropriate for non-packed samples.
     * For instance, 12 bit, 2 channels will return 4 bytes, not 3.
     * @param sampleSizeInBits the size of a single sample in bits
     * @param channels the number of channels
     * @return the size of a PCM frame in bytes.
     */
    protected static int calculatePCMFrameSize(int sampleSizeInBits,
                                               int channels) {
        return ((sampleSizeInBits + 7) / 8) * channels;
    }
}
