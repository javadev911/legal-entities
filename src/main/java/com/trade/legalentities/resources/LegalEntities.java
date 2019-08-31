package com.trade.legalentities.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trade.legalentities.exceptions.ApplicationException;
import com.trade.legalentities.model.LegalEntity;
import com.trade.legalentities.service.LegalEntityService;

@RestController
@SuppressWarnings("rawtypes")
@RequestMapping("/legal-entities")
public class LegalEntities {

    @Autowired
    private LegalEntityService legalEntityService;

    @GetMapping("")
    public ResponseEntity<List<LegalEntity>> getAllLegalEntities() {
        return ResponseEntity.ok(legalEntityService.getAllLegalEntities());
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable Integer id) {
        LegalEntity legalEntity;
        try {
            legalEntity = legalEntityService.findLegalEntityById(id);
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(legalEntity);
    }

    @PostMapping("")
    public ResponseEntity create(final @RequestBody LegalEntity legalEntity) {
        if (legalEntity == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        try {
            return ResponseEntity.ok(legalEntityService.createLegalEntity(legalEntity));
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody LegalEntity legalEntityToUpdate, @PathVariable Integer id) {

        if(legalEntityToUpdate == null || legalEntityToUpdate.getId() != id) {
            return ResponseEntity.badRequest().build();
        }

        LegalEntity updatedLegalEntity;
        try {
            updatedLegalEntity = legalEntityService.updateLegalEntity(legalEntityToUpdate, id);
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        
        return ResponseEntity.ok(updatedLegalEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLegalEntity(@PathVariable Integer id) {
        try {
            legalEntityService.deleteLegalEntity(id);
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}