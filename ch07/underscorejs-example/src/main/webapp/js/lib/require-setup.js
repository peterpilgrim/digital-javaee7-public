// require-setup.js
var require = {
    shim: {
        "bootstrap" : { "deps" :['jquery'] },
		"jquery": { exports: '$' },
        "underscore" : { exports: '_' }
    },
    // NOTICE: We put the minified files in the paths before the debuggable versions
    // This feature is called back FALLBACK paths. Just in case, we library did not exist, then we could go to a CDN
    paths: {
        "jquery" : [ "jquery-2.1.3.min", "jquery-2.1.3" ],
        "bootstrap" : [ "bootstrap-3.2.0.min", "bootstrap-3.2.0" ],
	    "underscore" : [ "underscore-1.8.2.min", "underscore-1.8.2" ]
    }
};
