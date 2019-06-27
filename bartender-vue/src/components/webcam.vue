<template>
  <div>
          <div>{{msg}}</div>
          <div id="record">
              <video ref="video" id="video" width="640" height="480" autoplay></video>
              <button id="snap" v-on:click="takePicture()">Foto aufnehmen</button>
          </div>
          <div id="foto">
            <canvas ref="canvas" id="canvas" width="640" height="480"></canvas>
            <button id="record" v-on:click="activateCam()">Neue Aufnahme</button>
          </div>    
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
                captures: [],
            }
        },
  methods: {
    takePicture() {
        this.canvas = this.$refs.canvas;
        var context = this.canvas.getContext("2d").drawImage(this.video, 0, 0, 640, 480);
        this.captures.push(canvas.toDataURL("image/png"));
        $('#foto').show();
        $('#record').hide();
    },
    activateCam(){
      $('#record').show();
      $('#foto').hide();
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
    #record {

    }
    #foto {
      display: none;
    }
    li {
        display: inline;
        padding: 5px;
    }
</style>