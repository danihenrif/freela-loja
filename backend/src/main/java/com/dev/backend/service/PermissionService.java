package com.dev.backend.service;

import com.dev.backend.entity.Permission;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dev.backend.repository.PermissionRepository;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    public Permission addPermission(Permission permission) {
        permission.setCreationDate(new Date());
        return permissionRepository.saveAndFlush(permission);
    }

    public Permission updatePermission(Permission permission, Long id) throws NotFoundException {
        try {
            permission.setId(id);
    
            @SuppressWarnings("null")
            Optional<Permission> optionalPermission = permissionRepository.findById(id);
            if (optionalPermission.isPresent()) {
                Permission existingPermission = optionalPermission.get();
                Date recoveryCreationDate = existingPermission.getCreationDate();
                permission.setCreationDate(recoveryCreationDate);
                permission.setUpdateDate(new Date());
                return permissionRepository.saveAndFlush(permission);
            } else {
                throw new RuntimeException("Permission not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating permission with id: " + id, e);
        }
    }

    @SuppressWarnings("null")
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id).get();
        permissionRepository.delete(permission);
    }
}
