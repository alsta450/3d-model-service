package com.maas._d_model_service.file_access.repository;

import com.maas._d_model_service.file_access.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
