package lk.ijse.travel_booking_system.controller;

import lk.ijse.travel_booking_system.dto.GuideDTO;
import lk.ijse.travel_booking_system.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guides")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @PostMapping
    public ResponseEntity<GuideDTO> saveGuide(@RequestBody GuideDTO guideDTO) {
        return ResponseEntity.ok(guideService.saveGuide(guideDTO));
    }

    @GetMapping
    public ResponseEntity<List<GuideDTO>> getAllGuides() {
        return ResponseEntity.ok(guideService.getAllGuides());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuideDTO> getGuideById(@PathVariable Long id) {
        return ResponseEntity.ok(guideService.getGuideById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuideDTO> updateGuide(@PathVariable Long id, @RequestBody GuideDTO guideDTO) {
        return ResponseEntity.ok(guideService.updateGuide(id, guideDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGuide(@PathVariable Long id) {
        guideService.deleteGuide(id);
        return ResponseEntity.ok("Guide Deleted Successfully");
    }
}
