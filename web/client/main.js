$(function () {

    var temp_points = [];
    var humi_points = [];
    var baro_points = [];

    baro_points.push({
        values: [],
        key: 'barometric'
    });

    humi_points.push({
        values: [],
        key: 'relativeHumidty'
    });

    temp_points.push({
        values: [],
        key: 'temprature'
    });
    var graph_height=($(window).height() - $("#header").height()) / 3;

    $("#char_temp").height(graph_height);
    $("#chart_humi").height(graph_height);
    $("#chart_baro").height(graph_height);

    // temp
    var chart_temp = nv.models.lineChart()
        .interpolate('monotone')
        .useInteractiveGuideline(true)
        .showLegend(true)
        .color(d3.scale.category10().range());

    chart_temp.xAxis
        .axisLabel('Time');

    chart_temp.yAxis
        .axisLabel('Temperature');

    nv.addGraph(loadTempGraph);

    // humi
    var chart_humi = nv.models.lineChart()
        .interpolate('monotone')
        .useInteractiveGuideline(true)
        .showLegend(true)
        .color(d3.scale.category10().range());

    chart_humi.xAxis
        .axisLabel('Time');

    chart_humi.yAxis
        .axisLabel('relativeHumidty');

    nv.addGraph(loadHumiGraph);

    //Baro
    var chart_baro = nv.models.lineChart()
        .interpolate('monotone')
        .useInteractiveGuideline(true)
        .showLegend(true)
        .color(d3.scale.category10().range());

    chart_baro.xAxis
        .axisLabel('Time');

    chart_baro.yAxis
        .axisLabel('barometric');

    nv.addGraph(loadBaroGraph);

    function loadTempGraph() {
        "use strict";
        d3.select('#chart_temp svg')
            .datum(temp_points)
            .transition()
            .duration(5)
            .call(chart_temp);

        nv.utils.windowResize(chart_temp.update);
        return chart_temp;
    }

    function loadHumiGraph() {
        "use strict";
        d3.select('#chart_humi svg')
            .datum(humi_points)
            .transition()
            .duration(5)
            .call(chart_humi);

        nv.utils.windowResize(chart_humi.update);
        return chart_humi;
    }

    function loadBaroGraph() {
        "use strict";
        d3.select('#chart_baro svg')
            .datum(baro_points)
            .transition()
            .duration(5)
            .call(chart_baro);

        nv.utils.windowResize(chart_baro.update);
        return chart_baro;
    }

    function newDataCallback(message) {
        "use strict";
        var parsed = JSON.parse(message);
        var level = parsed['level'];
        var barometric = parsed['barometric'];
        var relativeHumidty = parsed['relativeHumidty'];
        var sonicTemprature = parsed['sonicTemprature'];
        var timestamp = parsed['recordNum'];

        var temp_point = {
            x: timestamp,
            y: sonicTemprature
        };
        var humi_point = {
            x: timestamp,
            y: relativeHumidty
        };
        var baro_point = {
            x: timestamp,
            y: barometric
        };

        //console.log(point);

        temp_points[0].values.push(temp_point);
        if (temp_points[0].values.length > 20) {
            temp_points[0].values.shift();
        }
        loadTempGraph();

        humi_points[0].values.push(humi_point);
        if (humi_points[0].values.length > 20) {
            humi_points[0].values.shift();
        }
        loadHumiGraph();

        baro_points[0].values.push(baro_point);
        if (baro_points[0].values.length > 20) {
            baro_points[0].values.shift();
        }
        loadBaroGraph();
    }

    function formatDateTick(time) {
        "use strict";
        var date = new Date(time * 1000);
        return d3.time.format('%H:%M:%S')(date);
    }

    var socket = io();

    // - Whenever the server emits 'data', update the flow graph
    socket.on('data', function (data) {
        newDataCallback(data);
    });
});