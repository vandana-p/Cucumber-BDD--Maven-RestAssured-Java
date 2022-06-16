package utilities;

import java.io.File;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Paths;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConversionUtility {
	private static Logger logger = LoggerFactory.getLogger(ConversionUtility.class);

	public static String convertTextToString(String textFilePath) throws Exception {

		String text = "";

		text = new String(Files.readAllBytes(Paths.get(textFilePath)));
		logger.info("Input file is converted to string");
		return text;

	}

	public static File updateFileContent(String textFilePath, String oldValue, String newValue) throws Exception {

		String text = "";

		text = new String(Files.readAllBytes(Paths.get(textFilePath)));

		String newText = changeTextValue(text, oldValue, newValue);

		logger.info("Input file content is updated");
		return new File(newText);

	}

	public static String updateJsonFile(String textFilePath, List<String> oldValue, List<String> newValue)
			throws IOException

	{

		int i = 0;
		String textFromFile = new String(Files.readAllBytes(Paths.get(textFilePath)));

		while (i < oldValue.size())

		{

			textFromFile = textFromFile.replaceAll(oldValue.get(i), newValue.get(i));

			i++;

		}

		logger.info("Input file content is updated");
		return textFromFile;

	}

	public static String changeTextValue(String text, String oldValue, String newValue)

	{

		String changedValue = text.replaceAll(oldValue, newValue);
		logger.info("Input text is updated");
		return changedValue;

	}

}
