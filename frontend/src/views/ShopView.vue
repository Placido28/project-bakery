<template>
    <div class="shop">
        <h1>Nuestros Productos</h1>
        <div class="products-grid">
            <ProductCard v-for="product in products" :key="product.id" :product="product" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ProductCard from '@/components/ProductCard.vue';

const products = ref([
    { id: 1, name: 'Torta de chocolate', price: 25.99, image: 'https://via.placeholder.com/150' },
    { id: 2, name: 'Torta Red Velvet', price: 30.50, image: 'https://via.placeholder.com/150' },
    { id: 3, name: 'Torta Tres Leches', price: 28.00, image: 'https://via.placeholder.com/150' },
]);
</script>

<style scoped lang="scss">
.products-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: center;
}
</style>