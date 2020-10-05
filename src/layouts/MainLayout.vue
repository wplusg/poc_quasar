<template>
  <div>
    <div class="coordenadas">
      Latitude: <strong>{{ Latitude }}</strong>
      <br />
      Longitude: <strong>{{ Longitude }}</strong>
      <br />
      Precisão: <strong>{{ Precisao }}</strong>
    </div>
    <br />
    <div class="foto" v-if="visible">
      <img width="300" height="250" :src="imageSrc" />
    </div>
    <q-btn
      id="btn_photo"
      color="primary"
      label="Get Picture"
      @click="captureImage"
    />
  </div>
</template>

<script>
import { Plugins, CameraResultType } from "@capacitor/core";

const { Camera } = Plugins;
const { Geolocation } = Plugins;

export default {
  data() {
    return {
      imageSrc: "",
      position: "determining...",
      Latitude: "Calculando...",
      Longitude: "Calculando...",
      Precisao: "Calculando...",
      visible: false,
    };
  },
  methods: {
    async captureImage() {
      const image = await Camera.getPhoto({
        quality: 90,
        allowEditing: true,
        resultType: CameraResultType.Uri,
      });
      this.imageSrc = image.webPath;
      this.visible = true;
    },
    getCurrentPosition() {      
      Geolocation.getCurrentPosition().then((position) => {
        this.Latitude = position.coords.latitude;
        this.Longitude = position.coords.longitude;
        this.Precisao = position.coords.accuracy;        
      });
    },
  },
  mounted() {
    this.getCurrentPosition();

    this.geoId = Geolocation.watchPosition({}, (position, err) => {
        this.Latitude = position.coords.latitude;
        this.Longitude = position.coords.longitude;
        this.Precisao = position.coords.accuracy;  
    });
  },
  beforeDestroy() {
    Geolocation.clearWatch(this.geoId);
  },
};
</script>

<style >
.foto {
  margin-top: 10%;
  margin-left: 10%;
}
#btn_photo {
  margin-top: 10%;
  margin-left: 30%;
}
.coordenadas {
  margin-top: 10%;
  margin-left: 25%;
}
</style>