package com.tamil.thamizhpesi.audio.processor.utils;

import java.io.DataInputStream;
import java.io.IOException;

import java.io.File;
import java.io.OutputStream;

import com.tamil.thamizhpesi.audio.processor.SoundFileFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;


/**
 * Abstract File Writer class.
 *
 * @author Jan Borgersen
 */
abstract class TamilTTSFileWriter extends SoundFileWriter {


    // buffer size for write
    protected static final int bufferSize = 16384;

    // buffer size for temporary input streams
    protected static final int bisBufferSize = 4096;


    final SoundFileFormat.Type types[];


    /**
     * Constructs a new SunParser object.
     */
    TamilTTSFileWriter(SoundFileFormat.Type types[]) {
        this.types = types;
    }



    // METHODS TO IMPLEMENT SoundFileWriter

    // new, 10.27.99

    public SoundFileFormat.Type[] getAudioFileTypes(){

        SoundFileFormat.Type[] localArray = new SoundFileFormat.Type[types.length];
        System.arraycopy(types, 0, localArray, 0, types.length);
        return localArray;
    }


    public abstract SoundFileFormat.Type[] getAudioFileTypes(SoundInputStream stream);

    public abstract int write(SoundInputStream stream, SoundFileFormat.Type fileType, OutputStream out) throws IOException;

    public abstract int write(SoundInputStream stream, SoundFileFormat.Type fileType, File out) throws IOException;


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

}
