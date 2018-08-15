
$(document).ready(function() {
    $('#envresultstable').DataTable({
        columns: [
            { data: "envResultsId" },
            { data: "envResourceId" },
            { data: "testId" },
            { data: "testNumber" },
            { data: "envName"},
            { data: "envResourceRole"},
            { data: "testRateMin"},
            { data: "testRateMax"},
            { data: "testRateErrorCount"},
            { data: "testRateSamples"},
            { data: "testRateGeometricMean"},
            { data: "testRateStandardDeviation"},
            { data: "testRateSkipCount"},
            { data: "connectionCount"},
            { data: "latPercentile90"},
            { data: "latPercentile95"},
            { data: "latPercentile99"},
            { data: "error"},
        ],
        ajax: {
            url: '/api/env/results',
            dataSrc:  ''
        }
    });
});