package com.mahfuz.ecommerce_backend.common.entity;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createAt);

    LocalDateTime getModifiedAt();
    void setModifiedAt(LocalDateTime modifiedAt);

    Long getCreatedBy();
    void setCreatedBy(Long createdBy);

    Long getModifiedBy();
    void setModifiedBy(Long modifiedBy);
}
