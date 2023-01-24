<template>
  <div class="page-content">
    <PageNavbar />
    <ContactsCard />
  </div>
  <div id="footer">
    <PageFooter />
  </div>
</template>

<script>
import PageNavbar from "./components/navbar/PageNavbar.vue";
import ContactsCard from "./components/contacts/ContactsCard.vue";
import PageFooter from "./components/footer/PageFooter.vue";

export default {
  name: "App",
  components: {
    PageNavbar,
    ContactsCard,
    PageFooter,
  }
};
</script>

<style scoped>
#app {
  background-color: #fff;
  position: relative;
  min-height: 100vh;
}

.page-content {
  padding-bottom: 12rem;
}
</style>
