<template>
    <div class="home">
        <h1>Bienvenido a nuestra tienda</h1>
        <div class="banner">
            <img src="@/assets/banner.png" alt="Banner de la tienda">
        </div>

        <h2>Productos Destacados</h2>
        <div class="products-grid">
            <ProductCard v-for="product in featuredProducts" :key="product.id" :product="product" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ProductCard from '@/components/ProductCard.vue';

const featuredProducts = ref([
    { id: 1, name: 'Torta de chocolate', price: 25.99, image: 'https://via.placeholder.com/150' },
    { id: 2, name: 'Torta Red Velvet', price: 30.50, image: 'https://via.placeholder.com/150' },
]);
</script>

<style scoped lang="scss">
.home {
    text-align: center;
}

.banner img {
    width: 100%;
    max-height: 300px;
    object-fit: cover;
}

.products-grid {
    display: flex;
    gap: 20px;
    justify-content: center;
}
</style>