// nested/sub.js
define(['easel', 'devlog'], function (easel, DevLog ) {
    var dv = new DevLog();

    var easel = new easel();

    dv.consoleLogAndReportNewLine("**** Initializing the `js/app/nested/sub' module");
    dv.consoleLogAndReportNewLine("Inside sub.js and easel="+easel);

    var returnedModule = function () {
        this.getName = function () {
            return "sub";
        };
        this.getCanvasName = function () {
            return easel.getName();
        };
    };
  
    return returnedModule;
});