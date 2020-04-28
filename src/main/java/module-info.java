module com.mycompany.rentmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.rentmanagementapp to javafx.fxml;
    exports com.mycompany.rentmanagementapp;
}