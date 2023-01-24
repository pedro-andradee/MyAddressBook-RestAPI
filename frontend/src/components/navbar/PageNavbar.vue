<template>
  <nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
      <div class="d-flex logo">
        <img
          class="img-responsive img-logo"
          src="@/assets/address-book-logo.png"
          alt="Adress Book Logo"
        />
        <span class="logo-text">MyAddressBook</span>
      </div>
      <div>
        <form class="d-flex search-form">
          <span class="p-input-icon-left">
            <i class="pi pi-search" />
            <InputText class="p-inputtext-sm" type="text"  placeholder="Search" />
          </span>
          <Button class="p-button-sm search-button" label="Search" disabled="disabled"/>
        </form>
      </div>
    </div>
  </nav>
</template>

<script>
import InputText from "primevue/inputtext";
import Button from 'primevue/button';

export default {
  components: {
    InputText,
    Button
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Kalam&display=swap");
.navbar {
  background-color: #1B2531;
}
.logo {
  align-items: center;
}
.img-logo {
  max-width: 5rem;
  margin: 0 auto;
}
.logo-text {
  font-family: "Kalam", cursive;
  font-size: 2rem;
  color: #5fa8d3;
  margin: 0;
}
.search-form {
    gap: 0.5rem;
    margin-right: 1em;
}
</style>