module ds.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;


    opens UI to javafx.fxml;
    exports UI;
}