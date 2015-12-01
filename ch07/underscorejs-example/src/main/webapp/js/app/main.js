// main.js

// Start the main app logic.
requirejs(['jquery', 'underscore', 'bootstrap', 'easel', 'nested/sub', 'devlog'],
	function ($, _, bootstrap, easel, sub, DevLog ) {
	    //jQuery, easel and the app/sub module are all
	    //loaded and can be used here now.

		var dv = new DevLog();

		dv.consoleLogAndReportNewLine("**** Initializing the `js/app/main' module");

	    dv.consoleLogAndReportNewLine("start it up now!");

	    var e = new easel();
		dv.consoleLogAndReportNewLine("module 1 name = "+e.getName() );
		var s = new sub();
		dv.consoleLogAndReportNewLine("module 2 name = "+s.getName() );
		dv.consoleLogAndReportNewLine("other name = "+s.getCanvasName() );

		dv.reportNewLine(
				_.map( [ 1, 2, 3, 4], function(x){ return x * x; } )
		);
		// [1, 4. 9, 16]

		// DOM ready
	    $(function(){

	    	// Programmatically add class to toggles
			$('.btn.danger').button('toggle').addClass('fat');

			dv.consoleLogAndReportNewLine("set it off!");

			alert("set it off!");
	    });

	}
);
