package start;

import beregnafskrivning.BeregnAfskrivning;
import beregnafskrivning.BeregnAfskrivningController;
import beregnafskrivning.BeregnAfskrivningImpl;
import beregnomsaetning.BeregnOmsaetningController;
import entities.Afskrivning;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GrundUIController {
    private BeregnOmsaetningController beregnOmsaetningController;
//    private BeregnAfskrivningController beregnAfskrivningController;
    double afskrivningsPaneLayoutY = 38;
    ArrayList<Node> afskrivninger;
    ArrayList<BeregnAfskrivningController> beregnAfskrivningControllers;
    BeregnAfskrivningImpl beregnAfskrivning;

    @FXML
    Label omsaetningResultatLabel1, omsaetningResultatLabel2;

    @FXML
    Label afskrivningResultatLabel1, afskrivningResultatLabel2;

    @FXML
    private Pane omsaetningPane, afskrivningPane;

    public void initialize() throws IOException {
        afskrivninger = new ArrayList<>();
        beregnAfskrivningControllers = new ArrayList<>();
        beregnAfskrivning = new BeregnAfskrivningImpl();
        loadOmsaetning();
        loadAfskrivning();
    }

    public void loadOmsaetning() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnomsaetning/Beregn_omsaetning.fxml"));
        Node node = fxmlLoader.load();
        beregnOmsaetningController = fxmlLoader.getController();
        omsaetningPane.getChildren().add(node);
    }

    public void loadAfskrivning() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnafskrivning/Beregn_afskrivning.fxml"));
        Node node = fxmlLoader.load();
        BeregnAfskrivningController beregnAfskrivningController;
        beregnAfskrivningController = fxmlLoader.getController();
        beregnAfskrivningController.setGrundUIController(this);
        beregnAfskrivningController.setBeregnAfskrivning(beregnAfskrivning);
        beregnAfskrivningControllers.add(beregnAfskrivningController);
        afskrivningPane.getChildren().add(node);
        afskrivninger.add(node);
        beregnAfskrivningController.setNode(node);
    }

    @FXML
    public void tilfoejTilResultatBudget(){
        omsaetningResultatLabel1.setText(String.valueOf(beregnOmsaetningController.getOmsaetning().hentOmsaetning()));
        omsaetningResultatLabel2.setText(omsaetningResultatLabel1.getText());
    }

    @FXML
    public void tilfoejNyAfskrivning() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../beregnafskrivning/Beregn_afskrivning.fxml"));
        Node node = fxmlLoader.load();
        afskrivninger.add(node);
        afskrivningsPaneLayoutY += 68;
        BeregnAfskrivningController beregnAfskrivningController;
        beregnAfskrivningController = fxmlLoader.getController();
        beregnAfskrivningController.setGrundUIController(this);
        beregnAfskrivningController.setBeregnAfskrivning(beregnAfskrivning);
        beregnAfskrivningControllers.add(beregnAfskrivningController);
        node.setLayoutY(afskrivningsPaneLayoutY);
        afskrivningPane.setPrefHeight(afskrivningPane.getPrefHeight() + 68);
        afskrivningPane.getChildren().add(node);
        beregnAfskrivningController.setNode(node);
        changeLayout();
    }

    @FXML
    public void fjernAfkrivning(Node node, String string) throws IOException{
        if (afskrivninger.size() <= 0){
            return;
        }
        if (!string.isEmpty()) {
            if (beregnAfskrivning.hentAfskrivninger().containsKey(string)) {
                beregnAfskrivning.hentAfskrivninger().remove(string);
                opdaterAfskrivninger();
            }
        }
        afskrivningPane.getChildren().remove(node);
        afskrivninger.remove(node);
        changeLayout();
        afskrivningPane.setPrefHeight(afskrivningPane.getPrefHeight() - 68);
    }

    @FXML
    public void changeLayout() {
        for (int i = 0; i < afskrivninger.size(); i++) {
            afskrivninger.get(i).setLayoutY(i * 68 + 38);
        }
    }

    @FXML
    public void opdaterAfskrivninger() {
        double sum = 0;
        for (Map.Entry<String, Afskrivning> entry : beregnAfskrivning.hentAfskrivninger().entrySet()) {
            sum += entry.getValue().hentAfskrivningsvaerdi();
        }
        afskrivningResultatLabel1.setText(sum + "");
        afskrivningResultatLabel2.setText(sum + "");
    }
}
