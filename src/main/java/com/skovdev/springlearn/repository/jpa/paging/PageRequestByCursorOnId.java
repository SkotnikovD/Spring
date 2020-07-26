package com.skovdev.springlearn.repository.jpa.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;

@Data
@Accessors(chain=true)
@AllArgsConstructor
public class PageRequestByCursorOnId {

    /**
     * Element id from which you want returned page to start (excluded specified id)
     * If Null, then first page will be retried
     */
    @Nullable
    Integer fromId;

    int pageSize;

}
