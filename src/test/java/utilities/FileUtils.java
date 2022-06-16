package utilities;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Paths;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;


public class FileUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);


	public static String readFile(String filePath) {

		try {

			logger.info("Reading file");
			String text = new String(Files.readAllBytes(Paths.get(filePath)));
			logger.info("File converted to string");

			return text;

		} catch (IOException e) {

			logger.error("ERROR: Exception occurred while reading file. Exception:- {}", e.getMessage());

		}

		return null;

	}

}
