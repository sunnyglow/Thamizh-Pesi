package com.tamil.thamizhpesi.audio.processor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SoundFormat {

    // INSTANCE VARIABLES


    /**
     * The audio encoding technique used by this format.
     */
    protected Encoding encoding;

    /**
     * The number of samples played or recorded per second, for sounds that have this format.
     */
    protected float sampleRate;

    /**
     * The number of bits in each sample of a sound that has this format.
     */
    protected int sampleSizeInBits;

    /**
     * The number of audio channels in this format (1 for mono, 2 for stereo).
     */
    protected int channels;

    /**
     * The number of bytes in each frame of a sound that has this format.
     */
    protected int frameSize;

    /**
     * The number of frames played or recorded per second, for sounds that have this format.
     */
    protected float frameRate;

    /**
     * Indicates whether the audio data is stored in big-endian or little-endian order.
     */
    protected boolean bigEndian;


    /** The set of properties */
    private HashMap<String, Object> properties;


    /**
     * Constructs an <code>SoundFormat</code> with the given parameters.
     * The encoding specifies the convention used to represent the data.
     * The other parameters are further explained in the {@link SoundFormat
     * class description}.
     * @param encoding                  the audio encoding technique
     * @param sampleRate                the number of samples per second
     * @param sampleSizeInBits  the number of bits in each sample
     * @param channels                  the number of channels (1 for mono, 2 for stereo, and so on)
     * @param frameSize                 the number of bytes in each frame
     * @param frameRate                 the number of frames per second
     * @param bigEndian                 indicates whether the data for a single sample
     *                                                  is stored in big-endian byte order (<code>false</code>
     *                                                  means little-endian)
     */
    public SoundFormat(Encoding encoding, float sampleRate, int sampleSizeInBits,
                       int channels, int frameSize, float frameRate, boolean bigEndian) {

        this.encoding = encoding;
        this.sampleRate = sampleRate;
        this.sampleSizeInBits = sampleSizeInBits;
        this.channels = channels;
        this.frameSize = frameSize;
        this.frameRate = frameRate;
        this.bigEndian = bigEndian;
        this.properties = null;
    }


    /**
     * Constructs an <code>SoundFormat</code> with the given parameters.
     * The encoding specifies the convention used to represent the data.
     * The other parameters are further explained in the {@link SoundFormat
     * class description}.
     * @param encoding         the audio encoding technique
     * @param sampleRate       the number of samples per second
     * @param sampleSizeInBits the number of bits in each sample
     * @param channels         the number of channels (1 for mono, 2 for
     *                         stereo, and so on)
     * @param frameSize        the number of bytes in each frame
     * @param frameRate        the number of frames per second
     * @param bigEndian        indicates whether the data for a single sample
     *                         is stored in big-endian byte order
     *                         (<code>false</code> means little-endian)
     * @param properties       a <code>Map&lt;String,Object&gt;</code> object
     *                         containing format properties
     *
     * @since 1.5
     */
    public SoundFormat(Encoding encoding, float sampleRate,
                       int sampleSizeInBits, int channels,
                       int frameSize, float frameRate,
                       boolean bigEndian, Map<String, Object> properties) {
        this(encoding, sampleRate, sampleSizeInBits, channels,
                frameSize, frameRate, bigEndian);
        this.properties = new HashMap<String, Object>(properties);
    }


    /**
     * Constructs an <code>SoundFormat</code> with a linear PCM encoding and
     * the given parameters.  The frame size is set to the number of bytes
     * required to contain one sample from each channel, and the frame rate
     * is set to the sample rate.
     *
     * @param sampleRate                the number of samples per second
     * @param sampleSizeInBits  the number of bits in each sample
     * @param channels                  the number of channels (1 for mono, 2 for stereo, and so on)
     * @param signed                    indicates whether the data is signed or unsigned
     * @param bigEndian                 indicates whether the data for a single sample
     *                                                  is stored in big-endian byte order (<code>false</code>
     *                                                  means little-endian)
     */
    public SoundFormat(float sampleRate, int sampleSizeInBits,
                       int channels, boolean signed, boolean bigEndian) {

        this((signed == true ? Encoding.PCM_SIGNED : Encoding.PCM_UNSIGNED),
                sampleRate,
                sampleSizeInBits,
                channels,
                (channels == -1 || sampleSizeInBits == -1)?
                        -1:
                        ((sampleSizeInBits + 7) / 8) * channels,
                sampleRate,
                bigEndian);
    }

    /**
     * Obtains the type of encoding for sounds in this format.
     *
     * @return the encoding type
     * @see Encoding#PCM_SIGNED
     * @see Encoding#PCM_UNSIGNED
     * @see Encoding#ULAW
     * @see Encoding#ALAW
     */
    public Encoding getEncoding() {

        return encoding;
    }

    public float getSampleRate() {

        return sampleRate;
    }


    public int getSampleSizeInBits() {

        return sampleSizeInBits;
    }


    public int getChannels() {

        return channels;
    }


    public int getFrameSize() {

        return frameSize;
    }


    public float getFrameRate() {

        return frameRate;
    }


    /**
     * Indicates whether the audio data is stored in big-endian or little-endian
     * byte order.  If the sample size is not more than one byte, the return value is
     * irrelevant.
     * @return <code>true</code> if the data is stored in big-endian byte order,
     * <code>false</code> if little-endian
     */
    public boolean isBigEndian() {

        return bigEndian;
    }


    public Map<String,Object> properties() {
        Map<String,Object> ret;
        if (properties == null) {
            ret = new HashMap<String,Object>(0);
        } else {
            ret = (Map<String,Object>) (properties.clone());
        }
        return (Map<String,Object>) Collections.unmodifiableMap(ret);
    }

    public Object getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }


    /**
     * Indicates whether this format matches the one specified.
     * To match, two formats must have the same encoding,
     * and consistent values of the number of channels, sample rate, sample size,
     * frame rate, and frame size.
     * The values of the property are consistent if they are equal
     * or the specified format has the property value
     * {@code -1}.
     * The byte order (big-endian or little-endian) must be the same
     * if the sample size is greater than one byte.
     *
     * @param format format to test for match
     * @return {@code true} if this format matches the one specified,
     *         {@code false} otherwise.
     */
    public boolean matches(SoundFormat format) {
        if (format.getEncoding().equals(getEncoding())
                && (format.getChannels() == -1
                || format.getChannels() == getChannels())
                && (format.getSampleRate() == (float)-1
                || format.getSampleRate() == getSampleRate())
                && (format.getSampleSizeInBits() == -1
                || format.getSampleSizeInBits() == getSampleSizeInBits())
                && (format.getFrameRate() == (float)-1
                || format.getFrameRate() == getFrameRate())
                && (format.getFrameSize() == -1
                || format.getFrameSize() == getFrameSize())
                && (getSampleSizeInBits() <= 8
                || format.isBigEndian() == isBigEndian())) {
            return true;
        }
        return false;
    }


    /**
     * Returns a string that describes the format, such as:
     * "PCM SIGNED 22050 Hz 16 bit mono big-endian".  The contents of the string
     * may vary between implementations of Java Sound.
     *
     * @return a string that describes the format parameters
     */
    public String toString() {
        String sEncoding = "";
        if (getEncoding() != null) {
            sEncoding = getEncoding().toString() + " ";
        }

        String sSampleRate;
        if (getSampleRate() == (float) -1) {
            sSampleRate = "unknown sample rate, ";
        } else {
            sSampleRate = "" + getSampleRate() + " Hz, ";
        }

        String sSampleSizeInBits;
        if (getSampleSizeInBits() == (float) -1) {
            sSampleSizeInBits = "unknown bits per sample, ";
        } else {
            sSampleSizeInBits = "" + getSampleSizeInBits() + " bit, ";
        }

        String sChannels;
        if (getChannels() == 1) {
            sChannels = "mono, ";
        } else
        if (getChannels() == 2) {
            sChannels = "stereo, ";
        } else {
            if (getChannels() == -1) {
                sChannels = " unknown number of channels, ";
            } else {
                sChannels = ""+getChannels()+" channels, ";
            }
        }

        String sFrameSize;
        if (getFrameSize() == (float) -1) {
            sFrameSize = "unknown frame size, ";
        } else {
            sFrameSize = "" + getFrameSize()+ " bytes/frame, ";
        }

        String sFrameRate = "";
        if (Math.abs(getSampleRate() - getFrameRate()) > 0.00001) {
            if (getFrameRate() == (float) -1) {
                sFrameRate = "unknown frame rate, ";
            } else {
                sFrameRate = getFrameRate() + " frames/second, ";
            }
        }

        String sEndian = "";
        if ((getEncoding().equals(Encoding.PCM_SIGNED)
                || getEncoding().equals(Encoding.PCM_UNSIGNED))
                && ((getSampleSizeInBits() > 8)
                || (getSampleSizeInBits() == -1))) {
            if (isBigEndian()) {
                sEndian = "big-endian";
            } else {
                sEndian = "little-endian";
            }
        }

        return sEncoding
                + sSampleRate
                + sSampleSizeInBits
                + sChannels
                + sFrameSize
                + sFrameRate
                + sEndian;

    }


    public static class Encoding {


        // ENCODING DEFINES

        /**
         * Specifies signed, linear PCM data.
         */
        public static final Encoding PCM_SIGNED = new Encoding("PCM_SIGNED");

        /**
         * Specifies unsigned, linear PCM data.
         */
        public static final Encoding PCM_UNSIGNED = new Encoding("PCM_UNSIGNED");

        /**
         * Specifies floating-point PCM data.
         *
         * @since 1.7
         */
        public static final Encoding PCM_FLOAT = new Encoding("PCM_FLOAT");

        /**
         * Specifies u-law encoded data.
         */
        public static final Encoding ULAW = new Encoding("ULAW");

        /**
         * Specifies a-law encoded data.
         */
        public static final Encoding ALAW = new Encoding("ALAW");


        private String name;


        public Encoding(String name) {
            this.name = name;
        }


        public final boolean equals(Object obj) {
            if (toString() == null) {
                return (obj != null) && (obj.toString() == null);
            }
            if (obj instanceof Encoding) {
                return toString().equals(obj.toString());
            }
            return false;
        }

        public final int hashCode() {
            if (toString() == null) {
                return 0;
            }
            return toString().hashCode();
        }

        public final String toString() {
            return name;
        }

    }
}
