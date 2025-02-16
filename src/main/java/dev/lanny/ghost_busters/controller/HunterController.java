package dev.lanny.ghost_busters.controller;

import java.util.List;
import java.util.stream.Collectors;

import dev.lanny.ghost_busters.model.GhostClass;
import dev.lanny.ghost_busters.model.GhostModel;
import dev.lanny.ghost_busters.model.HunterModel;

public class HunterController {
    private HunterModel hunter;

    public HunterController(HunterModel hunterModel) {
        if (hunterModel == null) {
            throw new IllegalArgumentException("HunterModel not can be null");
        }
        this.hunter = hunterModel;
    }

    public List<GhostModel> getCapturedGhosts() {
        return hunter.getCapturedGhosts();
    }

    public void captureGhost(GhostModel ghost1) {
        hunter.captureGhost(ghost1);
    }

    public List<GhostModel> filterGhostsByClass(GhostClass ghostClass) {
        return hunter.filterGhostsByClass(ghostClass);
    }

    public List<GhostModel> filterGhostsByMonth(int month, int year) {
        return hunter.getCapturedGhosts().stream()
                .filter(ghost -> ghost.getCaptureDate().getMonthValue() == month
                        && ghost.getCaptureDate().getYear() == year)
                .collect(Collectors.toList());
    }

    public boolean freedomGhost(int ghostId) {
        return hunter.deleteGhost(ghostId);
    }

}
