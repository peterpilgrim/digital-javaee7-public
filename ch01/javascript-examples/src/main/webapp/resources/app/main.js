// The main application

require(['Employee'], function(Employee){
    var employee = new Employee(12, "Jane Smith", "Sales Director");

    console.log("employee = "+employee );
});