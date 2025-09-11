package com.enotes.repository;

import com.enotes.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {
}
