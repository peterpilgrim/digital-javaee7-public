// nested/sub.js
define(['easel'], function (easel) {
    var easel = new easel();
    console.log("**** Initializing the `js/app/nested/sub' module");
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