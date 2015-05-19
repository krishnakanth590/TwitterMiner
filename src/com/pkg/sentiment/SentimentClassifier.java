package com.pkg.sentiment;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

public class SentimentClassifier {
	String[] categories;
	public static LMClassifier cls;

	public SentimentClassifier() {
		System.out.println("In Sentiment classifier constructor");
		try {
			System.out.println("In try");
			cls = (LMClassifier) AbstractExternalizable.readObject(new File(
					"/home/krishnakanth/classifier.txt"));
			categories = cls.categories();
			System.out.println("End of try");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public String classify(String text) {
		System.out.println("In classify method");
		ConditionalClassification classification = cls.classify(text);
		return classification.bestCategory();
	}
}