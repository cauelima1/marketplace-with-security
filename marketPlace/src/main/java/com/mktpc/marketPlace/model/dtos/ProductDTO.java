package com.mktpc.marketPlace.model.dtos;

import com.mktpc.marketPlace.model.CategoryProduct;

public record ProductDTO (String name, String productDetails, CategoryProduct categoryProduct, int quant, double priceProduct) {
}
