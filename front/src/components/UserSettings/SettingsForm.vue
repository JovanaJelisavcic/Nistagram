<template>
  <div id="settingsContainer" class="p-3">
    <b-check v-model="isPrivate">Privatan profil</b-check>
    <b-form-group label-for="user_name" label="Ime">
      <b-input size="sm" ref="user_name" v-model="name" trim></b-input>
    </b-form-group>
    <b-form-group label-for="user_surname" label="Prezime">
      <b-input size="sm" ref="user_surname" v-model="surname" trim></b-input>
    </b-form-group>
    <b-form-group label-for="user_email" label="Email">
      <b-input size="sm" ref="user_email" v-model="email" trim></b-input>
    </b-form-group>
    <b-form-group label-for="user_phone_number" label="Broj telefona">
      <b-input size="sm" ref="user_phone_number" v-model="phone" trim></b-input>
    </b-form-group>
    <b-form-group label-for="user_sex" label="Pol">
      <b-form-radio v-model="sex" value="true">Muški</b-form-radio>
      <b-form-radio v-model="sex" value="false">Ženski</b-form-radio>
    </b-form-group>
    <div class="mb-2">
      <label for="example-datepicker">Datum rodjenja</label>
      <b-form-datepicker
        id="example-datepicker"
        v-model="dob"
      ></b-form-datepicker>
    </div>

    <b-form-group label-for="user_username" label="Korisnicko ime">
      <b-input size="sm" ref="user_username" v-model="username" trim></b-input>
    </b-form-group>
    <b-form-group label-for="user_website" label="Web sajt">
      <b-input size="sm" ref="user_website" v-model="website" trim></b-input>
    </b-form-group>
    <b-form-group label-for="user_bio" label="Biografija">
      <b-form-textarea size="sm" ref="user_bio" v-model="bio"></b-form-textarea>
    </b-form-group>
    <b-form-group label-for="user_password" label="Lozinka">
      <b-input
        size="sm"
        ref="user_password"
        v-model="password"
        type="password"
        trim
      ></b-input>
    </b-form-group>
    <b-button class="mt-3">
      Izmeni lične podatke
    </b-button>
  </div>
</template>

<script>
import { updateInfo } from "../../api";
export default {
  props: ["info"],
  data() {
    return {
      password: null,
    };
  },
  computed: {
    isPrivate: {
      get() {
        if (this.info && this.info.privacy) {
          return this.info.privacy;
        }
        return null;
      },
      set() {},
    },
    name: {
      get() {
        if (this.info && this.info.name) {
          return this.info.name;
        }
        return null;
      },
      set() {},
    },
    surname: {
      get() {
        if (this.info && this.info.surname) {
          return this.info.surname;
        }
        return null;
      },
      set() {},
    },

    email: {
      get() {
        if (this.info && this.info.email) {
          return this.info.email;
        }
        return null;
      },
      set() {},
    },
    phone: {
      get() {
        if (this.info && this.info.phoneNumber) {
          return this.info.phoneNumber;
        }
        return null;
      },
      set() {},
    },
    sex: {
      get() {
        if (this.info && this.info.sex) {
          return this.info.sex;
        }
        return null;
      },
      set() {},
    },
    dob: {
      get() {
        if (this.info && this.info.birthday) {
          return this.info.birthday;
        }
        return null;
      },
      set() {},
    },
    username: {
      get() {
        if (this.info && this.info.email) {
          return this.info.email;
        }
        return null;
      },
      set() {},
    },
    website: {
      get() {
        if (this.info && this.info.website) {
          return this.info.website;
        }
        return null;
      },
      set() {},
    },
    bio: {
      get() {
        if (this.info && this.info.bio) {
          return this.info.bio;
        }
        return null;
      },
      set() {},
    },
  },
  methods: {
    async updateInfo() {
      const params = {
        username: this.username,
        name: this.name,
        surname: this.surname,
        email: this.email,
        phoneNumber: this.phone,
        sex: this.sex,
        birthday: this.dob,
        website: this.website,
        bio: this.bio,
        privacy: this.isPrivate,
        password: this.password,
      };
      const response = await updateInfo(params);
      console.log(response);
    },
  },
};
</script>

<style>
#settingsContainer input {
  margin-bottom: 0.5rem;
}
</style>
