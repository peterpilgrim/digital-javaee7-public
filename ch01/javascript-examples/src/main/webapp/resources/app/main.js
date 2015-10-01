// The main application
// This is the MAIN JavScript module.
// We use RequireJS to avoid polluting global scope space in modern JavaScript programming
// Peter Pilgrim, 2015

require(['Employee','Department'], function(Employee, Department){

    var department = new Department( 1745, 'Information Technology');
    var employee1 = new Employee(12, "Jane Smith", "Sales Director");
    var employee2 = new Employee(18, "Gary Leeways", "Technical Leader");

    console.log("employee1 = "+employee1 );
    console.log("employee2 = "+employee2 );

    console.log("department = "+department );

    department.addEmployee(employee1);

    console.log("department = "+department );

    department.addEmployee(employee2);

    console.log("department = "+department );

});