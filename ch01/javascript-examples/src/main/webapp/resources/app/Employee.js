// Defines an Employee module
// We use RequireJS to avoid polluting global scope space in modern JavaScript programming
// Peter Pilgrim, 2015

define([], function() {
    var returnedModule = function(id0, name0, role0) {
        var id = id0;
        var name = name0;
        var role = role0;

        this.getId = function () {
            return id;
        }

        this.getName = function () {
            return name;
        }

        this.getRole = function () {
            return role;
        }

        this.toString = function() {
            return "Employee( id=" + id + ", name=" + name + ", role="+role+" )";
        }
    };

    return returnedModule;
})