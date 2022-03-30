module com.lab.oxgame.k_k {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires org.slf4j;

    opens com.lab.oxgame to javafx.fxml;
    opens com.lab.oxgame.gamemodel to javafx.base;
    exports com.lab.oxgame;
}