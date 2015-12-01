// easel.js
define([ 'devlog' ], function (DevLog) {
    var dv = new DevLog();

    dv.consoleLogAndReportNewLine("**** Initializing the `js/app/easel' module");

    var returnedModule = function () {
        var _name = 'easel';
        this.getName = function () {
            return _name;
        }
    };
 
    return returnedModule; 
});
