package be.technobel.api.service;

import be.technobel.api.models.dto.StoreDTO;
import be.technobel.api.models.form.ChangeOpeningsForm;

import java.util.List;

public interface StoreService {

    List<StoreDTO> getAll();
    StoreDTO getOne(Long id);
    void delete(Long id);
    void changeOpenings(Long id, ChangeOpeningsForm form);
    void addProductToStore(Long storeId, Long productId);

}
