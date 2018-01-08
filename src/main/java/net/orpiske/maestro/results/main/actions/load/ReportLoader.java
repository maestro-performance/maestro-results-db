package net.orpiske.maestro.results.main.actions.load;

import net.orpiske.maestro.results.dto.Test;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReportLoader {
    private Test test;
    private String envName;

    private PropertiesProcessor pp;

    public ReportLoader(final Test test, final String envName) {
        this.test = test;
        this.envName = envName;
    }

    private static void loadProperties(Map<String, Object> context, File testProperties) {
        if (testProperties.exists()) {
            Properties prop = new Properties();

            try (FileInputStream in = new FileInputStream(testProperties)) {
                prop.load(in);

                for (Map.Entry e : prop.entrySet()) {
                    //logger.debug("Adding entry {} with value {}", e.getKey(), e.getValue());
                    System.out.println("Adding entry " + e.getKey() + " with value " + e.getValue());
                    context.put((String) e.getKey(), e.getValue());
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //logger.debug("There are no properties file at {}", testProperties.getPath());
        }
    }

    private void loadFromDir(final File dir, final List<File> files) {
        Map<String, Object> values = new HashMap<>();

        test.setTestNumber(Integer.parseInt(dir.getName()));
        TestProcessor tp = new TestProcessor(test);

        int testId = tp.loadTest(dir);

        test.setTestId(testId);
        pp = new PropertiesProcessor(test, envName);

        files.forEach(item -> loadProperties(values, item));
        files.forEach(item -> pp.loadTest(item, values));

        // pp.loadTest(dir, values);
    }


    public void loadFiles(final File directory) {
        Iterator<File> iterator = FileUtils.iterateFiles(directory, new String[] { "properties"}, true);
        Map<File, List<File>> cache = new HashMap<>();

        while (iterator.hasNext()) {
            File file = iterator.next();

            File parent = file.getParentFile().getParentFile();
            List<File> subFiles = cache.get(parent);
            if (subFiles == null) {
                subFiles = new LinkedList<>();
            }

            System.out.println("Adding file " + file);
            subFiles.add(file);

            cache.put(parent, subFiles);
            System.out.println("Processing directory " + parent);
        }

        // for (File file : cache.keySet()) {
            // System.out.println("Directory (from key set) " + file);

            //File testNumber = file.getParentFile();


        // }

        cache.forEach(this::loadFromDir);


    }
}
