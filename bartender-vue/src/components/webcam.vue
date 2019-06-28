<template>
  <div>
          <div>{{msg}}</div>
          <div id="record">
              <div>
                <video ref="video" id="video" width="640" height="480" autoplay></video>
              </div>  
              <div>
                <button id="snap" v-on:click="takePicture()">Bild aufnehmen</button>
              </div>
          </div>
          <div id="picture">
            <div>
              <canvas ref="canvas" id="canvas" width="640" height="480"></canvas>
            </div>
            <div> 
              <button id="record" v-on:click="activateCam()">Neue Aufnahme</button>
              <button id="send" v-on:click="sendPicture()">Cocktail ermitteln</button>
            </div>  
          </div>    
          {{this.output}}
  </div>      
</template>

<script>
export default {
  name: 'Webcam',
  props: {
    msg: String
  },
  data() {
            return {
                video: {},
                canvas: {},
                image: {},
                output: {}
            }
        },
  methods: {
    takePicture() {
        this.canvas = this.$refs.canvas;
        var context = this.canvas.getContext("2d").drawImage(this.video, 0, 0, 640, 480);
        this.image   = canvas.toBlob(this.sendPicture, "image/jpeg");
       console.log(this.image);
        document.getElementById('picture').style.display = "block";
        document.getElementById('record').style.display = "none";
    },
    activateCam(){
      document.getElementById('record').style.display = "block";
      document.getElementById() ('picture').style.display = "none";
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
                })
                .catch(function (error) {
                    currentObj.output = error;
                });
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
    body: {
        background-color: #F0F0F0;
    }
    #app {
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
    #video {
        background-color: #000000;
    }
    #picture {
      display: none;
    }
    button{
    }
</style>