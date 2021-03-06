// Test statistics donut graphs
$(document).ready(function () {
    var tId0 = getUrlVars()["t0"]
    var url0 = $('[stats]').attr('graph-api') + tId0
    var element0 = "#test-statistics0"

    testStatisticsDonutGraph(tId0, url0, element0)
})

$(document).ready(function () {
    var tId1 = getUrlVars()["t1"]
    var url1 = $('[stats]').attr('graph-api') + tId1
    var element1 = "#test-statistics1"
    testStatisticsDonutGraph(tId1, url1, element1)
})

// Build SUT information tables
$(document).ready(function() {
    var tId0 = getUrlVars()["t0"]
    var tNum0 = getUrlVars()["n0"]

    var sutUrl = "/api/test/" + tId0 + "/sut"
    getSutInfo(sutUrl, 0)

    var testInfoUrl = "/api/test/" + tId0 + "/number/" + tNum0;
    getTestInfo(testInfoUrl, 0)

   var testPropertiesUrl = "/api/test/" + tId0 + "/number/" + tNum0 + "/properties";
   getTestProperties(testPropertiesUrl, 0)
})


$(document).ready(function() {
    var tId1 = getUrlVars()["t1"]
    var tNum1 = getUrlVars()["n1"]

    var sutUrl = "/api/test/" + tId1 + "/sut"
    getSutInfo(sutUrl, 1)

    var testInfoUrl = "/api/test/" + tId1 + "/number/" + tNum1;
    getTestInfo(testInfoUrl, 1)

    var testPropertiesUrl = "/api/test/" + tId1 + "/number/" + tNum1 + "/properties";
   getTestProperties(testPropertiesUrl, 1)
})

// Bar graphs for the latency metrics
$(document).ready(
    function() {
        var tId0 = getUrlVars()["t0"]
        var tNum0 = getUrlVars()["n0"]
        var tId1 = getUrlVars()["t1"]
        var tNum1 = getUrlVars()["n1"]

        var url = $('[graphs]').attr('graph-api') + tId0 + '/' + tNum0 + '/' + tId1 + '/' + tNum1 + '/'

        var element = '#line-chart-3';
        var values = ['90th percentile', '95th percentile', '99th percentile'];
        var groups = [];

        groupedBarGraph(url, element, values, groups, null)
    }
)

// Bar graphs for the rate metrics
$(document).ready(
    function() {
        var tId0 = getUrlVars()["t0"]
        var tNum0 = getUrlVars()["n0"]
        var tId1 = getUrlVars()["t1"]
        var tNum1 = getUrlVars()["n1"]

        var url = $('[sender-rate-graph]').attr('graph-api') + tId0 + '/' + tNum0 + '/' + tId1 + '/' + tNum1
        var element = '#line-chart-4';
        var values = ['Rate Geometric Mean'];
        var groups = [];

        groupedBarGraph(url, element, values, groups, null)
    }
)

$(document).ready(
    function() {
        var tId0 = getUrlVars()["t0"]
        var tNum0 = getUrlVars()["n0"]
        var tId1 = getUrlVars()["t1"]
        var tNum1 = getUrlVars()["n1"]


        var url = $('[receiver-rate-graph]').attr('graph-api') + tId0 + '/' + tNum0 + '/' + tId1 + '/' + tNum1
        var element = '#line-chart-5';
        var values = ['Rate Geometric Mean'];
        var groups = [];

        groupedBarGraph(url, element, values, groups, null)
    }
)

