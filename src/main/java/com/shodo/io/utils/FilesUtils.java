package com.shodo.io.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FilesUtils {

    private FilesUtils() {
        throw new AssertionError(FilesUtils.class.getName() + " can not be instantiated.");
    }

    public static Map<String, List<String>> readFilesContent(Stream<Path> filesPaths) {
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
                System.err.println("Error while loading lines from file : " + e.getMessage());
            }
        });
        return filesContent;
    }

    public static void displayFilesCount(String directory) throws IOException {
        long filesCount;
        try (var filesPaths = Files.list(Paths.get(directory))) {
            filesCount = filesPaths
                    .filter(p -> p.toString().endsWith(".txt"))
                    .count();
        }
        var filesText = "files";
        if (filesCount == 1) {
            filesText = "file";
        }
        System.out.println(filesCount + " " + filesText + " read in directory " + directory);
    }


}
