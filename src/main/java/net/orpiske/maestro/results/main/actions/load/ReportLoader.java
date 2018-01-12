package net.orpiske.maestro.results.main.actions.load;

import net.orpiske.maestro.results.dto.Test;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReportLoader {
    private static final Logger logger = LoggerFactory.getLogger(ReportLoader.class);

    private Test test;
    private String envName;

    private PropertiesProcessor pp;

    public ReportLoader(final Test test, final String envName) {
        this.test = test;
        this.envName = envName;
    }


    private void loadFromDir(final File dir, final List<File> files) {
        test.setTestNumber(Integer.parseInt(dir.getName()));

        TestProcessor tp = new TestProcessor(test);

        int testId = tp.loadTest(dir);

        test.setTestId(testId);
        pp = new PropertiesProcessor(test, envName);


        // Create a list of unique hosts reported in the test
        Set<File> testHosts = new LinkedHashSet<>();
        files.forEach(file -> testHosts.add(file.getParentFile()));

        // Load test data for each host
        testHosts.forEach(host -> pp.loadTest(host));
    }


    public void loadFiles(final File directory) {
        Iterator<File> iterator = FileUtils.iterateFiles(directory, new String[] { "properties"}, true);
        Map<File, List<File>> cache = new HashMap<>();

        logger.info("Searching for properties file from directory {}", directory);
        while (iterator.hasNext()) {
            File file = iterator.next();

            File parent = file.getParentFile().getParentFile();
            List<File> subFiles = cache.get(parent);
            if (subFiles == null) {
                subFiles = new LinkedList<>();
            }

            logger.trace("Adding file {}", file);
            subFiles.add(file);

            cache.put(parent, subFiles);
            logger.trace("Processing directory {}", parent);
        }

        cache.forEach(this::loadFromDir);


    }
}
