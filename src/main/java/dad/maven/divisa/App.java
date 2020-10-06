package dad.maven.divisa;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
	
	private Stage primaryStage;

	private Divisa euro = new Divisa("Euro", 1.0);
	private Divisa libra = new Divisa("Libra", 0.9);
	private Divisa dolar = new Divisa("Dolar", 1.17);
	private Divisa yen = new Divisa("Yen", 124.17);

	private Divisa[] divisas = { euro, libra, dolar, yen };

	private TextField introduceText, resultadoText;
	private ComboBox<Divisa> introduceCombo;
	private ComboBox<Divisa> resultadoCombo;
	private Button cambiarButton;

	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;

		introduceText = new TextField("0");
		introduceText.setPrefColumnCount(4);

		introduceCombo = new ComboBox<>();
		introduceCombo.getItems().addAll(divisas);
		introduceCombo.getSelectionModel().selectFirst();

		HBox introduceBox = new HBox();
		introduceBox.setAlignment(Pos.BASELINE_CENTER);
		introduceBox.setSpacing(5);
		introduceBox.getChildren().addAll(introduceText, introduceCombo);

		resultadoText = new TextField("0");
		resultadoText.setPrefColumnCount(4);
		resultadoText.setEditable(false);

		resultadoCombo = new ComboBox<>();
		resultadoCombo.getItems().addAll(divisas);
		resultadoCombo.getSelectionModel().selectLast();

		HBox resultadoBox = new HBox();
		resultadoBox.setAlignment(Pos.BASELINE_CENTER);
		resultadoBox.setSpacing(5);
		resultadoBox.getChildren().addAll(resultadoText, resultadoCombo);

		cambiarButton = new Button("Cambiar");
		cambiarButton.setOnAction(e -> onCambiarAction(e));

		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(introduceBox, resultadoBox, cambiarButton);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Cambio de divisa");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void onCambiarAction(ActionEvent e) {
		try {
			Double cantidadIntroducida = Double.parseDouble(introduceText.getText());
			Divisa divisaIntroducida = introduceCombo.getSelectionModel().getSelectedItem();
			Divisa divisaResultado = resultadoCombo.getSelectionModel().getSelectedItem();
			Double cantidadResultado = divisaResultado.fromEuro(divisaIntroducida.toEuro(cantidadIntroducida));
			resultadoText.setText("" + cantidadResultado);
		} catch (NumberFormatException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Error");
			alert.setHeaderText("Debe introducir un numero en la cantidad de origen");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
