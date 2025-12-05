<template>
  <div id="app">
    <HeaderBar />
    <main class="page-body">
      <router-view />
    </main>
    <FooterBar />
  </div>
</template>

<script>
  import HeaderBar from "./common/Header.vue";
  import FooterBar from "./common/Footer.vue";

  export default {
    components: {FooterBar, HeaderBar}
  };
</script>
<style>

:root {
  --max-width: 1100px;
}

html, body, #app {
  height: 100%;
  margin: 0;
  font-family: Inter, Arial, Helvetica, sans-serif;
  overflow-x: hidden;
  padding: 0;
}


#app {
  display: flex;
  flex-direction: column;
}

.page-body {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  max-width: var(--max-width);
  margin: 0 auto;
}
</style>
