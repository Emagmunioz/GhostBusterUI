package dev.lanny.ghost_busters.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.lanny.ghost_busters.controller.HunterController;
import dev.lanny.ghost_busters.model.HunterModel;

public class CaptureGhostFrameTest {
    private FrameFixture window;
    private HunterController hunterController;

    @BeforeEach
    @DisplayName("Configurar la ventana antes de cada test")
    void setUp() {
        hunterController = new HunterController(
                new HunterModel("Egon Spengler", new ArrayList<>()));

        CaptureGhostFrame captureGhostFrame = GuiActionRunner.execute(() -> new CaptureGhostFrame(null,hunterController));
        window = new FrameFixture(captureGhostFrame);
    }

    @AfterEach
    @DisplayName("Cerrar la ventana despues de cada test")
    void tearDown() {
        window.cleanUp();
    }

    @Test
    @DisplayName("La ventana de captura de fantasma deberia abrirse correctamente")
    void testCaptureGhostFrame_Open_Correctly() {
        assertNotNull(window);
        window.requireVisible();
        window.requireTitle("👻 Capturar Fantasma");
    }

    @Test
    @DisplayName("Todos los campos de entrada deberian estar visibles")
    void testAll_InputFields_AreVisible() {
        window.textBox("nameField").requireVisible();
        window.textBox("abilityField").requireVisible();
        window.textBox("dateField").requireVisible();
    }

    @Test
    @DisplayName("Si los campos estan vacios, deberia aparecer un mensaje de error")
    void testErrorMessage_WhenField_AreEmpty() {
        window.button("captureButton").click();
        window.label("statusLabel").requireText("❌ Nombre y habilidad no pueden estar vacíos."); 
    }
    
    @Test
@DisplayName("Si la fecha no tiene el formato correcto, debería mostrar un error")
void testErrorMessage_ForInvalid_DateFormat() {
    window.textBox("nameField").enterText("GhostName");
    window.textBox("abilityField").enterText("Invisibility");
    window.textBox("dateField").setText("12-02-2025");

    window.button("captureButton").click();
    
    window.label("statusLabel").requireText("❌ Fecha inválida. Use formato YYYY-MM-DD.");
}


    @Test
    @DisplayName("Si todo esta bien, el fantasma deberia capturarse correctamente")
    void testSuccessful_GHostCapture() {
        window.textBox("nameField").setText("");
        window.textBox("nameField").enterText("GhostName");

        window.textBox("abilityField").setText("");
        window.textBox("abilityField").enterText("Invisibility");

        window.textBox("dateField").setText("");
        window.textBox("dateField").enterText("2025-02-12");

        window.comboBox("ghostClassComboBox").selectItem("CLASS_III - Entidad inteligente");
        window.comboBox("threatLevelComboBox").selectItem("HIGH - Amenaza de nivel alto");

        window.button("captureButton").click();

        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        window.label("statusLabel").requireText("✅ ¡Fantasma capturado!");
    }

}
