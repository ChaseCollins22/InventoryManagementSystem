module collins.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens collins.inventorysystem to javafx.fxml, javafx.base;
    opens Model to javafx.base;
    exports Model;
    exports collins.inventorysystem;
}