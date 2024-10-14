package com.cxl.identity_service.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cxl.identity_service.entity.Permission;

@Repository
public interface PermissionRespository extends JpaRepository<Permission, String> {}
