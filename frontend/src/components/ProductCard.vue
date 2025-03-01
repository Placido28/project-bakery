<template>
    <div class="card product-card">
        <img :src="product.image" class="card-img-top" alt="Imagen del producto">
        <div class="card-body">
            <h5 class="card-title">{{ product.name }}</h5>
            <p class="card-text">${{ product.price.toFixed(2) }}</p>
            <button @click="addToCart" class="btn btn-primary">Añadir al carrito</button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import { useCartStore } from '@/stores/cart';

const props = defineProps<{ product: { id: number; name: string; price: number; image: string } }>();
const cartStore = useCartStore();

const addToCart = () => {
    cartStore.addToCart(props.product);
};
</script>

<style scoped lang="scss">
.product-card {
    width: 100%;
    max-width: 300px;
    margin: auto;
    text-align: center;
}
</style>