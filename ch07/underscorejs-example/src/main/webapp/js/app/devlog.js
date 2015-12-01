// devlog.js
define([ 'jquery' ], function ($) {
    console.log("**** Initializing the `js/app/devlog' module");
    var returnedModule = function () {
        var _name = 'devlog';
        this.getName = function () {
            return _name;
        }

        this.reportNewLine = function(msg) {
            this.report(msg + "\n");
        }

        this.report = function(msg) {
            var element = $('#reportingArea');

            if ( element !== undefined ) {
                var content = element.html();
                element.html( content + msg );
            }
        }

        this.consoleLogAndReportNewLine = function(msg) {
            console.log(msg);
            this.report(msg + "\n");
        }


    };
 
    return returnedModule; 
});
