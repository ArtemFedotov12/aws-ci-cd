package com.start.springlearningdemo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@ToString
@FieldNameConstants
@MappedSuperclass
// for annotations like "@CreatedDate"
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
  @Id @GeneratedValue private UUID id;

  @Column(name = "created_by")
  @CreatedBy private String createdBy;

  @Column(name = "created_at")
  @CreatedDate private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate private LocalDateTime updatedAt;

  @Column(name = "updated_by")
  @LastModifiedBy private String updatedBy;
}
