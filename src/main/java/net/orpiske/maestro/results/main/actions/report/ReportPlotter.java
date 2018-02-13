package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dto.TestResultRecord;

import java.util.List;

public interface ReportPlotter {
    void buildChart(final String title, final String xAxisTile, final String yAxisTitle,
                           List<TestResultRecord> resultRecords, final String fileName);
}
