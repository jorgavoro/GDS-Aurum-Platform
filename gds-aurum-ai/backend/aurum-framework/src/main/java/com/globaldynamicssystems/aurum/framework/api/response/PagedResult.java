package com.globaldynamicssystems.aurum.framework.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> items;
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}