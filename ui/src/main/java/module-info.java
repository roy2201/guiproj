module com.example.mainapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires lombok;


    opens com.example.mainapp to javafx.fxml;
    exports com.example.mainapp;
    exports Models;
    opens Models to javafx.fxml;
}