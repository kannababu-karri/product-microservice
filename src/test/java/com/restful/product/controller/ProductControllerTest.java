package com.restful.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.product.entity.Product;
import com.restful.product.service.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    /*@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // for JSON serialization

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(1L);
        product.setProductName("Aspirin");
        product.setProductDescription("Pain reliever");
        product.setCasNumber("50-78-2");
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        Mockito.when(productService.saveOrUpdate(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("Aspirin"));
    }

    @Test
    void testCreateProduct_InvalidName() throws Exception {
        product.setProductName("");

        mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetById_Success() throws Exception {
        Mockito.when(productService.findByProductId(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/product/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Aspirin"));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        Mockito.when(productService.findByProductId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/product/id/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAll_Success() throws Exception {
        List<Product> list = Arrays.asList(product);
        Mockito.when(productService.findAllProducts()).thenReturn(list);

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].productName").value("Aspirin"));
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        Mockito.doNothing().when(productService).deleteByProductId(1L);

        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully."));
    }

    @Test
    void testDeleteProduct_InvalidId() throws Exception {
        mockMvc.perform(delete("/api/product/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetByName_Success() throws Exception {
        Mockito.when(productService.findByProductName("Aspirin")).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/product/name/Aspirin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Aspirin"));
    }

    @Test
    void testGetByName_NotFound() throws Exception {
        Mockito.when(productService.findByProductName(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/product/name/Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchByProductNameLike() throws Exception {
        List<Product> list = Arrays.asList(product);
        Mockito.when(productService.findByProductNameLike("Aspi")).thenReturn(list);

        mockMvc.perform(get("/api/product/search/productName/Aspi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Aspirin"));
    }*/
}
