package lk.ijse.travel_booking_system.controller;

import lk.ijse.travel_booking_system.dto.TravelPackageDTO;
import lk.ijse.travel_booking_system.service.TravelPackageService;
import lk.ijse.travel_booking_system.util.PicEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/travel-packages")
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")

public class TravelPackageController {

    @Autowired
    private TravelPackageService travelPackageService;

   // private static final String UPLOAD_DIR = "uploads/";

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TravelPackageDTO> saveTravelPackage(
            @RequestPart("tPackageId")String id,
            @RequestPart("name") String name,
            @RequestPart("destination") String destination,
            @RequestPart("duration") String duration,
            @RequestPart("price") String price,
            @RequestPart("description") String description,
            @RequestPart("image") MultipartFile imageFile) {


            Double priceDouble = Double.parseDouble(price);
           Long idLong = Long.parseLong(id);

            TravelPackageDTO travelPackageDTO = new TravelPackageDTO();

            travelPackageDTO.settPackageId(idLong);
            travelPackageDTO.setName(name);
            travelPackageDTO.setDestination(destination);
            travelPackageDTO.setDuration(duration);
            travelPackageDTO.setPrice(priceDouble);
            travelPackageDTO.setDescription(description);



            String imagePath = PicEncoder.generatePicture(imageFile);

            travelPackageDTO.setImage(imagePath);
        System.out.println("controller ekata awa" +travelPackageDTO);
            boolean save =   travelPackageService.saveTravelPackage(travelPackageDTO);

      if (save){
          return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TravelPackageDTO>> getAllTravelPackages() {
        return ResponseEntity.ok(travelPackageService.getAllTravelPackages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPackageDTO> getTravelPackageById(@PathVariable Long id) {
        return ResponseEntity.ok(travelPackageService.getTravelPackageById(id));
    }

  /*  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TravelPackageDTO> updateTravelPackage(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("destination") String destination,
            @RequestParam("duration") String duration,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        //String imagePath = null;

       *//* if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imagePath = UPLOAD_DIR + imageFile.getOriginalFilename();
                Files.copy(imageFile.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }*//*

        TravelPackageDTO travelPackageDTO = new TravelPackageDTO(id, name, destination, imagePath, duration, price, description);
        return ResponseEntity.ok(travelPackageService.updateTravelPackage(id, travelPackageDTO));
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTravelPackage(@PathVariable Long id) {
        travelPackageService.deleteTravelPackage(id);
        return ResponseEntity.ok("Travel Package Deleted Successfully");
    }
}
