// Copyright 2013 Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland
// www.source-code.biz, www.inventec.ch/chdh
//
// This module is multi-licensed and may be used under the terms
// of any of the following licenses:
//
//  EPL, Eclipse Public License, V1.0 or later, http://www.eclipse.org/legal
//  LGPL, GNU Lesser General Public License, V2.1 or later, http://www.gnu.org/licenses/lgpl.html
//
// Please contact the author if you need another license.
// This module is provided "as is", without warranties of any kind.

package com.tamil.thamizhpesi.dsp.filter;

import com.tamil.thamizhpesi.audio.processor.SoundFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;

/**
* An IIR filter for the Java Sound API.
*
* <p>
* This class provides an {@link SoundInputStream} for
* filtering a sound stream. It uses the Fisher method to design the IIR filter.
*/
public class IirFilterAudioInputStreamFisher {

// Dummy constructor to suppress Javadoc.
private IirFilterAudioInputStreamFisher() {}

/**
* Returns an SoundInputStream that supplies the filtered audio signal.
*
* @param in
*    The input SoundInputStream.
* @param filterPassType
*    The filter pass type (lowpass, highpass, bandpass or bandstop).
* @param filterCharacteristicsType
*    The filter characteristics type.
*    The following filters are implemented: Butterworth, Chebyshev (type 1), Bessel.
* @param filterOrder
*    The filter order.
* @param ripple
*    Passband ripple in dB. Must be negative. Only used for Chebyshev filter, ignored for other filters.
* @param fcf1
*    The filter cutoff frequency in Hz for lowpass / highpass, lower cutoff frequency in Hz for bandpass / bandstop.
* @param fcf2
*    The upper cutoff frequency in Hz for bandpass / bandstop, ignored for lowpass / highpass.
*/
public static SoundInputStream getAudioInputStream (SoundInputStream in, FilterPassType filterPassType,
                                                    FilterCharacteristicsType filterCharacteristicsType, int filterOrder, double ripple, double fcf1, double fcf2) {
   SoundFormat format = in.getFormat();
   double sampleRate = format.getSampleRate();
   double fcf1Rel = fcf1 / sampleRate;
   double fcf2Rel = fcf2 / sampleRate;
   IirFilterCoefficients coeffs = IirFilterDesignFisher.design(filterPassType, filterCharacteristicsType, filterOrder, ripple, fcf1Rel, fcf2Rel);
   return IirFilterAudioInputStream.getAudioInputStream(in, coeffs); }

}
