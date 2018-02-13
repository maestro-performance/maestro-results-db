package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtocolReportCreator extends AbstractReportCreator {
    public ProtocolReportCreator(final String outputDir) {
        super(outputDir);
    }


    public ReportInfo create(final Sut sut, boolean durable, int limitDestinations, int messageSize,
                             int connectionCount) throws Exception
    {
        ReportsDao reportsDao = new ReportsDao();

        List<TestResultRecord> testResultRecords = reportsDao.protocolReports(sut.getSutName(), sut.getSutVersion(),
                durable, limitDestinations, messageSize, connectionCount);

        if (testResultRecords == null || testResultRecords.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return null;
        }


        Map<String, Object> context = new HashMap<String, Object>();

        context.put("testResultRecords", testResultRecords);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", limitDestinations);
        context.put("messageSize", messageSize);
        context.put("connectionCount", connectionCount);


        ReportInfo reportInfo = new ProtocolReportInfo(sut, durable, limitDestinations, messageSize, connectionCount);

        // Directory creating
        File baseReportDir = createReportBaseDir(reportInfo);


        // Data plotting
        ProtocolReportDataPlotter rdp = new ProtocolReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testResultRecords,
                "performance-by-protocol.png");

        generateIndex("protocol-results.html", baseReportDir, context);


        return reportInfo;
    }
}