package com.mktpc.marketPlace.model.dtos.dtosRequest;

import com.mktpc.marketPlace.model.CategoryProduct;

public record ProductDTO (String name, String productDetails, CategoryProduct categoryProduct, Long quant, double priceProduct) {
}
