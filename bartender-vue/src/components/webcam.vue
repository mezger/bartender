<template>
  <div class="webcam-component">
          <div id="record">
              <div>
                <video ref="video" id="video" width="640" height="480" autoplay></video>
              </div>  
              <div>
                <b-button id="snap" variant="primary" v-on:click="takePicture()">Mache ein Bild von dir!</b-button>
              </div>
          </div>
          <div id="picture">
            <div>
              <canvas ref="canvas" id="canvas" width="640" height="480"></canvas>
            </div>
            <div> 
              <b-button id="record" variant="primary" v-on:click="activateCam()">Neue Aufnahme</b-button>
            </div>  
          </div>
          <klapper :data="output" :aufbereiteteDaten="formatedData"></klapper> 
  </div>      
</template>

<script>
import Klapper from './klapper.vue'

export default {
  name: 'Webcam',
  components: {
    Klapper
  },
  props: {
    msg: String
  },
  data() {
            return {
                video: {},
                canvas: {},
                image: {},
                output: {},
                formatedData: {}
            }
        },
  methods: {
    takePicture() {
        this.canvas = this.$refs.canvas;
        this.canvas.getContext("2d").drawImage(this.video, 0, 0, 640, 480);
        this.image   = this.canvas.toBlob(this.sendPicture, "image/jpeg");
        document.getElementById('picture').style.display = "block";
        document.getElementById('record').style.display = "none";
    },
    activateCam(){
      document.getElementById('record').style.display = "block";
      document.getElementById('picture').style.display = "none";
    },
    sendPicture(picture){
                let currentObj = this;
                const formData = new FormData();
                formData.append('picture', picture);
                this.axios({
                    method: 'post',
                    url: 'http://localhost:8080/cocktailForImage',
                    data: formData,
                    config: { headers: {'Content-Type': 'multipart/form-data' }}
                })
                .then(function (response) {
                   currentObj.output = response.data;
                   this.createTable();
                   currentObj.$emit("cocktailFound", response.data);
                })
                .catch(function (error) {
                    currentObj.output = error;
                });
    },
      addToTable(key, value) {
           this.table.append('<tr><td>'+ key + '</td><td>'+ value + '</td></tr>');
       },
       createTable() {
            const rekognitionResult = this.output.rekognitionResult;
            this.addToTable('Alter', rekognitionResult.age);
            this.addToTable('Geschlecht', rekognitionResult.faceList[0].gender.value);     
       }
  },
  mounted() {
    this.video = this.$refs.video;
    if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia({ video: true }).then(stream => {
            this.video.srcObject=stream;
            this.video.play();
        });
    }
  }
}
</script>
<style>
    #video {
        background-color: #000000;
    }
    #picture {
      display: none;
    }
    div .btn{
      font-size: 1.5rem;
      margin: 5px;
    }
    #newPicture{
      background-color: #777272;
    }
</style>