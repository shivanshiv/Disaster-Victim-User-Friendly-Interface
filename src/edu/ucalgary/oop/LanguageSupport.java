package edu.ucalgary.oop;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class LanguageSupport {
    private static final String DEFAULT_LANG = "en-CA";
    private HashMap<String, String> translations = new HashMap<>();
    private String currentLanguage;

    public LanguageSupport(String languageCode) {
        if (languageCode == null || !isValidLanguageCode(languageCode)) {
            System.out.println("Error: Invalid language code format. Using default language (en-CA).");
            loadLanguageFile(DEFAULT_LANG);
        } else {
            if (!loadLanguageFile(languageCode)) {
                System.out.println("Error: Could not load language file for " + languageCode + ". Using default (en-CA).");
                loadLanguageFile(DEFAULT_LANG);
            }
        }
    }

    private boolean isValidLanguageCode(String code) {
        return code.matches("^[a-z]{2}-[A-Z]{2}$");
    }

    private boolean loadLanguageFile(String langCode) {
        // Update the file path to look in the "data" directory
        File xmlFile = new File("data/" + langCode + ".xml");
        if (!xmlFile.exists()) {
            System.out.println("File does not exist: " + xmlFile.getAbsolutePath());
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(xmlFile))) {
            String line;
            String currentKey = null;
            boolean inValue = false;
            StringBuilder valueBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.contains("<key>") && line.contains("</key>")) {
                    currentKey = line.substring(line.indexOf("<key>") + 5, line.indexOf("</key>"));
                } else if (line.contains("<value>")) {
                    inValue = true;
                    if (line.contains("</value>")) {
                        String value = line.substring(line.indexOf("<value>") + 7, line.indexOf("</value>"));
                        translations.put(currentKey, value);
                        inValue = false;
                    } else {
                        valueBuilder = new StringBuilder(line.substring(line.indexOf("<value>") + 7));
                    }
                } else if (inValue && line.contains("</value>")) {
                    valueBuilder.append(line.substring(0, line.indexOf("</value>")));
                    translations.put(currentKey, valueBuilder.toString());
                    inValue = false;
                } else if (inValue) {
                    valueBuilder.append(line);
                }
            }
            currentLanguage = langCode;
            return true;
        } catch (IOException e) {
            System.out.println("Error reading language file: " + e.getMessage());
            return false;
        }
    }

    public String getTranslation(String key) {
        return translations.getOrDefault(key, "Missing translation: " + key);
    }

    public String getTranslation(String key, Object... args) {
        String pattern = translations.getOrDefault(key, "Missing translation: " + key);
        return String.format(pattern, args);
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public static String[] getAvailableLanguages() {
        File dir = new File("data");
        File[] files = dir.listFiles((d, name) -> name.matches("^[a-z]{2}-[A-Z]{2}\\.xml$"));

        if (files == null) {
            return new String[]{DEFAULT_LANG};
        }

        String[] languages = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            languages[i] = files[i].getName().replace(".xml", "");
        }

        return languages;
    }
}