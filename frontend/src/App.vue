<template>
  <NavigationBar/>
  <router-view/>
  <PageFooter/>>
</template>

<script setup lang="ts">
import NavigationBar from './components/NavigationBar.vue';
import PageFooter from './components/PageFooter.vue';
</script>
