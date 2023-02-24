package be.technobel.api.controller;

import be.technobel.api.models.dto.StoreDTO;
import be.technobel.api.models.form.ChangeOpeningsForm;
import be.technobel.api.service.StoreService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping({"", "/all"})
    public List<StoreDTO> getAll(){
        return storeService.getAll();
    }


    // GET /store/1
    @GetMapping("/{id:[0-9]+}")
    public StoreDTO getOne(@PathVariable long id){
        return storeService.getOne(id);
    }

    // DELETE /store/1/delete
    // DELETE /store/1
    @DeleteMapping({
            "/{id:[0-9]+}",
            "/{id:[0-9]+}/delete"
    })
    public void delete(@PathVariable long id){
        storeService.delete(id);
    }

    @PatchMapping("/{id:[0-9]+}/openings")
    public void changeOpeningHours(
            @PathVariable long id,
            @RequestParam(required = false) LocalTime opensAt,
            @RequestParam(required = false) LocalTime closesAt
    ){
        storeService.changeOpenings(id, new ChangeOpeningsForm(opensAt, closesAt));
    }

    // product_id
    @PatchMapping("/{id:[0-9]+}/add")
    public void addProduct(@PathVariable("id") long storeId, @RequestParam("product_id") long productId){
        storeService.addProductToStore(storeId, productId);
    }

}
