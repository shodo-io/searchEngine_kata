package com.shodo.io.scoring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tool class manipulating files.
 */
public class InputFilesService {

    /**
     * This method reads all files content (filesPaths parameter).
     * The content is spliited into a list of words.
     * <p>Exemple :</p>
     * <p>If a file contains these words : one two three four, The methods will return a ({@link List}) of these four words.</p>
     * @param filesPaths Files list to read.
     * @return A Map containing as key, the files name. And a list of words in values.
     */
    public Map<String, List<String>> readFilesContent(Stream<Path> filesPaths) {
        Map<String, List<String>> filesContent = new HashMap<>();
        filesPaths.forEach(filePath -> {
            try {
                filesContent.put(filePath.getFileName().toString(),
                        Files.readAllLines(filePath)
                                .stream()
                                .flatMap(list -> Stream.of(list.split(" ")))
                                .filter(word -> !word.isEmpty())
                                .map(String::trim)
                                .collect(Collectors.toList()));
            } catch (IOException e) {
                //TODO: throw an exception
                System.err.println("Error while loading lines from file : " + e.getMessage());
            }
        });
        return filesContent;
    }

    /**
     * Displays on the console the number of files from the directory parameter.
     * @param directory Directory containing the files to count.
     * @param filesExtension Predicat that indicate files extension to consider.
     * @throws IOException If the directory doesn't exist.
     */
    public void displayFilesCount(String directory, String filesExtension) throws IOException {
        long filesCount;
        try (var filesPaths = Files.list(Paths.get(directory))) {
            filesCount = filesPaths
                    .filter(p -> p.toString().endsWith(filesExtension))
                    .count();
        }
        var filesText = "files";
        if (filesCount == 1) {
            filesText = "file";
        }
        System.out.println(filesCount + " " + filesText + " read in directory " + directory);
    }


}
