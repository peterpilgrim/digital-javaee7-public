// Define Department module
// We use RequireJS to avoid polluting global scope space in modern JavaScript programming
// Peter Pilgrim, 2015

define( ['Employee' ], function(Employee) {
    var returnedModule = function(id0, name0 ) {
        var id = id0;
        var name = name0;
        var employees = [];

        this.getId = function () {
            return id;
        }

        this.getName = function () {
            return name;
        }

        this.getEmployees = function () {
            return employees;
        }

        this.addEmployee = function(employee) {
            employees.push(employee);
        }

        this.toString = function() {
            return "Employee( id=" + id + ", name=" + name + ", employees="+employees+" )";
        }
    };

    return returnedModule;
});
