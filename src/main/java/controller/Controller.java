package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import view.App;

public class Controller implements Initializable {

	App app = new App();

	Task<Void> task;
	

    @FXML
    private ImageView pruebaImagen;
	
	
	@FXML
	private TextField asuntoTextField;

	@FXML
	private TextField destinatarioTextField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField puertoTextField;

	@FXML
	private TextField remitenteTextField;

	@FXML
	private TextField servidorTextField;

	@FXML
	private TextArea mensajeTextArea;

	@FXML
	private CheckBox sslCheck;

    @FXML
    private Button enviarButton;
	
	@FXML
	private GridPane view;

	@FXML
	void onCerrar(ActionEvent event) {

		App.primaryStage.close();

	}

//	@FXML
//	void onEnviar(ActionEvent event) {
//		
//		
//		Task<Void> task = new Task<>() {
//
//			@Override
//			protected Void call() throws Exception {
//				try {
//					Email email = new SimpleEmail();
//					email.setHostName(servidorTextField.getText());
//					email.setSmtpPort(Integer.parseInt(puertoTextField.getText()));
//					email.setAuthentication(remitenteTextField.getText(),passwordField.getText());
//					email.setSSLOnConnect(sslCheck.isSelected());
//					email.setSubject(asuntoTextField.getText());
//					email.setFrom(remitenteTextField.getText());
//					email.setMsg(mensajeTextArea.getText());
//					email.addTo(destinatarioTextField.getText());
//					email.send();
//					
//					Alert alertCon = new Alert(AlertType.CONFIRMATION);
//					alertCon.initOwner(app.primaryStage);
//					alertCon.setHeaderText("Mensaje enviado con éxito a '"+  destinatarioTextField.getText()  +"'.");
//					alertCon.show();
//					
//					boolean correcto = true;
//					correcto(correcto);
//					
//				} catch (EmailException | NumberFormatException e) {			
//					
//					Alert alertErr = new Alert(AlertType.ERROR);
//					alertErr.initOwner(app.primaryStage);
//					alertErr.setHeaderText("No se pudo enviar el email.");
//					alertErr.setContentText("Invalid message supplied");
//					alertErr.show();
//					}
//				return null;
//			}
//		
//		};
//		task.setOnSucceeded(e -> {
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("segundo Plano");
//			alert.setHeaderText("Proceso completado");
//			alert.show();
//			
//		});
//		
//		new Thread(task).start();
//	}

	@FXML
	void onEnviar(ActionEvent event) {
		
		enviarButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		enviarButton.setGraphic(new ImageView(new Image("/images/cargandoV2.GIF")));
		
		task = new Task<>() {

			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(2000);
					
					
					
					Email email = new SimpleEmail();
					email.setHostName(servidorTextField.getText());
					email.setSmtpPort(Integer.parseInt(puertoTextField.getText()));
					email.setAuthentication(remitenteTextField.getText(),passwordField.getText());
					email.setSSLOnConnect(sslCheck.isSelected());
					email.setSubject(asuntoTextField.getText());
					email.setFrom(remitenteTextField.getText());
					email.setMsg(mensajeTextArea.getText());
					email.addTo(destinatarioTextField.getText());
					
					email.send();
					
					
					
				} catch (EmailException | NumberFormatException e) {			
					
					task.cancel(true);
					
					}
				return null;
			}
		
		};
		task.setOnSucceeded(e -> {
			Alert alertCon = new Alert(AlertType.CONFIRMATION);
			alertCon.initOwner(App.primaryStage);
			alertCon.setHeaderText("Mensaje enviado con éxito a '"+  destinatarioTextField.getText()  +"'.");
			alertCon.setGraphic(new ImageView(new Image("/images/cosita2.GIF")));
			enviarButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
			alertCon.show();
			
			boolean correcto = true;
			correcto(correcto);
			
		});
		
		task.setOnCancelled(e -> {
			Alert alertErr = new Alert(AlertType.ERROR);
			alertErr.initOwner(App.primaryStage);
			alertErr.setHeaderText("No se pudo enviar el email.");
			alertErr.setContentText("Invalid message supplied");
			enviarButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
			alertErr.show();
			
		});
		
		task.setOnFailed(e -> {
			Alert alertErr = new Alert(AlertType.ERROR);
			alertErr.initOwner(App.primaryStage);
			alertErr.setHeaderText("No se pudo enviar el email.");
			alertErr.setContentText("Invalid message supplied");
			enviarButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
			alertErr.show();
			
		});
		
		new Thread(task).start();
		
	}
	
	@FXML
	void onVaciar(ActionEvent event) {

		asuntoTextField.clear();
		puertoTextField.clear();
		destinatarioTextField.clear();
		passwordField.clear();
		remitenteTextField.clear();
		servidorTextField.clear();
		mensajeTextArea.clear();

	}

	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Root.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {

		
	}

	public GridPane getView() {
		return view;
	}
	
	private void correcto(Boolean confirmacion) {
		
		if(confirmacion) {
			asuntoTextField.clear();
			destinatarioTextField.clear();
			mensajeTextArea.clear();
		}
		
	}
	
}
