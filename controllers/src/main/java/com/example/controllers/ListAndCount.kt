package com.example.controllers

data class ListAndCount<T>(
        val list: List<T>,
        val total: Int
)