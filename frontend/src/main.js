import { createApp } from 'vue'
import App from './App.vue'
import 'spectre.css/dist/spectre.min.css'
import 'spectre.css/dist/spectre-icons.min.css'
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faAddressBook } from '@fortawesome/free-solid-svg-icons'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import PrimeVue from 'primevue/config';
import InputText from 'primevue/inputtext';
import 'primevue/resources/primevue.min.css'
import 'primevue/resources/themes/saga-blue/theme.css'
import 'primeicons/primeicons.css'
import Button from 'primevue/button';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';
import Row from 'primevue/row';
import Dialog from 'primevue/dialog';
import InputMask from 'primevue/inputmask';


library.add(faAddressBook)

const app = createApp(App);
app.use(PrimeVue);
app.component('InputText', InputText);
app.component('PrimeButton', Button);
app.component('DataTable', DataTable);
app.component('ColumnPrime', Column);
app.component('ColumnGroup', ColumnGroup);
app.component('RowPrime', Row);
app.component('DialogPrime', Dialog);
app.component('InputMask', InputMask);
app.component('font-awesome-icon', FontAwesomeIcon)
app.mount('#app')
