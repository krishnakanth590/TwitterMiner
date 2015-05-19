package com.pkg.twitter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.context.FacesContext;

public class ReadStates {
	public static HashMap<String, String> stateName = new HashMap<String, String>();
	public static HashMap<String, String> stateCode = new HashMap<String, String>();
	public static HashMap<String, String> stateAndCode = new HashMap<String, String>();
	public static ArrayList<String> stateNames = new ArrayList<String>();

	public static FacesContext fc = FacesContext.getCurrentInstance();
	public static String path = fc.getExternalContext().getRealPath("/");

	public ReadStates() {
		try {

			System.out.println("path : " + path);

			// String path = System.getProperty("user.dir");

			BufferedReader br = new BufferedReader(new FileReader(path
					+ "states/States.txt"));
			String readLine;
			stateName.clear();
			stateAndCode.clear();
			stateCode.clear();
			stateNames.clear();
			while ((readLine = br.readLine()) != null) {
				String temp[] = readLine.split("-");
				stateName.put(temp[0], "1");
				stateCode.put(temp[1], "1");
				stateNames.add(temp[0]);
				stateAndCode.put(temp[1], temp[0]);
				System.out.println(temp[0]);
				System.out.println(temp[1]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static boolean isUSState(String s) {
		// Check if it is State name
		String isStateName = stateName.get(s);
		// Check if it is State code
		String isStateCode = stateCode.get(s);
		System.out.println("Input String : " + s);
		System.out.println("isStateName : " + isStateName);
		System.out.println("isStateCode : " + isStateCode);
		if (!(isStateName == null)) {
			System.out.println("Returning (if):");
			return true;
		} else if (!(isStateCode == null)) {
			System.out.println("Returning (else if):");
			return true;
		} else {
			System.out.println("Returning (else):");
			return false;
		}
	}
}