<template>
  <tr>
    <td>{{ name }}</td>
    <td>{{ telephone }}</td>
    <td>{{ email }}</td>
    <td>
      <div className="dsmeta-red-btn-container">
        <Button
          icon="pi pi-pencil"
          class="p-button-rounded p-button-warning mr-2"
          @click="editContact"
        />
        <Button
          icon="pi pi-trash"
          class="p-button-rounded p-button-danger"
          @click="deleteContact"
        />
      </div>
    </td>
  </tr>
</template>

<script>
import Button from "primevue/button";
export default {
  components: {
    Button,
  },
  data() {},
  props: {
    id: {
      type: Number,
    },
    name: {
      type: String,
    },
    telephone: {
      type: String,
    },
    email: {
      type: String,
    },
  },
  methods: {
    editContact() {
      this.$emit("edit-contact", this);
    },
    deleteContact() {
      fetch("http://localhost:3000/contacts/" + String(this.id), {
        method: "DELETE"
      });
      window.location.reload(true);
    },
  },
};
</script>

<style scoped>
tr {
  height: 74px;
  border-top: 2px solid #d3d3d3;
  background-color: #1b2531;
  color: white;
}
.dsmeta-red-btn-container {
  display: flex;
  justify-content: center;
}
</style>