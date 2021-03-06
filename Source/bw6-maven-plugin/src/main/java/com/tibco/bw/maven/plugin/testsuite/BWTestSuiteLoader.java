package com.tibco.bw.maven.plugin.testsuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.tibco.bw.maven.plugin.test.helpers.BWTestConfig;
import com.tibco.bw.maven.plugin.utils.BWFileUtils;

public class BWTestSuiteLoader {

	public List<File> collectTestCasesList(String baseDir) throws IOException{
		List<File> testSuitefile = new ArrayList<File>();
		List<String> testSuiteNameList = new ArrayList<String>();
		String[] testSuiteNames;
		
		if(BWTestConfig.INSTANCE.getTestSuiteName().contains("/")){
			testSuiteNames = StringUtils.splitByWholeSeparator(BWTestConfig.INSTANCE.getTestSuiteName(), "/");
		}
		else{
			testSuiteNames = new String []{BWTestConfig.INSTANCE.getTestSuiteName()};
		}
		Arrays.asList(testSuiteNames);
		
		String testFolderPath = BWFileUtils.getTestFolderName(baseDir.toString(),testSuiteNames[0]);
		if(null != testFolderPath){
			for(String suiteName :testSuiteNames){
				testSuiteNameList.add(testFolderPath.concat("//"+suiteName));
			}
		}
		else{
			throw new FileNotFoundException("Test Suite file " +testSuiteNames[0]+ " is not found");
		}
		
		BWTestConfig.INSTANCE.setTestSuiteNameList(Arrays.asList(testSuiteNames));

		BWTSFileReaderWrapper fileReader = new BWTSFileReaderWrapper();
		testSuitefile = fileReader.readBWTSFile(testSuiteNameList,testFolderPath);
		return testSuitefile;



	}
	
}
