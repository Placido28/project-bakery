<template>
    <div class="cart-item">
        <img :src="item.image" class="cart-img" alt="Producto">
        <div class="cart-info">
            <h5>{{ item.name }}</h5>
            <p>${{ item.price.toFixed(2) }}</p>
        </div>
        <button @click="removeFromCart" class="btn btn-danger">Eliminar</button>
    </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import { useCartStore } from '@/stores/cart';

const props = defineProps<{ item: { id: number; name: string; price: number; image: string } }>();
const cartStore = useCartStore();

const removeFromCart = () => {
    cartStore.removeFromCart(props.item.id);
};
</script>

<style scoped lang="scss">
.cart-item {
    display: flex;
    align-items: center;
    gap: 15px;
    padding: 10px;
    border-bottom: 1px solid #ccc;
}

.cart-img {
    width: 50px;
    height: 50px;
    object-fit: cover;
}
</style>