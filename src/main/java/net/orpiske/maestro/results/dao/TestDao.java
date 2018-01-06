package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestDao extends AbstractDao {
    public int insert(Test dto) {
        return runInsert(
                "insert into test(test_name, test_result, test_parameter_id, sut_id, " +
                        "test_report_link, test_data_storage_info, test_tags, test_date, test_duration, test_target_rate) " +
                        "values(:testName, :testResult, :testParameterId, :sutId, :testReportLink," +
                        ":testDataStorageInfo, :testTags, now(), :testDuration, :testTargetRate)", dto);
    }

    public List<Test> fetchById(int id) {
        return jdbcTemplate.query("select * from test where test_id = ?",
                new Object[] { id },
                new BeanPropertyRowMapper<>(Test.class));
    }

    public List<Test> fetch() {
        return jdbcTemplate.query("select * from test",
                new BeanPropertyRowMapper<>(Test.class));
    }
}
