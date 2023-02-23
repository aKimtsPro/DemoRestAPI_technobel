package be.technobel.api.service;

import be.technobel.api.models.dto.ProductDTO;
import be.technobel.api.models.form.ProductForm;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();

    void create(ProductForm form);

    void update(Long id, ProductForm form);

    void patchPrice(Long id, double price);

    void delete(Long id);

}
