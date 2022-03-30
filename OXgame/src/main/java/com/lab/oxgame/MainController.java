package com.lab.oxgame;

import com.lab.oxgame.dao.RozgrywkaDAO;
import com.lab.oxgame.dao.RozgrywkaDAOImpl;
import com.lab.oxgame.engine.OXGame;
import com.lab.oxgame.engine.OXGameImpl;
import com.lab.oxgame.gamemodel.OXEnum;
import com.lab.oxgame.gamemodel.Rozgrywka;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final RozgrywkaDAO rozgrywkaDAO;
    private ExecutorService executor;
    private final OXGame oxGame;
    private final Rozgrywka rozgrywka;

    @FXML
    private TableView<Rozgrywka> rozgrywkaTable;
    @FXML
    private TableColumn<Rozgrywka, Integer> rozgrywkaIdColumn;
    @FXML
    private TableColumn<Rozgrywka, String> graczXColumn;
    @FXML
    private TableColumn<Rozgrywka, String> graczOColumn;
    @FXML
    private TableColumn<Rozgrywka, OXEnum> zwyciezcaColumn;
    @FXML
    private TableColumn<Rozgrywka, LocalDateTime> dataczasRozgrywkiColumn;
    @FXML
    private TextField nazwaO;
    @FXML
    private TextField nazwaX;
    @FXML
    private Button btn0;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button nowaGra;

    private ObservableList<Rozgrywka> history;

    public MainController() {
        rozgrywkaDAO = new RozgrywkaDAOImpl();
        executor = Executors.newSingleThreadExecutor();
        oxGame = new OXGameImpl();
        oxGame.initialize();
        rozgrywka = new Rozgrywka();
    }

    private void toBeginingOfGame() {
        btn0.setDisable(true);
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        btn5.setDisable(true);
        btn6.setDisable(true);
        btn7.setDisable(true);
        btn8.setDisable(true);
        btn0.setText("");
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
    }

    @FXML
    private void initialize() {
        rozgrywkaIdColumn.setCellValueFactory(new PropertyValueFactory<Rozgrywka, Integer>("rozgrywkaId"));
        graczOColumn.setCellValueFactory(new PropertyValueFactory<Rozgrywka, String>("graczO"));
        graczXColumn.setCellValueFactory(new PropertyValueFactory<Rozgrywka, String>("graczX"));
        zwyciezcaColumn.setCellValueFactory(new PropertyValueFactory<Rozgrywka, OXEnum>("zwyciezca"));
        dataczasRozgrywkiColumn.setCellValueFactory(new PropertyValueFactory<Rozgrywka, LocalDateTime>
                ("dataczasRozgrywki"));
        history = FXCollections.observableArrayList();
        rozgrywkaTable.setItems(history);
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Rozgrywka> rozgrywki = new ArrayList<>();
            try {
                rozgrywki.addAll(rozgrywkaDAO.findAll());
            } catch (Exception e) {
                String errMsg = "Błąd podczas inicjalizacji historii rozgrywek!";
                logger.error(errMsg, e);
                String errDetails = e.getCause() != null ?
                        e.getMessage() + "\n" + e.getCause().getMessage() :
                        e.getMessage();
                Platform.runLater(() -> showError(errMsg, errDetails));
            }
            Platform.runLater(() -> {
                history.addAll(rozgrywki);
            });
        });
        nazwaX.setText("");
        nazwaO.setText("");
        toBeginingOfGame();
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void shutdown() {
        executor.shutdownNow();
    }

    @FXML
    private void onActionBtnReset(ActionEvent event) {
        history.clear();
        nazwaX.setText("");
        nazwaO.setText("");
        rozgrywkaDAO.deleteAll();
        toBeginingOfGame();
        oxGame.initialize();
//     testDb();
    }

    @FXML
    private void onActionBtnNowaGra(ActionEvent event) {
        if (!Objects.equals(nazwaO.getText(), "") && !Objects.equals(nazwaX.getText(), "")) {
            oxGame.initialize();
            rozgrywka.setGraczO(nazwaO.getText());
            rozgrywka.setGraczX(nazwaX.getText());
            btn0.setDisable(false);
            btn1.setDisable(false);
            btn2.setDisable(false);
            btn3.setDisable(false);
            btn4.setDisable(false);
            btn5.setDisable(false);
            btn6.setDisable(false);
            btn7.setDisable(false);
            btn8.setDisable(false);
        }
    }

//    private void testDb() {
//        try {
//            Rozgrywka rozgrywka = new Rozgrywka("Jan", "Julia", OXEnum.O, LocalDateTime.now());
//            rozgrywkaDAO.save(rozgrywka);
//            logger.info("Id utworzonej rozgrywki {}", rozgrywka.getRozgrywkaId());
//            List<Rozgrywka> rozgrywki = rozgrywkaDAO.findAll();
//            for (Rozgrywka r : rozgrywki) {
//                logger.info("Rozgrywka - Id: {}, Gracz O: {}, Gracz X: {}", r.getRozgrywkaId(), r.getGraczO(), r.getGraczX());
//            }
//            rozgrywkaDAO.deleteAll();
//        } catch (Exception e) {
//            logger.error("Błąd podczas testowania operacji bazodanowych!", e);
//        }
//    }

    @FXML
    public void onActionBtn1(ActionEvent event) {
        ruch((Button) event.getSource(), 0);
    }

    @FXML
    public void onActionBtn2(ActionEvent event) {
        ruch((Button) event.getSource(), 1);
    }

    @FXML
    public void onActionBtn3(ActionEvent event) {
        ruch((Button) event.getSource(), 2);
    }

    @FXML
    public void onActionBtn4(ActionEvent event) {
        ruch((Button) event.getSource(), 3);
    }

    @FXML
    public void onActionBtn5(ActionEvent event) {
        ruch((Button) event.getSource(), 4);
    }

    @FXML
    public void onActionBtn6(ActionEvent event) {
        ruch((Button) event.getSource(), 5);
    }

    @FXML
    public void onActionBtn7(ActionEvent event) {
        ruch((Button) event.getSource(), 6);
    }

    @FXML
    public void onActionBtn8(ActionEvent event) {
        ruch((Button) event.getSource(), 7);
    }

    @FXML
    public void onActionBtn9(ActionEvent event) {
        ruch((Button) event.getSource(), 8);
    }

    private void ruch(Button btn, int index) {
        OXEnum kolej = oxGame.getKolej();
        if (!Objects.equals(OXEnum.BRAK, oxGame.getKolej())) {
            if (Objects.equals(OXEnum.BRAK, oxGame.getPole(index))) {
                btn.setText(kolej.toString());
                oxGame.setPole(index);
                kolej = oxGame.getKolej();
                OXEnum zwyciezca = oxGame.getZwyciezca();
                if (zwyciezca != null) {
                    rozgrywka.setZwyciezca(zwyciezca);
                    rozgrywka.setDataczasRozgrywki(LocalDateTime.now());
                    rozgrywkaDAO.save(rozgrywka);
                    history.add(rozgrywka);
                    toBeginingOfGame();
                }
            }
        }
    }
}
