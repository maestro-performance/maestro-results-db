package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContendedReportCreator {
    private String outputDir;

    public ContendedReportCreator(String outputDir) {
        this.outputDir = outputDir;
    }


    public ReportInfo create(final Sut sut, final String protocol, boolean durable, int messageSize) throws Exception {
        ReportsDao reportsDao = new ReportsDao();

        List<TestResultRecord> testResultRecordsSender = reportsDao.contentedScalabilityReport(sut.getSutName(),
                sut.getSutVersion(), protocol, "sender", durable, messageSize);

        if (testResultRecordsSender == null || testResultRecordsSender.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return null;
        }


        List<TestResultRecord> testResultRecordsReceiver = reportsDao.contentedScalabilityReport(sut.getSutName(),
                sut.getSutVersion(), protocol, "receiver", durable, messageSize);

        if (testResultRecordsReceiver == null || testResultRecordsReceiver.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return null;
        }

        // testResultRecordsSender.forEach(System.out::println);


        Map<String, Object> context = new HashMap<String, Object>();

        context.put("testResultRecordsSender", testResultRecordsSender);
        context.put("testResultRecordsReceiver", testResultRecordsReceiver);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", 1);
        context.put("messageSize", messageSize);


        ReportInfo reportInfo = new ContendedReportInfo(sut, protocol, durable, 1, messageSize, 0);

        // Directory creating
        File baseReportDir = new File(outputDir, reportInfo.baseName());

        baseReportDir.mkdirs();

        // Data plotting
        ContendedReportDataPlotter rdp = new ContendedReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsSender,
                "contended-performance-sender.png");

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsReceiver,
                "contended-performance-receiver.png");
        generateIndex(context, baseReportDir);


        return reportInfo;
    }

    private void generateIndex(Map<String, Object> context, File baseReportDir) throws Exception {
        // Index HTML generation
        ContendedReportRenderer reportRenderer = new ContendedReportRenderer(ReportTemplates.DEFAULT, context);
        File indexFile = new File(baseReportDir, "index.html");
        FileUtils.writeStringToFile(indexFile, reportRenderer.render(), Charsets.UTF_8);
    }
}
