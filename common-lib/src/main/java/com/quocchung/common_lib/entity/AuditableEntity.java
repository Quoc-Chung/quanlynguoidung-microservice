package com.quocchung.common_lib.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditableEntity {

  @CreatedDate
  @Column(name = "create_at", updatable = false)
  private LocalDateTime createAt;

  @LastModifiedDate
  @Column(name = "update_at")
  private LocalDateTime updateAt;

  @Column(name = "create_by", updatable = false)
  private String createBy;

  @Column(name = "update_by")
  private String updateBy;
}