import { defineStore } from 'pinia';

export const useCartStore = defineStore('cart', {
    state: () => ({
        items: [] as { id: number; name: string; price: number; quantity: number }[],
    }),
    getters: {
        totalPrice: (state) => state.items.reduce((acc, item) => acc + item.price * item.quantity, 0),
    },
    actions: {
        addItem(product: { id: number; name: string; price: number }) {
            const existing = this.items.find((item) => item.id === product.id);
            if (existing) {
                existing.quantity++;
            } else {
                this.items.push({ ...product, quantity: 1 });
            }
        },
        removeItem(id: number) {
            this.items = this.items.filter((item) => item.id !== id);
        },
    },
});