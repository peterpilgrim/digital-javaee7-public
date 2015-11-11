// main.js

// Start the main app logic.
requirejs(['jquery', 'bootstrap', 'easel', 'nested/sub'],
	function ($, bootstrap, easel, sub) {
	    //jQuery, easel and the app/sub module are all
	    //loaded and can be used here now.

		console.log("**** Initializing the `js/app/main' module");

	    console.log("start it up now!");

	    var e = new easel();
		console.log("module 1 name = "+e.getName() );
		var s = new sub();
		console.log("module 2 name = "+s.getName() );
		console.log("other name = "+s.getCanvasName() );

		// DOM ready
	    $(function(){

	    	// Programmatically add class to toggles
			$('.btn.danger').button('toggle').addClass('fat');

			console.log("set it off!");

			alert("set it off!");
	    });

	}
);
