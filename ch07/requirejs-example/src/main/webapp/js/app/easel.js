// easel.js
define([], function () {
    console.log("**** Initializing the `js/app/easel' module");
    var returnedModule = function () {
        var _name = 'easel';
        this.getName = function () {
            return _name;
        }
    };
 
    return returnedModule; 
});
