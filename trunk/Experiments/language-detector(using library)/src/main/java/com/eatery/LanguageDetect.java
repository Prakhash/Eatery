package com.eatery;

import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;

import java.io.IOException;

/**
 * Created by bruntha on 6/5/15.
 */
public class LanguageDetect implements Runnable{

    public boolean isEnglish(String review)
    {
        boolean result=false;
        try {
            LanguageDetector detector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                    .shortTextAlgorithm(0)
                    .withProfiles(new LanguageProfileReader().readAllBuiltIn())
                    .build();

            if(detector.getProbabilities(text(review)).get(0).getLocale().getLanguage().equals("en"))
            {
                result=true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private CharSequence text(CharSequence text) {
        return CommonTextObjectFactories.forDetectingShortCleanText().forText( text );
    }

    @Override
    public void run() {

    }
}
