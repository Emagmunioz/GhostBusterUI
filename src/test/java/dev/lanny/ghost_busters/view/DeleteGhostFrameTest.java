package dev.lanny.ghost_busters.view;

import org.junit.jupiter.api.Test;

import dev.lanny.ghost_busters.controller.HunterController;
import dev.lanny.ghost_busters.model.HunterModel;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class DeleteGhostFrameTest {
    @Test
void testGhostListIsCreated() {
    assertDoesNotThrow(() -> {
        HunterModel testHunterModel = new HunterModel("Test Hunter", new ArrayList<>());
        HunterController testHunterController = new HunterController(testHunterModel);
        DeleteGhostFrame frame = new DeleteGhostFrame(new MainFrame(testHunterController), testHunterController);
        frame.setVisible(true);
        frame.dispose();
    });
}    
}
