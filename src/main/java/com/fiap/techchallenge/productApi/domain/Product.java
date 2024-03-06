package com.fiap.techchallenge.productApi.domain;

import com.fiap.techchallenge.productApi.domain.exceptions.InvalidDataException;
import com.fiap.techchallenge.productApi.util.ConstantsUtil;
import com.google.common.base.Strings;
import lombok.Builder;

import java.util.List;

public record Product(Long id,
                      String category,
                      Double price,
                      String description,
                      List<String> images) {

    @Builder
    public Product {
        if (Strings.isNullOrEmpty(description))
            throw new InvalidDataException(ConstantsUtil.INVALID_DESCRIPTION);

        if (Strings.isNullOrEmpty(category))
            throw new InvalidDataException(ConstantsUtil.INVALID_CATEGORY);

        if (price == null || price <= 0)
            throw new InvalidDataException(ConstantsUtil.INVALID_PRICE);
    }
}