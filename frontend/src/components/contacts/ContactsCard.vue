<template>
  <div className="contacts-card">
    <h2 className="contacts-title">Contatos</h2>
    <div>
      <div className="form-control-container d-flex">
        <div class="search-contact-form d-flex p-float-label">
          <p class="form-text">Buscar contato:</p>
          <div>
            <InputText
              type="text"
              v-model="valueSearchInput"
              place-holder="Nome"
              @keyup="searchContact"
            />
          </div>
        </div>
        <Button
          label="Adicionar contato"
          icon="pi pi-plus"
          class="p-button-success"
          @click="toggleAddContact"
        />
      </div>
    </div>
    <div>
      <div v-if="addContactIsVisible" class="add-contact-container">
        <form class="d-flex add-contact-form">
          <div class="input-name">
            <label for="name">Nome:</label>
            <InputText
              id="name"
              type="text"
              v-model.trim="this.contact.nameFormInput"
              place-holder="Nome"
            />
          </div>
          <div class="input-phone">
            <label for="phone">Telefone:</label>
            <InputMask
              id="phone"
              mask="(99) 99999-9999"
              v-model="this.contact.telephoneFormInput"
              placeholder="(99) 99999-9999"
            />
          </div>
          <div class="input-email">
            <label for="email">Email:</label>
            <InputText
              id="email"
              type="text"
              v-model="this.contact.emailFormInput"
              place-holder="Email"
            />
          </div>
          <Button
            class="p-button-sm p-button-success"
            label="Salvar"
            icon="pi pi-check"
            iconPos="right"
            @click="saveContact"
          />
        </form>
      </div>
      <div>
        <table className="contacts-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Telefone</th>
              <th>Email</th>
            </tr>
          </thead>
          <tbody>
            <ContactsItem
              v-for="c in contacts"
              :id="c.id"
              :name="c.name"
              :telephone="c.phoneNumber"
              :email="c.email"
              :key="c.id"
              @edit-contact="editContact"
            />
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import InputText from "primevue/inputtext";
import ContactsItem from "./ContactsItem.vue";
import Button from "primevue/button";
import InputMask from "primevue/inputmask";

export default {
  components: {
    InputText,
    ContactsItem,
    Button,
    InputMask,
  },
  data() {
    return {
      contacts: [],
      addContactIsVisible: false,
      valueSearchInput: "",
      contact: {
        id: null,
        nameFormInput: "",
        telephoneFormInput: "",
        emailFormInput: "",
      },
    };
  },
  methods: {
    toggleAddContact() {
      this.addContactIsVisible = !this.addContactIsVisible;
    },
    searchContact() {
      fetch(
        "http://localhost:3000/contacts?" +
          new URLSearchParams({
            name: this.valueSearchInput,
          })
      )
        .then((response) => {
          if (response.ok) {
            return response.json();
          }
        })
        .then((data) => {
          const contacts = [];
          for (const id in data.content) {
            contacts.push(data.content[id]);
          }
          this.contacts = contacts;
        });
    },
    loadContacts() {
      fetch("http://localhost:3000/contacts")
        .then((response) => {
          if (response.ok) {
            return response.json();
          }
        })
        .then((data) => {
          const contacts = [];
          for (const id in data.content) {
            contacts.push(data.content[id]);
          }
          this.contacts = contacts;
        });
    },
    saveContact() {
      if(this.contact.id != null) {
        fetch("http://localhost:3000/contacts/" + String(this.contact.id), {
          method: "PUT",
          body: JSON.stringify({
            name: this.contact.nameFormInput,
            email: this.contact.emailFormInput,
            phoneNumber: this.contact.telephoneFormInput,
          }),
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
        });
      } else {
        fetch("http://localhost:3000/contacts", {
          method: "POST",
          body: JSON.stringify({
            name: this.contact.nameFormInput,
            email: this.contact.emailFormInput,
            phoneNumber: this.contact.telephoneFormInput,
          }),
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
        });
      }
      this.contact.id = null;        
      this.contact.nameFormInput = "";
      this.contact.emailFormInput = "";
      this.contact.telephoneFormInput = "";
      window.location.reload(true);
    },
    editContact(contact) {
      this.addContactIsVisible = true;
      this.contact.id = contact.id;
      this.contact.nameFormInput = contact.name;
      this.contact.emailFormInput = contact.email;
      this.contact.telephoneFormInput = contact.telephone;
    },
  },
  mounted() {
    this.loadContacts();
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Kalam&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap");

* {
  font-family: "Roboto", sans-serif;
}

.contacts-card {
  background-color: #d3d3d3;
  padding: 30px 10px;
  border: 1px solid #000000;
  border-radius: 5px;
  margin: 2.5em 2.5em 0 2.5em;
}

.contacts-title {
  color: #000;
  font-family: "Kalam", cursive;
  font-size: 3em;
  margin-bottom: 20px;
  padding-left: 0.2em;
}

.form-control-container {
  margin-bottom: 16px;
  gap: 2em;
}
.search-contact-form {
  gap: 1em;
}

.form-text {
  margin: 0.6em 0 0 1em;
  font-size: 1em;
  color: #000;
}

.add-contact-form {
  gap: 1em;
  padding: 2em 0;
}

.add-contact-form label {
  padding-right: 1em;
  font-weight: 400;
}

.input-name {
  margin-left: 1.2em;
}

.contacts-table {
  width: 100%;
  border-spacing: 0;
  border-collapse: collapse;
}

.contacts-table thead {
  height: 55px;
  font-size: 16px;
  color: #1b2531;
  font-weight: 700;
  text-align: center;
}

.contacts-table tbody {
  font-size: 16px;
  font-weight: 400;
  color: #384459;
  text-align: center;
}
</style>