<template>
  <div
    class="h-100 d-flex align-items-center justify-content-center textShadowStrong"
  >
    <b-card
      class="w-50 p-3 text-white shadow"
      style="background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(181,82,177,1) 38%, rgba(0,255,149,1) 100%)"
    >
      <div
        class="p-3 w-100 d-inline-flex align-items-center justify-content-center"
        style="text-shadow: -1px -1px 0 #000, 1px -1px 0 #000"
      >
        <h3 class="p-3 text-outline">NIŠTAGRAM</h3>
      </div>
      <b-form-group class="p-3">
        <label class="pb-3">Korisničko ime</label>
        <b-input
          placeholder="jovana"
          v-model="username"
          class="shadow"
          trim
        ></b-input>
      </b-form-group>
      <b-form-group class="p-3">
        <label class="pb-3">Lozinka</label>
        <b-input
          v-model="password"
          class="shadow"
          type="password"
          trim
        ></b-input>
      </b-form-group>
      <div
        class="p-3 w-100 d-inline-flex align-items-center justify-content-center"
      >
        <b-button
          class="w-50 bg-white text-dark text-center shadow"
          @click="tryLogin"
          >Prijava</b-button
        >
      </div>
      <div
        class="p-3 w-100 d-inline-flex align-items-center justify-content-end"
        id="unregisteredAccess"
        @click="$router.push('/home')"
      >
        <div style="margin-right: 10px;">Nastavi bez prijave</div>
        <div class="h3">
          <b-icon style="color: #000;" icon="arrow-right-circle-fill"></b-icon>
        </div>
      </div>
    </b-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: null,
      password: null,
    };
  },
  methods: {
    async tryLogin() {
      if (
        await this.$store.dispatch("login/login", {
          email: this.username,
          password: this.password,
        })
      ) {
        this.$router.push("/home");
      }
    },
  },
};
</script>

<style>
#unregisteredAccess:hover {
  cursor: pointer;
}
</style>
