/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public class Charts {
    private boolean scaleShowGridLines = false;
    private int scaleGridLineWidth = 1;
    private String scaleGridLineColor = "rgba(0,0,0,.05)";
    private boolean scaleShowHorizontalLines = true;
    private boolean scaleShowVerticalLines = true;
    private int barValueSpacing = 5;
    private int barDatasetSpacing = 1;
    private boolean responsive = true;
    private boolean pointDot = false;
    private int pointDotRadius = 4;
    private String type = "barchart";
    private String lienOnClick;
    private String nomVariable;
    private String libelleOnClick;

    public String getChartOption() {
        String option = null;
        if (this.type.equalsIgnoreCase("barchart")) {
            option = "var barChartOptions                  = {\n      //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value\n      scaleBeginAtZero        : true,\n      //Boolean - Whether grid lines are shown across the chart\n      scaleShowGridLines      : " + this.scaleShowGridLines + ",\n      //String - Colour of the grid lines\n      scaleGridLineColor      : '" + this.scaleGridLineColor + "',\n      //Number - Width of the grid lines\n      scaleGridLineWidth      : " + this.scaleGridLineWidth + ",\n      //Boolean - Whether to show horizontal lines (except X axis)\n      scaleShowHorizontalLines: " + this.scaleShowHorizontalLines + ",\n      //Boolean - Whether to show vertical lines (except Y axis)\n      scaleShowVerticalLines  : " + this.scaleGridLineWidth + ",\n      //Boolean - If there is a stroke on each bar\n      barShowStroke           : true,\n      //Number - Pixel width of the bar stroke\n      barStrokeWidth          : 2,\n      //Number - Spacing between each of the X value sets\n      barValueSpacing         : " + this.barValueSpacing + ",\n      //Number - Spacing between data sets within X values\n      barDatasetSpacing       : " + this.barDatasetSpacing + ",\n      //String - A legend template\n      //Boolean - whether to make the chart responsive\n      responsive              : " + this.responsive + ",\n" + this.onClickOptions() + "      maintainAspectRatio     : true\n    }\n\n    barChartOptions.datasetFill = false;\nlet chart = new Chart(chartCanvas,{type:'bar',\ndata:data,options:barChartOptions})";
        } else if (this.type.equalsIgnoreCase("areachart")) {
            option = "    var areaChartOptions = {\n      //Boolean - If we should show the scale at all\n      showScale               : true,\n      //Boolean - Whether grid lines are shown across the chart\n      scaleShowGridLines      :  " + this.scaleShowGridLines + ",\n      //String - Colour of the grid lines\n      scaleGridLineColor      : ' " + this.scaleGridLineColor + "',\n      //Number - Width of the grid lines\n      scaleGridLineWidth      :  " + this.scaleGridLineWidth + ",\n      //Boolean - Whether to show horizontal lines (except X axis)\n      scaleShowHorizontalLines:  " + this.scaleShowHorizontalLines + ",\n      //Boolean - Whether to show vertical lines (except Y axis)\n      scaleShowVerticalLines  :  " + this.scaleShowVerticalLines + ",\n      //Boolean - Whether the line is curved between points\n      bezierCurve             : true,\n      //Number - Tension of the bezier curve between points\n      bezierCurveTension      : 0.3,\n      //Boolean - Whether to show a dot for each point\n      pointDot                : " + this.pointDot + ",\n      //Number - Radius of each point dot in pixels\n      pointDotRadius          : " + this.pointDotRadius + ",\n      //Number - Pixel width of point dot stroke\n      pointDotStrokeWidth     : 1,\n      //Number - amount extra to add to the radius to cater for hit detection outside the drawn point\n      pointHitDetectionRadius : 20,\n      //Boolean - Whether to show a stroke for datasets\n      datasetStroke           : true,\n      //Number - Pixel width of dataset stroke\n      datasetStrokeWidth      : 2,\n      //Boolean - Whether to fill the dataset with a color\n      datasetFill             : true,\n      //String - A legend template\n      //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container\n      maintainAspectRatio     : true,\n}";
        } else if (this.type.equalsIgnoreCase("donutchart")) {
            option = " var pieOptions     = {\n      //Boolean - Whether we should show a stroke on each segment\n      segmentShowStroke    : true,\n      //String - The colour of each segment stroke\n      segmentStrokeColor   : '#fff',\n      //Number - The width of each segment stroke\n      segmentStrokeWidth   : 2,\n      //Number - The percentage of the chart that we cut out of the middle\n      percentageInnerCutout: 50, // This is 0 for Pie charts\n      //Number - Amount of animation steps\n      animationSteps       : 100,\n      //String - Animation easing effect\n      animationEasing      : 'easeOutBounce',\n      //Boolean - Whether we animate the rotation of the Doughnut\n      animateRotate        : true,\n      //Boolean - Whether we animate scaling the Doughnut from the centre\n      animateScale         : false,\n      //Boolean - whether to make the chart responsive to window resizing\n      responsive           : " + this.responsive + ",\n      // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container\n      maintainAspectRatio  : true,\n      //String - A legend template\n    }\n    //Create pie or douhnut chart\n    // You can switch between pie and douhnut using the method below.\n    chart.Doughnut(data, pieOptions)";
        } else if (this.type.equalsIgnoreCase("linechart")) {
            option = "    var areaChartOptions = {\n      //Boolean - If we should show the scale at all\n      showScale               : true,\n      //Boolean - Whether grid lines are shown across the chart\n      scaleShowGridLines      : " + this.scaleShowGridLines + ",\n      //String - Colour of the grid lines\n      scaleGridLineColor      : '" + this.scaleGridLineColor + "',\n      //Number - Width of the grid lines\n      scaleGridLineWidth      : " + this.scaleGridLineWidth + ",\n      //Boolean - Whether to show horizontal lines (except X axis)\n      scaleShowHorizontalLines: " + this.scaleShowHorizontalLines + ",\n      //Boolean - Whether to show vertical lines (except Y axis)\n      scaleShowVerticalLines  : " + this.scaleShowVerticalLines + ",\n      //Boolean - Whether the line is curved between points\n      bezierCurve             : true,\n      //Number - Tension of the bezier curve between points\n      bezierCurveTension      : 0.3,\n      //Boolean - Whether to show a dot for each point\n      pointDot                : " + this.pointDot + ",\n      //Number - Radius of each point dot in pixels\n      pointDotRadius          : " + this.pointDotRadius + ",\n      //Number - Pixel width of point dot stroke\n      pointDotStrokeWidth     : 1,\n      //Number - amount extra to add to the radius to cater for hit detection outside the drawn point\n      pointHitDetectionRadius : 20,\n      //Boolean - Whether to show a stroke for datasets\n      datasetStroke           : true,\n      //Number - Pixel width of dataset stroke\n      datasetStrokeWidth      : 2,\n      //Boolean - Whether to fill the dataset with a color\n      datasetFill             : false,\n      //String - A legend template\n      //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container\n      maintainAspectRatio     : true,\n      //Boolean - whether to make the chart responsive to window resizing\n      responsive              : " + this.responsive + "\n    }\n\n    //Create the line chart\n    chart.Line(data, areaChartOptions)";
        } else if (this.type.equalsIgnoreCase("piechart")) {
            option = " var pieOptions     = {\n      //Boolean - Whether we should show a stroke on each segment\n      segmentShowStroke    : true,\n      //String - The colour of each segment stroke\n      segmentStrokeColor   : '#fff',\n      //Number - The width of each segment stroke\n      segmentStrokeWidth   : 2,\n      //Number - The percentage of the chart that we cut out of the middle\n      percentageInnerCutout: 0, // This is 0 for Pie charts\n      //Number - Amount of animation steps\n      animationSteps       : 100,\n      //String - Animation easing effect\n      animationEasing      : 'easeOutBounce',\n      //Boolean - Whether we animate the rotation of the Doughnut\n      animateRotate        : true,\n      //Boolean - Whether we animate scaling the Doughnut from the centre\n      animateScale         : false,\n      //Boolean - whether to make the chart responsive to window resizing\n      responsive           : " + this.responsive + ",\n      // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container\n      maintainAspectRatio  : true,\n      //String - A legend template\n    }\n    //Create pie or douhnut chart\n    // You can switch between pie and douhnut using the method below.\n    pieOptions.datasetFill = false;\nlet chart = new Chart(chartCanvas,{type:'pie',\ndata:data,options:pieOptions})";
        }
        return option;
    }

    public static String getChartOption(String chart) throws Exception {
        Charts charts = new Charts(chart);
        String option = charts.getChartOption();
        return option;
    }

    public static boolean isCercle(String chart) {
        boolean verifier = false;
        if (chart.equalsIgnoreCase("donutchart") || chart.equalsIgnoreCase("piechart")) {
            verifier = true;
        }
        return verifier;
    }

    public static boolean verifierChart(String chart) {
        boolean verifier = false;
        if (chart.equalsIgnoreCase("barchart") || chart.equalsIgnoreCase("areachart") || chart.equalsIgnoreCase("donutchart") || chart.equalsIgnoreCase("linechart") || chart.equalsIgnoreCase("piechart")) {
            verifier = true;
        }
        return verifier;
    }

    public Charts(String type) throws Exception {
        this.setType(type);
    }

    public Charts() {
    }

    public boolean isScaleShowGridLines() {
        return this.scaleShowGridLines;
    }

    public void setScaleShowGridLines(boolean scaleShowGridLines) {
        this.scaleShowGridLines = scaleShowGridLines;
    }

    public int getScaleGridLineWidth() {
        return this.scaleGridLineWidth;
    }

    public void setScaleGridLineWidth(int scaleGridLineWidth) {
        this.scaleGridLineWidth = scaleGridLineWidth;
    }

    public String getScaleGridLineColor() {
        return this.scaleGridLineColor;
    }

    public void setScaleGridLineColor(String scaleGridLineColor) {
        this.scaleGridLineColor = scaleGridLineColor;
    }

    public boolean isScaleShowHorizontalLines() {
        return this.scaleShowHorizontalLines;
    }

    public void setScaleShowHorizontalLines(boolean scaleShowHorizontalLines) {
        this.scaleShowHorizontalLines = scaleShowHorizontalLines;
    }

    public boolean isScaleShowVerticalLines() {
        return this.scaleShowVerticalLines;
    }

    public void setScaleShowVerticalLines(boolean scaleShowVerticalLines) {
        this.scaleShowVerticalLines = scaleShowVerticalLines;
    }

    public int getBarValueSpacing() {
        return this.barValueSpacing;
    }

    public void setBarValueSpacing(int barValueSpacing) {
        this.barValueSpacing = barValueSpacing;
    }

    public int getBarDatasetSpacing() {
        return this.barDatasetSpacing;
    }

    public void setBarDatasetSpacing(int barDatasetSpacing) {
        this.barDatasetSpacing = barDatasetSpacing;
    }

    public boolean isResponsive() {
        return this.responsive;
    }

    private void setResponsive(boolean responsive) {
        this.responsive = responsive;
    }

    public boolean isPointDot() {
        return this.pointDot;
    }

    public void setPointDot(boolean pointDot) {
        this.pointDot = pointDot;
    }

    public int getPointDotRadius() {
        return this.pointDotRadius;
    }

    public void setPointDotRadius(int pointDotRadius) {
        this.pointDotRadius = pointDotRadius;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) throws Exception {
        if (!Charts.verifierChart(type)) {
            throw new Exception("Chart invalide");
        }
        this.type = type;
    }

    public String getLienOnClick() {
        return this.lienOnClick;
    }

    public void setLienOnClick(String lienOnClick) {
        this.lienOnClick = lienOnClick;
    }

    public String getNomVariable() {
        return this.nomVariable;
    }

    public void setNomVariable(String nomVariable) {
        this.nomVariable = nomVariable;
    }

    public String getLibelleOnClick() {
        return this.libelleOnClick;
    }

    public void setLibelleOnClick(String libelleOnClick) {
        this.libelleOnClick = libelleOnClick;
    }

    public String onClickOptions() {
        if (this.lienOnClick != null) {
            String option = "onClick: function(e) {\nvar bar = this.getElementAtEvent(e)[0];\nvar index = bar._index;\nwindow.location.href =`" + this.lienOnClick + "&" + this.libelleOnClick + "=${" + this.nomVariable + "[index]}`;},";
            return option;
        }
        return "";
    }
}

