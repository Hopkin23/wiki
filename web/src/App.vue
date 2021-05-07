<template id="">
  <a-layout id="components-layout-demo-top-side-2">
    <the-header></the-header>
    <router-view/>
    <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
  import {defineComponent} from 'vue';
  import theHeader from '@/components/the-header.vue';
  import theFooter from '@/components/the-footer.vue'

  export default defineComponent({
    name: 'App',
    components: {
      theHeader,
      theFooter,
    },
  });
</script>
