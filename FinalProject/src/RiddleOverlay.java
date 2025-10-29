import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class RiddleOverlay {

    public static void show(Stage owner, String paperImagePath, String riddleText) {

        ImageView paper = new ImageView(loadImage(paperImagePath));
        paper.setFitWidth(360);
        paper.setFitHeight(240);
        paper.setPreserveRatio(true);

        Label text = new Label(riddleText);
        text.setWrapText(true);
        text.setStyle("-fx-font-size: 14px; -fx-text-fill: #222;");

        Button close = new Button("Got it");
        close.setDefaultButton(true);
        close.setOnAction(e -> ((Stage) close.getScene().getWindow()).close());

        VBox card = new VBox(14, paper, text, close);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #333; -fx-border-radius: 12; -fx-background-radius: 12;");

        StackPane backdrop = new StackPane(card);
        backdrop.setPadding(new Insets(20));
        backdrop.setStyle("-fx-background-color: rgba(0,0,0,0.45);");

        Stage dialog = new Stage();
        if (owner != null) dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Letter");
        dialog.setScene(new Scene(backdrop, 450, 420));
        dialog.showAndWait();
    }

    private static Image loadImage(String path) {
        try {
            var is = RiddleOverlay.class.getResourceAsStream("/" + path);
            if (is != null) return new Image(is);
        } catch (Exception ignore) {}
        return new Image(path);
    }
}
