package be.technobel.api.controller;

import be.technobel.api.models.dto.ProductDTO;
import be.technobel.api.models.form.ProductForm;
import be.technobel.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<ProductDTO> getAll(){
        return productService.getAll();
    }

    // REQUEST HTTP:
    // - URL (pathVariables)    @PathVariable
    // - METHOD (GET, POST, PUT, PATCH, DELETE, ...)
    // - params                 @RequestParam
    // - body                   @RequestBody
    // - headers                @RequestHeader

    // RESPONSE HTTP:
    // - Status                 @ResponseStatus
    // - headers                /
    // - body                   retour de mes méthodes ou @ResponseBody

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid ProductForm form) {
        productService.create(form);
    }

    @PutMapping("/{id:[0-9]+}/update")
    public void update(@PathVariable long id, @RequestBody @Valid ProductForm form){
        productService.update(id, form);
    }

    @PatchMapping("/{id:[0-9]+}")
    public void updatePrice(@PathVariable long id, @RequestParam double price){
        productService.patchPrice(id, price);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public void delete(@PathVariable long id){
        productService.delete(id);
    }

}
